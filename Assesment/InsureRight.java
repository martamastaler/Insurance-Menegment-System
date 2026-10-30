package Assesment;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InsureRight {

    private static final int MAX_CLIENTS = 100;
    private static Client[] clients = new Client[MAX_CLIENTS];
    private static int clientCount = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice = 0;

        do {
            try {
                printMenu();
                choice = readMenuChoice();

                switch (choice) {
                    case 1:
                        registerNewClient();
                        break;
                    case 2:
                        addPolicyToClient();
                        break;
                    case 3:
                        removePolicyFromClient();
                        break;
                    case 4:
                        printSingleClient();
                        break;
                    case 5:
                        printAllClients();
                        break;
                    case 6:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid menu option. Please choose 1 to 6.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Exception: " + e);
                System.out.println("Please enter a valid menu number.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                System.out.println("Returning to menu.");
            }

        } while (choice != 6);

        scanner.close();
    }

    public static void printMenu() {
        System.out.println("\n     INSURERIGHT MENU     ");
        System.out.println("1. Register a new client");
        System.out.println("2. Add a policy to a client");
        System.out.println("3. Remove a policy from a client");
        System.out.println("4. Print a client's details");
        System.out.println("5. Print all clients and their policies");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    public static int readMenuChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static String readNonEmptyString(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    throw new NumberFormatException("Input cannot be empty.");
                }

                return input;

            } catch (NumberFormatException e) {
                System.out.println("Exception: " + e);
                System.out.println("Please enter a valid text value.");
            }
        }
    }

    public static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;

            } catch (InputMismatchException e) {
                System.out.println("Exception: " + e);
                System.out.println("Please enter a valid whole number.");
                scanner.nextLine();
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                double value = Double.parseDouble(input);
                return value;

            } catch (NumberFormatException e) {
                System.out.println("Exception: " + e);
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (true/false): ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("true")) {
                return true;
            } else if (input.equals("false")) {
                return false;
            } else {
                System.out.println("Please enter only true or false.");
            }
        }
    }

    public static Client findClientById(String clientId) {
        for (int i = 0; i < clientCount; i++) {
            if (clients[i].getClientId().equals(clientId)) {
                return clients[i];
            }
        }
        return null;
    }

    public static boolean isDuplicateClientId(String clientId) {
        return findClientById(clientId) != null;
    }

    public static void registerNewClient() {
        try {
            if (clientCount >= MAX_CLIENTS) {
                System.out.println("Client storage is full. Cannot add more clients.");
                return;
            }

            String clientId = readNonEmptyString("Enter client ID (CL-XXXX): ");

            if (!clientId.matches("CL-\\d{4}")) {
                throw new Exception("Invalid client ID format. Must be CL-XXXX");
            }

            if (isDuplicateClientId(clientId)) {
                throw new Exception("Duplicate client ID.");
            }

            String clientName = readNonEmptyString("Enter client name: ");

            double monthlyIncome;
            while (true) {
                monthlyIncome = readDouble("Enter monthly income: ");
                if (monthlyIncome <= 0) {
                    System.out.println("Exception: Coverage rule check failed: Income must be positive.");
                    System.out.println("Please try again.");
                } else {
                    break;
                }
            }

            Client client = new Client(clientId, clientName, monthlyIncome);
            client.validateClientId();

            clients[clientCount] = client;
            clientCount++;

            System.out.println("Client registered successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    public static void addPolicyToClient() {
        try {
            String clientId = readNonEmptyString("Enter client ID: ");
            Client client = findClientById(clientId);

            if (client == null) {
                throw new Exception("Client not found.");
            }

            System.out.println("Select policy type:");
            System.out.println("1. Health Policy");
            System.out.println("2. Motor Policy");
            System.out.println("3. Home Policy");
            int type = readInt("Enter option: ");

            String policyId = readNonEmptyString("Enter policy ID (PL-XXXX): ");
            String providerName = readNonEmptyString("Enter provider name: ");
            double coverageAmount = readDouble("Enter coverage amount: ");

            Policy policy = null;

            switch (type) {
                case 1: {
                    int age = readInt("Enter age: ");
                    boolean preExisting = readBoolean("Has pre-existing condition?");
                    policy = new HealthPolicy(policyId, providerName, coverageAmount, age, preExisting);
                    break;
                }
                case 2: {
                    double vehicleValue = readDouble("Enter vehicle value: ");
                    int yearsNoClaims = readInt("Enter years of no claims: ");
                    policy = new MotorPolicy(policyId, providerName, coverageAmount, vehicleValue, yearsNoClaims);
                    break;
                }
                case 3: {
                    double propertySqm = readDouble("Enter property size in sqm: ");
                    boolean floodRisk = readBoolean("Is the property at flood risk?");
                    policy = new HomePolicy(policyId, providerName, coverageAmount, propertySqm, floodRisk);
                    break;
                }
                default:
                    System.out.println("Invalid policy type.");
                    return;
            }

            System.out.println("Adding policy to client " + clientId + "...");
            client.addPolicy(policy);
            System.out.println("Policy added successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Returning to menu.");
        } catch (ArithmeticException e) {
            System.out.println("Exception: Arithmetic error during premium calculation.");
            System.out.println("Returning to menu.");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Returning to menu.");
        }
    }

    public static void removePolicyFromClient() {
        try {
            String clientId = readNonEmptyString("Enter client ID: ");
            Client client = findClientById(clientId);

            if (client == null) {
                throw new Exception("Client not found.");
            }

            if (client.getPolicyCount() == 0) {
                System.out.println("This client has no policies to remove.");
                return;
            }

            client.printSummary();
            client.printPolicies();

            System.out.println("Remove by:");
            System.out.println("1. Policy index");
            System.out.println("2. Policy ID");
            int option = readInt("Enter option: ");

            if (option == 1) {
                int index = readInt("Enter policy index: ");
                client.removePolicy(index);
                System.out.println("Policy removed successfully.");
            } else if (option == 2) {
                String policyId = readNonEmptyString("Enter policy ID: ");
                client.removePolicyById(policyId);
                System.out.println("Policy removed successfully.");
            } else {
                System.out.println("Invalid option.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Returning to menu.");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println("Returning to menu.");
        }
    }

    public static void printSingleClient() {
        int processed = 0;

        try {
            String clientId = readNonEmptyString("Enter client ID: ");
            Client client = findClientById(clientId);

            if (client == null) {
                throw new Exception("Client not found.");
            }

            client.printSummary();
            client.printPolicies();
            processed = 1;

        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            System.out.println("Summary line: Total number of clients processed = " + processed);
        }
    }

    public static void printAllClients() {
        int processed = 0;

        try {
            if (clientCount == 0) {
                System.out.println("No clients registered.");
                return;
            }

            for (int i = 0; i < clientCount; i++) {
                clients[i].printSummary();
                clients[i].printPolicies();
                System.out.println();
                processed++;
            }

        } catch (Exception e) {
            System.out.println("Exception while printing report: " + e.getMessage());
        } finally {
            System.out.println("Summary line: Total number of clients processed = " + processed);
        }
    }
}
