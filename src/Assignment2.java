import java.io.*;
import java.util.Scanner;
public class Assignment2 {
	public static void main(String[] args) throws FileNotFoundException {
		File ben = new File("/home/can/Desktop/example.txt");
		Scanner dosya = new Scanner(ben);
		System.out.println(dosya.nextLine());
	}
}
