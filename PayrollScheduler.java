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

    public void stop() {
        scheduler.shutdown();
        System.out.println("Scheduler stopped.");
    }

    

    
}

