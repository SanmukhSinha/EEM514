import java.util.*;
import java.io.*;

public class DistinctWords {

    public static void main(String[] args) throws IOException {

        String file1 = args[0];
        String file2 = args[1];
        String line;
        long commonCount = 0, sTime = 0, eTime = 0, time[] = new long[4];
        ArrayList<String> a1 = new ArrayList<String>();
        ArrayList<String> a2 = new ArrayList<String>();
        LinkedList<String> l1 = new LinkedList<String>();
        LinkedList<String> l2 = new LinkedList<String>();
        HashSet<String> h1 = new HashSet<String>();
        HashSet<String> h2 = new HashSet<String>();
        TreeSet<String> t1 = new TreeSet<String>();
        TreeSet<String> t2 = new TreeSet<String>();
        FileReader fileRead;
        BufferedReader reader;

        sTime = System.nanoTime();
        {/* ArrayList */
            fileRead = new FileReader(file1);
            reader = new BufferedReader(fileRead);
            while ((line = reader.readLine()) != null) {
                String[] words = (line.trim()).split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (int j = 0; j < words[i].length(); j++) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(1);
                        }
                    }
                    for (int j = words[i].length() - 1; j > 0; j--) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(0, words[i].length() - 1);
                        }
                    }
                    if (!a1.contains(words[i])) {
                        a1.add(words[i]);
                    }
                }
            }
            reader.close();

            fileRead = new FileReader(file2);
            reader = new BufferedReader(fileRead);
            while ((line = reader.readLine()) != null) {
                String[] words = (line.trim()).split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (int j = 0; j < words[i].length(); j++) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(1);
                        }
                    }
                    for (int j = words[i].length() - 1; j > 0; j--) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(0, words[i].length() - 1);
                        }
                    }
                    if (!a2.contains(words[i])) {
                        a2.add(words[i]);
                    }
                }
            }
            reader.close();
        }
        eTime = System.nanoTime();
        time[0] = eTime - sTime;

        sTime = System.nanoTime();
        {/* LinkedList */
            fileRead = new FileReader(file1);
            reader = new BufferedReader(fileRead);
            while ((line = reader.readLine()) != null) {
                String[] words = (line.trim()).split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (int j = 0; j < words[i].length(); j++) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(1);
                        }
                    }
                    for (int j = words[i].length() - 1; j > 0; j--) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(0, words[i].length() - 1);
                        }
                    }
                    if (!l1.contains(words[i])) {
                        l1.add(words[i]);
                    }
                }
            }
            reader.close();

            fileRead = new FileReader(file2);
            reader = new BufferedReader(fileRead);
            while ((line = reader.readLine()) != null) {
                String[] words = (line.trim()).split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (int j = 0; j < words[i].length(); j++) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(1);
                        }
                    }
                    for (int j = words[i].length() - 1; j > 0; j--) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(0, words[i].length() - 1);
                        }
                    }
                    if (!l2.contains(words[i])) {
                        l2.add(words[i]);
                    }
                }
            }
            reader.close();
        }
        eTime = System.nanoTime();
        time[1] = eTime - sTime;

        sTime = System.nanoTime();
        {/* HashSet */
            fileRead = new FileReader(file1);
            reader = new BufferedReader(fileRead);
            while ((line = reader.readLine()) != null) {
                String[] words = (line.trim()).split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (int j = 0; j < words[i].length(); j++) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(1);
                        }
                    }
                    for (int j = words[i].length() - 1; j > 0; j--) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(0, words[i].length() - 1);
                        }
                    }

                    h1.add(words[i]);
                }
            }
            reader.close();

            fileRead = new FileReader(file2);
            reader = new BufferedReader(fileRead);
            while ((line = reader.readLine()) != null) {
                String[] words = (line.trim()).split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (int j = 0; j < words[i].length(); j++) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(1);
                        }
                    }
                    for (int j = words[i].length() - 1; j > 0; j--) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(0, words[i].length() - 1);
                        }
                    }

                    h2.add(words[i]);
                }
            }
            reader.close();
        }
        eTime = System.nanoTime();
        time[2] = eTime - sTime;

        sTime = System.nanoTime();
        {/* TreeSet */
            fileRead = new FileReader(file1);
            reader = new BufferedReader(fileRead);
            while ((line = reader.readLine()) != null) {
                String[] words = (line.trim()).split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (int j = 0; j < words[i].length(); j++) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(1);
                        }
                    }
                    for (int j = words[i].length() - 1; j > 0; j--) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(0, words[i].length() - 1);
                        }
                    }

                    t1.add(words[i]);
                }
            }
            reader.close();

            fileRead = new FileReader(file2);
            reader = new BufferedReader(fileRead);
            while ((line = reader.readLine()) != null) {
                String[] words = (line.trim()).split(" ");
                for (int i = 0; i < words.length; i++) {
                    for (int j = 0; j < words[i].length(); j++) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(1);
                        }
                    }
                    for (int j = words[i].length() - 1; j > 0; j--) {
                        if (Character.isLetterOrDigit(words[i].charAt(j))) {
                            break;
                        } else {
                            words[i] = words[i].substring(0, words[i].length() - 1);
                        }
                    }

                    t2.add(words[i]);
                }
            }
            reader.close();
        }
        eTime = System.nanoTime();
        time[3] = eTime - sTime;

        System.out.println("Number of distinct words in " + file1 + " = " + a1.size());
        System.out.println("Number of distinct words in " + file2 + " = " + a2.size());
        for (int i = 0; i < a1.size(); i++) {
            if (a2.contains(a1.get(i))) {
                commonCount++;
            }
        }
        System.out.println("Number of common words = " + commonCount);
        // System.out.println(l1.size());
        // System.out.println(l2.size());
        // System.out.println(h1.size());
        // System.out.println(h2.size());
        // System.out.println(t1.size());
        // System.out.println(t2.size());

        System.out.println("\nExecution time(ms):");
        System.out.println("ArrayList = " + time[0]/1000000);
        System.out.println("LinkedList = " + time[1]/1000000);
        System.out.println("HashSet = " + time[2]/1000000);
        System.out.println("TreeSet = " + time[3]/1000000);

        /*
        LinkedList take the longest time as elements are not stored sequentially, with causes longer traversal times.
        ArrayList takes less time than LinkedList are elements are(almost) stores sequentially.
        Sets take lesser time than Lists are they are optimized for storing unique data.
        HashSet are faster than TreeSet as they are not sorted, and its functions have O(1) complexity.
        TreeSet stored data in sorted form and its functions have O(log n) complexity.
        */
    }
}