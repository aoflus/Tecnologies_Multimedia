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
 * @author vikos
 */
public class Utils {

    private static int MAX_IMG_COUNT = 100;

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

    public static void saveZip(HashMap<Integer, Image> hmap, String fileName, String ruta) {
        File f = new File(ruta + fileName + ".zip");
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f));
            for (int key : hmap.keySet()) {
                Image im = hmap.get(key);
                ImageIO.write((BufferedImage) im, key + "jpeg", zos);
            }
        } catch (IOException ex) {
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
                System.out.println("Por cada imagen imprime el nombre:" + ze.getName());
                BufferedImage image = negative(ImageIO.read(zis));
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

    public static BufferedImage binarization(BufferedImage image, int threshold) {
        int r, g, b, bw;
        BufferedImage bwImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                r = new Color(image.getRGB(i, j)).getRed();
                g = new Color(image.getRGB(i, j)).getGreen();
                b = new Color(image.getRGB(i, j)).getBlue();
                if ((r > threshold) && (b > threshold) && (g > threshold)) {
                    bw = 255;
                } else {
                    bw = 0;
                }
                //String bwColor = String.format("#%06X", (0xFFFFFF & bw));
                int bwColor = coloring(new Color(image.getRGB(i, j)).getAlpha(), bw, bw, bw);
                //bwImage.setRGB(j, j, Integer.parseInt(bwColor));
                bwImage.setRGB(i, j, bwColor);
            }
        }

        return bwImage;
    }

    public static BufferedImage negative(BufferedImage image) {
        int nr, ng, nb;
        Color negative;
        BufferedImage negativeImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                /*int rgba = image.getRGB(i, j);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue());
                negativeImage.setRGB(i,, j, rgba);*/
                nr = 255 - new Color(image.getRGB(i, j)).getRed();
                ng = 255 - new Color(image.getRGB(i, j)).getGreen();
                nb = 255 - new Color(image.getRGB(i, j)).getBlue();
                negative = new Color(nr, ng, nb);
                negativeImage.setRGB(i, j, negative.getRGB());
            }
        }
        return negativeImage;
    }

    public static int coloring(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;
    }

}
