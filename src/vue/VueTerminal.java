package vue;
import modèle.*;
import java.io.IOException;

/**
 * Cette Classe est la vue terminal elle permet de visualiser le modèle depuis le terminal
 * 
*/
public class VueTerminal implements Ecouteur{
    private PlateauPuzzle p;
    private int sizeX;
    private int sizeY;
    

    public VueTerminal(PlateauPuzzle p){
        this.p = p;
        sizeX = p.getTailleX();
        sizeY = p.getTailleY();
        p.ajoutEcouteur(this);
        draw();
    }

    /**
     * Cette méthode permet de changer la coordonée du pointeur ne fonctionne que sur linux.
     * @param y Ordonnée du pointeur.
     * @param x Abscisse du pointeur.
     */
    private void gotoyx(int y, int x)
    {
        System.out.printf("%c[%d;%df",0x1B,y,x);
    }
    
    /**
     * Cette méthode permet de mettre à jour la vue après un changement du modèle.
     */
    public void MiseAJour(Object source){
        draw();
    }
    /**
     * Cette méthode permet de vider le terminal.
     */
    private void clr(){
        System.out.printf("\033c");
    }
    /**
     * Cette méthode permet de dessiner toute la vue.
     * CAD : le plateau, le score, la piece selectionnée.
     * elle ne prend pas d'arguments et ne fait qu'afficher du contenu.
     */
    private void draw(){
        int[][] plateau = p.getMatricePlateau();
        clr();
        drawNumbers();
        drawPlateau(plateau);
        PiecePuzzle pi = p.getSelectedPiece();
        if(pi != null){
            drawSidePiece(pi);
        }
        gotoyx(3,sizeX*2 + 5);
        System.out.println("score : " + p.resultat());
        if(p.isOver()){
            gotoyx(p.getTailleY()+3,0);
            System.out.printf("\033[38;2;0;255;0mPartie Terminée!\033[0m\n");
            System.out.printf("appuyez sur entrer pour quitter\n");
        }
    }
    /**
     * Cette méthode permet de dessiner le plateau de jeu actuel.
     */
    private void drawPlateau(int[][] plateau){
        for(int y = 0; y < sizeY;y++){
            for(int x = 0; x < sizeX;x++){
                gotoyx(2+y,3+x*2);
                if (plateau[y][x] > 0){
                    int col = plateau[y][x];
                    int b = col - (256 * (col/256));
                    col /= 256;
                    int g = col - (256 * (col/256));
                    col /= 256;
                    int r = col;

                    System.out.printf("\033[38;2;%d;%d;%dm #\033[0m",r,g,b);
                }else{
                    System.out.printf(" .");
                }
            }
        }
    }
    /**
     * Cette méthode permet de dessiner la piece selectionnée ainsi que le score.
     */
    private void drawSidePiece(PiecePuzzle p){
        int cX = 10+sizeX*2;
        int cY = sizeY/2 - p.getRotatedTailleY()/2;
        for(int y = 0; y < p.getRotatedTailleY();y++){
            for(int x = 0; x < p.getRotatedTailleX();x++){
                gotoyx(cY + y,cX + 2*x);
                if(p.getPieceXY(x,y)){
                    int col = p.getCouleur();
                    int b = col - (256 * (col/256));
                    col /= 256;
                    int g = col - (256 * (col/256));
                    col /= 256;
                    int r = col;
                    System.out.printf("\033[38;2;%d;%d;%dm #\033[0m",r,g,b);
                }else{
                     System.out.printf(" .");
                }
            }
        }
        Position pos = p.centerPiece();
        gotoyx(cY + pos.getY(),cX + 2*pos.getX());
        if(p.getPieceXY(pos.getX(),pos.getX())){
            System.out.printf("\033[38;2;%d;%d;%dm X\033[0m",255,255,255);
        }else{
            System.out.printf("\033[38;2;%d;%d;%dm +\033[0m",255,255,255);
        }
        gotoyx(cY-2,cX-4);
        System.out.println("pièce selectionnée:");

    }

    /**
     * Cette méthode permet de dessiner les nombres autour du plateau de jeu dans l'interface terminal.
     */
    private void drawNumbers(){
        for(int y = 0; y < sizeY;y++){
            gotoyx(2+y,0);
            System.out.printf("%d",y);
        }
        for(int x = 0; x < sizeX;x++){
            gotoyx(0,4+x*2);
            System.out.printf("%d",x);
        }
    }
}