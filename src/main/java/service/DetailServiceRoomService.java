package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.DetailServiceRoom;

public interface DetailServiceRoomService extends Remote {
	public boolean createCTDVPhong(DetailServiceRoom ctdvPhong) throws RemoteException;

	public ArrayList<DetailServiceRoom> getAllCTDVPhong() throws RemoteException;

	public ArrayList<DetailServiceRoom> searchDetailServiceRoomByBillID(int billID) throws RemoteException;

	public ArrayList<DetailServiceRoom> searchDetailServiceRoomByServiceName(int billID, int serviceID) throws RemoteException;

	public boolean updateAmountService(int quantity, int billID, int serviceID) throws RemoteException;

	public boolean delete(int billID, int serviceID) throws RemoteException;

	public boolean updateDetailService(DetailServiceRoom detailServiceRoom) throws RemoteException;
}
