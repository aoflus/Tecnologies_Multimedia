/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.Arrays;

    /**
     * Clase que implementa els filtres descrits pels requisits.
     * @author Victor i Alvaro
     */
public class Filtres {

    
    public static BufferedImage average1(BufferedImage image, int filter){

        int finalValue = filter*filter;
        float[] filterValues = new float[finalValue];
        Arrays.fill(filterValues, 1f/(float)finalValue);

        Kernel average = new Kernel(filter,filter,filterValues);

        return (new ConvolveOp(average).filter(image, null));
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
    /**
     * Filtre negatiu
     * @param image
     * @return 
     */
    public static BufferedImage negative(BufferedImage image) {
        int nr, ng, nb;
        Color negative;
        BufferedImage negativeImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                nr = 255 - new Color(image.getRGB(i, j)).getRed();
                ng = 255 - new Color(image.getRGB(i, j)).getGreen();
                nb = 255 - new Color(image.getRGB(i, j)).getBlue();
                negative = new Color(nr, ng, nb);
                negativeImage.setRGB(i, j, negative.getRGB());
            }
        }
        return negativeImage;
    }
    /**
     * Modifica colors de pixels
     * @param alpha
     * @param red
     * @param green
     * @param blue
     * @return 
     */
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
