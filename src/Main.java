import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Main {
    static Scanner menuInput = new Scanner(System.in);
    static DecimalFormat moneyFormat = new DecimalFormat("'R'###,##0.00");
    static HashMap<Integer, Account> data = new HashMap<Integer, Account>();

    public static void main(String[] args) throws IOException {
        data.put(123456789, new Account(123456789, 123, 20000, 50000));
        boolean end = false;
        while (!end) {
            try {
                System.out.println("\nOption 1 - Login");
                System.out.println("Option 2 - Create Account");
                System.out.print("\nOption: ");
                int choice = menuInput.nextInt();
                switch (choice) {
                    case 1:
                        getLogin();
                        end = true;
                        break;
                    case 2:
                        createAccount();
                        end = true;
                        break;
                    default:
                        System.out.println("\nInvalid Choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Choice.");
                menuInput.next();
            }
        }
        menuInput.close();
        System.exit(0);
    }

    static void createAccount() throws IOException {
        int cst_no = 0;
        boolean end = false;
        while (!end) {
            try {
                System.out.println("\nEnter your customer number ");
                cst_no = menuInput.nextInt();
                Iterator it = data.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (!data.containsKey(cst_no)) {
                        end = true;
                    }
                }
                if (!end) {
                    System.out.println("\nThis customer number is already registered");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Option.");
                menuInput.next();
            }
        }
        System.out.println("\nEnter PIN to be registered");
        int pin = menuInput.nextInt();
        data.put(cst_no, new Account(cst_no, pin));
        System.out.println("\nYour new account has been successfully registered!");
        System.out.println("\nRedirecting to login.............");
        getLogin();
    }

    public static void getLogin() throws IOException {
        boolean end = false;
        int customerNumber = 0;
        int pinNumber = 0;

        while (!end) {
            try {
                System.out.print("\nEnter your customer number: ");
                customerNumber = menuInput.nextInt();
                System.out.print("\nEnter your PIN number: ");
                pinNumber = menuInput.nextInt();

                Account account = data.get(customerNumber);
                if (account != null && pinNumber == account.getPinNumber()) {
                    showAccountMenu(account);
                    end = true;
                } else {
                    System.out.println("\nWrong Customer Number or Pin Number");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Character(s). Only Numbers.");
                menuInput.nextLine(); // Clear the input buffer
            }
        }
    }

    public static void showAccountMenu(Account account) throws IOException {
        boolean end = false;
        while (!end) {
            System.out.println("\nSelect the account you want to access: ");
            System.out.println("1. Checking Account");
            System.out.println("2. Savings Account");
            System.out.println("3. Change PIN");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");

            try {
                int choice = menuInput.nextInt();

                switch (choice) {
                    case 1:
                        showCheckingMenu(account);
                        break;
                    case 2:
                        showSavingMenu(account);
                        break;
                    case 3:
                        changePIN(account);
                        break;
                    case 4:
                        viewTransactionHistory(account);
                        break;
                    case 5:
                        System.out.println("\nThank you for using the ATM. Goodbye!");
                        end = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid choice. Please try again.");
                menuInput.nextLine(); // Clear the input buffer
            }
        }
    }

    public static void showCheckingMenu(Account account) throws IOException {
        boolean end = false;
        while (!end) {
            System.out.println("\nChecking Account: ");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer to Savings");
            System.out.println("5. Exit");

            try {
                int choice = menuInput.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("\nChecking Account Balance: " + moneyFormat.format(account.getCheckingBalance()));
                        break;
                    case 2:
                        account.getCheckingWithdrawInput();
                        break;
                    case 3:
                        account.getCheckingDepositInput();
                        break;
                    case 4:
                        account.getTransferInput("Checking");
                        break;
                    case 5:
                        showAccountMenu(account);
                        end = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid choice. Please try again.");
                menuInput.nextLine(); // Clear the input buffer
            }
        }
    }

    public static void showSavingMenu(Account account) throws IOException {
        boolean end = false;
        while (!end) {
            System.out.println("\nSavings Account: ");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer to Checking");
            System.out.println("5. Exit");

            try {
                int choice = menuInput.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("\nSavings Account Balance: " + moneyFormat.format(account.getSavingBalance()));
                        break;
                    case 2:
                        account.getSavingWithdrawInput();
                        break;
                    case 3:
                        account.getSavingDepositInput();
                        break;
                    case 4:
                        account.getTransferInput("Savings");
                        break;
                    case 5:
                        showAccountMenu(account);
                        end = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid choice. Please try again.");
                menuInput.nextLine(); // Clear the input buffer
            }
        }
    }

    public static void changePIN(Account account) {
        System.out.print("\nEnter the current PIN: ");
        int currentPIN = menuInput.nextInt();

        if (currentPIN == account.getPinNumber()) {
            System.out.print("\nEnter the new PIN: ");
            int newPIN = menuInput.nextInt();
            account.setPinNumber(newPIN);
            System.out.println("\nPIN changed successfully.");
        } else {
            System.out.println("\nIncorrect PIN. PIN change failed.");
        }
    }

    public static void viewTransactionHistory(Account account) {
        System.out.println("\nTransaction History:");
        System.out.println("---------------------");
        account.displayTransactionHistory();
    }
}

class Account {
    // variables
    private int customerNumber;
    private int pinNumber;
    private double checkingBalance = 0;
    private double savingBalance = 0;

    Scanner input = new Scanner(System.in);
    DecimalFormat moneyFormat = new DecimalFormat("'R'###,##0.00");

    private List<String> transactionHistory = new ArrayList<String>();

    public void setTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public void displayTransactionHistory() {
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }


    public Account() {
    }

    public Account(int customerNumber, int pinNumber) {
        this.customerNumber = customerNumber;
        this.pinNumber = pinNumber;
    }

    public Account(int customerNumber, int pinNumber, double checkingBalance, double savingBalance) {
        this.customerNumber = customerNumber;
        this.pinNumber = pinNumber;
        this.checkingBalance = checkingBalance;
        this.savingBalance = savingBalance;
    }

    public int setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
        return customerNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public int setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
        return pinNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public double getCheckingBalance() {
        return checkingBalance;
    }

    public double getSavingBalance() {
        return savingBalance;
    }

    public double calcCheckingWithdraw(double amount) {
        checkingBalance = (checkingBalance - amount);
        return checkingBalance;
    }

    public double calcSavingWithdraw(double amount) {
        savingBalance = (savingBalance - amount);
        return savingBalance;
    }

    public double calcCheckingDeposit(double amount) {
        checkingBalance = (checkingBalance + amount);
        return checkingBalance;
    }

    public double calcSavingDeposit(double amount) {
        savingBalance = (savingBalance + amount);
        return savingBalance;
    }

    public void calcCheckTransfer(double amount) {
        checkingBalance = checkingBalance - amount;
        savingBalance = savingBalance + amount;
    }

    public void calcSavingTransfer(double amount) {
        savingBalance = savingBalance - amount;
        checkingBalance = checkingBalance + amount;
    }

    public void getCheckingWithdrawInput() {
        boolean end = false;
        while (!end) {
            try {
                System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
                System.out.print("\nAmount you want to withdraw from Checking Account: ");
                double amount = input.nextDouble();
                if ((checkingBalance - amount) >= 0 && amount >= 0) {
                    calcCheckingWithdraw(amount);
                    System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
                    end = true;
                } else {
                    System.out.println("\nInsufficient Amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Choice.");
                input.next();
            }
        }
    }

    public void getSavingWithdrawInput() {
        boolean end = false;
        while (!end) {
            try {
                System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
                System.out.print("\nAmount you want to withdraw from Savings Account: ");
                double amount = input.nextDouble();
                if ((savingBalance - amount) >= 0 && amount >= 0) {
                    calcSavingWithdraw(amount);
                    System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
                    end = true;
                } else {
                    System.out.println("\nInsufficient Amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Choice.");
                input.next();
            }
        }
    }

    public void getCheckingDepositInput() {
        boolean end = false;
        while (!end) {
            try {
                System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
                System.out.print("\nAmount you want to deposit from Checking Account: ");
                double amount = input.nextDouble();
                if ((checkingBalance + amount) >= 0 && amount >= 0) {
                    calcCheckingDeposit(amount);
                    System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
                    end = true;
                } else {
                    System.out.println("\nInsufficient Amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Choice.");
                input.next();
            }
        }
    }

    public void getSavingDepositInput() {
        boolean end = false;
        while (!end) {
            try {
                System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
                System.out.print("\nAmount you want to deposit into your Savings Account: ");
                double amount = input.nextDouble();

                if ((savingBalance + amount) >= 0 && amount >= 0) {
                    calcSavingDeposit(amount);
                    System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
                    end = true;
                } else {
                    System.out.println("\nInsufficient Amount.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Choice.");
                input.next();
            }
        }
    }

    public void getTransferInput(String accType) {
        boolean end = false;
        while (!end) {
            try {
                if (accType.equals("Checking")) {
                    System.out.println("\nSelect an account you wish to transfer funds to:");
                    System.out.println("1. Savings");
                    System.out.println("2. Exit");
                    System.out.print("\nChoice: ");
                    int choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
                            System.out.print("\nAmount you want to deposit into your Savings Account: ");
                            double amount = input.nextDouble();
                            if ((savingBalance + amount) >= 0 && (checkingBalance - amount) >= 0 && amount >= 0) {
                                calcCheckTransfer(amount);
                                System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
                                System.out.println(
                                        "\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
                                end = true;
                            } else {
                                System.out.println("\nInsufficient Amount.");
                            }
                            break;
                        case 2:
                            return;
                        default:
                            System.out.println("\nInvalid Choice.");
                            break;
                    }
                } else if (accType.equals("Savings")) {
                    System.out.println("\nSelect an account you wish to transfer funds to: ");
                    System.out.println("1. Checking");
                    System.out.println("2. Exit");
                    System.out.print("\nChoice: ");
                    int choice = input.nextInt();
                    switch (choice) {
                        case 1:
                            System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingBalance));
                            System.out.print("\nAmount you want to deposit into your savings account: ");
                            double amount = input.nextDouble();
                            if ((checkingBalance + amount) >= 0 && (savingBalance - amount) >= 0 && amount >= 0) {
                                calcSavingTransfer(amount);
                                System.out.println("\nCurrent checking account balance: " + moneyFormat.format(checkingBalance));
                                System.out.println("\nCurrent savings account balance: " + moneyFormat.format(savingBalance));
                                end = true;
                            } else {
                                System.out.println("\nInsufficient Amount.");
                            }
                            break;
                        case 2:
                            return;
                        default:
                            System.out.println("\nInvalid Choice.");
                            break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid Choice.");
                input.next();
            }
        }
    }
}