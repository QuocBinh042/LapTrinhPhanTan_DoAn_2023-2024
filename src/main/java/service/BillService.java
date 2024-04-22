package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.List;
import java.util.Map;

import entity.Bill;
import entity.DetailBill;

public interface BillService extends Remote{
	public boolean createBill(Bill bill) throws RemoteException;

	public List<Bill> getAllBills() throws RemoteException;

	public boolean updateBillAfterPayment(Bill bill) throws RemoteException;
	
	public List<Bill> getBillsByDetailBillID(int id) throws RemoteException;

	public List<Bill> getBillsByTimeFrame(LocalDate startDate, LocalDate endDate) throws RemoteException;

	public List<Bill> getBillsByDate(LocalDate date) throws RemoteException;

	public List<Bill> getBillsByDay(int day) throws RemoteException;

	public List<Bill> getBillsByMonth(int month) throws RemoteException;

	public List<Bill> getBillsByYear(int year) throws RemoteException;

	public List<Bill> getBillsWithinDay() throws RemoteException;

	public List<Bill> getBillsByMonth() throws RemoteException;

	public List<Bill> getBillsByYear() throws RemoteException;

	public List<Bill> searchBills(String billID, String employeeName, String phoneNumber) throws RemoteException;

	public List<Bill> searchBillsByBillID(String id) throws RemoteException;

	public List<Bill> getBillsForStatistics(LocalDate date,LocalDate date2) throws RemoteException;

	public Double calculateDailyRevenue(LocalDate date) throws RemoteException;

	public Double calculateMonthlyRevenue(LocalDate date) throws RemoteException;

	public Double calculateYearlyRevenue(String year) throws RemoteException;

	public Double calculateTotalRevenue(LocalDate startDate, LocalDate endDate) throws RemoteException;

	public long calculateNumberOfBills(LocalDate startDate, LocalDate endDate) throws RemoteException;

	public long calculateNumberOfBillsByDate(LocalDate date) throws RemoteException;

	public long calculateNumberOfBillsByMonth(String month) throws RemoteException;

	public long calculateNumberOfBillsByYear(String year) throws RemoteException;

	public List<Integer> getBillYears() throws RemoteException;

	public Map<String, Double> getMonthlyRevenueInRange(LocalDate startDate, LocalDate endDate) throws RemoteException;

	public Map<Integer, Double> getYearlyRevenueTotal(int year) throws RemoteException;

	public Map<Integer, Double> getDailyRevenueForMonth(LocalDate month) throws RemoteException;

}
