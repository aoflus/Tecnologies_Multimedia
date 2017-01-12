/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Clase Decodificador que realitzarà la tasca de descodificar el nostre codec.
 *
 * @author Victor i Alvaro
 */
import Model.GestioImatge.Tesseles;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;
import static Model.JPEGCompress.compressInJPEG;

/**
 *
 * @author Victor Fernandez - Alvaro Ortega
 */
public class Decodificador {

    private ArrayList<Integer> ids;
    private ArrayList<Integer> xCoords;
    private ArrayList<Integer> yCoords;
    private ArrayList<BufferedImage> imatges;
    private int gop;
    private int tileWidth;
    private int tileHeight;
    private int nTiles;

    /**
     * Clase decodificador, que a partir de una archivo comprimido codificado y
     * un documento con los parametros de codificacion, descomprime el archivo y
     * recompone (decodifica) las imagenes.
     *
     * @param gop
     * @param nTiles
     */
    public Decodificador(int gop, int nTiles) {
        this.gop = gop;
        this.nTiles = nTiles;
        this.ids = new ArrayList<>();
        this.xCoords = new ArrayList<>();
        this.yCoords = new ArrayList<>();
        this.imatges = new ArrayList<>();

    }

    /**
     * Constructor de la clase decode, que inicializa los metodos internos, y
     * selecciona el fichero de descompresion.
     */
    public void decode() {
        this.readZip();
        this.iterateImages();
        new File("src/decompressed/").mkdirs();
        int comptador = 0;
        for (BufferedImage i : this.imatges) {
            try {
                compressInJPEG(i, "src/decompressed/", String.valueOf(comptador) + ".jpeg");
            } catch (IOException ex) {
                Logger.getLogger(Decodificador.class.getName()).log(Level.SEVERE, null, ex);
            }
            comptador++;
        }
    }

    /**
     * El metodo readZip permite leer un archivo zip codificado y descodificarlo
     * a la vez que descomprime el zip. Cuando se descomprime uno de los
     * elementos del zip, se busca en la lista de parametros las coordenadas y
     * se decodifica.
     */
    public void readZip() {
        try {
            File f = new File("src/resources/Compressed.zip");
            ZipFile z = new ZipFile(f);
            Enumeration<? extends ZipEntry> entries = z.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.equalsIgnoreCase("Compressed/coords.txt")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(z.getInputStream(entry)));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" ");
                        ids.add(Integer.parseInt(parts[0]));
                        xCoords.add(Integer.parseInt(parts[1]));
                        yCoords.add(Integer.parseInt(parts[2]));
                    }
                    reader.close();
                } else {
                    BufferedImage im = ImageIO.read(z.getInputStream(entry));
                    this.imatges.add(im);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Decodificador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que reconstruye las imagenes.
     */
    private void iterateImages() {
        this.tileWidth = this.imatges.get(0).getWidth() / nTiles;
        this.tileHeight = this.imatges.get(0).getHeight() / nTiles;
        BufferedImage iframe = null;
        for (int x = 0; x < this.imatges.size() - 1; x++) {
            BufferedImage frame = this.imatges.get(x);
            if (x % this.gop == 0) {
                iframe = frame;
            } else if (x == this.imatges.size() - 2) {
                this.buildPFrames(iframe, frame);
                this.buildPFrames(frame, this.imatges.get(x + 1));
            } else {
                this.buildPFrames(iframe, frame);
            }
        }
    }

    /**
     * Metodo auxiliar del metodo iterateImages, que a partir de un frame I y un 
     * frame P utilizando las teselas generadas por el metodo generateMacroblocks
     * a partir del frame I, aplica la media de color al frame P.
     * @param iframe
     * @param pframe 
     */
    private void buildPFrames(BufferedImage iframe, BufferedImage pframe) {
        ArrayList<Tesseles> tiles = generateMacroblocks(iframe);
        for (int i = 0; i < ids.size(); i++) {
            Tesseles t = tiles.get(ids.get(i));
            Integer x = xCoords.get(i);
            Integer y = yCoords.get(i);
            if (x != -1 && y != -1) {
                for (int xCoord = 0; xCoord < (this.tileHeight); xCoord++) {
                    for (int yCoord = 0; yCoord < (this.tileWidth); yCoord++) {
                        int rgb = t.getTessela().getRGB(yCoord, xCoord);
                        pframe.setRGB(yCoord + y, xCoord + x, rgb);
                    }
                }
            }
        }
    }

    /**
     * Metodo auxiliar del metodo buildPFrames, que a partir de una imagen pasada por parametro
     * permite subdividir dicha imagen en una array de teselas, que devuelve como retorno.
     * @param image
     * @return array de teselas de la imagen.
     */
    public ArrayList<Tesseles> generateMacroblocks(BufferedImage image) {
        ArrayList<Tesseles> teseles = new ArrayList<>();
        Tesseles t;
        int count = 0;
        for (int y = 0; y < image.getHeight(); y += this.tileHeight) {
            for (int x = 0; x < image.getWidth(); x += this.tileWidth) {
                t = new Tesseles(image.getSubimage(x, y, this.tileWidth, this.tileHeight), count);
                teseles.add(t);
                count++;
            }
        }
        return teseles;
    }

}
