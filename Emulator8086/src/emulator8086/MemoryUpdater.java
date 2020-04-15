/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emulator8086;

import line.Commandn;
import steps.StackElement;

/**
 *
 * @author Aryan
 */
public class MemoryUpdater 
{

    public static void updateMemory(Commandn commande, StackElement res) throws Exception 
    {
        try 
        {
            switch (commande.commands.toUpperCase()) 
            {
                case "ADC":
                case "ADD":
                case "OR":
                case "AND":
                case "XOR":
                case "ROL":
                case "ROR":
                case "SBB":
                case "SHL":
                case "SHR":
                case "SUB":
                    if (res.size == 1) 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, res.getLowValue());
                        EmulatorFrame.memoList = new int[1];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    } 
                    else 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, res.getLowValue());
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 1, res.getHighValue());
                        
                        EmulatorFrame.memoList = new int[2];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[1] = EmulatorFrame.Memory_Address++;
                    }
                    break;

                case "STC":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 249);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                case "CLC":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 248);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                case "STD":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 253);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                case "CLD":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 252);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                case "CMP":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 58);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                case "DEC":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 254);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                case "HLT":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 244);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                case "INC":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 254);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                
                case "JA":
                case "JAE":
                case "JB":
                case "JBE":
                case "JE":
                case "JG":
                case "JGE":
                case "JL":
                case "JLE":
                case "JMP":
                case "JNE":
                case "JNP":
                case "JP":
                case "JPO":
                    EmulatorFrame.memoList = null;
                    break;
                    
                case "LEA":
                    EmulatorFrame.memoList = null;
                    break;
                    
                case "LOOP":
                    EmulatorFrame.memoList = null;
                    break;
                    
                case "MOV":
                case "NEG":
                case "NOT":
                    if (res.size == 1) 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, res.getLowValue());
                        
                        EmulatorFrame.memoList = new int[1];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    } 
                    else 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, res.getLowValue());
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 1, res.getHighValue());
                        
                        EmulatorFrame.memoList = new int[2];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[1] = EmulatorFrame.Memory_Address++;
                    }
                    break;
                    
                case "NOP":
                    EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, 144);
                    
                    EmulatorFrame.memoList = new int[1];
                    EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    break;
                    
                case "POP":
                case "PUSH":
                    if (res.size == 1) 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, res.getLowValue());
                       
                        EmulatorFrame.memoList = new int[1];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                    } 
                    else 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, res.getLowValue());
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 1, res.getHighValue());
                        
                        EmulatorFrame.memoList = new int[2];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[1] = EmulatorFrame.Memory_Address++;
                    }
                    break;

                case "MUL":
                case "IMUL":
                    if (res.size == 1) 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, Register.getRegister().getValue("AL"));
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 1, Register.getRegister().getValue("AH"));
                       
                        EmulatorFrame.memoList = new int[2];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[1] = EmulatorFrame.Memory_Address++;
                    } 
                    else 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, Register.getRegister().getValue("AL"));
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 1, Register.getRegister().getValue("AH"));
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 2, Register.getRegister().getValue("DL"));
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 3, Register.getRegister().getValue("DH"));
                        
                        EmulatorFrame.memoList = new int[4];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[1] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[2] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[3] = EmulatorFrame.Memory_Address++;
                    }
                    break;

                case "DIV":
                case "IDIV":
                    if (res.size == 1) 
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, Register.getRegister().getValue("AL"));
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 1, Register.getRegister().getValue("AH"));
                        
                        EmulatorFrame.memoList = new int[2];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[1] = EmulatorFrame.Memory_Address++;
                    } 
                    else
                    {
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address, Register.getRegister().getValue("AL"));
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 1, Register.getRegister().getValue("AH"));
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 2, Register.getRegister().getValue("DL"));
                        EmulatorFrame.systemMemory.set(EmulatorFrame.Memory_Address + 3, Register.getRegister().getValue("DH"));
                      
                        EmulatorFrame.memoList = new int[4];
                        EmulatorFrame.memoList[0] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[1] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[2] = EmulatorFrame.Memory_Address++;
                        EmulatorFrame.memoList[3] = EmulatorFrame.Memory_Address++;
                    }
                    break;
            }
        } 
        catch (Exception ex) 
        {
            throw new Exception("Memory Updater Error in class: "+commande.line);
        }
    }
}
