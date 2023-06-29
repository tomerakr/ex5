package hac.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * The HighScore class represents a high score entry in the Wordle game.
 * It stores the ID, high score, and number of tries for a player.
 */
@Entity
public class HighScore implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Min(0)
    private int highScore;

    @NotNull
    @Min(0)
    private int numOfTries;

    @NotNull
    private String name;

    /**
     * Constructs a HighScore object with default values.
     */
    public HighScore() {}

    /**
     * Sets the ID of the high score entry.
     *
     * @param id The ID of the high score entry.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the ID of the high score entry.
     *
     * @return The ID of the high score entry.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the high score value.
     *
     * @param highScore The high score value.
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Returns the high score value.
     *
     * @return The high score value.
     */
    public int getHighScore() {
        return this.highScore;
    }

    /**
     * Sets the number of tries for the high score entry.
     *
     * @param numOfTries The number of tries for the high score entry.
     */
    public void setNumOfTries(int numOfTries) {
        this.numOfTries = numOfTries;
    }

    /**
     * Returns the number of tries for the high score entry.
     *
     * @return The number of tries for the high score entry.
     */
    public int getNumOfTries() {
        return this.numOfTries;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " has highScore of " + highScore + " with " + numOfTries + " tries.";
    }

}
