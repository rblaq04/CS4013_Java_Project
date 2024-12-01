import java.io.*;
import java.util.*;

public class LoginSystem {
    private List<User> users;
    private final String filePath;

    // Constructor
    public LoginSystem(String filePath) {
        this.filePath = filePath;
        this.users = new ArrayList<>();
        loadUsersFromCSV();
    }

    // Load users from CSV
    private void loadUsersFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true; // Flag to skip the header
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip the first line
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }


    // Save users to CSV
    private void saveUsersToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                bw.write(user.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Register a new user
    public void registerUser(String username, String password, String role) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }
        users.add(new User(username, password, role));
        saveUsersToCSV();
        System.out.println("User registered successfully.");
    }

    // Authenticate a user
    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Invalid credentials
    }

    // Login
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User user = authenticate(username, password);
        if (user != null) {
            System.out.println("Login successful. Welcome, " + user.getRole() + " " + user.getUsername());
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}
