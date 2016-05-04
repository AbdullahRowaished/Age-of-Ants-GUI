package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents a single instruction in the an ant brain, will never be used directly
 * @author Oscar
 */
public abstract class State_Super {
    String instruction; //The instruction string, never used but useful for debugging
    int st1; //The default state, every instruction includes at least one state to which the ant will be set
    
    /**
     * @param instruction_ sets the type of this instruction
     * @param st1_ sets the default state, should not be less than zero or greater than the brain size, no check is made however
     */
    public State_Super(String instruction_, int st1_)
    {
        instruction = instruction_;
        st1 = st1_;
    }
    
    /**
     * @return the type of this instruction
     */
    public String get_Instruction()
    {
        return instruction;
    }
    
    /**
     * @return the next instruction which the ant should execute
     */
    public int get_St1()
    {
        return st1;
    }
}
