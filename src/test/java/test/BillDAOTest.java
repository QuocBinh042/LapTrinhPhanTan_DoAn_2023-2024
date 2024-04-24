package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dao.BillDAO;
import dao.EmployeeDAO;
import entity.Bill;

public class BillDAOTest {
	private static BillDAO billDao;
	@BeforeAll
    static void setUpBeforeClass() throws Exception {
		billDao = new BillDAO();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    	billDao = null;
    }
    
    @Test
    public void testGetBillsByYear1() throws RemoteException {
        List<Bill> bills = billDao.getBillsByYear(2023);
        bills.forEach(b -> {
        	assertEquals(b.getPaymentDate().getYear(), 2023);
        });
    }
    
    @Test
    public void testGetBillsWithinDay() throws RemoteException{
    	List<Bill> bills = billDao.getBillsWithinDay();
    	LocalDate date = LocalDate.now();
    	bills.forEach(b -> {
    		assertEquals(b.getPaymentDate(), date);
    	});
    }
    
    @Test
    public void testGetBillsByMonth() throws RemoteException{
    	List<Bill> bills = billDao.getBillsByMonth();
    	LocalDate date = LocalDate.now();
    	bills.forEach(b -> {
    		assertEquals(b.getPaymentDate().getMonthValue(), date.getMonthValue());
    	});
    }
    
    @Test
    public void testGetBillsByYear2() throws RemoteException{
    	List<Bill> bills = billDao.getBillsByYear();
    	LocalDate date = LocalDate.now();
    	bills.forEach(b -> {
    		assertEquals(b.getPaymentDate().getYear(), date.getYear());
    	});
    }
}
