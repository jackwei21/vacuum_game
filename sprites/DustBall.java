package sprites;

/** A Sprite subclass to represent a dust ball in game.
 * @author Jack
 */
public class DustBall extends Dirt implements Moveable {

  /** Initialize a DustBall object with given symbol, coordinates, and score value of DustBall.
   * @param symbol The symbol to represent a DustBall object
   * @param row The row coordinate of DustBall object
   * @param column The column coordinate of DustBall object
   * @param value The score value of DustBall object
   */
  public DustBall(char symbol, int row, int column, int value) {
    super(symbol, row, column, value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see sprites.Moveable#moveTo(int, int)
   */
  @Override
  public void moveTo(int row, int column) {
    this.row = row;
    this.column = column;
  }
}
