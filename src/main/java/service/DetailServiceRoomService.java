package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.DetailServiceRoom;

public interface DetailServiceRoomService extends Remote {
	public boolean createCTDVPhong(DetailServiceRoom ctdvPhong) throws RemoteException;

	public ArrayList<DetailServiceRoom> getAllCTDVPhong() throws RemoteException;

	public ArrayList<DetailServiceRoom> timKiemCTDVPhongTheoMaHD(String maHD) throws RemoteException;

	public ArrayList<DetailServiceRoom> timKiemCTDVPhongTheoTenDV(String maHD, String maDV) throws RemoteException;

	public boolean capNhatSoLuongCTDVPhong(int soLuong, String maDV, String maHD) throws RemoteException;

	public boolean capNhatSoLuongDichVu(int soLuong, String tenDV) throws RemoteException;

	public boolean delete(String maDV) throws RemoteException;

}
