import java.util.*;

public class MiniBank {

    // ---------------- MENU ----------------
    static void showmenu() {
        System.out.println("\n------------------- MENU -------------------");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit Amount");
        System.out.println("3. Withdraw Amount");
        System.out.println("4. Transaction History");
        System.out.println("5. Logout");
        System.out.println("6. Change Password");
        System.out.println("7. Exit");
        System.out.print("Select an Option: ");
    }

    // ---------------- REGISTRATION ----------------
    static String[] registration(Scanner sc) {

        String username = "";
        String password = "";

        System.out.println("--------------- Registration ---------------");

        while (true) {
            System.out.print("Enter Username: ");
            username = sc.nextLine().trim();

            System.out.print("Enter Password: ");
            password = sc.nextLine().trim();

            if (username.isEmpty() || password.isEmpty()) {
                System.out.println("Fields cannot be empty");
            } else {
                System.out.println("Registration Successful");
                break;
            }
        }

        return new String[]{username, password};
    }

    // ---------------- LOGIN ----------------
    static boolean login(Scanner sc, String username, String password) {

        int attempts = 3;

        while (attempts > 0) {

            System.out.print("Enter Username: ");
            String enteredUsername = sc.nextLine().trim();

            System.out.print("Enter Password: ");
            String enteredPassword = sc.nextLine().trim();

            if (enteredUsername.equals(username) &&
                    enteredPassword.equals(password)) {

                System.out.println("Login Successful");
                return true;

            } else {
                attempts--;
                System.out.println("Invalid Credentials");
                System.out.println("Attempts Left: " + attempts);
            }
        }

        System.out.println("Account Locked");
        return false;
    }

    // ---------------- FORGOT PASSWORD ----------------
    static void ForgotPassword(Scanner sc,
                               String username,
                               String[] passwordHolder) {

        String storedColour = "blue";

        System.out.print("Enter Username: ");
        String checkedUsername = sc.nextLine().trim();

        if (!checkedUsername.equalsIgnoreCase(username)) {
            System.out.println("Username Not Found");
            return;
        }

        System.out.println("Username Verified");

        int attempts = 3;

        while (attempts > 0) {

            System.out.println("Security Question:");
            System.out.print("What is your favourite colour? ");

            String colour = sc.nextLine().trim();

            if (colour.equalsIgnoreCase(storedColour)) {

                System.out.println("Verified");

                while (true) {

                    System.out.print("Enter New Password: ");
                    String newPass = sc.nextLine().trim();

                    System.out.print("Confirm Password: ");
                    String confirmPass = sc.nextLine().trim();

                    if (newPass.isEmpty()) {
                        System.out.println("Password cannot be empty");
                        continue;
                    }

                    if (!newPass.equals(confirmPass)) {
                        System.out.println("Password does not match");
                        continue;
                    }

                    passwordHolder[0] = newPass;
                    System.out.println("Password Reset Successful");
                    return;
                }

            } else {
                attempts--;
                System.out.println("Wrong Answer");
                System.out.println("Attempts Left: " + attempts);
            }
        }

        System.out.println("Too Many Attempts. Try Again Later.");
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<String> transaction = new ArrayList<>();

        // Registration
        String[] userdata = registration(sc);
        String username = userdata[0];
        String[] passwordHolder = {userdata[1]};

        boolean isLoggedin = false;

        // ---------------- PRE LOGIN ----------------
        while (!isLoggedin) {

            System.out.println("\n1. Login");
            System.out.println("2. Forgot Password");
            System.out.println("3. Exit");
            System.out.print("Choose an Option: ");

            String input = sc.nextLine().trim();

            switch (input) {

                case "1":
                    isLoggedin =
                            login(sc, username, passwordHolder[0]);
                    break;

                case "2":
                    ForgotPassword(sc, username, passwordHolder);
                    break;

                case "3":
                    System.out.println("Thank You");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }

        // ---------------- BANKING SYSTEM ----------------
        double balance = 10000.00;

        while (isLoggedin) {

            showmenu();

            String input = sc.nextLine().trim();

            int choice = 0;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Enter valid number");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println("Current Balance: " + balance);
                    break;

                case 2:
                    System.out.print("Enter Deposit Amount: ");
                    double deposit =
                            Double.parseDouble(sc.nextLine());

                    if (deposit <= 0) {
                        System.out.println("Invalid Amount");
                    } else {
                        balance += deposit;
                        transaction.add("Deposited: " + deposit);
                        System.out.println("Amount Deposited");
                    }
                    break;

                case 3:
                    System.out.print("Enter Withdraw Amount: ");
                    double withdraw =
                            Double.parseDouble(sc.nextLine());

                    if (withdraw > balance) {
                        System.out.println("Insufficient Funds");
                    } else {
                        balance -= withdraw;
                        transaction.add("Withdrawn: " + withdraw);
                        System.out.println("Amount Withdrawn");
                    }
                    break;

                case 4:
                    if (transaction.isEmpty()) {
                        System.out.println("No Transactions");
                    } else {
                        for (String t : transaction) {
                            System.out.println(t);
                        }
                    }
                    break;

                case 5:
                    System.out.print("Are you sure to Logout? (Y/N): ");
                    String log = sc.nextLine();

                    if (log.equalsIgnoreCase("Y")) {
                        isLoggedin = false;
                        System.out.println("Logged Out");
                    }
                    break;

                case 6:
                    System.out.print("Enter Current Password: ");
                    String oldPassword = sc.nextLine();

                    if (!oldPassword.equals(passwordHolder[0])) {
                        System.out.println("Wrong Password");
                        break;
                    }

                    while (true) {

                        System.out.print("Enter New Password: ");
                        String newPassword =
                                sc.nextLine().trim();

                        System.out.print("Confirm Password: ");
                        String confirmPassword =
                                sc.nextLine().trim();

                        if (newPassword.isEmpty()) {
                            System.out.println(
                                    "Password cannot be empty");
                            continue;
                        }

                        if (!newPassword.equals(confirmPassword)) {
                            System.out.println(
                                    "Password Mismatch");
                            continue;
                        }

                        passwordHolder[0] = newPassword;
                        System.out.println(
                                "Password Changed Successfully");
                        break;
                    }
                    break;

                case 7:
                    System.out.print("Are you sure to Exit? (Y/N): ");
                    String exit = sc.nextLine();

                    if (exit.equalsIgnoreCase("Y")) {
                        System.out.println(
                                "Thank You For Using MiniBank");
                        return;
                    }
                    break;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}