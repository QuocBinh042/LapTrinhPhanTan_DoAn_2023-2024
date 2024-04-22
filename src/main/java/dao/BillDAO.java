package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import entity.Bill;
import entity.Customer;
import entity.DetailBill;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import service.BillService;

public class BillDAO extends UnicastRemoteObject implements BillService {
	public static final DecimalFormat FORMATMONEY = new DecimalFormat("###,###,### VNĐ");
	private EntityManager em;

	public BillDAO() throws RemoteException {
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	}

	public static void main(String[] args) throws RemoteException {
		BillDAO billDAO = new BillDAO();
		Employee employee = new Employee(0, "Tung", LocalDate.of(2003, 10, 10), true, "1234567890", "058203001143", "",
				"", true);
		Customer customer = new Customer(0, "Tung", "2234567890", "AAAA");
		Bill bill = new Bill(3, LocalDate.now(), LocalTime.now(), employee, customer, 0.0);

		try {
			billDAO.createBill(bill);
//			billDAO.getAllBills().forEach(e -> System.out.println(e));
//			billDAO.updateBillAfterPayment(bill);
//			billDAO.getBillsByYear(2023).forEach(e -> System.out.println(e));
//			billDAO.getBillsWithinDay().forEach(e -> System.out.println(e));
//			billDAO.getBillsByDay(12).forEach(e -> System.out.println(e));
//			billDAO.searchBills("", "K", "").forEach(e -> System.out.println(e));
//			System.out.println(FORMATMONEY.format(billDAO.calculateYearlyRevenue("2024")));
//			System.out.println(billDAO.calculateNumberOfBillsByYear("2024"));
//			billDAO.getBillsByTimeFrame(LocalDate.of(2023, 3, 3), LocalDate.of(2023, 9, 9)).forEach(e -> System.out.println(e));
//			System.out.println(billDAO.getBillsByDate(LocalDate.of(2023, 9, 1)));
//			System.out.println(billDAO.calculateTotalRevenue(LocalDate.of(2023, 3, 3), LocalDate.of(2023, 9, 9)));
//			System.out.println(billDAO.calculateNumberOfBills(LocalDate.of(2023, 3, 3), LocalDate.of(2023, 9, 9)));
//			System.out.println(billDAO.calculateNumberOfBillsByDate(LocalDate.of(2023, 9, 1)));
//			System.out.println(billDAO.calculateNumberOfBillsByMonth("9"));
//			billDAO.getMonthlyRevenueInRange(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 12)).forEach((k, v) -> System.out.println(k + " " + v));
//			billDAO.getYearlyRevenueTotal(2023).forEach((k, v) -> System.out.println(k + " " + v));
//			billDAO.getDailyRevenueForMonth(LocalDate.of(2023, 11, 1)).forEach((k, v) -> System.out.println(k + " " + v));
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
			if (em.find(Bill.class, bill.getId()) != null)
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
			if (em.find(Bill.class, bill.getId()) == null)
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
	public List<Bill> getBillsByYear(int year) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from Bill b where YEAR(b.paymentDate)  = :year", Bill.class)
				.setParameter("year", year).getResultList();
	}

	@Override
	public List<Bill> getBillsWithinDay() throws RemoteException {
		// TODO Auto-generated method stub
		return em
				.createQuery("select b from Bill b where b.paymentDate >= :currentDay and b.paymentDate < :tomorrow",
						Bill.class)
				.setParameter("currentDay", LocalDate.now().atStartOfDay().toLocalDate())
				.setParameter("tomorrow", LocalDate.now().atStartOfDay().toLocalDate().plusDays(1)).getResultList();
	}

	@Override
	public List<Bill> getBillsByMonth() throws RemoteException {
		LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
		LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

		return em.createQuery(
				"SELECT b FROM Bill b WHERE b.paymentDate >= :firstDayOfMonth AND b.paymentDate <= :lastDayOfMonth",
				Bill.class).setParameter("firstDayOfMonth", firstDayOfMonth)
				.setParameter("lastDayOfMonth", lastDayOfMonth).getResultList();
	}

	@Override
	public List<Bill> getBillsByYear() throws RemoteException {
		LocalDate firstDayOfYear = LocalDate.now().withDayOfYear(1);
		LocalDate lastDayOfYear = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());

		return em.createQuery(
				"SELECT b FROM Bill b WHERE b.paymentDate >= :firstDayOfYear AND b.paymentDate <= :lastDayOfYear",
				Bill.class).setParameter("firstDayOfYear", firstDayOfYear).setParameter("lastDayOfYear", lastDayOfYear)
				.getResultList();
	}

	@Override
	public List<Bill> searchBills(String billID, String employeeName, String phoneNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return em
				.createQuery("select b from Bill b "
						+ "where b.id like :id or b.employee.name like :name or b.customer.phoneNumber = :phoneNumber",
						Bill.class)
				.setParameter("id", billID).setParameter("name", employeeName).setParameter("phoneNumber", phoneNumber)
				.getResultList();
	}

	@Override
	public List<Bill> searchBillsByBillID(String id) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from Bill b where b.id = :id", Bill.class).setParameter("id", id)
				.getResultList();
	}

	@Override
	public Double calculateYearlyRevenue(String year) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select SUM(b.total) as revenue from Bill b where YEAR(b.paymentDate) = :year",
				Double.class).setParameter("year", year).getSingleResult();
	}

	@Override
	public long calculateNumberOfBillsByYear(String year) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select count(b) from Bill b where YEAR(b.paymentDate) = :year", Long.class)
				.setParameter("year", year).getSingleResult();
	}

	@Override
	public List<Integer> getBillYears() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bill> getBillsByTimeFrame(LocalDate startDate, LocalDate endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return em
				.createQuery(
						"select b from Bill b " + "where b.paymentDate >= :startDate and b.paymentDate <= :endDate",
						Bill.class)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
	}

	@Override
	public List<Bill> getBillsByDate(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from Bill b where b.paymentDate = :date", Bill.class).setParameter("date", date)
				.getResultList();
	}

	@Override
	public List<Bill> getBillsByDay(int day) throws RemoteException {
		// TODO Auto-generated method stub

		return em.createQuery("select b from Bill b where function('DAY', b.paymentDate) = :day", Bill.class)
				.setParameter("day", day).getResultList();
	}

	@Override
	public List<Bill> getBillsByMonth(int month) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from Bill b where function('MONTH', b.paymentDate) = :month", Bill.class)
				.setParameter("month", month).getResultList();
	}

	@Override
	public Double calculateTotalRevenue(LocalDate startDate, LocalDate endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return em
				.createQuery("select SUM(b.total) from Bill b "
						+ "where b.paymentDate >= :startDate and b.paymentDate <= :endDate", Double.class)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getSingleResult();
	}

	@Override
	public long calculateNumberOfBills(LocalDate startDate, LocalDate endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return em
				.createQuery("select COUNT(b) from Bill b "
						+ "where b.paymentDate >= :startDate and b.paymentDate <= :endDate", Long.class)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getSingleResult();
	}

	@Override
	public long calculateNumberOfBillsByDate(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select COUNT(b) from Bill b " + "where b.paymentDate = :date", Long.class)
				.setParameter("date", date).getSingleResult();
	}

	@Override
	public long calculateNumberOfBillsByMonth(String month) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select COUNT(b) from Bill b " + "where MONTH(b.paymentDate) = :month", Long.class)
				.setParameter("month", month).getSingleResult();
	}

	@Override
	public Map<String, Double> getMonthlyRevenueInRange(LocalDate startDate, LocalDate endDate) throws RemoteException {
		// TODO Auto-generated method stub
		return em
				.createQuery("SELECT MONTH(b.paymentDate) as month, SUM(b.total) as total " + "FROM Bill b "
						+ "WHERE b.paymentDate >= :startDate and b.paymentDate <= :endDate "
						+ "GROUP BY MONTH(b.paymentDate)", Tuple.class)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList().stream()
				.collect(Collectors.toMap(t -> String.valueOf(t.get("month")),
						t -> ((Number) t.get("total")).doubleValue()));
	}

	@Override
	public Map<Integer, Double> getYearlyRevenueTotal(int year) throws RemoteException {
		// TODO Auto-generated method stub
		return em
				.createQuery("SELECT YEAR(b.paymentDate) as year, SUM(b.total) as total " + "FROM Bill b "
						+ "WHERE YEAR(b.paymentDate) = :year " + "GROUP BY YEAR(b.paymentDate)", Tuple.class)
				.setParameter("year", year).getResultList().stream().collect(Collectors
						.toMap(t -> ((Number) t.get("year")).intValue(), t -> ((Number) t.get("total")).doubleValue()));
	}

	@Override
	public Map<Integer, Double> getDailyRevenueForMonth(LocalDate month) throws RemoteException {
		// TODO Auto-generated method stub
		return em
				.createQuery("SELECT DAY(b.paymentDate) as day, SUM(b.total) as total " + "FROM Bill b "
						+ "WHERE MONTH(b.paymentDate) = :month " + "GROUP BY DAY(b.paymentDate)", Tuple.class)
				.setParameter("month", month.getMonthValue()).getResultList().stream().collect(Collectors
						.toMap(t -> ((Number) t.get("day")).intValue(), t -> ((Number) t.get("total")).doubleValue()));
	}

	@Override
	public List<Bill> getBillsForStatistics(LocalDate date, LocalDate date2) throws RemoteException {
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

	@Override
	public List<Bill> getBillsByDetailBillID(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from Bill b join b.detailBills db where db.id = :id", Bill.class)
				.setParameter("id", id).getResultList();
	}

}
