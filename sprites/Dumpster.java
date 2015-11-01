package sprites;


/** A Sprite subclass to represent a dumpster in game.
 * @author Jack
 */
public class Dumpster extends Sprite {

  /** Initialize a Dumpster object with given symbol and given coordinates.
   * @param symbol The symbol to represent a Dumpster object
   * @param row The row coordinate of Dumpster object
   * @param column The column coordinate of Dumpster object
   */
  public Dumpster(char symbol, int row, int column) {
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
