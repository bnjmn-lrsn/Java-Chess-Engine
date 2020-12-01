package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.Player;

public class MiniMaxWithAlphaBetaPruning {
    private int searchDepth;
    private int branchesPruned;
    public Player player;
    private Player opponent;
    boolean maximizingPlayer;
    Board board;

    public MiniMaxWithAlphaBetaPruning(final int searchDepth, final boolean maximizingPlayer,
                                       final Player player, final Player opponent, final Board board){
        this.searchDepth = searchDepth;
        this.player = player;
        this.opponent = opponent;
        this.maximizingPlayer = maximizingPlayer;
        this.board = board;
    }

    public int alphaBeta(boolean maximizingPlayer, int depth, int alpha, int beta, Move move){
        if(depth == 0){
            BoardEvaluation.evaluateMove(move, this.board, this.player, this.opponent);
            //System.out.print(move + " ");
            return move.getMoveEvaluation();
        }

        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;
            for(Move child : this.player.generateAllPossibleMoves(this.board)){
                this.board.makeMove(child);
                int eval = alphaBeta(false, depth - 1, alpha, beta, child);
                maxEval = Math.max(maxEval, eval);
                child.setMoveEvaluation(maxEval);
                alpha = Math.max(alpha, maxEval);
                this.board.undoMove();
                if(beta <= alpha){
                    //System.out.println("Alpha break");
                    break;
                }
            }
            return maxEval;
        }
        else{
            int minEval = Integer.MAX_VALUE;
            for(Move child : this.opponent.generateAllPossibleMoves(this.board)){
                this.board.makeMove(child);
                int eval = alphaBeta(true, depth - 1, alpha, beta, child);
                minEval = Math.min(minEval, eval);
                child.setMoveEvaluation(minEval);
                beta = Math.min(beta, minEval);
                this.board.undoMove();
                if(beta <= alpha){
                    //System.out.println("Beta break");
                    break;
                }
            }
            return minEval;
        }
    }

    public int getSearchDepth(){
        return this.searchDepth;
    }

    public void setSearchDepth(int searchDepth){
        this.searchDepth = searchDepth;
    }
}
