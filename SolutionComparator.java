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
public class SolutionComparator implements Comparator<String>{

    @Override
    public int compare(String s1, String s2) {
        if(s1.charAt(0) >= s2.charAt(0)){
            return 1;
        }
        return -1;
    }
    
}
