package com.sokoban.service.boardBuild;

import com.sokoban.service.base.Board;
import com.sokoban.service.base.BoardCellException;
import com.sokoban.service.base.Item;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * A board created using text.
 */
public class TextBoardBuilder implements BoardBuilder {
    private final String TITLE;
    private final int SIZE_X;
    private final int SIZE_Y;
    private Board b;

    /**
     * TextBoardBuilder constructor.
     *
     * @param title the title of the board
     * @param sizeX the length of the board abscissa
     * @param sizeY the length of the board ordinate
     */
    public TextBoardBuilder(String title, int sizeX, int sizeY) {
        this.TITLE = title;
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
        this.build();
    }

    /**
     * Get the title of the board.
     *
     * @return a title of a board
     */
    @Override
    public String getTitle() {
        return this.TITLE;
    }

    /**
     * Get the length of the board abscissa.
     *
     * @return a length of a board abscissa
     */
    @Override
    public int getSizeX() {
        return this.SIZE_X;
    }

    /**
     * Get the length of the board ordinate.
     *
     * @return a length of a board ordinate
     */
    @Override
    public int getSizeY() {
        return this.SIZE_Y;
    }

    /**
     * Get the board.
     *
     * @return a board
     */
    public Board getBoard() {
        return this.b;
    }

    /**
     * Add a row to the board.
     *
     * @param elements the row composed by elements into a string
     * @param row the number of the row
     * @throws BoardCellException the cell coordinates of the board are not
     * valid
     */
    public void addRow(String elements, int row) throws BoardCellException {
        int sizeElements = elements.length();

        for (int j = 0; j < sizeElements; j++) {
            String letterStr = Character.toString(elements.charAt(j));
            // wall
            if (letterStr.equals(Item.WALL.toString())) {
                Board.getCell(row, j).setItem(Item.WALL);
            }
            // player
            if (letterStr.equals(Item.PLAYER.toString())) {
                Board.getCell(row, j).setItem(Item.PLAYER);
            }
            // crate
            if (letterStr.equals(Item.CRATE.toString())) {
                Board.getCell(row, j).setItem(Item.CRATE);
            }
            // target
            if (letterStr.equals(Item.TARGET.toString())) {
                Board.getCell(row, j).setItem(Item.TARGET);
            }
        }
    }

    /**
     * Generate a text file (file extension : .txt), from the board, which
     * contains the title, the dimensions and the board.
     *
     * @throws BoardCellException the cell coordinates of the board are not
     * valid
     * @throws BoardBuildException the board cannot be built
     */
    public void generateFile() throws BoardCellException, BoardBuildException {
        if (Board.getSizeX() <= 0 || Board.getSizeX() >= 100
                || Board.getSizeY() <= 0 || Board.getSizeY() >= 100) {
            throw new BoardBuildException(
                    "A board can't be build with a sizeX or/and a sizeY less or equal than 0 or more or equal than 100."
            );
        }

        try (
            // filename
            FileWriter fileWriter = new FileWriter("levels/" + this.b.getTitle() + ".txt");  
            PrintWriter boardFile = new PrintWriter(fileWriter)) {
            // title
            boardFile.println(this.b.getTitle());
            // dimensions
            boardFile.println("Dimensions : " + Board.getSizeX() + "x" + Board.getSizeY() + "\n");

            // board
            for (int i = 0; i <= Board.getSizeX(); i++) {
                for (int j = 0; j <= Board.getSizeY(); j++) {
                    boardFile.print(Board.getCell(i, j).getItem());
                }
                boardFile.println();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Build an instance of a board.
     *
     * @return a board
     */
    @Override
    public final Board build() {
        this.b = new Board(getTitle(), getSizeX(), getSizeY());
        return getBoard();
    }
}