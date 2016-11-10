/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compressio_lz.pkg77;

import java.util.HashMap;

/**
 *
 * @author vikos
 */
public class Compress {
    public static String binaria="1010101011101010010101010010101";
    /**
     * Funcion que comprime
     * @param ment
     * @param mdes
     * @return 
     */
    public static String compress(int ment, int mdes){
        String res="";
        int size = binaria.length();
        int pos = 0;
        
        for (int x = 0; x<size+1-ment-mdes; x++){
            System.out.println("iteracion: " + x);
            System.out.println("Deslizante: " + binaria.subSequence(x, (x+mdes)));
            System.out.println("Entrada" +binaria.subSequence(x+mdes,x+mdes+ment));
            
            for(int p=(mdes); p>ment-1; p--){
                System.out.println("Comparing subsequences:");
                System.out.println("des:"+binaria.subSequence(p-ment, p));
                System.out.println("ent:"+binaria.subSequence(x+mdes,x+mdes+ment));
                CharSequence comprimir=binaria.subSequence(p-ment, p);
                CharSequence entrada=binaria.subSequence(x+mdes,x+mdes+ment);

                
                for(int y=0; y<ment;y++){
                    comprimir.charAt(y);
                    entrada.charAt(y);
                   
                }
                
                
            }
        }
        
        
        return res;
    }
    
    
    /**
     * funcion que comprime y devolverá el hashmap valor:posicion para poder manejarnos luego al montar la salida comprimida.
     * @param trozo cadena del tamaño de mdes 
     * @param ment valor v entrada
     * @param mdes valor v deslizante
     * @return 
     */
    public HashMap<Integer, String>  comprimir (String trozo, String entrada ,int ment, int mdes){
        try{
        int maximcoinc=0;
        int contadorInvers = mdes - 1; //Si el tamaño de mdes es igual a 8, empezaremos a comprovar valores a partir del 7.
        int ventanaEntrada = ment - 1; //Duplicamos el valor del tamaño de la ventana de entrada para poder manejarnos.
        HashMap<Integer, String> repeticionsPosicio = new HashMap<Integer, String>();
        //hmap.put(3, "Anuj");
        System.out.println("trozo:" + trozo);
        System.out.println("entrada:" + entrada);
        int iteracion = 0;
        while (maximcoinc!=ment && contadorInvers >= 0){//Si encontramos el maximo de coincidencias posibles paramos, asi como si hemos llegado al final de la cadena.
            System.out.println("contadorInvers:" + contadorInvers);
            char bitActualDes = trozo.charAt(contadorInvers);
            char bitActualEntrada = entrada.charAt(0);
            System.out.println("-------------------------------------------------------------------------------------------------------------- it = " + iteracion);
            System.out.println("Del trozo "+ trozo + " cogemos la posicion " +"contadorInvers!!! " + contadorInvers +" que es el bit "+bitActualDes);
            System.out.println("De la  entrada "+ entrada + " cogemos la posicion " +"0!!!" + " que es el bit "+bitActualEntrada);         
            //Comprarmos el ultimo bit de la secuencia trozo con el primer bit de entrada, si coincide
            if (bitActualDes == bitActualEntrada){
                maximcoinc++;
                System.out.println("Iguals!!!");
                System.out.println("--------------------------------------------------------------------------------------------------------------");
                //Comprovamos que no hayamos guardado antes una coincidencia igual, solo nos interesará superior.
                if(!repeticionsPosicio.containsKey(maximcoinc)){ // Si no contiene esta key guardamos
                    System.out.println("No tenemos esta key " + maximcoinc + " la guardamos con la posicion:" + (mdes - contadorInvers));
                    repeticionsPosicio.put(maximcoinc,String.valueOf(mdes - contadorInvers));
                }
                int pent = 0 + 1, xdes = contadorInvers + 1; // Comenzamos a contar a partir de la posicion actual
                //Cuando encontramos la primera coincidencia provamos de seguir las dos secuencias a ver si hay mas coincidencias
                while(xdes < (mdes-1) && pent < ment && maximcoinc < ment){ //Mientras las nuevas variables no sobrepasen el valor maximo
                    System.out.println("Entramos a comparar los siguientes!!!");
                    char bitComparadoDes = trozo.charAt(xdes);
                    char bitComparadoEn = entrada.charAt(pent);
                    System.out.println("Del trozo "+ trozo + " cogemos la posicion " +" xdes!!! " + xdes +" que es el bit "+bitComparadoDes);
                    System.out.println("De la  entrada "+ entrada + " cogemos la posicion " +" pent!!! " + pent +" que es el bit "+bitComparadoEn);
                    if (bitComparadoDes == bitComparadoEn) {
                        System.out.println("Iguals2!!!");
                        maximcoinc++; //Otra coincidencia sumamos uno a la variable
                        if(!repeticionsPosicio.containsKey(maximcoinc)){ // Si no contiene esta key guardamos 2 coincidencias o las q sean y la posicion inicial
                            System.out.println("No tenemos esta key " + maximcoinc + " la guardamos con la posicion:" + (mdes - (contadorInvers)));
                            repeticionsPosicio.put(maximcoinc,String.valueOf(mdes - (contadorInvers)));
                        }
                    }else { // Si perdemos la racha reiniciamos
                        
                    }
                    xdes++; //Sumamos 1 a las variables que controlan la posicion
                    pent++;
                }
                maximcoinc = 0;
                    //if (!repeticionsPosicio.containsKey(maximcoinc)){
                    //maximcoinc++; //Sumamos uno a la variable que controla las coincidencias que hemos encontrado
                    //}
            }else{
                System.out.println("Diferents!!!");
                System.out.println("--------------------------------------------------------------------------------------------------------------");
                }
            contadorInvers--; //Avanzamos una posicion hacia la izquierda de la cadena al final del bucle
            iteracion++;
        }
        
        return repeticionsPosicio;
        }
        catch(java.lang.StringIndexOutOfBoundsException ex){
            ex.printStackTrace();
            HashMap<Integer, String> repeticionsPosicio1 = new HashMap<Integer, String>();
            return repeticionsPosicio1;
        }
        
    }
    
    
}
