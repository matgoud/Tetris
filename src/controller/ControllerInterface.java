package controller;

import vue.*;
import modèle.*;
import controller.StrategyIA.*;

/**
 * Cette Classe permet d'initialiser les controllers de l'interface.
 */
public class ControllerInterface{

    private DrawPiece dPiece;
    private DrawPlateau dPlateau;

    public ControllerInterface(DrawPiece dPiece,DrawPlateau dPlateau){
        this.dPiece = dPiece;
        this.dPlateau = dPlateau;
        ControllerPiece cPiece = new ControllerPiece(dPiece);
        ControllerPlateau cPlateau = new ControllerPlateau(dPlateau,dPiece);
    }
}