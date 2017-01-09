/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.JPEGCompress.compressInJPEG;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase codificador que contindra metodes que permetran realitzar la codificacio entre frames.
 * @author Victor i Alvaro
 */
public class Codificador {

    HashMap<Integer, Image> unzippedImg = new HashMap<Integer, Image>();
    int gop = 5, ntiles = 0;
    ArrayList <ArrayList> listaListasGOP = new ArrayList <ArrayList>();
    ArrayList <BufferedImage> listaGOP = new ArrayList <BufferedImage>();
    
    public Codificador(HashMap<Integer, Image> bufferWithUnzippedImg, int gop, int ntiles) {
        System.out.println("Imagenes leidas");
        this.gop = gop;
        this.ntiles=ntiles;
        System.out.println("GOP: " + this.gop);
        System.out.println("ntiles: " + this.ntiles);
        this.unzippedImg = bufferWithUnzippedImg;
        this.aplicaFiltres();
        this.compruebaExt();
        this.ompleGOP();
        this.recorreGOP();
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
    
    /**
     * Recorremos un numero de imagenes definido por el parametro GOP,
     * Y a partir de esta secuencia de imagenes comprimimos, la primera
     * sera la de referencia.
     */
    private void ompleGOP(){ // Hacemos una lista por cada tira de imagenes
        System.out.println("ompleGOP");
        for(int x = 0 ; x<unzippedImg.size();x++){
            if(x % this.gop == 0 || x + 1 >= unzippedImg.size()){  //Empezamos lista
                //Si gop es 5, entra en el if en el 0, en el 5, en el 10...
                if(!listaGOP.isEmpty()){
                    if(x + 1 >= unzippedImg.size()){
                        this.listaGOP.add((BufferedImage) unzippedImg.get(x));
                    };
                    listaListasGOP.add(listaGOP);
                }
                listaGOP = new ArrayList <BufferedImage>();
            }
            //Añadimos imagen a la lista GOP.
            this.listaGOP.add((BufferedImage) unzippedImg.get(x));
        }
    }
    /**
     * Metode per comprovar que s'hagi generat ve el vector de imatges
     * Cridem a subdividir en teseles
     */
    private void recorreGOP(){
        System.out.println("recorreGOP");
        int x = 0;
        for (ArrayList e:listaListasGOP){
            System.out.println((x++) + " tamany: "+ e.size());
            this.subdividirImgTesseles(e);
            
        }
    }
    
    /**
     * Subdividim en tesseles les imatges.
     * @param listaGOPs 
     */
    private void subdividirImgTesseles(ArrayList <BufferedImage> listaGOPs){
        System.out.println("subdividirImgTesseles");
        
        
    }
    
}
