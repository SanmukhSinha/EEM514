import java.util.Scanner;

public class Integers {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int num, max = 0, min = 0, count = 0, argsLen = args.length;
        float sum = 0;
        boolean flag = false;

        if (argsLen == 0) {
            System.out.println("Enter numbers (enter any other character to exit)");

            if (in.hasNextInt()) {
                num = in.nextInt();
                max = min = num;
                sum = (float) num;
                count++;
                flag = true;
            } else
                flag = false;

            while (flag) {
                if (in.hasNextInt()) {
                    num = in.nextInt();
                    if (num > max) {
                        max = num;
                    } else if (num < min)
                        min = num;

                    sum += (float) num;
                    count++;
                } else
                    break;
            }
        } else {
            int i;
            count = argsLen;
            num = Integer.parseInt(args[0]);
            max = min = num;
            sum += (float) num;
            for (i = 1; i < argsLen; i++) {
                num = Integer.parseInt(args[i]);
                sum += (float) num;

                if (num > max)
                    max = num;
                else if (num < min)
                    min = num;
            }
        }

        System.out.println("\nLargest Number = " + max);
        System.out.println("Smallest Number = " + min);
        System.out.println("Average = " + (sum / count));

        in.close();
    }
}