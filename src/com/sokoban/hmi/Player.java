package com.sokoban.hmi;

import com.sokoban.service.base.Game;
import com.sokoban.service.base.BoardCellException;
import com.sokoban.service.base.Board;
import com.sokoban.data.boardsDbMaintenance.BoardsDatabase;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * A player.
 */
public class Player {
    private static final Scanner IN = new Scanner(System.in);
    private static final PrintStream OUT = System.out;

    /**
     * Removes spaces from the string.
     *
     * @return a line lacking in spaces, wrote by an user
     */
    private static String read() {
        return IN.nextLine().trim();
    }

    /**
     * Main program.
     *
     * @param args the command line arguments
     * @throws BoardCellException if the cell coordinates of the board
     * are not valid
     */
    public static void main(String[] args) throws BoardCellException {
        BoardsDatabase.loadDatabase();
        
        OUT.println("| Welcome to Sokoban Project ! |" + "\n");
        
        boolean loop = true;
        while (loop) {
            OUT.println("1. List boards");
            OUT.println("2. Select a board and play");
            OUT.println("3. Quit." + "\n");
            OUT.println("* Type a number to use a command.");
            String command = read();
            OUT.println();
            switch (command) {
                case "1":
                    executeListCommand();
                    break;
                case "2":
                    executeSelectBoardCommand();
                    break;
                case "3":
                    loop = false;
                    break;
                default:
                    OUT.println("Command not found : \"" + command + "\"." + "\n");
                    break;
            }
        }
    }

    /**
     * Execute a "List boards" command.
     */
    private static void executeListCommand() {
        BoardsDatabase.list();
        OUT.print("\n");
    }

    /**
     * Execute a "Select a board and play" command.
     *
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    private static void executeSelectBoardCommand() throws BoardCellException {
        // ID
        OUT.println("Enter the board ID : ");
        String idShow = read();
        OUT.print("\n");

        Board b = BoardsDatabase.get(idShow);

        // verify board title
        if (b.getTitle() != null) {
            Game.interactMenu(b);    
        }
    }
}