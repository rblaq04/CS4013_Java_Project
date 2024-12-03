package payrollSystem;
import java.util.HashMap;
import java.util.Map;

/**
 * A Pay Scale Object <br>
 * 
 * A <code>PayScale</code> object contains the parameters and functionality of
 * an employee pay scale at the University of Limerick
 * 
 * @author Benjamin Curran
 */
class PayScale {
    private Map<Integer, Double> scalePoints; 
    private String description;

    /**
     * PayScale constructor
     * @param description - name of the role
     */
    public PayScale(String description) {
        this.scalePoints = new HashMap<>();
        this.description = description;
    }

    /**
     * Method for adding a scale point
     * @param point - the scale point
     * @param annualRate - the annual income rate
     */
    public void addScalePoint(int point, double annualRate) {
        scalePoints.put(point, annualRate);
    }

    /**
     * Gets the annual rate for a given scale point
     * @param point - the scale point
     * @return the annual rate
     */
    public Double getAnnualRate(int point) {
        return scalePoints.get(point);
    }

    /**
     * Gets the name of the role
     * @return the name of the role
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the scale points for all the positions
     * @return the scale points
     */
    public Map<Integer, Double> getScalePoints() {
        return scalePoints;
    }

    /**
     * Returns a string with all the scale points
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getDescription()).append("\n");
        for (Map.Entry<Integer, Double> entry : scalePoints.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}