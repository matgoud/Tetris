package test;
import modèle.Factory.*;
import modèle.*;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestPiece{
    

    private void test_Matrice(){
        //creation des pieces et verification de leurs positions 
        PiecePuzzle piece1 = new PieceBuilder().avecTaille(3,3).avecFormeT().Build();
        assertTrue(piece1.getPieceXY(0,0));
        assertFalse(piece1.getPieceXY(0,1));

        PiecePuzzle piece2 = new PieceBuilder().avecTaille(3,4).avecFormeS().Build();
        assertTrue(piece2.getPieceXY(1,2));
        assertFalse(piece2.getPieceXY(2,1));
        
    }

    private void test_Rotation(){
        //creation d'une piece et verification de quelques positions avant et apres rotations
        PiecePuzzle piece3 = new PieceBuilder().avecTaille(3,4).avecFormeU().Build();
        assertFalse(piece3.getPieceXY(1,0));
        assertTrue(piece3.getPieceXY(1,3));

        piece3.rotationGauche();
        assertTrue(piece3.getPieceXY(1,0));
        assertFalse(piece3.getPieceXY(0,1));
        piece3.rotationGauche();

        //verification de l'integralité des positions de la piece apres rotation
        for(int i =0;i<piece3.getX();i++){
            for(int j =0;j<piece3.getY();j++){
                if(i%2!=1 || j==0){
                    assertTrue(piece3.getPieceXY(i,j));
                }
                else{
                    assertFalse(piece3.getPieceXY(i,j));
                }
            }
                    
        }
        
        //verification qu'une piece ne peut tourner si une autre existe deja 
        piece3.setPieceXY(0,1,false);
        piece3.setPieceXY(1,2,true);
        piece3.rotationGauche();
        assertFalse(piece3.getPieceXY(1,2));
        
    }

    
    public void toutTestPiece(){
        test_Matrice();
        test_Rotation();
    }


}