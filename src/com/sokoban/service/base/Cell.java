package com.sokoban.service.base;

/**
 * A cell of the board.
 */
public class Cell {
    private final int ROW;
    private final int COLUMN;
    private Item i;
    private boolean blockState;
    
    /**
     * Cell constructor.
     * 
     * @param row the position of the cell on the line
     * @param column the position of the cell on the column
     */
    public Cell(int row, int column) {
        this.ROW = row;
        this.COLUMN = column;
        this.i = Item.VOID;
        this.blockState = false;
    }
    
    /**
     * Get the position of the cell on the line.
     *
     * @return a row value
     */
    public int getRow() {
        return this.ROW;
    }
    
    /**
     * Get the position of the cell on the column.
     *
     * @return a column value
     */
    public int getColumn() {
        return this.COLUMN;
    }
    
    /**
     * Get the item which correspond with the cell.
     *
     * @return a item
     */
    public Item getItem() {
        return this.i;
    }
    
    /**
     * Get the state of the cell.
     *
     * @return a state
     */
    public boolean getBlockState() {
        return this.blockState;
    }
        
    /**
     * Set a item to a cell.
     * 
     * @param i item
     * @return a item
     */   
    public Item setItem(Item i) {
        return this.i = i;
    }
    
    /**
     * Set a state to a cell.
     * 
     * @param b state
     * @return a state
     */
    public boolean setBlockState(boolean b) {
        return this.blockState = b;
    }
}