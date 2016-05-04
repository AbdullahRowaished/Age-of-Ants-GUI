package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 * This class represents a single cell in the world
 * @author oscar
 */
public class Cell {
    private boolean[] redMarks; //Holds the marks which have been set by any red ant
    private boolean[] blackMarks; //Holds the marks which have been set by any black ants
    private boolean rocky; 
    private int foodRemaining; //Holds the number of food particles remaining in this cell
    private AntHill antHill; //Determines which anthill, if any, is contained in this cell
    private Ant ant; //Holds the ant which is currently in this cell
    
    public Cell copy()
    {
        Cell c = new Cell(rocky, antHill);
        c.setFood(foodRemaining);
        return c;
    }
    
    /**
     * @param rocky_ true if the cell if rocky, false otherwise
     * @param antHill_ enum AntHill, determines what anthill exists if any
     */
    public Cell(boolean rocky_, AntHill antHill_)
    {
        antHill = antHill_;
        rocky = rocky_;
        redMarks = new boolean[6]; //There are six chemical marker
        blackMarks = new boolean[6]; //There are six chemical markers
        ant = null; //There is initially no ant in the cell
    }
    
    /**
     * @param amount sets how much food is remaining on this cell
     */
    public void setFood(int amount)
    {
        foodRemaining = amount;
    }

    /**
     * @param a sets the ant on this cell to be a
     * @throws Exception throws a new exception if there is already an ant on this cell
     */
    public void setAnt(Ant a) throws Exception
    {
        if (a!=null&&ant!=null) throw new Exception("Ant already exists on cell: "+ant.getX() + ", "+ant.getY());
        ant = a;
    }
    
    /**
     * @return the ant in this cell
     */
    public Ant getAnt()
    {
        return ant;
    }
    
    /**
     * @return returns the number of food particles left in this cell
     */
    public int foodRemaining() {
        return foodRemaining;
    }

    /**
     * @return true if the cell is rocky, false otherwise
     */
    public boolean isRocky()
    {
        return rocky;
    }
    
    /**
     * Reduces the number of food particles in this cell by one
     */
    public void reduceFood()
    {
        foodRemaining--;
    }
    
    /**
     * @param mark the index of the mark
     * @param isRed true if the ant marking is red, false otherwise
     */
    public void markCell(int mark, boolean isRed) {
        if (isRed) redMarks[mark] = true;
        else blackMarks[mark] = true;
    }

    /**
     * Increments the amount of food left on this cell
     */
    public void increaseFood() {
        foodRemaining++;
    }

    /**
     * @param isRed true if the mark colour being checked is red, false otherwise
     * @param mark index of the mark to check
     * @return true if there is a mark of the given index and colour, false otherwise
     */
    public boolean hasMark(boolean isRed, int mark) {
        if (isRed) return redMarks[mark];
        else return blackMarks[mark];
    }
    
    /**
     * @param isRed true if the anthill to be checked is red, false otherwise
     * @return true if there is an anthill of the given colour on this cell, false otherwise
     */
    public boolean hasAnthill(boolean isRed)
    {
        if (isRed) return antHill==AntHill.RED_ANTHILL;
        else return antHill==AntHill.BLACK_ANTHILL;
    }
    
    /**
     * @param isRed true if the ant unmarking is red, false otherwise
     * @param mark the index of the mark being removed
     */
    public void unmark(boolean isRed, int mark)
    {
        if (isRed) redMarks[mark] = false;
        else blackMarks[mark] = false;
    }
    
}
