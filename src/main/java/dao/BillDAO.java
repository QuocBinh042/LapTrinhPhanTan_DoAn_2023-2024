package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import entity.Bill;
import entity.Customer;
import entity.DetailBill;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
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
			billDAO.searchBills("", "Trần Mạnh Công", "").forEach(e -> System.out.println(e));
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
//			System.out.println(billDAO.getBillsByYear());
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
		StringBuilder queryStringBuilder = new StringBuilder("SELECT b FROM Bill b WHERE ");
		Map<String, Object> parameters = new HashMap<>();

		if (billID != null && !billID.isEmpty()) {
			queryStringBuilder.append("b.id = :id");
			parameters.put("id", Integer.parseInt(billID));
		}

		if (employeeName != null && !employeeName.isEmpty()) {
			if (parameters.size() > 0) {
				queryStringBuilder.append(" AND ");
			}
			queryStringBuilder.append("b.employee.name LIKE :name");
			parameters.put("name", "%" + employeeName + "%");
		}

		if (phoneNumber != null && !phoneNumber.isEmpty()) {
			if (parameters.size() > 0) {
				queryStringBuilder.append(" AND ");
			}
			queryStringBuilder.append("b.customer.phoneNumber = :phoneNumber");
			parameters.put("phoneNumber", phoneNumber);
		}

		TypedQuery<Bill> query = em.createQuery(queryStringBuilder.toString(), Bill.class);
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.getResultList();
	}

	@Override
	public List<Bill> searchBillsByBillID(int id) throws RemoteException {
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
		try {
			return em.createQuery("SELECT DISTINCT YEAR(b.paymentDate) FROM Bill b", Integer.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
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
	public List<Bill> getBillsByMonth(LocalDate month) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from Bill b where MONTH(b.paymentDate) = :month", Bill.class)
				.setParameter("month", month.getMonthValue()).getResultList();
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
	public long calculateNumberOfBillsByMonth(LocalDate month) throws RemoteException {
		try {
			int monthValue = month.getMonthValue();
			int yearValue = month.getYear();
			return em
					.createQuery("select count(b) from Bill b " + "where function('MONTH', b.paymentDate) = :month "
							+ "and function('YEAR', b.paymentDate) = :year", Long.class)
					.setParameter("month", monthValue).setParameter("year", yearValue).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
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

//	@Override
//	public Map<Integer, Double> getYearlyRevenueTotal(int year) throws RemoteException {
//		// TODO Auto-generated method stub
//		return em
//				.createQuery("SELECT YEAR(b.paymentDate) as year, SUM(b.total) as total " + "FROM Bill b "
//						+ "WHERE YEAR(b.paymentDate) = :year " + "GROUP BY YEAR(b.paymentDate)", Tuple.class)
//				.setParameter("year", year).getResultList().stream().collect(Collectors
//						.toMap(t -> ((Number) t.get("year")).intValue(), t -> ((Number) t.get("total")).doubleValue()));
//	}

	@Override
	public Map<Integer, Double> getDailyRevenueForMonth(LocalDate month) throws RemoteException {
		// TODO Auto-generated method stub
		  Map<Integer, Double> monthlyTotal = new TreeMap<>();

		    try {
		        int year = month.getYear();
		        int monthValue = month.getMonthValue();
		        YearMonth yearMonth = YearMonth.from(month);
		        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
		            LocalDate date = LocalDate.of(year, monthValue, day);

		            Double total = em.createQuery(
		                    "SELECT SUM(b.total) FROM Bill b " +
		                            "WHERE b.paymentDate = :date", Double.class)
		                    .setParameter("date", date)
		                    .getSingleResult();

		            monthlyTotal.put(day, total != null ? total : 0.0);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return monthlyTotal;
	}
	
	@Override
	public Map<Integer, Double> getMonthlyRevenueInRange(LocalDate month) throws RemoteException {
	    Map<Integer, Double> monthlyTotal = new TreeMap<>();

	    try {
	        int year = month.getYear();
	        int monthValue = month.getMonthValue();
	        YearMonth yearMonth = YearMonth.from(month);
	        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
	            LocalDate date = LocalDate.of(year, monthValue, day);

	            Double total = em.createQuery(
	                    "SELECT SUM(b.total) FROM Bill b " +
	                            "WHERE b.paymentDate = :date", Double.class)
	                    .setParameter("date", date)
	                    .getSingleResult();

	            monthlyTotal.put(day, total != null ? total : 0.0);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return monthlyTotal;
	}



	@Override
	public Map<Integer, Double> getYearlyRevenueTotal(int year) throws RemoteException {
	    Map<Integer, Double> yearlyTotal = new HashMap<>();
	    try {
	        List<Object[]> resultList = em.createQuery(
	                "SELECT MONTH(b.paymentDate), SUM(b.total) " +
	                        "FROM Bill b " +
	                        "WHERE YEAR(b.paymentDate) = :year " +
	                        "GROUP BY MONTH(b.paymentDate)", Object[].class)
	                .setParameter("year", year)
	                .getResultList();

	        for (Object[] result : resultList) {
	            Integer month = (Integer) result[0];
	            Double total = (Double) result[1];
	            yearlyTotal.put(month, total);
	        }

	        for (int month = 1; month <= 12; month++) {
	            yearlyTotal.putIfAbsent(month, 0.0);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return yearlyTotal;
	}

	@Override
	public List<Bill> getBillsForStatistics(LocalDate date, LocalDate date2) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double calculateDailyRevenue(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT SUM(b.total) FROM Bill b WHERE b.total != 0 AND b.paymentDate = :date",
				Double.class).setParameter("date", date).getSingleResult();
	}

	@Override
	public Double calculateMonthlyRevenue(LocalDate date) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery(
				"SELECT SUM(b.total) FROM Bill b WHERE b.total != 0 AND MONTH(b.paymentDate) = MONTH(:date)",
				Double.class).setParameter("date", date).getSingleResult();

	}

	@Override
	public List<Bill> getBillsByDetailBillID(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from Bill b join b.detailBills db where db.id = :id", Bill.class)
				.setParameter("id", id).getResultList();
	}

	@Override
	public List<Bill> getBillsByBillID(int id) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from Bill b where b.id = :id", Bill.class)
				.setParameter("id", id).getResultList();
	}
}
