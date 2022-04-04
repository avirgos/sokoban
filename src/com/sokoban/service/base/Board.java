package com.sokoban.service.base;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * A board.
 */
public class Board {
    private final String TITLE;
    private static int sizeX;
    private static int sizeY;
    private static HashSet<Cell> cells;
    private final PrintStream OUT = System.out;

    /**
     * Board constructor.
     *
     * @param title title of the board
     * @param sizeX length of the board abscissa
     * @param sizeY length of the board ordinate
     */
    public Board(String title, int sizeX, int sizeY) {
        this.TITLE = title;
        Board.sizeX = sizeX;
        Board.sizeY = sizeY;
        Board.cells = new HashSet<>();
        fillBoard();
    }
        
    /**
     * Get the title of the board.
     *
     * @return the title of a board
     */
    public String getTitle() {
        return this.TITLE;
    }
    
    /**
     * Get the length of the board abscissa.
     *
     * @return the length of a board abscissa
     */
    public static int getSizeX() {
        return Board.sizeX;
    }

    /**
     * Get the length of the board ordinate.
     *
     * @return the length of a board ordinate
     */
    public static int getSizeY() {
        return Board.sizeY;
    }
            
    /**
     * Get all the cells on the board.
     *
     * @return cells
     */
    public static HashSet<Cell> getAll() {
        return Board.cells;
    }

    /**
     * Get the position of the player on the board.
     *
     * @return the player cell
     */
    public Cell getPositionPlayer() {        
        for (Cell ce : getAll()) {
            if (ce.getItem().equals(Item.PLAYER)) {
                return ce;
            }
        }

        return null;
    }

    /**
     * Get all crates from a cell along the line.
     *
     * @param ce cell which correspond to the origin
     * @return the ArrayList collection that contains crates along the line
     */
    public static ArrayList<Cell> getAllCratesRow(Cell ce) {
        ArrayList<Cell> cellCrates = new ArrayList<>();

        for (Cell c : getAll()) {
            if (c.getItem().equals(Item.CRATE)
                    && c.getRow() == ce.getRow()) {
                cellCrates.add(c);
            }
        }

        return cellCrates;
    }

    /**
     * Get all crates from a cell along the column.
     *
     * @param ce cell which correspond to the origin
     * @return the ArrayList collection that contains crates along the column
     */
    public static ArrayList<Cell> getAllCratesCol(Cell ce) {
        ArrayList<Cell> cellCrates = new ArrayList<>();
        
        for (Cell c : getAll()) {
            if (c.getItem().equals(Item.CRATE)
                    && c.getColumn() == ce.getColumn()) {
                cellCrates.add(c);
            }
        }

        return cellCrates;
    }

    /**
     * Get all targets.
     *
     * @return the HashSet collection that contains targets
     */
    public static HashSet<Cell> getAllTargets() {
        HashSet<Cell> cellTargets = new HashSet<>();

        for (Cell c : getAll()) {
            if (c.getItem().equals(Item.TARGET)) {
                cellTargets.add(c);
            }
        }

        return cellTargets;
    }

    /**
     * Get an accurate cell of the board using abscissa and ordinate values.
     *
     * @param row position of the cell on the line
     * @param column position of the cell on the column
     * @return the cell
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    public static Cell getCell(int row, int column) throws BoardCellException {
        for (Cell cell : getAll()) {
            if (cell.getRow() == row && cell.getColumn() == column) {
                return cell;
            }
        }

        // row
        if (row < 0 || row > getSizeX()) {
            throw new BoardCellException(
                    "Row \"" + row + "\" value is invalid for a cell."
            );
        }
        // col
        if (column < 0 || column > getSizeY()) {
            throw new BoardCellException(
                    "Column \"" + column + "\" value is invalid for a cell."
            );
        }
        
        return null;
    }

    /**
     * Fill the board with cells.
     */
    private void fillBoard() {
        for (int i = 0; i <= getSizeX(); i++) {
            for (int j = 0; j <= getSizeY(); j++) {
                Cell cell = new Cell(i, j);
                addCell(cell);
            }
        }
    }

    /**
     * Add a cell to the cells of the board.
     *
     * @param ce cell
     */
    private void addCell(Cell ce) {
        getAll().add(ce);
    }

    /**
     * Display the abscissa of the board.
     *
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    private void drawAbscissaX() throws BoardCellException {
        for (int i = 0; i <= getSizeX(); i++) {
            if (getSizeX() < 10) {
                OUT.print(i + " ");
                drawContent(i);
                OUT.print("\n");
            } else if (i < 10) {
                OUT.print(i + "  ");
                drawContent(i);
                OUT.print("\n");
            }

            if (i >= 10 && i < 100) {
                OUT.print(i + " ");
                drawContent(i);
                OUT.print("\n");
            }
        }

        OUT.print("\n");
    }

    /**
     * Display the ordinate of the board.
     */
    private void drawOrdinateY() {
        if (getSizeY() < 10 || getSizeY() >= 10) {
            OUT.print("  ");
        }

        if (getSizeX() >= 10 && getSizeX() < 100) {
            OUT.print(" ");
        }

        for (int i = 0; i <= getSizeY(); i++) {
            OUT.print(i + " ");
        }

        OUT.print("\n");
    }

    /**
     * Display the content of the board.
     *
     * @param i cell abcissa value
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    private void drawContent(int i) throws BoardCellException {
        for (int j = 0; j <= getSizeY(); j++) {
            // wall
            if (getCell(i, j).getItem().equals(Item.WALL)) {
                OUT.print(Item.WALL);
            }
            // player
            if (getCell(i, j).getItem().equals(Item.PLAYER)) {
                OUT.print(Item.PLAYER);
            }
            // crate
            if (getCell(i, j).getItem().equals(Item.CRATE)) {
                OUT.print(Item.CRATE);
            }
            // target
            if (getCell(i, j).getItem().equals(Item.TARGET)) {
                OUT.print(Item.TARGET);
            }
            // void
            if (getCell(i, j).getItem().equals(Item.VOID)) {
                OUT.print(Item.VOID);
            }

            if (j < 10) {
                OUT.print(" ");
            }

            if (j >= 10 && j < 100) {
                OUT.print("  ");
            }
        }
    }

    /**
     * Display the board.
     *
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    protected void drawBoard() throws BoardCellException {
        drawOrdinateY();
        drawAbscissaX();
    }
}