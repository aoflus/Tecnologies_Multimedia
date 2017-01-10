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
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

/**
 * Clase codificador que contindra metodes que permetran realitzar la codificacio entre frames.
 * @author Victor i Alvaro
 */
public class Codificador {

    HashMap<Integer, Image> unzippedImg = new HashMap<Integer, Image>();
    int gop = 5, ntilesw=1, ntilesh = 1, seek, ntiles, quality;
    ArrayList <ArrayList> listaListasGOP = new ArrayList <ArrayList>();
    ArrayList <Marc> listaGOP = new ArrayList <Marc>();
    int height, width;
    ArrayList<Marc> comprimides = new ArrayList<Marc>();
    ArrayList<Tesseles> tesselesAcum = new ArrayList<Tesseles>();
    
    public Codificador(HashMap<Integer, Image> bufferWithUnzippedImg, int gop, String ntilesw, String ntilesh, int seek, int quality) {
        System.out.println("Imagenes leidas");
        this.gop = gop;
        //this.ntiles= (int) Math.sqrt(ntiles);
        //this.ntiles=ntiles;
        System.out.println("GOP: " + this.gop);
        this.ntilesh = Integer.valueOf(ntilesh);
        this.ntilesw = Integer.valueOf(ntilesw);
        this.seek = seek;
        this.quality = quality;
        System.out.println("ntilesh: " + this.ntilesh);
        System.out.println("ntilesw: " + this.ntilesw);
        this.unzippedImg = bufferWithUnzippedImg;
        this.aplicaFiltres();
        this.ompleGOP();
        this.listaListasGOP=this.recorreGOP();

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
                    Utils zipp= new Utils();
                    zipp.saveZip(listaGOP,"nouZip.zip");
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
    public ArrayList<ArrayList> recorreGOP(){
        System.out.println("recorreGOP");
        int x = 0;
        for (ArrayList<Marc> e:listaListasGOP){
            System.out.println((x++) + " tamany: "+ e.size());
            int comptador = 1;
            for (Marc img:e){
                BufferedImage image = img.getImage();
                this.width = image.getWidth()/this.ntilesh;
                this.height = image.getHeight()/this.ntilesh;
                new File("teseles"+comptador).mkdirs();
                img.setTesseles(this.subdividirImgTesseles(image));
                if(comptador < e.size()){
                    //img.setTesseles(findCompatibleBlock(e,img,e.get(comptador).getImage()));
                    //Marc resultant = new Marc(setPFramesColor(img.getTeseles(), e.get(comptador).getImage()),5);
                    //comprimides.add(resultant);
                    comptador ++;
                }else{
                    comprimides.add(img);
                }
                for(Tesseles t : img.getTesseles()) this.tesselesAcum.add(t);
            }
            
        }
        return listaListasGOP;
    }
    
    /**
     * Para dividir en bloques una imagen, hay que definir cuantas particiones en el eje x
     * y cuantas en el eje y se hacen, usando los valores this.ntilesh this.ntilesw
     * @param image
     * @return 
     */
    public ArrayList<Tesseles> subdividirImgTesseles(BufferedImage image){
        ArrayList<Tesseles> teseles = new ArrayList<>();
        Tesseles t;
        int count = 0;
        for(int y=0; y<image.getHeight(); y+=this.height){
            for(int x=0; x<image.getWidth(); x+=this.width){
                t = new Tesseles(image.getSubimage(x, y, this.width, this.height), count);
                teseles.add(t);
                count++;
            }
        }
        return teseles;
    }
    
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    //ntiles = numero de veces que parte la longitud y altura = 10
    //ntiles width = 
   
}
