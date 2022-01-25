package com.sokoban.service.base;

import java.util.HashSet;

/**
 * A modifier for a cell position.
 */
public class Position extends Game {

    /**
     * Position constructor.
     * 
     * @param title the title of the board
     * @param sizeX the length of the board abscissa
     * @param sizeY the length of the board ordinate
     */
    public Position(String title, int sizeX, int sizeY) {
        super(title, sizeX, sizeY);
    }

    /**
     * Move the player to a void cell.
     * 
     * @param p a player cell
     * @param adj a adjacent cell to the player cell
     */
    static void movePlayerCell(Cell p, Cell adj) {
        if (adj.getItem().equals(Item.VOID)) {
            adj.setItem(Item.PLAYER);
            p.setItem(Item.VOID);
        }
    }

    /**
     * Move a crate or multiple crates to a void cell or a target.
     * 
     * @param p a player cell
     * @param adj a adjacent cell to the player cell
     * @param direction a direction to move
     * @throws BoardCellException the cell coordinates of the board are
     * not valid
     */
    static void crateMove(Cell p, Cell adj, char direction) throws BoardCellException {
        switch (direction) {
            // up
            case 'U':
                // multiple crates
                if (getAllCratesCol(p).size() > 0 
                        && !getCell(p.getRow() - 2, p.getColumn()).getItem().equals(Item.VOID)) {
                    Cell ceCrateMoved = getCell(p.getRow() - (getAllCratesCol(p).size() + 1), p.getColumn());

                    if (adj.getItem().equals(Item.CRATE) 
                            && ceCrateMoved.getItem().equals(Item.VOID)
                            || ceCrateMoved.getItem().equals(Item.TARGET)) {
                        adj.setItem(Item.PLAYER);
                        ceCrateMoved.setItem(Item.CRATE);
                        p.setItem(Item.VOID);
                    }
                // unique crate    
                } else if (getAllCratesCol(p).size() > 0 
                       && getCell(p.getRow() - 2, p.getColumn()).getItem().equals(Item.VOID)) {

                    if (adj.getItem().equals(Item.CRATE)) {
                        Cell ceUniqueCrateMoved = getCell(p.getRow() - 2, p.getColumn());
                        ceUniqueCrateMoved.setItem(Item.CRATE);
                        adj.setItem(Item.PLAYER);
                        p.setItem(Item.VOID);
                    }
                }               
                break;
            // left
            case 'L':
                // multiple crates
                if (getAllCratesRow(p).size() > 0 
                        && !getCell(p.getRow(), p.getColumn() - 2).getItem().equals(Item.VOID)) {
                    Cell ceCrateMoved = getCell(p.getRow(), p.getColumn() - (getAllCratesRow(p).size() + 1));

                    if (adj.getItem().equals(Item.CRATE) 
                            && ceCrateMoved.getItem().equals(Item.VOID)
                            || ceCrateMoved.getItem().equals(Item.TARGET)) {
                        adj.setItem(Item.PLAYER);
                        ceCrateMoved.setItem(Item.CRATE);
                        p.setItem(Item.VOID);
                    }
                // unique crate 
                } else if (getAllCratesRow(p).size() > 0
                        && getCell(p.getRow(), p.getColumn() - 2).getItem().equals(Item.VOID)) {

                    if (adj.getItem().equals(Item.CRATE)) {
                        Cell ceUniqueCrateMoved = getCell(p.getRow(), p.getColumn() - 2);
                        ceUniqueCrateMoved.setItem(Item.CRATE);
                        adj.setItem(Item.PLAYER);
                        p.setItem(Item.VOID);
                    }
                }               
                break;
            // down
            case 'D':
                // multiple crates
                if (getAllCratesCol(p).size() > 0 
                        && !getCell(p.getRow() + 2, p.getColumn()).getItem().equals(Item.VOID)) {
                    Cell ceCrateMoved = getCell(p.getRow() + (getAllCratesCol(p).size() + 1), p.getColumn());

                    if (adj.getItem().equals(Item.CRATE) 
                            && ceCrateMoved.getItem().equals(Item.VOID)
                            || ceCrateMoved.getItem().equals(Item.TARGET)) {
                        adj.setItem(Item.PLAYER);
                        ceCrateMoved.setItem(Item.CRATE);
                        p.setItem(Item.VOID);
                    }
                // unique crate 
                } else if (getAllCratesCol(p).size() > 0 
                        && getCell(p.getRow() + 2, p.getColumn()).getItem().equals(Item.VOID)) {

                    if (adj.getItem().equals(Item.CRATE)) {
                        Cell ceUniqueCrateMoved = getCell(p.getRow() + 2, p.getColumn());
                        ceUniqueCrateMoved.setItem(Item.CRATE);
                        adj.setItem(Item.PLAYER);
                        p.setItem(Item.VOID);
                    }
                }               
                break;
            // right
            case 'R':
                // multiple crates
                if (getAllCratesRow(p).size() > 0
                        && !getCell(p.getRow(), p.getColumn() + 2).getItem().equals(Item.VOID)) {
                    Cell ceCrateMoved = getCell(p.getRow(), p.getColumn() + (getAllCratesRow(p).size() + 1));

                    if (adj.getItem().equals(Item.CRATE) 
                            && ceCrateMoved.getItem().equals(Item.VOID)
                            || ceCrateMoved.getItem().equals(Item.TARGET)) {
                        adj.setItem(Item.PLAYER);
                        ceCrateMoved.setItem(Item.CRATE);
                        p.setItem(Item.VOID);
                    }
                // unique crate     
                } else if (getAllCratesRow(p).size() > 0 
                        && getCell(p.getRow(), p.getColumn() + 2).getItem().equals(Item.VOID)) {

                    if (adj.getItem().equals(Item.CRATE)) {
                        Cell ceUniqueCrateMoved = getCell(p.getRow(), p.getColumn() + 2);
                        ceUniqueCrateMoved.setItem(Item.CRATE);
                        adj.setItem(Item.PLAYER);
                        p.setItem(Item.VOID);
                    }
                }
                break;
            default:
                System.out.println("Unknown direction !\n");
                break;
        }
    }

    /**
     * Block a crate to the player if it has reach a target.
     * 
     * @param ceCrate a Crate cell
     * @param cratesBeforeUpdateTargets a HashSet collection of the crates
     * from the board before an update of the targets (deletion of 
     * target)
     * @return true if the crate has reach a target, false if it isn't
     */
    static boolean blockCrate(Cell ceCrate, HashSet<Cell> cratesBeforeUpdateTargets) {
        boolean crateBlocked;

        for (Cell ce : cratesBeforeUpdateTargets) {
            if (ceCrate.getRow() == ce.getRow() && ceCrate.getColumn() == ce.getColumn()) {
                crateBlocked = true;
                ceCrate.setBlockState(crateBlocked);
            }
        }

        return ceCrate.getBlockState();
    }
}