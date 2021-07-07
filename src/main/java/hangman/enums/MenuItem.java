package hangman.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum MenuItem {

    PLAY(new String[]{"1","play"}),
    WORDS(new String[]{"2","word", "words"}),
    PLAYERCHANGE(new String[]{"3","player","name"}),
    STATISTICS(new String[]{"4","statistics","statistic"}),
    EXIT(new String[]{"0","exit","break","bye"});

    private final String[] menuItems;

    MenuItem(String[] menuItems){
        this.menuItems= menuItems;
    }

    public final Set<String> getPossibleInputs() {
        return new HashSet<>(Arrays.asList(menuItems));
    }

    public static MenuItem getModeByInput(String input){
        return Arrays.stream(values())
                .filter(item -> Arrays.asList(item.menuItems).contains(input))
                .findFirst().orElse(MenuItem.EXIT);
    }

    public static Set<String> getAllPossibleInputs(){
        Set<String> possibleChoices = new HashSet<>();
        for (MenuItem item : MenuItem.values()) {
            possibleChoices.addAll(item.getPossibleInputs());
        }
        return possibleChoices;
    }
}
