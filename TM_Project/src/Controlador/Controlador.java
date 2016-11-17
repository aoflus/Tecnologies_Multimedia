/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Model.Utils;
import Model.Filtres;
import static Model.JPEGCompress.compressInJPEG;
import Model.TimerEx;
import Vista.Viewer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tota accio logica es cridara dins d'aquesta classe, farem una funcio per cada opcio del menu
 * i picarem tot el necessari per a que es pugui realitzar.
 * @author vikos
 */
public class Controlador {
    public static final String AVE = "ave";
    public static final String NEG = "neg";
    public static final String BIN = "bin";
    public int comptador = 0;
    public HashMap<Integer, Image> bufferWithUnzippedImg = null;
    public HashMap<Integer, Image> bufferWithUnzippedImgNegative = new HashMap<Integer, Image>(); //Guardarem les sortides
    public HashMap<Integer, Image> bufferWithUnzippedImgBinarized = new HashMap<Integer, Image>();
    public HashMap<Integer, Image> bufferWithUnzippedImgAveraged = new HashMap<Integer, Image>();
    public Controlador(){}
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
                bufferWithUnzippedImgAveraged.put(x, Filtres.average((BufferedImage)bufferWithUnzippedImg.get(x),param));
            }else if(filtre == NEG){
                bufferWithUnzippedImgNegative.put(x, Filtres.negative((BufferedImage)bufferWithUnzippedImg.get(x)));
            }else if (filtre == BIN){
                bufferWithUnzippedImgBinarized.put(x, Filtres.binarization((BufferedImage)bufferWithUnzippedImg.get(x),param));
            }
        }
    }
    
    /**
     * Crea una instancia del timer que controlará el flujo de reproducción
     * @param frames
     * @param filtre 
     */
    public void reprodueixZip(int frames,String filtre){
        HashMap<Integer, Image> aux = null;
        int ms = 1000;
        System.out.println("frames:" + frames);
        int calculat = (int) Math.round(ms/frames);
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
        tim.TimerExMain(aux,calculat);
    }
    
    
    /**
     * Obre una imatge qualsevol en un Jframe.
     */
    public static void obreJFrameAmbImatge() {
        System.out.println("Introdueix la ruta de la imatge:");
        String rutaImatge=Utils.escanejaLinia();
        newViewerPassantRutaParametre(" ");
    }
    
    /**
     * Crea una instancia del JFrame per una imatge.
     * @param ruta String amb la ruta per obrir el fitxer en questió.
     * @throws Exception 
     */
    public static void newViewerPassantRutaParametre(String ruta) {
        Viewer viewer = new Viewer();
    }
    
    
    /**
     * Funcion que llama a la funcion de binarizar una imagen.
     */
    public void binaritzantImatge(int threshold){
        System.out.println("Entra a binaritzantImatge");
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(BIN,threshold);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,BIN);
    }
    /**
     * Funcion que llama a la funcion de invertir una imagen.
     */
    public void inverteixNegatiuImatge() {
        System.out.println("Entra a inverteixNegatiuImatge");
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(NEG,0);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,NEG);
    }

    /**
     * Funcion que llama a la funcion de aplicar el filtro de la media a una imagen.
     */
    public void averagingFilterImatge(int averaging) {
        System.out.println("Entra a averagingFilterImatge");
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(AVE,averaging);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,AVE);
    }
    /** 
     * Funció que realitza la sortida del programa, crea un directori amb nom fix prova i parseja totes les imatges a JPEG a dins de prova.
     * 
     * @param output 
     */
    public void sortidaPrograma(String output) {
        System.out.println("guardarem a output:" + "prova");
        String name = "prova";
        try {
            File theDir = new File(name);
            theDir.mkdir();
            boolean success=true;
            if (!success) {
                System.out.println("No s'ha pogut crear el directori");
            }
            String[] parts = output.split("\\.");
            for(int x = 0 ; x<bufferWithUnzippedImg.size();x++){
                String outputINom = parts[0] + x + "." +parts[1];
                compressInJPEG((BufferedImage) bufferWithUnzippedImg.get(x),name,outputINom);
            }
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
