package com.sokoban.service.base;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;

/**
 * A game.
 */
public class Game extends Board {
    private static HashSet<Cell> cratesBeforeUpdateTargets;
    private static final Scanner IN = new Scanner(System.in);
    private static final PrintStream OUT = System.out;

    /**
     * Game constructor.
     *
     * @param title title of the board
     * @param sizeX length of the board abscissa
     * @param sizeY length of the board ordinate
     */
    public Game(String title, int sizeX, int sizeY) {
        super(title, sizeX, sizeY);
        cratesBeforeUpdateTargets = new HashSet<>();
    }

    /**
     * Get the crates before the update of destinations.
     *
     * @return cratesBeforeUpdateTargets
     */
    public static HashSet<Cell> getCratesBeforeUpdateTargets() {
        return cratesBeforeUpdateTargets;
    }

    /**
     * Remove spaces from the string.
     *
     * @return the line lacking in spaces, wrote by an user
     */
    private static String read() {
        return IN.nextLine().trim();
    }

    /**
     * Interaction menu of Sokoban.
     *
     * @param b board
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    public static void interactMenu(Board b) throws BoardCellException {
        OUT.println("\n" + "The game begins !");
        OUT.println("Z = Up \nQ = Left \nS = Down \nD = Right\n");
        OUT.println("Board : " + b.getTitle());
        OUT.println("Dimensions : " + getSizeX() + "x" + getSizeY() + "\n");
        
        b.drawBoard();
        
        cratesBeforeUpdateTargets = getAllTargets();
        
        boolean loop = true;
        while (loop) {
            OUT.println("* Type a simple move or a combination of moves. (ZQSD)");
            OUT.println("* Back to main menu. (E)");
            String movesSelected = read();

            // newline each time move(s) typed by the user
            if (!movesSelected.isEmpty()) {
                OUT.print("\n");
            }

            // execution of player movements
            if (!movePlayerInteract(movesSelected, b, loop)) {
                break;
            }

            // end of the current game
            if (victory()) {
                loop = false;
            }
        }
    }

    /**
     * Player interaction for the move(s).
     *
     * @param movesSelected move(s) wrote by an user
     * @param b board
     * @param loop true if the game is still in progress, false if it isn't
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    private static boolean movePlayerInteract(String movesSelected, Board b, boolean loop) throws BoardCellException {
        char unknownChar;
        boolean unknownCharPresence = false;
        
        // move combination
        if (movesSelected.length() > 1) {
            for (int i = 0; i < movesSelected.length(); i++) {
                switch (movesSelected.charAt(i)) {
                    case 'Z':
                        Cell ce = b.getPositionPlayer();
                        moveUp(ce);
                        break;
                    case 'Q':
                        Cell ce2 = b.getPositionPlayer();
                        moveLeft(ce2);
                        break;
                    case 'S':
                        Cell ce3 = b.getPositionPlayer();
                        moveDown(ce3);
                        break;
                    case 'D':
                        Cell ce4 = b.getPositionPlayer();
                        moveRight(ce4);
                        break;
                    default:
                        unknownCharPresence = true;
                        unknownChar = movesSelected.charAt(i);
                        OUT.println("Invalid move : \"" + unknownChar + "\"." + "\n");
                        break;
                }
            }
            
            if (!unknownCharPresence) {
                b.drawBoard();
            }

        // only one move
        } else {
            switch (movesSelected) {
                case "Z":
                    Cell ce = b.getPositionPlayer();
                    moveUp(ce);
                    b.drawBoard();
                    break;
                case "Q":
                    Cell ce2 = b.getPositionPlayer();
                    moveLeft(ce2);
                    b.drawBoard();
                    break;
                case "S":
                    Cell ce3 = b.getPositionPlayer();
                    moveDown(ce3);
                    b.drawBoard();
                    break;
                case "D":
                    Cell ce4 = b.getPositionPlayer();
                    moveRight(ce4);
                    b.drawBoard();
                    break;
                case "E":
                    loop = false;
                    break;
                default:
                    OUT.println("Command not found : \"" + movesSelected + "\"." + "\n");
                    break;
            }
        }

        return loop;
    }

    /**
     * Move the player (and the crates) to the up direction.
     *
     * @param p player cell
     */
    private static void moveUp(Cell p) {
        try {
            Cell ceAdjacent = getCell(p.getRow() - 1, p.getColumn());

            if (!Position.blockCrate(ceAdjacent, getCratesBeforeUpdateTargets())) {
                Position.movePlayerCell(p, ceAdjacent);

                if (ceAdjacent.getItem().equals(Item.CRATE)) {
                    Position.crateMove(p, ceAdjacent, 'U');
                }
            }
        } catch (BoardCellException e) {
            OUT.println("Move out of the board !" + "\n");
        }
    }

    /**
     * Move the player (and the crates) to the left direction.
     *
     * @param p player cell
     */
    private static void moveLeft(Cell p) {
        try {
            Cell ceAdjacent = getCell(p.getRow(), p.getColumn() - 1);

            if (!Position.blockCrate(ceAdjacent, getCratesBeforeUpdateTargets())) {
                Position.movePlayerCell(p, ceAdjacent);

                if (ceAdjacent.getItem().equals(Item.CRATE)) {
                    Position.crateMove(p, ceAdjacent, 'L');
                }
            }
        } catch (BoardCellException e) {
            OUT.println("Move out of the board !" + "\n");
        }
    }

    /**
     * Move the player (and the crates) to the down direction.
     *
     * @param p player cell
     */
    private static void moveDown(Cell p) {
        try {
            Cell ceAdjacent = getCell(p.getRow() + 1, p.getColumn());

            if (!Position.blockCrate(ceAdjacent, getCratesBeforeUpdateTargets())) {
                Position.movePlayerCell(p, ceAdjacent);

                if (ceAdjacent.getItem().equals(Item.CRATE)) {
                    Position.crateMove(p, ceAdjacent, 'D');
                }
            }
        } catch (BoardCellException e) {
            OUT.println("Move out of the board !" + "\n");
        }
    }

    /**
     * Move the player (and the crates) to the right direction.
     *
     * @param p player cell
     */
    private static void moveRight(Cell p) {
        try {
            Cell ceAdjacent = getCell(p.getRow(), p.getColumn() + 1);

            if (!Position.blockCrate(ceAdjacent, getCratesBeforeUpdateTargets())) {
                Position.movePlayerCell(p, ceAdjacent);

                if (ceAdjacent.getItem().equals(Item.CRATE)) {
                    Position.crateMove(p, ceAdjacent, 'R');
                }
            }
        } catch (BoardCellException e) {
            OUT.println("Move out of the board !" + "\n");
        }
    }

    /**
     * Determine if the player has win the game.
     *
     * @return true if the player has placed all the crates into the targets
     * or false if it isn't
     */
    private static boolean victory() {
        boolean winner = false;

        if (getAllTargets().isEmpty()) {
            OUT.println("Congratulations !\n");
            winner = true;
        }

        return winner;
    }
}