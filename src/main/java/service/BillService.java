package service;

import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import entity.Bill;

public interface BillService extends Remote{
	public boolean createBill(Bill bill) throws RemoteException;

	public List<Bill> getAllBills() throws RemoteException;

	public boolean updateBillAfterPayment(Bill bill) throws RemoteException;

	public ArrayList<Bill> getBillsByTimeFrame(LocalDate startDate, LocalDate endDate) throws RemoteException;

	public ArrayList<Bill> getBillsByDate(LocalDate date) throws RemoteException;

	public ArrayList<Bill> getBillsByDay(int day) throws RemoteException;

	public ArrayList<Bill> getBillsByMonth(int month) throws RemoteException;

	public ArrayList<Bill> getBillsByYear(int year) throws RemoteException;

	public ArrayList<Bill> getBillsWithinDay() throws RemoteException;

	public ArrayList<Bill> getBillsByMonth() throws RemoteException;

	public ArrayList<Bill> getBillsByYear() throws RemoteException;

	public ArrayList<Bill> searchBills(String maHD, String tenNV, String sdtKhach) throws RemoteException;

	public ArrayList<Bill> searchBillsByBillID(String id) throws RemoteException;

	public ArrayList<Bill> getBillsForStatistics(LocalDate date,LocalDate date2) throws RemoteException;

	public Double calculateDailyRevenue(LocalDate date) throws RemoteException;

	public Double calculateMonthlyRevenue(LocalDate date) throws RemoteException;

	public Double calculateYearlyRevenue(String year) throws RemoteException;

	public Double calculateTotalRevenue(LocalDate startDate, LocalDate endDate) throws RemoteException;

	public int calculateNumberOfBills(LocalDate startDate, LocalDate endDate) throws RemoteException;

	public int calculateNumberOfBillsByDate(LocalDate date) throws RemoteException;

	public int calculateNumberOfBillsByMonth(LocalDate date) throws RemoteException;

	public int calculateNumberOfBillsByYear(String year) throws RemoteException;

	public List<Integer> getBillYears() throws RemoteException;

	public Map<String, Double> getMonthlyRevenueInRange(LocalDate startDate, LocalDate endDate) throws RemoteException;

	public Map<Integer, Double> getYearlyRevenueTotal(int year) throws RemoteException;

	public Map<Integer, Double> getDailyRevenueForMonth(LocalDate month) throws RemoteException;

}
