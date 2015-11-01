package sprites;


/** A Sprite subclass to represent a clean hallway in game.
 * @author Jack
 */
public class CleanHallway extends Sprite {

  /** Initialize a CleanHallway object with given symbol and given coordinates.
   * @param symbol The symbol to represent a CleanHallway object
   * @param row The row coordinate of CleanHallway object
   * @param column The column coordinate of CleanHallway object
   */
  public CleanHallway(char symbol, int row, int column) {
    super(symbol, row, column);
  }

  /*
   * (non-Javadoc)
   * 
   * @see sprites.Sprite#getSymbol()
   */
  @Override
  public char getSymbol() {
    return super.getSymbol();
  }

  /*
   * (non-Javadoc)
   * 
   * @see sprites.Sprite#getRow()
   */
  @Override
  public int getRow() {
    return super.getRow();
  }

  /*
   * (non-Javadoc)
   * 
   * @see sprites.Sprite#getColumn()
   */
  @Override
  public int getColumn() {
    return super.getColumn();
  }

  /*
   * (non-Javadoc)
   * 
   * @see sprites.Sprite#toString()
   */
  @Override
  public String toString() {
    return super.toString();
  }
}
