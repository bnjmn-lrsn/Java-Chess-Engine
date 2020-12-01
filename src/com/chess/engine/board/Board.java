package com.chess.engine.board;

import java.util.ArrayList;
import java.util.HashMap;

import com.chess.engine.pieces.*;
import com.chess.engine.player.*;

public final class Board {
    private Square[] gameBoard;
    private Player whitePlayer;
    private Player blackPlayer;
    private Player currentMoveMaker;
    private Alliance playerToMove;
    private ArrayList<Move> moveHistory;
    private ArrayList<Piece> whitePieceSet;
    private ArrayList<Piece> blackPieceSet;

    private boolean enPassant;
    private Square enPassantSquare;

    private static final String[] fileDesignators = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public static final HashMap<Integer, String> algebraicCoordinates = initAlgebraicCoordinates();

    public Board() {
        this.playerToMove = Alliance.WHITE;
        this.moveHistory = new ArrayList<>();
        this.enPassant = false;

        initBoardSquares();
        initWhitePieces();
        initBlackPieces();

        this.whitePlayer = new Player(Alliance.WHITE);
        this.blackPlayer = new Player(Alliance.BLACK);
        this.currentMoveMaker = this.whitePlayer;
    }

    public Board(Player whitePlayer, Player blackPlayer){
        this.playerToMove = Alliance.WHITE;
        this.moveHistory = new ArrayList<>();
        this.enPassant = false;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.currentMoveMaker = whitePlayer;

        initBoardSquares();
        initWhitePieces();
        initBlackPieces();

        this.whitePlayer.setMaterialInPlay(this.whitePieceSet);
        this.blackPlayer.setMaterialInPlay(this.blackPieceSet);

    }

    private void initBoardSquares() {
        this.gameBoard = new Square[120];

        for(int i = 0; i < this.gameBoard.length; ++i){
            this.gameBoard[i] = new Square(65, i);
        }

        int sq, sq64;
        for(int rank = 0; rank < 8; ++rank){
            for(int file = 0; file < 8; ++file){
                sq = (21 + file) + (rank * 10);
                sq64 = rank * 8 + file;
                this.gameBoard[sq].setCoordinate(sq64);
            }
        }
    }

    private void initWhitePieces(){
        this.whitePieceSet = new ArrayList<>();
        //White Pawns
        for(int i = 81; i < 89; ++i){
            this.gameBoard[i].setPiece(new Pawn(i, Alliance.WHITE));
            this.whitePieceSet.add(this.gameBoard[i].getPiece());
        }
        //White Rooks
        this.gameBoard[91].setPiece(new Rook(91, Alliance.WHITE));
        this.whitePieceSet.add(this.gameBoard[91].getPiece());
        this.gameBoard[98].setPiece(new Rook(98, Alliance.WHITE));
        this.whitePieceSet.add(this.gameBoard[98].getPiece());
        //White Knights
        this.gameBoard[92].setPiece(new Knight(92, Alliance.WHITE));
        this.whitePieceSet.add(this.gameBoard[92].getPiece());
        this.gameBoard[97].setPiece(new Knight(97, Alliance.WHITE));
        this.whitePieceSet.add(this.gameBoard[97].getPiece());
        //White Bishops
        this.gameBoard[93].setPiece(new Bishop(93, Alliance.WHITE));
        this.whitePieceSet.add(this.gameBoard[93].getPiece());
        this.gameBoard[96].setPiece(new Bishop(96, Alliance.WHITE));
        this.whitePieceSet.add(this.gameBoard[96].getPiece());
        //White Queen
        this.gameBoard[94].setPiece(new Queen(94, Alliance.WHITE));//94
        this.whitePieceSet.add(this.gameBoard[94].getPiece());
        //White King
        this.gameBoard[95].setPiece(new King(95, Alliance.WHITE));
        this.whitePieceSet.add(this.gameBoard[95].getPiece());
    }

    private void initBlackPieces() {
        this.blackPieceSet = new ArrayList<>();
        //Black Pawns
        for(int i = 31; i < 39; ++i){
            this.gameBoard[i].setPiece(new Pawn(i, Alliance.BLACK));
            this.blackPieceSet.add(this.gameBoard[i].getPiece());
        }
        //Black Rooks
        this.gameBoard[21].setPiece(new Rook(21, Alliance.BLACK));//21
        this.blackPieceSet.add(this.gameBoard[21].getPiece());
        this.gameBoard[28].setPiece(new Rook(28, Alliance.BLACK));
        this.blackPieceSet.add(this.gameBoard[28].getPiece());
        //Black Knights
        this.gameBoard[22].setPiece(new Knight(22, Alliance.BLACK));//22
        this.blackPieceSet.add(this.gameBoard[22].getPiece());
        this.gameBoard[27].setPiece(new Knight(27, Alliance.BLACK));
        this.blackPieceSet.add(this.gameBoard[27].getPiece());
        //Black Bishops
        this.gameBoard[23].setPiece(new Bishop(23, Alliance.BLACK));
        this.blackPieceSet.add(this.gameBoard[23].getPiece());
        this.gameBoard[26].setPiece(new Bishop(26, Alliance.BLACK));
        this.blackPieceSet.add(this.gameBoard[26].getPiece());
        //Black Queen
        this.gameBoard[24].setPiece(new Queen(24, Alliance.BLACK));
        this.blackPieceSet.add(this.gameBoard[24].getPiece());
        //Black King
        this.gameBoard[25].setPiece(new King(25, Alliance.BLACK));
        this.blackPieceSet.add(this.gameBoard[25].getPiece());
    }

    private static HashMap<Integer, String> initAlgebraicCoordinates() {
        HashMap<Integer, String> hm = new HashMap<>();
        int rank, file, coordinate, reverseFile = 8;
        String algebraicCoordinate;
        for(rank = 0; rank < 8; ++rank){
            for(file = 0; file < 8; ++file){
                coordinate = (21 + file) + (rank * 10);
                algebraicCoordinate = fileDesignators[file] + reverseFile;
                hm.put(coordinate, algebraicCoordinate);
            }
            reverseFile--;
        }
        return hm;
    }

    public int getBoardSize(){ return this.gameBoard.length; }

    public boolean isValidSquare(int coordinate) {
        return this.gameBoard[coordinate].getCoordinate() >= 0
               && this.gameBoard[coordinate].getCoordinate() <= 63;
    }

    public Square getSquare(int coordinate) {
        return this.gameBoard[coordinate];
    }

    public ArrayList<Piece> getWhitePieceSet(){
        return this.whitePieceSet;
    }

    public ArrayList<Piece> getBlackPieceSet(){
        return this.blackPieceSet;
    }

    public ArrayList<Piece> getPieceSet(Alliance alliance) {
        return alliance == Alliance.WHITE ? getWhitePieceSet() : getBlackPieceSet();
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player getCurrentMoveMaker(){ return this.currentMoveMaker; }

    public ArrayList<Move> getMoveHistory() {
        return this.moveHistory;
    }

    public void makeMove(Move move) {
        move.makeMove(this);
        move.getPieceMoved().setMovedState();
        this.moveHistory.add(move);
        if(this.playerToMove == Alliance.WHITE){
            this.playerToMove = Alliance.BLACK;
            this.currentMoveMaker = this.blackPlayer;
        }
        else {
            this.playerToMove = Alliance.WHITE;
            this.currentMoveMaker = this.whitePlayer;
        }
    }

    public void undoMove(){
        Move mostRecentMove = this.moveHistory.get(this.moveHistory.size() - 1);
        mostRecentMove.undoMove(this);
        this.moveHistory.remove(this.moveHistory.size() - 1);
        if(this.playerToMove == Alliance.WHITE){
            this.playerToMove = Alliance.BLACK;
            this.currentMoveMaker = this.blackPlayer;
        }
        else{
            this.playerToMove = Alliance.WHITE;
            this.currentMoveMaker = this.whitePlayer;
        }
    }

    public boolean hasEnPassantSquare(){
        if(this.moveHistory.size() > 0){
            return this.moveHistory.get(this.moveHistory.size() - 1).isPawnJump();
        }
        else{
            return false;
        }
    }

    public int  getEnPassantSquare(){
        if(this.hasEnPassantSquare()){
            return moveHistory.get(moveHistory.size() - 1).coordinateMovedTo;
        }
        else{
            return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();

        for(int i = 20; i < 100; ++i){
            if(i % 10 == 0) {
                board.append("\n");
            }
            if(isValidSquare(i)){
                board.append(gameBoard[i]);
            }
        }
        return board.toString();
    }
}