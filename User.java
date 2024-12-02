// This is a class for a user in the payroll system (an employee, HR, or admin)
public class User {
    private String username;
    private String password;
    private String role;
    private double hrsWorked;
    private int scalePoint;

    // Constructor
    public User(String username, String password, String role, double hrsWorked, int scalePoint) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.hrsWorked = hrsWorked;
        this.scalePoint = scalePoint;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public double getHrsWorked(){
        return  hrsWorked;
    }

    public void addHrsWorked(int hrs){
        hrsWorked =+ hrs;
    }

    public void setScalePoint(int scalePoint){
        this.scalePoint = scalePoint;
    }

    public int getScalePoint(){
        return scalePoint;
    }

    

    // To String for saving to CSV
    @Override
    public String toString() {
        return username + "," + password + "," + role +","+hrsWorked+","+scalePoint;
    }
}
