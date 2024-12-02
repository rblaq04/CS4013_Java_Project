// This is a class for a user in the payroll system (an employee, HR, or admin)
public class User {
    private String username;
    private String password;
    private String userType;
    private String roleType;
    private double hrsWorked;
    private int scalePoint;

    // Constructor
    public User(String username, String password, String userType, String roleType, double hrsWorked, int scalePoint) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.roleType = roleType;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType){
        this.userType = userType;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
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
        return username + "," + password + "," + userType +"," + roleType +","+hrsWorked+","+scalePoint;
    }
}
