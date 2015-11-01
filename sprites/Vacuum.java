package sprites;


/** A Sprite subclass to represent a vacuum in game.
 * @author Jack
 */
public class Vacuum extends Sprite implements Moveable {

  private int score;
  private int capacity;
  private int fullness;
  private Sprite under;

  /** Initialize a Vacuum object with given symbol, coordinates, and vacuum capacity.
   * @param symbol The symbol to represent a Vacuum object
   * @param row The row coordinate of Vacuum object
   * @param column The column coordinate of Vacuum object
   * @param capacity The maximum volume of Vacuum object
   */
  public Vacuum(char symbol, int row, int column, int capacity) {
    super(symbol, row, column);
    this.capacity = capacity;
  }

  /** Vacuum cleans and increment its score count if Vacuum is not full.
   * Returns true if Vacuum cleaned, false is Vacuum is full and unable to clean.
   * @param score Score value of the object being cleaned  
   * @return True if Vacuum cleaned, false is Vacuum is full and unable to clean.
   */
  public boolean clean(int score) {
    
    // returns false is Vacuum is full
    if (fullness >= capacity) {
      return false;
    } else {
      // increment fullness and score as Vacuum cleans
      fullness += game.Constants.FULLNESS_INC;
      this.score += score;
      return true;
    }
  }

  /** Empties Vacuum by setting its fullness to 0.
   */
  public void empty() {
    fullness = 0;
  }

  /** Returns Vacuum's score.
   * @return the score The score of Vacuum, accumulated from cleaning dirts and dust balls
   */
  public int getScore() {
    return score;
  }

  /** Returns the Sprite under Vacuum.
   * @return the under The Sprite cell under Vacuum
   */
  public Sprite getUnder() {
    return under;
  }

  /** Sets the Sprite under Vacuum.
   * @param under The new Sprite cell to be placed under Vacuum
   */
  public void setUnder(Sprite under) {
    this.under = under;
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
