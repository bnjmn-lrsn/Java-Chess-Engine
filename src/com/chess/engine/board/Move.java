package com.chess.engine.board;

import com.chess.engine.pieces.*;
import com.chess.engine.player.Alliance;

import java.lang.Math;

public abstract class Move {
    protected final int coordinateMovedFrom;
    protected final int coordinateMovedTo;
    protected final Piece pieceMoved;
    protected int moveEvaluation;

    public Move(int coordinateMovedFrom, int coordinateMovedTo,
                Piece pieceMoved) {
        this.coordinateMovedFrom = coordinateMovedFrom;
        this.coordinateMovedTo =coordinateMovedTo;
        this.pieceMoved = pieceMoved;
        this.moveEvaluation = 0;
    }

    public abstract boolean isCapture();

    public abstract boolean isPawnJump();

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

    public void setMoveEvaluation(int moveEvaluation){
        this.moveEvaluation = moveEvaluation;
    }

    public int getMoveEvaluation(){
        return this.moveEvaluation;
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
        public boolean isPawnJump() {
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
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            squareMovedFrom.setPiece(this.pieceMoved);
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
        public boolean isPawnJump() {
            return false;
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
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(coordinateMovedFrom);
            squareMovedTo.setPiece(this.pieceCaptured);
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
        public boolean isPawnJump() {
            return Math.abs(this.coordinateMovedFrom - this.coordinateMovedTo) == 20;
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
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            squareMovedFrom.setPiece(this.pieceMoved);
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
        public boolean isPawnJump() {
            return false;
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
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            squareMovedTo.setPiece(this.pieceCaptured);
            squareMovedFrom.setPiece(this.pieceMoved);
        }

        @Override
        public String toString(){
            return Board.algebraicCoordinates.get(this.coordinateMovedFrom).charAt(0) + "x" +
                    Board.algebraicCoordinates.get(this.coordinateMovedTo);
        }
    }

    public static final class EnPassantMove extends Move {
        private final Piece pieceCaptured;
        private final int capturePawnCoordinate;

        public EnPassantMove(int coordinateMovedFrom, int coordinateMovedTo, int capturedPawnCoordinate,
                             Piece pieceMoved, Piece pieceCaptured) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
            this.pieceCaptured = pieceCaptured;
            this.capturePawnCoordinate = capturedPawnCoordinate;
        }

        @Override
        public boolean isCapture() {
            return true;
        }

        @Override
        public boolean isPawnJump() {
            return false;
        }

        @Override
        public Piece getCapturedPiece() {
            return this.pieceCaptured;
        }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            Square capturePawnSquare = board.getSquare(this.capturePawnCoordinate);
            squareMovedTo.setPiece(this.pieceMoved);
            squareMovedFrom.removePiece();
            capturePawnSquare.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            Square capturePawnSquare = board.getSquare(this.capturePawnCoordinate);
            squareMovedFrom.setPiece(this.pieceMoved);
            squareMovedTo.removePiece();
            capturePawnSquare.setPiece(this.pieceCaptured);
        }
        @Override
        public String toString(){
            return Board.algebraicCoordinates.get(this.coordinateMovedFrom).charAt(0) + "x" +
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
        public boolean isPawnJump() {
            return false;
        }

        @Override
        public Piece getCapturedPiece() { return null; }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            squareMovedTo.setPiece(this.piecePromotedTo);
            this.pieceMoved.setCoordinate(-1);
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            squareMovedFrom.setPiece(this.pieceMoved);
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
        public boolean isPawnJump() {
            return false;
        }

        @Override
        public Piece getCapturedPiece(){ return this.pieceCaptured; }

        public Piece getPiecePromotedTo(){ return this.piecePromotedTo; }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            squareMovedTo.setPiece(this.piecePromotedTo);
            this.pieceMoved.setCoordinate(-1);
            squareMovedFrom.removePiece();
        }

        @Override
        public void undoMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            squareMovedFrom.setPiece(this.pieceMoved);
            this.piecePromotedTo.setCoordinate(-1);
            squareMovedTo.setPiece(this.pieceCaptured);
        }

        @Override
        public String toString(){
            return Board.algebraicCoordinates.get(this.coordinateMovedFrom).charAt(0) +
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
        public boolean isPawnJump() {
            return false;
        }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareRookMovedTo = board.getSquare(this.coordinateRookMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            Square squareRookMovedFrom = board.getSquare(this.coordinateRookMovedFrom);
            squareMovedTo.setPiece(this.pieceMoved);
            squareRookMovedTo.setPiece(this.rookMoved);
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
            squareRookMovedFrom.setPiece(this.rookMoved);
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
        public boolean isPawnJump() {
            return false;
        }

        @Override
        public void makeMove(Board board) {
            Square squareMovedTo = board.getSquare(this.coordinateMovedTo);
            Square squareRookMovedTo = board.getSquare(this.coordinateRookMovedTo);
            Square squareMovedFrom = board.getSquare(this.coordinateMovedFrom);
            Square squareRookMovedFrom = board.getSquare(this.coordinateRookMovedFrom);
            squareMovedTo.setPiece(this.pieceMoved);
            squareRookMovedTo.setPiece(this.rookMoved);
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
            squareRookMovedFrom.setPiece(this.rookMoved);
            squareMovedTo.removePiece();
            squareRookMovedTo.removePiece();
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }
    public static class NullMove extends Move{
        private static NullMove NULL_MOVE_INSTANCE = new NullMove(0, 0,
                new King(-1, Alliance.BLACK));

        private NullMove(int coordinateMovedFrom, int coordinateMovedTo, Piece pieceMoved) {
            super(coordinateMovedFrom, coordinateMovedTo, pieceMoved);
        }

        public static NullMove getInstance(){
            return NULL_MOVE_INSTANCE;
        }

        @Override
        public boolean isCapture() {
            return false;
        }

        @Override
        public boolean isPawnJump() {
            return false;
        }

        @Override
        public Piece getCapturedPiece() {
            return null;
        }

        @Override
        public void makeMove(Board board) {

        }

        @Override
        public void undoMove(Board board) {

        }

        @Override
        public String toString(){
            return "NULL_MOVE";
        }
    }

    public static class MoveFactory{
        private MoveFactory(){

        }
        public static Move createMove(Board board, final int squareMovedFrom, final int squareMovedTo){
            Move moveToCreate;
            for(Move move : board.getCurrentMoveMaker().generateAllPossibleMoves(board)){
                if(move.getCoordinateMovedFrom() == squareMovedFrom &&
                   move.getCoordinateMovedTo() == squareMovedTo){
                    moveToCreate = move;
                    return moveToCreate;
                }
            }
            return NullMove.getInstance();
        }
    }
}