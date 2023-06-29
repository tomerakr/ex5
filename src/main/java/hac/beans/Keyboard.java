package hac.beans;

import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The Keyboard class represents the keyboard used in the Wordle game.
 * It provides functionality to track and update the appearance of the keyboard based on user guesses.
 */
@Component
public class Keyboard implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567L;

    private String wordToGuess;
    private Map<String, String> keyboard;

    /**
     * Constructs a Keyboard object with default settings.
     * The keyboard is initialized with keys and their initial appearance settings.
     */
    public Keyboard() {
        keyboard = new LinkedHashMap<>();
        String[] keys = "Q,W,E,R,T,Y,U,I,O,P,A,S,D,F,G,H,J,K,L,Enter,Z,X,C,V,B,N,M,Delete".split(",");
        for (String key : keys) {
            keyboard.put(key, "");
        }
    }

    /**
     * Returns the keyboard map containing keys and their appearance settings.
     *
     * @return The keyboard map.
     */
    public Map<String, String> getKeyboard() {
        return keyboard;
    }

    /**
     * Sets the keyboard map containing keys and their appearance settings.
     *
     * @param keyboard The keyboard map to set.
     */
    public void setKeyboard(Map<String, String> keyboard) {
        this.keyboard = keyboard;
    }

    /**
     * Sets the word to guess in the Wordle game and resets the color for each key.
     *
     * @param wordToGuess The word to guess.
     */
    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.clearColors();
    }


    public void clearColors() {
        keyboard.replaceAll((k, v) -> "");
    }

    /**
     * Returns the word to guess in the Wordle game.
     *
     * @return The word to guess.
     */
    public String getWordToGuess() {
        return this.wordToGuess;
    }

    /**
     * Updates the appearance of the keyboard based on the user's guess.
     *
     * @param guess The user's guess.
     */
    public void addGuess(String guess) {
        if (this.wordToGuess == null) {
            return;
        }

        for (int i = 0; i < guess.length(); ++i) {
            char guessChar = guess.charAt(i);
            char wordChar = wordToGuess.charAt(i);
            if (guessChar == wordChar) {
                keyboard.put(String.valueOf(guessChar), "background-color: #6ECCAF");
            } else if (wordToGuess.indexOf(guessChar) != -1) {
                keyboard.put(String.valueOf(guessChar), "background-color: #FBFFB1");
            } else {
                keyboard.put(String.valueOf(guessChar), "background-color: lightgray");
            }
        }
    }
}
