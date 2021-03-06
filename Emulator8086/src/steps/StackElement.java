/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package steps;

/**
 *
 * @author Anshul
 */
public class StackElement 
{
    public int size;
    private long value;

    public StackElement(int size, long value) 
    {
        this.size = size;
        this.value = value;
    }
    
    public StackElement(int size, String binary) 
    {
        this.size = size;
        this.value = Integer.parseInt(binary, 2);
    }
    
    public void setValue(int value)
    {
        if(size == 0)
            this.value = value;
        else
        {
            int mod = 1;
            
            for(int i = 0; i < size;i++)
                mod *= 256;
            
            this.value = value % mod;
        }
    }
    
    public Long getValue()
    {
        return new Long(value);
    }
    
    public int getLowValue()
    {
        return new Integer(new Long((value + 256) %256).toString()).intValue();
    }
    
    public int getHighValue()
    {
        int vallue = new Integer(new Long(value).toString());
        
        if(size > 1)
        {
            int temp = (vallue + 256) %256;
            temp = (vallue - temp)/256;
            
            return temp;
        }
        return 0;
        
    }
    
    public String getMostSignificantBit()
    {
        return ""+getBinaryValue().charAt(0);
    }
    
    public Long getSignedValue()
    {
        if(getMostSignificantBit().equals("0"))
            return new Long(value);
        else
        {
            //reverse binary
            String reversed = getBinaryValue().replaceAll("1","2").replaceAll("0", "1").replaceAll("2", "0");
            return new Long(-1 * (Integer.parseInt(reversed, 2) + 1));
        }
    }
    
    public String getBinaryValue()
    {
        String result = "";
        long val = value;
        
        while(val >= 2)
        {
            long ch = val % 2;
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
    
    private String getChar(long val)
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
}
