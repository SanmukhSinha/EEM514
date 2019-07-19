import java.util.Scanner;

public class Palindrome {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = "";
        boolean flag = true;
        int argsLen = args.length;

        if (argsLen != 0) {
            int i;
            for (i = 0; i < argsLen; i++)
                str = str.concat(args[i]);
        } else {
            System.out.println("Enter a String :");
            str = in.nextLine();
            str = str.replaceAll("\\s+", "");
        }
        int i, len = str.length();

        for (i = 0; i < len / 2; i++) {
            if (str.charAt(i) != str.charAt(len - 1 - i)) {
                flag = false;
                break;
            }
        }

        if (flag) {
            System.out.println("A Palindrome");
        } else {
            System.out.println("Not a Palindrome");
        }
        in.close();
    }
}