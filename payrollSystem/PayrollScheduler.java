package payrollSystem;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.*;

/**
 * A Payroll Scheduler Object <br>
 * 
 * A <code>PayrollScheduler</code> object contains the parameters and functionality of
 * the payroll scheduler (which schedules payroll tasks) used in a payroll system at the University of Limerick
 * 
 * @author Benjamin Curran
 */
public class PayrollScheduler {
    private final PayrollSystem payrollSystem;
    private final ScheduledExecutorService scheduler;
    private final LoginSystem loginSystem;
    private final Map<String, LocalDate> claimSubmissionDates = new HashMap<>();


    /**
     * PayrollScheduler constructor
     * @param payrollSystem - the PayrollSystem class
     * @param loginSystem - the LoginSystem class
     */
    public PayrollScheduler(PayrollSystem payrollSystem, LoginSystem loginSystem) {
        this.payrollSystem = payrollSystem;
        this.loginSystem = loginSystem;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        
    }

    /**
     * Method to start scheduler
     */
    public void start() {
        // Schedule tasks to run daily
        Runnable task = this::processPayrollTasks;
        scheduler.scheduleAtFixedRate(task, 0, 24, TimeUnit.HOURS);
    }

    /**
     * Method to process all payroll tasks that are scheduled
     */
    private void processPayrollTasks() {
        LocalDate today = LocalDate.now();
            
        resetPartTimeClaims();

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

    /**
     * Boolean method for checking if it is currently before the second Friday of the month
     * @param date - current date
     * @return True if date is before second Friday, False otherwise
     */
    private boolean isBeforeSecondFriday(LocalDate date) {
        LocalDate firstDay = date.withDayOfMonth(1);
        LocalDate firstFriday = firstDay.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        LocalDate secondFriday = firstFriday.plusWeeks(1);

    
        return !date.isAfter(secondFriday);
    }

    
    /**
     * Method for automatically generating payslips
     */
    private void generatePayslips() {
        LocalDate today = LocalDate.now();
    
        List<User> employees = getAllEmployees();
        for (User employee : employees) {
            if ("P".equals(payrollSystem.getRoleType(employee.getUserType()))) {
                // Check if the employee has a valid claim
                LocalDate claimDate = claimSubmissionDates.get(employee.getUsername());
                if (employee.getCurrentClaim() && claimDate != null &&
                    claimDate.getMonth() == today.getMonth() &&
                    claimDate.getYear() == today.getYear() &&
                    isBeforeSecondFriday(claimDate)) {
                    payrollSystem.savePayslipToCSV(employee);
                   
                    } 
                } else {
                // Generate payslip for non-part-time employees
                payrollSystem.savePayslipToCSV(employee);
                
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

