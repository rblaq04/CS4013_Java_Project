import java.io.*;
import java.util.*;
public class TestLogin {
    public static void main(String[] args) {
        String filePath = "src\\users.csv"; // Path to the CSV file
        LoginSystem loginSystem = new LoginSystem(filePath);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Register User\n2. Login\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter role:");
                    String role = scanner.nextLine();
                    loginSystem.registerUser(username, password, role);
                    break;
                case 2:
                    loginSystem.login();
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