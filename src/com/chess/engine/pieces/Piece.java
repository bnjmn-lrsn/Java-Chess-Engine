package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public abstract class Piece {
    protected int coordinate;
    protected final Alliance alliance;
    protected int materialValue;
    protected ArrayList<Move> possibleMoves;
    protected String pieceType;
    protected boolean moved;

    public Piece(int coordinate, Alliance alliance) {
        this.coordinate = coordinate;
        this.alliance = alliance;
        possibleMoves = new ArrayList<>();
        materialValue = 0;
        moved = false;
    }

    public Piece(int coordinate, Alliance alliance, int value) {
        this(coordinate, alliance);
        this.materialValue = value;
    }

    public Piece(int coordinate, Alliance alliance, int value, String pieceType) {
        this(coordinate, alliance, value);
        this.pieceType = pieceType;
    }

    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }

    public int getCoordinate() {
        return this.coordinate;
    }

    public int getValue() {
        return this.materialValue;
    }

    public Alliance getAlliance() {
        return this.alliance;
    }

    public String getPieceType() {
        return this.pieceType;
    }

    public boolean hasMoved() { return this.moved; }

    public void setMovedState() {
        this.moved = true;
    }

    public abstract ArrayList<Move> getPossibleMoves(Board board);
}
