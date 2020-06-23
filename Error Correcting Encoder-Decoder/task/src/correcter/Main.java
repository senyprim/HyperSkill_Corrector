package correcter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] file = Files.readAllBytes(new File("send.txt").toPath());
        file=Repo.changeAllByte(file,new Random());
        try(FileOutputStream out = new FileOutputStream("received.txt" )){
            out.write(file);
        }

    }
}
