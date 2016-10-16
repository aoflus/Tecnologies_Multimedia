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
import java.util.ArrayList;

/**
 *
 * @author Álvaro
 */
public class HuffmanParser {

    private final ArrayList<Node> nodeList = new ArrayList<>();
    private final ArrayList<String> alphabet = new ArrayList<>();
    private final ValueComparator vc = new ValueComparator();
    private final ArrayList<String> solutionList = new ArrayList<>();
    private final SolutionComparator sc = new SolutionComparator();

    public void createList(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\s+");
                Node node = new Node();
                node.setSymbol(data[0]);
                alphabet.add(data[0]);
                node.setValue(Float.valueOf(data[1]));
                nodeList.add(node);
            }
            nodeList.sort(vc);
        } catch (FileNotFoundException ex) {
            System.err.println("The specified file does not exist. Please check the given path.");
        } catch (IOException ex) {
            System.err.println("There was an error reading the given document.");
        } catch (NumberFormatException ex) {
            System.err.println("There was an error reading a value.");
        }
    }
    
    public ArrayList<Node> getList(){
        return this.nodeList;
    }

    public void printList() {
        nodeList.stream().forEach((n) -> {
            System.out.println(n.getSymbol() + "||" + n.getValue());
        });
    }

    public void createTree() {
        while (nodeList.size() > 2) {
            Node n1 = nodeList.remove(nodeList.size() - 1);
            Node n2 = nodeList.remove(nodeList.size() - 1);

            /*node is the lowest value, so it goes on the left side*/
            Node newNode = new Node(n1, n2);
            newNode.setSymbol(n1.getSymbol() + n2.getSymbol());
            newNode.setValue(n1.getValue() + n2.getValue());
            nodeList.add(newNode);
            nodeList.sort(vc);
        }
    }

    public void createSolution() {
        String result = "";
        Node node;
        for (String letter : alphabet) {

            if (nodeList.get(0).getSymbol().contains(letter)) {
                /*The first element of the list is the one with the bigger chance, ergo the one that goes to the right*/
                node = nodeList.get(0);
                result = result + "1";
            } else {
                /*The last element of the list is the one with the lowest change, ergo the one that goes on the left side*/
                node = nodeList.get(1);
                result = result + "0";
            }
            
            while ((((node.getLeft() != null) || (node.getRight() != null))) && (node.getSymbol().length()) > 1) {
                if (node.getSymbol().contains(letter)) {
                    if ((node.getLeft() != null) && (node.getLeft().getSymbol().contains(letter))) {
                        node = node.getLeft();
                        result = result + "0";
                    } else if ((node.getRight() != null) && (node.getRight().getSymbol().contains(letter))) {
                        node = node.getRight();
                        result = result + "1";
                    }
                }
            }
            solutionList.add(letter + " " + result);
            result = "";
        }
    }

    public void printSolution() {
        solutionList.sort(sc);
        solutionList.stream().forEach((s) -> {
            System.out.println(s);
        });
    }
    
    public ArrayList<String> getSolutionList(){ return this.solutionList;}
    
}
