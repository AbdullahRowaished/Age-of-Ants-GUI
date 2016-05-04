package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents the sense instruction
 * @author oscar
 */
public class State_Sense extends State_Super{
    String senseDir; //The dircetion in which the ant will sense
    int st2; //The alternate instruction index
    String cond; //The conditional value
    int markNum; //The index of the mark being sensed, only used if the cond value is for sensing marks
    
    /**
     * @param instruction_ the type of this instruction
     * @param st1_ the default instruction index
     * @param senseDir_ the direction/location being sensed by the ant
     * @param cond_ the conditional being used in this sense instruction
     * @param markNum_ the index of mark being sensed
     */
    public State_Sense(String instruction_, int st1_, String senseDir_, String cond_, int st2_, int markNum_) {
        super(instruction_, st1_);
        markNum = markNum_;
        st2 = st2_;
        cond = cond_;
        senseDir = senseDir_;
    }
    
    /**
     * @return the mark indedx being sensed
     */
    public int get_MarkNum()
    {
        return markNum;
    }
    
    /**
     * @return the direction/location being sensed
     */
    public String get_SenseDir()
    {
        return senseDir;
    }
    
    /**
     * @return the alternate instruction index
     */
    public int get_St2()
    {
        return st2;
    }
    
    /**
     * @return the conditional being used
     */
    public String get_Cond()
    {
        return cond;
    }
    
}
