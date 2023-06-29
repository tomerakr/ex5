package hac.beans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * The WordsRepo interface provides database access and operations for the Words entity.
 */
public interface WordsRepo extends JpaRepository<Words, Long> {

    /**
     * Retrieves a random word from the database.
     *
     * @return A random word.
     */
    @Query(nativeQuery = true, value = "SELECT word FROM words ORDER BY RAND() LIMIT 1")
    String findRandomWord();

    /**
     * Checks if a word exists in the database.
     *
     * @param word The word to check.
     * @return true if the word exists, false otherwise.
     */
    boolean existsByWord(String word);
}
