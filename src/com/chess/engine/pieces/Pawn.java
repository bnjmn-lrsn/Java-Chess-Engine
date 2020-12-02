package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class Pawn extends Piece {
    private Piece promotionPiece;
    private final int directionModifier;

    public Pawn(int coordinate, Alliance alliance) {
        super(coordinate, alliance);
        this.materialValue = 100;
        this.pieceType = "Pawn";
        this.directionModifier = alliance.getDirectionModifier();
    }

    public String toString() { return "P"; }

    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        this.possibleMoves = new ArrayList<>();
        int newCoordinate;
        Square newSquare;
        Square checkSquare;
        Move move;

        if(this.alliance.isPawnStartingSquare(this.coordinate)){
            newCoordinate = this.coordinate + (this.directionModifier * 20);
            newSquare = board.getSquare(newCoordinate);
            checkSquare = board.getSquare(coordinate + (this.directionModifier * 10));
            if(!newSquare.isOccupied() && !checkSquare.isOccupied()){
                move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                this.possibleMoves.add(move);
            }

            newCoordinate = this.coordinate + (this.directionModifier * 10);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(!(newSquare.isOccupied())){
                    move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                    this.possibleMoves.add(move);
                }
            }
            newCoordinate = this.coordinate + (this.directionModifier * 11);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                    move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                    this.possibleMoves.add(move);
                }
            }

            newCoordinate = this.coordinate + (this.directionModifier * 9);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                    move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                    this.possibleMoves.add(move);
                }
            }
        }
        else if(this.alliance.isPawnPromotionNextMove(this.coordinate)){
            newCoordinate = this.coordinate + (this.directionModifier * 10);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(!newSquare.isOccupied()){
                    this.promotionPiece = new Knight(newCoordinate, this.alliance);
                    move = new Move.PawnPromotion(this.coordinate, newCoordinate, this, this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Bishop(newCoordinate, this.alliance);
                    move = new Move.PawnPromotion(this.coordinate, newCoordinate, this, this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Rook(newCoordinate, this.alliance);
                    move = new Move.PawnPromotion(this.coordinate, newCoordinate, this, this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Queen(newCoordinate, this.alliance);
                    move = new Move.PawnPromotion(this.coordinate, newCoordinate, this, this.promotionPiece);
                    this.possibleMoves.add(move);
                }
            }
            newCoordinate = this.coordinate + (this.directionModifier * 9);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                    this. promotionPiece = new Knight(newCoordinate, this.alliance);
                    move = new Move.PawnPromotionWithCapture(this.coordinate, newCoordinate, this, newSquare.getPiece(), this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Bishop(newCoordinate, this.alliance);
                    move = new Move.PawnPromotionWithCapture(this.coordinate, newCoordinate, this, newSquare.getPiece(), this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Rook(newCoordinate, this.alliance);
                    move = new Move.PawnPromotionWithCapture(this.coordinate, newCoordinate, this, newSquare.getPiece(), this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Queen(newCoordinate, this.alliance);
                    move = new Move.PawnPromotionWithCapture(this.coordinate, newCoordinate, this, newSquare.getPiece(), this.promotionPiece);
                    this.possibleMoves.add(move);
                }
            }
            newCoordinate = this.coordinate + (this.directionModifier * 11);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                    this. promotionPiece = new Knight(newCoordinate, this.alliance);
                    move = new Move.PawnPromotionWithCapture(this.coordinate, newCoordinate, this, newSquare.getPiece(),this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Bishop(newCoordinate, this.alliance);
                    move = new Move.PawnPromotionWithCapture(this.coordinate, newCoordinate, this, newSquare.getPiece(), this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Rook(newCoordinate, this.alliance);
                    move = new Move.PawnPromotionWithCapture(this.coordinate, newCoordinate, this, newSquare.getPiece(), this.promotionPiece);
                    this.possibleMoves.add(move);

                    this.promotionPiece = new Queen(newCoordinate, this.alliance);
                    move = new Move.PawnPromotionWithCapture(this.coordinate, newCoordinate, this, newSquare.getPiece(), this.promotionPiece);
                    this.possibleMoves.add(move);
                }
            }
        }
        else{
            newCoordinate = this.coordinate + (this.directionModifier * 10);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(!(newSquare.isOccupied())){
                    move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                    this.possibleMoves.add(move);
                }
            }
            newCoordinate = this.coordinate + (this.directionModifier * 11);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                    move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                    this.possibleMoves.add(move);
                }
            }
            newCoordinate = this.coordinate + (this.directionModifier * 9);
            if(board.isValidSquare(newCoordinate)){
                newSquare = board.getSquare(newCoordinate);
                if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                    move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                    this.possibleMoves.add(move);
                }
            }
        }
        if(board.hasEnPassantSquare()){
            int enPassantCoordinate = board.getEnPassantSquare();
            if(this.coordinate + 1 == enPassantCoordinate || this.coordinate - 1 == enPassantCoordinate) {
                Square enPassantSquare = board.getSquare(enPassantCoordinate);
                Move enPassant = new Move.EnPassantMove(this.coordinate, enPassantCoordinate +
                        (10 * this.directionModifier), enPassantCoordinate, this, enPassantSquare.getPiece());
                this.possibleMoves.add(enPassant);
            }
        }
        return possibleMoves;
    }
}
