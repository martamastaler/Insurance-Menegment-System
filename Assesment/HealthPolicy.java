package Assesment;

public class HealthPolicy extends Policy {

    private int age;
    private boolean preExisting;

    // Default constructor
    public HealthPolicy() {
        super();
        this.age = 0;
        this.preExisting = false;
    }

    // Parameterised constructor
    public HealthPolicy(String policyId, String providerName,
                        double coverageAmount, int age, boolean preExisting) {
        super(policyId, providerName, coverageAmount);
        setAge(age);
        setPreExisting(preExisting);
    }

    // Getters
    public int getAge() {
        return age;
    }

    public boolean isPreExisting() {
        return preExisting;
    }

    // Setters
    public void setAge(int age) {
        assert age >= 0 && age <= 120 : "Age must be between 0 and 120";
        this.age = age;
    }

    public void setPreExisting(boolean preExisting) {
        this.preExisting = preExisting;
    }

    // Monthly premium calculation
    @Override
    public double monthlyPremium() {
        double ageFactor;

        if (age < 40) {
            ageFactor = 1.0;
        } else if (age <= 59) {
            ageFactor = 1.5;
        } else {
            ageFactor = 2.0;
        }

        double premium = getCoverageAmount() * 0.005 * ageFactor;

        if (preExisting) {
            premium *= 1.20; // +20%
        }

        return premium;
    }

    // toString
    @Override
    public String toString() {
        return "Health Policy | " +
                super.toString() +
                ", Age: " + age +
                ", Pre-existing condition: " + (preExisting ? "Yes" : "No") +
                ", Monthly Premium: £" + String.format("%.2f", monthlyPremium());
    } 
    
}
