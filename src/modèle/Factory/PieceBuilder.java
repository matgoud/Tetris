package modèle.Factory;
import modèle.*;

import java.util.Random;

public class PieceBuilder {
    Position pos;
    int tailleX;
    int tailleY;
    boolean[][] forme;
    int rotation;
    int couleur;


    /**
     * PieceBuilder est une usine a Piece afin de facilement pouvoir créer des pièces pour le projet.
     */
    public PieceBuilder(){
        pos = new Position(0, 0);
        tailleX = 0;
        tailleY = 0;
        forme = null;
        rotation = 0;
        Random r = new Random();
        couleur = r.nextInt(0xffffff-1) + 1;
    }


    /**
     * aleatoire permet de générer une piece aléatoire, la forme sera définie parmi celles implémentées.
     * @param r une instance de la classe Random pour pouvoir utiliser des nombre aléatoires.
     * @param XposMax précise la coordonée X maximum.
     * @param YposMax précise la coordonée Y maximum.
     * @param XposMin précise la coordonée X minimum.
     * @param YposMin précise la coordonée Y minimum.
     * @param XtailleMax précise la taille X maximum.
     * @param YtailleMax précise la taille Y maximum.
     * @param XtailleMin précise la taille X minimum.
     * @param YtailleMin précise la taille Y minimum.
    */
    public PieceBuilder aleatoire(Random r,int XposMax,int YposMax,int XposMin,int YposMin,int XtailleMax,int YtailleMax,int XtailleMin,int YtailleMin){
        pos = new Position(r.nextInt(XposMax-XposMin)+XposMin, r.nextInt(YposMax-YposMin)+YposMin);
        tailleX = r.nextInt(XtailleMax-XtailleMin)+XtailleMin;
        tailleY = r.nextInt(YtailleMax-YtailleMin)+YtailleMin;
        rotation = r.nextInt(4);
        int forme = r.nextInt(5);
        if(forme == 0)avecFormeT();
        if(forme == 1)avecFormeL();
        if(forme == 2)avecFormeBloc();
        if(forme == 3)avecFormeU();
        if(forme == 4)avecFormeS();
        return this;
    }
    /**
     * aleatoire permet de générer une piece aléatoire, la forme sera définie parmi celles implémentées.
     * les paramètres non précisés ne sont pas modifiés.
     * @param r une instance de la classe Random pour pouvoir utiliser des nombre aléatoires.
     * @param XtailleMax précise la taille X maximum.
     * @param YtailleMax précise la taille Y maximum.
     * @param XtailleMin précise la taille X minimum.
     * @param YtailleMin précise la taille Y minimum.
    */
    public PieceBuilder aleatoire(Random r,int XtailleMax,int YtailleMax,int XtailleMin,int YtailleMin){
        tailleX = r.nextInt(XtailleMax-XtailleMin)+XtailleMin;
        tailleY = r.nextInt(YtailleMax-YtailleMin)+YtailleMin;
        rotation = r.nextInt(4);
        int forme = r.nextInt(5);
        if(forme == 0)avecFormeT();
        if(forme == 1)avecFormeL();
        if(forme == 2)avecFormeBloc();
        if(forme == 3)avecFormeU();
        if(forme == 4)avecFormeS();
        return this;
    }
    
    /**
     * aleatoire permet de générer une piece aléatoire, la forme sera définie parmi celle implémentées.
     * les paramètres non précisés ne sont pas modifiés.
     * @param r une instance de la classe Random pour pouvoir utiliser des nombre aléatoires.
    */
    public PieceBuilder aleatoire(Random r){
        rotation = r.nextInt(4);
        int forme = r.nextInt(5);
        if(forme == 0)avecFormeT();
        if(forme == 1)avecFormeL();
        if(forme == 2)avecFormeBloc();
        if(forme ==3)avecFormeU();
        if(forme == 4)avecFormeS();
        return this;
    }
    /**
     * avecPosition permet de préciser la position de la pièce.
     * @param X position X
     * @param Y position Y
     */
    public PieceBuilder avecPosition(int X,int Y){
        pos = new Position(X, Y);
        return this;
    }

    /**
     * avecTaille permet de préciser la taille de la pièce.
     * @param X taille X
     * @param Y taille Y
     */
    public PieceBuilder avecTaille(int X,int Y){
        tailleX = X;
        tailleY = Y;

        return this;
    }

    public PieceBuilder avecCouleur(int col){
        if(col > 0xffffff || col < 0)throw new IllegalArgumentException("la couleur doit être entre 0 et " + 0xffffff);
        couleur = col;

        return this;
    }

    /**
     * avecRotation permet de spécifier la rotation.
     * @param R est la rotation modulée a 4.
     */
    public PieceBuilder avecRotation(int R){
        rotation = R % 4;
        return this;
    }

    /**
     * avecFormeT permet de remplir la pièce avec la forme d'un T.
     */
    public PieceBuilder avecFormeT(){
        if(tailleX < 1 || tailleY < 1)throw new IllegalArgumentException("la taille de la forme n'est pas initialisée,il faut definir la taille avant de préciser la forme!");
        forme = new boolean[tailleX][tailleY];
        for(int j =0;j<tailleY;j++){
            forme[tailleX/2][j] = true;
        }

        for(int i =0;i<tailleX;i++){
            forme[i][0] = true;
        }
        return this;
    }

    /**
     * avecFormeL permet de remplir la pièce avec la forme d'un L.
     */
    public PieceBuilder avecFormeL(){
        if(tailleX < 1 || tailleY < 1)throw new IllegalArgumentException("la taille de la forme n'est pas initialisée,il faut definir la taille avant de préciser la forme!");
        forme = new boolean[tailleX][tailleY];
        for(int j =0;j<tailleY;j++){
            forme[0][j] = true;
        }

        for(int i =0;i<tailleX;i++){
            forme[i][0] = true;
        }
        return this;
    }
    /**                                                        
     * avecFormeS permet de remplir la piece avec la forme d'un S :    |____
     *                                                                      |                                                             
    */
    public PieceBuilder avecFormeS(){
        if(tailleX < 1 || tailleY < 1)throw new IllegalArgumentException("la taille de la forme n'est pas initialisée,il faut definir la taille avant de préciser la forme!");
        forme = new boolean[tailleX][tailleY];
        for(int j=0;j<tailleY;j++){
            if(j<tailleY/2){
              forme[0][j]=true;  
            }
            else{
                forme[tailleX-1][j]=true;
            }
        }
        for(int i=0;i<tailleX;i++){
            forme[i][tailleY/2]=true;
        }
        return this;

    }
    /**
     * avecFormeU permet de remplir la piece avec la forme d'un U.
     */
    public PieceBuilder avecFormeU(){
        if(tailleX < 1 || tailleY < 1)throw new IllegalArgumentException("la taille de la forme n'est pas initialisée,il faut definir la taille avant de préciser la forme!");
        forme = new boolean[tailleX][tailleY];
        for(int j =0 ;j<tailleY;j++){
            forme[0][j]=true;
            forme[tailleX-1][j]=true;
        }
        for(int i =0;i<tailleX;i++){
            forme[i][tailleY-1]=true;
        }
        return this;
    }
    /**
     * avecFormeBloc permet de remplir la piece avec la forme d'un Bloc.
     */
    public PieceBuilder avecFormeBloc(){
        if(tailleX < 1 || tailleY < 1)throw new IllegalArgumentException("la taille de la forme n'est pas initialisée,il faut definir la taille avant de préciser la forme!");
        forme = new boolean[tailleX][tailleY];
        for(int j =0;j<tailleY;j++){
            for(int i =0;i<tailleX;i++){
                forme[i][j] = true;
            }
        }

        return this;
    }
    /**
     * Build construit la piece selon les paramètres précédents.
     */
    public PiecePuzzle Build(){
        if(forme == null)throw new IllegalArgumentException("il est necéssaire de spécifier une forme!");
        PiecePuzzle out = new PiecePuzzle(pos, tailleX, tailleY, forme, rotation,couleur);
        return out;
    }
}
