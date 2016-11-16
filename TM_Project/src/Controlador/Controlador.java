/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Model.Utils;
import Model.TimerEx;
import Vista.Reproductor;
import Vista.Viewer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public Controlador(){}
    /**
     * Opcio 1 del main menu que realitzara ...
     */
    public void obreZip(String rutaZipProva) {
        this.bufferWithUnzippedImg = Utils.unZipping(rutaZipProva);
    }
    
    
    public void reprodueixZip(int frames){
        int ms = 1000;
        System.out.println("frmaes:" + frames);
        int calculat = (int) Math.round(ms/frames);
        TimerEx tim = new TimerEx();
        tim.TimerExMain(this.bufferWithUnzippedImg,calculat);
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
        BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        Viewer view = new Viewer();
        view.mostraImatgeParam(prova,BIN);
    }

    public void inverteixNegatiuImatge() {
        System.out.println("Entra a inverteixNegatiuImatge");
        BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        Viewer view = new Viewer();
        view.mostraImatgeParam(prova,NEG);
    }
    
    public void averagingFilterImatge(int averaging) {
        System.out.println("Entra a averagingFilterImatge");
        BufferedImage prova = (BufferedImage) bufferWithUnzippedImg.get(0);
        Viewer view = new Viewer();
        view.mostraImatgeParam(prova,AVE);
    }
    
}
