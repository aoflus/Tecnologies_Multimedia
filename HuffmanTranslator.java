/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman_parser.Tecnologies_Multimedia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Álvaro
 */
public class HuffmanTranslator {

    private final HuffmanParser hfp;
    private final ArrayList<String> dictionary;
    private final ArrayList<String> alphabetTable;
    private final ArrayList<String> binaryTable;
    private final ArrayList<String> results;
    private final String saveFile;

    public HuffmanTranslator(File alphabet, String saveFile) {
        hfp = new HuffmanParser();
        hfp.createList(alphabet);
        hfp.createTree();
        hfp.createSolution();
        dictionary = hfp.getSolutionList();
        this.saveFile = saveFile;
        alphabetTable = new ArrayList<>();
        binaryTable = new ArrayList<>();
        results = new ArrayList<>();
    }
    
    public void createTables(){
        for (String s : dictionary){
            String[] data = s.split("\\s+");
            alphabetTable.add(data[0]);
            binaryTable.add(data[1]);
        }
    }
    
    public void saveToFile(String saveFile) throws FileNotFoundException, UnsupportedEncodingException{
        try (PrintWriter out = new PrintWriter(saveFile)) {
            results.stream().forEach((result) -> {
                out.println(result);
            });
        }
    }
    
    public void showResult(){
        results.stream().forEach((result) -> {
           System.out.println(result);
        });
    }

    public void parseFile(File file) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                for(int i = 0; i < line.length(); i++) {
                    if(alphabetTable.contains(Character.toString(line.charAt(i)))){
                        line = line.replace(Character.toString(line.charAt(i)), binaryTable.get(alphabetTable.indexOf(Character.toString(line.charAt(i)))));
                    }
                }
                results.add(line);
            }
            saveToFile(saveFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HuffmanTranslator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HuffmanTranslator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
