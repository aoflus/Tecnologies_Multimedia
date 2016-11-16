package Vista;
    /*
    * A modificar.
    */
import Model.Utils;
import static Model.Utils.negative;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.*;
import javax.swing.*;

/**
 * A Java class to demonstrate how to load an image from disk with the
 * ImageIO class. Also shows how to display the image by creating an
 * ImageIcon, placing that icon an a JLabel, and placing that label on
 * a JFrame.
 * 
 * @author Victor
 */

public class Viewer {
 
    public Viewer(){
        this.createFrame();
    }
    public String filename;
    JFrame frame;
    JLabel jLabel;
   // public Viewer() {
  //      SwingUtilities.invokeLater(new Runnable() {
 //           public void run() {
//                JFrame editorFrame = new JFrame("Image Demo");
//                editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//                BufferedImage image = null;
//                try{
//                    image = ImageIO.read(new File(filename));
//                }catch (Exception e){
//                    e.printStackTrace();
//                    System.exit(1);
//                }
//                ImageIcon imageIcon = new ImageIcon(image);
//                JLabel jLabel = new JLabel();
//                jLabel.setIcon(imageIcon);
//                editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

//                editorFrame.pack();
//                editorFrame.setLocationRelativeTo(null);
//                editorFrame.setVisible(true);
//            }
//        });
//    }
    /**
     * Setter per al filename.
     * @param filename 
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
    /**
     * Creem una instancia d'un frame.
     */
    public void createFrame(){
        System.out.println("Creem el frame:");
        frame = new JFrame("Exemple reproduccio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800, 600);
        jLabel = new JLabel();
    }
    
    public static void recorreBufferedImages(ArrayList<Image> buffer){
        int xAxis = 1;
        int yAxis = 1;
        
        JFrame editorFrame = new JFrame("Image Demo");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        BufferedImage image = null;
        for(int i = 0; i < buffer.size(); i++){
            try{
                image = (BufferedImage) buffer.get(i);
            }catch (Exception e){
                System.out.println("Te a lloc l'excepcio.");
                e.printStackTrace();
                System.exit(1);
            }
            System.out.println("i: " + i);
            ImageIcon imageIcon = new ImageIcon(image);
            JLabel jLabel = new JLabel();
            jLabel.setIcon(imageIcon);
            editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

            editorFrame.pack();
            editorFrame.setLocationRelativeTo(null);
            editorFrame.setVisible(true);
        }
    }
    
    /**
     * Mostrem una imatge passada per parametre, aquesta funcio l'executarà quasi unicament el tiemr.
     * @param image 
     */
    public void mostraImatgeParam(Image image, String filtre){
        BufferedImage imageBuffered = (BufferedImage) image;
         System.out.println("mostraImatgeParam: filtre: " + filtre);
        try{
            switch (filtre) {
            case "bin":
                System.out.println("binarization switch");
                imageBuffered = Utils.binarization((BufferedImage) image, 30);  
            break;
            case "neg":
                System.out.println("negative switch");
                imageBuffered =  negative((BufferedImage) image);  
            break;
            case "ave":
                System.out.println("average switch");
            break;
        }
            
        }catch (Exception e){
            System.out.println("Te lloc l'excepcio.");
            e.printStackTrace();
            System.exit(1);
        }
        ImageIcon imageIcon = new ImageIcon(imageBuffered);
        jLabel.setIcon(imageIcon);
        int width = imageIcon.getIconWidth();
        int heigth = imageIcon.getIconHeight();
        frame.getContentPane().add(jLabel, BorderLayout.CENTER);
        //frame.pack();
        frame.setLocationRelativeTo(null);
        jLabel.updateUI();
    }
    
    public void closeAll(){
        frame.removeAll();
        frame.setEnabled(false);
    }
    
}