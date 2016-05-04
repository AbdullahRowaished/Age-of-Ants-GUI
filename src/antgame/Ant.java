package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents a single ant with all details required for the ant to function correctly
 * @author oscar
 */
public class Ant {
    int currentState; //The ants current position in its players brain
    final int ID; //The identification number of this ant
    int resting; //The number of steps which this ant will rest for
    int direction; //The direction in which the ant is facing, 0-5, 0 is east and progresses clockwise
    boolean hasFood; //Says whether the ant is carrying some food
    int locationX; //The x location in the world on which the ant sits
    int locationY; //The y location in the world on which the ant sits
    final boolean ISRED;
    boolean alive; //true if the ant is alive 
    World world; //The world in which the ant exists
    static int nextId = 0; //The next available id

    /**
     * @param id_ this ants id
     * @param x this ants x location
     * @param y this ants y location
     * @param isRed_ true if this ant is red, false otherwise
     * @param world_ the world on which this ant will exist
     */
    public Ant(int id_, int x, int y, boolean isRed_, World world_) {
        alive = true;
        ISRED = isRed_;
        locationX = x;
        locationY = y;
        ID = id_;
        currentState = 0;
        direction = 0;
        world = world_;
    }

    /**
     * @param true if the ant is still resting, false otherwise
     */
    public boolean isResting()
    {
        return resting > 0;
    }
    
    /**
     * Tells the ant to rest for one simulation step
     */
    public void rest()
    {
        resting--;
    }
    
    /**
     * @return true if the ant is red, false otherwise
     */
    public boolean isRed() {
        return ISRED;
    }

    /**
     * @return true if the ant is alive, false otherwise
     */
    public boolean isAlive() {
        return alive;
    }
    
    /**
     * @return true if the ant has food, false otherwise
     */
    public boolean hasFood()
    {
        return hasFood;
    }

    /**
     * @return the x location of this ant
     */
    public int getX() {
        return locationX;
    }

    /**
     * @return the y location of this ant
     */
    public int getY() {
        return locationY;
    }

    /**
     * Kills this ant, essentially removes it from the game
     */
    public void kill()
    {
        alive = false;
    }

    /**
     * @param true if the ant should turn left, false otherwise
     */
    public void turnLeft(boolean left) {
        direction = left?(direction+5)%6:(direction+1)%6;
    }

    /**
     * Tells the ant to hold food, does not affect the cell which it is on
     */
    public void pickUpFood() {
        hasFood = true;
    }

    /**
     * @return returns the direction which the ant is facing
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @param x sets the ant to be at location x
     */
    public void setX(int x)
    {
        locationX = x;
    }
    
    /**
     * @param y sets the ant to be at location y
     */
    public void setY(int y)
    {
        locationY = y;
    }
    
    /**
     * Forces the ant to release its food, does not affect the cell which it is on
     */
    public void dropFood() {
        hasFood = false;
    }

    /**
     * @param state sets the ants brain index to this
     */
    public void setState(int state) {
        currentState = state;
    }

    /**
     * @param period tells the ant to rest for this many simulation steps
     */
    public void setResting(int period) {
        resting = period;
    }

    /**
     * @return the brain index which this ant is currently at
     */
    public int getState() {
        return currentState;
    }

}
