package hangman.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hangman.helper.JSONHelper;
import hangman.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Set;

public class PlayerDataService extends JSONHelper {
    private static final Logger logger = LoggerFactory.getLogger(PlayerDataService.class);

    private Map<String, Player> playerMap;
    private boolean hasChanged;

    public PlayerDataService() {
        super("players.txt");
        loadData();
    }

    public void loadData() {
        playerMap = new HashMap<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFile)), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<Map<String, Player>> typeRef
                    = new TypeReference<Map<String, Player>>() {
            };
            playerMap = mapper.readValue(json, typeRef);
        }
        catch (IOException e) {
            logger.error("Error on loading JSON Data - no Players loaded", e);
        }

    }

    public void saveData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(playerMap);
            writeToFile(json);
        }
        catch (IOException e) {
            logger.error("Error on writing JSON Data", e);
        }
    }

    public Player getPlayer(String name) {
        return playerMap.get(name);
    }

    public Player getPlayerIfExists(String name){
        if(doesPlayerAlreadyExist(name)){
            return playerMap.get(name);
        }
        return new Player(name);
    }

    public List<Player> getAllPlayers(){
        return new ArrayList<>(playerMap.values());
    }

    public void addNewPlayer(String name, Player player) {
        playerMap.put(name, player);
        hasChanged = true;
    }

    public Set<String> getNamesOfPlayers() {
        return playerMap.keySet();
    }

    public boolean doesPlayerAlreadyExist(String name){
        return getNamesOfPlayers().contains(name);
    }

    public boolean hasChanged() {
        return hasChanged;
    }

}
