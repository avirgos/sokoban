package com.sokoban.service.boardBuild;

import com.sokoban.service.base.Board;
import com.sokoban.service.base.BoardCellException;
import com.sokoban.service.base.Item;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * A board created using a file.
 */
public class FileBoardBuilder implements BoardBuilder {
    private String title;
    private int sizeX;
    private int sizeY;
    private final File BOARD_FILE;
    private final Map<Integer, String> ROWS;
    private Board b;

    /**
     * FileBoardBuilder constructor.
     *
     * @param f the board file
     */
    public FileBoardBuilder(File f) {
        this.BOARD_FILE = f;
        this.ROWS = new HashMap<>();
        this.build();
    }

    /**
     * Get the title of the board.
     *
     * @return a title of a board
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Get the length of the board abscissa.
     *
     * @return a length of a board abscissa
     */
    @Override
    public int getSizeX() {
        return this.sizeX;
    }

    /**
     * Get the length of the board ordinate.
     *
     * @return a length of a board ordinate
     */
    @Override
    public int getSizeY() {
        return this.sizeY;
    }

    /**
     * Get the board file.
     *
     * @return a board file
     */
    public File getFile() {
        return this.BOARD_FILE;
    }

    /**
     * Get the rows of the board.
     *
     * @return ROWS
     */
    public Map<Integer, String> getRows() {
        return this.ROWS;
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
     * Read the general informations (title and dimensions) of a board file.
     *
     * @throws BoardCellException the cell coordinates of the board are not
     * valid
     * @throws BoardBuildException the board cannot be built
     */
    public void readFileBoardInformations() throws BoardCellException, BoardBuildException {
        try (
                Scanner scanner = new Scanner(getFile())) {
            int meter = 0;
            while (scanner.hasNextLine()) {
                meter++;
                String lineStr = scanner.nextLine();
                if (!lineStr.equals("")) {
                    // title
                    if (meter == 1) {
                        this.title = lineStr;
                    }

                    // dimensions
                    if (meter == 2) {
                        String[] dimensionsParts = lineStr.split("x");

                        String partSizeX = dimensionsParts[0].replaceAll("[^0-9]", "");
                        this.sizeX = Integer.valueOf(partSizeX);

                        String partSizeY = dimensionsParts[1].replaceAll("[^0-9]", "");
                        this.sizeY = Integer.valueOf(partSizeY);

                        if (Integer.valueOf(partSizeX) <= 0 || Integer.valueOf(partSizeX) >= 100
                                || Integer.valueOf(partSizeY) <= 0 || Integer.valueOf(partSizeY) >= 100) {
                            throw new BoardBuildException(
                                    "A board can't be build with a sizeX or/and a sizeY less or equal than 0 or more or equal than 100."
                            );
                        }
                    }
                }
            }

            // board build to test informations
            this.b = new Board(getTitle(), getSizeX(), getSizeY());

            if (Board.getSizeX() <= 0 || Board.getSizeX() >= 100
                    || Board.getSizeY() <= 0 || Board.getSizeY() >= 100) {
                throw new BoardBuildException(
                        "A board can't be build with a sizeX or/and a sizeY less or equal than 0 or more or equal than 100."
                );
            }

        } catch (FileNotFoundException e) {
            System.out.println("File cannot be found." + "\n");
        }
    }

    /**
     * Read a board file.
     *
     * @throws BoardCellException the cell coordinates of the board are not
     * valid
     */
    public void readFileContent() throws BoardCellException {
        try {
            Scanner scanner = new Scanner(this.BOARD_FILE);
            int meter = 0;
            int meter2 = -1;
            while (scanner.hasNextLine()) {
                meter++;
                String lineStr = scanner.nextLine();
                if (!lineStr.equals("")) {
                    if (meter >= 4) {
                        meter2++;
                        // row
                        int i = (meter + meter2) - meter;
                        // column
                        for (int j = 0; j <= Board.getSizeY(); j++) {
                            // wall
                            if (Character.toString(lineStr.charAt(j)).equals(Item.WALL.toString())) {
                                Board.getCell(i, j).setItem(Item.WALL);
                            }
                            // player
                            if (Character.toString(lineStr.charAt(j)).equals(Item.PLAYER.toString())) {
                                Board.getCell(i, j).setItem(Item.PLAYER);
                            }
                            // crate
                            if (Character.toString(lineStr.charAt(j)).equals(Item.CRATE.toString())) {
                                Board.getCell(i, j).setItem(Item.CRATE);
                            }
                            // target
                            if (Character.toString(lineStr.charAt(j)).equals(Item.TARGET.toString())) {
                                Board.getCell(i, j).setItem(Item.TARGET);
                            }
                        }
                        getRows().put(i, lineStr);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // ...
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