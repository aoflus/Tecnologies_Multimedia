/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.Controlador;
import Model.Utils;
import java.util.Scanner;
/**
 * Classe menu per consola per la cual gestionarem tot el funcionament de la aplicacio, 
 * si cal fer per Jframe mes endavant s'implementara.
 * @author vikos
 */

public class Menu {
    /**
     * Constructor, cridem al menu.
    */
    public boolean acaba = false;
    
    public Menu() throws Exception{
        while (!acaba){
            this.mainMenu();
        }
    }
    
    /**
     * Metode public que es cridara desde el main per a realitzar tota la llogica.
     * 
     */
    public void mainMenu() throws Exception{
        this.mostraMenu();
        int choice = 10;
        try{
            choice = Utils.escanejaInt();
        }catch(java.util.InputMismatchException ex){
            System.out.println("No es permeten més que números.");
        }
        switch (choice) {
            case 1:
                System.out.println("Has seleccionat llegir des de un fitxer ZIP arxius.");
                Controlador.opcio1();
                break;
            case 2:
                System.out.println("");
                break;
            case 3:
                System.out.println("");
                break;
            case 4:
                System.out.println("");
                break;
            case 5:
                System.out.println("Has clicat més opcions.");
                this.obreSubMenu5();
                break;
            case 6:
                System.out.println("Sortir.");
                acaba = true;
                break;
            default:
                System.out.println("Has posat un numero que no està al menu, torna-ho a provar.");
                this.mainMenu();
                // The user input an unexpected choice.
        }
    }
    /**
     * Mostrem menu per pantalla de les opcions possibles
     */
    public void mostraMenu(){
        System.out.println("===========================================================================================");
        System.out.println("|   Selecciona una opcio:                                                                 |");
        System.out.println("===========================================================================================");
        System.out.println("| Opcions:                                                                                |");
        System.out.println("|        1. Obre un zip posant-hi la ruta, es mostraran les imatges dins del mateix.      |");
        System.out.println("|        2. Desa les imatges a dins d'un altre zip.                                       |");
        System.out.println("|        3. Reprodueix les imatges podent modificar la freqüència de reproducció          |");
        System.out.println("|        4. Aplica filtres a les imatges                                                  |");
        System.out.println("|        5. Més opcions                                                                   |");
        System.out.println("|        6. Surt                                                                          |");
        System.out.println("===========================================================================================");
    }
    
    /**
     * Submenu de la opcio 5 per a mostrar més opcions
     */
    public void mostraSubMenu5(){
        System.out.println("===========================================================================================");
        System.out.println("|   Selecciona una opcio:                                                                 |");
        System.out.println("===========================================================================================");
        System.out.println("| Opcions:                                                                                |");
        System.out.println("|        1. Llegeix una imatge ficant-hi la ruta.                                         |");
        System.out.println("|        2. Surt                                                                          |");
        System.out.println("===========================================================================================");
    }
    
    /**
     * Submenu, opcions extres.
     */
    public void obreSubMenu5() throws Exception {
        this.mostraSubMenu5();
        int choice = 10;
        try{
            choice = Utils.escanejaInt();
        }catch(java.util.InputMismatchException ex){
            System.out.println("No es permeten més que números.");
        }
        switch (choice) {
            case 1:
                System.out.println("Has seleccionat llegir una imatge qualsevol.");
                Controlador.opcioSubMenu1();
                break;
            case 2:
                System.out.println("Sortir.");
                this.mainMenu();
                break;
            default:
                System.out.println("Has posat un numero que no està al menu, torna-ho a provar.");
                obreSubMenu5();
                // The user input an unexpected choice.
        }
    }
}
