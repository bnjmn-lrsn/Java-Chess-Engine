package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class King extends Piece{
    private final int[] coordinateModifiers = {-11, -10, -9, -1, 1, 9, 10, 11};
    private boolean moved;

    public King(int coordinate, Colour colour) {
        super(coordinate, colour);
        this.materialValue = Integer.MAX_VALUE;
        this.pieceType = "King";
        moved = false;
    }

    /*public String toString() {
        return colour == Colour.BLACK ? "k" : "K";
    }*/
    public String toString() {
        return "K";
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        possibleMoves = new ArrayList<>();
        int newCoordinate;
        for (int coordinateModifier : coordinateModifiers) {
            newCoordinate = coordinate + coordinateModifier;
            Square newSquare = board.getSquare(newCoordinate);
            if (board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))) {
                Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                possibleMoves.add(move);
            } else if (board.isValidSquare(newCoordinate) &&
                    (newSquare.isOccupied()
                            && newSquare.getPiece().getColour() != colour)) {
                Piece pieceToCapture = newSquare.getPiece();
                Move move = new Move.Capture(coordinate, newCoordinate, this, pieceToCapture);
                possibleMoves.add(move);
            }

        }
        return possibleMoves;
    }

    public boolean hasMoved() {
        return moved;
    }
}