package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class Pawn extends Piece {

    public Pawn(int coordinate, Colour colour) {
        super(coordinate, colour);
        this.materialValue = 100;
        this.pieceType = "Pawn";
    }

    /*public String toString() {
        return colour == Colour.BLACK ? "p" : "P";
    }*/
    public String toString() {
        return "P";
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        possibleMoves = new ArrayList<>();
        int newCoordinate;
        if(colour == Colour.WHITE) {
            if(coordinate >= 81) {
                newCoordinate = coordinate - 20;
                Square newSquare = board.getSquare(newCoordinate);
                Square checkSquare = board.getSquare(coordinate - 10);
                if(board.isValidSquare(newCoordinate)
                        && (!(newSquare.isOccupied())
                        && !(checkSquare.isOccupied()))){
                    Move move = new Move.PawnMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 10;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))){
                    Move move = new Move.PawnMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 11;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.PawnCapture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 9;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.PawnCapture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
            }
            else if(coordinate < 81 && coordinate > 38) {
                newCoordinate = coordinate - 10;
                Square newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))){
                    Move move = new Move.PawnMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 11;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.PawnCapture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 9;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.PawnCapture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
            }
        }else if(colour == Colour.BLACK){
            if(coordinate <= 38){
                newCoordinate = coordinate + 20;
                Square newSquare = board.getSquare(newCoordinate);
                Square checkSquare = board.getSquare(coordinate + 10);
                if(board.isValidSquare(newCoordinate)
                        && (!(newSquare.isOccupied())
                        && !(checkSquare.isOccupied()))){
                    Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate + 10;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))){
                    Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate + 11;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.Capture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate + 9;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.Capture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
            }
            else if(coordinate > 38 && coordinate < 81) {
                newCoordinate = coordinate + 10;
                Square newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))){
                    Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate + 11;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.Capture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate + 9;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.Capture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
            }
        }
        return possibleMoves;
    }
}
