package employee;

import static org.junit.Assert.*;
import org.junit.Test;

public class EmployeeAccessSystemTest {

    EmployeeAccessSystem system = new EmployeeAccessSystem();

    @Test
    public void testEligibleEmployee() throws InvalidEmployeeException {
        Employee emp = new Employee(
                "E101", "Ravi", 25, "IT",
                "Active", "High", true);

        String result = system.checkEligibility(emp, "Confidential");

        assertEquals("Eligible", result);
    }

    @Test
    public void testBoundaryAge21() throws InvalidEmployeeException {
        Employee emp = new Employee(
                "E102", "Arun", 21, "HR",
                "Active", "High", true);

        String result = system.checkEligibility(emp, "Internal");

        assertEquals("Eligible", result);
    }

    @Test
    public void testAgeBelow21() throws InvalidEmployeeException {
        Employee emp = new Employee(
                "E103", "Kiran", 20, "IT",
                "Active", "High", true);

        String result = system.checkEligibility(emp, "Internal");

        assertTrue(result.contains(
                "Employee must be at least 21 years old"));
    }

    @Test
    public void testAuthorizedDepartment()
            throws InvalidEmployeeException {

        Employee emp = new Employee(
                "E104", "Suresh", 30, "Finance",
                "Active", "High", true);

        String result = system.checkEligibility(emp, "Internal");

        assertEquals("Eligible", result);
    }

    @Test
    public void testUnauthorizedDepartment()
            throws InvalidEmployeeException {

        Employee emp = new Employee(
                "E105", "Rahul", 30, "Sales",
                "Active", "High", true);

        String result = system.checkEligibility(emp, "Internal");

        assertTrue(result.contains(
                "Department is not authorized"));
    }

    @Test
    public void testInactiveEmployee()
            throws InvalidEmployeeException {

        Employee emp = new Employee(
                "E106", "Vijay", 30, "IT",
                "Inactive", "High", true);

        String result = system.checkEligibility(emp, "Internal");

        assertTrue(result.contains(
                "Employee does not have active employment status"));
    }

    @Test
    public void testInvalidEmployeeId()
            throws InvalidEmployeeException {

        Employee emp = new Employee(
                "E107", "Ajay", 30, "IT",
                "Active", "High", false);

        String result = system.checkEligibility(emp, "Internal");

        assertTrue(result.contains(
                "Employee ID is invalid"));
    }

    @Test
    public void testConfidentialAccessWithHighClearance()
            throws InvalidEmployeeException {

        Employee emp = new Employee(
                "E108", "Manoj", 30, "IT",
                "Active", "High", true);

        String result = system.checkEligibility(emp, "Confidential");

        assertEquals("Eligible", result);
    }

    @Test
    public void testConditionalEligibility()
            throws InvalidEmployeeException {

        Employee emp = new Employee(
                "E109", "Karthik", 30, "IT",
                "Active", "Medium", true);

        String result = system.checkEligibility(emp, "Confidential");

        assertEquals("Conditionally Eligible", result);
    }

    @Test
    public void testInsufficientClearance()
            throws InvalidEmployeeException {

        Employee emp = new Employee(
                "E110", "Ramesh", 30, "IT",
                "Active", "Low", true);

        String result = system.checkEligibility(emp, "Confidential");

        assertTrue(result.contains(
                "Insufficient security clearance"));
    }

    @Test
    public void testMultipleFailures()
            throws InvalidEmployeeException {

        Employee emp = new Employee(
                "E111", "Sanjay", 18, "Sales",
                "Inactive", "Low", false);

        String result = system.checkEligibility(emp, "Confidential");

        assertTrue(result.contains(
                "Employee must be at least 21 years old"));

        assertTrue(result.contains(
                "Department is not authorized"));

        assertTrue(result.contains(
                "Employee does not have active employment status"));

        assertTrue(result.contains(
                "Employee ID is invalid"));

        assertTrue(result.contains(
                "Insufficient security clearance"));
    }

    @Test
    public void testInvalidEmployeeIdInput() {

        Employee emp = new Employee(
                "", "Test", 25, "IT",
                "Active", "High", true);

        try {
            system.checkEligibility(emp, "Internal");
            fail("Expected InvalidEmployeeException");
        } catch (InvalidEmployeeException e) {
            assertEquals(
                    "Employee ID cannot be empty",
                    e.getMessage());
        }
    }

    @Test
    public void testNegativeAgeInput() {

        Employee emp = new Employee(
                "E112", "Test", -5, "IT",
                "Active", "High", true);

        try {
            system.checkEligibility(emp, "Internal");
            fail("Expected InvalidEmployeeException");
        } catch (InvalidEmployeeException e) {
            assertEquals(
                    "Age cannot be negative",
                    e.getMessage());
        }
    }

    @Test
    public void testInvalidAccessLevel() {

        Employee emp = new Employee(
                "E113", "Test", 25, "IT",
                "Active", "High", true);

        try {
            system.checkEligibility(emp, "TopSecret");
            fail("Expected InvalidEmployeeException");
        } catch (InvalidEmployeeException e) {
            assertEquals(
                    "Invalid access level",
                    e.getMessage());
        }
    }

    @Test
    public void testInvalidClearanceLevel() {

        Employee emp = new Employee(
                "E114", "Test", 25, "IT",
                "Active", "Extreme", true);

        try {
            system.checkEligibility(emp, "Internal");
            fail("Expected InvalidEmployeeException");
        } catch (InvalidEmployeeException e) {
            assertEquals(
                    "Invalid security clearance level",
                    e.getMessage());
        }
    }
}
