package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * Represents the mark instruction
 * @author oscar
 */
public class State_Mark extends State_Super{
    int markNum; //The mark index which should be set to true on the cell which the executing ant lies
    
    /**
     * @param instruction_ the type of this instruction
     * @param st1_ the default index of the next instruction
     * @param markNum the mark index which should be set to true on the cell
     */
    public State_Mark(String instruction_, int st1_, int markNum_) {
        super(instruction_, st1_);
        markNum = markNum_;
    }
    
    /**
     * returns the mark index to be set to true in the cell
     */
    public int getMarkNum()
    {
        return markNum;
    }
}
