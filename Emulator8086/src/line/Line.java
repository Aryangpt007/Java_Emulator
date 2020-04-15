/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package line;

/**
 *
 * @author Aryan
 */
public class Line 
{   // Generic Asm row class.
    //Line;
    // can be a Command (Command extends Line) // ADD AX, BX
    // can be variable (Variable extends Line) // var1 db 1, 3, 5
    // can be a function definition (Function Definition extends Line) 
    
    public String line;
    public int CommandLine;
    
    public Line(String line,int CommandLine)
    {
        this.line = line;
        this.CommandLine = CommandLine;
    }
    
    public String toString() //Line objects added to Jlist return the value from the toString method.
    {
        return line;
    }
}
