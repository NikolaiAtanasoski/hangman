package hangman.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public abstract class IOHelper {
    private static final Logger logger = LoggerFactory.getLogger(IOHelper.class);

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

    public String getNextInput(boolean showCursor) {
        if (showCursor) {
            System.out.print(">  ");
        }

        return readLine();
    }

    private String readLine() {
        try {
            return INPUT.readLine();
        }
        catch (IOException e) {
            logger.error("Error on reading input", e);
            return "";
        }
    }

    public void print(String msg, int additionalNewLineCount) {
        StringBuilder msgBuilder = new StringBuilder(msg);
        for (int i = 0; i < additionalNewLineCount; i++) {
            msgBuilder.append("\n");
        }
        msg = msgBuilder.toString();
        System.out.println(msg);
    }

}
