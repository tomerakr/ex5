package hac.beans;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * The SingleInput class represents a single input in the Wordle game.
 * It holds information about the letter value, the letter itself, and its active state.
 */
@Component
public class SingleInput implements Serializable {

    @Serial
    private static final long serialVersionUID = 1234567L;

    // -1: wrong letter, 0: right letter, 1: right letter and place
    @NotNull
    private int letterVal = 5; // Just a random number not in the list above

    @NotNull
    @Pattern(regexp = "[A-Z]")
    private Character letter;

    @NotNull
    private boolean active = false;

    /**
     * Constructs a SingleInput object with default values.
     */
    public SingleInput() {}

    /**
     * Returns the letter value of the SingleInput.
     *
     * @return The letter value.
     */
    public int getLetterVal() {
        return this.letterVal;
    }

    /**
     * Sets the letter value of the SingleInput.
     *
     * @param letterVal The letter value to set.
     */
    public void setLetterVal(int letterVal) {
        this.letterVal = letterVal;
    }

    /**
     * Returns the letter of the SingleInput.
     *
     * @return The letter.
     */
    public Character getLetter() {
        return this.letter;
    }

    /**
     * Sets the letter of the SingleInput.
     *
     * @param letter The letter to set.
     */
    public void setLetter(Character letter) {
        this.letter = letter;
    }

    /**
     * Returns the active state of the SingleInput.
     *
     * @return The active state.
     */
    public boolean getActive() {
        return this.active;
    }

    /**
     * Sets the active state of the SingleInput.
     *
     * @param active The active state to set.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns the color style based on the letter value of the SingleInput.
     *
     * @return The color style.
     */
    public String getColor() {
        return switch (this.letterVal) {
            case Utility.WRONG_LETTER -> "background-color: lightgray";
            case Utility.RIGHT_LETTER -> "background-color: #FBFFB1";
            case Utility.RIGHT_LETTER_AND_PLACE -> "background-color: #6ECCAF";
            default -> "background-color: default";
        };
    }
}
