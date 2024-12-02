import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
class RoleFactory {
    private Map<String, PayScale> fullTimeRoles = new HashMap<>();
    private Map<String, PayScale> partTimeRoles = new HashMap<>();;

    public RoleFactory(){
    }

    public void loadPayScalesFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String description = parts[0].trim();
                    String scalePointStr = parts[1].trim();
                    String annualRateStr = parts[2].trim();

                    int scalePoint = scalePointStr.isEmpty() ? 0 : Integer.parseInt(scalePointStr);
                    double annualRate = Double.parseDouble(annualRateStr);

                    if (description.toUpperCase().contains("PART-TIME")) {
                        partTimeRoles.computeIfAbsent(description, PayScale::new).addScalePoint(scalePoint, annualRate);
                    } else {
                        fullTimeRoles.computeIfAbsent(description, PayScale::new).addScalePoint(scalePoint, annualRate);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading pay scales from CSV: " + e.getMessage());
        }
    }

     public void savePayScalesToCSV(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String description : fullTimeRoles.keySet()) {
                PayScale payScale = fullTimeRoles.get(description);
                for (Map.Entry<Integer, Double> entry : payScale.getScalePoints().entrySet()) {
                    int scalePoint = entry.getKey();
                    double annualRate = entry.getValue();
                    bw.write(description + "," + scalePoint + "," + annualRate);
                    bw.newLine();
                }
            }

           
            for (String description : partTimeRoles.keySet()) {
                PayScale payScale = partTimeRoles.get(description);
                for (Map.Entry<Integer, Double> entry : payScale.getScalePoints().entrySet()) {
                    int scalePoint = entry.getKey();
                    double annualRate = entry.getValue();
                    bw.write(description + "," + scalePoint + "," + annualRate);
                    bw.newLine();
                }
            }

            System.out.println("Pay scales successfully saved to CSV.");
        } catch (IOException e) {
            System.err.println("Error saving pay scales to CSV: " + e.getMessage());
        }
    }


    public void addRole() {
        
        Scanner in = new Scanner(System.in);
        System.out.println("Add F)ull-time role or P)art-time role?");
        String command = in.nextLine().toUpperCase();
        if (command.equals("F") || command.equals("P")) {

            System.out.println("Enter Role Name:");
            String description = in.nextLine();
            PayScale payScale = new PayScale(description);
            
            

            int scale = 1;
            while (true) {
                System.out.println("A)dd scale point or F)inish?");
                String choice = in.nextLine().toUpperCase();

                if (choice.equals("F")) {
                    break;
                } else if (choice.equals("A")) {
                    if (command.equals("F")) {
                    System.out.println("Enter annual rate for Scale Point " + scale + ":");
                    } else if (command.equals("P")) {
                        System.out.println("Enter hourly rate for Scale Point " + scale + ":");
                    }
                    try {
                        double annualRate = Double.parseDouble(in.nextLine());
                        payScale.addScalePoint(scale, annualRate);
                        scale++;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid rate. Please enter a number.");
                    }
                }
            }

            if (command.equals("F")) {
                fullTimeRoles.put(description, payScale);
                System.out.println("Full-time role added successfully.");
            } else {
                partTimeRoles.put(description, payScale);
                System.out.println("Part-time role added successfully.");
            }
        } else {
            System.out.println("Invalid choice. Please choose F or P.");
        }
    }

    public PayScale getPayScale(String name) {
        if (fullTimeRoles.containsKey(name)) {
            return fullTimeRoles.get(name);
        } else if (partTimeRoles.containsKey(name)) {
            return partTimeRoles.get(name);
        } else {
            return null;
        }
    }

    public String getRoleType(String name) {
        if (fullTimeRoles.containsKey(name)) {
            return "F";
        } else if (partTimeRoles.containsKey(name)) {
            return "P";
        } else {
            return null;
        }
    }

    public void displayRoles() {
        System.out.println("Full-Time Roles:");
        for(String description : fullTimeRoles.keySet()){
            System.out.println(fullTimeRoles.get(description).toString());
            }
        

        System.out.println("Part-Time Roles:");
        for(String description : partTimeRoles.keySet()){
            System.out.println(partTimeRoles.get(description).toString());
            }
    
    }
}
