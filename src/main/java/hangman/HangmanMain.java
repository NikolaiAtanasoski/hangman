package hangman;

import hangman.enums.MenuItem;
import hangman.enums.Messages;
import hangman.helper.IOHelper;
import hangman.model.Player;
import hangman.service.HangmanWordDataService;
import hangman.service.PlayerDataService;

import java.util.HashMap;
import java.util.Map;

public class HangmanMain extends IOHelper {

    private HangmanWordDataService wordDataService = new HangmanWordDataService();
    private PlayerDataService playerDataService = new PlayerDataService();
    private int gamesPlayed;
    private int wins;
    private int deaths;
    private Map<String, Integer> letterInputCount;

    private Player currentPlayer;

    private boolean exit = false;

    public HangmanMain() {
        print(Messages.WELCOME.getMessage(), 0);
        String player = askWhoIsPlaying();
        currentPlayer = playerDataService.getPlayerIfExists(player);
        letterInputCount = new HashMap<>();

        run();
    }

    public static void main(String[] args) {
        new HangmanMain();
    }

    private String askWhoIsPlaying() {
        print("Enter your name of the delinquent: ", 0);
        return getNextInput(true);
    }

    public void run() {
        boolean stillPlaying;
        do {
            showMainMenu();
            MenuItem selectedItem = askForMenuItem();
            stillPlaying = runGame(selectedItem);
        } while (stillPlaying);

        showStatistics();
        askForSave();
        print(Messages.EXIT.getMessage(), 0);
    }

    public void play() {
        boolean humanWantsNextRound = true;
        while (humanWantsNextRound) {
            HangmanGame hangmanGame = new HangmanGame();
            String topic = wordDataService.getRandomtopic();
            String wordToGuess = wordDataService.getRandomWordFromTopic(topic);
            boolean hasWonTheGame = hangmanGame.game(wordToGuess, topic);
            increaseStatistics(hasWonTheGame, hangmanGame.getLetterInputCount());
            humanWantsNextRound = doesHumanWantNextRound();
        }
    }

    public boolean doesHumanWantNextRound() {
        print(Messages.TRY_AGAIN.getMessage(), 0);
        String exitLetter = getNextInput(true);
        return exitLetter.equalsIgnoreCase("y");
    }

    public void showMainMenu() {
        print("Currently playing: " + currentPlayer.getName(), 1);
        print(Messages.MAINMENU.getMessage(), 0);
    }

    public boolean runGame(MenuItem choice) {
        boolean stillPlaying = true;
        switch (choice) {
            case PLAY:
                play();
                break;
            case WORDS:
                addWords();
                break;
            case PLAYERCHANGE:
                changePlayer();
                break;
            case STATISTICS:
                showStatistics();
                break;
            case EXIT:
            default:
                stillPlaying = false;

        }
        return stillPlaying;
    }

    private void changePlayer() {
        print("Player Name:", 0);
        String name = getNextInput(true);
        if (!playerDataService.doesPlayerAlreadyExist(name)) {
            Player newPlayer = new Player(name);
            playerDataService.addNewPlayer(name, newPlayer);
            currentPlayer = newPlayer;
        }
        else {
            currentPlayer = playerDataService.getPlayer(name);
        }
    }

    private void askForSave() {
        if (wordDataService.hasChanged()) {
            print("Save changes to dictionary? (y/n)", 0);
            String saveChanges = getNextInput(true);
            if (saveChanges.equalsIgnoreCase("y")) {
                wordDataService.saveData();
            }
        }
        if (playerDataService.hasChanged()) {
            print("Save new players? (y/n)", 0);
            String saveChanges = getNextInput(true);
            if (saveChanges.equalsIgnoreCase("y")) {
                playerDataService.saveData();
            }
        }
    }

    private void addWords() {
        print("Enter new Topic or one which is already in Dictionary:", 0);
        for (String topic : wordDataService.getData().keySet()) {
            print("- " + topic, 0);
        }
        String topic = getNextInput(true);

        if (wordDataService.hasTopic(topic)) {
            print("Already in this Topic:", 0);
            for (String word : wordDataService.getWordsFromTopic(topic)) {
                print("- " + word, 0);
            }
        }

        print("Enter new Word:", 0);
        String word = getNextInput(true);
        wordDataService.addWord(topic, word);
    }

    private MenuItem askForMenuItem() {
        String choiceInput = "";

        for(choiceInput = getNextInput(true).toLowerCase(); !isChoiceLegal(choiceInput); choiceInput = getNextInput(true).toLowerCase(), print("invalid input", 1)){
            showMainMenu();
        }
        return MenuItem.getModeByInput(choiceInput);

//        while (true) {
//            String choiceInput = getNextInput(true).toLowerCase();
//            if (isChoiceLegal(choiceInput)) {
//                return MenuItem.getModeByInput(choiceInput);
//            }
//            print("invalid input", 1);
//            showMainMenu();
//        }
    }

    private boolean isChoiceLegal(String choice) {
        return MenuItem.getAllPossibleInputs().contains(choice);
    }

    private void showStatistics() {

        print("Current Session:", 0);
        print("games played total: " + gamesPlayed, 0);
        print("games won total: " + wins, 0);
        print("games lost total: " + deaths, 1);

        showPlayerStatistics(currentPlayer);

        print("Show for all players? (y/n)", 0);
        String showAllPlayers = getNextInput(true);
        if (showAllPlayers.equalsIgnoreCase("y")) {
            showAllPlayerStatistics();
        }

        print("Letters entered:", 1);
        for (String letter : letterInputCount.keySet()) {
            print(letter + " " + letterInputCount.get(letter), 0);
        }

        print("PRESS ANY KEY TO GET BACK TO MENU...", 0);
        getNextInput(false);
    }

    protected void showPlayerStatistics(Player player) {
        print("     Name: " + player.getName(), 0);
        print("games played total: " + player.getGamesPlayed(), 0);
        print("games won total: " + player.getWins(), 0);
        print("games lost total: " + player.getDeaths(), 1);
    }

    protected void showAllPlayerStatistics() {
        for (Player player : playerDataService.getAllPlayers()) {
            showPlayerStatistics(player);
        }
    }

    private void increaseStatistics(boolean hasWonTheGame, Map<String, Integer> gameLetterInputCount) {
        if (hasWonTheGame) {
            wins++;
            currentPlayer.addOneWin();
        }
        else {
            deaths++;
            currentPlayer.addOneDeath();
        }
        gamesPlayed++;

        for (String letter : gameLetterInputCount.keySet()) {
            addToLetterStatistic(letter, gameLetterInputCount.get(letter));
        }
    }

    private void addToLetterStatistic(String letter, int count) {
        if (!letterInputCount.containsKey(letter)) {
            letterInputCount.put(letter, 0);
        }
        int currentCount = letterInputCount.get(letter);
        letterInputCount.put(letter, currentCount + count);
    }

}
