package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * Represents the pickup instruction, tells the ant to source a particle of food from the cell it is on
 * @author oscar
 */
public class State_PickUp extends State_Super{
    int st2; //Additional state, should be used if there is nothing to pickup
    
    /**
     * Creates a new pickup object
     * @param instruction the type of this instruction
     * @param st1 index of next instruction if a food is picked up
     * @param st2 index of next instruction if no food is picked up
     */
    public State_PickUp(String instruction_, int st1_, int st2_) {
        super(instruction_, st1_);
        st2 = st2_;
    }
    
    /**
     * @return the index of the next instruction if there is no food to pick up
     */
    public int get_St2()
    {
        return st2;
    }
}
