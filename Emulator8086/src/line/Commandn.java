/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package line;

import emulator8086.Variablev;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aryan
 */
public class Commandn extends Line 
{
    public String commands; //ADD
    
    public int functionLine; // If the command is loop, line of the function definition to be looped
    
    private List<Variablev> VariableList = null;// list of variables after command definition 0) AX  , 1)BX
    
    public Commandn(String line, String commands, int line1)
    {
        super(line,line1);
        this.commands = commands;
        VariableList = new ArrayList<Variablev>();
    }
    
    public void addVariable(Variablev variablen)
    {
        VariableList.add(variablen);
    }
    
    public List getVariableList()
    {
        return VariableList;
    }
    
//    public void printF()//outline to see the summary
//    {
//        System.out.println("Command: "+commands);
//        
//        for (int i = 0; i < VariableList.size(); i++) 
//        {
//			System.out.println((i+1)+". Variable: Type"+VariableList.get(i).typeT+ " Value:"+ VariableList.get(i).ValueV);
//        }
//        System.out.println("-------");
//    }
}
