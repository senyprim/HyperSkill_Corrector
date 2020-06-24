package correcter;

import java.util.Random;

public class Hamming {
    private static byte encode(byte data){
        byte mask= (byte) ((data&128)>>2|(data&64)>>3|(data&32)>>3|(data&16)>>3);
        mask=(byte) (mask
                |((mask&32)>>5^(mask&8)>>3^(mask&2)>>1)<<7
                |((mask&32)>>5^(mask&4)>>2^(mask&2)>>1)<<6
                |((mask&16)>>4^(mask&8)>>3^(mask&4)>>2^(mask&2)>>1)<<4);
        return mask;
   }
   public static byte[] encode(byte[] data){
        byte[] result=new byte[data.length*2];
        for(int index=0;index<data.length;index++){
            result[index*2]=encode(data[index]);
            result[index*2+1]=encode((byte) (data[index]<<4));
        }
        return result;
   }

    public static byte[] send(byte[] data, Random rnd){
        for(int index=0;index<data.length;index++){
            data[index]= (byte) (data[index]^1<<rnd.nextInt(8));
        }
        return  data;
    }

   private static byte getCheckBits(byte data){
       return (byte) (data&128|data&64|data&16);
   }
   private static byte calculateCheckBits(byte mask){
        byte result=(byte)
                (((mask&32)>>5^(mask&8)>>3^(mask&2)>>1)<<7
                |((mask&32)>>5^(mask&4)>>2^(mask&2)>>1)<<6
                |((mask&16)>>4^(mask&8)>>3^(mask&4)>>2^(mask&2)>>1)<<4);
        return result;
   }
    public static byte fix(byte data){
        byte change=(byte)(getCheckBits(data)^calculateCheckBits(data));
        if (change==0) return data;
        int errorPosition=0;
        for(int position=0;position<4;position++){
            errorPosition+=(change<<position&128)*position;
        }
        return (byte)(128>>errorPosition^data);
   }
   public static byte[] fix(byte[] data){
        byte[] result = new byte[data.length];
        for(int index=0;index<data.length;index++){
            result[index]=fix(data[index]);
        }
        return result;
   }
   private static byte decode(byte data){
       return (byte) ((data&32)<<2|(data&8)<<3|(data&4)<<3|(data&2)<<3);
   }

   public static byte[] decode(byte[] data){
        byte[] result= new byte[data.length/2];
        for(int index=0;index<result.length;index++){
            result[index]=(byte) (decode(data[index*2])|decode(data[index*2+1])>>4);
        }
        return result;
   }
}
