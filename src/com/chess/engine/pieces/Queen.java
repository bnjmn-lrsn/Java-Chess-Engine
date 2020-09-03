package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;


public class Queen extends Piece {
    private final int coordinateModifiers[] = {-11, -10, -9, -1, 1, 9, 10, 11};

    public Queen(int coordinate, Colour colour) {
        super(coordinate, colour);
        this.materialValue = 900;
        this.pieceType = "Queen";
    }

    public String toString() {
        return colour == Colour.BLACK ? "q" : "Q";
    }

    public ArrayList<Move> getPossibleMoves(Board board){
        possibleMoves = new ArrayList<Move>();
        int distanceModifier = 1;
        int newCoordinate = 0;
        Square newSquare;
        for(int i = 0; i < coordinateModifiers.length; ++i) {
            newCoordinate = this.coordinate + distanceModifier * coordinateModifiers[i];
            newSquare = board.getSquare(newCoordinate);
            while(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))){
                Move move = new Move.PieceMove(coordinate, newCoordinate, this);
                possibleMoves.add(move);
                distanceModifier++;
                newCoordinate = this.coordinate +  distanceModifier * coordinateModifiers[i];
                newSquare = board.getSquare(newCoordinate);
            }
            if(board.isValidSquare(newCoordinate) &&
                    ((newSquare.isOccupied()) &&
                            (newSquare.getPiece().getColour() != colour))){
                Move move = new Move.Capture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                possibleMoves.add(move);
            }
            distanceModifier = 1;
        }
        return possibleMoves;
    }

}
