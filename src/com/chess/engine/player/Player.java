package com.chess.engine.player;

import java.util.ArrayList;

import com.chess.engine.pieces.*;
import com.chess.engine.board.*;

public class Player {
    private ArrayList<Piece> materialInPlay;
    private ArrayList<Move> allPossibleMoves;
    private final int directionModifier;
    private final Alliance alliance;
    private final Piece king;
    private final int[] diagonalModifiers = {-11, -9, 9, 11};
    private final int[] rankFileModifiers = {-10, -1, 1, 10};
    private final int[] knightModifiers = {-21, -19, -12, -8, 8, 12, 19, 21};
    private final int[] kingModifiers = {-11, -10, -9, -1, 1, 9, 10, 11};

    public Player(Alliance alliance, Board board) {
        this.alliance = alliance;
        calculateActivePieces(board);
        this.directionModifier = alliance.getDirectionModifier();
        this.king = this.materialInPlay.get(this.materialInPlay.size() - 1);
    }

    public Alliance getPlayerAlliance() {
        return this.alliance;
    }

    public ArrayList<Piece> getMaterialInPlay(){
        return this.materialInPlay;
    }

    public boolean kingIsSafe(Move move, Board board) {
        makeMove(move, board);

        if(checkForDiagonalThreats(board)) {
            undoMove(move, board);
            return false;
        }else if(checkForRankFileThreats(board)) {
            undoMove(move, board);
            return false;
        }else if(checkForKnightThreats(board)) {
            undoMove(move, board);
            return false;
        }else if(checkForPawnThreats(board)) {
            undoMove(move, board);
            return false;
        }else if(checkForOpposingKing(board)) {
            undoMove(move, board);
            return false;
        }else {
            undoMove(move, board);
            return true;
        }
    }

    public boolean isInCheck(Board board){
        if(checkForDiagonalThreats(board)) {
            return true;
        }else if(checkForRankFileThreats(board)) {
            return true;
        }else if(checkForKnightThreats(board)) {
            return true;
        }else if(checkForPawnThreats(board)) {
            return true;
        }else if(checkForOpposingKing(board)) {
            return true;
        }else {
            return false;
        }
    }

    private boolean checkForDiagonalThreats(Board board) {
        for(int modifier : this.diagonalModifiers){
            if(checkForDistantThreat(board, modifier, "Bishop")
                    || checkForDistantThreat(board, modifier, "Queen")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForRankFileThreats(Board board) {
        for(int modifier : this.rankFileModifiers){
            if(checkForDistantThreat(board, modifier, "Rook")
                    || checkForDistantThreat(board, modifier, "Queen")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForKnightThreats(Board board) {
        for(int modifier : this.knightModifiers){
            if(checkForThreat(board, modifier, "Knight")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForPawnThreats(Board board) {
        return checkForThreat(board, (directionModifier * 11), "Pawn")
                || (checkForThreat(board, (directionModifier * 9), "Pawn"));
    }

    private boolean checkForOpposingKing(Board board){
        for(int modifier : this.kingModifiers) {
            if(checkForThreat(board, modifier, "King")){
                return true;
            }
        }
        return false;
    }

    private boolean checkForThreat(Board board, int modifier, String pieceType) {
        int kingSquare = this.king.getCoordinate();
        int newCoordinate;
        Square newSquare;
        Piece attackingPiece;

        newCoordinate = kingSquare + modifier;
        if(board.isValidSquare(newCoordinate)){
            newSquare = board.getSquare(newCoordinate);
            if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                attackingPiece = newSquare.getPiece();
                return attackingPiece.getPieceType().equals(pieceType);
            }
        }
        return false;
    }

    private boolean checkForDistantThreat(Board board, int modifier, String pieceType) {
        int distanceModifier = 1;
        int kingSquare = this.king.getCoordinate();
        int newCoordinate;
        Square newSquare;

        newCoordinate = kingSquare + distanceModifier * modifier;
        newSquare = board.getSquare(newCoordinate);
        while(board.isValidSquare(newCoordinate) && (!(newSquare.isOccupied()))){
            distanceModifier++;
            newCoordinate = kingSquare + distanceModifier * modifier;
            newSquare = board.getSquare(newCoordinate);
        }
        return checkForThreat(board, distanceModifier * modifier, pieceType);
    }

    private ArrayList<Move> generateCastlingMoves(Board board){
        ArrayList<Move> castlingMoves = new ArrayList<>();

        Square newSquare;
        Piece piece;
        Move move;
        int rookCoordinate;
        int transitionCoordinate, destinationCoordinate, queenKnightCoordinate;
        int kingStartCoordinate = this.alliance.getKingStartingCoordinate();

        if(!this.king.hasMoved() && !isInCheck(board)){
            //king-side castling
            rookCoordinate = this.alliance.getKingSideRookCoordinate();
            newSquare = board.getSquare(rookCoordinate);
            if(newSquare.isOccupied()){
                piece = newSquare.getPiece();
                if((piece.getPieceType().equals("Rook") && !piece.hasMoved())
                        && piece.getAlliance() == this.alliance){
                    transitionCoordinate = this.alliance.getKingSideTransitionCoordinate();
                    destinationCoordinate = this.alliance.getKingSideCastlingCoordinate();
                    if(!board.getSquare(transitionCoordinate).isOccupied() && !board.getSquare(destinationCoordinate).isOccupied()){
                        board.getSquare(transitionCoordinate).setPiece(this.king);
                        this.king.setCoordinate(transitionCoordinate);
                        board.getSquare(kingStartCoordinate).removePiece();
                        if(!isInCheck(board)){
                            board.getSquare(transitionCoordinate).removePiece();
                            board.getSquare(destinationCoordinate).setPiece(this.king);
                            this.king.setCoordinate(destinationCoordinate);
                            if(!isInCheck(board)){
                                this.king.setCoordinate(kingStartCoordinate);
                                board.getSquare(kingStartCoordinate).setPiece(this.king);
                                board.getSquare(destinationCoordinate).removePiece();
                                move = new Move.KingSideCastle(kingStartCoordinate, destinationCoordinate, this.king,
                                        rookCoordinate, transitionCoordinate, piece);
                                castlingMoves.add(move);
                            }else{
                                this.king.setCoordinate(kingStartCoordinate);
                                board.getSquare(destinationCoordinate).removePiece();
                                board.getSquare(kingStartCoordinate).setPiece(this.king);
                            }
                        }else{
                            this.king.setCoordinate(kingStartCoordinate);
                            board.getSquare(transitionCoordinate).removePiece();
                            board.getSquare(kingStartCoordinate).setPiece(this.king);
                        }
                    }
                }
            }
            //queen-side castling
            rookCoordinate = this.alliance.getQueenSideRookCoordinate();
            newSquare = board.getSquare(rookCoordinate);
            if(newSquare.isOccupied()) {
                piece = newSquare.getPiece();
                if ((piece.getPieceType().equals("Rook") && !piece.hasMoved())
                        && piece.getAlliance() == this.alliance) {
                    transitionCoordinate = this.alliance.getQueenSideTransitionCoordinate();
                    destinationCoordinate = this.alliance.getQueenSideCastlingCoordinate();
                    queenKnightCoordinate = this.alliance.getQueenKnightCoordinate();
                    if(!board.getSquare(transitionCoordinate).isOccupied() && !board.getSquare(destinationCoordinate).isOccupied()
                            && !board.getSquare(queenKnightCoordinate).isOccupied()){
                        board.getSquare(transitionCoordinate).setPiece(this.king);
                        this.king.setCoordinate(transitionCoordinate);
                        board.getSquare(kingStartCoordinate).removePiece();
                        if(!isInCheck(board)){
                            board.getSquare(destinationCoordinate).setPiece(this.king);
                            this.king.setCoordinate(destinationCoordinate);
                            board.getSquare(transitionCoordinate).removePiece();
                            if(!isInCheck(board)){
                                this.king.setCoordinate(kingStartCoordinate);
                                board.getSquare(kingStartCoordinate).setPiece(this.king);
                                board.getSquare(destinationCoordinate).removePiece();
                                move = new Move.QueenSideCastle(kingStartCoordinate, destinationCoordinate, this.king,
                                        rookCoordinate, transitionCoordinate, piece);
                                castlingMoves.add(move);
                            }else{
                                this.king.setCoordinate(kingStartCoordinate);
                                board.getSquare(kingStartCoordinate).setPiece(this.king);
                                board.getSquare(destinationCoordinate).removePiece();
                            }
                        }else{
                            board.getSquare(kingStartCoordinate).setPiece(this.king);
                            this.king.setCoordinate(kingStartCoordinate);
                            board.getSquare(transitionCoordinate).removePiece();
                        }
                    }
                }
            }
        }
        return castlingMoves;
    }

    private void calculateActivePieces(Board board){
        this.materialInPlay = new ArrayList<>();
        Square sq;
        for(int i = 0; i < board.getBoardSize(); ++i){
            if(board.isValidSquare(i)){
                sq = board.getSquare(i);
                if(sq.isOccupied() && sq.getPiece().getAlliance() == this.alliance){
                    this.materialInPlay.add(sq.getPiece());
                }
            }
        }
    }

    public ArrayList<Move> generateAllPossibleMoves(Board board) {
        calculateActivePieces(board);
        this.allPossibleMoves = new ArrayList<>();
        ArrayList<Move> currentPieceMoves;
        for(Piece piece : this.materialInPlay){
            currentPieceMoves = piece.getPossibleMoves(board);
            for(Move move : currentPieceMoves){
                if(kingIsSafe(move, board)){
                    this.allPossibleMoves.add(move);
                }
            }
        }
        if(!this.king.hasMoved()){
            this.allPossibleMoves.addAll(generateCastlingMoves(board));
        }
        return this.allPossibleMoves;
    }

    public void makeMove(Move move, Board board) {
        move.makeMove(board);
    }

    public void undoMove(Move move, Board board) {
        move.undoMove(board);
    }
}