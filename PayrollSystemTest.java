import java.util.List;

public class PayrollSystemTest {
    public static void main(String[] args) {

        RoleFactory roleFactory = new RoleFactory();

        // Load pay scales from CSV
        roleFactory.loadPayScalesFromCSV("salaryScales");

        // Initialize PayrollSystem with RoleFactory
        PayrollSystem payrollSystem = new PayrollSystem(roleFactory);

        // Create test users
        User user1 = new User("Alice", "Pasword", "Teacher"); // Full-time
        user1.setScalePoint(1);

        // Test printing and saving payslip for user1
        System.out.println("Processing payslip for Alice:");
        payrollSystem.printPayslip(user1);


        // Test reading payslips from the CSV file
        System.out.println("\nReading all payslips from CSV:");
        List<String> payslips = payrollSystem.readPayslipsFromCSV();
        for (String payslip : payslips) {
            System.out.println(payslip);
        }
    }
}
