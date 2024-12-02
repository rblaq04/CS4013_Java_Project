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
                double doublePart = Double.parseDouble(parts[5]);
                int intPart = Integer.parseInt(parts[6]);
                if (parts.length == 7) {
                    users.add(new User(parts[0], parts[1], parts[2], parts[3], parts[4], doublePart, intPart));
                } else {
                    System.out.println("Failed to load users from CSV.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }


    // Save users to CSV
    private void saveUsersToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("username,password,userType,roleType,hrsWorked,scalePoint");
            bw.newLine();
            for (User user : users) {
                bw.write(user.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Public method to save users to be used from the LoginSystemMenu class
    public void saveUsers() {
        saveUsersToCSV(); // Calls the private method to save users
    }  

    // Register a new user
    public void registerUser(String username, String password, String userType, String position, String roleType, double hrsWorked, int scalePoint) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }
        users.add(new User(username, password, userType, position, roleType, hrsWorked, scalePoint));
        saveUsersToCSV();
        System.out.println("User registered successfully.");
    }

    public List<User> getUsers() {
        return users; // Return the list of users
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
}
