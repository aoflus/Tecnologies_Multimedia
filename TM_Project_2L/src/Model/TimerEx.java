/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Vista.Reproductor;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe timer, l'utilitzem per a controlar el fluxe de reproduccio de les imatges
 * ens crearem una classe viewer y assignarem un temps d'espera entre imatges.
 * @author Victor y Alvaro
 */

public class TimerEx {
    ArrayList<Image> llistaImatges = new ArrayList<Image>();
    int tamanyLlista;
    int x = -1;
    Reproductor reprod;
    TimerTask task;
    /**
     * Metode timerExMain que passada una llista ens crea el reproductor i gestiona les imatges cada X fps
     * @param hashImatgesParam
     * @param ms 
     */
    public void TimerExMain(HashMap<Integer, Image> hashImatgesParam, int ms, ArrayList<BufferedImage> llista){
        if(hashImatgesParam != null){
            int limit = hashImatgesParam.size();
            ArrayList<Image> llistaImatgesParam = new ArrayList<Image>();
            for (int x=0 ; x<limit; x++){
                llistaImatgesParam.add(hashImatgesParam.get(x));
            }
        
        this.tamanyLlista = llistaImatgesParam.size();
        this.llistaImatges = llistaImatgesParam;
        }else{
            this.tamanyLlista = llista.size();
        }
        Timer timer;
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run(){
                
                if (hashImatgesParam != null){
                    if(x == -1){
                        //view = new Viewer();
                        reprod = new Reproductor();
                        reprod.setVisible(true);
                        reprod.passaParams(timer,task);
                        reprod.mostraImatgeAlFrame(llistaImatges.get(0));
                        x++;
                    }else if (x < tamanyLlista){
                        //System.out.println("Actualitza: " + x);
                        reprod.mostraImatgeAlFrame(llistaImatges.get(x));
                    } else {
                        //System.out.println("finish");
                        reprod.dispose();
                        timer.cancel();
                        timer.purge();
                    }
                    x++;
                }else{
                    if(x == -1){
                        //view = new Viewer();
                        reprod = new Reproductor();
                        reprod.setVisible(true);
                        reprod.passaParams(timer,task);
                        reprod.mostraImatgeAlFrame(llista.get(0));
                        x++;
                    }else if (x < tamanyLlista){
                        //System.out.println("Actualitza: " + x);
                        reprod.mostraImatgeAlFrame(llista.get(x));
                    } else {
                        //System.out.println("finish");
                        reprod.dispose();
                        timer.cancel();
                        timer.purge();
                    }
                    x++;
                
                
                }
            }
            };
            // Empezamos dentro de 10ms y luego lanzamos la tarea cada 1000ms
        if(x < this.tamanyLlista){
            //System.out.println("ms:" + ms);
            timer.schedule(task, 1, (int) ms/4);
        }else{
            timer.cancel();
            timer.purge();
            task.cancel();
        }
    }

}