package Vista;
    /*
    * A modificar.
    */
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
    public Viewer(String filename) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame editorFrame = new JFrame("Image Demo");
                editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                BufferedImage image = null;
                try{
                    image = ImageIO.read(new File(filename));
                }catch (Exception e){
                    e.printStackTrace();
                    System.exit(1);
                }
                ImageIcon imageIcon = new ImageIcon(image);
                JLabel jLabel = new JLabel();
                jLabel.setIcon(imageIcon);
                editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

                editorFrame.pack();
                editorFrame.setLocationRelativeTo(null);
                editorFrame.setVisible(true);
            }
        });
    }
    
    public static void recorreBufferedImages(ArrayList<Image> buffer){
        int xAxis = 1;
        int yAxis = 1;
        JFrame editorFrame = new JFrame("Image Demo");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel jLabel = new JLabel();
        for(int i = 0; i < buffer.size(); i++){
            ImageIcon imageIcon = new ImageIcon(buffer.get(i));
            jLabel.setIcon(imageIcon);
            editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
            editorFrame.pack();
            editorFrame.setLocationRelativeTo(null);
            editorFrame.setVisible(true);
        }
    }
    
}