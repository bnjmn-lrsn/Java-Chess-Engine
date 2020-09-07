package com.chess.engine;

import java.util.ArrayList;

import com.chess.engine.player.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.*;

public class Tritogeneia {

    public static void main(String[] args){
        Board newBoard = new Board();
        Player white = new Player(Colour.WHITE, newBoard);
        long start, end;
        start = System.currentTimeMillis();
        ArrayList<Move> test = white.generateAllPossibleMoves(newBoard);
        for(Move move : test){
            System.out.println(move);
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
        newBoard.printBoard();
    }
}
