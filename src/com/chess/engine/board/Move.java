package com.chess.engine.board;

import com.chess.engine.pieces.*;

public abstract class Move {
    protected final int coordinateMovedFrom;
    protected final int coordinateMovedTo;
    protected final Piece pieceMoved;

    public Move(int coordinateMovedFrom, int coordinateMovedTo,
                Piece pieceMoved) {
        this.coordinateMovedFrom = coordinateMovedFrom;
        this.coordinateMovedTo =coordinateMovedTo;
        this.pieceMoved = pieceMoved;
    }
    public Move(int coordinateMovedFrom, int coordinateMovedTo) {
        pieceMoved = null;
        this.coordinateMovedFrom = coordinateMovedFrom;
        this.coordinateMovedTo = coordinateMovedTo;
    }

    public abstract boolean isCapture();

    public abstract Piece getCapturedPiece();

    public int getCoordinateMovedFrom() {
        return coordinateMovedFrom;
    }

    public int getCoordinateMovedTo() {
        return coordinateMovedTo;
    }

    public Piece getPieceMoved() {
        return pieceMoved;
    }

    public static final class PieceMove extends Move {

        public PieceMove(int coordinateMovedFrom, int coordinateMovedTo,
                         Piece pieceMoved) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
        }

        @Override
        public boolean isCapture() {
            return false;
        }

        @Override
        public Piece getCapturedPiece() {
            return null;
        }

        public String toString() {
            return pieceMoved.getColour() + " " + pieceMoved.getPieceType() + " moved from "
                    + Board.algebraicCoordinates.get(this.coordinateMovedFrom) + " to "
                    + Board.algebraicCoordinates.get(this.coordinateMovedTo);
        }
    }

    public static final class Capture extends Move {

        private final Piece pieceCaptured;

        public Capture(int coordinateMovedFrom, int coordinateMovedTo,
                       Piece pieceMoved, Piece pieceCaptured) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.pieceCaptured = pieceCaptured;
        }

        @Override
        public boolean isCapture() {
            return true;
        }

        @Override
        public Piece getCapturedPiece() {
            return pieceCaptured;
        }

        public String toString() {
           return pieceMoved.getColour() + " " + pieceMoved.getPieceType() + " moved from "
                    + Board.algebraicCoordinates.get(this.coordinateMovedFrom) + " to "
                    + Board.algebraicCoordinates.get(this.coordinateMovedTo) +
                    " and captured " + this.pieceCaptured.getColour()
                    + " " + this.pieceCaptured.getPieceType();

            /*return pieceMoved.getPieceType().equals("Pawn") ?
                    Board.algebraicCoordinates.get(this.coordinateMovedFrom) + " "
                    + Board.algebraicCoordinates.get(this.coordinateMovedTo) :
                    pieceMoved + Board.algebraicCoordinates.get(this.coordinateMovedFrom) + " "
                    + pieceMoved + Board.algebraicCoordinates.get(this.coordinateMovedTo);*/
        }

    }

    public static final class Promotion extends Move {

        Piece piecePromotedTo;

        public Promotion(int coordinateMovedFrom, int coordinateMovedTo,
                         Piece pieceMoved, Piece piecePromotedTo) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.piecePromotedTo = piecePromotedTo;
        }

        @Override
        public boolean isCapture() { return false; }

        @Override
        public Piece getCapturedPiece() { return null; }

        public String toString(){
            return "";
        }
    }

    public static final class PromotionWithCapture extends Move {

        Piece pieceCaptured;
        Piece piecePromotedTo;

        public PromotionWithCapture(int coordinateMovedFrom, int coordinateMovedTo,
                                    Piece pieceMoved, Piece pieceCaptured, Piece piecePromotedTo) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.pieceCaptured = pieceCaptured;
            this.piecePromotedTo = piecePromotedTo;
        }

        @Override
        public boolean isCapture() { return true; }

        @Override
        public Piece getCapturedPiece() { return pieceCaptured; }

        public String toString(){
            return "";
        }
    }
}
