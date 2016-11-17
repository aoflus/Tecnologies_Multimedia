package Model;

import Controlador.Controlador;
import Vista.Viewer;
import com.beust.jcommander.JCommander;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * In progress: 
 * Recorra imagenes controlando frecuencia. (Victor)
 *
 *
 *
 */

/**
 *
 * @author Álvaro
 */
public class Main {
    
    /**
     * Metode main que comença el programa creant un objecte menu, des d'on es llançara tota la lògica.
     * 
     * A mes gestionarem els params. Es comprova si existeix, si existeix, llançarem la lògica que correspongui
     * @param args 
     */
    public static void main(String[] args) {
        //Creem un settings i el jcommander.
        Settings settings = new Settings();
        new JCommander(settings, args); // simple one-liner
        Controlador controlador = new Controlador();
        
        //Si determina la ruta on hem de buscar el zip 
        String rutaZip = settings.getInput();
        if(rutaZip!=null){
            System.out.println(rutaZip);
            controlador.obreZip(rutaZip);
        }
        //Sortida del arxiu
        String output = settings.getOutput();
        if(output!=null){
            System.out.println("Guarda la sequencia de sortida: "+output);
            controlador.sortidaPrograma(output);
        }
        
        //Si determina el numero de frames el calculem
        String frames = settings.getFps();
        int fps;
        if (frames != null){
            fps = Integer.valueOf(frames);
            controlador.reprodueixZip(fps,"");
        }else{fps = 10;}
        
        /**
         * Los parametros definidos en settings sirven para ordenar al programa que haga cosas.
         * Es decir, si se llama al programa con el arg "binarization" tendremos que aplicarlo a alguna imagen.
         */
        //Si hi ha el parametre binaritzacio ho fem
        String binarization = settings.getBinarization(); //Recogemos de las settings el parametro ya procesado con su respectivo getter
        if(binarization != null){
            System.out.println("Fa la binaritzacio");
            //Se comprueba que el parametro no sea null, en el caso que sea null querrá decir que no se ha puesto como argumento, es decir, la funcionalidad
            //no la tendremos que hacer, por tanto si está, llamaremos a la funcion que ejecute lo que el parametro especifica en el documento TM_ProjectePractiques
            int thresh = Integer.valueOf(binarization);
            controlador.binaritzantImatge(thresh);
            controlador.reprodueixZip(fps,"bin");
        }
        
        //Si hi ha el parametre negatiu ho fem
        if (settings.getNegative()){
            System.out.println("Fa el negatiu");
            controlador.inverteixNegatiuImatge();
            controlador.reprodueixZip(fps,"neg");
        }
        
        //Si hi ha el parametre average ho fem:
        String aver = settings.getAveraging();
        if (aver != null){
            int averNumb = Integer.valueOf(settings.getAveraging());
            controlador.averagingFilterImatge(averNumb);
            controlador.reprodueixZip(fps,"ave");
        }

    }
    
    
}
