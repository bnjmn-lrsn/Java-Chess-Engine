package com.chess.gui;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Alliance;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import static com.chess.gui.Table.*;

public class CapturedPiecesPanel extends JPanel {

    private JPanel northPanel;
    private JPanel southPanel;

    private final static Color PANEL_COLOUR = Color.decode("0x963a23");
    private final static Dimension CAPTURED_PIECES_DIMENSION = new Dimension(120, 100);
    private final static EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public CapturedPiecesPanel(){
        super(new BorderLayout());
        setBackground(PANEL_COLOUR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOUR);
        this.southPanel.setBackground(PANEL_COLOUR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(CAPTURED_PIECES_DIMENSION);
    }

    public void redo(MoveLog moveLog){
        //southPanel.removeAll();
        //northPanel.removeAll();
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOUR);
        this.southPanel.setBackground(PANEL_COLOUR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);

        final List<Piece> whiteCapturedPieces = new ArrayList<>();
        final List<Piece> blackCapturedPieces = new ArrayList<>();

        for(Move move: moveLog.getMovesPlayedInGame()){
            if(move.isCapture()){
                final Piece capturedPiece = move.getCapturedPiece();
                if(capturedPiece.getAlliance() == Alliance.WHITE){
                    whiteCapturedPieces.add(capturedPiece);
                }else{
                    blackCapturedPieces.add(capturedPiece);
                }
            }
        }
        Collections.sort(whiteCapturedPieces, Comparator.comparingInt(Piece::getValue));
        Collections.sort(blackCapturedPieces, Comparator.comparingInt(Piece::getValue));

        for(Piece piece : whiteCapturedPieces){
            try{
                final BufferedImage pieceImage = ImageIO.read(new File("art" + File.separator
                + "standard" + File.separator + piece.getAlliance().toString().charAt(0) +
                        piece.toString() + ".gif"));
                final ImageIcon icon = new ImageIcon(pieceImage);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() - 30, icon.getIconWidth() - 30, Image.SCALE_SMOOTH
                )));
                this.southPanel.add(imageLabel);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        for(Piece piece : blackCapturedPieces){
            try{
                final BufferedImage pieceImage = ImageIO.read(new File("art" + File.separator
                        + "standard" + File.separator + piece.getAlliance().toString().charAt(0) +
                        piece.toString() + ".gif"));
                final ImageIcon icon = new ImageIcon(pieceImage);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(
                        icon.getIconWidth() - 30, icon.getIconWidth() - 30, Image.SCALE_SMOOTH
                )));
                this.northPanel.add(imageLabel);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        validate();
    }
    
    
}
