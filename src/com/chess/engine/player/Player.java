package com.chess.engine.player;

import java.util.ArrayList;

import com.chess.engine.pieces.*;
import com.chess.engine.board.*;

public class Player {
    private ArrayList<Piece> materialInPlay;
    private ArrayList<Move> allPossibleMoves;
    private final Colour myColour;
    private final Piece king;
    private final int[] diagonalModifiers = {-11, -9, 9, 11};
    private final int[] rankFileModifiers = {-10, -1, 1, 10};
    private final int[] knightModifiers = {-21, -19, -12, -8, 8, 12, 19, 21};
    private final int[] kingModifiers = {-11, -10, -9, -1, 1, 9, 10, 11};

    public Player(Colour myColour, Board board) {
        this.myColour = myColour;
        materialInPlay = board.getPieceSet(myColour);
        king = materialInPlay.get(materialInPlay.size() - 1);
    }

    public Colour getPlayerColour() {
        return myColour;
    }

    public ArrayList<Piece> getMaterialInPlay(){
        return materialInPlay;
    }

    public boolean kingIsSafe(Move move, Board board) {
        makeMove(move, board);

        if(checkForDiagonalThreats(board)) {
            undoMove(move, board);
            return false;
        }else if(checkForRankFileThreats(board)) {
            undoMove(move, board);
            return false;
        }else if(checkForKnightThreats(board)) {
            undoMove(move, board);
            return false;
        }else if(checkForPawnThreats(board)) {
            undoMove(move, board);
            return false;
        }else if(checkForOpposingKing(board)) {
            undoMove(move, board);
            return false;
        }else {
            undoMove(move, board);
            return true;
        }
    }

    private boolean checkForDiagonalThreats(Board board) {
        for(int modifier : diagonalModifiers){
            if(checkForDistantThreat(board, modifier, "Bishop") || checkForDistantThreat(board, modifier, "Queen")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForRankFileThreats(Board board) {
        for(int modifier : rankFileModifiers){
            if(checkForDistantThreat(board, modifier, "Rook") || checkForDistantThreat(board, modifier, "Queen")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForKnightThreats(Board board) {
        for(int modifier : knightModifiers){
            if(checkForThreat(board, modifier, "Knight")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForPawnThreats(Board board) {
        if(myColour == Colour.WHITE){
            if(checkForThreat(board, -11, "Pawn")){
                return true;
            }
            else return checkForThreat(board, -9, "Pawn");
        }
        else if(myColour == Colour.BLACK){
            if(checkForThreat(board, 11, "Pawn")){
                return true;
            }
            else return checkForThreat(board, 9, "Pawn");
        }
        return false;
    }

    private boolean checkForOpposingKing(Board board) {
        for(int modifier : kingModifiers) {
            if(checkForThreat(board, modifier, "King")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForThreat(Board board, int modifier, String pieceType){
        int kingSquare = king.getCoordinate();
        int newCoordinate;
        Square newSquare;
        Piece attackingPiece;

        newCoordinate = kingSquare + modifier;
        if(board.isValidSquare(newCoordinate)){
            newSquare = board.getSquare(newCoordinate);
            if(newSquare.isOccupied() && newSquare.getPiece().getColour() != myColour){
                attackingPiece = newSquare.getPiece();
                return attackingPiece.getPieceType().equals(pieceType);
            }
        }
        return false;
    }

    private boolean checkForDistantThreat(Board board, int modifier, String pieceType){
        int distanceModifier = 1;
        int kingSquare = king.getCoordinate();
        int newCoordinate;
        Square newSquare;

        newCoordinate = kingSquare + distanceModifier * modifier;
        newSquare = board.getSquare(newCoordinate);
        while(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))) {
            distanceModifier++;
            newCoordinate = kingSquare + distanceModifier * modifier;
            newSquare = board.getSquare(newCoordinate);
        }
        return checkForThreat(board, distanceModifier * modifier, pieceType);
    }

    public ArrayList<Move> generateAllPossibleMoves(Board board){
        allPossibleMoves = new ArrayList<>();
        ArrayList<Move> currentPieceMoves;
        for(Piece piece : materialInPlay) {
            currentPieceMoves = piece.getPossibleMoves(board);
            for(Move move : currentPieceMoves) {
                if(kingIsSafe(move, board)) {
                    allPossibleMoves.add(move);
                }
            }
        }
        return allPossibleMoves;
    }

    public void makeMove(Move move, Board board) {
        int coordinateMovedFrom = move.getCoordinateMovedFrom();
        int coordinateMovedTo = move.getCoordinateMovedTo();
        Square squareMovedTo = board.getSquare(coordinateMovedTo);
        Square squareMovedFrom = board.getSquare(coordinateMovedFrom);
        Piece pieceMoved = move.getPieceMoved();

        squareMovedTo.setPiece(pieceMoved);
        pieceMoved.setCoordinate(coordinateMovedTo);
        squareMovedFrom.removePiece();
    }

    public void undoMove(Move move, Board board) {
        int coordinateMovedFrom = move.getCoordinateMovedFrom();
        int coordinateMovedTo = move.getCoordinateMovedTo();
        Square squareMovedTo = board.getSquare(coordinateMovedTo);
        Square squareMovedFrom = board.getSquare(coordinateMovedFrom);
        Piece pieceMoved = move.getPieceMoved();

        if(move.isCapture()) {
            Piece oldPiece = move.getCapturedPiece();
            squareMovedTo.setPiece(oldPiece);
            pieceMoved.setCoordinate(coordinateMovedFrom);
            squareMovedFrom.setPiece(pieceMoved);
        }else {
            squareMovedFrom.setPiece(pieceMoved);
            pieceMoved.setCoordinate(coordinateMovedFrom);
            squareMovedTo.removePiece();
        }
    }
}
