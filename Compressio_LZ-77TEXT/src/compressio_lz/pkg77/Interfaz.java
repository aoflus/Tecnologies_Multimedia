/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compressio_lz.pkg77;
import static compressio_lz.pkg77.Compressio_LZ77.bitsString;
import static compressio_lz.pkg77.Compressio_LZ77.noRandom;
import static compressio_lz.pkg77.Compressio_LZ77.numBitsRandomGenerados;
import static compressio_lz.pkg77.Compressio_LZ77.ventanaDeslizante;
import static compressio_lz.pkg77.Compressio_LZ77.ventanaEntrada;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author vikos
 */
public class Interfaz {
    public String comprimido;
    Compress comprime = new Compress();

    public String cadena1 = "11011100101001111010100010001";
    public Interfaz(){
        if(noRandom){ // Si no es random passem com parametre la cadena 
            cadena1 = bitsString;
            System.out.println("Cadena: " + cadena1);
            this.run();
        }else{ //Si es random hacemos una cadena de randoms
            cadena1 = Utils.generaRandoms(numBitsRandomGenerados);
            System.out.println("Cadena: " + cadena1);
            this.run();
        }
        
    }
    
    /**
     * Ejecuta todo
     */
           
    public void run(){
        //Al comprimir se le pasa un valor de bits igual al tamaño de la ventana deslizante.
        this.recorreComprime();
        comprime.descomprimir(comprimido);
    }
    
    /**
     * Devuelve el codigo aleatorio generado por la utils comprimido
     * @return 
     */
    public void recorreComprime(){
        String cadenaBits;
        int acum = 0;
        cadenaBits = cadena1;
        
        String inicioCadenaComprimida = cadenaBits.substring(0,ventanaDeslizante); //El inicio de la cadena van a ser los primeros ventanaDeslizante bits
        //System.out.println("Tamaño cadena: " + cadenaBits);
        boolean fin = false;
        int inicio = 0;
        while(!fin && (inicio + ventanaEntrada + ventanaDeslizante) < cadenaBits.length()){
            //System.out.println("Cadena:" + cadenaBits);
            String paraSplit = troceaCadena(cadenaBits,inicio);
            //Separamos la cadena en dos partes
            String[] parts = paraSplit.split(" ");
            String deslizante = parts[0];
            String entrada = parts[1];
            //System.out.println("deslizante: " + deslizante + " entrada: " + entrada);
            HashMap<Integer, String> resultados = comprime.comprimir(deslizante,entrada,ventanaEntrada, ventanaDeslizante);
            //System.out.println("Imprimimos los resultados");
            String valuePair = imprimeCoincidencias(resultados);
            String[] partsPair = valuePair.split(" ");
            String coinc = partsPair[0];
            String distancia = partsPair[1]; 
            //System.out.println("coinc: " + coinc + " distancia: " + distancia);
            String binary1 = Utils.castIntsToString(Integer.parseInt(coinc),Integer.parseInt( distancia));
            //System.out.println("binary: " + binary1);
            inicio = inicio + Integer.parseInt(coinc);
            inicioCadenaComprimida = inicioCadenaComprimida + " " + binary1;
            //System.out.println(inicio + ventanaEntrada + ventanaDeslizante - cadenaBits.length());
            //System.out.println(ventanaEntrada + ventanaDeslizante);
            //if(inicio + ventanaEntrada + ventanaDeslizante - cadenaBits.length() < ventanaEntrada + ventanaDeslizante ){
            //    fin = true;
            //}
            //System.out.println(inicio);
            acum = acum + Integer.parseInt(coinc);
        }

        inicioCadenaComprimida = inicioCadenaComprimida + " " + cadenaBits.substring(acum + ventanaDeslizante);
        comprimido = inicioCadenaComprimida;
        System.out.println("Cadena comprimida: " + inicioCadenaComprimida );
        
    }
    /**
     * Funcion trocea cadena que va a recibir una longitud de bits igual al mdes i ment juntos, para poderlo dividir directamente en dos partes
     * @param binario
     * @return 
     */
    public String troceaCadena(String binario, int inicio){
        String bin1, bin2;
        bin1 = binario.substring(inicio,ventanaDeslizante+inicio);
        bin2 = binario.substring(ventanaDeslizante+inicio,ventanaDeslizante+ventanaEntrada+inicio);
        return bin1 + " " + bin2;
    }
    
    /**
     * Resultados del hashmap por pantalla, nos devuelve el ultimo que seguro que es el mejor
     * @param resultados 
     */
    public String imprimeCoincidencias(HashMap<Integer,String> resultados){
        Iterator it = resultados.entrySet().iterator();
        String valuePair = "";
        while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        valuePair = pair.getKey() + " " + pair.getValue();
        //System.out.println(pair.getKey() + " = " + pair.getValue());
        it.remove(); // avoids a ConcurrentModificationException    
    }
    return valuePair;
    }
}

