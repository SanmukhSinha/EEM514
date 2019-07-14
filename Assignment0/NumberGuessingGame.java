import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {

    public static void guess(int low, int high, Scanner in) {
        Random rand;
        int randomNum, guessNum, turns = 0;

        rand = new Random(System.currentTimeMillis());
        randomNum = rand.nextInt(high - low + 1) + low;

        while (true) {
            turns++;
            guessNum = in.nextInt();
            if (guessNum < low || guessNum > high) {
                System.out.println("Enter a number in the range");
                turns--;
            }else if (guessNum < randomNum) {
                System.out.println("Think Higher \n");
            } else if (guessNum > randomNum) {
                System.out.println("Think Lower \n");
            } else {
                System.out.println("Number matched in " + turns + " tries");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter a range of numbers: ");
        int low = in.nextInt();
        int high = in.nextInt();

        if (high < low) {
            int temp = low;
            low = high;
            high = temp;
        }

        System.out.println("Computer chose a number in this range\n\nEnter your Guess:");

        guess(low, high, in);
        in.close();
    }
}