import java.util.Scanner;
import java.util.Comparator;
import java.util.Arrays;

class Alpha {
    char ascii;
    int count;

    public Alpha(char ascii, int count) {
        this.ascii = ascii;
        this.count = count;
    }
}

class SortbyCount implements Comparator<Alpha> {

    public int compare(Alpha a, Alpha b) {
        if (b.count != a.count)
            return b.count - a.count;
        else
            return (int) a.ascii - (int) b.ascii;
    }
}

public class Lettercount{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int i, j, alphaCount[] = new int[256], argsLen = args.length;
        Alpha arr[] = new Alpha[256];

        for (i = 0; i < 256; i++)
            arr[i] = new Alpha((char) i, 0);

        if (argsLen != 0) {
            for (i = 0; i < argsLen; i++) {
                String str = args[i];
                str = str.toUpperCase();
                str = str.replaceAll("[^A-Z]", "");
                int strLen = str.length();

                for (j = 0; j < strLen; j++)
                    alphaCount[(int) str.charAt(j)]++;
            }
        } else {
            System.out.println("Enter a String:");
            String str = in.nextLine();
            str = str.toUpperCase();
            str = str.replaceAll("[^A-Z]", "");
            int strLen = str.length();

            for (j = 0; j < strLen; j++)
                alphaCount[(int) str.charAt(j)]++;
        }

        for (i = 0; i < 256; i++)
            arr[i].count = alphaCount[i];
        Arrays.sort(arr, new SortbyCount());

        for (i = 0; i < 256; i++)
            if (arr[i].count != 0)
                System.out.println(arr[i].ascii + " " + arr[i].count);

        in.close();
    }
}