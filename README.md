# sokoban

[Sokoban](https://en.wikipedia.org/wiki/Sokoban) game adapted in Java.  

- University project.  

__Global objective :__ 
```
The program must work in text mode. It's a Java program that allows you to run a one-player game. The different boards
must be created "in hard" and with the help of a text file. These must be stored in a database.
```
______________________________________

# Goal and rules

The player's objective is to move all the crates on the
the board to the indicated targets.

- The player can only move to adjacent empty squares.
- The player can push a series of crates, if an empty square is behind.

______________________________________

# Example of a game

![example](/img/example.png)

______________________________________

# Structure

![sokoban-uml-general-specification](/img/sokoban-uml-general-specification.png)

______________________________________

# Features

The main program consists of :
- display the available boards in the database
- choose a board from the available boards in the database
- choose to leave the game

For a game :
- the program displays the board and asks for a single move or a multiple move (combination of moves) as well as a proposal to return to the the main menu
- a move is made and a new board is displayed adding
the modification(s)

At the end of the game :
- the program displays a simple message if the player wins
- the program returns to the main menu