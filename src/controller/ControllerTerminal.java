package controller;
import modèle.*;
import java.util.Scanner;

/**
 * Cette Classe est le controleur terminal elle permet de modifier le modèle depuis le terminal
 * 
*/
public class ControllerTerminal extends Thread{
    private PlateauPuzzle p;
    private Scanner scan;
    private String title = "";
    private boolean tourneDroite = true;
    public ControllerTerminal(PlateauPuzzle p){
        scan = new Scanner(System.in);  // Create a Scanner object
        this.p = p;
        this.start();
    }
    /**
     * Cette méthode permet de changer la coordonée du pointeur ne fonctionne que sur linux
     * @param y Ordonnée du pointeur
     * @param x Abscisse du pointeur
     */
    private void gotoyx(int y, int x)
    {
        System.out.printf("%c[%d;%df",0x1B,y,x);
    }

    private void goToTitlePos(){
        gotoyx(p.getTailleY()+3,0);
    }
    private void goToCorrectPos(){
        gotoyx(p.getTailleY()+4,0);
    }
    private void goToCorrectPosEntry(){
        gotoyx(p.getTailleY()+5,0);
    }
    private void goToCorrectPosOutput(){
        gotoyx(p.getTailleY()+6,0);
    }
    private void clr(){
        System.out.printf("\033c");
    }
    private void clearQuery(){
        clr();
        p.changement();
        goToTitlePos();
        System.out.println(title);
        goToCorrectPos();
    }
    private Position getSelectedCoords(){
        String out = "";
        boolean done = false;
        int X = 0;
        int Y = 0;
        while(!done){
            clearQuery();
            System.out.println("Veuillez rentrer une coordonée horizontale entre 0 et " + (p.getTailleX()-1));
            goToCorrectPosEntry();
            done = true;
            try{
                out = scan.nextLine();
                X = Integer.parseInt(out);
            }catch(Exception e){
                done = false;
                if(p.isOver()){//si la partie se termine alors qu'on cherche l'entrée utilisateur on s'en moque
                    done = true;
                }
            }
            if(X < 0 || X >= p.getTailleX())done = false;
            if(p.isOver()){//si la partie se termine alors qu'on cherche l'entrée utilisateur on s'en moque
                done = true;
            }
        }
        done = false;
        if(p.isOver()){
            done = true;
        }
        while(!done){
            clearQuery();
            System.out.println("Veuillez rentrer une coordonée verticale entre 0 et " + (p.getTailleY()-1));
            goToCorrectPosEntry();
            done = true;
            try{
                out = scan.nextLine();
                Y = Integer.parseInt(out);
            }catch(Exception e){
                done = false;
                if(p.isOver()){
                    done = true;
                }
            }
            if(Y < 0 || Y >= p.getTailleY())done = false;
            if(p.isOver()){//si la partie se termine alors qu'on cherche l'entrée utilisateur on s'en moque
                done = true;
            }
        }
        goToCorrectPosOutput();
        return new Position(X,Y);
    }
    private void wait(int milliseconds ){
        try{
            this.sleep(milliseconds);
        }catch (Exception e) {
            System.out.println(e);
        }

    }
    private void Action(){
        boolean done = false;
        String out = "";
        while(!done){
            clearQuery();
            goToCorrectPosEntry();
            out = scan.nextLine();
            if(p.isOver()){
                done = true;
            }else{
                if(out.equals("S") || out.equals("s")){
                    done = true;
                    if(p.getSelectedPiece() == null){
                        title = "Rentrez les coordonées de la case que vous voulez selectionner";
                        Position pos = getSelectedCoords();
                        p.setSelectedPiece(p.getPieceEnPos(pos));
                        p.removePiece(p.getSelectedPiece());
                    }else{
                        goToCorrectPos();
                        clearQuery();
                        System.out.printf("\033[38;2;255;0;0mImpossible, une pièce est déjà selectionnée!\033[0m");
                        wait(2000);
                    }
                }
                if(out.equals("P") || out.equals("p")){
                    done = true;
                    if(p.getSelectedPiece() == null){
                        goToCorrectPos();
                        clearQuery();
                        System.out.printf("\033[38;2;255;0;0mImpossible, pas de piece selectionnée!\033[0m");
                        wait(2000);
                    }else{
                        boolean done2 = false;
                        while(!done2){
                            title = "Rentrez les coordonées de la case que vous aller afficher";
                            Position pos = getSelectedCoords();
                            PiecePuzzle pi = p.getSelectedPiece();
                            if(pi != null){
                                pi.setPos(pos);
                                if(p.ajouterPiece(pi) && pi !=null){
                                    done2 = true;
                                    p.unSelectPiece();
                                }else{

                                    title = "\033[38;2;255;0;0mImpossible la piece est en collision avec une autre ou sort de l'espace de jeu!\033[0m";
                                    clearQuery();
                                    wait(2000);

                                }
                            }else{
                                title = "\033[38;2;255;255;0mla pièce a été déselectionnée par un autre controleur retour au menu...\033[0m";
                                clearQuery();
                                wait(2000);
                                done2 = true;
                            }
                        }
                    }
                }
                if(out.equals("R") || out.equals("r")){
                    done = true;
                    if(p.getSelectedPiece() == null){
                        goToCorrectPos();
                        clearQuery();
                        System.out.printf("\033[38;2;255;0;0mImpossible, pas de piece selectionnée!\033[0m");
                        wait(2000);
                    }else{
                        PiecePuzzle pi = p.getSelectedPiece();
                        if(tourneDroite)pi.rotationDroite();
                        else{pi.rotationGauche();}
                    }
                }
                if(out.equals("Rg") || out.equals("rg") || out.equals("RG") || out.equals("rG")){
                    done = true;
                    if(p.getSelectedPiece() == null){
                        goToCorrectPos();
                        clearQuery();
                        System.out.printf("\033[38;2;255;0;0mImpossible, pas de piece selectionnée!\033[0m");
                        wait(2000);
                    }else{
                        PiecePuzzle pi = p.getSelectedPiece();
                        pi.rotationGauche();
                        tourneDroite = false;
                    }
                }
                if(out.equals("Rd") || out.equals("rd") || out.equals("RD") || out.equals("rD")){
                    done = true;
                    if(p.getSelectedPiece() == null){
                        goToCorrectPos();
                        clearQuery();
                        System.out.printf("\033[38;2;255;0;0mImpossible, pas de piece selectionnée!\033[0m");
                        wait(2000);
                    }else{
                        PiecePuzzle pi = p.getSelectedPiece();
                        pi.rotationDroite();
                        tourneDroite = true;
                    }
                }
                if(out.equals("t") || out.equals("T")){
                    done = true;
                    if(p.getSelectedPiece() != null){
                        goToCorrectPos();
                        clearQuery();
                        System.out.printf("\033[38;2;255;0;0mImpossible une pièce est selectionnée!\033[0m");
                        wait(2000);
                    }else{
                        p.endGame();
                    }
                }
            }
        }
    }
    /**
     * Cette méthode permet de lancer le controleur terminal, cela est effectué par le biais d'un thread.
     * De cette manière le controleur est totalement isolé du reste du programme.
     * Le controleur va demander des entrées utilisateur il est donc nécéssaire qu'il ne bloque pas le processus principal.
     */
    public void run(){  
        while(!p.isOver()){
            
            title = "Entrez votre prochaine action (S)electionner (P)lacer (R)otation (T)erminer la partie";
            Action();

        }
        
    }  
}