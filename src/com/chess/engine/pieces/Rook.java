package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class Rook extends Piece {
    private final int[] coordinateModifiers = {-10, -1, 1, 10};

    public Rook(int coordinate, Alliance alliance) {
        super(coordinate, alliance);
        this.materialValue = 500;
        this.pieceType = "Rook";
    }

    public String toString() {
        return "R";
    }

    public ArrayList<Move> getPossibleMoves(Board board){
        this.possibleMoves = new ArrayList<>();
        int distanceModifier = 1;
        int newCoordinate;
        Square newSquare;
        for (int coordinateModifier : this.coordinateModifiers) {
            newCoordinate = this.coordinate + distanceModifier * coordinateModifier;
            newSquare = board.getSquare(newCoordinate);
            while (board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))) {
                Move move = new Move.PieceMove(this.coordinate, newCoordinate, this);
                this.possibleMoves.add(move);
                distanceModifier++;
                newCoordinate = this.coordinate + distanceModifier * coordinateModifier;
                newSquare = board.getSquare(newCoordinate);
            }
            if (board.isValidSquare(newCoordinate) &&
                    ((newSquare.isOccupied()) &&
                            (newSquare.getPiece().getAlliance() != this.alliance))) {
                Move move = new Move.Capture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                this.possibleMoves.add(move);
            }
            distanceModifier = 1;
        }
        return this.possibleMoves;
    }

}
