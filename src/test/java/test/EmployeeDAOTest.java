package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dao.EmployeeDAO;
import entity.Employee;

public class EmployeeDAOTest {
	private static EmployeeDAO employeeDAO;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        employeeDAO = new EmployeeDAO();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    	employeeDAO = null;
    }
    
    @Test
    public void testGetAllEmployees() throws RemoteException {
        List<Employee> employees = employeeDAO.getAllEmployees();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }
    
    @Test
    public void testAddEmployee() throws RemoteException {
        Employee employee = new Employee(0, "Tung", LocalDate.of(2003, 10, 10), true, "1234567890", "058203001143", "", "123456789", true);
        assertTrue(employeeDAO.addEmployee(employee));
    }

    @Test
    public void testUpdateEmployee() throws RemoteException {
    	Employee employee = new Employee(21, "Tung", LocalDate.of(2003, 10, 10), true, "1234567890", "058203001143", "", "123456789", true);
        boolean result = employeeDAO.updateEmployee(employee);
        assertTrue(result);
    }

    @Test
    public void testUpdatePassword() throws RemoteException {
        String phoneNumber = "0386076296";
        String newPassword = "123456789";
        boolean result = employeeDAO.updatePassword(phoneNumber, newPassword);
        assertTrue(result);
    }

    @Test
    public void testDeleteEmployeeByID() throws RemoteException {
        assertFalse(employeeDAO.deleteEmployeeByID(3));
    }

    @Test
    public void testCheckAccount() throws RemoteException {
        String phoneNumber = "0386076296";
        String password = "123456789";
        Employee employee = employeeDAO.checkAccount(phoneNumber, password);
        assertNotNull(employee);
    }

    @Test
    public void testGetEmployeeByID() throws RemoteException {
        int employeeID = 1;
        Employee employee = employeeDAO.getEmployeeByID(employeeID);
        assertNotNull(employee);
    }

    @Test
    public void testGetEmployeesByPosition() throws RemoteException {
        String position = "Phục vụ";
        List<Employee> employees = employeeDAO.getEmployeesByPosition(position);
        assertNotNull(employees);
    }

    @Test
    public void testGetEmployeesByStatus() throws RemoteException {
        boolean status = true;
        List<Employee> employees = employeeDAO.getEmployeesByStatus(status);
        assertNotNull(employees);
    }
}
