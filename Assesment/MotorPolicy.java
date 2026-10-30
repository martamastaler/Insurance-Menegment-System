package Assesment;

public class MotorPolicy extends Policy {

    private double vehicleValue;
    private int yearsNoClaims;

    // Default constructor
    public MotorPolicy() {
        super();
        this.vehicleValue = 0.0;
        this.yearsNoClaims = 0;
    }

    // Parameterised constructor
    public MotorPolicy(String policyId, String providerName,
                       double coverageAmount, double vehicleValue, int yearsNoClaims) {
        super(policyId, providerName, coverageAmount);
        setVehicleValue(vehicleValue);
        setYearsNoClaims(yearsNoClaims);
    }

    // Getters
    public double getVehicleValue() {
        return vehicleValue;
    }

    public int getYearsNoClaims() {
        return yearsNoClaims;
    }

    // Setters
    public void setVehicleValue(double vehicleValue) {
        assert vehicleValue >= 0 : "Vehicle value must not be negative";
        this.vehicleValue = vehicleValue;
    }

    public void setYearsNoClaims(int yearsNoClaims) {
        assert yearsNoClaims >= 0 : "Years of no claims must not be negative";
        this.yearsNoClaims = yearsNoClaims;
    }

    // Monthly premium calculation
    @Override
    public double monthlyPremium() {
        double premium = (getCoverageAmount() * 0.004) + (vehicleValue * 0.002);

        double discount = yearsNoClaims * 0.10;
        if (discount > 0.50) {
            discount = 0.50;
        }

        premium = premium * (1 - discount);
        return premium;
    }

    // toString
    @Override
    public String toString() {
        return "Motor Policy | " +
                super.toString() +
                ", Vehicle Value: £" + String.format("%.2f", vehicleValue) +
                ", Years No Claims: " + yearsNoClaims +
                ", Monthly Premium: £" + String.format("%.2f", monthlyPremium());
    }
}
