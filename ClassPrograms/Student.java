import java.util.Scanner;

public class Student
{
 //instance data
 String name;
 float ct1;
 float ct2;

 //constructor 
 public Student(String studentName)
 {
    this.name = studentName;
    this.ct1 = 0;
    this.ct2 = 0;
 }

 //inputGrades (version 1): prompt for and read in student's grades for ct1 and ct2.
 public void inputGrades1(Scanner in)
 {
 //add body of inputGrades
 System.out.println("Enter CT marks for "+ this.name);

 System.out.print("CT1: ");
 this.ct1 = in.nextFloat();

 System.out.print("CT2: ");
 this.ct2 = in.nextFloat();
 }
 
 //inputGrades (version 2): ct1 and ct2 as params. 
 public void inputGrades2(float ct1, float ct2)// modify as needed 
 {
 //add body of inputGrades
 this.ct1 = ct1;
 this.ct2 = ct2;
 }

 //getAverage: compute and return the student's test average
 //add header for getAverage
 public float getAverage()
 {
 //add body of getAverage
    return (ct1+ct2)/2;

 }

 //getName: print the student's name
 //add header for printName
 public String getName()
 {
 //add body of printName
    return this.name;
 }
}