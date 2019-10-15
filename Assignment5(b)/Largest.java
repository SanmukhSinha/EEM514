import java.util.ArrayList;

public class Largest {

    public static void main(String[] args) {

        /*ArrayList Size can only be an Integer */
        int size = Integer.MAX_VALUE;
        //ArrayList<Integer> list1 = new ArrayList<Integer>(size-2);//Max Possible size due to VM space
        //ArrayList<Integer> list2 = new ArrayList<Integer>(size/5);//Max Possible size due to Heap space
        //Max size due Heap space varies slightly everytime

        System.out.println("Largest ArrayList size supported due to: ");
        System.out.println("VM space = Integer.MAX_VALUE-2 = "+(size-2));
        System.out.println("Heap space = (approx) Integer.MAX_VALUE/5 = "+size/5);
    }
}