public class BankAccount {
    private double balance;
    private String name;
    private String number;

    public BankAccount(double balance, String name, String number) {
        this.balance = balance;
        this.name = name;
        this.number = number;
    }

    public void withdrawMoney(double money) {
        if (money < this.balance)
            this.balance -= money;
        else 
            System.out.println("Given amount more than balance");
    }

    public void depositMoney(double money) {
        this.balance += money;
    }

    public double getBalance(){
        return this.balance;
    }

    public void deductFees(){
        if (100.0 < this.balance)
            this.balance -= 100.0;
        else 
            System.out.println("Balance is less than 100");
    }

    @Override
    public String toString(){
        return "Name: "+this.name+"\nAccount Number: "+this.number+"\nBalance: "+this.balance;
    }

    public static void main(String[] args) {

        BankAccount ac = new BankAccount(12000.5 ,"Bazy V.","YOYOV0L1");

        ac.withdrawMoney(100.5);
        ac.depositMoney(500);
        System.out.println(ac.getBalance());
        
        ac.deductFees();
        ac.deductFees();
        System.out.println(ac);
    }
}