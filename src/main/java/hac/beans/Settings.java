package hac.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * The Settings class represents the game settings for the Wordle game.
 * It includes the number of guesses allowed and the theme (light or dark) of the game.
 */
@Entity
public class Settings implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Min(1)
    private int numOfGuesses = 6;

    @NotNull
    private Boolean theme = false; // true is light, false is dark

    /**
     * Constructs a Settings object with default settings.
     */
    public Settings() {}

    /**
     * Sets the ID of the game settings.
     *
     * @param id The ID to set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the ID of the game settings.
     *
     * @return The ID.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the number of guesses allowed in the Wordle game.
     *
     * @param numOfGuesses The number of guesses allowed.
     */
    public void setNumOfGuesses(int numOfGuesses) {
        this.numOfGuesses = numOfGuesses;
    }

    /**
     * Returns the number of guesses allowed in the Wordle game.
     *
     * @return The number of guesses allowed.
     */
    public int getNumOfGuesses() {
        return this.numOfGuesses;
    }

    /**
     * Sets the theme of the Wordle game.
     * true represents the light theme, false represents the dark theme.
     *
     * @param theme The theme to set.
     */
    public void setTheme(Boolean theme) {
        this.theme = theme;
    }

    /**
     * Returns the theme of the Wordle game.
     * true represents the light theme, false represents the dark theme.
     *
     * @return The theme.
     */
    public Boolean getTheme() {
        return this.theme;
    }
}
