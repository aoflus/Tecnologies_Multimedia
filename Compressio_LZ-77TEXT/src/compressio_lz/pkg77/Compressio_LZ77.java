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
import java.lang.*;
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
    public static int numBitsRandomGenerados;
    public static boolean error=false, dcmp1=false;
    public static boolean noRandom = false;
    public static String bitsString;
    public static boolean decompila = false;
    public static void main(String[] args) {
        // TODO code application logic here
        //Gestionamos los params
        
        gestionParametros(args);
        validateValues();
        if(!error){
            
            LogicaText logica = new LogicaText("quijote_short.txt");
            if (false){ // Si es per descomprimri no utilitzem la interficie
            System.out.println("Empieza Hamlet:");
            for(int x=4;x<=4096;x=x*2){
                ventanaEntrada=x;
                for(int xy=x;xy<=4096;xy=xy*2){
                    ventanaDeslizante=xy;
                    System.out.println("Mdes: " + ventanaDeslizante + " Ment: " + ventanaEntrada);
                    long startTime = System.nanoTime();
                    try{
                        //LogicaText logica = new LogicaText("hamlet_short.txt");
                    }catch(java.lang.StringIndexOutOfBoundsException ex){
                        System.out.println("Petó");
                        ex.printStackTrace();
                    }
                    System.out.println("Total Time: " + (System.nanoTime() - startTime) / 1000000);
        
                }
            }
            System.out.println("Empieza Quijote:");
            for(int x=4;x<=4096;x=x*2){
                ventanaEntrada=x;
                for(int xy=x;xy<=4096;xy=xy*2){
                    ventanaDeslizante=xy;
                    System.out.println("Mdes: " + ventanaDeslizante + " Ment: " + ventanaEntrada);
                    long startTime = System.nanoTime();
                    try{
                        //LogicaText logica = new LogicaText("quijote_short.txt");
                    }catch(java.lang.StringIndexOutOfBoundsException ex){
                        System.out.println("Petó");
                        ex.printStackTrace();
                    }
                    System.out.println("Total Time: " + (System.nanoTime() - startTime) / 1000000);
        
                }
            }
            
        }
        }else{System.out.println("Algun error se ha cometido.");}
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
                        System.out.println("No puedes parsear un string a un integer1");
                        error=true;
                    }
                    break;

                case "-Mdes":
                    try{
                        ventanaDeslizante = Integer.valueOf(args[i+1]);
                        System.out.println(args[i]+" "+ventanaDeslizante);
                        i++;
                    }catch(java.lang.NumberFormatException exc){
                        System.out.println("No puedes parsear un string a un integer2");
                        error = true;
                    }
                    
                case "-Rndmval":
                    try{
                        numBitsRandomGenerados = Integer.valueOf(args[i+1]);
                        System.out.println(args[i]+" "+numBitsRandomGenerados);
                        i++;
                        if(numBitsRandomGenerados<ventanaEntrada+ventanaDeslizante){
                            error = true;
                        }
                    }catch(java.lang.NumberFormatException exc){
                        //System.out.println("No puedes parsear un string a un integer3");
                    }
                break;
                case "-Norndm":
                    try{
                        bitsString = args[i+1];
                        noRandom = true;
                        System.out.println(args[i]+" "+ bitsString);
                        i++;
                        if(bitsString.length()<ventanaEntrada+ventanaDeslizante){
                            error = true;
                        }
                    }catch(java.lang.NumberFormatException exc){
                        System.out.println("No puedes parsear un string a un integer3");
                        error = true;
                    }
                break;
                    case "-Dcmp":
                    try{
                        dcmp1 = true;
                        bitsString = args[i+1];
                        String descompres = Compress.descomprimir(bitsString);
                        System.out.println(args[i]+" "+ bitsString);
                        System.out.println("Cadena descompresa:" + descompres);
                        i++;
                    }catch(java.lang.NumberFormatException exc){
                        System.out.println("No puedes parsear un string a un integer3");
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
                error = true;
            }
        }
    }
}
