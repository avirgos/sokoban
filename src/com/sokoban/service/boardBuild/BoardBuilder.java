package com.sokoban.service.boardBuild;

import com.sokoban.service.base.Board;

/**
 * BoardBuilder interface.
 */
public interface BoardBuilder {
    
    /**
     * Build an instance of a board.
     * 
     * @return a board
     */
    Board build();
    
    /**
     * Get the title of the board.
     * 
     * @return a title of a board
     */
    String getTitle();
    
    /**
     * Get the length of the board abscissa.
     * 
     * @return a length of a board abscissa
     */
    int getSizeX();
    
    /**
     * Get the length of the board ordinate.
     * 
     * @return a length of a board ordinate
     */
    int getSizeY();
}