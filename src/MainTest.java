import test.*;
public class MainTest{
    public static void main(String[] args){
        TestPiece pieceTest = new TestPiece();
        TestPieceBuilder pieceBuilderTest = new TestPieceBuilder();
        TestPlateau plateauTest =new TestPlateau();

        pieceTest.toutTestPiece();
        System.out.println("All tests Piece -> OK");
        plateauTest.toutTestPlateau();
        System.out.println("All tests Plateau -> OK");
        pieceBuilderTest.toutTestPieceBuilder();
        System.out.println("All tests PieceBuilder -> OK");

        System.out.println("All tests  -> OK");
    }
}