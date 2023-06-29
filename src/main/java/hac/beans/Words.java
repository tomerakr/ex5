package hac.beans;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.io.Serializable;

/**
 * The Words class represents a word used in the Wordle game.
 * It contains the word itself.
 */
@Entity
public class Words implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, max = 5)
    @Column(unique = true)
    private String word;

    /**
     * Default constructor for the Words class.
     */
    public Words() {}

    /**
     * Retrieves the ID of the word.
     *
     * @return The ID of the word.
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the ID of the word.
     *
     * @param id The ID of the word.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the word.
     *
     * @return The word.
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Sets the word.
     *
     * @param word The word.
     */
    public void setWord(String word) {
        this.word = word;
    }
}
