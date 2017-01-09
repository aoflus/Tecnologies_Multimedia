/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.GestioImatge;

import java.awt.image.BufferedImage;

/**
 * Classe Tessela que implementara atributs i metodes per a la gestió de les tesseles.
 * Author Victor i Alvaro
 */
public class Tesseles {
    private int idOriginal;
    private int xCoordDest;
    private int yCoordDest;
    private BufferedImage tesela;
    private int x;
    private int y;
    
    public Tesseles(BufferedImage tesela, int idOriginal) {
        this.idOriginal = idOriginal;
        this.tesela = tesela;
    }

    public int getIdOriginal() {
        return idOriginal;
    }


    public int getxCoordDest() {
        return xCoordDest;
    }


    public int getyCoordDest() {
        return yCoordDest;
    }

    public void setIdOriginal(int idOriginal) {
        this.idOriginal = idOriginal;
    }
    
    public BufferedImage getTesela() {
        return tesela;
    }

    public void setxCoordDest(int xCoordDest) {
        this.xCoordDest = xCoordDest;
    }

    public void setyCoordDest(int yCoordDest) {
        this.yCoordDest = yCoordDest;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
