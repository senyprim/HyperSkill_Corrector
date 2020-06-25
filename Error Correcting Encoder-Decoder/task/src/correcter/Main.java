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
        try(FileOutputStream out = new FileOutputStream("encoded.txt" )){
            out.write(Hamming.encode(file));
        }
    }
    public static void send() throws IOException {
        byte[] file = Files.readAllBytes(Paths.get("encoded.txt"));
        try(FileOutputStream out = new FileOutputStream("received.txt" )){
            out.write(Hamming.send(file,new Random()));
        }
    }
    public static void decode() throws IOException {
        byte[] file = Files.readAllBytes(Paths.get("received.txt"));
        byte[] decode = Hamming.decode(Hamming.fix(file));
        try(FileOutputStream out = new FileOutputStream("decoded.txt" )){
            out.write(decode);
        }
    }

}
