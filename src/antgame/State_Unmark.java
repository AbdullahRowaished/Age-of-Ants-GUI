package antgame;

 

 

/**
 * This class represents the unmark instruction, removes a mark in the cell of the given index and colour
 * @author oscar
 */
public class State_Unmark extends State_Super{
    int markNum; //The index of the mark to be removed
    
    /**
     * @param instruction_ the type of this instruction
     * @param st1_ the default index of the next instruction
     * @param markNum_ the index of the mark to be removed
     */
    public State_Unmark(String instruction_, int st1_, int markNum_) {
        super(instruction_, st1_);
        markNum = markNum_;
    }
    
    /**
     * @return the index of the mark to be removed
     */
    public int get_MarkNum()
    {
        return markNum;
    }
}