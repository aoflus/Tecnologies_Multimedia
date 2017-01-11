/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.GestioImatge.Marc;
import Model.GestioImatge.Tesseles;
import static Model.JPEGCompress.compressInJPEG;
import Vista.Viewer;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

/**
 * Clase codificador que contindra metodes que permetran realitzar la codificacio entre frames.
 * @author Victor i Alvaro
 */
public class Codificador {

    HashMap<Integer, Image> unzippedImg = new HashMap<Integer, Image>();
    int gop = 5, seek, ntiles, quality;
    ArrayList <ArrayList> listaListasGOP = new ArrayList <ArrayList>();
    ArrayList <Marc> listaGOP = new ArrayList <Marc>();
    int height, width;
    ArrayList<Marc> comprimides = new ArrayList<Marc>();
    ArrayList<Tesseles> tesselesAcum = new ArrayList<>();
    
    public Codificador(HashMap<Integer, Image> bufferWithUnzippedImg, int gop, int ntiles, int seek, int quality) {
        System.out.println("Imagenes leidas");
        this.gop = gop;
        System.out.println("GOP: " + this.gop);
        this.ntiles = ntiles;
        this.seek = seek;
        this.quality = quality;
        this.unzippedImg = bufferWithUnzippedImg;
        this.aplicaFiltres();
        this.ompleGOP();
        this.recorreGOP();

    }

    /**
     * Metodo que aplica los filtros seleccionados por el user.
     */
    private void aplicaFiltres() {
        System.out.println("Aplicamos filtros seleccionados por el usuario. No implementado."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Recorremos un numero de imagenes definido por el parametro GOP,
     * Y a partir de esta secuencia de imagenes comprimimos, la primera
     * sera la de referencia.
     */
    private void ompleGOP(){ // Hacemos una lista por cada tira de imagenes
        System.out.println("ompleGOP");
        for(int x = 0 ; x<unzippedImg.size();x++){
            if(x % this.gop == 0 || x + 1 >= unzippedImg.size()){  //Empezamos lista
                //Si gop es 5, entra en el if en el 0, en el 5, en el 10...
                if(!listaGOP.isEmpty()){
                    if(x + 1 >= unzippedImg.size()){
                        
                        this.listaGOP.add(new Marc((BufferedImage) unzippedImg.get(x), x));
                    };
                    Utils zipp= new Utils();
                    zipp.saveZip(listaGOP,"nouZip.zip");
                    listaListasGOP.add(listaGOP);
                }
                listaGOP = new ArrayList <Marc>();
            }
            //Añadimos imagen a la lista GOP.
            this.listaGOP.add(new Marc((BufferedImage) unzippedImg.get(x), x));
            System.out.println("Iteracions: " + x);
        }
        System.out.println("Fin omple GOP");
    }

    
    private void recorreGOP(){
        System.out.println("recorreGOP");
        int x = 0;
        Marc n = null;
        Marc n_1 = null;
        for (int p = 0;p<listaListasGOP.size();p++){
            //System.out.println("Generem sequencia");
            for (int z = 0;z<listaListasGOP.get(p).size()-1;z++){
                n = (Marc) listaListasGOP.get(p).get(z);
                if(z == 0){
                    //Primera imagen
                    comprimides.add(n);
                }
                n_1 = (Marc) listaListasGOP.get(p).get(z+1);
                this.width = n_1.getImage().getWidth()/this.ntiles;
                this.height = n_1.getImage().getHeight()/this.ntiles;
                n.setTesseles(subdividirImgTesseles(n.getImage()));
                n.setTesseles(findCompatibleBlock(n, n_1.getImage()));
                Marc resultat = new Marc(setPFramesColor(n.getTesseles(), n_1.getImage()),5);
                comprimides.add(resultat);
                for(Tesseles t : n.getTesseles()) this.tesselesAcum.add(t);
            }
            
        }
        //System.out.println("FORAAAA: " + this.tesselesAcum.size());
        //System.out.println("IMAGES SIZE: " + this.comprimides.size());       
        this.saveZIP();
        
    }    
    
 public ArrayList<Tesseles> subdividirImgTesseles(BufferedImage image){
        ArrayList<Tesseles> teseles = new ArrayList<>();
        Tesseles tesela;
        int comptador = 0;
        for(float y=0; y<Math.round(image.getHeight()); y+=this.height){
            for(float x=0; x<Math.round(image.getWidth()); x+=this.width){
                x=Math.round(x);
                y=Math.round(y);
                tesela = new Tesseles(image.getSubimage((int)x, (int) y, (int)this.width, (int)this.height), comptador);
                teseles.add(tesela);
                comptador++;
            }
        }
//        System.out.println("-------------------------------------------------------");
//        System.out.println("teseles:" + teseles.size());
//        System.out.println("-------------------------------------------------------");
        //System.out.println("Fin tesseles.");
        return teseles;
    }
    
/*
 * -----------------ENCODING FUNCTIONS -----------------
 *
 */
 
 
   private ArrayList<Tesseles> findCompatibleBlock(Marc iFrame, BufferedImage pFrame) {
        float maxPSNR = Float.MIN_VALUE;
        int xMaxValue = 0, yMaxValue = 0, xMin, xMax, yMin, yMax, idTesela, idX, idY;
        ArrayList<Tesseles> teselesResultants = new ArrayList<>();
        for (Tesseles t : iFrame.getTesseles()) {
            maxPSNR = Float.MIN_VALUE;
            idTesela = t.getId();
            idY = (idTesela%this.ntiles)*this.width;
            idX = ((int)Math.ceil(idTesela/this.ntiles))*this.height;
            xMin = ((idX - this.seek) < 0) ? 0 : (idX - this.seek);
            yMin = ((idY - this.seek) < 0) ? 0 : (idY - this.seek);
            xMax = (idX + this.seek + this.height) > (((BufferedImage) this.unzippedImg.get(0)).getHeight()) ? (((BufferedImage) this.unzippedImg.get(0)).getHeight())  : (idX + this.seek + this.height);
            yMax = (idY + this.seek + this.width) > (((BufferedImage) this.unzippedImg.get(0)).getWidth())  ? (((BufferedImage) this.unzippedImg.get(0)).getWidth())  : (idY + this.seek + this.width);
            for(int x=xMin; x<=xMax-this.height; x++){
                for(int y=yMin; y<=yMax-this.width; y++){
                    float psnr = calculatePSNR(t, pFrame.getSubimage(y, x, this.width, this.height));
                    if(psnr > maxPSNR && psnr >= this.quality){
                        maxPSNR = psnr;
                        xMaxValue = x;
                        yMaxValue = y;
                    }
                }
            }
            if(maxPSNR != Float.MIN_VALUE){
                t.setCoordDestiX(xMaxValue);
                t.setCoordDestiY(yMaxValue);
            }else{
                t.setCoordDestiX(-1);
                t.setCoordDestiY(-1);
            }
            teselesResultants.add(t);
        }
        return(teselesResultants);
    }
    

    private float calculatePSNR(Tesseles tesela, BufferedImage pframe){
        float noise = 0, mse = 0, psnr = 0;
        BufferedImage iFrame = tesela.getTessela();
        for (int i=0; i<iFrame.getHeight(); i++) {
            for (int j=0; j<iFrame.getWidth(); j++) {
                Color iframe_rgb = new Color(iFrame.getRGB(j, i));
                Color pframe_rgb = new Color(pframe.getRGB(j, i));
                noise += Math.pow(pframe_rgb.getRed() - iframe_rgb.getRed(), 2);
                noise += Math.pow(pframe_rgb.getGreen()- iframe_rgb.getGreen(), 2);
                noise += Math.pow(pframe_rgb.getBlue() - iframe_rgb.getBlue(), 2);
            }
          }
        mse = noise/(iFrame.getHeight()*iFrame.getWidth()*3);
        psnr = (float) (10 * Math.log10((255 * 255) / mse));
        return psnr;
    }
    
    private BufferedImage setPFramesColor(ArrayList<Tesseles> teseles, BufferedImage pFrame){
        BufferedImage result = pFrame;
        for(Tesseles t : teseles){
            int x = t.getCoordDestiX();
            int y = t.getCoordDestiY();
            if(x != -1 && y != -1){
                Color c = meanColorImage(t.getTessela());
                for(int xCoord=x; xCoord<(x+this.height); xCoord++){
                    for(int yCoord=y; yCoord<(y+this.width); yCoord++){
                        result.setRGB(yCoord, xCoord, c.getRGB());
                    }
                }
            }
        }
        return result;
    }
    

    private Color meanColorImage(BufferedImage im){
        Color color;
        int sumR=0, sumG=0, sumB=0, pixelCount=0;
        for(int x=0; x<this.width; x++){
            for(int y=0; y<this.height; y++){
                color = new Color(im.getRGB(x, y));
                pixelCount++;
                sumR += color.getRed();
                sumG += color.getGreen();
                sumB += color.getBlue();
            }
        }
        return new Color((sumR/pixelCount),(sumG/pixelCount),(sumB/pixelCount));
    }
 

    private void saveCompressedImages(){
        for(ArrayList<Marc>  p: this.listaListasGOP){
            for(Marc f : p){
                try {
                    ImageIO.write(f.getImage(), "jpeg", new File("src/resources/Compressed/frame"+String.format("%03d",f.getId())+".jpeg"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void saveZIP(){
        new File("src/resources/Compressed").mkdirs();
        this.makeCoordsFile();
        this.saveCompressedImages();
        this.zipFolder("src/resources/Compressed","src/resources/Compressed.zip");
        this.deleteDir(new File("src/resources/Compressed"));
    }
    
    private void makeCoordsFile(){
        BufferedWriter bw = null;  
        try {
            String name = "src/resources/Compressed/coords.txt";
            bw = new BufferedWriter(new FileWriter(name));
            for (Tesseles t : this.tesselesAcum) {
                //System.out.println(t.getId()+" "+t.getCoordDestiX()+" "+t.getCoordDestiY());
                bw.write(t.getId()+" "+t.getCoordDestiX()+" "+t.getCoordDestiY()+"\n");  
            }
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    

    private void zipFolder(String srcFolder, String destZipFile){
        try {
            ZipOutputStream zip = null;
            FileOutputStream fileWriter = null;
            
            fileWriter = new FileOutputStream(destZipFile);
            zip = new ZipOutputStream(fileWriter);
            
            addFolderToZip("", srcFolder, zip);
            zip.flush();
            zip.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addFileToZip(String path, String srcFile, ZipOutputStream zip){
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
          addFolderToZip(path, srcFile, zip);
        } else {
            FileInputStream in = null;
            try {
                byte[] buf = new byte[1024];
                int len;
                in = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                } } catch (FileNotFoundException ex) {
                Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
  }
   
    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip){
        File folder = new File(srcFolder);
        for (String fileName : folder.list()) {
          if (path.equals("")) {
            addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
          } else {
            addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
          }
        }
    }
   
    private boolean deleteDir(File dir) {
      if (dir.isDirectory()) {
         String[] children = dir.list();
         for (int i = 0; i < children.length; i++) {
            boolean success = deleteDir
            (new File(dir, children[i]));
            if (!success) {
               return false;
            }
         }
      }
      return dir.delete();
  }
}
