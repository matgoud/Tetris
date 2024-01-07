package test;
import modèle.Factory.*;
import modèle.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestPlateau{


    private void test_ajoutPiece(){
        //verification qu'une piece est bien ajouté si il n'y a aucun probleme
        PiecePuzzle piece1 = new PieceBuilder().avecTaille(3,4).avecPosition(5,5).avecFormeS().Build();
        PiecePuzzle piece2 = new PieceBuilder().avecTaille(4,3).avecPosition(10,10).avecFormeU().Build();
        PlateauPuzzle plateau1 = new PlateauPuzzle(20,20);
        plateau1.ajouterPiece(piece1);
        plateau1.ajouterPiece(piece2);
        assertTrue(plateau1.getPlateau().size()==2);

        //verification qu'une piece rentre en collision et n'est pas ajouté si trop proche ou hors jeux
        PlateauPuzzle plateau2 = new PlateauPuzzle(10,10);
        plateau2.ajouterPiece(piece1);
        piece2.setPos(new Position(6,6));
        assertFalse(plateau2.ajouterPiece(piece2));//collision

        piece1.setPos(new Position(0,0));
        assertFalse(plateau2.ajouterPiece(piece1));//hors jeux

        piece2.setPos(new Position(9,9));
        assertFalse(plateau2.ajouterPiece(piece2));//hors jeux
        

    }

    private void test_resultat(){
        //verification que le resultat renvoyé est le bon pour une piece
        PlateauPuzzle plateau3 = new PlateauPuzzle(10,10);
        PiecePuzzle piece3 = new PieceBuilder().avecTaille(5,5).avecPosition(4,4).avecFormeBloc().Build();
        plateau3.ajouterPiece(piece3);
        int resultat1 = plateau3.resultat();
        assertTrue(resultat1 == piece3.getX()*piece3.getY());

        //De meme mais pour plusieurs pieces
        PiecePuzzle piece4 = new PieceBuilder().avecTaille(2,3).avecPosition(1,1).avecFormeL().Build();
        plateau3.ajouterPiece(piece4);
        int resultat2 = plateau3.resultat();
        assertFalse(resultat1 == resultat2);
        assertTrue(resultat2==49);

    }

    private void test_matrice(){
        //verification que les pieces sont bien presentes dans la matrice du plateau
        PlateauPuzzle plateau4 = new PlateauPuzzle(15,15);
        PiecePuzzle piece5 = new PieceBuilder().avecTaille(2,3).avecPosition(1,1).avecFormeL().Build();
        PiecePuzzle piece6 = new PieceBuilder().avecTaille(3,3).avecPosition(4,4).avecFormeT().Build();
        plateau4.ajouterPiece(piece5);
        plateau4.ajouterPiece(piece6);
        int[][] matrice1 = plateau4.getMatricePlateau();
        assertFalse(matrice1[0][1]==0);
        for(int i =0;i<3;i++){
            assertFalse(matrice1[i][0]==0);
        }
        
        for(int j =0;j<3;j++){
            assertFalse(matrice1[3][3+j]==0);
            assertFalse(matrice1[3+j][4]==0);
        }

        //De meme mais apres rotation
        plateau4.rotationPiece(piece6,true);
        plateau4.rotationPiece(piece6,true);
        int[][]matrice2 = plateau4.getMatricePlateau();
        assertTrue(matrice2[3][3]==0);
        assertTrue(matrice2[3][5]==0);
        for(int k =0;k<3;k++){
            assertFalse(matrice2[5][3+k]==0);
            assertFalse(matrice2[3+k][4]==0);
        }

        
    }

    private void test_piecePosition(){
        //verification que la fonction getPiecePos renvoie les bonnes valeurs
        PlateauPuzzle plateau5 =new PlateauPuzzle(20,20);
        PiecePuzzle piece7 = new PieceBuilder().avecTaille(3,3).avecPosition(15,15).avecFormeU().Build();
        PiecePuzzle piece8 = new PieceBuilder().avecTaille(5,5).avecPosition(4,4).avecFormeBloc().Build();
        plateau5.ajouterPiece(piece7);
        plateau5.ajouterPiece(piece8);
        int[][] matrice3 = plateau5.getMatricePlateau();
        

        assertTrue(plateau5.getPieceEnPos(new Position(2,2))==piece8);
        assertFalse(plateau5.getPieceEnPos(new Position(15,15))==piece7);
        assertTrue(plateau5.getPieceEnPos(new Position(15,16))==piece7);


    }
    public void toutTestPlateau(){
        test_ajoutPiece();
        test_resultat();
        test_matrice();
        test_piecePosition();

    }
}