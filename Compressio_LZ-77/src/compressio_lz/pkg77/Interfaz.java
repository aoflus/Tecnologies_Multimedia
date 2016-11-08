/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compressio_lz.pkg77;
import java.util.Scanner;
/**
 *
 * @author vikos
 */
public class Interfaz {
 
    
    /**
     * Permitimios introducir al usuario la cadena
     * @return 
     */
    public String gestionaEntrada(){
    String binaria="";
    Scanner scan= new Scanner(System.in);

    //For string

    binaria= scan.nextLine();

    System.out.println(binaria);
    return binaria;
}
}

