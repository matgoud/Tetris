package controller;

import modèle.*;
import modèle.Strategy.*;
import controller.StrategyIA.*;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;

/**
* Cette Classe permet de lancer le menu pour choisir la difficulté.
*/
public class Menu{

    private PlateauPuzzle p;
    private JFrame menu;
    private boolean setDifucut;
    private JButton d1;
    private JButton d2;
    private JButton d3;

    //Permet d'initialiser une partie en choisisant une difficulté ou un joueur robot.
    public Menu(){

        this.p = new PlateauPuzzle(20,20);
        this.menu = new JFrame("menu");
        this.setDifucut = false;
        
        Client client = new Client();
        client.setStrategy(new GenRandAll());

        this.d1 = new JButton("Facile");
        d1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                client.creerInitPlateau(p,10,0);
                setDifucut(true);
            }
        });

        this.d2 = new JButton("Moyen");
        d2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                client.creerInitPlateau(p,10,1);
                setDifucut(true);
            }
        });

        this.d3 = new JButton("Difficile");
        d3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                client.creerInitPlateau(p,10,2);
                setDifucut(true);
            }
        });

        menu.setSize(500,500);
        menu.setLayout(new GridLayout(3,1));
        menu.add(d1);
        menu.add(d2);
        menu.add(d3);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
        menu.setResizable(false);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
    * Cette méthode permet de récupérer le fenêtre du menu.
    */
    public JFrame getMenu(){
        return menu;
    }

    /**
    * Cette méthode permet de récupérer le PlateauPuzzle créé par le menu
    */
    public PlateauPuzzle getP(){
        return p;
    }

    /**
    * Cette méthode permet de récupérer un boolean qui nous indique la difficulté qui a été choisie.
    */
    public boolean getSetDificult(){
        return setDifucut;
    }

    /**
    * Cette méthode permet de changer la valeur de setDificut.
    */
    public void setDifucut(boolean b){
        setDifucut = b;
    }

}
