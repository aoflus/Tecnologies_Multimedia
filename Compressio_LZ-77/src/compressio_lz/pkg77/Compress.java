/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compressio_lz.pkg77;

/**
 *
 * @author vikos
 */
public class Compress {
    public static String binaria="1010101011101010010101010010101";
    
    public static String compress(int ment, int mdes){
        String res="";
        int size = binaria.length();
        int pos = 0;
        for (int x = 0; x<size+1-ment-mdes; x++){
            System.out.println("iteracion: " + x);
            System.out.println("Deslizante: " + binaria.subSequence(x, (x+mdes)));
            System.out.println("Entrada" +binaria.subSequence(x+mdes,x+mdes+ment));
            
            for(int p=(mdes); p>ment-1; p--){
                System.out.println("Comparing subsequences:");
                System.out.println("des:"+binaria.subSequence(p-ment, p));
                System.out.println("ent:"+binaria.subSequence(x+mdes,x+mdes+ment));
                CharSequence comprimir=binaria.subSequence(p-ment, p);
                CharSequence entrada=binaria.subSequence(x+mdes,x+mdes+ment);

                
                for(int y=0; y<ment;y++){
                    comprimir.charAt(y);
                    entrada.charAt(y);
                    
                    System.out.println("");
                }
                
                
            }
        }
        
        
        return res;
    }
}
