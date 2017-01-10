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
public class Marc{
    private BufferedImage image;
    private int id;
    private ArrayList<Tesseles> tesseles;
    private ArrayList<BufferedImage> pFrames;

    public Marc(BufferedImage image, int id) {
        this.image = image;
        this.id = id;
        this.tesseles = new ArrayList<>();
        this.pFrames = new ArrayList<>();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Tesseles> getTesseles() {
        return tesseles;
    }

    public void setTesseles(ArrayList<Tesseles> tesseles) {
        this.tesseles = tesseles;
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
}
