//Assignment 5(a)
import java.util.*;

public class PhoneBook {

    private String name;
    private String email;
    private String phone;

    public PhoneBook() {
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

    public boolean equals(PhoneBook p){
        if(name.equals(p.name)){
            if(email.equals(p.email)){
                if(phone.equals(p.phone)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {

        List<PhoneBook> phoneList = new ArrayList<PhoneBook>();
        int choice = 2;
        Scanner in = new Scanner(System.in);

        System.out.println(
                "1.Enter details\n2.Get details by name\n3.Get all duplicates\n4.Get details by email domain\n5.Exit");

        while (choice >= 1 && choice <= 4) {
            String name, email, phone;
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

                phoneList.add(new PhoneBook(name, email, phone));
                break;

            case 2:
                System.out.print("Enter name: ");
                name = in.nextLine();

                for (int i = 0; i < phoneList.size(); i++) {
                    temp = phoneList.get(i);
                    if (temp.getName().equalsIgnoreCase(name)) {
                        if (!flag) {
                            System.out.println("\nRecords:");
                        }
                        flag = true;
                        System.out.println("Email: " + temp.getEmail());
                        System.out.println("Phone Number: " + temp.getPhone() + "\n");
                    }
                }

                if (!flag) {
                    System.out.println("No record found!");
                }
                break;

            case 3:
                System.out.println("Duplicates:");
                ArrayList<Integer> list = new ArrayList<Integer>();

                for (int i = 0; i < phoneList.size(); i++) {
                    if (!list.contains(i)) {
                        ArrayList<Integer> dups = new ArrayList<Integer>();
                        int count = 0;
                        dups.add(i);
                        for (int j = i + 1; j < phoneList.size(); j++) {
                            if (phoneList.get(i).equals(phoneList.get(j))) {
                                count++;
                                list.add(j);
                                dups.add(j);
                            }
                        }
                        if (count > 0) {
                            System.out.println("Name: " + phoneList.get(i).getName());
                            System.out.println("Email: " + phoneList.get(i).getEmail());
                            System.out.println("Phone Number: " + phoneList.get(i).getPhone());
                            System.out.println("Number of duplicates = " + count);
                            System.out.print("Position of duplicates: ");
                            for(int k=0;k<dups.size();k++){
                                System.out.print(dups.get(k)+", ");
                            }
                            System.out.println("\n");
                        }
                    }
                }

                break;

            case 4:
                System.out.print("Enter email domain: ");
                email = in.nextLine();

                for (int i = 0; i < phoneList.size(); i++) {
                    temp = phoneList.get(i);
                    if (temp.getEmail().endsWith(email)) {
                        if (!flag) {
                            System.out.println("\nRecords:");
                        }
                        flag = true;
                        System.out.println("Name: " + temp.getName());
                        System.out.println("Phone Number: " + temp.getPhone());
                        System.out.println("Email: " + temp.getEmail() + "\n");
                    }
                }

                if (!flag) {
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