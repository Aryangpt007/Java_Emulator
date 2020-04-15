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
public class Variable extends Line 
{
    public String name;
    public int size;
    
    public Variable(String line, int sizes, String name, int line1) 
    {
        super(line, line1);
        this.name = name;
        this.size = sizes;
    }
}
