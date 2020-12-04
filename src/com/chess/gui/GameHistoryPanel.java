package com.chess.gui;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.Alliance;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.chess.gui.Table.*;

public class GameHistoryPanel extends JPanel {
    private final DataModel model;
    private final JScrollPane scrollPane;

    private final static Dimension HISTORY_PANEL_DIMENSION = new Dimension(100, 400);

    GameHistoryPanel(){
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        this.scrollPane.setColumnHeaderView(table.getTableHeader());
        this.scrollPane.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void redo(Board board, MoveLog moveLog){
        int currentRow = 0;
        this.model.clear();
        for(Move move : moveLog.getMovesPlayedInGame()){
            String moveText = move.toString();
            if(move.getPieceMoved().getAlliance() == Alliance.WHITE){
                this.model.setValueAt(moveText, currentRow, 0);
            }else if(move.getPieceMoved().getAlliance() == Alliance.BLACK){
                this.model.setValueAt(moveText, currentRow, 1);
                currentRow++;
            }
        }
        if(moveLog.getMovesPlayedInGame().size() > 0){
            Move lastMove = moveLog.getMovesPlayedInGame().get(moveLog.getMovesPlayedInGame().size() - 1);
            String moveText = lastMove.toString();
            if(lastMove.getPieceMoved().getAlliance() == Alliance.WHITE){
                if(board.getBlackPlayer().isInCheck(board)) {
                    moveText = moveText + "+";
                    this.model.setValueAt(moveText, currentRow, 0);
                }
            }
        }
        final JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        validate();
    }

    private static class DataModel extends DefaultTableModel{

        private final List<Row> values;
        private final static String[] HEADERS = {"White", "Black"};

        DataModel(){
            this.values = new ArrayList<>();
        }

        public void clear(){
            this.values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount(){
            if(this.values == null){
                return 0;
            }
            else{
                return this.values.size();
            }
        }

        @Override
        public int getColumnCount(){
            return HEADERS.length;
        }

        @Override
        public Object getValueAt(final int row, final int column){
            final Row currentRow = this.values.get(row);
            if(column == 0){
                return currentRow.getWhiteMove();
            }
            else if(column == 1){
                return currentRow.getBlackMove();
            }
            else{
                return null;
            }
        }

        @Override
        public void setValueAt(final Object aValue, final int row, final int column){
            final Row currentRow;
            if(this.values.size() <= row){
                currentRow = new Row();
                this.values.add(currentRow);
            }
            else{
                currentRow = this.values.get(row);
            }
            if(column == 0){
                currentRow.setWhiteMove((String)aValue);
                fireTableRowsInserted(row, row);
            }
            else if(column == 1){
                currentRow.setBlackMove((String)aValue);
                fireTableCellUpdated(row, column);
            }
        }

        @Override
        public Class<?> getColumnClass(final int column){
            return Move.class;
        }

        @Override
        public String getColumnName(final int column){
            return HEADERS[column];
        }
    }

    private static class Row{
        private String whiteMove;
        private String blackMove;

        private Row(){
        }

        public String getWhiteMove() {
            return this.whiteMove;
        }

        public String getBlackMove() {
            return this.blackMove;
        }

        public void setWhiteMove(String whiteMove) {
            this.whiteMove = whiteMove;
        }

        public void setBlackMove(String blackMove) {
            this.blackMove = blackMove;
        }
    }

}
