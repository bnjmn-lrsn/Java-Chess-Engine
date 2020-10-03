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

    public abstract boolean isCapture();

    public abstract Piece getCapturedPiece();

    public abstract void makeMove(Board board);

    public abstract void undoMove(Board board);

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

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedTo.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedTo);
            squareMovedFrom.removePiece();

        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedFrom.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedFrom);
            squareMovedTo.removePiece();
        }

        @Override
        public String toString() {
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

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedTo.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedTo);
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(coordinateMovedFrom);

            squareMovedTo.setPiece(this.pieceCaptured);
            this.pieceMoved.setCoordinate(this.coordinateMovedFrom);
            squareMovedFrom.setPiece(this.pieceMoved);
        }

        @Override
        public String toString() {
           return this.pieceMoved + "x" + Board.algebraicCoordinates.get(this.coordinateMovedTo);
        }
    }

    public static class PawnMove extends Move {

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
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedTo.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedTo);
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedFrom.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedFrom);
            squareMovedTo.removePiece();
        }

        @Override
        public String toString(){
            return Board.algebraicCoordinates.get(this.coordinateMovedTo);
        }
    }

    public static final class PawnCapture extends Move {

        private final Piece pieceCaptured;

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
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedTo.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedTo);
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedTo.setPiece(this.pieceCaptured);
            this.pieceMoved.setCoordinate(this.coordinateMovedFrom);
            squareMovedFrom.setPiece(this.pieceMoved);
        }

        @Override
        public String toString(){
            return Board.algebraicCoordinates.get(this.coordinateMovedFrom) + "x" +
                    Board.algebraicCoordinates.get(this.coordinateMovedTo);
        }
    }

    public static final class PawnPromotion extends Move {

        private final Piece piecePromotedTo;

        public PawnPromotion(int coordinateMovedFrom, int coordinateMovedTo,
                             Piece pieceMoved, Piece piecePromotedTo) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.piecePromotedTo = piecePromotedTo;
        }

        public Piece getPiecePromotedTo(){ return this.piecePromotedTo; }

        @Override
        public boolean isCapture() { return false; }

        @Override
        public Piece getCapturedPiece() { return null; }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedTo.setPiece(this.piecePromotedTo);
            this.piecePromotedTo.setCoordinate(this.coordinateMovedTo);
            this.pieceMoved.setCoordinate(-1);
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedFrom.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedFrom);
            squareMovedTo.removePiece();
            this.piecePromotedTo.setCoordinate(-1);
        }

        @Override
        public String toString() {
            return Board.algebraicCoordinates.get(this.coordinateMovedTo) + "=" + this.piecePromotedTo;
        }
    }

    public static final class PawnPromotionWithCapture extends Move {
        private final Piece piecePromotedTo;
        private final Piece pieceCaptured;

        public PawnPromotionWithCapture(int coordinateMovedFrom, int coordinateMovedTo,
                                        Piece pieceMoved, Piece pieceCaptured, Piece piecePromotedTo) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.pieceCaptured = pieceCaptured;
            this.piecePromotedTo = piecePromotedTo;
        }

        @Override
        public boolean isCapture() { return true; }

        @Override
        public Piece getCapturedPiece(){ return this.pieceCaptured; }

        public Piece getPiecePromotedTo(){ return this.piecePromotedTo; }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedTo.setPiece(this.piecePromotedTo);
            this.piecePromotedTo.setCoordinate(this.coordinateMovedTo);
            this.pieceMoved.setCoordinate(-1);
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);

            squareMovedFrom.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedFrom);
            this.piecePromotedTo.setCoordinate(-1);
            squareMovedTo.setPiece(this.pieceCaptured);
            this.pieceCaptured.setCoordinate(this.coordinateMovedTo);
        }

        @Override
        public String toString(){
            return Board.algebraicCoordinates.get(this.coordinateMovedFrom) +
                   "x" + Board.algebraicCoordinates.get(this.coordinateMovedTo) +
                   "=" + this.piecePromotedTo;
        }
    }

    public static abstract class Castle extends Move {

        Piece rookMoved;
        protected final int coordinateRookMovedFrom;
        protected final int coordinateRookMovedTo;

        public Castle(int coordinateMovedFrom, int coordinateMovedTo, Piece pieceMoved,
                      int coordinateRookMovedFrom, int coordinateRookMovedTo, Piece rookMoved) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.coordinateRookMovedFrom = coordinateRookMovedFrom;
            this.coordinateRookMovedTo = coordinateRookMovedTo;
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


        public Piece getRookMoved(){ return this.rookMoved; }
    }

    public static final class QueenSideCastle extends Castle {

        public QueenSideCastle(int coordinateMovedFrom, int coordinateMovedTo, Piece pieceMoved,
                               int coordinateRookMovedFrom, int coordinateRookMovedTo, Piece rookMoved) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved,
                  coordinateRookMovedFrom, coordinateRookMovedTo, rookMoved);
        }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareRookMovedTo = board.getSquare(this.coordinateRookMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            Square squareRookMovedFrom = board.getSquare(this.coordinateRookMovedFrom);

            squareMovedTo.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedTo);
            squareRookMovedTo.setPiece(this.rookMoved);
            this.rookMoved.setCoordinate(this.coordinateRookMovedTo);
            squareMovedFrom.removePiece();
            squareRookMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareRookMovedTo = board.getSquare(this.coordinateRookMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            Square squareRookMovedFrom = board.getSquare(this.coordinateRookMovedFrom);

            squareMovedFrom.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedFrom);
            squareRookMovedFrom.setPiece(this.rookMoved);
            this.rookMoved.setCoordinate(this.coordinateRookMovedFrom);
            squareMovedTo.removePiece();
            squareRookMovedTo.removePiece();
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static final class KingSideCastle extends Castle {

        public KingSideCastle(int coordinateMovedFrom, int coordinateMovedTo, Piece pieceMoved,
                              int coordinateRookMovedFrom, int coordinateRookMovedTo, Piece rookMoved) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved,
                  coordinateRookMovedFrom, coordinateRookMovedTo, rookMoved);
        }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareRookMovedTo = board.getSquare(this.coordinateRookMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            Square squareRookMovedFrom = board.getSquare(this.coordinateRookMovedFrom);

            squareMovedTo.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedTo);
            squareRookMovedTo.setPiece(this.rookMoved);
            this.rookMoved.setCoordinate(this.coordinateRookMovedTo);
            squareMovedFrom.removePiece();
            squareRookMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareRookMovedTo = board.getSquare(this.coordinateRookMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            Square squareRookMovedFrom = board.getSquare(this.coordinateRookMovedFrom);

            squareMovedFrom.setPiece(this.pieceMoved);
            this.pieceMoved.setCoordinate(this.coordinateMovedFrom);
            squareRookMovedFrom.setPiece(this.rookMoved);
            this.rookMoved.setCoordinate(this.coordinateRookMovedFrom);
            squareMovedTo.removePiece();
            squareRookMovedTo.removePiece();
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }
}