package employee;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        EmployeeAccessSystem system = new EmployeeAccessSystem();

        System.out.print("Enter number of employees: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nEmployee " + i);

            System.out.print("Employee ID: ");
            String employeeId = scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Department: ");
            String department = scanner.nextLine();

            System.out.print("Employment Type (Active/Inactive): ");
            String employmentType = scanner.nextLine();

            System.out.print("Security Clearance (Low/Medium/High): ");
            String securityClearance = scanner.nextLine();

            System.out.print("Is Employee ID Valid? (true/false): ");
            boolean idValid = scanner.nextBoolean();
            scanner.nextLine();

            System.out.print(
                    "Requested Access (Public/Internal/Confidential): ");
            String accessLevel = scanner.nextLine();

            Employee employee = new Employee(
                    employeeId,
                    name,
                    age,
                    department,
                    employmentType,
                    securityClearance,
                    idValid
            );

            try {

                String result =
                        system.checkEligibility(employee, accessLevel);

                System.out.println("Result: " + result);

            } catch (InvalidEmployeeException e) {

                System.out.println("Input Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
