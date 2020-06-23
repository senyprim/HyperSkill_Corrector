package correcter;

import java.util.*;
import java.util.stream.Collectors;

public class Repo {
    public static String replaceString="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 ";
    public static String[] splitToToken(String str, int size){
        int len=str.length()/size + (str.length()%size>0?1:0);
        String[] array = new String[len];
        for (int index=0;index<len;index++){
            array[index]=str.substring(index*size,Math.min((index+1)*size,str.length()));
        }
        return array;
    }
    public static String onceRandomChange(String str, int size ,Random rnd){
        int replaceIndex=rnd.nextInt(size);
        char newSymbol;
        do {
             newSymbol = replaceString.charAt(rnd.nextInt(replaceString.length() ));
        }while (newSymbol==str.charAt(replaceIndex));
        StringBuilder stb=new StringBuilder(str);
        stb.setCharAt(replaceIndex,newSymbol);
        return stb.toString();
    }
    public static String randomChange(String str,int size,Random rnd){
        return Arrays.stream(splitToToken(str, size))
                .map(token->onceRandomChange(token,size,rnd))
                .collect(Collectors.joining());
    }
    public static String encodeAll(String str,int size){
        return str.chars()
                .mapToObj(i->(char)i)
                .map(ch->ch.toString().repeat(size))
                .collect(Collectors.joining());
    }

    public static String decodeToken(String str){
        Map<Character,Integer> map = new HashMap<>();
        for(int index=0;index<str.length();index++){
            Character ch= str.charAt(index);
            if (!map.containsKey(ch)){
                map.put(ch,1);
            } else {
                int val = map.get(ch);
                map.put(ch,val+1);
            }
        }
        var entry=map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        return entry==null?null:entry.getKey().toString();
    }
    public static String decodeAll(String str,int size){
        return Arrays.stream(splitToToken(str, size))
                .map(Repo::decodeToken)
                .collect(Collectors.joining());
    }
}
