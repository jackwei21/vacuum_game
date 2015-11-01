package sprites;


/** A Sprite subclass to represent a dirt in game.
 * @author Jack
 */
public class Dirt extends Sprite {

  protected int value;

  /** Initialize a Dirt object with given symbol, coordinates, and score value of Dirt.
   * @param symbol The symbol to represent a Dirt object
   * @param row The row coordinate of Dirt object
   * @param column The column coordinate of Dirt object
   * @param value The score value of Dirt object
   */
  public Dirt(char symbol, int row, int column, int value) {
    super(symbol, row, column);
    this.value = value;
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

  /** Returns Dirt's score value.
   * @return the value
   */
  public int getValue() {
    return value;
  }
}
