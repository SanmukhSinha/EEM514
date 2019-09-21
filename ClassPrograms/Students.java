import java.util.*;

public class Students {
    String name;
    float ct1;
    float ct2;

    public Students(String studentName, float ct1, float ct2) {
        this.name = studentName;
        this.ct1 = ct1;
        this.ct2 = ct2;
    }

    public float avgMarks(){
        return (this.ct1+this.ct2)/2;
    }

    public static void main(String[] args) {

        // BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String str;
        String data[] = new String[3];
        int n = 0, i, choice;
        Scanner in = new Scanner(System.in);

        n = Integer.parseInt(in.nextLine());
        Students[] s = new Students[n];

        for (i = 0; i < n; i++) {
            str = in.nextLine();
            data = str.split(" ");
            s[i] = new Students(data[0], Float.parseFloat(data[1]), Float.parseFloat(data[2]));
        }

        System.out.println(
                "1 for the student (name) with the highest average marks)\n2 for class average \n3 for top five students sorted by CT1 scores \n4 for top five students sorted by CT2 scores \n5 for top five students sorted by average CT scores\n6 to exit\nEnter choice 1-6");
        //choice = in.nextInt();
        choice =2;
        float a[];

        while (choice < 6) {
            switch (choice) {
            case 1:
                a = new float[n];
                int maxAt = 0;
                for (i = 0; i < n; i++) {
                    a[i] = s[i].avgMarks();
                }
                for (i = 0; i < n; i++) {
                    maxAt = a[i] > a[maxAt] ? i : maxAt;
                }
                System.out.println(s[maxAt].name +" has Highest Average marks");
                break;
            case 2:
                a= new float[n];
                float sum=0;
                for (i = 0; i < n; i++) {
                    sum+=s[i].ct1 + s[i].ct2;
                }
                System.out.println("Class average marks = "+sum);;
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;

            default:
                break;
            }
            break;//
        }
        in.close();
    }
}