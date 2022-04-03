package com.sokoban.service.boardBuild;

import com.sokoban.service.base.Board;
import com.sokoban.service.base.BoardCellException;
import com.sokoban.service.base.Item;
import java.io.File;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * TextBoardBuilder class tests.
 */
public class TextBoardBuilderTest {
    private final TextBoardBuilder BUILDER = new TextBoardBuilder("Test Builder", 10, 5);
    
    /**
     * Test of the TextBoardBuilder constructor.
     */
    @Test
    public void TextBoardBuilderConstructor() {
        assertEquals("Test Builder", BUILDER.getTitle());
        assertEquals(10, BUILDER.getSizeX());
        assertEquals(5, BUILDER.getSizeY());
    }
    
    /**
     * Test of the creation of the row. 
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    @Test
    public void TextBoardBuilderAddRow() throws BoardCellException {
        BUILDER.addRow("#.#.#.", 0);
        assertEquals(Item.WALL, Board.getCell(0, 0).getItem());
        assertEquals(Item.VOID, Board.getCell(0, 1).getItem());
        assertEquals(Item.WALL, Board.getCell(0, 2).getItem());
    }
    
    /**
     * Test of the creation of the file generation.
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     * @throws IOException some sort has occurred
     * @throws BoardBuildException the board cannot be built
     */
    @Test
    public void TextBoardBuilderGenerateFile() throws BoardCellException, IOException, BoardBuildException {
        Board b = BUILDER.build();
        BUILDER.addRow("#x#.#.", 0);
        BUILDER.addRow("#.#.#.", 1);
        BUILDER.addRow("#.#.#.", 2);
        BUILDER.addRow("#.#.#C", 3);
        BUILDER.addRow("#.#.#.", 4);
        BUILDER.addRow("#.#.#P", 5);
        
        File testFile = new File("levels/" + b.getTitle() + ".txt");
        
        BUILDER.generateFile();
        // Test file existence
        assertEquals(true, testFile.exists());
        // Test file content
        assertEquals(109, testFile.length());
        
        testFile.delete(); // comment this line if you want to see, in details, the board created
    }
}