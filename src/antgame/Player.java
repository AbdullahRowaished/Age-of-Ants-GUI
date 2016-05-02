package antgame;

import java.io.File;

/**
 * represents a player
 * @author Abdullah Rowaished
 */
class Player {
    String name;
    File brain;
    
    Player(String name, File brain) {
        this.name = name;
        this.brain = brain;
    }
}
