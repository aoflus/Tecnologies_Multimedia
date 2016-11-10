/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package compressio_lz.pkg77;
import java.lang.Math;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 *
 * @author vikos
 */
public class Compressio_LZ77 {

    /**
     * @param args the command line arguments
     */
    public static int ventanaEntrada;
    public static int ventanaDeslizante;
    public static boolean error=true;
    
    public static void main(String[] args) {
        // TODO code application logic here
        gestionParametros(args);
        //Compress.compress(ventanaEntrada, ventanaDeslizante);
        //Al comprimir se le pasa un valor de bits igual al tamaño de la ventana deslizante.
        Compress comprime = new Compress();
        HashMap<Integer, String> resultados = comprime.comprimir("01010110","0101",ventanaEntrada, ventanaDeslizante);
        System.out.println("Imprimimos los resultados");
        
        Iterator it = resultados.entrySet().iterator();
        while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        System.out.println(pair.getKey() + " = " + pair.getValue());
        it.remove(); // avoids a ConcurrentModificationException
    }
    }
    
    /**
     * Metodo para gestión de parametros, aceptamos "Ment" y "Mdes"
     * @param args 
     */
    public static void gestionParametros(String[] args) {
        
        for (int i = 0 ; i < args.length;i++) {
            switch (args[i]) {
                case "-Ment":
                    try{
                        ventanaEntrada = Integer.valueOf(args[i+1]);
                        System.out.println(args[i]+" "+ventanaEntrada);
                        i++;
                    }catch(java.lang.NumberFormatException exc){
                        System.out.println("No puedes parsear un string a un integer");
                        error=true;
                    }
                    break;

                case "-Mdes":
                    try{
                        ventanaDeslizante = Integer.valueOf(args[i+1]);
                        System.out.println(args[i]+" "+ventanaDeslizante);
                        i++;
                    }catch(java.lang.NumberFormatException exc){
                        System.out.println("No puedes parsear un string a un integer");
                        error = true;
                    }
                break;
                default:
                    System.out.println("No se conoce ese parametro");
                break;
 
            }
        }
    }
    /**
     * Metodo que gestiona que los valores introducidos sean validos
     */
    public static void validateValues(){
        //Comprobamos si son potencias de dos
        if(Math.log(ventanaEntrada) / Math.log(2) == (int)Math.log(ventanaEntrada) / Math.log(2) && Math.log(ventanaDeslizante) / Math.log(2) == (int)Math.log(ventanaDeslizante) / Math.log(2)) {
            if(ventanaEntrada <= ventanaDeslizante){
                error = false;
            }
        }
        if(error){
            int defaultEntrada = 4;
            int defaultDeslizante = 8;
            ventanaEntrada = defaultEntrada;
            ventanaDeslizante = defaultDeslizante;
        }
    }
}
