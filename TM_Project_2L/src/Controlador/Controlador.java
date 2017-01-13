/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Model.Codificador;
import Model.Decodificador;
import Model.Utils;
import Model.Filtres;
import static Model.JPEGCompress.compressInJPEG;
import Model.TimerEx;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tota accio logica es realitzara utilitzant el controlador com a intermediari
 * @author Victor i Alvaro
 */
public class Controlador {
    public static final String AVE = "ave";
    public static final String NEG = "neg";
    public static final String BIN = "bin";
    public int comptador = 0;
    public String output;
    public HashMap<Integer, Image> bufferWithUnzippedImg = null;
    public HashMap<Integer, Image> bufferWithUnzippedImgNegative = new HashMap<Integer, Image>(); //Guardarem les sortides
    public HashMap<Integer, Image> bufferWithUnzippedImgBinarized = new HashMap<Integer, Image>();
    public HashMap<Integer, Image> bufferWithUnzippedImgAveraged = new HashMap<Integer, Image>();
    public HashMap<Integer, Image> unzippedImg = new HashMap<Integer, Image>();
    public boolean batch = false;
    public Controlador(String output,boolean batch){
        this.output=output;
        this.batch = batch;
    }
    /**
     * Obre zip, crida al utils per a parsejar les imatges.
     */
    public void obreZip(String rutaZipProva) {
        this.bufferWithUnzippedImg = Utils.unZipping(rutaZipProva);
    }
    
    /**
     * Metode que passantli un string que identifiqui el tipus de filtre genera un hashmap amb les imatges amb el filtre.
     * @param filtre
     * @param param 
     */
    public void carregaImFiltrades(String filtre, int param){
        int limit = bufferWithUnzippedImg.size();
        ArrayList<Image> llistaImatgesParam = new ArrayList<Image>();
        for (int x=0 ; x<limit; x++){
            if(filtre == AVE){
                bufferWithUnzippedImgAveraged.put(x, Filtres.average1((BufferedImage)bufferWithUnzippedImg.get(x),param));
            }else if(filtre == NEG){
                bufferWithUnzippedImgNegative.put(x, Filtres.negative((BufferedImage)bufferWithUnzippedImg.get(x)));
            }else if (filtre == BIN){
                bufferWithUnzippedImgBinarized.put(x, Filtres.binarization((BufferedImage)bufferWithUnzippedImg.get(x),param));
            }
        }// Quan acabem de guardar a la llista auxiliar, remplacem amb la que utilitzarem sempre i aixi s'acumularan els filtres.
        if(filtre == AVE){
            this.bufferWithUnzippedImg = bufferWithUnzippedImgAveraged;
        }else if(filtre == NEG){
            this.bufferWithUnzippedImg = bufferWithUnzippedImgNegative;
        }else if (filtre == BIN){
            this.bufferWithUnzippedImg = bufferWithUnzippedImgBinarized;
        }
    }
    
//    public void carregaImFiltrades(String filtre, int param){
//        int limit = bufferWithUnzippedImg.size();
//        ArrayList<Image> llistaImatgesParam = new ArrayList<Image>();
//        for (int x=0 ; x<limit; x++){
//            if(filtre == AVE){
//                bufferWithUnzippedImgAveraged.put(x, Filtres.average1((BufferedImage)bufferWithUnzippedImg.get(x),param));
//            }else if(filtre == NEG){
//                bufferWithUnzippedImgNegative.put(x, Filtres.negative((BufferedImage)bufferWithUnzippedImg.get(x)));
//            }else if (filtre == BIN){
//                bufferWithUnzippedImgBinarized.put(x, Filtres.binarization((BufferedImage)bufferWithUnzippedImg.get(x),param));
//            }
//        }
//    }
    
    /**
     * Crea una instancia del timer que controlará el flujo de reproducción
     * @param frames
     * @param filtre 
     */
    public void reprodueixZip(int frames,String filtre, ArrayList<BufferedImage> llista){
        int ms = 1000;
        int calculat = (int) Math.round(ms/frames);
        if(llista == null){
            HashMap<Integer, Image> aux = null;
            if(filtre == AVE){
                aux = bufferWithUnzippedImgAveraged;
            }else if(filtre == NEG){
                aux = bufferWithUnzippedImgNegative;
            }else if (filtre == BIN){
                aux = bufferWithUnzippedImgBinarized;
            }else{
                aux = bufferWithUnzippedImg;
            }

            TimerEx tim = new TimerEx();
            tim.TimerExMain(aux,calculat,llista);
        }else{
            TimerEx tim = new TimerEx();
            tim.TimerExMain(null,calculat,llista);
        }
        
    }
    
    
    
    /**
     * Funcion que llama a la funcion de binarizar una imagen.
     */
    public void binaritzantImatge(int threshold){
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(BIN,threshold);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,BIN);
    }
    /**
     * Funcion que llama a la funcion de invertir una imagen.
     */
    public void inverteixNegatiuImatge() {
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(NEG,0);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,NEG);
    }

    /**
     * Funcion que llama a la funcion de aplicar el filtro de la media a una imagen.
     */
    public void averagingFilterImatge(int averaging) {
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(AVE,averaging);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,AVE);
    }
    
    
    /**
     * Metode que realitza el encoding en cas que s'hagi seleccionat per parametre. El metode
     * crida a la classe codificador i mostra per pantalla el progres.
     * @param fps
     * @param gop
     * @param ntiles
     * @param seek
     * @param quality 
     */
    public void encode(int fps, int gop, int ntiles, int seek, int quality) {
        if(!batch){this.reprodueixZip(fps, "", null);}
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        System.out.println("Entramos en encode y comenzamos a medir el tiempo.");
        Codificador encode = new Codificador(bufferWithUnzippedImg, gop, ntiles, seek, quality, output);
        time_end = System.currentTimeMillis();
        System.out.println("La tarea ha costado "+ ( time_end - time_start ) +" milisegundos");
        this.setCompressSize();
    }
    /**
     * Imprimimos por pantalla los datos de tamaño de los archivos
     * 
     */
    public void setCompressSize() {
        File jpegFile = new File("material/Cubo.zip");
        File outptFile = new File("src/resources/"+this.output);
        System.out.println("Zip inicial:  " + jpegFile.length() + " Comprimido: " + outptFile.length());
        System.out.println("El ratio de compresion es el siguiente: " + ((float)jpegFile.length()/(float)outptFile.length()));
        
    }
    
    
    
    /**
     * Metode que realitza el decoding en cas que s'hagi seleccionat per parametre. El metode crida
     * a la classe decodificador i mostra per pantalla el progres.
     * @param fps
     * @param gop
     * @param ntiles 
     */
    public void decode(int fps, int gop, int ntiles) {
        long time_start, time_end;
        time_start = System.currentTimeMillis();
        System.out.println("Entramos en decode y comenzamos a medir el tiempo.");
        Decodificador decode = new Decodificador(fps,gop,ntiles,output);
        ArrayList<BufferedImage> listacod = decode.decode();
        if (!batch){
        this.reprodueixZip(fps, "", listacod);}
        time_end = System.currentTimeMillis();
        System.out.println("La tarea ha costado "+ ( time_end - time_start ) +" milisegundos");
    }
    
    
}
