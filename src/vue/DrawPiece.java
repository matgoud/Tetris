package vue;

import modèle.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.Font;

/**
 * Cette classe permet de dessiner le JPanel contenant la pièce sélectionnée.
 */
public class DrawPiece extends JPanel implements Ecouteur{

    private PlateauPuzzle p;
    private JButton resultat;
    private final Color ROUGE = new Color( 176, 58, 46);
    private final Color GRIS = new Color( 179, 182, 183 );

    public DrawPiece(PlateauPuzzle p){
        super();
        this.resultat = new JButton("résultat");
        add(resultat);
        setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        this.p = p;
        //Permet à la classe d'écouter le plateau pour qu'elle se mette à jour à chque changement
        p.ajoutEcouteur(this);
    }


    /**
     * Cette méthode permet de dessiner la pièce sélectionée.
     */
    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        //condition permettant de retirer l'affichage de l'interface quand la partie est terminée
        if(p.isOver() == false){
            
            int dx = 60;
    
            int height = (int) this.getHeight();
            int width = (int) this.getWidth();
    
            Font font = new Font("Arial", Font.BOLD, 20);
            g.setFont(font);
            g.drawString("pièce sélectionnée",width/2-(19*5),height/5);
            
            //Si une pièce est sélectionnée on la dessine
            if(p.getSelectedPiece() != null){
                PiecePuzzle piece = p.getSelectedPiece();
                Position tmp_pos = piece.centerPiece();
                int color = piece.getCouleur();
                int b = color - (256 * (color/256));
                color /= 256;
                int gg = color - (256 * (color/256));
                color /= 256;
                int r = color;
                Color pieceColor = new Color(r,gg,b);
                for(int i = 0;i<piece.getX();i++){
                    for(int j = 0;j<piece.getY();j++){
                        if(piece.getPieceXY(i,j)==true){
                            g.setColor(pieceColor);
                            g.fillRect(i*dx+(height/3),j*dx+(width/3),dx,dx);
                            g.setColor(Color.BLACK);
                            g.drawRect(i*dx+(height/3),j*dx+(width/3),dx,dx);
                        }
                    }
                }
            }
        }
    }

    /**
    * Cette méthode permet de récupérer le bouton résultat.
    */
    public JButton getResultatButton(){
        return resultat;
    }

    /**
    * Cette méthode permet de récupérer le plateau de jeu.
    */
    public PlateauPuzzle getPlateau(){
        return p;
    }

    /**
    * Cette méthode permet de mettre à jour DrawPiece lorsqu'il y a un changement dans le plateau.
    */
    @Override
    public void MiseAJour(Object source){
        this.repaint();
    }

}