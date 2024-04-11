package service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.Bill;

public interface HoaDonService {
	public boolean createInvoice(Bill hoaDon);

	public ArrayList<Bill> getAllInvoices();

	public boolean updateInvoiceAfterPayment(Bill hd);

	public ArrayList<Bill> getInvoicesByTimeFrame(Date ngayBatDau, Date ngayKetThuc);

	public ArrayList<Bill> getInvoicesByDate(Date date);

	public ArrayList<Bill> getInvoicesByDay(Date date);

	public ArrayList<Bill> getInvoicesByMonth(Date date);

	public ArrayList<Bill> getInvoicesByYear(int nam);

	public ArrayList<Bill> getInvoicesWithinDay();

	public ArrayList<Bill> getInvoicesByMonth();

	public ArrayList<Bill> getInvoicesByYear();

	public ArrayList<Bill> searchInvoices(String maHD, String tenNV, String sdtKhach);

	public ArrayList<Bill> searchInvoicesByInvoiceID(String maHD);

	public ArrayList<Bill> getInvoicesForStatistics(java.util.Date date, java.util.Date date2);

	public Double calculateDailyRevenue(java.util.Date date);

	public Double calculateMonthlyRevenue(java.util.Date date);

	public Double calculateYearlyRevenue(String yearString);

	public Double calculateTotalRevenue(Date ngayBatDau, Date ngayKetThuc);

	public int calculateNumberOfInvoices(Date ngayBatDau, Date ngayKetThuc);

	public int calculateNumberOfInvoicesByDate(Date date);

	public int calculateNumberOfInvoicesByMonth(Date date);

	public int calculateNumberOfInvoicesByYear(String yearString);

	public List<Integer> getInvoiceYears();

	public Map<String, Double> getMonthlyRevenueInRange(Date startDate, Date endDate);

	public Map<Integer, Double> getYearlyRevenueTotal(Integer year);

	public Map<Integer, Double> getDailyRevenueForMonth(Date month);

}
