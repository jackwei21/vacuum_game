package game;


/** A Grid subclass that uses two dimensional array to hold all cells of game board.
 * This is a generic subclass.
 * @author Jack
 */
public class ArrayGrid<T> implements Grid<T> {

  private int numRows;
  private int numColumns;
  private T[][] grid;

  /** Initialize a DustBall object with given dimensions.
   * @param numRows The number of rows in ArrayGrid
   * @param numColumns the number of columns in ArrayGrid
   */
  @SuppressWarnings("unchecked")
  public ArrayGrid(int numRows, int numColumns) {
    this.numRows = numRows;
    this.numColumns = numColumns;
    // downcast the Object array to array of type T
    grid = (T[][]) new Object[numRows][numColumns];
  }

  /*
   * (non-Javadoc)
   * 
   * @see game.Grid#setCell(int, int, java.lang.Object)
   */
  @Override
  public void setCell(int row, int column, T item) {
    grid[row][column] = item;
  }

  /*
   * (non-Javadoc)
   * 
   * @see game.Grid#getCell(int, int)
   */
  @Override
  public T getCell(int row, int column) {
    return grid[row][column];
  }

  /*
   * (non-Javadoc)
   * 
   * @see game.Grid#getNumRows()
   */
  @Override
  public int getNumRows() {
    return numRows;
  }

  /*
   * (non-Javadoc)
   * 
   * @see game.Grid#getNumColumns()
   */
  @Override
  public int getNumColumns() {
    return numColumns;
  }

  /** Checks if given Object and current ArrayGrid are equal to each other.
   * Equal as same type and all cells are same.
   */
  @SuppressWarnings("unchecked")
  public boolean equals(Object other) {

    // check if same type
    if (!(other instanceof ArrayGrid)) {
      return false;
    }

    // downcast given Object to ArrayGrid
    ArrayGrid<T> otherGrid = (ArrayGrid<T>) other;

    // check is same dimensions
    if (!((otherGrid.getNumRows() == numRows) && (otherGrid.getNumColumns() == numColumns))) {
      return false;
    } else {
      // check every cell
      for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numColumns; j++) {
          if (!(otherGrid.getCell(i, j).equals(grid[i][j]))) {
            return false;
          }
        }
      }
    }
    // two ArrayGrids are equal, return true by default
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see game.Grid#getNumColumns()
   */
  @Override
  public String toString() {
    // Create an empty String to hold ArrayGrid's String representation
    String gridText = "";

    // Append symbol of every Sprite cell to end of gridText
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numColumns; j++) {
        gridText += grid[i][j].toString();
      }
      // Append newline character to end of gridText when switching line
      gridText += "\n";
    }
    // Return gridText
    return gridText;
  }
}
