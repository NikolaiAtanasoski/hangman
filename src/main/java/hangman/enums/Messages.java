package hangman.enums;

public enum Messages {

    WELCOME("WELCOME TO HANGMAN 2020 \n====================================\n\n" +
                    "You were captured by the imperial troops and you have to guess a password.\n"),

    TOPIC("The word's topic that is going to be guessed is: "),

    GOOD_LUCK("GOOD LUCK!!"),

    PASSPRAHSE("Passphrase: "),

    WIN("Congratulations!"),

    GAME_OVER("************************************************\n" +
                      "**\n" +
                      "* You are dead and doomed *\n" +
                      "* Your soul will be tortoured in hell forever *\n" +
                      "**\n" +
                      "************************************************"),
    TRY_AGAIN("Try again?(y/n)"),

    EXIT("OK, thanks for hanging in, bye!"),

    MAINMENU("            MAIN MENU\n" +
             "      1, play: play the game\n" +
             "     2, words: add word(s)\n" +
             "    3, player: change player\n" +
             "4, statistics: show statistics\n" +
             "      0, exit: leave");

    private final String message;

    Messages(String message){
        this.message= message;
    }

    public String getMessage() {
        return this.message;
    }
}
