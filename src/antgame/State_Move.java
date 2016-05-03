package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents the move instruction
 * @author oscar
 */
public class State_Move extends State_Super{
    int st2; //The alternate instruction index
    
    /**
     * @param instruction_ the type of this instruction
     * @param st1_ the instruction index of the default next instruction
     * @param st2_ the instruction index of the alternate next instruction
     */
    public State_Move(String instruction_, int st1_, int st2_) {
        super(instruction_, st1_);
        st2 = st2_;
    }
    
    /**
     * @return the alternate instruction index
     */
    public int get_St2()
    {
        return st2;
    }
}
