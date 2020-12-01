package com.chess.engine.player;

public enum Alliance {
    WHITE(){
        public boolean isWhite(){ return true; }

        public boolean isBlack(){
            return false;
        }

        @Override
        public int getDirectionModifier() {
            return -1;
        }

        @Override
        public boolean isPawnStartingSquare(int coordinate) {
            return coordinate >= 81 && coordinate <= 88;
        }

        @Override
        public boolean isPawnPromotionNextMove(int coordinate) {
            return coordinate <= 38;
        }

        @Override
        public int getKingSideCastlingCoordinate(){
            return 97;
        }

        @Override
        public int getQueenSideCastlingCoordinate(){
            return 93;
        }

        @Override
        public int getKingSideRookCoordinate(){
            return 98;
        }

        @Override
        public int getQueenSideRookCoordinate(){
            return 91;
        }

        @Override
        public int getKingSideTransitionCoordinate() {
            return 96;
        }

        @Override
        public int getQueenSideTransitionCoordinate() {
            return 94;
        }

        @Override
        public int getQueenKnightCoordinate() {
            return 92;
        }

        @Override
        public int getKingStartingCoordinate() {
            return 95;
        }

        @Override
        public boolean isMaximizingPlayer() {
            return true;
        }
    },
    BLACK(){
        public boolean isWhite(){
            return false;
        }

        public boolean isBlack(){
            return true;
        }

        @Override
        public int getDirectionModifier() {
            return 1;
        }

        @Override
        public boolean isPawnStartingSquare(int coordinate) {
            return coordinate >= 31 && coordinate <= 38;
        }

        @Override
        public boolean isPawnPromotionNextMove(int coordinate) {
            return coordinate >= 81;
        }

        @Override
        public int getKingSideCastlingCoordinate(){
            return 27;
        }

        @Override
        public int getQueenSideCastlingCoordinate(){
            return 23;
        }

        @Override
        public int getKingSideRookCoordinate(){
            return 28;
        }

        @Override
        public int getQueenSideRookCoordinate(){
            return 21;
        }

        @Override
        public int getKingSideTransitionCoordinate() {
            return 26;
        }

        @Override
        public int getQueenSideTransitionCoordinate() {
            return 24;
        }

        @Override
        public int getQueenKnightCoordinate() {
            return 22;
        }

        @Override
        public int getKingStartingCoordinate() {
            return 25;
        }

        @Override
        public boolean isMaximizingPlayer() {
            return false;
        }


    };
    public abstract int getDirectionModifier();
    public abstract boolean isPawnStartingSquare(int coordinate);
    public abstract boolean isPawnPromotionNextMove(int coordinate);
    public abstract int getKingSideCastlingCoordinate();
    public abstract int getQueenSideCastlingCoordinate();
    public abstract int getKingSideRookCoordinate();
    public abstract int getQueenSideRookCoordinate();
    public abstract int getKingSideTransitionCoordinate();
    public abstract int getQueenSideTransitionCoordinate();
    public abstract int getQueenKnightCoordinate();
    public abstract int getKingStartingCoordinate();
    public abstract boolean isMaximizingPlayer();
}
