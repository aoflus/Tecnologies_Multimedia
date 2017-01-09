/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.GestioImatge.Marc;
import Model.GestioImatge.Tesseles;
import static Model.JPEGCompress.compressInJPEG;
import Vista.Viewer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase codificador que contindra metodes que permetran realitzar la codificacio entre frames.
 * @author Victor i Alvaro
 */
public class Codificador {

    HashMap<Integer, Image> unzippedImg = new HashMap<Integer, Image>();
    int gop = 5, ntilesw = 3, ntilesh = 3;
    ArrayList <ArrayList> listaListasGOP = new ArrayList <ArrayList>();
    ArrayList <Marc> listaGOP = new ArrayList <Marc>();
    float height, width;
    ArrayList<Marc> comprimides;
    
    
    public Codificador(HashMap<Integer, Image> bufferWithUnzippedImg, int gop, int ntiles) {
        System.out.println("Imagenes leidas");
        this.gop = gop;
        //this.ntiles= (int) Math.sqrt(ntiles);
        //this.ntiles=ntiles;
        System.out.println("GOP: " + this.gop);
        this.unzippedImg = bufferWithUnzippedImg;
        this.aplicaFiltres();
        this.ompleGOP();
        this.recorreGOP();
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
                        
                        this.listaGOP.add(new Marc((BufferedImage) unzippedImg.get(x), x));
                    };
                    listaListasGOP.add(listaGOP);
                }
                listaGOP = new ArrayList <Marc>();
            }
            //Añadimos imagen a la lista GOP.
            this.listaGOP.add(new Marc((BufferedImage) unzippedImg.get(x), x));
        }
    }
    /**
     * Metode per comprovar que s'hagi generat ve el vector de imatges
     * Cridem a subdividir en teseles i ens les guardem
     */
    private void recorreGOP(){
        System.out.println("recorreGOP");
        int x = 0;
        for (ArrayList<Marc> e:listaListasGOP){
            System.out.println((x++) + " tamany: "+ e.size());
            for (Marc img:e){
                BufferedImage image = img.getImage();
                System.out.println("real width:" + image.getWidth());
                System.out.println("real height:" + image.getHeight());
                this.width = (float)image.getWidth()/this.ntilesh;
                this.height = (float)image.getHeight()/this.ntilesh;
                System.out.println("this.width" + this.width);
                System.out.println("this.height " + this.height );
                img.setTeseles(this.subdividirImgTesseles(image));
            }  
        }
    }
    
    /**
     * Para dividir en bloques una imagen, hay que definir cuantas particiones en el eje x
     * y cuantas en el eje y se hacen, usando los valores this.ntilesh this.ntilesw
     * @param image
     * @return 
     */
    public ArrayList<Tesseles> subdividirImgTesseles(BufferedImage image){
        ArrayList<Tesseles> teseles = new ArrayList<>();
        Tesseles tesela;
        int comptador = 0;

        for(float y=0; y<Math.round(image.getHeight()); y+=this.height){
            for(float x=0; x<Math.round(image.getWidth()); x+=this.width){
                x=Math.round(x);
                y=Math.round(y);
                tesela = new Tesseles(image.getSubimage((int)x, (int) y, (int)this.width, (int)this.height), comptador);
                teseles.add(tesela);
                comptador++;
                //compressInJPEG(tesela.getTesela(),"teseles",String.valueOf(comptador)+".jpeg");

            }
        }
        System.out.println("-------------------------------------------------------");
        System.out.println("teseles:" + teseles.size());
        System.out.println("-------------------------------------------------------");
        return teseles;
    }
}
