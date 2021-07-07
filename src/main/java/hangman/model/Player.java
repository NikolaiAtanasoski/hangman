package hangman.model;

public class Player {

    private String name;

    private int gamesPlayed;
    private int deaths;
    private int wins;

    public Player() {

    }

    public Player(String name) {
        this.name = name;
        this.gamesPlayed = 0;
        this.deaths = 0;
        this.wins = 0;
    }

    public static Player defaultPlayer() {
        Player defaultPlayer = new Player();
        defaultPlayer.setName("player");
        defaultPlayer.setDeaths(0);
        defaultPlayer.setGamesPlayed(0);
        defaultPlayer.setWins(0);
        return defaultPlayer;
    }

    public void addOneWin(){
        this.wins++;
        this.gamesPlayed++;
    }


    public void addOneDeath(){
        this.deaths++;
        this.gamesPlayed++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
