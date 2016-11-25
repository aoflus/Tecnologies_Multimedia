/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compressio_lz.pkg77;

import static compressio_lz.pkg77.Compressio_LZ77.ventanaDeslizante;
import static compressio_lz.pkg77.Compressio_LZ77.ventanaEntrada;
import java.util.HashMap;

/**
 *
 * @author vikos
 */
public class Compress {

    
    /**
     * funcion que comprime y devolverá el hashmap valor:posicion para poder manejarnos luego al montar la salida comprimida.
     * @param trozo cadena del tamaño de mdes 
     * @param ment valor v entrada
     * @param mdes valor v deslizante
     * @return 
     */
    public HashMap<Integer, String>  comprimir (String trozo, String entrada ,int ment, int mdes){
        int maximcoinc=0;
        int contadorInvers = mdes - 1; //Si el tamaño de mdes es igual a 8, empezaremos a comprovar valores a partir del 7.
        int ventanaEntrada = ment - 1; //Duplicamos el valor del tamaño de la ventana de entrada para poder manejarnos.
        HashMap<Integer, String> repeticionsPosicio = new HashMap<Integer, String>();
        //hmap.put(3, "Anuj");
        //System.out.println("trozo:" + trozo);
        //System.out.println("entrada:" + entrada);
        int iteracion = 0;
        while ( maximcoinc!=ment && contadorInvers >= 0){//Si encontramos el maximo de coincidencias posibles paramos, asi como si hemos llegado al final de la cadena.
            //System.out.println("contadorInvers:" + contadorInvers);
            char bitActualDes = trozo.charAt(contadorInvers);
            char bitActualEntrada = entrada.charAt(0);
            //System.out.println("-------------------------------------------------------------------------------------------------------------- it = " + iteracion);
            //System.out.println("Del trozo "+ trozo + " cogemos la posicion " +"contadorInvers!!! " + contadorInvers +" que es el bit "+bitActualDes);
            //System.out.println("De la  entrada "+ entrada + " cogemos la posicion " +"0!!!" + " que es el bit "+bitActualEntrada);         
            //Comprarmos el ultimo bit de la secuencia trozo con el primer bit de entrada, si coincide
            if (bitActualDes == bitActualEntrada){
                maximcoinc++;
                //System.out.println("Iguals!!!");
                //System.out.println("--------------------------------------------------------------------------------------------------------------");
                //Comprovamos que no hayamos guardado antes una coincidencia igual, solo nos interesará superior.
                if(!repeticionsPosicio.containsKey(maximcoinc)){ // Si no contiene esta key guardamos
                    //System.out.println("No tenemos esta key " + maximcoinc + " la guardamos con la posicion:" + (mdes - contadorInvers));
                    repeticionsPosicio.put(maximcoinc,String.valueOf(mdes - contadorInvers));
                }
                int pent = 1, xdes = contadorInvers + 1; // Comenzamos a contar a partir de la posicion actual
                //Cuando encontramos la primera coincidencia provamos de seguir las dos secuencias a ver si hay mas coincidencias
                while(xdes < mdes && pent < ment && maximcoinc < ment && maximcoinc != 0){ //Mientras las nuevas variables no sobrepasen el valor maximo
                    //System.out.println("Entramos a comparar los siguientes!!!");
                    char bitComparadoDes = trozo.charAt(xdes);
                    char bitComparadoEn = entrada.charAt(pent);
                    //System.out.println("Del trozo "+ trozo + " cogemos la posicion " +" xdes!!! " + xdes +" que es el bit "+bitComparadoDes);
                    //System.out.println("De la  entrada "+ entrada + " cogemos la posicion " +" pent!!! " + pent +" que es el bit "+bitComparadoEn);
                    if (bitComparadoDes == bitComparadoEn) {
                        //System.out.println("Iguals2!!!");
                        maximcoinc++; //Otra coincidencia sumamos uno a la variable
                        if(!repeticionsPosicio.containsKey(maximcoinc)){ // Si no contiene esta key guardamos 2 coincidencias o las q sean y la posicion inicial
                            //System.out.println("No tenemos esta key " + maximcoinc + " la guardamos con la posicion:" + (mdes - (contadorInvers)));
                            repeticionsPosicio.put(maximcoinc,String.valueOf(mdes - (contadorInvers)));
                        }
                    }else { // Si perdemos la racha reiniciamos
                        //System.out.println("Diferents1!!!");
                        //System.out.println("--------------------------------------------------------------------------------------------------------------");
                        maximcoinc = 0;
                    }
                    xdes++; //Sumamos 1 a las variables que controlan la posicion
                    pent++;
                }
                maximcoinc = 0;
                    //if (!repeticionsPosicio.containsKey(maximcoinc)){
                    //maximcoinc++; //Sumamos uno a la variable que controla las coincidencias que hemos encontrado
                    //}
            }else{
                //System.out.println("Diferents2!!!");
                //System.out.println("--------------------------------------------------------------------------------------------------------------");
            }
            //System.out.println(contadorInvers + ": Contador invers");
            if(contadorInvers==0){ //Si el contador invers va a ser mas pequeño que uno, comprobamos que todos los valores sean iguales y forzamos
                //System.out.println("fuera de los limites: " + trozo + " - " +entrada);
                //System.out.println("Insertamos en funcion: ");
                 //Si todos los valores de trozo son 0, tendremos que forzar un cero después
                int count1 = 0; //Como estamos seguros que el outofbounds solo saltará cuando sea este caso, comprovamos q todo sea ceros, sino salimos
                int count2 = 0;
                char entradaChar=entrada.charAt(0);
                char trozoChar=trozo.charAt(count1);
                //System.out.println(trozoChar == '0' );
                //System.out.println('0' != entradaChar);
                while(count1 < trozo.length() &&  trozoChar == '0' && '0' != entradaChar){ // comparamos todos los caracteres del tamaño del trozo y si todos son 0 deberiamos forzar un 0
                    
                    count1++;
                    //System.out.println("count1: " + count1);
                }
                while(count2 < trozo.length() && trozo.charAt(count2) == '1' && '1' !=entradaChar){ // comparamos todos los caracteres del tamaño del trozo y si todos son 0 deberiamos forzar un 0
                    count2++;
                    //System.out.println("count2: " + count2);
                }
                if(count1 == trozo.length()){
                    //Todo ceros forzamos un 1
                    //System.out.println("todo 0");
                    repeticionsPosicio.put(3,"2");
                }else if (count2 == trozo.length()){
                    //algun uno
                    //System.out.println("todo 1");
                    repeticionsPosicio.put(3,"1");
                }
            }
            contadorInvers--; //Avanzamos una posicion hacia la izquierda de la cadena al final del bucle
            iteracion++;

    }

    return repeticionsPosicio;
    }
    
    /**
     * Descomprime una secuencia generada LZ-77
     * @return 
     */
    public static String descomprimir(String secuencia){
        int nBitsRep = (int) (Math.log(ventanaEntrada) / Math.log(2));
        int nBitsPos = (int) (Math.log(ventanaDeslizante) / Math.log(2));
        String resultado = "";
        secuencia = secuencia.replaceAll(" ", ""); //Si hay espacios los eliminamos
        //System.out.println("Secuencia comprimida:" + secuencia + " length: " + secuencia.length());
        //System.out.println("Length: " + secuencia.length());
        int inicio = 0;
        String bin1 = secuencia.substring(inicio,ventanaDeslizante+inicio);
        resultado = resultado + bin1;
        int contadorPos = ventanaDeslizante;
        boolean control = true;
        while(control){
            String bin2 = secuencia.substring(contadorPos,contadorPos+nBitsRep+nBitsPos);
            //System.out.println("bin1:" + bin1);
            //System.out.println("bin2:" + bin2);
            String bin21 = bin2.substring(0, nBitsRep);
            String bin22 = bin2.substring(nBitsRep);
            //System.out.println("bin21:" + bin21);
            //System.out.println("bin22:" + bin22);
            int copiaValor = Integer.parseInt(bin21,2);
            int desplazaValor = Integer.parseInt(bin22,2);
            if (copiaValor == 0){ //Controlamos el caso del overflow
                copiaValor = ventanaEntrada;
            }
            if (desplazaValor == 0){
                desplazaValor = ventanaDeslizante;
            }
            //System.out.println("copiaValor:" + copiaValor);
            //System.out.println("desplazaValor:" + desplazaValor);
            String guardaCopia = "";
            int desplazaCadena = copiaValor;
            boolean entraAlWhile=true;
            if(copiaValor == 3 && desplazaValor == 2 || desplazaValor == 1 && copiaValor == 3 ){
                //System.out.println("Estas entrando aqui, enserio?");
                if (desplazaValor == 2){
                    guardaCopia = "1";
                }else if(desplazaValor == 1){
                    guardaCopia = "0";
                }
                desplazaCadena = 1; //Cambiamos el valor hardcode pq no afecta, y si no lo cambiamos no funciona ya q forzamos un caso extremo
                entraAlWhile = false;
            }
            while (copiaValor > 0 && bin2.length() >= nBitsRep + nBitsPos && entraAlWhile){
                int usa = ventanaDeslizante - desplazaValor;
                guardaCopia = guardaCopia + bin1.charAt(usa);
                //System.out.println("guardacopia: " + guardaCopia + " usa: " + usa);
                copiaValor--;
                desplazaValor--;
            }

            resultado = resultado + guardaCopia;
            inicio = inicio +  desplazaCadena;
            //System.out.println("inicio: " + inicio + " ventanaDeslizante+inicio " +ventanaDeslizante+inicio);
            bin1 = resultado.substring(inicio,ventanaDeslizante+inicio);
            //System.out.println("resultado:" + resultado);
            contadorPos = contadorPos + nBitsRep + nBitsPos;
            //System.out.println("contadorPos:" + contadorPos);
            if(secuencia.length()- contadorPos < nBitsRep+nBitsPos){
            control = false;
            resultado = resultado + secuencia.substring(contadorPos, secuencia.length());
            }
        }
        //System.out.println("resultado:" + resultado);
        return resultado;
    }

}
