/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman_parser.Tecnologies_Multimedia;

import java.util.Comparator;

/**
 *
 * @author Álvaro
 */
public class ValueComparator implements Comparator<Node>{

    
    @Override
    public int compare(Node n1, Node n2) {
        if (n1.getValue() < n2.getValue())
            return 1;
        return -1;
    }
        
    
    
}
