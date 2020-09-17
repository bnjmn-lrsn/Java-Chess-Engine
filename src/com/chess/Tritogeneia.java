package com.chess;

import java.util.ArrayList;

import com.chess.engine.player.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.*;
import com.chess.gui.Table;

public class Tritogeneia {

    public static void main(String[] args) {
        /*Board newBoard = new Board();
        Player white = new Player(Colour.WHITE, newBoard);
        //long start, end;
        //start = System.currentTimeMillis();
        ArrayList<Move> testWhite = white.generateAllPossibleMoves(newBoard);
        for(Move move : testWhite){
            System.out.println(move);
        }
        //end = System.currentTimeMillis();
        //System.out.println(end - start);
        newBoard.printBoard();
        Player black = new Player(Colour.BLACK, newBoard);
        ArrayList<Move> testBlack = black.generateAllPossibleMoves(newBoard);
        for(Move move : testBlack){
            System.out.println(move);
        }*/

        Table table = new Table();
    }
}
