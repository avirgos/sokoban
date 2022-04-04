package com.sokoban.boardsDatabaseManagement;

import com.sokoban.service.base.BoardCellException;
import com.sokoban.service.boardBuild.BoardBuildException;
import com.sokoban.service.boardBuild.FileBoardBuilder;
import java.io.File;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * A database administrator.
 */
public class Administrator {
    private static final Scanner IN = new Scanner(System.in);
    private static final PrintStream OUT = System.out; 
    private static final File LEVELS_DIRECTORY = new File("levels/");

    /**
     * Remove spaces from the string.
     *
     * @return the line lacking in spaces, wrote by an user
     */
    private static String read() {
        return IN.nextLine().trim();
    }

    /**
     * Main program.
     *
     * @param args the command line arguments
     * @throws SQLException the database access error or other errors
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     * @throws BoardBuildException the board cannot be built
     */
    public static void main(String[] args) throws SQLException, BoardCellException, BoardBuildException {
        BoardsDatabase.loadDatabase();
        OUT.println("| Administration Interface |" + "\n");
        boolean loop = true;
        while (loop) {
            OUT.println("1. Create new database");
            OUT.println("2. List boards");
            OUT.println("3. Show board");
            OUT.println("4. Add board from file");
            OUT.println("5. Remove board from database");
            OUT.println("6. Quit.");
            OUT.println("\n" + "* Type a number to use a command.");
            String command = read();
            OUT.println();
            switch (command) {
                case "1":
                    executeCreateDBCommand();
                    break;
                case "2":
                    executeListCommand();
                    break;
                case "3":
                    executeShowCommand();
                    break;
                case "4":
                    executeAddCommand();
                    break;
                case "5":
                    executeRemoveCommand();
                    break;
                case "6":
                    loop = false;
                    break;
                default:
                    OUT.println("Command not found : \"" + command + "\"." + "\n");
                    break;
            }
        }
    }

    /**
     * Execute a "Create new database" command.
     */
    private static void executeCreateDBCommand() {
        BoardsDatabase.createNewDatabase();
    }

    /**
     * Execute a "List boards" command.
     */
    private static void executeListCommand() {
        BoardsDatabase.list();
        OUT.print("\n");
    }

    /**
     * Execute a "Show board" command.
     * 
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    private static void executeShowCommand() throws BoardCellException {
        // ID
        OUT.println("Enter the board ID : ");
        String idShow = read();
        OUT.print("\n");

        BoardsDatabase.get(idShow);
    }

    /**
     * Execute a "Add board from file" command.
     * 
     * @throws SQLException the database access error or other errors
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     * @throws BoardBuildException the board cannot be built
     */
    private static void executeAddCommand() throws SQLException, BoardCellException, BoardBuildException {
        // ID
        OUT.println("Enter the board ID : ");
        String idAdd = read();
        OUT.print("\n");

        OUT.println("|--------------------------------|");
        OUT.println("|                                |");
        OUT.println("| Files in \" levels \" directory. |");
        OUT.println("|                                |");
        OUT.println("|--------------------------------|");
        listBoardsInDirectory(LEVELS_DIRECTORY);
        OUT.print("\n");
        
        // Path
        OUT.println("Enter the board name (with the file extension).");
        String path = "levels/" + read();
        OUT.print("\n");

        // File
        File fPath = new File(path);
        FileBoardBuilder fileBuilder = new FileBoardBuilder(fPath);
        fileBuilder.readFileBoardInformations();
        
        BoardsDatabase.add(idAdd, fileBuilder, fileBuilder.build());
    }

    /**
     * Execute a "Remove board from database" command.
     */
    private static void executeRemoveCommand() {
        // ID
        OUT.println("Enter the board ID : ");
        String idRemove = read();
        OUT.print("\n");
        
        BoardsDatabase.remove(idRemove);
    }
    
    /**
     * List files in a directory.
     * 
     * @param directory directory 
     */
    private static void listBoardsInDirectory(File directory) {
        for (File fileEntry : directory.listFiles()) {
            if (fileEntry.isDirectory()) {
                listBoardsInDirectory(fileEntry);
            } else {
                OUT.println("-> " + fileEntry.getName());
            }
        }
    }
}