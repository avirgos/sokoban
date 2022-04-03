package com.sokoban.service.base;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Game class tests.
 */
public class GameTest {
    static Board b = new Board("Test Board", 4, 20);
    
    /**
     * Test of the up move of the player.
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    @Test
    public void GameMoveUp() throws BoardCellException {
        Cell p = Board.getCell(1, 0);
        Cell ceAdjacent = Board.getCell(p.getRow() - 1, p.getColumn());
        
        assertEquals(Board.getCell(0, 0), ceAdjacent);
    }
    
    /**
     * Test of the left move of the player.
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    @Test
    public void GameMoveLeft() throws BoardCellException {
        Cell p = Board.getCell(0, 1);
        Cell ceAdjacent = Board.getCell(p.getRow(), p.getColumn() - 1);
        
        assertEquals(Board.getCell(0, 0), ceAdjacent);
    }
    
    /**
     * Test of the down move of the player.
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    @Test
    public void GameMoveDown() throws BoardCellException {
        Cell p = Board.getCell(0, 0);
        Cell ceAdjacent = Board.getCell(p.getRow() + 1, p.getColumn());
        
        assertEquals(Board.getCell(1, 0), ceAdjacent);
    }
    
    /**
     * Test of the right move of the player.
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    @Test
    public void GameMoveRight() throws BoardCellException {
        Cell p = Board.getCell(0, 0);
        Cell ceAdjacent = Board.getCell(p.getRow(), p.getColumn() + 1);
        
        assertEquals(Board.getCell(0, 1), ceAdjacent);
    }
    
    /**
     * Test of the game victory.
     */
    @Test
    public void GameVictory() {
        assertEquals(0, b.getAllTargets().size());
    }
}