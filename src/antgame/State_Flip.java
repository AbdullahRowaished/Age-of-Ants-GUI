package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * Represents the flip instruction, this instruction randomly goes to one of the states specified based on the probability given
 * @author oscar
 */
public class State_Flip extends State_Super{
    int st2; //The index to go to if the pseudo random number is not 0
    int p; //The inverse probability of going to the instruction index helf in st1
    
    /**
     * @param instruction_ the type of this instruction
     * @param st1_ the default next instruction index
     * @param st2_ the alternate next instruction index
     * @param p_ the inverse probability of going to st1
     */
    public State_Flip(String instruction_, int st1_, int st2_, int p_) {
        super(instruction_, st1_);
        st2 = st2_;
        p = p_;
    }
    
    /**
     * @return the index of the alternate instruction
     */
    public int get_St2()
    {
        return st2;
    }
    
    /**
     * @return the inverse probability of the next instruction being at index st1
     */
    public int get_P()
    {
        return p;
    }
}
