package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents the turn instruction
 * @author oscar
 */
public class State_Turn extends State_Super{
    boolean turnLeft; //True if the ant should turn to the left when this instruction is executed, false otherwise
    
    /**
     * @param instruction_ the type of this instruction
     * @param st1_ the default index of the next instruction
     * @param turnLeft_ true if the ant should turn left, false otherwise
     */
    public State_Turn(String instruction_, int st1_, boolean turnLeft_) {
        super(instruction_, st1_);
        turnLeft = turnLeft_;
    }
    
    /**
     * @return returns whether the ant should turn left or not
     */
    public boolean get_TurnLeft()
    {
        return turnLeft;
    }
}
