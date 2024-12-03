/**
 * A User Object <br>
 * 
 * A <code>User</code> object contains the parameters and functionality of
 * a user in a payroll system used at the University of Limerick
 * 
 * @author Rayan Blaq
 */
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
    /**
     * User constructor
     * 
     * @param username - the user's username
     * @param password - the user's password
     * @param userType - the user's access level (Employee, HR or Admin)
     * @param position - the user's position at the university (i.e. Associate Professor)
     * @param roleType - user is part-time (P) or full-time (F)
     * @param hrsWorked - the number of hours worked by the user
     * @param scalePoint - the point a user is at on their salary scale
     */
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

    /**
    * Gets the username.
    * @return the username of the user
    */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the access level of the user (Employee, Admin or HR)
     * @return the access level of the user
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Sets the access level of the user (Employee, Admin or HR)
     * @param userType - the user's access level (Employee, HR or Admin)
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Gets the position of the user at the university
     * @return the position of the user
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the user at the university
     * @param position the user's position at the university
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets the user's role type (part-time or full-time)
     * @return whether the user is part-time (P) or full-time (F)
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * Sets the user's role type (part-time or full-time)
     * @param roleType - user is part-time (P) or full-time (F)
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * Gets the hours worked by the user
     * @return how many hours the user has worked
     */
    public double getHrsWorked() {
        return  hrsWorked;
    }
    
    /**
     * Sets the user's hours worked
     * @param hrsWorked - huors worked by the user
     */
    public void setHrsWorked(double hrsWorked) {
        this.hrsWorked = hrsWorked;
    }

    /**
     * Adds additional hours worked by the user
     * @param hrs - additional hours worked by the user
     */
    public void addHrsWorked(double hrs) {
        hrsWorked =+ hrs;
    }

    /**
     * Sets the point the user is at on their salary scale
     * @param scalePoint - point the user is at on their salary scale
     */
    public void setScalePoint(int scalePoint) {
        this.scalePoint = scalePoint;
    }

    /**
     * Gets the point the user is at on their salary scale
     * @return point the user is at on their salary scale
     */
    public int getScalePoint() {
        return scalePoint;
    }

    /**
     * Gets any current pay claim made by a part-time employee
     * @return current pay claim
     */
    public boolean getCurrentClaim() {
        return currentClaim;
    }

    /**
     * Sets a current pay claim made by a part-time employee
     * @param currentClaim - current pay claim
     */
    public void setCurrentClaim(boolean currentClaim) {
        this.currentClaim = currentClaim;
    }

    // To String for saving to CSV
    /**
     * Returns a string representation of the user object.
     * The string contains the values of all fields, formatted for adding to a CSV file.
     */
    @Override
    public String toString() {
        return username + "," + password + "," + userType +"," + position +"," + roleType +"," + hrsWorked + "," + scalePoint + "," + currentClaim;
    }
}
