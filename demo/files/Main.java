import java.util.ArrayList;
import java.util.List;

class Account {
    // Data class holding only data, no behavior
    String accountHolderName;
    double balance;
    String accountNumber;
    
    public Account(String name, double balance, String accountNumber) {
        this.accountHolderName = name;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }
}

class AccountHolderDetails {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    // Constructor
    public AccountHolderDetails(String name, String email, String phoneNumber, String address) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


// God Class: Too many responsibilities packed into this single class
class Bank {
    private final List<Account> accounts = new ArrayList<>();
    private final List<String> transactionLog = new ArrayList<>();
    
    // Long Method: Handles multiple tasks like account creation, deposits, withdrawals, and reports
    public void performOperations(String operationType, String accountHolderName, String accountNumber, double amount, boolean isVIP, String bankBranch, String email, String phoneNumber, String address, String status, boolean generateReport) {
        // Handle account creation
        if (operationType.equals("create")) {
            createAccount(accountHolderName, accountNumber, amount, email, phoneNumber, address);
        }
        
        // Handle deposit/withdrawal
        if (operationType.equals("deposit")) {
            for (Account account : accounts) {
                if (account.accountNumber.equals(accountNumber)) {
                    account.balance += amount;
                    transactionLog.add("Deposit: " + amount + " to " + accountNumber);
                    System.out.println("Deposit successful: " + amount + " to " + account.accountHolderName);
                }
            }
        } else if (operationType.equals("withdrawal")) {
            for (Account account : accounts) {
                if (account.accountNumber.equals(accountNumber)) {
                    if (account.balance >= amount) {
                        account.balance -= amount;
                        transactionLog.add("Withdrawal: " + amount + " from " + accountNumber);
                        System.out.println("Withdrawal successful: " + amount + " from " + account.accountHolderName);
                    } else {
                        System.out.println("Insufficient funds for withdrawal.");
                    }
                }
            }
        }

        // VIP status check
        if (isVIP) {
            System.out.println("VIP customer " + accountHolderName);
        }

        // Branch processing
        if (bankBranch.equals("NY")) {
            System.out.println("New York branch processing.");
        } else if (bankBranch.equals("SF")) {
            System.out.println("San Francisco branch processing.");
        }

        // Generate report if needed
        if (generateReport) {
            printAccountReport(accountNumber);
        }
    }
    
    // Duplicated Code: Similar logic in multiple places, can be refactored
    public void checkAccountStatus(String accountNumber) {
        for (Account account : accounts) {
            if (account.accountNumber.equals(accountNumber)) {
                if (account.balance > 1000) {
                    System.out.println("Account balance is healthy.");
                } else {
                    System.out.println("Low account balance.");
                }
            }
        }
    }

    public void printAccountReport(String accountNumber) {
        for (Account account : accounts) {
            if (account.accountNumber.equals(accountNumber)) {
                System.out.println("Account Report for " + account.accountHolderName);
                System.out.println("Balance: " + account.balance);
            }
        }
    }
    
    // Large Parameter List: Too many parameters for this method
    public void createAccount(String accountHolderName, String accountNumber, double initialDeposit, String email, String phoneNumber, String address) {
        Account newAccount = new Account(accountHolderName, initialDeposit, accountNumber);
        accounts.add(newAccount);
        System.out.println("Account created for: " + accountHolderName);
    }

    public List<String> getTransactionLog() {
        return transactionLog;
    }
}

// Unused Code: This method is never called
class BonusManager {
    public void calculateYearEndBonus() {
        System.out.println("Calculating year-end bonuses...");
    }
}

public class Main {
    public static void Main(String[] args) {
        Bank bank = new Bank();
        // Create accounts and perform operations
        bank.performOperations("create", "Alice", "ACC001", 1000, false, "NY", "alice@mail.com", "555-1234", "123 Main St", "active", false);
        bank.performOperations("deposit", "Alice", "ACC001", 500, false, "NY", null, null, null, null, false);
        bank.performOperations("withdrawal", "Alice", "ACC001", 300, true, "NY", null, null, null, null, true);
        
        // Check account status (duplicated logic)
        bank.checkAccountStatus("ACC001");
        
        // Another instance of large parameter list and long method
        bank.performOperations("create", "Bob", "ACC002", 2000, false, "SF", "bob@mail.com", "555-5678", "456 Main St", "active", false);
        bank.performOperations("withdrawal", "Bob", "ACC002", 2500, true, "SF", null, null, null, null, true);
        
        // Bonus manager with unused code
        BonusManager bonusManager = new BonusManager();
        bonusManager.calculateYearEndBonus(); // This is never used
    }
}
