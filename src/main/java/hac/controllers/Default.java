package hac.controllers;

import hac.beans.*;
import hac.beans.Words;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** this is a test controller, delete/replace it when you start working on your project */
/**
 * The Default class handles the default functionality and requests for the game.
 */
@Controller
public class Default {

    @Autowired
    private SettingsRepo settingsRepo; // JPA repository for accessing settings in the database
    @Autowired
    private HighScoreRepo highScoreRepo; // JPA repository for accessing high scores in the database
    @Autowired
    private WordsRepo wordsRepo; // JPA repository for accessing words in the database
    @Autowired
    @Qualifier("sessionTable")
    private GuessTable table; // The guess table for the current session
    @Autowired
    @Qualifier("sessionKeyboard")
    private Keyboard keyboard; // The keyboard for the current session

    /**
     * Handles the request to the index page.
     *
     * @param model The model to add attributes to.
     * @return The name of the game view.
     */
    @RequestMapping("/")
    public String index(Model model) {
        Settings settings = settingsRepo.findFirstByOrderByIdDesc();
        if (table.getIsNewGame()) {
            table.setIsNewGame(false);
            table.setWordToGuess(wordsRepo.findRandomWord());
            System.out.println("word to guess: " + table.getWordToGuess()); // For debugging purposes
            keyboard.setWordToGuess(table.getWordToGuess());
            if (settings != null) {
                table.setNumOfGuess(settings.getNumOfGuesses());
            }
        }
        model.addAttribute("keyboard", keyboard.getKeyboard());
        model.addAttribute("theme", settings != null? settings.getTheme() : true);
        model.addAttribute("table", table.getTable());
        model.addAttribute("activeGuess", table.getActiveRow());
        return "game";
    }

    /**
     * Handles the POST request to add a guess to the game.
     *
     * @param guess     The guess entered by the user.
     * @return A redirect to the index page.
     */
    @PostMapping("/addGuess")
    public String addGuess(@RequestParam("guess") String guess) {
        System.out.println("added guess this is what i got: " + guess);
        if (!isWord(guess)) {
            table.saveGuess(guess);
            return "redirect:/";
        }
        table.addGuess(guess);
        keyboard.addGuess(guess);
        if (table.getHasWon()) {
            return "redirect:/winPage";

        }
        return "redirect:/";
    }

    public boolean isWord(String word) {
        final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(API_URL + word, String.class);
            // If the API returns a response, the word is valid
            return true;
        } catch (HttpClientErrorException e) {
            // If the API returns an error, the word is not valid
            return false;
        }
    }

    @RequestMapping("/winPage")
    public String win(Principal principal, Model model) {
        if (!table.getHasWon()) {
            return "redirect:/";
        }
        Settings settings = settingsRepo.findFirstByOrderByIdDesc();
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("highScore").descending());  // sort by highScore in descending order
        Page<HighScore> page = highScoreRepo.findAll(pageRequest);  // fetch the first page of high scores
        saveScore(principal.getName());
        model.addAttribute("highScoresContent", page.getContent());
        model.addAttribute("theme", settings != null ? settings.getTheme() : true);
        return "winPage";
    }

    /**
     * Saves the high score for the user.
     *
     * @param name The name of the user.
     */
    private synchronized void saveScore(String name) {
        int[] count = table.getCount();
        HighScore score = new HighScore();
        score.setName(name);
        score.setNumOfTries(table.getActiveRow() + 1);
        Random rand = new Random();
        int randomNum = rand.nextInt(11);
        score.setHighScore((count[0] + randomNum) * 7 + (count[1] + randomNum) * 5 + (count[2] + randomNum) * 3);
        highScoreRepo.save(score);
    }

    @PostMapping("/startNewGame")
    public String startNewGame() {
        table.setIsNewGame(true);
        return "redirect:/";
    }
}
