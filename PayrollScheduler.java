import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.*;

public class PayrollScheduler {
    private final PayrollSystem payrollSystem;
    private final ScheduledExecutorService scheduler;
    private final LoginSystem loginSystem;
    private final Map<String, LocalDate> claimSubmissionDates = new HashMap<>();


    public PayrollScheduler(PayrollSystem payrollSystem, LoginSystem loginSystem) {
        this.payrollSystem = payrollSystem;
        this.loginSystem = loginSystem;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        
    }

    public void start() {
        // Schedule tasks to run daily
        Runnable task = this::processPayrollTasks;
        scheduler.scheduleAtFixedRate(task, 0, 24, TimeUnit.HOURS);
    }

    private void processPayrollTasks() {
        LocalDate today = LocalDate.now();

        resetPartTimeClaims();

        // Check if today is the second Friday of the month
        if (isSecondFriday(today)) {
            System.out.println("Second Friday: Reminder to part-time employees to submit pay claims.");
       
        }

        // Check if today is the 25th of the month
        if (today.getDayOfMonth() == 25) {
            System.out.println("Generating payslips for all employees...");
            generatePayslips();
        }

        if (today.getMonth() == Month.OCTOBER) {
            System.out.println("October: Updating salary scales for full-time staff...");
            updateSalaryScalesForFullTimeStaff();
        }
    }

    private boolean isSecondFriday(LocalDate date) {

        LocalDate firstDay = date.withDayOfMonth(1);
        LocalDate firstFriday = firstDay.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        LocalDate secondFriday = firstFriday.plusWeeks(1);

        return date.equals(secondFriday);
    }

    private void validatePartTimeClaims() {
        LocalDate today = LocalDate.now();
    
        // Only check claims after the 15th of the month
        if (today.getDayOfMonth() >= 15) {
            List<User> employees = getAllEmployees();
            for (User employee : employees) {
                if ("P".equals(payrollSystem.getRoleType(employee.getUserType()))) {
                    if (!employee.getCurrentClaim()) {
                        System.out.printf("Reminder: Part-time employee %s has not submitted a claim.%n", employee.getUsername());
                    }
                }
            }
        }
    }
    

    private void generatePayslips() {
        LocalDate today = LocalDate.now();
    
        List<User> employees = getAllEmployees();
        for (User employee : employees) {
            if ("P".equals(payrollSystem.getRoleType(employee.getUserType()))) {
                // Check if the employee has a valid claim
                LocalDate claimDate = claimSubmissionDates.get(employee.getUsername());
                if (employee.getCurrentClaim() && claimDate != null &&
                    claimDate.getMonth() == today.getMonth() &&
                    claimDate.getYear() == today.getYear()) {
                    payrollSystem.savePayslipToCSV(employee);
                    System.out.printf("Payslip generated for part-time employee: %s%n", employee.getUsername());
                } else {
                    System.out.printf("No payslip generated for %s. Claim not submitted or invalid.%n", employee.getUsername());
                }
            } else {
                // Generate payslip for non-part-time employees
                payrollSystem.savePayslipToCSV(employee);
                System.out.printf("Payslip generated for full-time/admin employee: %s%n", employee.getUsername());
            }
        }
    }
    
    


    private List<User> getAllEmployees() {
       return loginSystem.getUsers();
    }

    private void updateSalaryScalesForFullTimeStaff() {
        List<User> employees =  getAllEmployees();
        for (User employee : employees) {
            if ("F".equals(payrollSystem.getRoleType(employee.getUserType()))) {
                int currentScalePoint = employee.getScalePoint();

                if (currentScalePoint < 1) {
                    employee.setScalePoint(currentScalePoint + 1);
                    System.out.printf("Salary scale updated for: %s (New Scale Point: %d)%n", employee.getUsername(), currentScalePoint + 1);
                } else {
                    System.out.printf("Salary scale unchanged for: %s (Already at top scale point)%n", employee.getUsername());
                }
            }
        }
    }

    public void submitClaim(User employee) {
        if ("P".equals(payrollSystem.getRoleType(employee.getUserType()))) {
            LocalDate today = LocalDate.now();
    
            // Update claim status in the map
            claimSubmissionDates.put(employee.getUsername(), today);
            
    
        }
    }
    
    private void resetPartTimeClaims() {
        LocalDate today = LocalDate.now();
        if (today.getDayOfMonth() == 1) {
            claimSubmissionDates.clear(); // Clear all submission dates for the new month
    
            List<User> employees = getAllEmployees();
            for (User employee : employees) {
                if ("P".equals(payrollSystem.getRoleType(employee.getUserType()))) {
                    employee.setCurrentClaim(false);
                    
                }
            }
        }
    }
    
    
}

