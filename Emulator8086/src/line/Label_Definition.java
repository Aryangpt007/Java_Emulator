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
public class Label_Definition extends Line
{
    public String Function_name;
    
    public Label_Definition(String line, String Function_name, int Command_line)
    {
        super(line,Command_line);
        this.Function_name = Function_name;
    }
}
