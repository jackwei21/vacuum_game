package ui;

import game.VacuumGame;

import java.util.Scanner;


/** Shows a text version user interface of the game.
 * @author Jack
 */
public class TextUi implements Ui {

  private VacuumGame game;

  /** Initialize a TextUi for the given VacuumGame.
   * @param game The VacuumGame of this TextUi
   */
  public TextUi(VacuumGame game) {
    this.game = game;
  }

  /** Launches the VacuumGame for user to play with.
   * @see ui.Ui#launchGame()
   */
  @Override
  public void launchGame() {

    // creates new Scanner to read user input
    Scanner scanner = new Scanner(System.in);

    // reads input from player and update game with respect to the input
    // ends game when gameOver() condition is met
    while (!game.gameOver()) {
      System.out.println(game.getGrid().toString());
      game.move(scanner.nextLine().charAt(0));
    }
    // closes Scanner
    scanner.close();
  }

  /** Displays message to indicate the winner to the game and shows score of winner.
   * @see ui.Ui#displayWinner()
   */
  @Override
  public void displayWinner() {
    // finds whether player 1 won, player 2 won, or tie game
    int winner = game.getWinner();

    // displays message accordingly to post game condition
    if (winner == 0) {
      System.out.println("It's a tie.");
    } else if (winner == 1) {
      System.out.println("Player 1 wins with a score of " + game.getVacuumOne().getScore() + " .");
    } else {
      System.out.println("Player 2 wins with a score of " + game.getVacuumTwo().getScore() + " .");
    }
  }
}
