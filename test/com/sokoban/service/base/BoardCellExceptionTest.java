package com.sokoban.service.base;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * BoardCellException class test.
 */
public class BoardCellExceptionTest {
        
    /**
     * Test to check BoardCellException of the getCell method. 
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    @Test
    public void BoardGetCellException() throws BoardCellException {
        assertEquals(Item.VOID, Board.getCell(-1, 0).getItem());
        assertEquals(Item.VOID, Board.getCell(0, 21).getItem());
    }
}