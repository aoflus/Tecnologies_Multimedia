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
     * A mes gestionarem els params.
     * @param args 
     */
    public static void main(String[] args) {
        
        Settings settings = new Settings();
        new JCommander(settings, args); // simple one-liner
        Controlador controlador = new Controlador();
        
        
        String rutaZip = settings.getInput();
        if(rutaZip!=null){
            System.out.println(rutaZip);
            controlador.obreZip(rutaZip);
        }
        
        String frames = settings.getFps();
        if (frames != null){
            int fps = Integer.valueOf(settings.getFps());
            controlador.reprodueixZip(fps);
        }
        
        /**
         * Los parametros definidos en settings sirven para ordenar al programa que haga cosas.
         * Es decir, si se llama al programa con el arg "binarization" tendremos que aplicarlo a alguna imagen.
         */
        String binarization = settings.getBinarization(); //Recogemos de las settings el parametro ya procesado con su respectivo getter
        if(binarization != null){
            //Se comprueba que el parametro no sea null, en el caso que sea null querrá decir que no se ha puesto como argumento, es decir, la funcionalidad
            //no la tendremos que hacer, por tanto si está, llamaremos a la funcion que ejecute lo que el parametro especifica en el documento TM_ProjectePractiques
            controlador.binaritzantImatge();
        }
    }
    
    
}
