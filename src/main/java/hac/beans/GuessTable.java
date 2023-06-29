package hac.beans;

import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * The GuessTable class represents the table used in the Wordle game to track guesses.
 * It contains information about the guesses made, the word to guess, and game statistics.
 */
@Component
public class GuessTable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567L;
    private SingleInput[][] table;
    private String wordToGuess;
    private int activeRow;
    private int numOfGuess;
    private boolean hasWon;
    private boolean isNewGame;
    private int[] count;

    /**
     * Constructs a GuessTable object with default settings.
     * The default number of guesses is set to 6.
     * The table is initialized and marked as not won.
     */
    public GuessTable() {
        this.numOfGuess = 6;
        createNewTable();
        this.isNewGame = true;
        this.hasWon = false;
    }

    /**
     * Creates a new table with the specified number of guesses.
     * Each guess slot in the table is initialized with a SingleInput object.
     * The first row is set as the active row.
     */
    private void createNewTable() {
        int COLS = 5;
        this.activeRow = 0;
        this.table = new SingleInput[numOfGuess][COLS];

        for (int i = 0; i < numOfGuess; i++) {
            for (int j = 0; j < COLS; j++) {
                this.table[i][j] = new SingleInput();
            }
        }
        this.table[activeRow][0].setActive(true);
        count = new int[3]; // one for each option of guess
    }

    /**
     * Sets the number of guesses allowed in the game.
     * This method recreates the table with the updated number of guesses.
     *
     * @param numOfGuess The number of guesses allowed.
     */
    public void setNumOfGuess(int numOfGuess) {
        this.numOfGuess = numOfGuess;
        createNewTable();
    }

    /**
     * Returns the number of guesses allowed in the game.
     *
     * @return The number of guesses allowed.
     */
    public int getNumOfGuess() {
        return this.numOfGuess;
    }

    /**
     * Sets the word to guess in the game.
     *
     * @param wordToGuess The word to guess.
     */
    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    /**
     * Returns the word to guess in the game.
     *
     * @return The word to guess.
     */
    public String getWordToGuess() {
        return this.wordToGuess;
    }
    public void setIsNewGame(boolean isNewGame) {
        this.isNewGame = isNewGame;
        if (this.isNewGame) {
            this.activeRow = 0;
            this.hasWon = false;
            createNewTable();
        }
    }
    public boolean getIsNewGame() { return this.isNewGame; }


    /**
     * Sets the count array containing statistics about the guesses made.
     * The count array stores the count of correct letters and placements,
     * correct letters with wrong placements, and incorrect letters.
     *
     * @param count The count array.
     */
    public void setCount(int[] count) {
        this.count = count;
    }

    /**
     * Returns the count array containing statistics about the guesses made.
     *
     * @return The count array.
     */
    public int[] getCount() {
        return this.count;
    }

    /**
     * Sets the flag indicating whether the game has been won or not.
     *
     * @param hasWon True if the game has been won, false otherwise.
     */
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    /**
     * Returns whether the game has been won or not.
     *
     * @return True if the game has been won, false otherwise.
     */
    public boolean getHasWon() {
        return this.hasWon;
    }

    /**
     * Sets the table of guesses.
     *
     * @param table The table of guesses.
     */

    public void setTable(SingleInput[][] table) {
        this.table = table;
    }

    /**
     * Returns the table of guesses.
     *
     * @return The table of guesses.
     */
    public SingleInput[][] getTable() {
        return this.table;
    }

    public void saveGuess(String guess) {
        for (int i = 0; i < guess.length(); ++i) {
            char guessChar = guess.charAt(i);
            this.table[this.activeRow][i].setLetter(guessChar);
        }
        this.table[this.activeRow][0].setActive(false);
        this.table[this.activeRow][this.table[this.activeRow].length - 1].setActive(true);
    }

    /**
     * Returns the index of the active row in the table.
     *
     * @return The index of the active row.
     */
    public int getActiveRow() {
        return this.activeRow;
    }

    /**
     * Sets the index of the active row in the table.
     *
     * @param activeRow The index of the active row.
     */
    public void setActiveRow(int activeRow) {
        this.activeRow = activeRow;
    }

    /**
     * Adds a guess to the table.
     * Compares the guess with the word to guess and updates the table accordingly.
     * Calculates statistics about the guess and updates the count array.
     *
     * @param guess The guess to add.
     */
    public void addGuess(String guess) {
        if (guess.length() != this.table[0].length || this.wordToGuess == null) { // Check against the length of the first row
            return;
        }
        int correctLetters = 0;
        for (int i = 0; i < guess.length(); ++i) {
            char guessChar = guess.charAt(i);
            char wordChar = wordToGuess.charAt(i);
            if (guessChar == wordChar) {
                this.table[this.activeRow][i].setLetterVal(Utility.RIGHT_LETTER_AND_PLACE);
                ++count[0];
                ++correctLetters;
            }
            else if (wordToGuess.indexOf(guessChar) != -1) {
                this.table[this.activeRow][i].setLetterVal(Utility.RIGHT_LETTER);
                ++count[1];
            }
            else {
                this.table[this.activeRow][i].setLetterVal(Utility.WRONG_LETTER);
                ++count[2];
            }
            this.table[this.activeRow][i].setLetter(guessChar);
        }
        this.table[activeRow][0].setActive(false); //turn off last active row
        this.table[this.activeRow][this.table[this.activeRow].length - 1].setActive(false);
        ++this.activeRow;
        if (correctLetters == guess.length()) {
            hasWon = true;
        }
        if (this.activeRow < this.numOfGuess && !this.hasWon) {
            this.table[activeRow][0].setActive(true);
        }
    }
}

