package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dao.CustomerDAO;
import entity.Customer;

public class CustomerTestDAO {
    private static CustomerDAO customerDAO;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        customerDAO = new CustomerDAO();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        customerDAO = null;
    }

    @Test
    public void testAddCustomer() throws RemoteException {
        Customer c = new Customer(22, "acb", "1234567", " ");
        boolean isCustomerAdded = customerDAO.addCustomer(c);
        assertEquals(true, isCustomerAdded);
    }

    @Test
    public void testUpdateCustomer() throws RemoteException {
        Customer c = new Customer(22, "acb", "1234567", " ");
        boolean isCustomerUpdated = customerDAO.updateCustomer(c);
        assertEquals(true, isCustomerUpdated);
    }

    @Test
    public void testGetAllCustomers() throws RemoteException {
        List<Customer> customers = customerDAO.getAllCustomers();
        customers.forEach(System.out::println);
    }

    @Test
    public void testDeleteCustomer() throws RemoteException {
        boolean isCustomerDeleted = customerDAO.deleteCustomer(22);
        assertEquals(true, isCustomerDeleted);
    }

    @Test
    public void testFindCustomersByPhoneNumber() throws RemoteException {
        List<Customer> customers = customerDAO.findCustomersByPhoneNumber("1234");
        customers.forEach(System.out::println);
    }
}
