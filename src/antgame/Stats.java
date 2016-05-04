package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents the statistics generated in a single game
 * @author oscar
 */
public class Stats {
    int redFood; //Amount of food which red has accumulated
    int blackFood; //Amount of food which black has accumulated
    int blackKills; //Number of kills which black has made
    int redKills; //Number of kills which red has made
    int foodRemaining; //Amount of food remaining in the world
    int remainingAnts; //Number of ants remaining in the world
    
    /**
     * Initialises the stats to the contest specification
     */
    public Stats()
    {
        blackKills = 0;
        redKills = 0;
        foodRemaining = 0;
        remainingAnts = 254;
        redFood = 0;
        blackFood = 0;
    }
    
    /**
     * Increment reds food
     */
    public void redFood()
    {
        redFood++;
    }
    
    /**
     * Increment blacks food
     */
    public void blackFood()
    {
        blackFood++;
    }
    
    /**
     * @return the amount of food which the black colony has currently acquired
     */
    public int getBlackFood()
    {
        return blackFood;
    }
    
    /**
     * @return the amount of food which the red colony has acquired
     */
    public int getRedFood()
    {
        return redFood;
    }
    
    /**
     * @return the number of ants remaining
     */
    public int getAnts()
    {
        return remainingAnts;
    }
    
    /**
     * @return the number of kills which red has made
     */
    public int getRedKills()
    {
        return redKills;
    }
    
    /**
     * @return the number of kills which black has made
     */
    public int getBlackKills()
    {
        return blackKills;
    }
    
    /**
     * Black makes a kill
     */
    public void blackKill()
    {
        remainingAnts--;
        blackKills++;
    }
    
    /**
     * Red makes a kill
     */
    public void redKill()
    {
        remainingAnts--;
        redKills++;
    }
    
}
