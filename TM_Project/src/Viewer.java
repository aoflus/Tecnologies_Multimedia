
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Álvaro
 */
public class Viewer extends JFrame{
    
    private Icon icon;
    
    public Viewer(){
        super();
        
    }
    
    public void setIcon(BufferedImage image){
        icon = (Icon) image;
    }
}
