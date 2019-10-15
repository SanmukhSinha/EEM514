import java.util.*;

public class SetEx {
	/*
	Duplicates not allowed, insertion order not preserved by default
	Optimized for finding particular items quickly
	
	I've put several calls in this code, you can uncomment and try out
	*/

    public static void main(String[] args) {

        // HashSet does not retain the order in which we input.
         Set<String> set1 = new HashSet<String>();

        // LinkedHashSet remembers the order that we added items in
        // Set<String> set1 = new LinkedHashSet<String>();

        // TreeSet sorts in natural order
        //Set<String> set1 = new TreeSet<String>();

        if (set1.isEmpty()) {
            System.out.println("Set is empty at start");
        }

        set1.add("Sandwich");
        set1.add("Ice Cream");
        set1.add("R2D2");
        set1.add("Mokepon");
		
        // Adding duplicate items does nothing.
        if (!set1.add("Mokepon")) System.out.println("Duplicate insert ignored.");

        System.out.println("Set1: "+set1);

		/*
        // Iteration 
        for (String element : set1) {
            System.out.println(element);
        }
		// Iteration - using an Iterator 
		System.out.println("----------------------------");
		Iterator <String> it = set1.iterator();
		while (it.hasNext()) System.out.println(it.next());
		System.out.println("----------------------------");
		*/
		
		///*
        // Does set contain a given item?
        if (set1.contains("Item")) {
            System.out.println("Contains Item");
        }

        if (!set1.contains("Item")) {
            System.out.println("Does not contain Item");
        }
		//*/
		
        /// set2 contains some common elements with set1, and some new ones

        Set<String> set2 = new TreeSet<String>();

        set2.add("Football");
        set2.add("R2D2");
        set2.add("Mokepon");
        set2.add("monkey");
        set2.add("Train");
        
		System.out.println("Set2: "+set2);

        // Union 
        
        Set<String> union = new TreeSet<String>(set1);
        
		// addAll - retains in "union" those elements that  occur in either set
        union.addAll(set2);
        
        System.out.println("Union: "+union);
		
        // Intersection 
        
        Set<String> intersection = new HashSet<String>(set1);
        
		// retainAll - retains in "intersection" those elements that also occur in set2
        intersection.retainAll(set2);
        
        System.out.println("Intersection: "+intersection);
        
        // Difference 
        
        Set<String> difference = new HashSet<String>(set2);
		
        // removeAll - removes from "difference" those elements that also occur in set2
        difference.removeAll(set1);
        System.out.println("difference: "+difference);
    }

}