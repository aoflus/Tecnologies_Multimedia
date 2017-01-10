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
    private int id, coordDestiX, coordDestiY, x, y;
    private BufferedImage tessela;
    
    public Tesseles(BufferedImage tessela, int id) {
        this.id = id;
        this.tessela = tessela;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoordDestiX() {
        return coordDestiX;
    }

    public void setCoordDestiX(int coordDestiX) {
        this.coordDestiX = coordDestiX;
    }

    public int getCoordDestiY() {
        return coordDestiY;
    }

    public void setCoordDestiY(int coordDestiY) {
        this.coordDestiY = coordDestiY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getTessela() {
        return tessela;
    }

    public void setTessela(BufferedImage tessela) {
        this.tessela = tessela;
    }
    
}