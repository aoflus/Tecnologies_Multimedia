/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;

/**
 * A la classe Utils generarem totes les funcionalitats "externes" per a realitzar
 * les especificacions descrites a la rubrica.
 * @author vikos
 */
public class Utils {
    private static int MAX_IMG_COUNT = 100;
    /**
     * Metode que retorna la seguen linia escrita pel teclat
     * @return 
     */
    public static String escanejaLinia(){
        Scanner sc = new Scanner(System.in);
        String retorna = sc.nextLine();
        return retorna;
    }
    
    /**
     * Metode que retorna un int introduit pel teclat
     * @return 
     */
    public static int escanejaInt(){
        Scanner sc = new Scanner(System.in);
        int retorna = sc.nextInt();
        return retorna;
    }
    
    /**
     * Metode definit que permet obrir un zip,
     * @param zip Accepta com a parametre un arxiu zip.
     * @throws IOException Per si no es troba l'arxiu.
     */
    public void readZIP(ZipFile zip) throws IOException{
        Enumeration<? extends ZipEntry> entries = zip.entries();
        
        /*While we have any entries left*/
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            
            /*Check that the entry is not a directory*/
            if (!entry.isDirectory()){
                /*Create the stream*/
                ZipInputStream zis = (ZipInputStream) zip.getInputStream(entry);
                BufferedImage image = ImageIO.read(zis);
                
                /*Create the image*/
                
            }
        }
    }
    
    /**
     * Descomprimim un zip passada una ruta com a parametre i mostrem les imatges de dins.
     * @param zipFile 
     */
    public static ArrayList<Image> unZipping(String zipFile, boolean ordena) {
        ArrayList<Image> ordre = new ArrayList<Image>();
        try{
            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            
            
            while(ze!=null){
                if(!ordena){
                    System.out.println("Por cada imagen imprime el nombre:" + ze.getName());
                    BufferedImage image = ImageIO.read(zis);
                    ordre.add(image); 
                    ze = zis.getNextEntry();
                }else{
                    //Aqui ordenarem.
                }
            }
            zis.closeEntry();
            zis.close();
            return ordre;
    	}catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
}