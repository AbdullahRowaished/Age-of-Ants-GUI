package antgame;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * This class allows the program top parse a text file into a usable brain representation, if it is valid
 * @author Owner
 */
public class AntBrain_Parser {
    File b1; //The file to be parsed
    ArrayList<State_Super> brain; //The parsed brain from b1
    
    /**
     * Parses the brain
     * @param file the file to be parsed
     * @throws java.io.IOException
     * @throws java.io.FileNotFoundException
     */
    public AntBrain_Parser(File file) throws Exception
    {
        b1 = file;
        brain = new ArrayList();
        BufferedReader r1;
        FileReader f1 = new FileReader(b1);
        r1 = new BufferedReader(f1);
        String inputLine;
        int count = 0;
        
        //Gets all of the states from file1 and adds them to player 1's brain
       
            while (r1.ready() && count < 10001)
            {
                inputLine = r1.readLine();
                count++;
                if (!isWellFormed(inputLine))
                {
                    throw new IOException("Input is not well formed on line: " + count + "  Line content: " + inputLine);
                }
            }
            if (count == 10001)throw new IOException("Input file has too many lines, exiting");
    }
    
    /**
     * @param line the line to be parsed
     * @return true if the line is well formed, false otherwise
     */
    private boolean isWellFormed(String line)
    {
        boolean wellFormed = false;
        String[] splitLine = line.split("\\s");
        
        //Check which instruction the given line represents and then add that instruction to the brain
        if (splitLine[0].equals("Sense") && splitLine.length < 7)
        {
            if (splitLine[1].equals("Here") || splitLine[1].equals("Ahead") || splitLine[1].equals("LeftAhead") || splitLine[1].equals("RightAhead"))
            {
                if (Integer.parseInt(splitLine[2]) >= 0 )
                {
                    if (Integer.parseInt(splitLine[3]) >= 0 )
                    {
                        if (splitLine[4].matches("Friend|Foe|FriendWithFood|FoeWithFood|Food|Rock|FoeMarker|Home|FoeHome") && splitLine.length == 5)
                        {
                            brain.add(new State_Sense(splitLine[0], Integer.parseInt(splitLine[2]), splitLine[1], splitLine[4], Integer.parseInt(splitLine[3]),0));
                            wellFormed = true;
                        }
                        else if (splitLine[4].matches("Marker") && splitLine.length == 6 && Integer.parseInt(splitLine[5]) >= 0 && Integer.parseInt(splitLine[5]) <= 5)
                        {
                            brain.add(new State_Sense(splitLine[0], Integer.parseInt(splitLine[2]), splitLine[1], splitLine[4], Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[5])));
                            wellFormed = true;
                        }
                    }
                }
            }
        }
        else if (splitLine[0].equals("Drop") && splitLine.length == 2)
        {
            if (Integer.parseInt(splitLine[1]) >= 0)
            {
                brain.add(new State_Drop(splitLine[0],Integer.parseInt(splitLine[1])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Flip") && splitLine.length == 4)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[2]) >= 0 && Integer.parseInt(splitLine[3])>= 0)
            {
                brain.add(new State_Flip(splitLine[0], Integer.parseInt(splitLine[3]),Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[1])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Mark") && splitLine.length == 3)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[1]) <= 5 && Integer.parseInt(splitLine[2]) >= 0)
            {
                brain.add(new State_Mark(splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[1])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Unmark") && splitLine.length == 3)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[1]) <= 5 && Integer.parseInt(splitLine[2]) >= 0)
            {
                brain.add(new State_Unmark(splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[1])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("PickUp") && splitLine.length == 3)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[2]) >= 0)
            {
                brain.add(new State_PickUp(splitLine[0],Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Move") && splitLine.length == 3)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[2]) >= 0)
            {
                brain.add(new State_Move(splitLine[0],Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Turn") && splitLine.length == 3)
        {
            if (splitLine[1].matches("Left|Right") && Integer.parseInt(splitLine[2]) >= 0) 
            {
                brain.add(new State_Turn(splitLine[0],Integer.parseInt(splitLine[2]),splitLine[1].equals("Left")));
                wellFormed = true;
            }
        }
        return wellFormed;
    }
    
    /**
     * @return the parsed brain
     */
    public ArrayList<State_Super> getBrain1()
    {
        return brain;
    }
}
