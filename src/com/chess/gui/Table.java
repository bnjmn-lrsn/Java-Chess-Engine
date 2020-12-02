package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Alliance;
import com.chess.engine.player.Player;

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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private Player white;
    private Player black;
    private Board chessBoard;
    private BoardDirection boardDirection;
    private boolean highlightLegalMoves;

    private Square sourceSquare;
    private Square destinationSquare;
    private Piece humanMovedPiece;

    //Artwork attribution : Original work by C. Burnett (2006)(https://en.wikipedia.org/wiki/User:Cburnett)
    private static String defaultPieceImagePath = "art" + File.separator + "standard" + File.separator;
    private final Color lightSquareColour = Color.decode("#dcb075");//light pink/brown
    private final Color darkSquareColour = Color.decode("#65000b");// red/brown
    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(550,550);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension SQUARE_PANEL_DIMENSION = new Dimension(8, 8);

    public Table(){
        this.gameFrame = new JFrame("Tritogeneia");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.white = new Player(Alliance.WHITE);
        this.black = new Player(Alliance.BLACK);
        this.chessBoard = new Board(white, black);
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(e -> System.out.println("Opening the PGN file!"));
        fileMenu.add(openPGN);
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }

    private JMenu createPreferencesMenu(){
        JMenu preferenceMenu = new JMenu("Preferences");
        JMenuItem flipBoardMenuItem = new JMenuItem("Flip board");
        flipBoardMenuItem.addActionListener(e -> {
            boardDirection = boardDirection.opposite();
            boardPanel.drawBoard(chessBoard);
        });
        preferenceMenu.add(flipBoardMenuItem);
        preferenceMenu.addSeparator();
        final JCheckBoxMenuItem legalMoveHighlighter = new JCheckBoxMenuItem("Highlight legal moves", false);
        legalMoveHighlighter.addActionListener(e -> highlightLegalMoves = legalMoveHighlighter.isSelected());
        preferenceMenu.add(legalMoveHighlighter);
        return preferenceMenu;
    }

    public static class MoveLog {

        private final List<Move> movesPlayedInGame;

        public MoveLog(){
            this.movesPlayedInGame = new ArrayList<>();
        }

        public List<Move> getMovesPlayedInGame(){
            return this.movesPlayedInGame;
        }

        public void addMove(Move move){
            this.movesPlayedInGame.add(move);
        }

        public int sizeMoveLog(){
            return this.movesPlayedInGame.size();
        }

        public void clearMoveLog(){
            this.movesPlayedInGame.clear();
        }

        public Move removeMove(){
            return this.movesPlayedInGame.get(this.movesPlayedInGame.size() - 1);
        }
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

        public void drawBoard(Board boardToDraw) {
            removeAll();
            for(SquarePanel panel : boardDirection.traverse(boardSquares)){
                panel.drawSquare(boardToDraw);
                add(panel);
            }
            validate();
            repaint();
        }
    }

    private class SquarePanel extends JPanel {
        private final int squareID;

        SquarePanel(BoardPanel boardPanel, int squareID){
            super(new GridBagLayout());
            this.squareID = squareID;
            setPreferredSize(SQUARE_PANEL_DIMENSION);
            assignSquareColour();
            assignSquarePieceIcon(chessBoard);

            addMouseListener(new MouseListener(){
                @Override
                public void mouseClicked(MouseEvent e){
                    if(isRightMouseButton(e)){//cancel everything
                        sourceSquare = null;
                        destinationSquare = null;
                        humanMovedPiece = null;
                    }
                    else if(isLeftMouseButton(e)){
                        if(sourceSquare == null){
                            sourceSquare = chessBoard.getSquare(squareID);
                            humanMovedPiece = sourceSquare.getPiece();
                            if(humanMovedPiece == null){
                                sourceSquare = null;
                            }
                        }
                        else{
                            destinationSquare = chessBoard.getSquare(squareID);
                            Move move = Move.MoveFactory.createMove(chessBoard, sourceSquare.getIndex(), destinationSquare.getIndex());
                            chessBoard.makeMove(move);
                            sourceSquare = null;
                            destinationSquare = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(() -> boardPanel.drawBoard(chessBoard));
                    }
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
            if(this.squareID / 10 % 2 == 0){
                setBackground(this.squareID % 2 != 0 ? lightSquareColour : darkSquareColour);
            }else if(this.squareID / 10 % 2 != 0){
                setBackground(this.squareID % 2 != 0 ? darkSquareColour : lightSquareColour);
            }
        }

        public void drawSquare(Board boardToDraw) {
            assignSquareColour();
            assignSquarePieceIcon(boardToDraw);
            highlightLegalMoves(boardToDraw);
            validate();
            repaint();
        }

        private void highlightLegalMoves(Board board){
            if(highlightLegalMoves){
                for(Move move : getPieceLegalMoves(board)){
                    if(move.getCoordinateMovedTo() == squareID) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("art" + File.separator +
                                    "misc" + File.separator + "green_dot.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        
        private Collection<Move> getPieceLegalMoves(Board board){
            if(humanMovedPiece != null &&
                    humanMovedPiece.getAlliance() == board.getCurrentMoveMaker().getPlayerAlliance()){
                Collection<Move> legalMoves = new ArrayList<>();
                for(Move move : board.getCurrentMoveMaker().generateAllPossibleMoves(board)){
                    if(move.getPieceMoved() == humanMovedPiece){
                        legalMoves.add(move);
                    }
                }
                return legalMoves;
            }
            return Collections.EMPTY_LIST;
        }
    }

    enum BoardDirection{
        NORMAL {
            @Override
            List<SquarePanel> traverse(List<SquarePanel> boardSquares) {
                return boardSquares;
            }

            @Override
            BoardDirection opposite() {
                return REVERSED;
            }
        },
        REVERSED{
            @Override
            List<SquarePanel> traverse(List<SquarePanel> boardSquares) {
                Collections.reverse(boardSquares);
                return boardSquares;
            }

            @Override
            BoardDirection opposite() {
                return NORMAL;
            }
        };
        abstract List<SquarePanel> traverse(List<SquarePanel> boardSquares);
        abstract BoardDirection opposite();
    }
}
