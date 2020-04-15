/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emulator8086;

import steps.StackElement;

/**
 *
 * @author Aryan
 */
public class Variablev 
{

    public VariableType typeT;
    public String ValueV;
    public int value;//decimal Value
    public int size;//byte the size of 1-2 byte
    
    

    public Variablev(int value) 
    {
        typeT = VariableType.IMMEDIATE;
        this.value = value;
        this.size = 0;
    }

    public Variablev(String reg) 
    {
        reg = reg.toUpperCase();
        typeT = VariableType.REGISTER;
        ValueV = reg;
        if (reg.equals("AX") || reg.equals("BX") || reg.equals("CX") || reg.equals("DX")) 
        {
            size = 2;
        } 
        else 
        {
            size = 1;
        }
    }

    public Variablev(String VariableName, int sizeS, int index) 
    {
        typeT = VariableType.MEMORY;
        ValueV = VariableName;
        size = sizeS;
        value = index;
    }

    public StackElement getValue() 
    {
        if (typeT == VariableType.REGISTER) 
        {
            return new StackElement(size, Register.getRegister().getValue(ValueV));
        } 
        else if (typeT == VariableType.MEMORY)
        {
            return new StackElement(size, EmulatorFrame.variableMap.get(ValueV).getValue(value));
        } 
        else 
        {
            return new StackElement(0, value);
        }
    }

    public void setValue(StackElement element) throws Exception 
    {
        if (typeT == VariableType.REGISTER && (element.size == size || element.size == 0)) 
        {
            Register.getRegister().setValue(ValueV, element.getValue().intValue());
        } 
        else if (typeT == VariableType.MEMORY && (element.size == size || element.size == 0)) 
        {
            EmulatorFrame.variableMap.get(ValueV).setValue(value, element.getValue().intValue());
        } 
        else if ((typeT == VariableType.REGISTER && element.size != size) || (typeT == VariableType.MEMORY && element.size != size)) 
        {
            throw new Exception("Size Error.");
        } 
        else 
        {
            throw new Exception("No Value can be set");
        }
    }
    public void setBinaryValue(int size, String strg) throws Exception 
    {
        
        if (typeT == VariableType.REGISTER && (size == size || size == 0)) 
        {
            Register.getRegister().setValue(ValueV, Integer.parseInt(strg, 2));
        } 
        else if (typeT == VariableType.MEMORY && (size == size || size == 0)) 
        {
            EmulatorFrame.variableMap.get(ValueV).setValue(value, Integer.parseInt(strg, 2));
        } 
        else if ((typeT == VariableType.REGISTER && size != size) || (typeT == VariableType.MEMORY && size != size)) 
        {
            throw new Exception("Size Error.\n Register/Memory size do not match");
        } 
        else 
        {
            throw new Exception("no value can be set");
        }
    }
    public String getHexValue()
    {
        String result = "";
        int val = -1;
        
        if(typeT == VariableType.REGISTER)
            val = Register.getRegister().getValue(ValueV);
        else if(typeT == VariableType.MEMORY)
            val = EmulatorFrame.variableMap.get(ValueV).getValue(value);
        else
            val = value;
        
        while(val >= 16)
        {
            int ch = val % 16;
            result += getChar(ch);
            val = (val-ch)/16;
        }
        
        result += getChar(val);
        
        if(size == 1)
            result = fillWithZero(2,result);
        
        if(size == 2)
            result = fillWithZero(4,result);
        
        if(result.endsWith("A") ||
                result.endsWith("B") ||
                result.endsWith("C") ||
                result.endsWith("D") ||
                result.endsWith("E"))
            result +="0";
       
        return new StringBuilder(result).reverse().toString()+"h";
    }
    
    public String getMostSignificantBit()
    {
        return ""+Variablev.this.getBinaryValue().charAt(0);
    }
    
    public String getBinaryValue()
    {
        String result = "";
        int val = -1;
        
        if(typeT == VariableType.REGISTER)
            val = Register.getRegister().getValue(ValueV);
        else if(typeT == VariableType.MEMORY)
            val = EmulatorFrame.variableMap.get(ValueV).getValue(value);
        else
            val = value;
        
        while(val >= 2)
        {
            int ch = val % 2;
            result += getChar(ch);
            val = (val-ch)/2;
        }
        
        result += getChar(val);
        
        if(size == 1)
            result = fillWithZero(8,result);
        
        if(size == 2)
            result = fillWithZero(16,result);
        
        return new StringBuilder(result).reverse().toString();
    }
    
    public String getBinaryValue(int sizeS)
    {
        String result = "";
        int val = -1;
        
        if(typeT == VariableType.REGISTER)
            val = Register.getRegister().getValue(ValueV);
        else if(typeT == VariableType.MEMORY)
            val = EmulatorFrame.variableMap.get(ValueV).getValue(value);
        else
            val = value;
        
        while(val >= 2)
        {
            int ch = val % 2;
            result += getChar(ch);
            val = (val-ch)/2;
        }
        
        result += getChar(val);
        
        if(sizeS == 1)
            result = fillWithZero(8,result);
       
        if(sizeS == 2)
            result = fillWithZero(16,result);
        
        return new StringBuilder(result).reverse().toString();
    }
    
    private String getChar(int val)
    {
        if(val < 10)
            return val+"";
        
        return ((char)(55+val))+"";
    }
    
    private String fillWithZero(int lngth, String result)
    {
        while(result.length() < lngth)
        {
            result+="0";
        }
        
        return result;
    }

    public enum VariableType
    {

        REGISTER, //AX,BX like Register
        MEMORY, //DB DW variable in types
        IMMEDIATE // 12h a value like
    }

}
