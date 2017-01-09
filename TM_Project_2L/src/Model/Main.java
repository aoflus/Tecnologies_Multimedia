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
 *​
 *​    Compresion de los datos de compresion
 *​    Oos
 *​    Ascii
 *​    Utf
 *​    Int
 *​    Shrt mejor este por buffered mas rapido ram que disco
 *​    Zip file output stream a traves de zip es lo q mas comprime
 *
 *
 */

/**
 * params: -i "material/Cubo.zip" -e -o "nombre.jpeg" --fps 10 --negative --binarization 30 --averaging 7
 * 
 * Clase Main que arranca tot el programa.
 * @author Victor i Alvaro
 */
public class Main {
    
    /**
     * Metode main que comença el programa creant un objecte menu, des d'on es llançara tota la lògica.
     * 
     * A mes gestionarem els params. Es comprova si existeix, si existeix, llançarem la lògica que correspongui
     * @param args 
     */
    public static Settings settings;
    
    public static void main(String[] args) {
        //Creem un settings i el jcommander.
        settings = new Settings();
        new JCommander(settings, args); // simple one-liner
        Controlador controlador = new Controlador();
        
        //Si determina la ruta on hem de buscar el zip 
        String rutaZip = settings.getInput();
        boolean encode = settings.getEncode();
        String output = settings.getOutput();
        String frames = settings.getFps();
        int fps;
        //Asignamos fps
        if (frames != null && !encode){
            fps = Integer.valueOf(frames);
        }else{fps = 10;}
        
        //Abrimos el zip
        if(rutaZip!=null){
            System.out.println(rutaZip);
            controlador.obreZip(rutaZip);
        }
        //Codificamos
        if(encode){
            System.out.printf("Entramos en encode.");
            int gop, ntiles = 0;
            if (settings.getGOP()!=null){
                gop = Integer.valueOf(settings.getGOP());
            }else{gop = 5;}
            // De momento cogemos el tamaño de las teselas en pixeles.
            // A implementar coger valores diferentes eje x e y.
            if (settings.getnTiles()!=null){
                ntiles = Integer.valueOf(settings.getnTiles());
            }
            controlador.encode(fps,gop, ntiles);
        }
        
        //Sortida del arxiu
        if(output!=null){
            //System.out.println("Guarda la sequencia de sortida: "+output);
            controlador.sortidaPrograma(output);
        }
        

        
//        /**
//         * Filtros a parte de los del encode
//         */
//        String binarization = settings.getBinarization(); //Recogemos de las settings el parametro ya procesado con su respectivo getter
//        if(binarization != null){
//            //System.out.println("Fa la binaritzacio");
//            //Se comprueba que el parametro no sea null, en el caso que sea null querrá decir que no se ha puesto como argumento, es decir, la funcionalidad
//            //no la tendremos que hacer, por tanto si está, llamaremos a la funcion que ejecute lo que el parametro especifica en el documento TM_ProjectePractiques
//            int thresh = Integer.valueOf(binarization);
//            controlador.binaritzantImatge(thresh);
//            controlador.reprodueixZip(fps,"bin");
//        }
//        
//        //Si hi ha el parametre negatiu ho fem
//        if (settings.getNegative()){
//            //System.out.println("Fa el negatiu");
//            controlador.inverteixNegatiuImatge();
//            controlador.reprodueixZip(fps,"neg");
//        }
//        
//        //Si hi ha el parametre average ho fem:
//        String aver = settings.getAveraging();
//        if (aver != null){
//            int averNumb = Integer.valueOf(settings.getAveraging());
//            controlador.averagingFilterImatge(averNumb);
//            controlador.reprodueixZip(fps,"ave");
//        }
//
   }
    /**
     * Si realizamos encode, tenemos que preguntar antes si hay que aplicar algun filtro.
     */
    public static void filtresToApply(Controlador controlador){
        String binarization = settings.getBinarization(); //Recogemos de las settings el parametro ya procesado con su respectivo getter
        if(binarization != null){
            System.out.println("Fa la binaritzacio amb encode");
            //Se comprueba que el parametro no sea null, en el caso que sea null querrá decir que no se ha puesto como argumento, es decir, la funcionalidad
            //no la tendremos que hacer, por tanto si está, llamaremos a la funcion que ejecute lo que el parametro especifica en el documento TM_ProjectePractiques
            int thresh = Integer.valueOf(binarization);
            controlador.binaritzantImatge(thresh);
        }
        
        //Si hi ha el parametre negatiu ho fem
        if (settings.getNegative()){
            System.out.println("Fa el negatiu amb encode");
            controlador.inverteixNegatiuImatge();
        }
        
        //Si hi ha el parametre average ho fem:
        String aver = settings.getAveraging();
        if (aver != null){
            System.out.println("Fa la averaging amb encode");
            int averNumb = Integer.valueOf(settings.getAveraging());
            controlador.averagingFilterImatge(averNumb);
        }
    }
}