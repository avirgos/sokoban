package com.sokoban.data.boardBuild;

import com.sokoban.service.boardBuild.FileBoardBuilder;
import com.sokoban.service.boardBuild.TextBoardBuilder;
import com.sokoban.service.boardBuild.BoardBuildException;
import com.sokoban.service.base.Board;
import com.sokoban.service.base.BoardCellException;
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * FileBoardBuilder class tests.
 */
public class FileBoardBuilderTest {

    /**
     * Test of the FileBoardBuilder constructor.
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     * @throws BoardBuildException the board cannot be built
     */
    @Test
    public void FileBoardBuilderConstructor() throws BoardCellException, BoardBuildException {
        TextBoardBuilder builder = new TextBoardBuilder("Test Builder", 10, 5);
        
        Board b = builder.build();
        builder.addRow("#x#.#.", 0);
        builder.addRow("#.#.#.", 1);
        builder.addRow("#.#.#.", 2);
        builder.addRow("#.#.#C", 3);
        builder.addRow("#.#.#.", 4);
        builder.addRow("#.#.#P", 5);
        
        File testFile = new File("levels/" + b.getTitle() + ".txt");
        
        builder.generateFile();
        
        FileBoardBuilder fileBuilder = new FileBoardBuilder(testFile);
        assertEquals(testFile.getPath(), fileBuilder.getFile().toString());
        
        testFile.delete(); // comment this line if you want to see, in details, the board created
    }
}