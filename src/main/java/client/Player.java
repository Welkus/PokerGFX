package client;

/**
 * Klasa Player, szablon gracza
 */
public class Player {

    public String username;
    public int chips;

    /**
     * @param username
     * @param chips
     */
    public Player(String username, int chips) {
        this.username = username;
        this.chips = chips;

    }
}
