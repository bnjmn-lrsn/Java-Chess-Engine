package com.chess.engine.player;
import java.util.ArrayList;

import com.chess.engine.pieces.*;
import com.chess.engine.board.*;

public class Player {
    private ArrayList<Piece> materialInPlay;
    private ArrayList<Piece> capturedMaterial;
    private ArrayList<Move> allPossibleMoves;
    private final Colour myColour;
    private Piece king;
    private boolean kingSafe;

    public Player(Colour myColour, Board board) {
        this.myColour = myColour;
        materialInPlay = new ArrayList<Piece>();
        if(myColour == Colour.WHITE) {
            materialInPlay = board.getWhitePieceSet();
        }else {
            materialInPlay = board.getBlackPieceSet();
        }
        king = materialInPlay.get(materialInPlay.size() - 1);
        capturedMaterial = new ArrayList<Piece>();
    }

    public Colour getPlayerColour() {
        return myColour;
    }

    public ArrayList<Piece> getMaterialInPlay(){
        return materialInPlay;
    }

    public void setMaterialInPlay(ArrayList<Piece> material) {
        materialInPlay = material;
    }

    public boolean kingIsSafe(Move move, Board board) {
        //update the board with the new move
        makeMove(move, board);

        //check for threats
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
        int diagonalModifiers[] = {-11, -9, 9, 11};
        int kingSquare = king.getCoordinate();
        int distanceModifier = 1;
        Square newSquare;
        int newCoordinate;
        Piece attackingPiece;

        for(int modifier : diagonalModifiers) {
            newCoordinate = kingSquare + distanceModifier * modifier;
            newSquare = board.getSquare(newCoordinate);
            while(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))) {
                distanceModifier++;
                newCoordinate = kingSquare + distanceModifier * modifier;
                newSquare = board.getSquare(newCoordinate);
            }
            if(board.isValidSquare(newCoordinate) &&
                    ((newSquare.isOccupied()) &&
                            (newSquare.getPiece().getColour() != myColour))){
                attackingPiece = newSquare.getPiece();
                if(attackingPiece.getPieceType().equals("Bishop") || attackingPiece.getPieceType().equals("Queen")) {
                    return true;
                }
            }
            distanceModifier = 1;
        }
        return false;
    }
    private boolean checkForRankFileThreats(Board board) {
        int rankFileModifiers[] = {-10, -1, 1, 10};
        int kingSquare = king.getCoordinate();
        int distanceModifier = 1;
        Square newSquare;
        int newCoordinate;
        Piece attackingPiece;

        for(int modifier : rankFileModifiers) {
            newCoordinate = kingSquare + distanceModifier * modifier;
            newSquare = board.getSquare(newCoordinate);
            while(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))) {
                distanceModifier++;
                newCoordinate = kingSquare + distanceModifier * modifier;
                newSquare = board.getSquare(newCoordinate);
            }
            if(board.isValidSquare(newCoordinate) &&
                    ((newSquare.isOccupied()) &&
                            (newSquare.getPiece().getColour() != myColour))){
                attackingPiece = newSquare.getPiece();
                if(attackingPiece.getPieceType().equals("Rook") || attackingPiece.getPieceType().equals("Queen")) {
                    return true;
                }
            }
            distanceModifier = 1;
        }
        return false;
    }
    private boolean checkForKnightThreats(Board board) {
        int knightModifiers[] = {-21, -19, -12, -8, 8, 12, 19, 21};
        return kingSafe;
    }
    private boolean checkForPawnThreats(Board board) {
        return kingSafe;
    }
    private boolean checkForOpposingKing(Board board) {
        return kingSafe;
    }

    public ArrayList<Move> generateAllPossibleMoves(Board board){
        allPossibleMoves = new ArrayList<Move>();
        ArrayList<Move> currentPieceMoves = new ArrayList<Move>();
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

        if(move.isCapture()){
            //Piece oldPiece = squareMovedTo.getPiece();
            squareMovedTo.setPiece(pieceMoved);
            pieceMoved.setCoordinate(coordinateMovedTo);
            squareMovedFrom.removePiece();
        }else {
            squareMovedTo.setPiece(pieceMoved);
            pieceMoved.setCoordinate(coordinateMovedTo);
            squareMovedFrom.removePiece();
        }
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
