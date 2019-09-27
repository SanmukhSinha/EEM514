import java.util.* ;
/*
I've put in enough stuff here for you to understand how to work with Collection classes. ArrayLists, LinkedLists etc.
Explore the docs for more.
*/
public class ListEx
{

  public static void main ( String[] args)
  {
    // Create an ArrayList that holds references to String
    //ArrayList<String> names = new ArrayList<String>();
    List <String> names = new ArrayList<String>();

	// Similarly, you can create a LinkedList that holds references to String like this:
	// LinkedList<Integer> llist = new LinkedList<Integer>();

    // Capacity starts at 10, but size starts at 0
    System.out.println("Initial size: " + names.size() );

    // Add three String references
    names.add("1");
    names.add("2");
    names.add("3");
    System.out.println("Size after additions: " + names.size() );
       
    // Access and print out the Objects
    for ( int j=0; j<names.size(); j++ )
      System.out.println("element " + j + ": " + names.get(j) );
	  
	//*
	// Some more operations
    names.set(1, "4");
	names.add(1, "5");
	names.add(names.indexOf("2")+1,"6"); 
	names.add("1");	
    // Access and print out the Objects
	int i=0;
    System.out.println("----------------------------------");
    for ( String j : names )
      System.out.println("element " + i++ + ": " + j );
    System.out.println("----------------------------------");

	// Lets kick something out
	int lastIndexOf1 = names.lastIndexOf("1");
	System.out.println("Removed: "+names.remove(lastIndexOf1)+" from location "+ lastIndexOf1);
	
	// Removing items one at a time
	while ( names.size()!=0 ) {
	   System.out.println("Randomly removing "+names.remove((new Random()).nextInt(names.size())));
	}
	//*/

  }
}