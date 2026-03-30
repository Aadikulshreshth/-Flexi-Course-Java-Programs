import java.util.ArrayList;
import java.util.Scanner;

class BankingException extends Exception {
    public BankingException(String message) {
        super(message);
    }
}

class BankAccount {

    private final String accountNo;
    private final String holderName;
    protected double balance;     

    public BankAccount(String accountNo, String holderName, double openingBalance) throws BankingException {

        if (accountNo == null || accountNo.isBlank()) {
            throw new BankingException("Account number cannot be empty.");
        }
        if (holderName == null || holderName.isBlank()) {
            throw new BankingException("Holder name cannot be empty.");
        }
        if (openingBalance < 0) {
            throw new BankingException("Opening balance cannot be negative.");
        }

        this.accountNo = accountNo;
        this.holderName = holderName;
        this.balance = openingBalance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Deposit amount must be positive.");
        }
        balance += amount;
    }

    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdraw amount must be positive.");
        }
        if (amount > balance) {
            throw new BankingException("Insufficient balance.");
        }
        balance -= amount;
    }

    public String getAccountType() {
        return "GENERIC";
    }

    public String summary() {
        return String.format("[%s] AccNo=%s, Holder=%s, Balance=%.2f",
                getAccountType(), accountNo, holderName, balance);
    }
}

class SavingsAccount extends BankAccount {

    private final double minBalance;
    private final double interestRate; // yearly %

    public SavingsAccount(String accountNo, String holderName,
                          double openingBalance, double minBalance, double interestRate) throws BankingException {
        super(accountNo, holderName, openingBalance);

        if (minBalance < 0) {
            throw new BankingException("Minimum balance cannot be negative.");
        }
        if (interestRate < 0) {
            throw new BankingException("Interest rate cannot be negative.");
        }

        this.minBalance = minBalance;
        this.interestRate = interestRate;
    }

    
    public String getAccountType() {
        return "SAVINGS";
    }
    
    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdraw amount must be positive.");
        }

        double remaining = balance - amount;
        if (remaining < minBalance) {
            throw new BankingException("Withdrawal denied: minimum balance must be maintained: " + minBalance);
        }

        balance = remaining;
    }

    
    public void applyAnnualInterest() {
        balance += (balance * interestRate / 100.0);
    }
}

class CurrentAccount extends BankAccount {

    private final double overdraftLimit;

    public CurrentAccount(String accountNo, String holderName,
                          double openingBalance, double overdraftLimit) throws BankingException {
        super(accountNo, holderName, openingBalance);

        if (overdraftLimit < 0) {
            throw new BankingException("Overdraft limit cannot be negative.");
        }

        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public String getAccountType() {
        return "CURRENT";
    }
    
    public void withdraw(double amount) throws BankingException {
        if (amount <= 0) {
            throw new BankingException("Withdraw amount must be positive.");
        }

        double remaining = balance - amount;
        if (remaining < -overdraftLimit) {
            throw new BankingException("Withdrawal denied: overdraft limit exceeded: " + overdraftLimit);
        }

        balance = remaining;
    }
}

public class BankingDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<BankAccount> accounts = new ArrayList<>();
        try {
            System.out.print("Enter number of accounts: ");
            int n = sc.nextInt();
            sc.nextLine(); 

            for (int i = 0; i < n; i++) {
                System.out.println("\nChoose Account Type (1-Savings, 2-Current, 3-Basic): ");
                int choice = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter Account No: ");
                String accNo = sc.nextLine();

                System.out.print("Enter Holder Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Opening Balance: ");
                double balance = sc.nextDouble();

                BankAccount acc = null;

                switch (choice) {
                    case 1:
                        System.out.print("Enter Minimum Balance: ");
                        double minBal = sc.nextDouble();

                        System.out.print("Enter Interest Rate: ");
                        double rate = sc.nextDouble();

                        acc = new SavingsAccount(accNo, name, balance, minBal, rate);
                        break;

                    case 2:
                        System.out.print("Enter Overdraft Limit: ");
                        double limit = sc.nextDouble();

                        acc = new CurrentAccount(accNo, name, balance, limit);
                        break;

                    case 3:
                        acc = new BankAccount(accNo, name, balance);
                        break;

                    default:
                        System.out.println("Invalid choice!");
                        i--; 
                        continue;
                }

                accounts.add(acc);
            }

            System.out.println("\n---- Accounts Created ----");
            printAll(accounts);
            sc.nextLine();

            System.out.print("Enter account number to operate: ");
            String searchAcc = sc.nextLine();

            BankAccount found = null;

            for (BankAccount acc : accounts) {
                if (acc.getAccountNo().equals(searchAcc)) {
                    found = acc;
                    break;
                }
            }

            if (found == null) {
                System.out.println("Account not found!");
            } else {
                System.out.print("Enter deposit amount: ");
                double dep = sc.nextDouble();
                found.deposit(dep);

                System.out.print("Enter withdraw amount: ");
                double wd = sc.nextDouble();
                try {
                    found.withdraw(wd);
                } catch (BankingException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("\n---- Final State ----");
            printAll(accounts);

        } catch (BankingException ex) {
            System.out.println("Banking error: " + ex.getMessage());
        }
        sc.close();
    }
    private static void printAll(ArrayList<BankAccount> accounts) {
        for (BankAccount acc : accounts) {
            System.out.println(acc.summary());
        }
    }
}
