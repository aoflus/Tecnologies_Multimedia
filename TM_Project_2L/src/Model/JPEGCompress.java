/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

    /**
     * Classe que comprimeix en JPEG donada una certa qualitat com a parametre.
     * @author Victor i Alvaro
     */

public class JPEGCompress {
    /**
     * Funcio que comprimeix en JPEG i ho fica dins de la ruta "name".
     * @param image
     * @param name
     * @param outputIName
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void compressInJPEG(BufferedImage image,String name,String outputIName) throws FileNotFoundException, IOException{
        
        //File imageFile = new File("Desert.jpg");
        File compressedImageFile = new File(name+"/"+outputIName);
        //InputStream inputStream = new FileInputStream(imageFile);
        OutputStream outputStream = new FileOutputStream(compressedImageFile);
        float imageQuality = 0.3f;
        BufferedImage bufferedImage = image;
        //Get image writers
        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");
        if (!imageWriters.hasNext()){
            throw new IllegalStateException("Writers Not Found!!");
        }
        ImageWriter imageWriter = (ImageWriter) imageWriters.next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        //Set the compress quality metrics
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(imageQuality);

        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
        //inputStream.close();
        outputStream.close();
        imageOutputStream.close();
        imageWriter.dispose();
  }
}
