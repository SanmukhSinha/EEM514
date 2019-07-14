import java.util.Scanner;
import java.util.Random;

public class Nim {

    public static int playerMove(Scanner in, int max) {
        System.out.print("\nYour Move: ");
        int player = in.nextInt();

        while (player < 1 || player > max) {
            System.out.println("Enter correct number");
            System.out.print("\nYour Move: ");
            player = in.nextInt();
        }

        return player;
    }

    public static int compMove(int max) {
        Random rand = new Random(System.nanoTime());

        int comp = rand.nextInt(max) + 1;
        System.out.println("\nComputer Move: " + comp);

        return comp;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random rand = new Random(System.currentTimeMillis());
        boolean again;
        int points, maxSticks, min = 40, max = 80;
        ;

        System.out.println("Select Level\n1 : Basic Nim \n2 : Tough Nim \n3 : Wicked Nim");
        int lvl = in.nextInt();

        switch (lvl) {
        case 1:
            System.out.println("Choose from 1, 2 or 3");
            again = true;
            points = 0;

            while (again) {
                int count = 0;
                int total = rand.nextInt(max - min + 1) + min;

                System.out.println("Enter 1 to move first, any other number to move second");
                int first = in.nextInt();
                System.out.println("Number of sticks: " + total);

                if (first == 1) {
                    count += playerMove(in, 3);
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nYou Won");
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));
                }

                while (true) {
                    count += compMove(3);
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nComputer Won");
                        points -= 5;
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));

                    count += playerMove(in, 3);
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nYou Won");
                        points += 5;
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));
                }

                System.out.println("Points = " + points);
                System.out.println("Enter 1 to play again, any other number to exit");

                int temp = in.nextInt();
                if (temp != 1) {
                    again = false;
                }
            }
            break;

        case 2:
            maxSticks = rand.nextInt(5) + 3;
            again = true;
            points = 0;

            while (again) {
                int count = 0;
                int total = rand.nextInt(max - min + 1) + min;

                System.out.println("Enter 1 to move first, any other number to move second");
                int first = in.nextInt();
                System.out.println("Number of sticks : " + total + "\n");

                if (first == 1) {
                    System.out.print("Enter a number between 1-" + maxSticks);
                    count += playerMove(in, maxSticks);
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nYou Won");
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));
                }

                while (true) {
                    count += compMove(maxSticks);
                    maxSticks = (maxSticks - 3 + 1) % 5 + 3;
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nComputer Won");
                        points -= 5;
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));

                    System.out.print("\nEnter a number between 1-" + maxSticks);
                    count += playerMove(in, maxSticks);
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nYou Won");
                        points += 5;
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));
                }

                System.out.println("Points = " + points);
                System.out.println("Enter 1 to play again, any other number to exit");

                int temp = in.nextInt();
                if (temp != 1) {
                    again = false;
                }
            }
            break;

        case 3:
            System.out.println("Choose from 1, 2 or 3");
            again = true;
            points = 0;

            while (again) {
                int count = 0, lastmove;
                int total = rand.nextInt(max - min + 1) + min;

                System.out.println("Number of sticks: " + total + "\n");

                if (total % 4 == 0) {
                    lastmove = playerMove(in, 3);
                    count += lastmove;
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nYou Won");
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));
                } else {
                    lastmove = 4 - total % 4;
                }

                while (true) {
                    count += 4 - lastmove;
                    System.out.println("\nComputer Move: " + (4 - lastmove));
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nComputer Won");
                        points -= 5;
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));

                    lastmove = playerMove(in, 3);
                    count += lastmove;
                    if (count >= total) {
                        System.out.println("Sticks left: " + (total - count));
                        System.out.println("\nYou Won");
                        points += 5;
                        break;
                    }
                    System.out.println("Sticks left: " + (total - count));
                }

                System.out.println("Points = " + points);
                System.out.println("Enter 1 to play again, any other number to exit");

                int temp = in.nextInt();
                if (temp != 1) {
                    again = false;
                }
            }
            break;
        }
        in.close();
    }
}