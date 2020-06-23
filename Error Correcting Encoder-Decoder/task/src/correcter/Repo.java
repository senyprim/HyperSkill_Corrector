package correcter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Repo {
    public static String replaceString="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ";
    public static String[] splitToSymbols(String str,int size){
        int len=str.length()/size + (str.length()%size>0?1:0);
        String[] array = new String[len];
        for (int index=0;index<len;index++){
            array[index]=str.substring(index*size,Math.min((index+1)*size,str.length()));
        }
        return array;
    }
    public static String onceRandomChange(String str, int size ,Random rnd){
        int replaceIndex=rnd.nextInt(size);
        char newSymbol=replaceString.charAt(rnd.nextInt(replaceString.length()+1));
        StringBuilder stb=new StringBuilder(str);
        stb.setCharAt(replaceIndex,newSymbol);
        return stb.toString();
    }
    public static String randomChange(String str,int size,Random rnd){
        return Arrays.stream(splitToSymbols(str, size))
                .map(token->onceRandomChange(token,size,rnd))
                .collect(Collectors.joining());
    }
}
