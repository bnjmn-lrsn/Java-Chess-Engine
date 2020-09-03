package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class Pawn extends Piece {

    private static int[] whiteCoordinateModifiers = {-20, -11, -10, -9};
    private static int[] blackCoordinateModifiers = {20, 11, 10, 9};

    public Pawn(int coordinate, Colour colour) {
        super(coordinate, colour);
        this.materialValue = 100;
        this.pieceType = "Pawn";
    }

    public String toString() {
        return colour == Colour.BLACK ? "p" : "P";
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        possibleMoves = new ArrayList<Move>();
        int newCoordinate = 0;
        if(colour == Colour.WHITE) {
            if(coordinate >= 81) {
                newCoordinate = coordinate - 20;
                Square newSquare = board.getSquare(newCoordinate);
                Square checkSquare = board.getSquare(coordinate - 10);
                if(board.isValidSquare(newCoordinate)
                        && (!(newSquare.isOccupied())
                        && !(checkSquare.isOccupied()))){
                    Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 10;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))){
                    Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 11;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.Capture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 9;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.Capture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
            }
            else if(coordinate < 81 && coordinate > 38) {
                newCoordinate = coordinate - 10;
                Square newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))){
                    Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 11;
                newSquare = board.getSquare(newCoordinate);
                if(board.isValidSquare(newCoordinate)
                        && (newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != colour)) {
                    Move move = new Move.Capture(coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
                newCoordinate = coordinate - 9;
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
