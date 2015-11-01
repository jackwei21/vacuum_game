package sprites;


/** A Sprite interface for all types of cell of game.
 * @author Jack
 */
public abstract class Sprite {

  protected char symbol;
  protected int row;
  protected int column;

  /** Initialize a Sprite object of given symbol and given coordinates.
   * @param symbol The symbol to represent a Sprite object
   * @param row The row coordinate of Sprite object
   * @param column The column coordinate of Sprite object
   */
  public Sprite(char symbol, int row, int column) {
    this.symbol = symbol;
    this.row = row;
    this.column = column;
  }

  /** Returns Sprite's symbol.
   * @return the symbol
   */
  public char getSymbol() {
    return symbol;
  }

  /** Returns Sprite's row coordinate.
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /** Returns Sprite's column coordinate.
   * @return the column
   */
  public int getColumn() {
    return column;
  }

  /** Returns a String representation of Sprite.
   * In format of a length one String consisting of Sprite's symbol. 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.valueOf(symbol);
  }
}
