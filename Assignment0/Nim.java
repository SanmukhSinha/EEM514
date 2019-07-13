import java.util.Scanner;
import java.util.Random;

public class Nim {

    public static int playerMove(Scanner in) {
        System.out.print("\nYour Move: ");
        int player = in.nextInt();

        while (player < 0 || player > 3) {
            System.out.println("Enter correct number");
            System.out.print("\nYour Move: ");
            player = in.nextInt();
        }

        return player;
    }

    public static int compMove() {
        Random rand = new Random(System.nanoTime());

        int comp = rand.nextInt(3) + 1;
        System.out.println("\nComputer Move: " + comp);

        return comp;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Select Level: 1  2 ");
        int lvl = in.nextInt();
        System.out.print("Enter total of pen: ");
        int total = in.nextInt();

        System.out.println("Choose from 1, 2 or 3");
        int count = 0;

        switch (lvl) {
        case 1:
            while (true) {
                count += playerMove(in);
                if (count >= total) {
                    System.out.println("Total: " + count);
                    System.out.println("\nYou Won");
                    break;
                }
                System.out.println("Total: " + count);

                count += compMove();
                if (count >= total) {
                    System.out.println("Total: " + count);
                    System.out.println("\nComputer Won");
                    break;
                }
                System.out.println("Total: " + count);
            }
            break;

        case 2:
            int lastMove, comp;

            if (total % 4 == 0) {
                lastMove = playerMove(in);
                count += lastMove;
                System.out.println("Total: " + count);
            } else {
                lastMove = 4 - total % 4;
            }

            while (count < total) {
                comp = 4 - lastMove;
                count += comp;
                System.out.println("\nComputer Move: " + comp);
                if (count >= total) {
                    System.out.println("Total: " + count);
                    System.out.println("\nComputer Won");
                    break;
                }
                System.out.println("Total: " + count);

                lastMove = playerMove(in);
                count += lastMove;
                if (count >= total) {
                    System.out.println("Total: " + count);
                    System.out.println("\nYou Won");
                    break;
                }
                System.out.println("Total: " + count);
            }
            break;

        default:
            System.out.println("No such level");
        }
        in.close();
    }
}