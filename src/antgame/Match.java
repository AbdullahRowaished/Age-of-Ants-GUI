package antgame;

/**
 * represents a pairing between a Pair object and a World object
 * @author Abdullah Rowaished
 */
class Match {
    World world;
    Pair pair;
    
    Match(World world, Pair pair) {
        this.world = world;
        this.pair = pair;
    }
}
