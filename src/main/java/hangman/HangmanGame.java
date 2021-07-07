package hangman;

import hangman.model.Hangman;
import hangman.enums.Messages;
import hangman.helper.IOHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class HangmanGame extends IOHelper {

    private static final Hangman HANGMAN = new Hangman();

    private int deathCounter;
    private String wordToGuess;
    private String currentWordState;
    private String lastWordState;

    private Map<String, Integer> letterInputCount;

    public boolean game(String wordToGuess, String kategorie) {
        init(wordToGuess, kategorie);

        while (deathCounter < HANGMAN.getDeadHangmanIndex()) {
            showPassPhraseAndHangman("");
            doARound();
            if (isWin()) {
                print(Messages.WIN.getMessage(), 0);
                return true;
            }
        }

        print(Messages.GAME_OVER.getMessage(), 0);
        return false;
    }

    public void init(String wordToGuess, String topic) {
        this.deathCounter = 0;
        this.wordToGuess = wordToGuess;
        this.currentWordState = StringUtils.repeat("_", wordToGuess.length());
        this.lastWordState = StringUtils.repeat("_", wordToGuess.length());
        this.letterInputCount = new HashMap<>();

        print(Messages.TOPIC.getMessage() + topic, 1);
        print(Messages.GOOD_LUCK.getMessage(), 1);
    }

    public void doARound() {
        String nextLetter = readNextLetter();
        addToLetterCount(nextLetter);
        currentWordState = testNextLetter(nextLetter);
        if (currentWordState.equals(lastWordState)) {
            deathCounter += 7;
        }
        lastWordState = currentWordState;
    }

    public String readNextLetter() {
        while (true) {
            String nextLetter = getNextInput(true);
            if (nextLetter.length() == 0) {
                showPassPhraseAndHangman("Nichts eingegeben!");
            }
            if (nextLetter.length() > 1) {
                showPassPhraseAndHangman("Zuviele Zeichen!");
            }
            else if (!Character.isLetter(nextLetter.charAt(0))) {
                showPassPhraseAndHangman("Kein Buchstabe!");
            }
            else {
                return nextLetter;
            }
        }
    }

    public void showPassPhraseAndHangman(String errorMsg) {
        if (errorMsg.length() > 0) {
            print(errorMsg, 1);
        }
        print(Messages.PASSPRAHSE.getMessage() + currentWordState, 0);
        print(HANGMAN.getHangmanAtIndex(deathCounter), 0);
    }

    public boolean isWin() {
        return currentWordState.equalsIgnoreCase(wordToGuess);
    }

    public String testNextLetter(String nextLetter) {
        String lettersAlreadyFound = currentWordState.toLowerCase() + nextLetter.toLowerCase();
        String withUnderscores = wordToGuess.toLowerCase().replaceAll("[^" + lettersAlreadyFound + "]", "_");
        return withUnderscores.substring(0, 1).toUpperCase() + withUnderscores.substring(1);
    }

    private void addToLetterCount(String letter) {
        if (!letterInputCount.containsKey(letter)) {
            letterInputCount.put(letter, 0);
        }
        int currentCount = letterInputCount.get(letter);
        letterInputCount.put(letter, currentCount + 1);

    }

    public Map<String, Integer> getLetterInputCount() {
        return letterInputCount;
    }

}
