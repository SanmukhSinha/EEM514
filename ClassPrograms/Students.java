import java.util.*;

class CompareAvg implements Comparator<Students>{

    public int compare(Students a, Students b){
        return (int)(a.getAvg() - b.getAvg());
    }
}

class CompareCT1 implements Comparator<Students>{

    public int compare(Students a,Students b){
        return (int)(a.getCT1() - b.getCT1());
    }
}

class CompareCT2 implements Comparator<Students>{

    public int compare(Students a,Students b){
        return (int)(a.getCT2() - b.getCT2());
    }
}

class Students{
    String name;
    float ct1;
    float ct2;

    public Students(String studentName, float ct1, float ct2) {
        this.name = studentName;
        this.ct1 = ct1;
        this.ct2 = ct2;
    }

    public float getAvg(){
        return (this.ct1+this.ct2)/2;
    }
    
    public String getName(){
        return this.name;
    }

    public float getCT1(){
        return this.ct1;
    }

    public float getCT2(){
        return this.ct2;
    }

    public static void main(String[] args) {

        // BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String str;
        String data[] = new String[3];
        int n = 0, i, choice;//n=numberOfStudents
        Scanner in = new Scanner(System.in);

        n = Integer.parseInt(in.nextLine());
        Students[] s = new Students[n];

        for (i = 0; i < n; i++) {//data input
            str = in.nextLine();
            data = str.split(" ");
            s[i] = new Students(data[0], Float.parseFloat(data[1]), Float.parseFloat(data[2]));
        }

        System.out.println(
                "1 for the student (name) with the highest average marks)\n2 for class average \n3 for top five students sorted by CT1 scores \n4 for top five students sorted by CT2 scores \n5 for top five students sorted by average CT scores\n6 to exit\nEnter choice 1-6");
        //choice = in.nextInt();
        choice = 5;

        while (choice < 6) {
            switch (choice) {
            case 1://highest ang marks
                Arrays.sort(s,new CompareAvg());
                System.out.println("Highest Average marks: " + s[n-1].name);
                break;

            case 2://class average
                float sum=0;
                for (i = 0; i < n; i++) {
                    sum+=s[i].ct1 + s[i].ct2;
                }
                System.out.println("Class average marks = "+sum);;
                break;

            case 3://top 5 ct1 marks
                Arrays.sort(s,new CompareCT1());
                System.out.println("Top 5 Students by CT1 Marks: ");
                for(i=0;i<5 && i<n;i++){
                    System.out.println(s[n-i-1].getName());
                }
                break;

            case 4://top 5 ct2 marks
                Arrays.sort(s,new CompareCT2());
                System.out.println("Top 5 Students by CT2 Marks: ");
                for(i=0;i<5 && i<n;i++){
                    System.out.println(s[n-i-1].getName());
                }
                break;

            case 5:
                Arrays.sort(s,new CompareAvg());
                System.out.println("Top 5 Students by Average CT Marks: ");
                for(i=0;i<5 && i<n;i++){
                    System.out.println(s[n-i-1].getName());
                }
                break;

            default:
                break;
            }
            break;//
        }
        in.close();
    }
}