package com.chess.engine.pieces;

import java.util.ArrayList;

import com.chess.engine.board.*;
import com.chess.engine.player.*;

public class Pawn extends Piece {
    private Piece promotionPiece;

    public Pawn(int coordinate, Alliance alliance) {
        super(coordinate, alliance);
        this.materialValue = 100;
        this.pieceType = "Pawn";
    }

    public String toString() { return "P"; }

    @Override
    public ArrayList<Move> getPossibleMoves(Board board) {
        this.possibleMoves = new ArrayList<>();
        int newCoordinate;
        Square newSquare;
        Square checkSquare;
        Move move;

        if(this.alliance == Alliance.WHITE) {
            //Move from starting position
            if(this.coordinate >= 81) {
                newCoordinate = this.coordinate - 20;
                newSquare = board.getSquare(newCoordinate);
                checkSquare = board.getSquare(coordinate - 10);

                if(!newSquare.isOccupied() && !checkSquare.isOccupied()){
                    move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                    this.possibleMoves.add(move);
                }
                newCoordinate = this.coordinate - 10;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(!(newSquare.isOccupied())){
                        move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                        this.possibleMoves.add(move);
                    }
                }
                newCoordinate = this.coordinate - 11;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                        move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                        this.possibleMoves.add(move);
                    }
                }

                newCoordinate = this.coordinate - 9;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                        move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                        this.possibleMoves.add(move);
                    }
                }
            }
            //Moving up the board to the seventh rank
            else if(this.coordinate < 81 && this.coordinate > 38) {
                newCoordinate = this.coordinate - 10;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(!(newSquare.isOccupied())){
                        move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                        this.possibleMoves.add(move);
                    }
                }
                newCoordinate = this.coordinate - 11;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                        move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                        this.possibleMoves.add(move);
                    }
                }
                newCoordinate = this.coordinate - 9;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                        move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                        this.possibleMoves.add(move);
                    }
                }
            }
            //Promotions: pawns are obligated to promote once they reach the opposite end of the board.
            else if(this.coordinate <= 38){
                newCoordinate = this.coordinate - 10;
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
                newCoordinate = this.coordinate - 9;
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
                newCoordinate = this.coordinate - 11;
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
        }else if(this.alliance == Alliance.BLACK){
            if(this.coordinate <= 38){
                newCoordinate = this.coordinate + 20;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    checkSquare = board.getSquare(this.coordinate + 10);
                    if(!newSquare.isOccupied() && !checkSquare.isOccupied()){
                        move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                        this.possibleMoves.add(move);
                    }
                }
                newCoordinate = this.coordinate + 10;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(!(newSquare.isOccupied())){
                        move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                        this.possibleMoves.add(move);
                    }
                }
                newCoordinate = this.coordinate + 11;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                        move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                        this.possibleMoves.add(move);
                    }
                }
                newCoordinate = this.coordinate + 9;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                        move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                        this.possibleMoves.add(move);
                    }
                }
            }
            else if(this.coordinate > 38 && this.coordinate < 81) {
                newCoordinate = this.coordinate + 10;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(!(newSquare.isOccupied())){
                        move = new Move.PawnMove(this.coordinate, newCoordinate, this);
                        this.possibleMoves.add(move);
                    }
                }
                newCoordinate = this.coordinate + 11;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                        move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                        this.possibleMoves.add(move);
                    }
                }
                newCoordinate = this.coordinate + 9;
                if(board.isValidSquare(newCoordinate)){
                    newSquare = board.getSquare(newCoordinate);
                    if(newSquare.isOccupied() && newSquare.getPiece().getAlliance() != this.alliance){
                        move = new Move.PawnCapture(this.coordinate, newCoordinate, this, newSquare.getPiece());
                        this.possibleMoves.add(move);
                    }
                }
            }
            else if(this.coordinate >= 81){
                newCoordinate = this.coordinate + 10;
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
                newCoordinate = this.coordinate + 9;
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
                newCoordinate = this.coordinate + 11;
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
        }
        return possibleMoves;
    }
}
