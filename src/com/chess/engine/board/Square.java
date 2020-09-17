package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Colour;

public class Square {
    private Piece piece;
    private boolean occupied;
    private int coordinate;

    public Square(int coordinate) {
        this.coordinate = coordinate;
        occupied = false;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        occupied = true;
    }
    public Piece getPiece() {
        if(isOccupied()) {
            return piece;
        }else {
            return null;
        }
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void removePiece() {
        piece = null;
        occupied = false;
    }

    public String toString() {
        if(this.isOccupied()){
            if(this.getPiece().getColour() == Colour.BLACK){
                return "|" + this.piece.toString().toLowerCase() + "|";
            }else {
                return "|" + this.piece.toString() +  "|";
            }
        }else{
            return "| |";
        }
    }

}
