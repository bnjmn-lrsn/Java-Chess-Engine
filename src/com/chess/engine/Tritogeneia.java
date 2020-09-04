package com.chess.engine;

import com.chess.engine.player.*;
import com.chess.engine.board.*;
import com.chess.engine.pieces.*;

import java.util.ArrayList;

public class Tritogeneia {

    public static void main(String[] args){
        Board newBoard = new Board();
        Player white = new Player(Colour.WHITE, newBoard);
        //newBoard.printBoard();
        long start, end;
        start = System.currentTimeMillis();
        ArrayList<Move> test = white.generateAllPossibleMoves(newBoard);
        /*for(int x = 0; x < test.size(); ++x) {
            System.out.println(test.get(x));
        }*/
        for(Move move : test){
            System.out.println(move);
        }
        end = System.currentTimeMillis();
        System.out.println(end - start);
        newBoard.printBoard();
    }
}
