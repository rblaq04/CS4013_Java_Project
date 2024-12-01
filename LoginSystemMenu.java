import java.io.*;
import java.util.*;

public class LoginSystemMenu {
    private final LoginSystem loginSystem;
    private final Scanner scanner; // Declare a class-level scanner

    // Constructor
    public LoginSystemMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.scanner = new Scanner(System.in); // Initialize scanner
    }

    // Start the CLI
    public void start() {
        try {
            while (true) {
                System.out.println("\n1. Login\n2. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        User loggedInUser = handleLogin();
                        if (loggedInUser != null) {
                            handleUserActions(loggedInUser);
                        }
                        break;
                    case 2:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } finally {
            scanner.close(); // Close scanner when application exits
        }
    }

    // Handle user login
    private User handleLogin() {
        loginSystem.login();

        System.out.println("Re-enter username for role-specific actions:");
        String username = scanner.nextLine();

        // Verify the user using loadUsersFromCSV logic
        try (BufferedReader br = new BufferedReader(new FileReader("src\\users.csv"))) { // Update path as needed
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(username)) {
                    return new User(parts[0], parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error verifying user: " + e.getMessage());
        }
        System.out.println("User not found after login. Please try again.");
        return null;
    }

    // Handle actions based on user role
    private void handleUserActions(User user) {
        switch (user.getRole().toLowerCase()) {
            case "employee":
                handleEmployeeActions(user);
                break;
            case "admin":
                handleAdminActions(user);
                break;
            case "hr":
                handleHrActions(user);
                break;
            default:
                System.out.println("Invalid role: " + user.getRole());
        }
    }

    // Employee actions
    private void handleEmployeeActions(User user) {
        System.out.println("\nEmployee Options");
        System.out.println("1. View Details");
        System.out.println("2. View Payslips");
        System.out.println("3. Logout");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Your details: " + user);
                break;
            case 2:
                System.out.println("Viewing payslips...");
                // Placeholder: logic for viewing payslips goes here
                break;
            case 3:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    // Admin actions
    private void handleAdminActions(User user) {
      while (true) {
          System.out.println("\nAdmin Options");
          System.out.println("1. Employee Options");
          System.out.println("2. Add New Employee");
          System.out.println("3. Logout");
          int choice = scanner.nextInt();
          scanner.nextLine(); // Consume newline
  
          switch (choice) {
              case 1:
                  handleEmployeeActions(user);
                  break;
              case 2:
                  System.out.println("Enter new employee details.");
                  System.out.println("Username:");
                  String username = scanner.nextLine();
                  System.out.println("Password:");
                  String password = scanner.nextLine();
                  System.out.println("Role (Employee/Admin/HR):");
                  String role = scanner.nextLine();
  
                  // Create a new User object for the new employee
                  User newUser = new User(username, password, role);
                  
                  // Save the new user to the CSV file
                  saveUserToCSV(newUser);
  
                  System.out.println("New user added successfully.");
                  break;
              case 3:
                  System.out.println("Logging out...");
                  return;
              default:
                  System.out.println("Invalid choice. Returning to main menu.");
          }
      }
  }
  

    // HR actions
    private void handleHrActions(User user) {
        while (true) {
            System.out.println("\nHR Options");
            System.out.println("1. Employee Options");
            System.out.println("2. Implement Promotion");
            System.out.println("3. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleEmployeeActions(user);
                    break;
                case 2:
                    System.out.println("Enter the username of the employee to promote:");
                    String username = scanner.nextLine();
                    System.out.println("Confirm promotion changes for " + username + " (yes/no):");
                    String confirmation = scanner.nextLine();

                    if (confirmation.equalsIgnoreCase("yes")) {
                        System.out.println("Promotion applied for " + username);
                        // Placeholder: logic for implementing promotion goes here
                    } else {
                        System.out.println("Promotion canceled.");
                    }
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Returning to main menu.");
            }
        }
    }

    // Save the new user to the CSV file
    private void saveUserToCSV(User newUser) {
       try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\users.csv", true))) { // Open in append mode
         bw.write(newUser.toString());
         bw.newLine(); // Add a new line after each user
      } catch (IOException e) {
         System.out.println("Error saving user: " + e.getMessage());
      }
   }
}
