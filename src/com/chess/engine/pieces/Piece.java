package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public abstract class Piece {
    protected int coordinate;
    protected final Colour colour;
    protected int materialValue;
    /*
     * Material value reference. Values are measured in centipawns(one pawn = 100 centipawns).
     * Pawn: 100. Can increase depending on the pawn's position, e.g. if the pawn is close to promotion.
     * 		Center pawns are worth slightly more than flank pawns.
     * Knight: 300
     * Bishop: 305 - 310. Knights and bishops are worth roughly the same
     * Queen: 900
     * King: infinite OR 100000
     */
    //DON'T NEED TO CHECK FOR WRAPPING WITH 120 ELEMENT ARRAY!
    //8 x 8 board nested within a 10 x 12 board
    //Pawn: +10 to move forward for white, -10 for black, +/-20 from start, +-11, and +-9 to capture.
    //Knight: -21, -19, -12, -8, 8, 12, 19, 21
    //Bishop: -11, -9, 9, 11
    //Rook: -10, -1, 1, 10
    //Queen: rook + bishop
    //King: -11, -10, -9, -1, 1, 9, 10, 11
    protected ArrayList<Move> possibleMoves;
    protected String pieceType;

    public Piece(int coordinate, Colour colour) {
        this.coordinate = coordinate;
        this.colour = colour;
        possibleMoves = new ArrayList<Move>();
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
