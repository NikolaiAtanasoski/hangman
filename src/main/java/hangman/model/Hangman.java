package hangman.model;

import java.util.HashMap;
import java.util.Map;

public class Hangman {
    private static final Map<Integer, String> HANGMAN_IAMGE = generateHangmanMap();
    private static final int DEAD_HANGMAN_INDEX = 21;

    public Hangman(){

    }

    public String getHangmanAtIndex(int index){
        if(index > DEAD_HANGMAN_INDEX){
            return HANGMAN_IAMGE.get(DEAD_HANGMAN_INDEX);
        }
        return HANGMAN_IAMGE.get(index);
    }

    public int getDeadHangmanIndex(){
        return DEAD_HANGMAN_INDEX;
    }


    private static final Map<Integer, String> generateHangmanMap() {
        Map<Integer, String> hangmanmap = new HashMap<>();
        hangmanmap.put(0, "     ");
        hangmanmap.put(1, "   --");
        hangmanmap.put(2, " ----");
        hangmanmap.put(3, "-----");
        hangmanmap.put(4, "|\n-----");
        hangmanmap.put(5, "|\n|\n-----");
        hangmanmap.put(6, "|\n|\n|\n-----");
        hangmanmap.put(7, "|\n|\n|\n|\n-----");
        hangmanmap.put(8, "|\n|\n|\n|\n|\n-----");
        hangmanmap.put(9, "|\n|\n|\n|\n|\n|\n-----");
        hangmanmap.put(10, "/\n|\n|\n|\n|\n|\n|\n-----");
        hangmanmap.put(11, "/-\n|\n|\n|\n|\n|\n|\n-----");
        hangmanmap.put(12, "/--\n|\n|\n|\n|\n|\n|\n-----");
        hangmanmap.put(13, "/---\n|\n|\n|\n|\n|\n|\n-----");
        hangmanmap.put(14, "/---\n|  |\n|\n|\n|\n|\n|\n-----");
        hangmanmap.put(15, "/---\n|  |\n|  0\n|\n|\n|\n|\n-----");
        hangmanmap.put(16, "/---\n|  |\n|  0\n| /\n|\n|\n|\n-----");
        hangmanmap.put(17, "/---\n|  |\n|  0\n| /|\n|\n|\n|\n-----");
        hangmanmap.put(18, "/---\n|  |\n|  0\n| /|\\\n|\n|\n|\n-----");
        hangmanmap.put(19, "/---\n|  |\n|  0\n| /|\\\n|  |\n|\n|\n-----");
        hangmanmap.put(20, "/---\n|  |\n|  0\n| /|\\\n|  |\n| /\n|\n-----");
        hangmanmap.put(21, "/---\n|  |\n|  0\n| /|\\\n|  |\n| / \\\n|\n-----");

        return hangmanmap;
    }
}
