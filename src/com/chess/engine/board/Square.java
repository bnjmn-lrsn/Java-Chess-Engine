package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Alliance;

public class Square {
    private Piece piece;
    private boolean occupied;
    private int coordinate;
    private final int index;

    public Square(int coordinate, int index) {
        this.coordinate = coordinate;
        this.occupied = false;
        this.piece = null;
        this.index = index;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }

    public int getCoordinate() {
        return this.coordinate;
    }

    public int getIndex() {
        return index;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        this.occupied = true;
        this.piece.setCoordinate(this.index);
    }
    public Piece getPiece() {
        if(isOccupied()){
            return this.piece;
        }else{
            return null;
        }
    }

    public boolean isOccupied() {
        return this.occupied;
    }

    public void removePiece() {
        this.piece = null;
        this.occupied = false;
    }

    public String toString() {
        if(this.isOccupied()){
            if(this.getPiece().getAlliance() == Alliance.BLACK){
                return "|" + this.piece.toString().toLowerCase() + "|";
            }
            else{
                return "|" + this.piece.toString() +  "|";
            }
        }
        else{
            return "| |";
        }
    }

}
