package hac.controllers;

import hac.beans.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * The Admin class handles the administration-related functionality and requests.
 */
@Controller
@RequestMapping("/admin")
public class Admin {

    @Autowired
    private SettingsRepo settingsRepo; // JPA repository for accessing settings in the database
    @Autowired
    private WordsRepo wordsRepo;
    @Autowired
    private HighScoreRepo highScoreRepo;
    @Autowired
    @Qualifier("sessionTable")
    private GuessTable table; // The guess table for the current session
    @Autowired
    @Qualifier("sessionKeyboard")
    private Keyboard keyboard; // The guess table for the current session

    /**
     * Handles the request to the settings page.
     *
     * @param model The model to add attributes to.
     * @return The name of the settings view.
     */
    @RequestMapping("/settings")
    public String settings(Model model) {
        Settings settings = settingsRepo.findFirstByOrderByIdDesc();
        model.addAttribute("theme", settings != null ? settings.getTheme() : true);
        return "settings";
    }

    /**
     * Handles the POST request to set the game settings.
     *
     * @param settings The updated game settings.
     * @return A redirect to the settings page.
     */
    @PostMapping("/setSettings")
    public String setSettings(@Valid Settings settings) {
        table.setNumOfGuess(settings.getNumOfGuesses());
        keyboard.clearColors();
        settingsRepo.save(settings);
        return "redirect:/admin/settings";
    }

    /**
     * Clears the high score table by deleting all high scores from the repository.
     *
     * @return A string representing the redirection URL to the settings page.
     */
    @PostMapping("/clearHighScoreTable")
    public synchronized String clearHighScoreTable() {
        highScoreRepo.deleteAll();
        return "redirect:/admin/settings";
    }

    /**
     * Handles the POST request to add words to the game.
     *
     * @param allRequestParams The request parameters containing the words to add.
     * @return A redirect to the settings page.
     */
    @PostMapping("/addWords")
    public String addWords(@RequestParam Map<String, String> allRequestParams) {
        String wordsText = allRequestParams.get("words");
        String[] words = wordsText.split("\n");
        for (String word : words) {
            Words wordToSave = new Words();
            wordToSave.setWord(word.trim().toUpperCase());
            wordsRepo.save(wordToSave);
        }
        return "redirect:/admin/settings";
    }
}
