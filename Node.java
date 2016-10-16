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
public class Node{
    
    private Node left = null;
    private Node right = null;
    private String symbol;
    private float value;
    
    public Node(){
    }
    
    public Node(Node right, Node left){
        this.left = left;
        this.right = right;
    }
    
    public Node(Node left, Node right, String symbol, float value){
        this.left = left;
        this.right = right;
        this.symbol = symbol;
        this.value = value;
    }
    
    public Node getLeft(){return this.left;}
    public Node getRight(){return this.right;}
    public String getSymbol(){return this.symbol;}
    public float getValue(){return this.value;}
    
    public void setLeft(Node node){this.left = node;}
    public void setRight(Node node){this.right = node;}
    public void setSymbol(String symbol){this.symbol = symbol;}
    public void setValue(float value){this.value = value;}
    
    
}
