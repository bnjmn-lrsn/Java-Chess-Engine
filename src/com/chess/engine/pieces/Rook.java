package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class Rook extends Piece {
    private final int[] coordinateModifiers = {-10, -1, 1, 10};
    private boolean moved;

    public Rook(int coordinate, Colour colour) {
        super(coordinate, colour);
        this.materialValue = 500;
        this.pieceType = "Rook";
        moved = false;
    }

    /*public String toString() {
        return colour == Colour.BLACK ? "r" : "R";
    }*/

    public String toString() {
        return "R";
    }

    public ArrayList<Move> getPossibleMoves(Board board){
        possibleMoves = new ArrayList<>();
        int distanceModifier = 1;
        int newCoordinate;
        Square newSquare;
        for (int coordinateModifier : coordinateModifiers) {
            newCoordinate = this.coordinate + distanceModifier * coordinateModifier;
            newSquare = board.getSquare(newCoordinate);
            while (board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))) {
                Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                possibleMoves.add(move);
                distanceModifier++;
                newCoordinate = this.coordinate + distanceModifier * coordinateModifier;
                newSquare = board.getSquare(newCoordinate);
            }
            if (board.isValidSquare(newCoordinate) &&
                    ((newSquare.isOccupied()) &&
                            (newSquare.getPiece().getColour() != colour))) {
                Move move = new Move.Capture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                possibleMoves.add(move);
            }
            distanceModifier = 1;
        }
        return possibleMoves;
    }

    public boolean hasMoved() {
        return moved;
    }
}
