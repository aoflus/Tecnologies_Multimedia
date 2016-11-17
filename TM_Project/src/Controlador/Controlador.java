/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Model.Utils;
import Model.Filtres;
import Model.JPEGCompress;
import static Model.JPEGCompress.compressInJPEG;
import Model.TimerEx;
import Vista.Reproductor;
import Vista.Viewer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

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
     * Opcio 1 del main menu que realitzara ...
     */
    public void obreZip(String rutaZipProva) {
        this.bufferWithUnzippedImg = Utils.unZipping(rutaZipProva);
    }

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
     * Obre una imatge qualsevol en un Jframe
     */
    public static void opcioSubMenu1() {
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
    
    public static boolean volsOrdenar() {
        System.out.println("Si vols ordenar el resultat introdueix 1, en qualsevol altre cas no ordena pel nom.");
        try{
            int vols = Utils.escanejaInt();
            if (vols == 1){
                return true;
            }else{return false;}
        }catch(java.util.InputMismatchException ex){
            System.out.println("No has introduit un número, no s'ordenarà.");
            return false;
        }
    }
    /**
     * Funcion que binariza una imagen
     */
    public void binaritzantImatge(int threshold){
        System.out.println("Entra a binaritzantImatge");
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(BIN,threshold);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,BIN);
    }

    public void inverteixNegatiuImatge() {
        System.out.println("Entra a inverteixNegatiuImatge");
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(NEG,0);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,NEG);
    }
    
    public void averagingFilterImatge(int averaging) {
        System.out.println("Entra a averagingFilterImatge");
        //BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        this.carregaImFiltrades(AVE,averaging);
//        Viewer view = new Viewer();
//        view.mostraImatgeParam(prova,AVE);
    }

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
