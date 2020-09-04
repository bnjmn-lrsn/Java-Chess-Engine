package com.chess.engine.pieces;
import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class Bishop extends Piece {
    private final int[] coordinateModifiers = {-11, -9, 9, 11};

    public Bishop(int coordinate, Colour colour) {
        super(coordinate, colour);
        this.materialValue = 305;
        this.pieceType = "Bishop";
    }

    public String toString() {
        return colour == Colour.BLACK ? "b" : "B";
    }

    public ArrayList<Move> getPossibleMoves(Board board){
        possibleMoves = new ArrayList<>();
        int distanceModifier = 1;
        int newCoordinate;
        Square newSquare;
        for (int coordinateModifier : coordinateModifiers) {
            newCoordinate = coordinate + distanceModifier * coordinateModifier;
            newSquare = board.getSquare(newCoordinate);
            while (board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))) {
                Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                possibleMoves.add(move);
                distanceModifier++;
                newCoordinate = coordinate + distanceModifier * coordinateModifier;
                newSquare = board.getSquare(newCoordinate);
            }
            if (board.isValidSquare(newCoordinate) &&
                    ((newSquare.isOccupied()) &&
                            (newSquare.getPiece().getColour() != colour))) {
                Move move = new Move.Capture(coordinate, newCoordinate, this, newSquare.getPiece());
                possibleMoves.add(move);
            }
            distanceModifier = 1;
        }
        return possibleMoves;
    }
}
