import java.util.*;

public class TestLogin {
    public static void main(String[] args) {
        String filePath = "users.csv"; // Path to the CSV file
        LoginSystem loginSystem = new LoginSystem(filePath);
        
        // Create RoleFactory instance
        RoleFactory roleFactory = new RoleFactory();
        
        // Create PayrollSystem instance using the RoleFactory
        PayrollSystem payrollSystem = new PayrollSystem(roleFactory);

        // Create the LoginSystemMenu instance
        LoginSystemMenu loginSystemMenu = new LoginSystemMenu(loginSystem, payrollSystem);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Register User\n2. Start Login System\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Register User functionality
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter role (Employee/HR/Admin):");
                    String role = scanner.nextLine();
                    loginSystem.registerUser(username, password, role);
                    break;
                case 2:
                    // Start LoginSystemMenu (This will start the login process and show menus)
                    loginSystemMenu.start();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
