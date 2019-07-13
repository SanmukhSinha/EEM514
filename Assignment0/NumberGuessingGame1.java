import java.util.Scanner;

public class NumberGuessingGame1 {

    public static int guess(int low, int high, int turns, Scanner in) {
        if (low <= high) {
            int mid = (low + high) / 2;

            System.out.println("Is your number less than greater than or equal to: " + mid);
            int num = in.nextInt();

            switch (num) {
            case 1:
                turns++;
                guess(low, mid - 1, turns, in);
                break;

            case 2:
                turns++;
                guess(mid + 1, high, turns, in);
                break;

            case 3:
                turns++;
                System.out.println("Found your Number: " + mid + " in " + turns + " turns");
                break;

            default:
                System.out.println("Enter correct option...");
                guess(low, high, turns, in);
                break;
            }
            return 0;
        } else {
            System.out.println("Error: You did something wrong");
            return 1;
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

        System.out.println("Think of a number in this range\n");
        System.out.println("Enter: \n1 for Less than \n2 for Greater than \n3 for Equal\n ");

        guess(low, high, 0, in);
        in.close();
    }
}