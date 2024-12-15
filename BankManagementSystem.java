package encapsulationPackage;

import java.util.*;

abstract class Account {
    private String accountNumber;
    private String accountHolderName;
    protected double balance;

    public Account(String accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public abstract void withdraw(double amount);

    public void displayDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }
}

class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500;

    public SavingsAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= MIN_BALANCE) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient funds. Maintain a minimum balance of " + MIN_BALANCE);
        }
    }
}

class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000;

    public CurrentAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (balance - amount >= -OVERDRAFT_LIMIT) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Overdraft limit exceeded.");
        }
    }
}

public class BankManagementSystem {
    private static List<Account> accounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Banking System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Display Account Details");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    performDeposit();
                    break;
                case 3:
                    performWithdrawal();
                    break;
                case 4:
                    displayAccountDetails();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter Account Holder Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        System.out.println("Account Type: 1. Savings 2. Current");
        System.out.print("Choose Account Type: ");
        int type = scanner.nextInt();
        String accountNumber = "ACC" + (accounts.size() + 1);
        if (type == 1) {
            accounts.add(new SavingsAccount(accountNumber, name, balance));
        } else if (type == 2) {
            accounts.add(new CurrentAccount(accountNumber, name, balance));
        } else {
            System.out.println("Invalid account type.");
        }
        System.out.println("Account created successfully. Account Number: " + accountNumber);
    }

    private static void performDeposit() {
        System.out.print("Enter Account Number: ");
        scanner.nextLine();
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter Amount to Deposit: ");
            double amount = scanner.nextDouble();
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void performWithdrawal() {
        System.out.print("Enter Account Number: ");
        scanner.nextLine();
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter Amount to Withdraw: ");
            double amount = scanner.nextDouble();
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void displayAccountDetails() {
        System.out.print("Enter Account Number: ");
        scanner.nextLine();
        String accountNumber = scanner.nextLine();
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.displayDetails();
        } else {
            System.out.println("Account not found.");
        }
    }

    private static Account findAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

