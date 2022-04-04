package com.sokoban.service.base;

/**
 * An exception to check the cell coordinates of the board.
 */
public class BoardCellException extends Exception {
    
    /**
     * InvalidBoardCellException.
     * 
     * @param message message to explain why the user got this exception
     */
    public BoardCellException(String message) {
        super(message);
    }
}