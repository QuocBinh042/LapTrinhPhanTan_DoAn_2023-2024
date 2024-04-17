package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import entity.Bill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import service.BillService;

public class BillDAO extends UnicastRemoteObject implements BillService{

	private EntityManager em;
	
	public BillDAO() throws RemoteException{
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	}
	
	public static void main(String[] args) throws RemoteException{
		BillDAO billDAO = new BillDAO();
		Bill bill = new Bill(3, LocalDate.now(), LocalTime.now(), null, null, 0.0);
		
		try {
//			billDAO.createBill(bill);
//			billDAO.getAllBills().forEach(e -> System.out.println(e));
//			billDAO.updateBillAfterPayment(bill);
//			billDAO.getBillsByYear(2023).forEach(e -> System.out.println(e));
//			billDAO.getBillsWithinDay().forEach(e -> System.out.println(e));
			billDAO.getBillsByDay(12).forEach(e -> System.out.println(e));
		} catch (RemoteException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean createBill(Bill bill) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if(em.find(Bill.class, bill.getId()) != null)
				return false;
	
			em.merge(bill);
			tx.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
		
		return false;
	}

	@Override
	public List<Bill> getAllBills() throws RemoteException {
		// TODO Auto-generated method stub
		return em.createNamedQuery("getAllBills", Bill.class).getResultList();
	}

	@Override
	public boolean updateBillAfterPayment(Bill bill) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if(em.find(Bill.class, bill.getId()) == null)
				return false;
			
			em.merge(bill);
			tx.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
		return false;
	}

	@Override
	public ArrayList<Bill> getBillsByYear(int year) throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<Bill>) em.createQuery("select b from Bill b where Function('Year', b.paymentDate)  = :year", Bill.class)
								   .setParameter("year", year)
								   .getResultList();
	}

	@Override
	public ArrayList<Bill> getBillsWithinDay() throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<Bill>) em.createQuery("select b from Bill b where b.paymentDate >= :currentDay and b.paymentDate < :tomorrow",
									Bill.class)
								   .setParameter("currentDay", LocalDate.now().atStartOfDay().toLocalDate())
								   .setParameter("tomorrow", LocalDate.now().atStartOfDay().toLocalDate().plusDays(1))
								   .getResultList();
	}

	@Override
	public ArrayList<Bill> getBillsByMonth() throws RemoteException {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public ArrayList<Bill> getBillsByYear() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Bill> searchBills(String maHD, String tenNV, String sdtKhach) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Bill> searchBillsByBillID(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<Bill>) em.createQuery("select b from Bill b where b.id = :id", Bill.class)
				   .setParameter("id", id)
				   .getResultList();
	}


	@Override
	public Double calculateYearlyRevenue(String year) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calculateNumberOfBillsByYear(String year) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> getBillYears() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Bill> getBillsByTimeFrame(LocalDate startDate, LocalDate endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Bill> getBillsByDate(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Bill> getBillsByDay(int day) throws RemoteException {
		// TODO Auto-generated method stub
		
		return (ArrayList<Bill>) em.createQuery("select b from Bill b where function('DAY', b.paymentDate) = :day", Bill.class)
				 .setParameter("day", day)
				 .getResultList();
	}

	@Override
	public ArrayList<Bill> getBillsByMonth(int month) throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<Bill>) em.createQuery("select b from Bill b where function('MONTH', b.paymentDate) = :month", Bill.class)
				 .setParameter("month", month)
				 .getResultList();
	}

	@Override
	public Double calculateTotalRevenue(LocalDate startDate, LocalDate endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int calculateNumberOfBills(LocalDate startDate, LocalDate endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int calculateNumberOfBillsByDate(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int calculateNumberOfBillsByMonth(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, Double> getMonthlyRevenueInRange(LocalDate startDate, LocalDate endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Double> getYearlyRevenueTotal(int year) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Double> getDailyRevenueForMonth(LocalDate month) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Bill> getBillsForStatistics(LocalDate date, LocalDate date2) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double calculateDailyRevenue(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double calculateMonthlyRevenue(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
