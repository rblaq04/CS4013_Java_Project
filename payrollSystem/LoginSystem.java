package payrollSystem;
import java.io.*;
import java.util.*;
/**
 * A Login System Object <br>
 * 
 * A <code>LoginSystem</code> object contains the parameters and functionality of
 * the backend of a login system used in a payroll system.
 * 
 * @author Rayan Blaq, Dónal Reynolds
 */

public class LoginSystem {
    private List<User> users;
    private final String filePath;

    // Constructor
    /**
     * LoginSystem constructor
     * 
     * @param filePath - the filepath for the users.csv file
     */
    public LoginSystem(String filePath) {
        this.filePath = filePath;
        this.users = new ArrayList<>();
        loadUsersFromCSV();
    }
 
    // Load users from CSV
    /**
     * Private method to load users into the users list from a CSV file
     */
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
                if (parts.length == 8) {
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
    /**
     * Private method to save users currently in the users list to a CSV file 
     */
    private void saveUsersToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("username,password,userType,position,roleType,hrsWorked,scalePoint,currentClaim");
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
    /**
     * Public method to save users
     */
    public void saveUsers() {
        saveUsersToCSV(); // Calls the private method to save users
    }  

    
    /** 
     * Register a new user
     * 
     * @param username - the user's username
     * @param password - the user's password
     * @param userType - the user's access level (Employee, HR or Admin)
     * @param position - the user's position at the university (i.e. Associate Professor)
     * @param roleType - user is part-time (P) or full-time (F)
     * @param hrsWorked - the number of hours worked by the user
     * @param scalePoint - the point a user is at on their salary scale
     */
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

    /**
     * Get list of users
     * @return the list of current users
     */
    public List<User> getUsers() {
        return users; // Return the list of users
    }


    /**
     * Authenticate a user when logging in
     * @param username - the user's username
     * @param password - the user's password
     * @return the user that has been authenticated or null if credentials were invalid
     */
    public User authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Invalid credentials
    }
}
