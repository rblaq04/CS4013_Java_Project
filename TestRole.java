public class TestRole {
    public static void main(String[] args) {
        RoleFactory roleFactory = new RoleFactory();

        // Load pay scales from CSV
        roleFactory.loadPayScalesFromCSV("salaryScales.csv");

        // Add a role interactively
       

        // Display all roles
        roleFactory.displayRoles();

        // Load pay scales to CSV
        roleFactory.savePayScalesToCSV("salaryScales.csv");
    }
}
