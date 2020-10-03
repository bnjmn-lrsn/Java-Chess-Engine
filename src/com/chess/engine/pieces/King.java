package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class King extends Piece{
    private final int[] coordinateModifiers = {-11, -10, -9, -1, 1, 9, 10, 11};

    public King(int coordinate, Alliance alliance) {
        super(coordinate, alliance);
        this.materialValue = Integer.MAX_VALUE;
        this.pieceType = "King";
    }

    public String toString() {
        return "K";
    }

    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        this.possibleMoves = new ArrayList<>();
        int newCoordinate;
        for (int coordinateModifier : this.coordinateModifiers) {
            newCoordinate = this.coordinate + coordinateModifier;
            Square newSquare = board.getSquare(newCoordinate);

            if (board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))) {
                Move move = new Move.PieceMove(this.coordinate, newCoordinate, this);
                this.possibleMoves.add(move);
            }
            else if (board.isValidSquare(newCoordinate) &&
                      (newSquare.isOccupied()
                      && newSquare.getPiece().getAlliance() != this.alliance)) {
                Piece pieceToCapture = newSquare.getPiece();
                Move move = new Move.Capture(this.coordinate, newCoordinate, this, pieceToCapture);
                this.possibleMoves.add(move);
            }
        }

        return this.possibleMoves;
    }
}