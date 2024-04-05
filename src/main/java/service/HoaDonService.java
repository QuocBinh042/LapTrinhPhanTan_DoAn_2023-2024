package service;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.HoaDon;

public interface HoaDonService {
	public boolean createInvoice(HoaDon hoaDon);

	public ArrayList<HoaDon> getAllInvoices();

	public boolean updateInvoiceAfterPayment(HoaDon hd);

	public ArrayList<HoaDon> getInvoicesByTimeFrame(Date ngayBatDau, Date ngayKetThuc);

	public ArrayList<HoaDon> getInvoicesByDate(Date date);

	public ArrayList<HoaDon> getInvoicesByDay(Date date);

	public ArrayList<HoaDon> getInvoicesByMonth(Date date);

	public ArrayList<HoaDon> getInvoicesByYear(int nam);

	public ArrayList<HoaDon> getInvoicesWithinDay();

	public ArrayList<HoaDon> getInvoicesByMonth();

	public ArrayList<HoaDon> getInvoicesByYear();

	public ArrayList<HoaDon> searchInvoices(String maHD, String tenNV, String sdtKhach);

	public ArrayList<HoaDon> searchInvoicesByInvoiceID(String maHD);

	public ArrayList<HoaDon> getInvoicesForStatistics(java.util.Date date, java.util.Date date2);

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
