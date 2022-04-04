package com.sokoban.boardsDatabaseManagement;

import com.sokoban.service.base.BoardCellException;
import com.sokoban.service.base.Board;
import com.sokoban.service.boardBuild.FileBoardBuilder;
import com.sokoban.service.boardBuild.TextBoardBuilder;
import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * A database for boards.
 */
public class BoardsDatabase {
    private static final String BOARDS_DB_DIRECTORY_PATH = "db/";
    private static final String BOARDS_DB_FILE_PATH = "db/boards-db.sqlite3";
    private static final String BOARDS_DB_URL_PATH = "jdbc:sqlite:" + "db/boards-db.sqlite3";
    private static final PrintStream OUT = System.out;

    /**
     * Boards database initialization and load a SQLite driver.
     */
    public static void loadDatabase() {
        File boardsDatabaseDirectory = new File(BOARDS_DB_DIRECTORY_PATH);
        File boardsDatabaseFile = new File(BOARDS_DB_FILE_PATH);

        // verify if the directory "db" is created
        if (!boardsDatabaseDirectory.exists()) {
            boardsDatabaseDirectory.mkdirs();
        }

        try {
            // verify if the file "boards-db.sqlite3" is created in "db" directory
            if (!boardsDatabaseFile.exists()) {
                createNewDatabase();
            }

            loadDriverSQLite();

            DriverManager.getConnection(BOARDS_DB_URL_PATH);
        } catch (SQLException e) {
            OUT.println("Database not found : " + BOARDS_DB_URL_PATH);
            System.exit(1);
        }
    }

    /**
     * Load a SQLite driver.
     */
    private static void loadDriverSQLite() {
        String sqlite_driver = "org.sqlite.JDBC";

        try {
            Class.forName(sqlite_driver);
        } catch (ClassNotFoundException e) {
            OUT.println("Driver not found : " + sqlite_driver);
            System.exit(1);
        }
    }

    /**
     * Create a new database without any boards.
     */
    static void createNewDatabase() {
        try {
            try ( Connection c = DriverManager.getConnection(BOARDS_DB_URL_PATH)) {
                Statement s = c.createStatement();
                // table Boards
                s.execute("DROP TABLE IF EXISTS Boards");
                s.execute("CREATE TABLE Boards (\n"
                        + "\tid_board TEXT NOT NULL,\n"
                        + "\ttitle_board TEXT NOT NULL,\n"
                        + "\tsizeX INTEGER NOT NULL,\n"
                        + "\tsizeY INTEGER NOT NULL,\n"
                        + "\tPRIMARY KEY (id_board)"
                        + ");"
                );

                // table Rows
                s.execute("DROP TABLE IF EXISTS Rows");
                s.execute("CREATE TABLE Rows (\n"
                        + "\tid_board TEXT NOT NULL,\n"
                        + "\trow_num INTEGER NOT NULL,\n"
                        + "\tdescription TEXT NOT NULL\n"
                        + ");"
                );

                OUT.println("Database created. No boards added yet.\n");
            }
        } catch (SQLException e) {
            OUT.println(e.getMessage() + "\n");
        }
    }

    /**
     * List boards.
     */
    public static void list() {
        try {
            try ( Connection c = DriverManager.getConnection(BOARDS_DB_URL_PATH)) {
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM Boards");

                OUT.println("| id_board\t | title_board\t\t | sizeX | sizeY |");
                OUT.println("|------------|-------------------|-------|-------|");

                while (rs.next()) {
                    String id = rs.getString("id_board");
                    String title = rs.getString("title_board");
                    int sizeX = rs.getInt("sizeX");
                    int sizeY = rs.getInt("sizeY");
                    OUT.format("| %s\t | %s\t | %d\t | %s\t |\n", id, title, sizeX, sizeY);
                }
            }
        } catch (SQLException e) {
            OUT.println(e.getMessage() + "\n");
        }
    }

    /**
     * Add a board to a database using a file.
     *
     * @param id board ID
     * @param fileBuilder FileBoardBuilder from the file containing the
     * board
     * @param board board
     * @throws BoardCellException the cell coordinates of the board are not
     * valid
     */
    static void add(String id, FileBoardBuilder fileBuilder, Board board) throws BoardCellException {
        fileBuilder.readFileContent();
                    
        try {           
            try ( Connection c = DriverManager.getConnection(BOARDS_DB_URL_PATH)) {
                // table Boards
                PreparedStatement ps = c.prepareStatement("INSERT INTO Boards"
                        + " VALUES (?, ?, ?, ?)");
                ps.setString(1, id);
                ps.setString(2, board.getTitle());
                ps.setInt(3, Board.getSizeX());
                ps.setInt(4, Board.getSizeY());
                ps.executeUpdate();

                // table Rows
                for (int row = 0; row < fileBuilder.getRows().size(); row++) {
                    PreparedStatement ps2 = c.prepareStatement("INSERT INTO Rows"
                            + " VALUES(?, ?, ?)");
                    ps2.setString(1, id);
                    ps2.setInt(2, row);
                    ps2.setString(3, fileBuilder.getRows().get(row));
                    ps2.executeUpdate();
                }
            }
        } catch (SQLException e) {
            // ...
        }
    }

    /**
     * Remove a board from a database.
     *
     * @param id board ID
     */
    static void remove(String id) {
        try {
            try ( Connection c = DriverManager.getConnection(BOARDS_DB_URL_PATH)) {
                idVerification(c, id);

                // table Boards
                PreparedStatement ps2 = c.prepareStatement("DELETE FROM Boards"
                        + " WHERE id_board = ?");
                ps2.setString(1, id);
                ps2.executeUpdate();

                // table Rows
                PreparedStatement ps3 = c.prepareStatement("DELETE FROM Rows"
                        + " WHERE id_board = ?");
                ps3.setString(1, id);
                ps3.executeUpdate();
            }
        } catch (SQLException e) {
            OUT.println("Invalid board ID." + "\n");
        }
    }

    /**
     * Get a specific board from a database.
     *
     * @param id board ID
     * @return the board
     * @throws BoardCellException the cell coordinates of the board are not
     * valid
     */
    public static Board get(String id) throws BoardCellException {
        Board b = new Board(null, 0, 0);

        try {
            try ( Connection c = DriverManager.getConnection(BOARDS_DB_URL_PATH)) {
                // table Boards
                PreparedStatement ps = c.prepareStatement("SELECT * FROM Boards"
                        + " WHERE id_board = ?");
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                // TextBoardBuilder
                TextBoardBuilder builder = new TextBoardBuilder(rs.getString("title_board"), rs.getInt("sizeX"), rs.getInt("sizeY"));
                b = builder.build();

                // table Rows
                PreparedStatement ps2 = c.prepareStatement("SELECT * FROM Rows"
                        + " WHERE id_board = ?");
                ps2.setString(1, id);
                ResultSet rs2 = ps2.executeQuery();

                OUT.println("| id_board\t | row_num | description");
                OUT.println("|----------------|---------|");

                while (rs2.next()) {
                    int rowNum = rs2.getInt("row_num");
                    String description = rs2.getString("description");
                    OUT.format("| %s\t | %d\t   | %s\t|\n", id, rowNum, description);

                    builder.addRow(description, rowNum);
                }
                OUT.print("\n");
                
            }
        } catch (SQLException e) {
            OUT.println("Invalid board ID." + "\n");
        }

        return b;
    }

    /**
     * Get a specific board id and verify it.
     *
     * @param c connection
     * @param id board id
     * @throws SQLException the database access error or other errors
     */
    private static void idVerification(Connection c, String id) throws SQLException {
        // research ID in table Boards
        PreparedStatement ps = c.prepareStatement("SELECT * FROM Boards"
                + " WHERE id_board = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        // TextBoardBuilder to verify ID
        TextBoardBuilder builder = new TextBoardBuilder(rs.getString("title_board"), rs.getInt("sizeX"), rs.getInt("sizeY"));
        builder.build();
    }
}