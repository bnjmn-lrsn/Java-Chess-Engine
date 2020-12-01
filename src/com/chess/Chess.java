package com.chess;

import java.util.ArrayList;

import com.chess.engine.pieces.*;
import com.chess.engine.player.*;
import com.chess.engine.board.*;
import com.chess.engine.player.ai.MiniMaxWithAlphaBetaPruning;
import com.chess.gui.Table;

public class Chess {

    public static void main(String[] args) {
        Player white = new Player(Alliance.WHITE);
        Player black = new Player(Alliance.BLACK);
        Board chessBoard = new Board(white, black);

        int i = 1;
        ArrayList<Move> testWhite = white.generateAllPossibleMoves(chessBoard);
        for(Move move : testWhite){
            System.out.println(i + ". " + move);
            i++;
        }

        System.out.println(chessBoard);


        Table chess = new Table();
    }
}
