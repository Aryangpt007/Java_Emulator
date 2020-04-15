/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package steps;

import emulator8086.Flag;
import emulator8086.Register;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aryan
 */
public class InstanceList 
{
    private List<Instance> InitializeList = new ArrayList<Instance>(); 
   
    public void save(Instance initial)
    {
        InitializeList.add(initial);
    }
    
    public Instance get(int i)
    {
        return InitializeList.get(i);
    }
    
    public void load(int i) throws Exception
    {
        Instance initial = InitializeList.get(i);
        Register.getRegister().setValue("AX", initial.registers.get("AX"));
        Register.getRegister().setValue("BX", initial.registers.get("BX"));
        Register.getRegister().setValue("CX", initial.registers.get("CX"));
        Register.getRegister().setValue("DX", initial.registers.get("DX"));
	Flag.getFlag().CF = initial.flags[0];
	Flag.getFlag().ZF = initial.flags[1];
	Flag.getFlag().SF = initial.flags[2];
	Flag.getFlag().OF = initial.flags[3];
	Flag.getFlag().PF = initial.flags[4];
	Flag.getFlag().DF = initial.flags[5];
        
    }
    
    public int getSize()
    {
        return InitializeList.size();
    }

}
