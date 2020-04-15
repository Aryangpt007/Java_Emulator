/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emulator8086;

import emulator8086.Variablev.VariableType;
import line.Commandn;
import java.util.List;
import steps.Stack;
import steps.StackElement;

/**
 *
 * @author Anshul
 */
public class Instructions 
{

    public static int MOV(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        dest.setValue(src.getValue());
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int ADD(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        long result = dest.getValue().getValue() + src.getValue().getValue();
        StackElement res = new StackElement(dest.size, setFlagStatesForAdd(dest.size, new Long(result).intValue()));
        dest.setValue(res);
        
        Flag.getFlag().SF = dest.getMostSignificantBit().equals("1");
        Flag.getFlag().OF = dest.getMostSignificantBit().equals(src.getMostSignificantBit()) && !res.getMostSignificantBit().equals(src.getMostSignificantBit());
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;
        
        MemoryUpdater.updateMemory(commande, res);
        
        return ++line1;
    }

    public static int SUB(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        long result = dest.getValue().getValue() - src.getValue().getValue();
        StackElement res = new StackElement(dest.size, setFlagStatesForAdd(dest.size, new Long(result).intValue()));
        dest.setValue(res);
        
        Flag.getFlag().SF = dest.getMostSignificantBit().equals("1");
        Flag.getFlag().OF = dest.getMostSignificantBit().equals(src.getMostSignificantBit()) && !res.getMostSignificantBit().equals(src.getMostSignificantBit());
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;
        
        MemoryUpdater.updateMemory(commande, res);
        
        return ++line1;
    }

    public static int PUSH(int line1, Commandn commande) throws Exception 
    {
        Variablev v = (Variablev) commande.getVariableList().get(0);
        
        if (v.typeT == VariableType.REGISTER) 
        {
            Stack.getStack().push(v.getValue());
        } 
        else if (v.typeT == VariableType.MEMORY) 
        {
            Stack.getStack().push(v.getValue());
        } 
        else 
        {
            throw new Exception("The pushed value must be register or memory");
        }
        
        MemoryUpdater.updateMemory(commande, v.getValue());
        
        return ++line1;
    }

    public static int POP(int line1, Commandn commande) throws Exception 
    {
        Variablev dest = (Variablev) commande.getVariableList().get(0);
        StackElement element = Stack.getStack().pop();
        
        if (dest.size < 2) 
        {
            throw new Exception("Popped destination size must be word (2 bytes).");
        }
        dest.setValue(element);
        
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int ADC(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        long result = dest.getValue().getValue() + src.getValue().getValue() + (Flag.getFlag().CF ? 1 : 0);
        StackElement res = new StackElement(dest.size, setFlagStatesForAdd(dest.size, new Long(result).intValue()));
        dest.setValue(res);
        
        Flag.getFlag().SF = dest.getMostSignificantBit().equals("1");
        Flag.getFlag().OF = dest.getMostSignificantBit().equals(src.getMostSignificantBit()) && !res.getMostSignificantBit().equals(src.getMostSignificantBit());
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;
        
        MemoryUpdater.updateMemory(commande, res);
        
        return ++line1;
    }

    public static int AND(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);

        String result = "";
        String destOperand = dest.getBinaryValue(dest.size);
        String srcOperand = src.getBinaryValue(dest.size);
        
        for (int i = 0; i < destOperand.length(); i++) 
        {
            result += ((destOperand.charAt(i) == '1' && srcOperand.charAt(i) == '1') ? '1' : '0');
        }

        dest.setBinaryValue(dest.size, result);
        
        Flag.getFlag().CF = false;
        Flag.getFlag().OF = false;
        Flag.getFlag().ZF = dest.getValue().getValue() == 0;
        Flag.getFlag().SF = dest.getMostSignificantBit().equals("1");
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;
        
        MemoryUpdater.updateMemory(commande, dest.getValue());

        return ++line1;
    }

    public static int CLC(int line1, Commandn commande) throws Exception 
    {
        Flag.getFlag().CF = false;
        
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int CLD(int line1, Commandn commande) throws Exception 
    {
        Flag.getFlag().DF = false;
        
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int CMP(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        long result = dest.getValue().getValue() - src.getValue().getValue();
        Variablev dummy = new Variablev(setFlagStatesForAdd(dest.size, new Long(result).intValue()));
       
        Flag.getFlag().SF = dummy.getMostSignificantBit().equals("1");
        Flag.getFlag().PF = (dummy.getBinaryValue().length() - dummy.getBinaryValue().replace("1", "").length()) % 2 == 1;
        
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int DEC(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        StackElement result = dest.getValue();
        
        result.setValue(new Long(result.getValue() - 1).intValue());
        dest.setValue(result);
        
        Flag.getFlag().ZF = (result.getValue() == 0);
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;
        
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int DIV(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev src = list.get(0);
        
        switch (src.size) 
        {
            case 1:
                long ax = Register.getRegister().getValue("AX");
                long ah = ax % src.getValue().getValue();
                long al = (ax - (ax % src.getValue().getValue())) / src.getValue().getValue();
                
                Register.getRegister().setValue("AH", new Long(ah).intValue());
                Register.getRegister().setValue("AL", new Long(al).intValue());
                
                break;
            case 2:
                long dxax = (Register.getRegister().getValue("AX").longValue() * 256 * 256) + Register.getRegister().getValue("AX").longValue();
                long ldx = dxax % src.getValue().getValue().longValue();
                long lax = (dxax - (dxax % src.getValue().getValue().longValue())) / src.getValue().getValue().longValue();
                
                Register.getRegister().setValue("DX", new Long(ldx).intValue());
                Register.getRegister().setValue("AX", new Long(lax).intValue());
                
                break;
        }
        MemoryUpdater.updateMemory(commande, src.getValue());
        
        return ++line1;
    }

    public static int HLT(int line1, Commandn commande) throws Exception 
    {
        MemoryUpdater.updateMemory(commande, null);
        
        return -1;//-1 = end the program
    }

    public static int IDIV(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev src = list.get(0);
        
        switch (src.size) 
        {
            case 1:
                long ax = new StackElement(2,Register.getRegister().getValue("AX")).getSignedValue();
                long ah = ax % src.getValue().getSignedValue();
                long al = (ax - (ax % src.getValue().getSignedValue())) / src.getValue().getSignedValue();
                
                if(al < 256)
                {
                    al += 256;
                }
                    
                Register.getRegister().setValue("AH", new Long(ah).intValue());
                Register.getRegister().setValue("AL", new Long(al).intValue());
                
                break;
            case 2:
                long dxax = new StackElement(4,(Register.getRegister().getValue("AX").longValue() * 256 * 256) + Register.getRegister().getValue("AX").longValue()).getSignedValue().longValue();
                long ldx = dxax % src.getValue().getValue().longValue();
                long lax = (dxax - (dxax % src.getValue().getValue().longValue())) / src.getValue().getValue().longValue();
                
                if(lax < 256*256)
                {
                    lax += 256*256;
                }
                    
                Register.getRegister().setValue("DX", new Long(ldx).intValue());
                Register.getRegister().setValue("AX", new Long(lax).intValue());
                
                break;
        }
        MemoryUpdater.updateMemory(commande, src.getValue());
        
        return ++line1;
    }

    public static int IMUL(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev src = list.get(0);
      
        long result;
        long lngresult;
        
        switch (src.size) 
        {
            case 1:
                result = src.getValue().getSignedValue() * new StackElement(1, Register.getRegister().getValue("AL")).getSignedValue();
               
                Flag.getFlag().CF = result >= 256;
                Flag.getFlag().OF = result >= 256;
                
                Register.getRegister().setValue("AX", new Long(result).intValue());
                
                break;
            case 2:
                long srcval = src.getValue().getSignedValue();
                long regval = new StackElement(2, Register.getRegister().getValue("AX")).getSignedValue();
                
                lngresult = srcval * regval;
                
                Flag.getFlag().CF = new Long(lngresult).compareTo(new Long(256 * 256)) >= 0;
                Flag.getFlag().OF = new Long(lngresult).compareTo(new Long(256 * 256)) >= 0;
                
                long axValue = lngresult % 65536;
                long dxValue = (lngresult - (lngresult % (256 * 256))) / (256 * 256);
                
                Register.getRegister().setValue("AX", new Long(axValue).intValue());
                Register.getRegister().setValue("DX", new Long(dxValue).intValue());
                
                break;
        }
        MemoryUpdater.updateMemory(commande, src.getValue());
        
        return ++line1;
    }

    public static int INC(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        StackElement result = dest.getValue();
        result.setValue(new Long(result.getValue() + 1).intValue());

        dest.setValue(result);
        Flag.getFlag().ZF = (result.getValue() == 0);
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;
        MemoryUpdater.updateMemory(commande, null);
        return ++line1;
    }

    public static int JA(int line1, Commandn commande) throws Exception 
    {
        if (!Flag.getFlag().CF && !Flag.getFlag().ZF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JAE(int line1, Commandn commande) throws Exception 
    {
        if (!Flag.getFlag().CF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JB(int line1, Commandn commande) throws Exception 
    {
        if (Flag.getFlag().CF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JBE(int line1, Commandn commande) throws Exception 
    {
        if (Flag.getFlag().CF && Flag.getFlag().ZF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JE(int line1, Commandn commande) throws Exception 
    {
        if (Flag.getFlag().ZF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JG(int line1, Commandn commande) throws Exception 
    {
        if (!Flag.getFlag().ZF && Flag.getFlag().SF == Flag.getFlag().OF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JGE(int line1, Commandn commande) throws Exception 
    {
        if (Flag.getFlag().SF == Flag.getFlag().OF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JL(int line1, Commandn commande) throws Exception 
    {
        if (Flag.getFlag().SF != Flag.getFlag().OF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JMP(int line1, Commandn commande) throws Exception 
    {
        
        MemoryUpdater.updateMemory(commande, null);
        
        return commande.functionLine;
    }

    public static int JLE(int line1, Commandn commande) throws Exception 
    {
        if (Flag.getFlag().SF != Flag.getFlag().OF && Flag.getFlag().ZF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JNE(int line1, Commandn commande) throws Exception 
    {
        if (!Flag.getFlag().ZF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JNP(int line1, Commandn commande) throws Exception 
    {
        if (!Flag.getFlag().PF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JP(int line1, Commandn commande) throws Exception 
    {
        if (Flag.getFlag().PF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int JPO(int line1, Commandn commande) throws Exception 
    {
        if (!Flag.getFlag().PF) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int LEA(int line1, Commandn commande) throws Exception 
    {
        throw new Exception("LEA instruction is not supported");
    }

    public static int LOOP(int line1, Commandn commande) throws Exception 
    {
        Register.getRegister().setValue("CX", Register.getRegister().getValue("CX") - 1);
        
        if (Register.getRegister().getValue("CX") != 0) 
        {
            return commande.functionLine;
        }
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int MUL(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev src = list.get(0);
        long result;
        
        switch (src.size) 
        {
            case 1:
                result = src.getValue().getValue() * Register.getRegister().getValue("AL");
               
                Flag.getFlag().CF = result >= 256;
                Flag.getFlag().OF = result >= 256;
                
                Register.getRegister().setValue("AX", new Long(result).intValue());
                
                break;
            case 2:
                long srcval = src.getValue().getValue();
                long regval = Register.getRegister().getValue("AX");
                
                result = srcval * regval;
                
                Flag.getFlag().CF = new Long(result).compareTo(new Long(256 * 256)) >= 0;
                Flag.getFlag().OF = new Long(result).compareTo(new Long(256 * 256)) >= 0;
                
                long axValue = result % 65536;
                long dxValue = (result - (result % (256 * 256))) / (256 * 256);
                
                Register.getRegister().setValue("AX", new Long(axValue).intValue());
                Register.getRegister().setValue("DX", new Long(dxValue).intValue());
                
                break;
        }
        MemoryUpdater.updateMemory(commande, src.getValue());
        
        return ++line1;
    }

    public static int NEG(int line1, Commandn commande) throws Exception {
        //negative of number
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        StackElement result = dest.getValue();

        if (dest.size == 1) 
        {
            result.setValue(256 - result.getValue().intValue());
        }
        
        if (dest.size == 2) 
        {
            result.setValue(65536 - result.getValue().intValue());
        }
        
        dest.setValue(result);
        
        Flag.getFlag().ZF = (result.getValue() == 0);
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;
        Flag.getFlag().SF = dest.getMostSignificantBit().equals("1");
        Flag.getFlag().OF = (dest.size == 1 && dest.getValue().getValue() == 128) || (dest.size == 2 && dest.getValue().getValue() == 32768);
        
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int NOP(int line1, Commandn commande) throws Exception 
    {
        //command that causes the wait, no effect 
        //Do nothing
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int NOT(int line1, Commandn commande) throws Exception 
    {
        //not number
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        StackElement result = dest.getValue();

        if (dest.size == 1) 
        {
            result.setValue(255 - result.getValue().intValue());
        }
        
        if (dest.size == 2) 
        {
            result.setValue(65535 - result.getValue().intValue());
        }
        dest.setValue(result);
       
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int OR(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);

        String result = "";
        String destOperand = dest.getBinaryValue(dest.size);
        String srcOperand = src.getBinaryValue(dest.size);
        
        for (int i = 0; i < destOperand.length(); i++) 
        {
            result += ((destOperand.charAt(i) == '1' || srcOperand.charAt(i) == '1') ? '1' : '0');
        }

        dest.setBinaryValue(dest.size, result);
        
        Flag.getFlag().CF = false;
        Flag.getFlag().OF = false;
        Flag.getFlag().ZF = dest.getValue().getValue() == 0;
        Flag.getFlag().SF = dest.getMostSignificantBit().equals("1");
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;

        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int XOR(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);

        String result = "";
        String destOperand = dest.getBinaryValue(dest.size);
        String srcOperand = src.getBinaryValue(dest.size);
        for (int i = 0; i < destOperand.length(); i++) 
        {
            result += (((destOperand.charAt(i) == '1' && srcOperand.charAt(i) == '0') || (destOperand.charAt(i) == '0' && srcOperand.charAt(i) == '1')) ? '1' : '0');
        }

        dest.setBinaryValue(dest.size, result);
        
        Flag.getFlag().CF = false;
        Flag.getFlag().OF = false;
        Flag.getFlag().ZF = dest.getValue().getValue() == 0;
        Flag.getFlag().SF = dest.getMostSignificantBit().equals("1");
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;

        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int STD(int line1, Commandn commande) throws Exception 
    {
        Flag.getFlag().DF = true;
       
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int STC(int line1, Commandn commande) throws Exception 
    {
        Flag.getFlag().CF = true;
      
        MemoryUpdater.updateMemory(commande, null);
        
        return ++line1;
    }

    public static int SHR(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        String result = "";
        
        if (dest.typeT == VariableType.REGISTER && src.typeT == VariableType.IMMEDIATE) 
        {
            result = record(dest.getBinaryValue(), -1 * src.value, false);
        } 
        else
        {
            throw new Exception("Wrong types for ROL instruction");
        }
        
        dest.setValue(new StackElement(dest.size, result));
        
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int SHL(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        String result = "";
        
        if (dest.typeT == VariableType.REGISTER && src.typeT == VariableType.IMMEDIATE) 
        {
            result = record(dest.getBinaryValue(), src.value, false);
        } 
        else 
        {
            throw new Exception("Wrong types for ROL instruction");
        }
        dest.setValue(new StackElement(dest.size, result));
        
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int SBB(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        long result = dest.getValue().getValue() - (src.getValue().getValue() + (Flag.getFlag().CF ? 1 : 0));
        
        StackElement res = new StackElement(dest.size, setFlagStatesForAdd(dest.size, new Long(result).intValue()));
        dest.setValue(res);
        
        Flag.getFlag().OF = dest.getMostSignificantBit().equals(src.getMostSignificantBit()) && !res.getMostSignificantBit().equals(src.getMostSignificantBit());
        Flag.getFlag().PF = (dest.getBinaryValue().length() - dest.getBinaryValue().replace("1", "").length()) % 2 == 0;
        
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int ROR(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        String result = "";
        
        if (dest.typeT == VariableType.REGISTER && src.typeT == VariableType.IMMEDIATE) 
        {
            result = record(dest.getBinaryValue(), -1 * src.value, true);
        } 
        else 
        {
            throw new Exception("Wrong types for ROL instruction");
        }
        dest.setValue(new StackElement(dest.size, result));
        
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    public static int ROL(int line1, Commandn commande) throws Exception 
    {
        List<Variablev> list = commande.getVariableList();
        Variablev dest = list.get(0);
        Variablev src = list.get(1);
        
        String result = "";
        
        if (dest.typeT == VariableType.REGISTER && src.typeT == VariableType.IMMEDIATE) 
        {
            result = record(dest.getBinaryValue(), src.value, true);
        } 
        else 
        {
            throw new Exception("Wrong types for ROL instruction");
        }
        dest.setValue(new StackElement(dest.size, result));
        
        MemoryUpdater.updateMemory(commande, dest.getValue());
        
        return ++line1;
    }

    private static String record(String binary, int i, boolean roOperation) 
    {
        String buffer;
       
        for (int j = 0; j < i; j++) 
        {//left
            buffer = binary.toString();
            binary = binary + (roOperation ? binary.charAt(0) : '0');
            binary = binary.substring(1);
            
            Flag.getFlag().CF = binary.charAt(buffer.length() - 1) == '1';
            Flag.getFlag().OF = buffer.charAt(0) != binary.charAt(0);
            
            if (!roOperation) 
            {
                Flag.getFlag().ZF = !binary.contains("1");
            }
        }
        
        for (int j = 0; i < j; j--) 
        {//right
            buffer = binary.toString();
            binary = (roOperation ? binary.charAt(binary.length() - 1) : '0') + binary;
            binary = binary.substring(0, binary.length() - 1);
            
            Flag.getFlag().CF = binary.charAt(0) == '1';
            Flag.getFlag().OF = buffer.charAt(0) != binary.charAt(0);
            
            if (!roOperation) {
                Flag.getFlag().ZF = !binary.contains("1");
            }
        }
        return binary;
    }

    private static int setFlagStatesForAdd(int size, int result) 
    {
        switch (size) 
        {
            case 1:
                if (result > 255 || result < 0) 
                {
                    Flag.getFlag().CF = true;
                    result %= 256;
                } 
                else 
                {
                    Flag.getFlag().CF = false;
                }
                
                Flag.getFlag().ZF = result == 0;
                Flag.getFlag().SF = result > 127;
                
                break;
            case 2:
                if (result > 65535 || result < 0) 
                {
                    Flag.getFlag().CF = true;
                    result %= 256;
                } 
                else 
                {
                    Flag.getFlag().CF = false;
                }
                
                Flag.getFlag().ZF = result == 0;
                Flag.getFlag().SF = result > 32767;
                
                break;

        }
        return result;
    }

}
