package com.sokoban.data.boardBuild;

import com.sokoban.service.boardBuild.TextBoardBuilder;
import com.sokoban.service.boardBuild.BoardBuildException;
import com.sokoban.service.base.BoardCellException;
import org.junit.Test;

public class BoardBuildExceptionTest {
    
    /**
     * Test to check BoardBuildException of the generateFile method. 
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     * @throws BoardBuildException the board cannot be built
     */
    @Test
    public void GenerateFileException() throws BoardCellException, BoardBuildException {
        TextBoardBuilder builder = new TextBoardBuilder("Test Builder", 0, 0);
        builder.build();
        builder.generateFile();
    }
    
    /**
     * Test to check BoardBuildException of the generateFile method. 
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     * @throws BoardBuildException the board cannot be built
     */
    @Test
    public void GenerateFileException2() throws BoardCellException, BoardBuildException {
        TextBoardBuilder builder = new TextBoardBuilder("Test Builder", 5, -1);
        builder.build();
        builder.generateFile();
    }
            
    /**
     * Test to check BoardBuildException of the generateFile method. 
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     * @throws BoardBuildException the board cannot be built
     */
    @Test
    public void GenerateFileException3() throws BoardCellException, BoardBuildException {
        TextBoardBuilder builder = new TextBoardBuilder("Test Builder", 100, 5);
        builder.build();
        builder.generateFile();
    }
}