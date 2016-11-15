/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Vista.Reproductor;
import Vista.Viewer;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe timer, l'utilitzem per a controlar el fluxe de reproduccio de les imatges
 * ens crearem una classe viewer y assignarem un temps d'espera entre imatges.
 * @author vikos
 */

public class TimerEx {
    ArrayList<Image> llistaImatges = new ArrayList<Image>();
    Viewer view;
    int tamanyLlista;
    int x = -1;
    Reproductor reprod;
    TimerTask task;
    public void TimerExMain(ArrayList<Image> llistaImatgesParam, int ms){
        this.tamanyLlista = llistaImatgesParam.size();
        this.llistaImatges = llistaImatgesParam;
        Timer timer;
        timer = new Timer();

        task = new TimerTask() {
            @Override
            public void run(){
                if(x == -1){
                    //view = new Viewer();
                    reprod = new Reproductor();
                    reprod.setVisible(true);
                    reprod.passaParams(timer,task);
                }else if (x < tamanyLlista){
                    System.out.println("Actualitza: " + x);
                    reprod.mostraImatgeAlFrame(llistaImatges.get(x));
                    //view.mostraImatgeParam(llistaImatges.get(x));
                } else {
                    System.out.println("finish");
                    timer.cancel();
                    timer.purge();
                }
                x++;
            }
            };
            // Empezamos dentro de 10ms y luego lanzamos la tarea cada 1000ms
        if(x < this.tamanyLlista){
            timer.schedule(task, 10, ms);
        }else{
            timer.cancel();
            timer.purge();
            task.cancel();
        }
    }

}