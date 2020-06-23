package correcter;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println(str);
        str=Repo.encodeAll(str,3);
        System.out.println(str);
        str=Repo.randomChange(str,3,new Random());
        System.out.println(str);
        str=Repo.decodeAll(str,3);
        System.out.println(str);

    }
}
