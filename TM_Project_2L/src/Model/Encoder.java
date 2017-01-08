/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Image;
import java.util.HashMap;

/**
 *
 * @author Victor
 */
public class Encoder {

    HashMap<Integer, Image> unzippedImg = new HashMap<Integer, Image>();
    public Encoder(HashMap<Integer, Image> bufferWithUnzippedImg) {
        System.out.println("Imagenes leidas");
        this.unzippedImg = bufferWithUnzippedImg;
        this.aplicaFiltres();
        this.compruebaExt();
        
    }
    /**
     * Metodo que comprueba extension de las imagenes.
     */
    private void compruebaExt() {
        System.out.println("Comprobamos si estan todas en el formato deseado. No implementado aun"); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * Metodo que aplica los filtros seleccionados por el user.
     */
    private void aplicaFiltres() {
        System.out.println("Aplicamos filtros seleccionados por el usuario. No implementado."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
