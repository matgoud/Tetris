package test;
import modèle.Factory.*;
import modèle.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPieceBuilder {
    //teste si l'ajout de position fonctionne
    private void TestAddPosition(){
        Position pos = new Position(4,5);
        PiecePuzzle p1 = new PiecePuzzle(pos, 2, 2, null, 0,0);
        PiecePuzzle p2 = new PieceBuilder().avecTaille(2,2).avecFormeL().avecPosition(pos.getX(),pos.getY()).Build();
        assertTrue(p1.pos.getX() == p2.pos.getX() && p1.pos.getY() == p2.pos.getY());
    }
    //teste si l'ajout de taille fonctionne
    private void TestAddTaille(){
        int tx = 8;
        int ty = 9;
        PiecePuzzle p1 = new PiecePuzzle(null, tx, ty, null, 0,0);
        PiecePuzzle p2 = new PieceBuilder().avecTaille(tx,ty).avecFormeL().Build();
        assertTrue(p1.getTailleX() == p2.getTailleX() && p1.getTailleY() == p2.getTailleY());
    }
    //teste si l'ajout de rotation fonctionne
    private void TestAddRotation(){
        int r = 19;
        PiecePuzzle p1 = new PiecePuzzle(null, 2, 2, null, r,0);
        PiecePuzzle p2 = new PieceBuilder().avecTaille(2,2).avecFormeL().avecRotation(r).Build();
        assertFalse(p1.getRotation() == p2.getRotation());//car la rotation de la piece n'est pas modulée dans le constructeur!
        r = 3;
        p1 = new PiecePuzzle(null, 0, 0, null, r,0);
        p2 = new PieceBuilder().avecTaille(2,2).avecFormeL().avecRotation(r).Build();
        assertTrue(p1.getRotation() == p2.getRotation());
    }
    /**
     * Cette méthode permet de d'effectuer tout les tests contenu dans cette classe, si l'un des tests ne passe
     * pas le programme s'arrête
     */
    public void toutTestPieceBuilder(){
        TestAddPosition();
        TestAddTaille();
        TestAddRotation();
    }
}