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
    
    @Override
    public String toString() {
        return pair.player1.name + "\t" + pair.player2.name + "\t" + world.world.getName() + "\n";
    }
}
