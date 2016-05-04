package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents a single player
 * @author oscar
 */
public class Player {
    private final String name; //Holds the players name being used in the game
    private int score; //Holds the current score of the player
    private final State_Super[] brain; //Holds the brain submitted by the user
    
    /**
     * @param name_ the name of the player
     * @param brain_ the parsed brain of the player
     */
    public Player(String name_, State_Super[] brain_)
    {
        brain = brain_;
        name = name_;
    }
    
    /**
     * @return the players parsed brain
     */
    public State_Super[] getBrain()
    {
        return brain;
    }
    
    /**
     * @return the players name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return the players current score
     */
    public int currentScore()
    {
        return score;
    }
    
    /**
     * Say that the player has won a game
     */
    public void win()
    {
        score+=2;
    }
    
    /**
     * Say that the payer has drawn in a game
     */
    public void draw()
    {
        score++;
    }
}
