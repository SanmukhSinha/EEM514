
//Assignment 5(b)
import java.util.*;
import java.io.*;

public class LinkArrayList {

    public static void main(String[] args) throws IOException {

        int size = 500000, nIter = 1000, last;
        // Scanner in = new Scanner(System.in);
        // System.out.println("Enter Size: ");
        // size = in.nextInt();

        ArrayList<Integer> aList = new ArrayList<Integer>();
        LinkedList<Integer> lList = new LinkedList<>();
        long[] aListTime = { 0, 0, 0, 0, 0, 0 };// insertAtFront insertAtRandom insertAtEnd
        long[] lListTime = { 0, 0, 0, 0, 0, 0 };// insertAtFront insertAtRandom insertAtEnd
        long sTime = 0, eTime = 0;

        for (int i = 0; i < size; i++) {// adding numbers to both lists
            aList.add(i);
            lList.add(i);
        }

        for (int i = 0; i < nIter; i++) {
            /* Inserting at Front */
            // ArrayList
            sTime = System.nanoTime();
            aList.add(0, 12345);
            eTime = System.nanoTime();
            aListTime[0] += eTime - sTime;

            // LinkedList
            sTime = System.nanoTime();
            lList.addFirst(12345);
            eTime = System.nanoTime();
            lListTime[0] += eTime - sTime;

            /* Inserting at Middle */
            // ArrayList
            sTime = System.nanoTime();
            aList.add(250000, 12345);
            eTime = System.nanoTime();
            aListTime[1] += eTime - sTime;

            // Linked List
            sTime = System.nanoTime();
            lList.add(250000, 12345);
            eTime = System.nanoTime();
            lListTime[1] += eTime - sTime;

            /* Inserting at End */
            // ArrayList
            sTime = System.nanoTime();
            aList.add(12345);
            eTime = System.nanoTime();
            aListTime[2] += eTime - sTime;

            // Linked List
            sTime = System.nanoTime();
            lList.add(12345);
            eTime = System.nanoTime();
            lListTime[2] += eTime - sTime;

            /* Deleting from Front */
            // ArrayList
            sTime = System.nanoTime();
            aList.remove(0);
            eTime = System.nanoTime();
            aListTime[3] += eTime - sTime;

            // LinkedList
            sTime = System.nanoTime();
            lList.remove();
            eTime = System.nanoTime();
            lListTime[3] += eTime - sTime;

            /* Deleting from Middle */
            // ArrayList
            sTime = System.nanoTime();
            aList.remove(250000);
            eTime = System.nanoTime();
            aListTime[4] += eTime - sTime;

            // Linked List
            sTime = System.nanoTime();
            lList.remove(250000);
            eTime = System.nanoTime();
            lListTime[4] += eTime - sTime;

            /* Deleting from End */
            // ArrayList
            last = aList.size() - 1;
            sTime = System.nanoTime();
            aList.remove(last);
            eTime = System.nanoTime();
            aListTime[5] += eTime - sTime;

            // Linked List
            last = lList.size() - 1;
            sTime = System.nanoTime();
            lList.remove(last);
            eTime = System.nanoTime();
            lListTime[5] += eTime - sTime;
        }

        for (int i = 0; i < 6; i++) {
            aListTime[i] /= nIter;
            lListTime[i] /= nIter;
        }

        System.out.println("Insertion Time:\nArray Linked");
        for (int i = 0; i < 3; i++) {
            System.out.println(aListTime[i] + " " + lListTime[i]);
        }

        System.out.println("Deletion Time:\nArray Linked");
        for (int i = 3; i < 6; i++) {
            System.out.println(aListTime[i] + " " + lListTime[i]);
        }

        FileWriter fileWrite;
        BufferedWriter writer;
        fileWrite = new FileWriter("arrayInsert.dat");
        writer = new BufferedWriter(fileWrite);
        writer.write(aListTime[0] + " Front");
        writer.newLine();
        writer.write(aListTime[1] + " Middle");
        writer.newLine();
        writer.write(aListTime[2] + " End");
        writer.close();

        fileWrite = new FileWriter("linkedInsert.dat");
        writer = new BufferedWriter(fileWrite);
        writer.write(lListTime[0] + " Front");
        writer.newLine();
        writer.write(lListTime[1] + " Middle");
        writer.newLine();
        writer.write(lListTime[2] + " End");
        writer.close();

        fileWrite = new FileWriter("arrayDelete.dat");
        writer = new BufferedWriter(fileWrite);
        writer.write(aListTime[3] + " Front");
        writer.newLine();
        writer.write(aListTime[4] + " Middle");
        writer.newLine();
        writer.write(aListTime[5] + " End");
        writer.close();

        fileWrite = new FileWriter("linkedDelete.dat");
        writer = new BufferedWriter(fileWrite);
        writer.write(lListTime[3] + " Front");
        writer.newLine();
        writer.write(lListTime[4] + " Middle");
        writer.newLine();
        writer.write(lListTime[5] + " End");
        writer.close();
    }
}