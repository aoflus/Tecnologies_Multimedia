/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman_parser.Tecnologies_Multimedia;

import java.io.File;

/**
 *
 * @author Álvaro
 */
public class Main {

    
    public static void main(String[] args) {
        /*-- Aqui se ha de crear la parte de preguntar por el nombre de los archivos -- */
        //Scanner sc = new Scanner(System.in);
        //String filepath = sc.nextLine();
        
        
        /*Para facilitar las simulaciones archivos ya creados*/
        File file = new File("data.txt");
        File text = new File("text.txt");
        
        /* Test apartado A*/
        HuffmanParser hp = new HuffmanParser();
        hp.createList(file);
        hp.createTree();
        hp.createSolution();
         
        /* Test apartado B*/
        HuffmanTranslator hft = new HuffmanTranslator(file, "save.txt");
        hft.createTables();
        hft.parseFile(text);
  
        /* Test apartado C */
        RandomGenerator rdg = new RandomGenerator(100, file);
        rdg.printSequence();
    }
}
