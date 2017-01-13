/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.GestioImatge.Marc;
import Model.GestioImatge.Tesseles;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Clase codificador que contindra metodes que permetran realitzar la
 * codificacio entre frames.
 *
 * @author Victor i Alvaro
 */
public class Codificador {

    HashMap<Integer, Image> unzippedImg = new HashMap<Integer, Image>();
    int gop = 5, seek, ntiles, quality;
    ArrayList<ArrayList> listaListasGOP = new ArrayList<ArrayList>();
    ArrayList<Marc> listaGOP = new ArrayList<Marc>();
    int height, width;
    ArrayList<Marc> comprimides = new ArrayList<Marc>();
    ArrayList<Tesseles> tesselesAcum = new ArrayList<>();
    Utils zipp;
    String output;
    public Codificador(HashMap<Integer, Image> bufferWithUnzippedImg, int gop, int ntiles, int seek, int quality, String output) {
        this.zipp = new Utils();
        //System.out.println("Imagenes leidas");
        this.gop = gop;
        this.output = output;
        //System.out.println("GOP: " + this.gop);
        this.ntiles = ntiles;
        this.seek = seek;
        this.quality = quality;
        this.unzippedImg = bufferWithUnzippedImg;
        this.ompleGOP();
        this.recorreGOP();
        
    }

    /**
     * Recorremos un numero de imagenes definido por el parametro GOP, Y a
     * partir de esta secuencia de imagenes comprimimos, la primera sera la de
     * referencia.
     */
    private void ompleGOP() { // Hacemos una lista por cada tira de imagenes
        //System.out.println("ompleGOP");
        for (int x = 0; x < unzippedImg.size(); x++) {
            if (x % this.gop == 0 || x + 1 >= unzippedImg.size()) {  //Empezamos lista
                //Si gop es 5, entra en el if en el 0, en el 5, en el 10...
                if (!listaGOP.isEmpty()) {
                    if (x + 1 >= unzippedImg.size()) {
                        this.listaGOP.add(new Marc((BufferedImage) unzippedImg.get(x), x));
                    };
                    zipp.saveZip(listaGOP, "nouZip.zip");
                    listaListasGOP.add(listaGOP);
                }
                listaGOP = new ArrayList<Marc>();
            }
            //Añadimos imagen a la lista GOP.
            this.listaGOP.add(new Marc((BufferedImage) unzippedImg.get(x), x));
        }
    }

    /**
     * El metode recorreGOP selecciona per cada un dels marcs generats a partir de les group
     * of pictures (GOP) les tesseles de les imatges que configuran la llista que son compatibles 
     * amb el marc, crea un marc nou amb la mitja de color obtingut entre les tesseles compatibles, i 
     * guarda les imatges resultades un cop aplicat el marc en la llista comprimides.
     */
    private void recorreGOP() {
        //System.out.println("recorreGOP");
        int x = 0;
        Marc n;
        Marc n_1;
        for (int p = 0; p < listaListasGOP.size(); p++) {
            for (int z = 0; z < listaListasGOP.get(p).size() - 1; z++) {
                n = (Marc) listaListasGOP.get(p).get(z);
                if (z == 0) {
                    //Primera imagen
                    comprimides.add(n);
                }
                n_1 = (Marc) listaListasGOP.get(p).get(z + 1);
                this.width = n_1.getImage().getWidth() / this.ntiles;
                this.height = n_1.getImage().getHeight() / this.ntiles;
                n.setTesseles(subdividirImgTesseles(n.getImage()));
                n.setTesseles(buscarTesselesIguals(n, n_1.getImage()));
                Marc resultat = new Marc(setColorPFrames(n.getTesseles(), n_1.getImage()), 5);
                comprimides.add(resultat);
                for (Tesseles t : n.getTesseles()) {
                    this.tesselesAcum.add(t);
                }
            }

        }
        this.guardarZIP();

    }

    /**
     * El metode subdividirImgTesseles, a partir de una imatge passada per parametre, divideix
     * aquesta en tesseles, i les guarda en una arraylist que envia com a retorn de la funcio.
     * @param image
     * @return 
     */
    public ArrayList<Tesseles> subdividirImgTesseles(BufferedImage image) {
        ArrayList<Tesseles> teseles = new ArrayList<>();
        Tesseles tesela;
        int comptador = 0;
        for (float y = 0; y < Math.round(image.getHeight()); y += this.height) {
            for (float x = 0; x < Math.round(image.getWidth()); x += this.width) {
                x = Math.round(x);
                y = Math.round(y);
                tesela = new Tesseles(image.getSubimage((int) x, (int) y, (int) this.width, (int) this.height), comptador);
                teseles.add(tesela);
                comptador++;
            }
        }
//        System.out.println("-------------------------------------------------------");
//        System.out.println("teseles:" + teseles.size());
//        System.out.println("-------------------------------------------------------");
        return teseles;
    }

    /*
 * -----------------ENCODING FUNCTIONS -----------------
 *
     */
    /**
     * La funció buscarTesselesIguals, donat un marc I i una imatge P, compara
     * les similituds en les tesseles generades a la imatge P amb el marc I, i
     * en cas de trobar similituds, guarda les tesseles resultats en una
     * variable de retorn.
     *
     * @param iFrame
     * @param pFrame
     * @return
     */
    private ArrayList<Tesseles> buscarTesselesIguals(Marc iFrame, BufferedImage pFrame) {
        float maxPSNR;
        int xMaxValue = 0;
        int yMaxValue = 0;
        int x, y, minX, maxX, minY, maxY, id;
        ArrayList<Tesseles> teselesResultants = new ArrayList<>();

        for (Tesseles t : iFrame.getTesseles()) {
            maxPSNR = Float.MIN_VALUE;
            id = t.getId();
            // Mida de les tesseles
            x = ((int) Math.ceil(id / ntiles)) * height;
            y = (id % ntiles) * width;
            // Valor minim un cop afegida exploracio
            minX = ((x - seek) < 0) ? 0 : (x - seek);
            minY = ((y - seek) < 0) ? 0 : (y - seek);
            // Valor maxim un cop afegida exploracio
            maxX = (x + height + seek) > (((BufferedImage) unzippedImg.get(0)).getHeight()) ? 
                    (((BufferedImage) unzippedImg.get(0)).getHeight()) : (x + height + seek);
            maxY = (y + width + seek) > (((BufferedImage) unzippedImg.get(0)).getWidth()) ? 
                    (((BufferedImage) unzippedImg.get(0)).getWidth()) : (y + width + seek);

            //Iterem sobre les teseles i busquem coincidencies PSNR
            for (int i = minX; i <= maxX - height; i++) {
                for (int j = minY; j <= maxY - width; j++) {
                    float psnr = calcularRatioPSNR(t, pFrame.getSubimage(j, i, width, height));
                    if (psnr > maxPSNR && psnr >= quality) {
                        maxPSNR = psnr;
                        xMaxValue = i;
                        yMaxValue = j;
                    }
                }
            }
            //Si no trobem mes equivalencies, busquem a la seguent tesela.
            if (maxPSNR != Float.MIN_VALUE) {
                t.setCoordDestiX(xMaxValue);
                t.setCoordDestiY(yMaxValue);
            } 
            else {
                t.setCoordDestiX(-1);
                t.setCoordDestiY(-1);
            }
            teselesResultants.add(t);
        }
        return (teselesResultants);
    }

    /**
     * La funcio calcularRatioPSNR calcula donat un marc I i una imatge J, la relació
     * Peak signal-to-noise ratio entre el marc I i les tesseles de la imatge J.
     * @param tesela
     * @param pframe
     * @return 
     */
    private float calcularRatioPSNR(Tesseles tesela, BufferedImage pframe) {
        float soroll = 0, mse = 0, psnr = 0;
        BufferedImage iFrame = tesela.getTessela();
        for (int i = 0; i < iFrame.getHeight(); i++) {
            for (int j = 0; j < iFrame.getWidth(); j++) {
                Color iframe_rgb = new Color(iFrame.getRGB(j, i));
                Color pframe_rgb = new Color(pframe.getRGB(j, i));
                soroll = (float) (soroll + Math.pow(pframe_rgb.getRed() - iframe_rgb.getRed(), 2));
                soroll = (float) (soroll + Math.pow(pframe_rgb.getGreen() - iframe_rgb.getGreen(), 2));
                soroll = (float) (soroll + Math.pow(pframe_rgb.getBlue() - iframe_rgb.getBlue(), 2));
            }
        }
        mse = soroll / (iFrame.getHeight() * iFrame.getWidth() * 3);
        psnr = (float) (10 * Math.log10((255 * 255) / mse));
        return psnr;
    }

    /**
     * La funció medianaColor retorna la mediana del color dels pixels de una imatge.
     * @param im
     * @return 
     */
    private Color medianaColor(BufferedImage im) {
        Color color;
        int sumR = 0; 
        int sumG = 0; 
        int sumB = 0; 
        int pixelCount = 0;
        int red, green, blue;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                color = new Color(im.getRGB(x, y));
                pixelCount++;
                sumR = sumR + color.getRed();
                sumG = sumG + color.getGreen();
                sumB = sumB + color.getBlue();
            }
        }
        red = sumR / pixelCount;
        green = sumG / pixelCount;
        blue = sumB / pixelCount;
        return new Color(red, green, blue);
    }

    /**
     * La funcio setColorPFrames aplica el color encontrado por la mediana de color del pFrame a 
     * las teselas que dieron coincidencia.
     * pFrame a las tesselas seleccionada
     * @param teseles
     * @param pFrame
     * @return 
     */
    private BufferedImage setColorPFrames(ArrayList<Tesseles> teseles, BufferedImage pFrame) {
        BufferedImage result = pFrame;
        teseles.forEach((t) -> {
            int x = t.getCoordDestiX();
            int y = t.getCoordDestiY();
            if (x != -1 && y != -1) {
                Color c = medianaColor(t.getTessela());
                for (int xCoord = x; xCoord < (x + height); xCoord++) {
                    for (int yCoord = y; yCoord < (y + width); yCoord++) {
                        result.setRGB(yCoord, xCoord, c.getRGB());
                    }
                }
            }
        });
        return result;
    }

    /**
     * La funcion guardarImagenes garda las imagenes una vez codificadas.
     */
    private void guardarImagenes() {
        JPEGCompress jpegcomp = new JPEGCompress();
        for (ArrayList<Marc> p : listaListasGOP) {
            p.forEach((f) -> {
                try {
                    JPEGCompress.compressInJPEG(f.getImage(), "src/resources/Compressed/",  "marc" +String.format("%02d", f.getId()) + ".jpeg");
                } catch (IOException ex) {
                    System.err.println("Excepcion IO detectada" + ex);
                }
            });
        }
    }

    // ---------------------- METODOS GUARDADO EN ZIP UTILS ----------------------------------
    
    /**
     * la función guardarZIP se encarga de crear un zip con las imagenes y las coordenadas para
     * poder realizar la decodificacion.
     */
    private void guardarZIP() {
        new File("src/resources/Compressed").mkdirs();
        this.zipp.crearTxTCoordenadas(this.tesselesAcum);
        this.guardarImagenes();
        this.zipp.crearCarpetaZIP("src/resources/Compressed", "src/resources/"+this.output);
        File outptFile = new File("src/resources/"+this.output);
        this.zipp.borrarDirectorio(new File("src/resources/Compressed"));
    }


}
