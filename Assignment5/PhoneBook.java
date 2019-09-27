import java.util.*;

public class PhoneBook {
    public String name;
    public String email;
    public String phone;

    public PhoneBook(){
        this.name = "";
        this.email = "";
        this.phone = "";
    }

    public PhoneBook(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public static void main(String[] args) {

        List<PhoneBook> phoneList = new ArrayList<PhoneBook>();
        int choice = 2;
        Scanner in = new Scanner(System.in);

        System.out.println("1.Enter details\n2.Get details by name\n3.Get all duplicates\n4.Get details by email domain\n5.Exit");

        while (choice >= 1 && choice <= 4) {
            String name,email,phone;
            PhoneBook temp = new PhoneBook();
            Boolean flag = false;

            System.out.print("Enter choice: ");
            choice = in.nextInt();
            in.nextLine();
            System.out.println();

            switch (choice) {
            case 1:
                System.out.print("Enter name: ");
                name = in.nextLine();
                System.out.print("Enter email: ");
                email = in.nextLine();
                System.out.print("Enter phone: ");
                phone = in.nextLine();

                phoneList.add(new PhoneBook(name,email,phone));
                break;

            case 2:
                System.out.print("Enter name: ");
                name = in.nextLine();

                for(int i=0;i<phoneList.size();i++){
                    temp = phoneList.get(i);
                    if(temp.getName().equalsIgnoreCase(name)){
                        if(!flag){
                            System.out.println("\nRecords:");
                        }
                        flag = true;
                        System.out.println("Email: "+temp.getEmail());
                        System.out.println("Phone Number: "+ temp.getPhone()+"\n");
                    }
                }

                if(!flag){
                    System.out.println("No record found!");
                }
                break;

            case 3:

                break;

            case 4:
                System.out.print("Enter email domain: ");
                email = in.nextLine();

                for(int i=0;i<phoneList.size();i++){
                    temp = phoneList.get(i);
                    if(temp.getEmail().endsWith(email)){
                        if(!flag){
                            System.out.println("\nRecords:");
                        }
                        flag = true;
                        System.out.println("Name: "+temp.getName());
                        System.out.println("Phone Number: "+ temp.getPhone()+"\n");
                    }
                }

                if(!flag){
                    System.out.println("No record found!");
                }
                break;

            default:
                break;
            }
            System.out.println();
        }

        in.close();
    }
}