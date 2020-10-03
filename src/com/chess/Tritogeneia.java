package com.chess;

import java.util.ArrayList;

import com.chess.engine.player.*;
import com.chess.engine.board.*;

public class Tritogeneia {

    public static void main(String[] args) {
        Board newBoard = new Board();
        System.out.println(newBoard);
        Player white = new Player(Alliance.WHITE, newBoard);
        //long start, end;
        //start = System.currentTimeMillis();
        int i = 1;
        ArrayList<Move> testWhite = white.generateAllPossibleMoves(newBoard);
        for(Move move : testWhite){
            System.out.println(i + ". " + move);
            i++;
        }
        //end = System.currentTimeMillis();
        //System.out.println(end - start);

        Player black = new Player(Alliance.BLACK, newBoard);
        i = 1;
        ArrayList<Move> testBlack = black.generateAllPossibleMoves(newBoard);
        for(Move move : testBlack){
            System.out.println(i + ". " + move);
            i++;
        }
        System.out.println(newBoard);

        //Table table = new Table();
    }
}
