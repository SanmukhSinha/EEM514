import java.util.Scanner;

public class guessmenew {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter an integer between zero and nine");
        int num = in.nextInt();
        int randNum = (int) (1000.0 * (1.0 + Math.random()));
        System.out.println("I converted " + num + " into " + randNum + " ! For full credit, go and tell Prem how I did it!");
        in.close();
    }
}