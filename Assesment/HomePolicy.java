package Assesment;

public class HomePolicy extends Policy {

    private double propertySqm;
    private boolean floodRisk;

    // Default constructor
    public HomePolicy() {
        super();
        this.propertySqm = 0.0;
        this.floodRisk = false;
    }

    // Parameterised constructor
    public HomePolicy(String policyId, String providerName,
                      double coverageAmount, double propertySqm, boolean floodRisk) {
        super(policyId, providerName, coverageAmount);
        setPropertySqm(propertySqm);
        setFloodRisk(floodRisk);
    }

    // Getters
    public double getPropertySqm() {
        return propertySqm;
    }

    public boolean isFloodRisk() {
        return floodRisk;
    }

    // Setters
    public void setPropertySqm(double propertySqm) {
        assert propertySqm > 0 : "Property size must be positive";
        this.propertySqm = propertySqm;
    }

    public void setFloodRisk(boolean floodRisk) {
        this.floodRisk = floodRisk;
    }

    // Monthly premium calculation
    @Override
    public double monthlyPremium() {
        double premium = 0.0;

        try {
            premium = getCoverageAmount() * 0.003 * (propertySqm / 50.0);

            if (floodRisk) {
                premium *= 1.30; // +30%
            }
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic error during premium calculation: " + e.getMessage());
        }

        return premium;
    }

    // toString
    @Override
    public String toString() {
        return "Home Policy | " +
                super.toString() +
                ", Property Size: " + String.format("%.2f", propertySqm) + " sqm" +
                ", Flood Risk: " + (floodRisk ? "Yes" : "No") +
                ", Monthly Premium: £" + String.format("%.2f", monthlyPremium());
    }
    
}
