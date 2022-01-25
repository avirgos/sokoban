package com.sokoban.data.boardsDbMaintenance;

import com.sokoban.service.base.BoardCellException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * BoardsDatabase class tests.
 */
public class BoardsDatabaseTest {
    
    /**
     * Test of the board got using BoardsDatabase. 
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    @Test
    public void BoardsDatabseGet() throws BoardCellException {
        // A Simple Board
        assertEquals("A Simple Board", BoardsDatabase.get("simple").getTitle());
        // The Zagreus trial
        assertEquals("The Zagreus trial", BoardsDatabase.get("difficult").getTitle());
    }
}