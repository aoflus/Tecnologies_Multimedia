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
    public StringBuffer descomprimidoBin;
    Compress comprime = new Compress();
    public StringBuffer toCompress;
    public String cadena1 = "11011100101001111010100010001";
    public Interfaz(StringBuffer toCompress){
        this.toCompress = toCompress;
        this.run();
    }
    
    /**
     * Ejecuta todo
     */
           
    public void run(){
        //Al comprimir se le pasa un valor de bits igual al tamaño de la ventana deslizante.
        this.recorreComprime();
        String resultado = comprime.descomprimir(comprimido);
        descomprimidoBin = new StringBuffer(resultado);
    }

    public StringBuffer getDescomprimidoBin() {
        return descomprimidoBin;
    }
    
    /**
     * Devuelve el codigo aleatorio generado por la utils comprimido
     * @return 
     */
    public void recorreComprime(){
        String cadenaBits;
        StringBuffer str = toCompress;
        
        int acum = 0;
        cadenaBits = str.toString();
        System.out.println("cadenaBitsSize: "+ cadenaBits.length() +" CadenaBits: "+cadenaBits);
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
            String coinc = "";
            String distancia = ""; 
            String[] partsPair = valuePair.split(" ");
            coinc = partsPair[0];
            distancia = partsPair[1]; 
            //System.out.println("coinc: " + coinc + " distancia: " + distancia);
            String binary1 = Utils.castIntsToString(Integer.parseInt(coinc),Integer.parseInt( distancia));
            //System.out.println("binary: " + binary1);
            if(Integer.parseInt(coinc) == 3 && Integer.parseInt(distancia) == 2 || Integer.parseInt( distancia) == 1 && Integer.parseInt(coinc) == 3 ){
                //System.out.println("Eh, chanchullos");
                if (Integer.parseInt(coinc) == 3){
                    coinc = "1";
                }
            }
            inicio = inicio + Integer.parseInt(coinc);
            inicioCadenaComprimida = inicioCadenaComprimida + " " + binary1;
            
            //System.out.println("Comprimiendo: " + inicioCadenaComprimida);
            //System.out.println("Acum: " + acum);
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
        System.out.println("Cadena comprimida length: " +inicioCadenaComprimida.length()+" Cadena: " + inicioCadenaComprimida);
        
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
        //System.out.println(resultados.isEmpty());
        while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        valuePair = pair.getKey() + " " + pair.getValue();
        //System.out.println(pair.getKey() + " = " + pair.getValue());
        it.remove(); // avoids a ConcurrentModificationException    
    }
    return valuePair;
    }
}

