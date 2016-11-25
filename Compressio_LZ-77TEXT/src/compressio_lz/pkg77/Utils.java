/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compressio_lz.pkg77;

import static compressio_lz.pkg77.Compressio_LZ77.ventanaDeslizante;
import static compressio_lz.pkg77.Compressio_LZ77.ventanaEntrada;
import static java.lang.Math.random;
import java.util.Random;
     
/**
 *
 * @author vikos
 */
public class Utils {
    /**
     * Cast two ints to binary string with space
     * @param firstVal
     * @param secondVal
     * @return 
     */
    public static String castIntsToString(int firstVal, int secondVal){
        String firstValBin = Integer.toBinaryString(firstVal);
        String secondValBin = Integer.toBinaryString(secondVal);
        int count1 = 0, count2 = 0;
        //System.out.println("Longitud secondValBin: " + secondValBin.length() + " que hay aqui : " +secondValBin);
        while(secondValBin.length()< Math.log(ventanaDeslizante) / Math.log(2)){
            secondValBin = "0" + secondValBin;
            //System.out.println(" Entra y le suma un cero vmaos " +secondValBin);
        }
        //System.out.println("Math.log(ventanaDeslizante) / Math.log(2) " + Math.log(ventanaDeslizante) / Math.log(2) + " secondValBin: " + secondValBin);
        while(firstValBin.length() < Math.log(ventanaEntrada) / Math.log(2)){
            firstValBin = "0" + firstValBin;
        }
        //System.out.println("Math.log(ventanaEntrada) / Math.log(2) " + Math.log(ventanaEntrada) / Math.log(2) + " firstValBin: " + firstValBin);
        
        if(firstValBin.length()>Math.log(ventanaEntrada) / Math.log(2)){
            firstValBin = firstValBin.substring(1);
        }//else{System.out.println("Noentraenelprimerif");}
        if(secondValBin.length()>Math.log(ventanaDeslizante) / Math.log(2)){
            //System.out.println("o entro aqui y le hago un substring y me quedo tan ancho");
            secondValBin = secondValBin.substring(1);
        }//else{System.out.println("Noentraenelsegundoif");}

        return firstValBin + " " + secondValBin;
    }
    
    /**
     * Generamos random bits
     * @param i
     * @return 
     */
    public static String generaRandoms(int i){
        String randoms = "";
        Random numAleatorio;
        numAleatorio = new Random ();
        int r = numAleatorio.nextInt();
        for (int p = 0 ; p < i; p++){
            r = numAleatorio.nextInt();
            if (r%2==0){
                randoms = randoms + "0";
            }else{
                randoms = randoms + "1";
            }
        }
        return randoms;
    }
    

}
