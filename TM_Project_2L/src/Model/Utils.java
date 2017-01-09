/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

/**
 * A la classe Utils generarem totes les funcionalitats "externes" per a
 * realitzar les especificacions descrites a la rubrica.
 *
 * @author Victor i Alvaro
 */
public class Utils {

    /**
     * Metode que retorna la seguen linia escrita pel teclat
     *
     * @return
     */
    public static String escanejaLinia() {
        Scanner sc = new Scanner(System.in);
        String retorna = sc.nextLine();
        return retorna;
    }

    /**
     * Metode que retorna un int introduit pel teclat
     *
     * @return
     */
    public static int escanejaInt() {
        Scanner sc = new Scanner(System.in);
        int retorna = sc.nextInt();
        return retorna;
    }


    /**
     * Metode definit que permet obrir un zip,
     *
     * @param zip Accepta com a parametre un arxiu zip.
     * @throws IOException Per si no es troba l'arxiu.
     */
    public void readZIP(ZipFile zip) throws IOException {
        Enumeration<? extends ZipEntry> entries = zip.entries();

        /*While we have any entries left*/
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();

            /*Check that the entry is not a directory*/
            if (!entry.isDirectory()) {
                /*Create the stream*/
                ZipInputStream zis = (ZipInputStream) zip.getInputStream(entry);
                BufferedImage image = ImageIO.read(zis);

                /*Create the image*/
            }
        }
    }
    /**
     * Metode que guarda en un zip.
     * @param hmap
     * @param fileName
     * @param ruta 
     */
    public static void saveZip(HashMap<Integer, Image> hmap, String fileName, String ruta) {
    	byte[] buffer = new byte[1024];

    	try{

    		FileOutputStream fos = new FileOutputStream("\\MyFile.zip");
    		ZipOutputStream zos = new ZipOutputStream(fos);
    		ZipEntry ze= new ZipEntry("spy.log");
    		zos.putNextEntry(ze);
    		FileInputStream in = new FileInputStream("C:\\spy.log");

    		int len;
    		while ((len = in.read(buffer)) > 0) {
    			zos.write(buffer, 0, len);
    		}

    		in.close();
    		zos.closeEntry();

    		//remember close it
    		zos.close();

    		System.out.println("Done");

    	}catch(IOException ex){
    	   ex.printStackTrace();
    	}
    }

    /**
     * Descomprimim un zip passada una ruta com a parametre i mostrem les
     * imatges de dins.
     *
     * @param zipFile
     */
    public static HashMap<Integer, Image> unZipping(String zipFile) {
        ArrayList<Image> ordre = new ArrayList<Image>();
        HashMap<Integer, Image> hmap = new HashMap<Integer, Image>();

        try {
            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            int pos;
            int cont = 0;
            while (ze != null) {
                //System.out.println("Por cada imagen imprime el nombre:" + ze.getName());
                if(!compruebaExt(ze.getName())) {
                    System.err.println("El fichero no tiene la extensión correcta");
                    System.exit(0);
                }
                
                BufferedImage image = ImageIO.read(zis);
                try {
                    pos = Integer.valueOf(ze.getName().substring(4, 6));
                    hmap.put(pos, image);
                } catch (NumberFormatException nfe) {
                    hmap.put(cont, image);
                }
                ze = zis.getNextEntry();
                cont++;
            }
            zis.closeEntry();
            zis.close();
            return hmap;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * Metodo que comprueba extension de las imagenes.
     */
    private static boolean compruebaExt(String name) {
        String substring = name.substring(name.lastIndexOf(".")+1,name.length());
        return (substring.equalsIgnoreCase("png") || substring.equalsIgnoreCase("jpeg") || substring.equalsIgnoreCase("jpg") || substring.equalsIgnoreCase("gif") || substring.equalsIgnoreCase("bmp"));
        }
}
