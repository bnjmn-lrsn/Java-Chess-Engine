package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.Square;
import com.chess.engine.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Board chessBoard;

    private Square sourceSquare;
    private Square destinationSquare;
    private Piece humanMovedPiece;

    //private final Color lightSquareColour = Color.decode("#d3cfde");//light gray
    //private final Color darkSquareColour = Color.decode("#808080");//dark dray

    private static String defaultPieceImagePath = "art" + File.separator + "standard" + File.separator;
    private final Color lightSquareColour = Color.decode("#dcb075");//light pink/brown
    //private final Color darkSquareColour = Color.decode("#65000b");// red/brown
    private final Color darkSquareColour = Color.decode("#320005");//dark red/brown

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(550,550);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension SQUARE_PANEL_DIMENSION = new Dimension(8, 8);

    public Table(){
        this.gameFrame = new JFrame("Tritogeneia");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        chessBoard = new Board();
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());

        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opening the PGN file!");
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private class BoardPanel extends JPanel {
        private final List<SquarePanel> boardSquares;
        
        BoardPanel(){
            super(new GridLayout(8,8));
            this.boardSquares = new ArrayList<>();
            int id;
            for(int i = 0; i < 64; ++i){
                id = (21 + (i % 8)) + (10 * (i / 8));
                final SquarePanel squarePanel = new SquarePanel(this, id);
                this.boardSquares.add(squarePanel);
                add(squarePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class SquarePanel extends JPanel {

        private final int squareID;

        SquarePanel(BoardPanel boardPanel, int squareID) {
            super(new GridBagLayout());
            this.squareID = squareID;
            setPreferredSize(SQUARE_PANEL_DIMENSION);
            assignSquareColour();
            assignSquarePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    /*if(isRightMouseButton(event)) {
                        if(sourceSquare == null) {
                            sourceSquare = chessBoard.getSquare(squareID);
                            humanMovedPiece = sourceSquare.getPiece();
                            if(humanMovedPiece == null){
                                sourceSquare = null;
                            }
                        } else {
                            destinationSquare = chessBoard.getSquare(squareID);
                            final Move move = null;


                        }


                    } else if(isLeftMouseButton(event)) {

                    }*/

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            validate();
        }

        private void assignSquarePieceIcon(Board board) {
            this.removeAll();
            if(board.getSquare(this.squareID).isOccupied()){
                try {
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath +
                            board.getSquare(this.squareID).getPiece().getAlliance().toString().substring(0, 1) +
                            board.getSquare(this.squareID).getPiece().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

        }

        private void assignSquareColour() {
            if(this.squareID / 10 % 2 == 0) {
                setBackground(this.squareID % 2 != 0 ? lightSquareColour : darkSquareColour);
            }else if(this.squareID / 10 % 2 != 0) {
                setBackground(this.squareID % 2 != 0 ? darkSquareColour : lightSquareColour);

            }
        }

    }

}
