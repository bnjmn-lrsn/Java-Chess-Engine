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
        return this.coordinateMovedFrom;
    }

    public int getCoordinateMovedTo() {
        return this.coordinateMovedTo;
    }

    public Piece getPieceMoved() {
        return this.pieceMoved;
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
            /*return pieceMoved.getColour() + " " + pieceMoved.getPieceType() + " moved from "
                    + Board.algebraicCoordinates.get(this.coordinateMovedFrom) + " to "
                    + Board.algebraicCoordinates.get(this.coordinateMovedTo);*/
            return this.pieceMoved + Board.algebraicCoordinates.get(this.coordinateMovedTo);
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
            return this.pieceCaptured;
        }

        public String toString() {
           /*return pieceMoved.getColour() + " " + pieceMoved.getPieceType() + " moved from "
                    + Board.algebraicCoordinates.get(this.coordinateMovedFrom) + " to "
                    + Board.algebraicCoordinates.get(this.coordinateMovedTo) +
                    " and captured " + this.pieceCaptured.getColour()
                    + " " + this.pieceCaptured.getPieceType();*/

           return this.pieceMoved + "x" + Board.algebraicCoordinates.get(this.coordinateMovedTo);
        }

    }

    public static class PawnMove extends Move{

        public PawnMove(int coordinateMovedFrom, int coordinateMovedTo, Piece pieceMoved) {
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

        @Override
        public String toString(){
            return Board.algebraicCoordinates.get(this.coordinateMovedTo);
        }
    }

    public static final class PawnCapture extends PawnMove {
        Piece pieceCaptured;

        public PawnCapture(int coordinateMovedFrom, int coordinateMovedTo,
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
            return this.pieceCaptured;
        }

        @Override
        public String toString(){
            return Board.algebraicCoordinates.get(this.coordinateMovedFrom) + "x" +
                    Board.algebraicCoordinates.get(this.coordinateMovedTo);
        }
    }

    public static final class PawnPromotion extends PawnMove {
        Piece piecePromotedTo;

        public PawnPromotion(int coordinateMovedFrom, int coordinateMovedTo,
                             Piece pieceMoved, Piece piecePromotedTo) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.piecePromotedTo = piecePromotedTo;
        }
    }

    public static class Castle extends Move {
        Rook rookMoved;

        public Castle(int coordinateMovedFrom, int coordinateMovedTo,
                      Piece pieceMoved, Rook rookMoved) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.rookMoved = rookMoved;
        }

        @Override
        public boolean isCapture() {
            return false;
        }

        @Override
        public Piece getCapturedPiece() {
            return null;
        }
    }

    public static final class QueenSideCastle extends Castle {

        public QueenSideCastle(int coordinateMovedFrom, int coordinateMovedTo,
                               Piece pieceMoved, Rook rookMoved) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved, rookMoved);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static final class KingSideCastle extends Castle {

        public KingSideCastle(int coordinateMovedFrom, int coordinateMovedTo,
                              Piece pieceMoved, Rook rookMoved) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved, rookMoved);
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }


}
