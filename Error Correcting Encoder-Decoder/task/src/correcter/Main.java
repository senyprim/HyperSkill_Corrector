package correcter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        encode();
        send();
        decode();
    }
    public static void encode() throws IOException {
        byte[] file = Files.readAllBytes(Paths.get("send.txt"));
        for(byte b : file){
            System.out.print(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
            System.out.print(" ");
        }
        System.out.println();
        byte[] encode = CheckSum.encode(file);
        for(byte b : encode){
            System.out.print(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
            System.out.print(" ");
        }
        System.out.println();
        try(FileOutputStream out = new FileOutputStream("encoded.txt" )){
            out.write(encode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void send() throws IOException {
        byte[] file = Files.readAllBytes(Paths.get("encoded.txt"));
        byte[] send = CheckSum.send(file,new Random());
        for(byte b : send){
            System.out.print(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
            System.out.print(" ");
        }
        System.out.println();
        try(FileOutputStream out = new FileOutputStream("received.txt" )){
            out.write(send);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void decode() throws IOException {
        byte[] file = Files.readAllBytes(Paths.get("received.txt"));
        byte[] decode = CheckSum.decode(file);
        for(byte b : decode){
            System.out.print(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
            System.out.print(" ");
        }
        System.out.println();
        try(FileOutputStream out = new FileOutputStream("decoded.txt" )){
            out.write(decode);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
