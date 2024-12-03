// This is a class for a user in the payroll system (an employee, HR, or admin)
public class User {
    private String username;
    private String password;
    private String userType;
    private String position;
    private String roleType;
    private double hrsWorked;
    private int scalePoint;
    private boolean currentClaim;

    // Constructor
    public User(String username, String password, String userType, String position, String roleType, double hrsWorked, int scalePoint) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.position = position;
        this.roleType = roleType;
        this.hrsWorked = hrsWorked;
        this.scalePoint = scalePoint;
        this.currentClaim = false;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public double getHrsWorked() {
        return  hrsWorked;
    }

    public void addHrsWorked(double hrs) {
        hrsWorked =+ hrs;
    }

    public void setScalePoint(int scalePoint) {
        this.scalePoint = scalePoint;
    }

    public int getScalePoint() {
        return scalePoint;
    }

    public boolean getCurrentClaim() {
        return currentClaim;
    }

    public void setCurrentClaim(boolean currentClaim) {
        this.currentClaim = currentClaim;
    }

    // To String for saving to CSV
    @Override
    public String toString() {
        return username + "," + password + "," + userType +"," + position +"," + roleType +"," + hrsWorked + "," + scalePoint + "," + currentClaim;
    }
}
