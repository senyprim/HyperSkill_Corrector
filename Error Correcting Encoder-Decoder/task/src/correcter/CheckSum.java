package correcter;

import java.util.ArrayList;
import java.util.Random;

public class CheckSum {
    public static byte[] encode(byte[] data){
        int size=data.length*8/3+((data.length*8)%3>0?1:0);
        byte[] result = new byte[size];
        int byteCounter=0;
        int bitCounter=0;
        int checkSum=0;
        byte outByte=0;
        for(int byteIndex=0;byteIndex<data.length;byteIndex++){
            byte inByte=data[byteIndex];
            for (int bitIndex=0;bitIndex<8;bitIndex++){
                boolean val=getBit(inByte,bitIndex);
                checkSum+=val?1:0;
                outByte=setPairBit(outByte,bitCounter,val);
                bitCounter+=2;
                if (bitCounter==6){
                    outByte=setPairBit(outByte,6,checkSum%2!=0);
                    checkSum=0;
                    bitCounter=8;
                }
                if (bitCounter==8){
                    result[byteCounter++]=outByte;
                    outByte=0;
                    bitCounter=0;
                }
                System.out.println(Integer.toBinaryString((outByte & 0xFF) + 0x100).substring(1));
            }
        }
        return result;
    }

    private static byte setBit(byte data,int index,boolean val){
        return (byte) (!val?data&(~1<<index):data|1<<index);
    }
    private static boolean getBit(byte data, int index){
        return (data&(1<<index))==0?false:true;
    }
    private static byte setPairBit(byte data,int index,boolean val){
        if (index>6) throw new IllegalArgumentException("Wrong bit index");
        byte first=setBit(data,index,val);
        return setBit(first,index+1,val);
    }
    private static boolean isErrorPair(byte data,int pairNumber){
        return getBit(data,pairNumber*2)^getBit(data,pairNumber*2+1);
    }
    private static boolean[] getDecodeBits(byte data){
        boolean[] bit = new boolean[4];
        int errorIndex=-1;
        boolean checksum=false;
        for(int i=0;i<4;i++){
            boolean val = getBit(data,i*2);
            if (isErrorPair(data,i)){
                errorIndex=1;
            }else {
                if (i==0&&errorIndex<0||i==1&&errorIndex>=0){
                    checksum=val;
                } else{
                    checksum^=val;
                }
                bit[i]=val;
            }
        }
        bit[errorIndex]=checksum;
        return bit;
    }

    public static byte[] decode(byte[] data){
        byte[] result=new byte[data.length*3/8+((data.length*3)%8>0?1:0)];
        byte decodeByte=0;
        int byteCounter=0;
        int bitCounter=0;
        for(int byteIndex=0;byteIndex<data.length;byteIndex++){
            boolean[] threeBit=getDecodeBits(data[byteIndex]);
            for(int pairIndex=0;pairIndex<3;pairIndex++){
                decodeByte=setBit(decodeByte,bitCounter++,threeBit[pairIndex]);
                if (bitCounter==8){
                    result[byteCounter++]=decodeByte;
                    bitCounter=0;
                    decodeByte=0;
                }
            }
        }
        return result;
    }

    public static byte[] send(byte[] data,Random rnd){
        for(int index=0;index<data.length;index++){
            data[index]= (byte) (data[index]^1<<rnd.nextInt(8));
        }
        return  data;
    }
}
