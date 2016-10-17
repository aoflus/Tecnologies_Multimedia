/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman_parser.Tecnologies_Multimedia;

/**
 *
 * @author Álvaro
 */
public class PerformanceCalc {
    
    private double stdPerfRatio;
    private int roundStdPerfRatio;
    private double huffmanPerfRatio;
    private double entropy;
    private final HuffmanParser hfp;
    private final String[] sequence;

    public PerformanceCalc(String[] sequence, HuffmanParser hfp){
        stdPerfRatio = 0;
        huffmanPerfRatio = 0;
        this.hfp = hfp;
        this.sequence = sequence;
        this.calcStdRatio();
        this.calcHuffmanRatio();
        this.calcEntrophy();
    }
    
    private void calcStdRatio(){
        stdPerfRatio = Math.log(hfp.getSolutionList().size())/Math.log(2);
        roundStdPerfRatio = (int)Math.ceil(stdPerfRatio);
    }
    
    private void calcHuffmanRatio(){
        for(String c: sequence){
            huffmanPerfRatio = huffmanPerfRatio + hfp.getSolutionList().get(c).length();
        }
    }
    
    private void calcEntrophy(){
        hfp.getList().stream().forEach((node) -> {
            entropy = entropy + Math.log(node.getValue())/Math.log(2) * node.getValue();
        });
        entropy = 0-entropy;
    }
    
    public void getPerformanceRatio(){
        System.out.println("The standard performance for this system is: " + roundStdPerfRatio + " bits/character for the given sequence.");
        System.out.println("The performance for this system after applying Huffman is " + huffmanPerfRatio/sequence.length + " bits/character for the given sequence");
        System.out.println("The entropy for this system is " + entropy);
        System.out.println("the performance ratio is " + roundStdPerfRatio/entropy + ":1.");
    }
}
