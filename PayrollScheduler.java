import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.*;

public class PayrollScheduler {
    private final PayrollSystem payrollSystem;
    private final ScheduledExecutorService scheduler;
    private final LoginSystem loginSystem;

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

    

    private void generatePayslips() {
        List<User> employees = getAllEmployees(); 
        for (User employee : employees) {
                payrollSystem.savePayslipToCSV(employee);
            
        }
    }


    private List<User> getAllEmployees() {
       return loginSystem.getUsers();
    }

    private void updateSalaryScalesForFullTimeStaff() {
        List<User> employees =  getAllEmployees();
        for (User employee : employees) {
            if ("F".equals(payrollSystem.getRoleType(employee.getRole()))) {
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

    

    
}

