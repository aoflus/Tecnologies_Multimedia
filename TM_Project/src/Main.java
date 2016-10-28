
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.






 */

/**
 *
 * @author Álvaro
 */
public class Main {
    
    private final Viewer viewer = new Viewer();
    
    public void readZIP(ZipFile zip) throws IOException{
        Enumeration<? extends ZipEntry> entries = zip.entries();
        
        /*While we have any entries left*/
        while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();
            
            /*Check that the entry is not a directory*/
            if (!entry.isDirectory()){
                /*Create the stream*/
                ZipInputStream zis = (ZipInputStream) zip.getInputStream(entry);
                BufferedImage image = ImageIO.read(zis);
                
                /*Create the image*/
                
            }
        }
    }
    
    public void showImage(BufferedImage image){
        
    }
    
}
