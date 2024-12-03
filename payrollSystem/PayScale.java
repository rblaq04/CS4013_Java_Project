
import java.util.HashMap;
import java.util.Map;

class PayScale {
    private Map<Integer, Double> scalePoints; 
    private String description;

    public PayScale(String description) {
        this.scalePoints = new HashMap<>();
        this.description = description;
    }

    public void addScalePoint(int point, double annualRate) {
        scalePoints.put(point, annualRate);
    }

    public Double getAnnualRate(int point) {
        return scalePoints.get(point);
    }

    public String getDescription() {
        return description;
    }

    public Map<Integer, Double> getScalePoints() {
        return scalePoints;
    }

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
