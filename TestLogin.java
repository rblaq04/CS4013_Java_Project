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
            System.out.println("1. Start Login System\n2. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Start LoginSystemMenu (This will start the login process and show menus)
                    loginSystemMenu.start();
                    break;
                case 2:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
