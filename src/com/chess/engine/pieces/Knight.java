package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class Knight extends Piece{
    private final int[] coordinateModifiers = {-21, -19, -12, -8, 8, 12, 19, 21};

    public Knight(int coordinate, Colour colour) {
        super(coordinate, colour);
        this.pieceType = "Knight";
        this.materialValue = 300;
    }

    /*public String toString() {
        return colour == Colour.BLACK ? "n" : "N";
    }*/
    public String toString(){
        return "N";
    }

    public ArrayList<Move> getPossibleMoves(Board board){
        possibleMoves = new ArrayList<>();
        int newCoordinate;
        Square newSquare;

        for(int coordinateModifier : coordinateModifiers) {
            newCoordinate = this.coordinate + coordinateModifier;
            newSquare = board.getSquare(newCoordinate);
            if(board.isValidSquare(newCoordinate)) {
                if(newSquare.isOccupied()
                        && newSquare.getPiece().getColour() != this.colour) {
                    Move move = new Move.Capture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                    possibleMoves.add(move);
                }
                else if (!(newSquare.isOccupied())) {
                    Move move = new Move.PieceMove(this.coordinate, newCoordinate, this);
                    possibleMoves.add(move);
                }
            }
        }
        return possibleMoves;
    }

}
