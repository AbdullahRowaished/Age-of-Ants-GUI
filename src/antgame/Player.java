package antgame;

import java.io.File;

/**
 * represents a player
 * @author Abdullah Rowaished
 */
class Player {
    String name;
    File brain;
    int score;
    
    Player(String name, File brain) {
        this.name = name;
        this.brain = brain;
        score = 0;
    }
}
