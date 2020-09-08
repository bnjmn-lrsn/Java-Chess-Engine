package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public abstract class Piece {
    protected int coordinate;
    protected final Colour colour;
    protected int materialValue;
    protected ArrayList<Move> possibleMoves;
    protected String pieceType;

    public Piece(int coordinate, Colour colour) {
        this.coordinate = coordinate;
        this.colour = colour;
        possibleMoves = new ArrayList<>();
        materialValue = 0;
    }

    public Piece(int coordinate, Colour colour, int value) {
        this(coordinate, colour);
        materialValue = value;
    }

    public Piece(int coordinate, Colour colour, int value, String pieceType) {
        this(coordinate, colour, value);
        this.pieceType = pieceType;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }

    public int getCoordinate() {
        return coordinate;
    }

    public int getValue() {
        return materialValue;
    }

    public Colour getColour() {
        return colour;
    }

    public String getPieceType() {
        return pieceType;
    }

    public abstract ArrayList<Move> getPossibleMoves(Board board);
}
