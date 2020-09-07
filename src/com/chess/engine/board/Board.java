package com.chess.engine.board;

import java.util.ArrayList;
import java.util.HashMap;

import com.chess.engine.pieces.*;
import com.chess.engine.player.*;

public final class Board {
    private static Square[] gameBoard;
    private static Colour playerToMove;
    private static ArrayList<Move> moveHistory;
    private static ArrayList<Piece> whitePieceSet;
    private static ArrayList<Piece> blackPieceSet;
    private static final String[] fileDesignators = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public final static HashMap<Integer, String> algebraicCoordinates = initAlgebraicCoordinates();


    public Board() {
        playerToMove = Colour.WHITE;
        moveHistory = new ArrayList<>();

        initBoardSquares();
        initWhitePieces();
        initBlackPieces();
    }

    private void initBoardSquares(){
        gameBoard = new Square[120];

        for(int i = 0; i < gameBoard.length; ++i) {
            gameBoard[i] = new Square(65);
        }

        int sq, sq64;
        for(int rank = 0; rank < 8; ++rank) {
            for(int file = 0; file < 8; ++file) {
                sq = (21 + file) + (rank * 10);
                sq64 = rank * 8 + file;
                gameBoard[sq].setCoordinate(sq64);
            }
        }
    }

    private void initWhitePieces(){
        whitePieceSet = new ArrayList<>();
        //White Pawns
        for(int i = 81; i < 89; ++i) {
            gameBoard[i].setPiece(new Pawn(i, Colour.WHITE));
            whitePieceSet.add(gameBoard[i].getPiece());
        }
        //White Rooks
        gameBoard[91].setPiece(new Rook(91, Colour.WHITE));
        whitePieceSet.add(gameBoard[91].getPiece());
        gameBoard[98].setPiece(new Rook(98, Colour.WHITE));
        whitePieceSet.add(gameBoard[98].getPiece());
        //White Knights
        gameBoard[92].setPiece(new Knight(92, Colour.WHITE));
        whitePieceSet.add(gameBoard[92].getPiece());
        gameBoard[97].setPiece(new Knight(97, Colour.WHITE));
        whitePieceSet.add(gameBoard[97].getPiece());
        //White Bishops
        gameBoard[93].setPiece(new Bishop(93, Colour.WHITE));
        whitePieceSet.add(gameBoard[93].getPiece());
        gameBoard[96].setPiece(new Bishop(96, Colour.WHITE));
        whitePieceSet.add(gameBoard[96].getPiece());
        //White Queen
        gameBoard[94].setPiece(new Queen(94, Colour.WHITE));//94
        whitePieceSet.add(gameBoard[94].getPiece());
        //White King
        gameBoard[95].setPiece(new King(95, Colour.WHITE));
        whitePieceSet.add(gameBoard[95].getPiece());

    }

    private void initBlackPieces(){
        blackPieceSet = new ArrayList<>();
        //Black Pawns
        for(int i = 31; i < 39; ++i) {
            gameBoard[i].setPiece(new Pawn(i, Colour.BLACK));
            blackPieceSet.add(gameBoard[i].getPiece());
        }
        //Black Rooks
        gameBoard[21].setPiece(new Rook(21, Colour.BLACK));//21
        blackPieceSet.add(gameBoard[21].getPiece());
        gameBoard[28].setPiece(new Rook(28, Colour.BLACK));
        blackPieceSet.add(gameBoard[28].getPiece());
        //Black Knights
        gameBoard[22].setPiece(new Knight(22, Colour.BLACK));//22
        blackPieceSet.add(gameBoard[22].getPiece());
        gameBoard[27].setPiece(new Knight(27, Colour.BLACK));
        blackPieceSet.add(gameBoard[27].getPiece());
        //Black Bishops
        gameBoard[23].setPiece(new Bishop(23, Colour.BLACK));
        blackPieceSet.add(gameBoard[23].getPiece());
        gameBoard[26].setPiece(new Bishop(26, Colour.BLACK));
        blackPieceSet.add(gameBoard[26].getPiece());
        //Black Queen
        gameBoard[24].setPiece(new Queen(24, Colour.BLACK));
        blackPieceSet.add(gameBoard[24].getPiece());
        //Black King
        gameBoard[25].setPiece(new King(25, Colour.BLACK));
        blackPieceSet.add(gameBoard[25].getPiece());
    }

    private static HashMap<Integer, String> initAlgebraicCoordinates(){
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

    public boolean isValidSquare(int coordinate) {
		/*if(coordinate < 0 || coordinate > 119) {
			return false;
		}*/
        return gameBoard[coordinate].getCoordinate() >= 0
                && gameBoard[coordinate].getCoordinate() <= 63;
    }

    public ArrayList<Piece> getWhitePieceSet(){
        return whitePieceSet;
    }

    public ArrayList<Piece> getBlackPieceSet(){
        return blackPieceSet;
    }

    public void printBoard() {
        for(int i = 20; i < 100; ++i) {
            if (i % 10 == 0) {
                System.out.println();
            }
            if(isValidSquare(i)) {
                System.out.print(gameBoard[i]);
            }
        }
        System.out.println();
    }

    public Square getSquare(int coordinate) {
        return gameBoard[coordinate];
    }
}