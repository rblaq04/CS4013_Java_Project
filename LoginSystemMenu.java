import java.util.*;

public class LoginSystemMenu {
    private final LoginSystem loginSystem;
    private final PayrollSystem payrollSystem;

    // Constructor
    public LoginSystemMenu(LoginSystem loginSystem, PayrollSystem payrollSystem) {
        this.loginSystem = loginSystem;
        this.payrollSystem = payrollSystem;
    }

    // Start the CLI
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
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

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

    private void handleUserActions(Scanner scanner, User user) {
        // Handle the actions for different userTypes
        switch (user.getUserType()) {
            case "Employee":
                handleEmployeeActions(scanner, user); // Employee can access Employee options
                break;
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

    private void handleEmployeeActions(Scanner scanner, User user) {
        while (true) {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. View Most Recent Payslip\n2. View Historical Payslips");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    payrollSystem.printPayslip(user); // Use PayrollSystem to print payslip
                    break;
                case 2:
                    payrollSystem.readPayslipsFromCSV();
                case 3:
                    return; // Logout
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

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
                    System.out.println("Enter position:");
                    String position = scanner.nextLine();
                    System.out.println("Enter roleType ((F)ull-time/(P)art-time):");
                    String roleType = scanner.nextLine();
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