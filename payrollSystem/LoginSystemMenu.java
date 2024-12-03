package payrollSystem;
import java.util.*;

/**
 * A Login System Menu Object <br>
 * 
 * A <code>LoginSystemMenu</code> object contains the parameters and functionality of
 * the CLI frontend of a login system used in a payroll system
 * 
 * @author Dónal Reynolds
 */
public class LoginSystemMenu {
    private final LoginSystem loginSystem;
    private final PayrollSystem payrollSystem;
    private final PayrollScheduler payrollScheduler;

    // Constructor
    /**
     * LoginSystemMenu constructor
     * @param loginSystem - the LoginSystem class
     * @param payrollSystem - the PayrollSystem class
     */
    public LoginSystemMenu(LoginSystem loginSystem, PayrollSystem payrollSystem) {
        this.loginSystem = loginSystem;
        this.payrollSystem = payrollSystem;
        this.payrollScheduler = new PayrollScheduler(payrollSystem, loginSystem);
        payrollScheduler.start();
    }

    // Start the CLI
    /**
     * Method for starting the system and taking user inputs on further actions
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Login\n2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleLogin(scanner);
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Method for logging users in - takes user input of username and password
     * @param scanner - scanner for user input
     */
    private void handleLogin(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User loggedInUser = loginSystem.authenticate(username, password);
        if (loggedInUser != null) {
            System.out.println("Login successful. Welcome, " + loggedInUser.getUserType() + " " + loggedInUser.getPosition() + " " + loggedInUser.getUsername());
            handleUserActions(scanner, loggedInUser);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    /**
     * Method for handling user actions based on the user type (Employee, Admin or HR)
     * @param scanner - scanner for taking user input
     * @param user - the current user
     */
    private void handleUserActions(Scanner scanner, User user) {
        // Handle the actions for different userTypes
        switch (user.getUserType()) {
            case "Employee":
                if (user.getRoleType().equals("F")) {
                handleEmployeeActions(scanner, user); // Employee can access Employee options
                break;
                }
                else {
                    handlePartTimeEmployeeActions(scanner, user);
                    break;
                }
            case "HR":
                handleEmployeeActions(scanner, user); // HR can also access Employee options
                handleHRActions(scanner); // HR actions
                break;
            case "Admin":
                handleEmployeeActions(scanner, user); // Admin can also access Employee options
                handleAdminActions(scanner); // Admin actions
                break;
            default:
                System.out.println("Invalid userType. Contact system administrator.");
        }
    }

    /**
     * Method for handling actions that employees can take
     * @param scanner - scanner for taking user input
     * @param user - the current user
     */
    private void handleEmployeeActions(Scanner scanner, User user) {
        while (true) {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. View Most Recent Payslip\n2. View Historical Payslips\n3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    payrollSystem.printPayslip(user); // Use PayrollSystem to print payslip
                    break;
                case 2:
                    payrollSystem.readPayslipsFromCSV(user);
                    break;
                case 3:
                    return; // Logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Method for handling actions that part-time employees can take
     * @param scanner - scanner for taking user input
     * @param user - the current user
     */
    private void handlePartTimeEmployeeActions(Scanner scanner, User user) {
        while (true) {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. View Most Recent Payslip\n2. View Historical Payslips\n3. Submit a Pay Claim Form\n4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    payrollSystem.printPayslip(user); // Use PayrollSystem to print payslip
                    break;
                case 2:
                    payrollSystem.readPayslipsFromCSV(user);
                    break;
                case 3:
                    System.out.println("Enter hours worked for current month: ");
                    String payClaimForm = scanner.nextLine();
                    double hrsWorked = Double.parseDouble(payClaimForm);
                    user.setHrsWorked(hrsWorked);
                    user.setCurrentClaim(true);
                    payrollScheduler.submitClaim(user);
                    break;
                case 4:
                    return; // Logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Method for handling actions that admins can take
     * @param scanner - scanner for taking user input
     */
    private void handleAdminActions(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Employee\n2. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter userType (Employee/HR/Admin):");
                    String userType = scanner.nextLine();
                    if (userType.toUpperCase() == "EMPLOYEE") {
                        userType = "Employee";
                    } else if (userType.toUpperCase() == "ADMIN") {
                        userType = "Admin";
                    } else if (userType.toUpperCase() == "EMPLOYEE") {
                        userType = "Employee";
                    } else {userType = "Employee";}
                    System.out.println("Enter position:");
                    String position = scanner.nextLine().toUpperCase();
                    System.out.println("Enter roleType ((F)ull-time/(P)art-time):");
                    String roleType = scanner.nextLine().toUpperCase();
                    System.out.println("Enter hours worked so far (can be 0):");
                    String stringHrsWorked = scanner.nextLine();
                    double hrsWorked = Double.parseDouble(stringHrsWorked);
                    System.out.println("Enter scale point:");
                    String stringScalePoint = scanner.nextLine();
                    int scalePoint = Integer.parseInt(stringScalePoint);
                    loginSystem.registerUser(username, password, userType, position, roleType, hrsWorked, scalePoint);
                    break;
                case 2:
                    return; // Logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Method for handling actions that HR users can take
     * @param scanner - scanner for taking user input
     */
    private void handleHRActions(Scanner scanner) {
        while (true) {
            System.out.println("\nHR Menu:");
            System.out.println("1. Implement Promotion\n2. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    implementPromotion(scanner); // Add full promotion functionality
                    break;
                case 2:
                    return; // Logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Method for implementing a promotion - can only be accessed by HR user
     * @param scanner - scanner for taking user input
     */
    private void implementPromotion(Scanner scanner) {
        System.out.print("Enter the username of the employee to promote: ");
        String username = scanner.nextLine();
    
        // Locate the user
        User user = null;
        for (User u : loginSystem.getUsers()) { // Access the list of users via an existing method
            if (u.getUsername().equalsIgnoreCase(username)) {
                user = u;
                break;
            }
        }
    
        if (user == null || !user.getUserType().equalsIgnoreCase("Employee")) {
            System.out.println("Employee not found or invalid userType.");
            return;
        }
    
        System.out.println("Current Scale Point: " + user.getScalePoint());
        System.out.print("Enter the new Scale Point: ");
        int newScalePoint = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        System.out.println("Confirm promotion for " + user.getPosition() + " " + user.getUsername() + " from Scale Point " +
                            user.getScalePoint() + " to Scale Point " + newScalePoint + "? (yes/no)");
        String confirmation = scanner.nextLine();
    
        if (confirmation.equalsIgnoreCase("yes")) {
            user.setScalePoint(newScalePoint);
            loginSystem.saveUsers(); // Save users to CSV after the promotion
            System.out.println("Promotion implemented successfully.");
        } else {
            System.out.println("Promotion canceled.");
        }
    }
}
