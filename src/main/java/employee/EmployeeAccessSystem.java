package employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAccessSystem {

    public String checkEligibility(Employee employee, String accessLevel)
            throws InvalidEmployeeException {

        validateInput(employee, accessLevel);

        List<String> reasons = new ArrayList<>();

        if (employee.getAge() < 21) {
            reasons.add("Employee must be at least 21 years old");
        }

        if (!isAuthorizedDepartment(employee.getDepartment())) {
            reasons.add("Department is not authorized");
        }

        if (!employee.getEmploymentType().equalsIgnoreCase("Active")) {
            reasons.add("Employee does not have active employment status");
        }

        if (!employee.isIdValid()) {
            reasons.add("Employee ID is invalid");
        }

        int requiredLevel = getRequiredClearanceLevel(accessLevel);
        int employeeLevel =
                getEmployeeClearanceLevel(employee.getSecurityClearance());

        if (employeeLevel < requiredLevel) {
            reasons.add("Insufficient security clearance for "
                    + accessLevel + " access");
        }

        if (!reasons.isEmpty()) {
            return "Not Eligible: " + String.join("; ", reasons);
        }

        if (employeeLevel >= requiredLevel) {
            return "Eligible";
        }

        return "Conditionally Eligible";
    }

    private boolean isAuthorizedDepartment(String department) {

        return department.equalsIgnoreCase("IT")
                || department.equalsIgnoreCase("HR")
                || department.equalsIgnoreCase("Finance")
                || department.equalsIgnoreCase("Administration");
    }

    private int getRequiredClearanceLevel(String accessLevel) {

        if (accessLevel.equalsIgnoreCase("Public")) {
            return 1;
        }

        if (accessLevel.equalsIgnoreCase("Internal")) {
            return 2;
        }

        return 3;
    }

    private int getEmployeeClearanceLevel(String clearance) {

        if (clearance.equalsIgnoreCase("Low")) {
            return 1;
        }

        if (clearance.equalsIgnoreCase("Medium")) {
            return 2;
        }

        if (clearance.equalsIgnoreCase("High")) {
            return 3;
        }

        return 0;
    }

    private void validateInput(Employee employee, String accessLevel)
            throws InvalidEmployeeException {

        if (employee == null) {
            throw new InvalidEmployeeException(
                    "Employee details cannot be null");
        }

        if (employee.getEmployeeId() == null
                || employee.getEmployeeId().trim().isEmpty()) {
            throw new InvalidEmployeeException(
                    "Employee ID cannot be empty");
        }

        if (employee.getName() == null
                || employee.getName().trim().isEmpty()) {
            throw new InvalidEmployeeException(
                    "Employee name cannot be empty");
        }

        if (employee.getAge() < 0) {
            throw new InvalidEmployeeException(
                    "Age cannot be negative");
        }

        if (employee.getDepartment() == null
                || employee.getDepartment().trim().isEmpty()) {
            throw new InvalidEmployeeException(
                    "Department cannot be empty");
        }

        if (employee.getEmploymentType() == null
                || employee.getEmploymentType().trim().isEmpty()) {
            throw new InvalidEmployeeException(
                    "Employment type cannot be empty");
        }

        if (employee.getSecurityClearance() == null
                || employee.getSecurityClearance().trim().isEmpty()) {
            throw new InvalidEmployeeException(
                    "Security clearance cannot be empty");
        }

        if (accessLevel == null
                || accessLevel.trim().isEmpty()) {
            throw new InvalidEmployeeException(
                    "Access level cannot be empty");
        }

        if (!(accessLevel.equalsIgnoreCase("Public")
                || accessLevel.equalsIgnoreCase("Internal")
                || accessLevel.equalsIgnoreCase("Confidential"))) {
            throw new InvalidEmployeeException(
                    "Invalid access level");
        }

        if (!(employee.getSecurityClearance().equalsIgnoreCase("Low")
                || employee.getSecurityClearance().equalsIgnoreCase("Medium")
                || employee.getSecurityClearance().equalsIgnoreCase("High"))) {
            throw new InvalidEmployeeException(
                    "Invalid security clearance level");
        }
    }
}
