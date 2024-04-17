package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.DetailBill;

public interface DetailBillService extends Remote{
	public boolean createDetailBill(DetailBill detailBill) throws RemoteException;

	public List<DetailBill> findDetailBillByRoomID(int roomID) throws RemoteException;

	public List<DetailBill> findDetailBillByRoomName(String roomName) throws RemoteException;

	public List<DetailBill> findDetailBillByBillID(int BillID) throws RemoteException;

	public boolean updateCheckoutTime(DetailBill detailBill) throws RemoteException;

}
