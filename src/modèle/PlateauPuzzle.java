package modèle;
import modèle.ChainOfResponsibility.*;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;
/**
 * Cette classe est un plateau dans lequel on manipule des pieces.
 */
public class PlateauPuzzle extends ModeleEcoutable{
    public ArrayList<PiecePuzzle> plateau = new ArrayList<>();
    public int tailleX;
    public int tailleY;
    private boolean isOver;
    private PiecePuzzle selected;
    /**
     * @param tailleX la tailleX du plateau.
     * @param tailleY la tailleY du plateau.
     */
    public PlateauPuzzle(int tailleX,int tailleY){
        this.tailleX = tailleX;
        this.tailleY = tailleY;    
        this.isOver = false;
        this.selected = null;
        changement();
    }

    public int getTailleX() {
        return tailleX;
    }

    public int getTailleY() {
        return tailleY;
    }
    /**
     * @return renvoi la liste des pieces presentes dans le plateau.
     */
    public ArrayList<PiecePuzzle> getPlateau(){
        return plateau;
    }

    public boolean isOver(){
        return isOver;
    }
    public void endGame(){
        isOver = true;
        changement();
    }

    public void setSelectedPiece(PiecePuzzle piece){
        selected = piece;
        changement();
    }

    public PiecePuzzle getSelectedPiece(){
        return selected;
    }

    public void unSelectPiece(){
        selected = null;
        changement();
    }
    //verification que la piece peut etre ajoutée via une chain of responsibility
    //prend en argument une Piece qui sera ajouté ou non à plateau
    /**
     * @param piece La piece qui va peut être être ajouté dans le plateau.
     * @return True si la piece a pu être positionné, False sinon.
     */
    public boolean ajouterPiece(PiecePuzzle piece){
        int taille = getPlateau().size();
        Position stock = piece.centerPlateau();
        
        ArrayList<Maillon> chaine = new ArrayList<>();
        Maillon horsJeu = new MaillonHorsJeu(stock,tailleX,tailleY);
        chaine.add(horsJeu);
        for(PiecePuzzle pieceTemp : plateau){//initialisation de la chain of responsibility
            
            Maillon collision = new MaillonCollision(pieceTemp);
            chaine.add(collision);
            
        }
        for(int i =1;i<chaine.size();i++){
            Maillon m2 = chaine.get(i-1);
            Maillon m1 = chaine.get(i);
            m2.setSuivant(m1);
        }

        horsJeu.actionValide(piece,this);//lancement du maillon (tout les maillons doivent reussir si on souhaite que la piece se positionne)
        
        if(taille != getPlateau().size()){
            changement();
            return true;
        }

        return false;
        

    }

    /**
     * @return l'aire de la zone la plus grande possible en prenant en compte toutes les pieces posées .
     */
    public int resultat(){
        int res = 0;
        int xHaut = tailleX;
        int yHaut = tailleY;
        int xBas = 0;
        int yBas = 0;
        for(PiecePuzzle piece : getPlateau()){//selection des positions les plus grandes et petites de X,Y
            Position posHautGauche = piece.getPositionMin();
            Position posBasDroite = piece.getPositionMax();
            if(posHautGauche.getX()<xHaut){
                xHaut = posHautGauche.getX();
            }
            if(posHautGauche.getY()<yHaut){
                yHaut= posHautGauche.getY();
            }
            if(posBasDroite.getX()>xBas){
                xBas = posBasDroite.getX();
            }
            if(posBasDroite.getY()>yBas){
                yBas = posBasDroite.getY();
            }
        }
        
        res = (1+(xBas-xHaut))*(1+(yBas-yHaut));//calculer l'air
        return res;

    }

    //affiche une matrice prise en argument 
    public String visuelMatrice(int[][] matrice){
        String ch="";
        int n = matrice.length;
        int m = matrice[0].length;
        for(int i =0;i<n;i++){
            for(int j =0;j<m;j++){
                if(matrice[j][i]!=0){
                    ch=ch+"1 ";
                }
                else{
                    ch=ch+"0 ";
                }
            }
            ch=ch+"\n";
        }
        return ch;

        
    }

    /**
     * @return renvoie une Matrice correspondant aux pieces du plateau actuel .
     */
    public int[][] getMatricePlateau(){
        int[][] res = new int[tailleY][tailleX];
        for(PiecePuzzle piece : getPlateau()){
            
            for(int i =0;i<piece.getX();i++){
                for(int j=0;j<piece.getY();j++){
                    Position posHautGauche = piece.getPositionMin();
        
                    if(piece.getPieceXY(i,j)){
                        
                        res[posHautGauche.getY()+j][posHautGauche.getX()+i]=piece.getCouleur();
                    }

                }
            }
        }
        return res;
    }
    /**
     * @param stock1 Le point le plus en haut à gauche de la piece 1 via getPositionMin de piecePuzzle.
     * @param stock11 Le point le plus en bas à gauche de la piece 1 via getPositionMax de piecePuzzle.
     * @param stock2 Le point le plus en haut à gauche de la piece 2 via getPositionMin de piecePuzzle.
     * @param stock22 Le point le plus en bas à gauche de la piece 2 via getPositionMax de piecePuzzle.
     * @return Renvoi 2 Positions dans une arrayList qui correspond à la plus petite zone commune entre deux pieces prises en argument .
     */
    public ArrayList<Position> getCoordCommun(Position stock1 ,Position stock11 ,Position stock2 ,Position stock22){
        ArrayList<Position> res = new ArrayList<>();
        /* Si le point le plus à droite du premier rectangle est à gauche du point le plus à gauche du 2eme rectangle
        ou si "               "à gauche"                        "à droite"                "à droite"                     ",
        ou si "               "en bas  "                        "en haut "                "en haut "                     ",
        ou si "               "en haut "                        "en bas  "                "en bas  "                     ",
        alors il n'y a pas de zone commune entre les 2 rectangles*/
        if(stock11.getX()<stock2.getX() || stock1.getX()>stock22.getX() || stock11.getY()<stock2.getY() || stock1.getY()>stock22.getY()) return null;

        /* Le point le plus à droite de la zone commune est donc le point le plus à gauche entre le point le plus à droite de chaque rectangle,
           "               "à gauche"                                            "à droite"                      "à gauche"                   ",
           "               "en bas  "                                            "en haut "                      "en bas  "                   ",
           "               "en haut "                                            "en bas  "                      "en haut "                   ",
        */
        Position coordHG = new Position(max(stock1.getX(),stock2.getX()),max(stock1.getY(),stock2.getY()));
        Position coordBD = new Position(min(stock11.getX(),stock22.getX()),min(stock11.getY(),stock22.getY()));
        //System.out.println("Point en haut à gauche de la colision: "+coordHG);
        //System.out.println("Point en bas à droite de la colision: "+coordBD);
        res.add(coordHG);
        res.add(coordBD);
        return res;
    }
    /**
     * @param piece la piece qui va subir une rotation
     * @param gauche la direction vers laquel la piece va tourner
     * @return True si la piece à reussi sa rotation False sinon
     */
    public boolean rotationPiece(PiecePuzzle piece,boolean gauche){//true = rotation gauche false = rotation droite;
        plateau.remove(piece);
        if(gauche){
            piece.rotationGauche();
            if(!ajouterPiece(piece)){
                //System.out.println("Echec de la rotation");
                piece.rotationDroite();
                ajouterPiece(piece);
                return false;
            }
            else{
                return true;
                //System.out.println("Rotation Reussie");
            }
        }
        else{
            piece.rotationDroite();
            if(!ajouterPiece(piece)){
                //System.out.println("Echec de la rotation");
                piece.rotationGauche();
                ajouterPiece(piece);
                return false;
            }
            else{
                return true;
                //System.out.println("Rotation Reussie");
            }

        }
    }

    public boolean movePiece(PiecePuzzle newPiece){
        plateau.remove(newPiece);
        return ajouterPiece(newPiece);
    }

    public void removePiece(PiecePuzzle newPiece){
        if(plateau.contains(newPiece)){
            plateau.remove(newPiece);
            changement();
        }
    }

    public PiecePuzzle getPieceEnPos(Position pos){
        for(PiecePuzzle pi : plateau){
            Position pHG = pi.getPositionMin();
            Position diff = new Position(pos.getX()-pHG.getX(),pos.getY()-pHG.getY());
            ///System.out.println("diff : "+diff);
            if(pi.getPieceXY(diff.getX(), diff.getY())) return pi;
        }
        return null;
    }
}