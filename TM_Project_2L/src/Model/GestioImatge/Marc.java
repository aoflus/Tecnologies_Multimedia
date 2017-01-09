/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GestioImatge;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *  Classe Marc que implementara certs atributs per a la gestió de frames.
 *  @Author Victor i Alvaro
 */
public class Marc implements Comparable<Marc>{
    private BufferedImage image;
    private int id;
    private ArrayList<Tesseles> teseles;
    private ArrayList<BufferedImage> pFrames;

    public Marc(BufferedImage image, int id) {
        this.image = image;
        this.id = id;
        this.teseles = new ArrayList<>();
        this.pFrames = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public ArrayList<Tesseles> getTeseles() {
        return teseles;
    }

    public void setTeseles(ArrayList<Tesseles> teseles) {
        this.teseles = teseles;
    }

    public ArrayList<BufferedImage> getpFrames() {
        return pFrames;
    }

    public void setpFrames(ArrayList<BufferedImage> pFrames) {
        this.pFrames = pFrames;
    }
    
    public void addpFrame(BufferedImage image){
        this.pFrames.add(image);
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public int compareTo(Marc o) {
        //return Comparators.ID.compare(this, o);
        return 1;
    }
/*
    public static class Comparators {
        public static Comparator<Frame> ID = new Comparator<Frame>() {
            @Override
            public int compare(Frame o1, Frame o2) {
                return o1.getId().compareTo(o2.getId());
            }
        };
    }*/
    
}
