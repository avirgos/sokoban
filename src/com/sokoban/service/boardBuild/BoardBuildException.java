package com.sokoban.service.boardBuild;

/**
 * An exception to check the build of the board.
 */
public class BoardBuildException extends Exception {
    
    /**
     * BoardBuildException.
     * 
     * @param message message to explain why the user got this exception
     */
    public BoardBuildException(String message) {
        super(message);
    }
}