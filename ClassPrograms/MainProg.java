import java.util.Scanner;

public class MainProg
{
 public static void main(String[] args)
 {
    Scanner in = new Scanner(System.in);
    Student s1 = new Student("Bhasudev Vagat"); //Bazy V.
    Student s2 = new Student("Baninder Makshi");
    float sub1=0, sub2=0; 
    //input grades for B. Vagat
    s1.inputGrades1(in);
    //print average for B. Vagat
    System.out.println("Average marks of "+s1.getName()+": "+s1.getAverage());
    System.out.println();
    //input grades for B. Makshi into sub1, sub2
    System.out.println("Enter CT1 and CT2 marks for " + s2.getName());
    sub1 = in.nextFloat();
    sub2 = in.nextFloat();
    //and then input the grades (using the second version of the input function)
    s2.inputGrades2(sub1, sub2);
    //print average for both
    System.out.println("Average marks of "+s2.getName()+": "+s2.getAverage());
    in.close();
 }
} 