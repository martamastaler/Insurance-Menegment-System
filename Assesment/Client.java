package Assesment;

public class Client implements Assessable {

    private String clientId;
    private String clientName;
    private double monthlyIncome;
    private Policy[] policies;
    private int policyCount;

    // Default constructor
    public Client() {
        this.clientId = "CL-0000";
        this.clientName = "Unknown";
        this.monthlyIncome = 0.0;
        this.policies = new Policy[5];
        this.policyCount = 0;
    }

    // Parameterised constructor
    public Client(String clientId, String clientName, double monthlyIncome) {
        setClientId(clientId);
        setClientName(clientName);
        setMonthlyIncome(monthlyIncome);
        this.policies = new Policy[5];
        this.policyCount = 0;
    }

    // Getters
    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public Policy[] getPolicies() {
        return policies;
    }

    public int getPolicyCount() {
        return policyCount;
    }

    // Setters
    public void setClientId(String clientId) {
        if (clientId == null || !clientId.matches("CL-\\d{4}")) {
            throw new IllegalArgumentException("Client ID must be in format CL-XXXX");
        }
        this.clientId = clientId;
    }

    public void setClientName(String clientName) {
        if (clientName == null || clientName.trim().isEmpty()) {
            this.clientName = "Unknown";
        } else {
            this.clientName = clientName;
        }
    }

    public void setMonthlyIncome(double monthlyIncome) {
        assert monthlyIncome >= 0 : "Monthly income must not be negative";
        this.monthlyIncome = monthlyIncome;
    }

    // total premium
    public double getTotalMonthlyPremium() {
        double total = 0.0;

        for (int i = 0; i < policyCount; i++) {
            try {
                total += policies[i].monthlyPremium();
            } catch (ArithmeticException e) {
                System.out.println("Arithmetic error while calculating premium: " + e.getMessage());
            }
        }

        return total;
    }

    // Helper method: premium limit
    public double getPremiumLimit() {
        return monthlyIncome * 0.40;
    }

    // Validate client ID
    public void validateClientId() throws Exception {
        if (clientId == null || !clientId.matches("CL-\\d{4}")) {
            throw new Exception("Invalid client ID format. Must be CL-XXXX");
        }
    }

    // Check duplicate policy ID
    public boolean hasDuplicatePolicyId(String policyId) {
        for (int i = 0; i < policyCount; i++) {
            if (policies[i].getPolicyId().equals(policyId)) {
                return true;
            }
        }
        return false;
    }

    // Add policy
    public void addPolicy(Policy policy) throws Exception {
        if (policy == null) {
            throw new Exception("Policy cannot be null.");
        }

        if (!policy.getPolicyId().matches("PL-\\d{4}")) {
            throw new Exception("Invalid policy ID format. Must be PL-XXXX");
        }

        if (hasDuplicatePolicyId(policy.getPolicyId())) {
            throw new Exception("Duplicate policy ID.");
        }

        if (policyCount >= policies.length) {
            throw new Exception("A client may hold at most 5 active policies.");
        }

        if (monthlyIncome <= 0) {
            throw new Exception("Coverage rule check failed: Income must be positive.");
        }

        double newTotal = getTotalMonthlyPremium() + policy.monthlyPremium();
        if (newTotal > getPremiumLimit()) {
            throw new Exception("Coverage rule violated - total premium would exceed 40% of monthly income.");
        }

        policies[policyCount] = policy;
        policyCount++;
    }

    // Remove policy by index
    public void removePolicy(int index) {
        if (index < 0 || index >= policyCount) {
            throw new ArrayIndexOutOfBoundsException("Invalid policy index.");
        }

        for (int i = index; i < policyCount - 1; i++) {
            policies[i] = policies[i + 1];
        }

        policies[policyCount - 1] = null;
        policyCount--;
    }

    // Remove policy by ID
    public void removePolicyById(String policyId) throws Exception {
        int index = findPolicyIndexById(policyId);

        if (index == -1) {
            throw new Exception("Policy not found.");
        }

        removePolicy(index);
    }

    // Find policy index by ID
    public int findPolicyIndexById(String policyId) {
        for (int i = 0; i < policyCount; i++) {
            if (policies[i].getPolicyId().equals(policyId)) {
                return i;
            }
        }
        return -1;
    }

    // Print all policies
    public void printPolicies() {
        if (policyCount == 0) {
            System.out.println("No policies found.");
            return;
        }

        System.out.printf("%-10s %-10s %-15s %-15s %-15s%n",
                "PolicyID", "Type", "Provider", "Coverage", "MonthlyPremium");

        for (int i = 0; i < policyCount; i++) {
            Policy p = policies[i];
            String type = p.getClass().getSimpleName().replace("Policy", "");

            System.out.printf("%-10s %-10s %-15s £%-14.2f £%-14.2f%n",
                    p.getPolicyId(),
                    type,
                    p.getProviderName(),
                    p.getCoverageAmount(),
                    p.monthlyPremium());
        }
    }

    // Interface method
    @Override
    public boolean isWithinCoverageLimit() {
        return policyCount <= 5 && getTotalMonthlyPremium() <= getPremiumLimit();
    }

    // Interface method
    @Override
    public void printSummary() {
        System.out.printf("Client: %s %s Income: £%.2f/month Policies: %d/5%n",
                clientId, clientName, monthlyIncome, policyCount);

        System.out.printf("Within coverage limit - %s (Total premium: £%.2f / Limit: £%.2f)%n",
                isWithinCoverageLimit() ? "YES" : "NO",
                getTotalMonthlyPremium(),
                getPremiumLimit());
    }

    // toString
    @Override
    public String toString() {
        return "Client ID: " + clientId +
                ", Name: " + clientName +
                ", Monthly Income: £" + String.format("%.2f", monthlyIncome) +
                ", Policies: " + policyCount + "/5";
    }
}   
