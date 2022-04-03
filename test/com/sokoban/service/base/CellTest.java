package com.sokoban.service.base;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Cell class tests.
 */
public class CellTest {
    
    /**
     * Test of the cell constructor.
     */
    @Test
    public void CellConstructor() {
        Cell ce = new Cell(4, 20);
        assertEquals(4, ce.getRow());
        assertEquals(20, ce.getColumn());
    }
    
    /**
     * Test of the item of the cell.
     */
    @Test
    public void CellItem() {
        // void
        Cell ce = new Cell(4, 20);
        assertEquals(Item.VOID, ce.getItem());
        // crate
        ce.setItem(Item.CRATE);
        assertEquals(Item.CRATE, ce.getItem());
        // destination
        ce.setItem(Item.TARGET);
        assertEquals(Item.TARGET, ce.getItem());
        // wall
        ce.setItem(Item.WALL);
        assertEquals(Item.WALL, ce.getItem());
        // player
        ce.setItem(Item.PLAYER);
        assertEquals(Item.PLAYER, ce.getItem());
    }
    
    /**
     * Test if the cell is blocked or not.
     */
    @Test
    public void CellBlockState() {
        // cell not blocked
        Cell ce = new Cell(4, 20);
        assertEquals(false, ce.getBlockState());
        // cell blocked
        ce.setBlockState(true);
        assertEquals(true, ce.getBlockState());
    }
}