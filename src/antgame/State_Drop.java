package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents the drop instruction, this forces the ant to drop its food if it has some
 * @author oscar
 */
public class State_Drop extends State_Super{
    
    /*
     * @param instruction_ the type of this instruction
     * @param st1_ the index of the next instruction
     */
    public State_Drop(String instruction_, int st1_) {
        super(instruction_, st1_);
    }
    
}
