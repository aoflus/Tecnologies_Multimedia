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
import javax.swing.Timer;

/**
 * Tota accio logica es cridara dins d'aquesta classe, farem una funcio per cada opcio del menu
 * i picarem tot el necessari per a que es pugui realitzar.
 * @author vikos
 */
public class Controlador {
    public static String rutaProva = "material/Imagenes/Cubo00.png";
    public static String rutaZipProva = "material/Cubo.zip";
    public int comptador = 0;
    
    public Controlador(){
        
    }
    /**
     * Opcio 1 del main menu que realitzara ...
     */
    public static void opcio1() {
        System.out.println("Introdueix pel teclat la ruta on es troba l'arxiu zip amb les imatges.");
        String rutaDelZip = Utils.escanejaLinia();
        boolean ordena = volsOrdenar();
        ArrayList<Image> bufferWithUnzippedImg = Utils.unZipping(rutaZipProva,ordena);
        System.out.println("Introdueix pel teclat el temps de refresc entre imatges en ms.");
        int ms = Utils.escanejaInt();
        TimerEx tim = new TimerEx();
        tim.TimerExMain(bufferWithUnzippedImg,ms);
        

        
    }
    
    
    /**
     * Opcio 2 del main menu que realitzara ...
     */
    public static void opcio2(){
        
    }
    
    /**
     * Opcio 3 del main menu que realitzara ...
     */
    public static void opcio3(){
        
    }
    
    /**
     * Obre una imatge qualsevol en un Jframe
     */
    public static void opcioSubMenu1() {
        System.out.println("Introdueix la ruta de la imatge:");
        String rutaImatge=Utils.escanejaLinia();
        newViewerPassantRutaParametre(rutaProva);
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
    
}
