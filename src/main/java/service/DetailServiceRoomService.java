package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.DetailServiceRoom;

public interface DetailServiceRoomService extends Remote {
	public boolean createCTDVPhong(DetailServiceRoom ctdvPhong) throws RemoteException;

	public ArrayList<DetailServiceRoom> getAllCTDVPhong() throws RemoteException;

	public ArrayList<DetailServiceRoom> searchDetailServiceRoomByBillID(String billID) throws RemoteException;

	public ArrayList<DetailServiceRoom> searchDetailServiceRoomByServiceName(String billID, String serviceID) throws RemoteException;

	public boolean updateAmountService(int quantity, String billID, String serviceID) throws RemoteException;

	public boolean delete(String billID, String serviceID) throws RemoteException;

}
