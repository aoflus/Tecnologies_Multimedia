/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman_parser.Tecnologies_Multimedia;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 *
 * @author Álvaro
 */
public class RandomGenerator {
    
    private final String [] sequence;
    private final HuffmanParser hfp;
    private final TreeMap<Float, String> intervals;
    
    public RandomGenerator(int length, File file){
        sequence = new String[length];
        hfp = new HuffmanParser();
        hfp.createList(file);
        intervals = new TreeMap();
        this.randomCriteria();
        this.generateRandomList();
    }
    
    public void randomCriteria(){
        float acum = 0.0f;
        for(Node node : hfp.getList()){
            acum = acum + node.getValue();
            intervals.put(acum, node.getSymbol());
        }
    }
    
    public void generateRandomList(){
        float value = 0.0f;
        float key = 0.0f;
        boolean out;
        for(int i = 0; i < sequence.length; i++){
            out = false;
            Set keyset = intervals.entrySet();
            Iterator keys = keyset.iterator();
            value = (float) Math.random();
            while((keys.hasNext())&&(!out)){
                Map.Entry memtry = (Map.Entry)keys.next();
                key = (float) memtry.getKey();
                if (value <= key){
                    sequence[i] = (String) intervals.get(key);
                    out = true;
                }
            }
        }
    }
    
    public void printSequence(){
        for(String s : sequence){
            System.out.print(s);
        }
        System.out.println();
    }
}
