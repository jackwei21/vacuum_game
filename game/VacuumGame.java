package game;


import sprites.CleanHallway;
import sprites.Dirt;
import sprites.Dumpster;
import sprites.DustBall;
import sprites.Sprite;
import sprites.Vacuum;
import sprites.Wall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A class that represents the basic functionality of the vacuum game. This class is responsible for
 * performing the following operations: 1. At creation, it initializes the instance variables used
 * to store the current state of the game. 2. When a move is specified, it checks if it is a legal
 * move and makes the move if it is legal. 3. It reports information about the current state of the
 * game when asked.
 */
public class VacuumGame {

  // a random number generator to move the DustBalls
  private Random random;

  // the grid
  private Grid<Sprite> grid;

  // the first player
  private Vacuum vacuum1;

  // the second player
  private Vacuum vacuum2;

  // the dirt (both static dirt and mobile dust balls)
  private List<Dirt> dirts;

  // the dustballs
  private List<DustBall> dustBalls;

  // the dumpsters
  private List<Dumpster> dumpsters;

  /**
   * Creates a new VacuumGame that corresponds to the given input text file. Assumes that the input
   * file has one or more lines of equal lengths, and that each character in it (other than newline)
   * is a character that represents one of the sprites in this game.
   * 
   * @param layoutFileName
   *          path to the input grid file
   */
  public VacuumGame(String layoutFileName) throws IOException {
    dirts = new ArrayList<Dirt>();
    dumpsters = new ArrayList<Dumpster>();
    dustBalls = new ArrayList<DustBall>();
    random = new Random();

    // open the file, read the contents, and determine
    // dimensions of the grid
    int[] dimensions = getDimensions(layoutFileName);
    grid = new ArrayGrid<Sprite>(dimensions[0], dimensions[1]);

    // open the file again, read the contents, and store them in grid
    Scanner sc = new Scanner(new File(layoutFileName));

    // INITIALIZE THE GRID HERE
    for (int i = 0; i < grid.getNumRows(); i++) {
      // read line i in text file, break String line down to char array
      String row = sc.nextLine();
      char[] symbols = row.toCharArray();
      
      // initialize Sprite and add it to grid (and/or list) acoordingly to item in char array
      // Sprite would be in location i,j in grid
      // Dirt, Dumpster, and DustBall are added to their ArrayList in addition to grid setting
      for (int j = 0; j < grid.getNumColumns(); j++) {
        char symbol = symbols[j];
        
        if (symbol == Constants.WALL) {
          Wall wall = new Wall(Constants.WALL, i, j);
          grid.setCell(i, j, wall);
        } else if (symbol == Constants.CLEAN) {
          CleanHallway clean = new CleanHallway(Constants.CLEAN, i, j);
          grid.setCell(i, j, clean);
        } else if (symbol == Constants.P1) {
          vacuum1 = new Vacuum(Constants.P1, i, j, Constants.CAPACITY);
          CleanHallway clean = new CleanHallway(Constants.CLEAN, i, j);
          vacuum1.setUnder(clean);
          grid.setCell(i, j, vacuum1);
        } else if (symbol == Constants.P2) {
          vacuum2 = new Vacuum(Constants.P2, i, j, Constants.CAPACITY);
          CleanHallway clean = new CleanHallway(Constants.CLEAN, i, j);
          vacuum2.setUnder(clean);
          grid.setCell(i, j, vacuum2);
        } else if (symbol == Constants.DIRT) {
          Dirt dirt = new Dirt(Constants.DIRT, i, j, Constants.DIRT_SCORE);
          grid.setCell(i, j, dirt);
          dirts.add(dirt);
        } else if (symbol == Constants.DUMPSTER) {
          Dumpster dumpster = new Dumpster(Constants.DUMPSTER, i, j);
          grid.setCell(i, j, dumpster);
          dumpsters.add(dumpster);
        } else {
          DustBall dustBall = new DustBall(Constants.DUST_BALL, i, j, Constants.DUST_BALL_SCORE);
          grid.setCell(i, j, dustBall);
          dustBalls.add(dustBall);
        }
      }
    }
    sc.close();
  }

  /**
   * Returns the dimensions of the grid in the file named layoutFileName.
   * 
   * @param layoutFileName
   *          path of the input grid file
   * @return an array [numRows, numCols], where numRows is the number of rows and numCols is the
   *         number of columns in the grid that corresponds to the given input grid file
   * @throws IOException
   *           if cannot open file layoutFileName
   */
  private int[] getDimensions(String layoutFileName) throws IOException {

    Scanner sc = new Scanner(new File(layoutFileName));

    // find the number of columns
    String nextLine = sc.nextLine();
    int numCols = nextLine.length();

    int numRows = 1;

    // find the number of rows
    while (sc.hasNext()) {
      numRows++;
      nextLine = sc.nextLine();
    }

    sc.close();
    return new int[] { numRows, numCols };
  }

  /** Returns VacuumGame's grid.
   * @return the grid
   */
  public Grid<Sprite> getGrid() {
    return grid;
  }

  /** Returns VacuumGame's first Vacuum.
   * @return the vacuum1
   */
  public Vacuum getVacuumOne() {
    return vacuum1;
  }

  /** Returns VacuumGame's second Vacuum.
   * @return the vacuum2
   */
  public Vacuum getVacuumTwo() {
    return vacuum2;
  }

  /** Returns number of rows of VacuumGame's grid.
   * @return number of rows of VacuumGame's grid
   */
  public int getNumRows() {
    return grid.getNumRows();
  }

  /** Returns number of columns of VacuumGame's grid.
   * @return number of columns of VacuumGame's grid
   */
  public int getNumColumns() {
    return grid.getNumColumns();
  }

  /** Returns the Sprite stored in VacuumGame's grid at given coordinates.
   * @param row the row coordinate of Sprite in grid
   * @param col the column coordinate of Sprite in grid
   * @return the sprite located at given coordinates in grid
   */
  public Sprite getSprite(int row, int col) {
    return grid.getCell(row, col);
  }

  /** Moves vacuum and dustballs.
   * @param nextMove user input move key
   * @return true if input move key is a valid command, false otherwise
   */
  public boolean move(char nextMove) {

    boolean result = true;
    Vacuum currentVacuum = identifyVacuum(nextMove);
    int[] newCoord = getNewCoord(nextMove, currentVacuum);

    // checks if the input key is a valid move command
    if (!vacuumValidMove(newCoord)) {
      result = false;
    } else {
      moveVacuum(newCoord, currentVacuum);
    }

    // move all dustballs
    for (int i = 0; i < dustBalls.size(); i++) {
      moveDustBall(dustBalls.get(i));
    }
    return result;
  }

  /** Finds which Vacuum the user want to move, return vacuum1 or vacuum2 based on user input key.
   * @param nextMove user input move key
   * @return the Vacuum user wants to move
   */
  private Vacuum identifyVacuum(char nextMove) {
    // find which Vacuum the user wants to move
    if ((nextMove == Constants.P1_DOWN) || (nextMove == Constants.P1_LEFT)
        || (nextMove == Constants.P1_RIGHT) || (nextMove == Constants.P1_UP)) {
      return vacuum1;
    } else {
      return vacuum2;
    }
  }

  /** Calculates the vaccum move target tile and return its coordinate.
   * @param nextMove user input move key
   * @param currentVacuum the Vacuum user wants to move
   * @return coordinate of target tile to move Vacuum on
   */
  private int[] getNewCoord(char nextMove, Vacuum currentVacuum) {

    // get current coordinate
    int nextRow = currentVacuum.getRow();
    int nextCol = currentVacuum.getColumn();

    // find target tile based on user input key, return target tile coordinate
    switch (nextMove) {
      case Constants.P1_DOWN:
      case Constants.P2_DOWN:
        nextRow += 1;
        break;

      case Constants.P1_LEFT:
      case Constants.P2_LEFT:
        nextCol -= 1;
        break;

      case Constants.P1_RIGHT:
      case Constants.P2_RIGHT:
        nextCol += 1;
        break;

      case Constants.P1_UP:
      case Constants.P2_UP:
        nextRow -= 1;
        break;

      default:
        break;
    }
    return new int[] { nextRow, nextCol };
  }

  /** Checks if the user input move is a valid move.
   * @param newCoord coordinate of target tile to move Vacuum on
   * @return true is move is valid, false if move is invalid
   */
  private boolean vacuumValidMove(int[] newCoord) {

    // checks if the target tile is within grid boundary
    if (!checkEdges(newCoord)) {
      return false;
    }

    // get target tile, and check if it is a valid space to move on
    // walls and other vacuums are not valid target 
    Sprite nextCell = grid.getCell(newCoord[0], newCoord[1]);
    if ((nextCell instanceof Wall) || (nextCell instanceof Vacuum)) {
      return false;
    }
    return true;
  }

  private boolean checkEdges(int[] newCoord) {
    // checks if target tile is within grid boundary
    // return true if next move is limited in grid, false if it is out of grid
    if ((newCoord[0] < 0) || (newCoord[0] > grid.getNumRows() - 1) || (newCoord[1] < 0)
        || (newCoord[1] > grid.getNumColumns() - 1)) {
      return false;
    }
    return true;
  }

  /** Move Vacuum onto targeted tile.
   * The Vacuum will clean if it is not full and targeted tile is Dirt or DustBall.
   * The Vacuum wiil not clean if it is full.
   * The Vacuum will be emptied if targeted tile is Dumpster.
   * @param newCoord coordinate of target tile to move Vacuum on
   * @param currentVacuum the Vacuum user wants to move
   */
  private void moveVacuum(int[] newCoord, Vacuum currentVacuum) {

    // get target tile and cuurent coordinate
    Sprite nextCell = grid.getCell(newCoord[0], newCoord[1]);
    int[] oldCoord = { currentVacuum.getRow(), currentVacuum.getColumn() };

    
    // checks the type of target tile to decide cooresponding action
    // changes target tile if it is cleanable and vacuum is not full
    // if Dirt/DustBall is cleaned, it will be removed from ArrayList of Dirt/DustBall
    if (nextCell instanceof Dumpster) {
      currentVacuum.empty();
    } else if ((nextCell instanceof DustBall) && (currentVacuum.clean(Constants.DUST_BALL_SCORE))) {
      dustBalls.remove(grid.getCell(newCoord[0], newCoord[1]));
      nextCell = new CleanHallway(Constants.CLEAN, newCoord[0], newCoord[1]);
    } else if ((nextCell instanceof Dirt) && (currentVacuum.clean(Constants.DIRT_SCORE))) {
      dirts.remove(grid.getCell(newCoord[0], newCoord[1]));
      nextCell = new CleanHallway(Constants.CLEAN, newCoord[0], newCoord[1]);
    }
    // change old Vacuum spot in grid to what was under vacuum
    // give vacuum the target tile to be its below tile
    // move vacuum and update grid
    grid.setCell(oldCoord[0], oldCoord[1], currentVacuum.getUnder());
    currentVacuum.setUnder(nextCell);
    currentVacuum.moveTo(newCoord[0], newCoord[1]);
    grid.setCell(newCoord[0], newCoord[1], currentVacuum);

  }
  // check possiable coords -> select one -> set previous tile as dirt -> move
  // if no coord to take, just return

  /** Move DustBall in a random direction by one tile.
   * Move is invalid if target slot is Wall, Dumpster, Vacuum, or other DustBall.
   * If random move is invalid, DustBall will stay in its original position.
   */
  private void moveDustBall(DustBall dustBall) {
    // get current coordinate, let target tile coordinate be same as current coordinate
    int[] oldCoord = { dustBall.getRow(), dustBall.getColumn() };
    int[] newCoord = { dustBall.getRow(), dustBall.getColumn() };

    // change target tile coordinate to correspond random movement
    switch (random.nextInt(4)) {
      case 0:
        newCoord[1] -= 1;
        break;
      case 1:
        newCoord[0] -= 1;
        break;
      case 2:
        newCoord[1] += 1;
        break;
      case 3:
        newCoord[0] += 1;
        break;
      default:
        break;
    }

    // if target tile is out of grid, the DustBall will not move
    if (!checkEdges(newCoord)) {
      return;
    }

    // get the target tile from grid
    Sprite nextCell = grid.getCell(newCoord[0], newCoord[1]);

    // if target tile is CleanHallway and Dirt (not Dustball), then move accordingly
    // aka if target tile is a valid move
    if ((nextCell instanceof CleanHallway)
        || ((nextCell instanceof Dirt) && !(nextCell instanceof DustBall))) {
      // initialize a new Dirt to leave behind, add it to list of Dist
      Dirt newDirt = new Dirt(Constants.DIRT, oldCoord[0], oldCoord[1], Constants.DIRT_SCORE);
      dirts.add(newDirt);
      // if DustBall was not under a full Vacuum, then it behaves simply
      if (!(grid.getCell(oldCoord[0], oldCoord[1]) instanceof Vacuum)) {
        // if target tile is dirt, remove the dirt created and just move
        if (nextCell instanceof Dirt) {
          dirts.remove(grid.getCell(newCoord[0], newCoord[1]));
        }
        // if target tile is CleanHallway, leave Dirt on past tile and then move
        grid.setCell(oldCoord[0], oldCoord[1], newDirt);
      } else {
        // DustBall was under a full Vacuum
        // Since DustBall moves, set the tile below Vacuum to dirt
        // if target tile is dirt, remove the dirt created and just move
        ((Vacuum) grid.getCell(oldCoord[0], oldCoord[1])).setUnder(newDirt);
        if (nextCell instanceof Dirt) {
          dirts.remove(grid.getCell(newCoord[0], newCoord[1]));
        }
      }
      // move the DustBall and update grid
      dustBall.moveTo(newCoord[0], newCoord[1]);
      grid.setCell(newCoord[0], newCoord[1], dustBall);
    }
    return;
  }

  /** Checks if game is over, where all Dirts and DustBalls are cleaned.
   * @return true if no more Dirt and DustBall left, false otherwise
   */
  public boolean gameOver() {
    // check by seeing if list of Dirt and DustBall is empty
    if (dirts.isEmpty() && dustBalls.isEmpty()) {
      return true;
    }
    return false;
  }

  /** Returns an integer to represent the Vacuum with highest Score in game.
   * 0 if vacuum1 has same score as vacuum2.
   * 1 if vacuum1 has the highest score.
   * 2 if vacuum2 has the highest score.
   * @return an integer to represent the Vacuum with highest Score in game
   */
  public int getWinner() {
    // compare the scores and return int value accordingly
    if (vacuum1.getScore() > vacuum2.getScore()) {
      return 1;
    } else if (vacuum1.getScore() < vacuum2.getScore()) {
      return 2;
    }
    return 0;
  }
}
