public class TestRole {
    public static void main(String[] args) {
        RoleFactory roleFactory = new RoleFactory();

        // Load pay scales from CSV
        roleFactory.loadPayScalesFromCSV("payScales.csv");

        // Add a role interactively
        roleFactory.addRole();

        // Display all roles
        roleFactory.displayRoles();

        // Load pay scales to CSV
        roleFactory.savePayScalesToCSV("payScales.csv");
    }
}