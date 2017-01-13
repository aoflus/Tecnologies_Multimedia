/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.GestioImatge.Marc;
import Model.GestioImatge.Tesseles;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
import static Model.JPEGCompress.convertToJPG;

/**
 * A la classe Utils generarem totes les funcionalitats "externes" per a
 * realitzar les especificacions descrites a la rubrica.
 *
 * @author Victor i Alvaro
 */
public class Utils {


    public static int compressSize = 0;
    public static int descompressSize = 0;
    
     /**
     * Metode que retorna la seguen linia escrita pel teclat
     *
     * @returnla la linia escrita per teclat.
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
            compressSize = (int) ze.getSize();

            int pos;
            int cont = 0;
            
            while (ze != null) {
                //System.out.println("Por cada imagen imprime el nombre:" + ze.getName());
                if(!compruebaExt(ze.getName())) {
                    System.err.println("El fichero no tiene la extensión correcta");
                    convertToJPG(ze.getName(), "jpg");
                    //System.exit(0);
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
    
    public int count;
    /**
     * Metodo que pasada una lista de imagenes, y un nombre de archivo, genera un zip con las imagenes.
     * @param imgList
     * @param outName 
     */
    public void saveZip(ArrayList<Marc> imgList, String outName){
        ZipOutputStream outputStreamZip = null;
        BufferedOutputStream outputStreamBuffered; 
        FileOutputStream outputStreamFile;
        try {
            outputStreamFile = new FileOutputStream(outName, true);
            outputStreamBuffered = new BufferedOutputStream (outputStreamFile);
            outputStreamZip = new ZipOutputStream(outputStreamBuffered);
            
            for (Marc image : imgList) {
               ZipEntry entry = new ZipEntry("outImage"+image.getId()+".jpg");
               try {
                   outputStreamZip.putNextEntry(entry);
                   ImageIO.write(image.getImage(), "jpg", outputStreamZip);
               } catch (IOException ex) {
                    System.out.println("ERROR: There has been a problem while writing the image to the output file");
                    try{
                    outputStreamZip.flush();
                    outputStreamZip.close();
            } catch (Exception e) {
                System.out.println("S'ha produit un error tancant la connexio");
            }
               }
               this.count++;
            }           
            try{
                outputStreamZip.flush();
                outputStreamZip.close();
            } catch (Exception e) {
                System.out.println("S'ha produit un error tancant la connexio");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            try{
                outputStreamZip.flush();
                outputStreamZip.close();
            } catch (Exception e) {
                System.out.println("S'ha produit un error tancant la connexio");
            }
        }
    }
    
    /**
     * Creamos una carpeta zip con el contenido del src.
     * @param srcFolder
     * @param destZipFile 
     */
    public void crearCarpetaZIP(String srcFolder, String destZipFile) {
        try {
            ZipOutputStream zip = null;
            FileOutputStream fileWriter = null;

            fileWriter = new FileOutputStream(destZipFile);
            
            zip = new ZipOutputStream(fileWriter);
            
            anyadirDirectorio("", srcFolder, zip);
            
            zip.flush();
            zip.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo auxiliar para guardar un fichero en un zip.
     * @param path
     * @param srcFile
     * @param zip 
     */
    public void anyadirFichero(String path, String srcFile, ZipOutputStream zip) {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            anyadirDirectorio(path, srcFile, zip);
        } else {
            FileInputStream in = null;
            try {
                byte[] buf = new byte[1024];
                int len;
                in = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Codificador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Metodo auxiliar para guardar un directorio en un zip.
     * @param path
     * @param srcFolder
     * @param zip 
     */
    public void anyadirDirectorio(String path, String srcFolder, ZipOutputStream zip) {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                anyadirFichero(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                anyadirFichero(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }

    /**
     * Metodo auxiliar para borrar el directorio de muletilla que se crea en el proceso.
     * @param dir
     * @return 
     */
    public boolean borrarDirectorio(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String children1 : children) {
                boolean success = borrarDirectorio(new File(dir, children1));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    /**
     * Este metodo crea las coordenadas para poder decodificar las imagenes, por tanto guarda las relaciones
     * entre los I frames y las teselas de los P frames.
     */
    public void crearTxTCoordenadas(ArrayList<Tesseles> tesselesAcum) {
        BufferedWriter bw = null;
        try {
            String name = "src/resources/Compressed/coords.txt";
            bw = new BufferedWriter(new FileWriter(name));
            for (Tesseles t : tesselesAcum) {
                bw.write(t.getId() + " " + t.getCoordDestiX() + " " + t.getCoordDestiY() + "\n");
            }
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            System.err.println("Excepcion IO" + ex);
        }
    }

}
