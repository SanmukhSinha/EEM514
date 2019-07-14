import java.util.Scanner;

public class Encrypt {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String result = "", str = "";
        int argsLen = args.length;

        if (argsLen != 0) {
            int i;
            for (i = 0; i < argsLen; i++) {
                str = str + args[i];
                str = str + " ";
            }
            str = str.substring(0, str.length() - 1);
        } else {
            System.out.println("Enter a String:");
            str = in.nextLine();
        }

        int i, len = str.length();

        for (i = 0; i < len / 2; i++) {
            result = result + str.charAt(i);
            result = result + str.charAt(len - 1 - i);
        }
        if (len % 2 != 0) {
            result = result + str.charAt(i);
        }

        System.out.println("Encrypted String:\n" + result);
        in.close();
    }
}