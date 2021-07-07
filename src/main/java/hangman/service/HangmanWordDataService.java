package hangman.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hangman.helper.JSONHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class HangmanWordDataService extends JSONHelper {
    private static final Logger logger = LoggerFactory.getLogger(HangmanWordDataService.class);
    private static final Random RANDOM = new Random();

    private boolean hasChanged = false;

    private Map<String, Set<String>> DATA;


    public HangmanWordDataService() {
        super("hangman_words.txt");
        loadData();
    }


    public Map<String,Set<String>> generateDefaultWordsMap() {
        Map<String,Set<String>> map = new HashMap<>();

        map.put("Pokemon", new HashSet<>());
        map.get("Pokemon").add("Shiggy");
        map.get("Pokemon").add("Glumanda");
        map.get("Pokemon").add("Bisasam");
        map.get("Pokemon").add("Pikachu");

        map.put("Software Entwicklung", new HashSet<>());
        map.get("Software Entwicklung").add("Java");
        map.get("Software Entwicklung").add("Python");
        map.get("Software Entwicklung").add("Bash");

        return map;
    }

    public void loadData() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFile)), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Set<String>>> typeRef
                    = new TypeReference<HashMap<String, Set<String>>>() {
            };
            DATA = mapper.readValue(json, typeRef);
        }
        catch (IOException e) {
            logger.error("Error on loading JSON Data - default Words loaded", e);
            DATA = generateDefaultWordsMap();
        }
    }

    public void saveData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(DATA);
            writeToFile(json);
        }
        catch (IOException e) {
            logger.error("Error on writing JSON Data", e);
        }
    }

    public String getRandomtopic(){
        int index = RANDOM.nextInt(DATA.keySet().size());
        return (String) DATA.keySet().toArray()[index];
    }

    public String getRandomWordFromTopic(String topic) {
        int index = RANDOM.nextInt(DATA.get(topic).size());
        return (String) DATA.get(topic).toArray()[index];
    }

    public Map<String, Set<String>> getData() {
        return DATA;
    }

    public void addWord(String topic, String word){
        if(!DATA.containsKey(topic)){
            DATA.put(topic,new HashSet<>());
        }
        DATA.get(topic).add(word);
        hasChanged = true;
    }

    public boolean hasTopic(String topic){
        return DATA.containsKey(topic);
    }

    public Set<String> getWordsFromTopic(String topic){
        return DATA.get(topic);
    }


    public boolean hasChanged(){
        return hasChanged;
    }

}

