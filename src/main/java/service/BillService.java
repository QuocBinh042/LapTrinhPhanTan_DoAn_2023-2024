package service;

import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.Bill;

public interface BillService extends Remote{
	public boolean createBill(Bill bill) throws RemoteException;

	public List<Bill> getAllBills() throws RemoteException;

	public boolean updateBillAfterPayment(Bill bill) throws RemoteException;

	public ArrayList<Bill> getBillsByTimeFrame(Date startDate, Date endDate) throws RemoteException;

	public ArrayList<Bill> getBillsByDate(Date date) throws RemoteException;

	public ArrayList<Bill> getBillsByDay(Date date) throws RemoteException;

	public ArrayList<Bill> getBillsByMonth(Date date) throws RemoteException;

	public ArrayList<Bill> getBillsByYear(int year) throws RemoteException;

	public ArrayList<Bill> getBillsWithinDay() throws RemoteException;

	public ArrayList<Bill> getBillsByMonth() throws RemoteException;

	public ArrayList<Bill> getBillsByYear() throws RemoteException;

	public ArrayList<Bill> searchBills(String maHD, String tenNV, String sdtKhach) throws RemoteException;

	public ArrayList<Bill> searchBillsByBillID(String maHD) throws RemoteException;

	public ArrayList<Bill> getBillsForStatistics(java.util.Date date, java.util.Date date2) throws RemoteException;

	public Double calculateDailyRevenue(java.util.Date date) throws RemoteException;

	public Double calculateMonthlyRevenue(java.util.Date date) throws RemoteException;

	public Double calculateYearlyRevenue(String year) throws RemoteException;

	public Double calculateTotalRevenue(Date startDate, Date endDate) throws RemoteException;

	public int calculateNumberOfBills(Date startDate, Date endDate) throws RemoteException;

	public int calculateNumberOfBillsByDate(Date date) throws RemoteException;

	public int calculateNumberOfBillsByMonth(Date date) throws RemoteException;

	public int calculateNumberOfBillsByYear(String year) throws RemoteException;

	public List<Integer> getBillYears() throws RemoteException;

	public Map<String, Double> getMonthlyRevenueInRange(Date startDate, Date endDate) throws RemoteException;

	public Map<Integer, Double> getYearlyRevenueTotal(Integer year) throws RemoteException;

	public Map<Integer, Double> getDailyRevenueForMonth(Date month) throws RemoteException;

}
