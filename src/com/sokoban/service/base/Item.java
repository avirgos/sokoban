package com.sokoban.service.base;

/**
 * A item assigned to a cell.
 */
public enum Item {
    WALL("#"),
    PLAYER("P"),
    CRATE("C"),
    TARGET("x"),
    VOID(".");
    private final String value;
    
    /**
     * Item constructor.
     * 
     * @param value item cell value
     */
    private Item(String value) {
        this.value = value;
    }
          
    /**
     * Return the cell item value.
     * 
     * @return the cell item value
     */
    @Override
    public String toString() {
        return this.value;
    }
}