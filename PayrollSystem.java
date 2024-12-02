import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class PayrollSystem {
    private User user;
    private double grossPay;
    private double healthInsurance;
    private double unionFees;
    private double incomeTax;
    private double prsi;
    private double usc;
    private double netPay;
    private RoleFactory roleFactory;
    private double hrsWorked;


    private static final String PAYSLIP_FILE = "payslips.csv";

    // Constructor
    public PayrollSystem(RoleFactory roleFactory) {
        this.roleFactory = roleFactory;
    }

    // Method to calculate net pay after all deductions
    public double calculateNetPay(User user) {
        String role = user.getRole();
        int scalePoint = user.getScalePoint();
         hrsWorked = user.getHrsWorked();
        PayScale payScale = roleFactory.getPayScale(role);
        String roleType = roleFactory.getRoleType(role);

        if("F".equals(roleType)){
            grossPay = payScale.getAnnualRate(scalePoint) / 12;
        } else if ("P".equals(roleType)){
            grossPay = hrsWorked * payScale.getAnnualRate(scalePoint);
        }   
        
        calculateHealthInsurance();
        calculateUnionFees();
        calculateIncomeTax();
        calculatePRSI();
        calculateUSC();

        double totalDeductions = this.healthInsurance + this.unionFees + this.incomeTax + this.prsi + this.usc;
        this.netPay = this.grossPay - totalDeductions;
        return netPay;
    }

    // Method to print the payslip
    public void printPayslip(User user) {
        calculateNetPay(user);
        System.out.printf("Gross Pay: €%.2f%n", this.grossPay);
        System.out.printf("Health Insurance: €%.2f%n", this.healthInsurance);
        System.out.printf("Union Fees: €%.2f%n", this.unionFees);
        System.out.printf("Income Tax: €%.2f%n", this.incomeTax);
        System.out.printf("PRSI: €%.2f%n", this.prsi);
        System.out.printf("USC: €%.2f%n", this.usc);
        System.out.printf("Net Pay: €%.2f%n", this.netPay);
        savePayslipToCSV(user);
    }

    // Method to save payslip to CSV
    private void savePayslipToCSV(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYSLIP_FILE, true))) {
            writer.write(String.format("%s,%s,%d,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f%n",
                    user.getUsername(),
                    user.getRole(),
                    user.getScalePoint(),
                    this.grossPay,
                    this.healthInsurance,
                    this.unionFees,
                    this.incomeTax,
                    this.prsi,
                    this.usc,
                    this.netPay));
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Method to read payslips from CSV
    public List<String> readPayslipsFromCSV() {
        List<String> payslips = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PAYSLIP_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                payslips.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV file: " + e.getMessage());
        }
        return payslips;
    }


    // Method to calculate health insurance
    private void calculateHealthInsurance() {
        this.healthInsurance = this.grossPay * 0.02;
    }

    // Method to calculate union fees
    private void calculateUnionFees() {
        this.unionFees = 10.0;
    }

    // Method to calculate USC
    private void calculateUSC() {
        if (this.grossPay <= 12012) {
            this.usc = this.grossPay * 0.005;
        } else if (this.grossPay <= 25760) {
            double firstBand = 12012 * 0.005;
            double secondBand = (this.grossPay - 12012) * 0.02;
            this.usc = firstBand + secondBand;
        } else if (this.grossPay <= 70044) {
            double firstBand = 12012 * 0.005;
            double secondBand = (21295 - 12012) * 0.02;
            double thirdBand = (this.grossPay - 21295) * 0.045;
            this.usc = firstBand + secondBand + thirdBand;
        } else {
            double firstBand = 12012 * 0.005;
            double secondBand = (21295 - 12012) * 0.02;
            double thirdBand = (70044 - 21295) * 0.045;
            double upperBand = (this.grossPay - 70044) * 0.08;
            this.usc = firstBand + secondBand + thirdBand + upperBand;
        }
    }

    // Method to calculate income tax
    private void calculateIncomeTax() {
        if (this.grossPay <= 36800) {
            this.incomeTax = this.grossPay * 0.20;
        } else {
            double lowerBandTax = 36800 * 0.20;
            double higherBandTax = (this.grossPay - 36800) * 0.40;
            this.incomeTax = lowerBandTax + higherBandTax;
        }
    }

    // Method to calculate PRSI
    private void calculatePRSI() {
        if(grossPay/52 <= 352){
            this.prsi = 0;
        }else{
            this.prsi = this.grossPay * 0.04;
        }
    }

    
    

}


