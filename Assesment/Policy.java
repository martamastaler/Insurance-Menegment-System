package Assesment;

public abstract class Policy {
    private String policyId;
    private String providerName;
    private double coverageAmount;
    
     public Policy() {
        this.policyId = "PL-0000";
        this.providerName = "Unknown";
        this.coverageAmount = 0.0;
    }

    // Parameterised constructor
    public Policy(String policyId, String providerName, double coverageAmount) {
        setPolicyId(policyId);
        setProviderName(providerName);
        setCoverageAmount(coverageAmount);
    }

    // Getters
    public String getPolicyId() {
        return policyId;
    }

    public String getProviderName() {
        return providerName;
    }

    public double getCoverageAmount() {
        return coverageAmount;
    }

    // Setters
    public void setPolicyId(String policyId) {
        if (policyId == null || !policyId.matches("PL-\\d{4}")) {
            throw new IllegalArgumentException("Policy ID must be in format PL-XXXX");
        }
        this.policyId = policyId;
    }

    public void setProviderName(String providerName) {
        if (providerName == null || providerName.trim().isEmpty()) {
            this.providerName = "Unknown";
        } else {
            this.providerName = providerName;
        }
    }

    public void setCoverageAmount(double coverageAmount) {
        assert coverageAmount > 0 : "Coverage amount must be positive";
        this.coverageAmount = coverageAmount;
    }

    // Abstract method
    public abstract double monthlyPremium();

    // toString
    @Override
    public String toString() {
        return "Policy ID: " + policyId +
               ", Provider: " + providerName +
               ", Coverage Amount: £" + String.format("%.2f", coverageAmount);
    }
}

