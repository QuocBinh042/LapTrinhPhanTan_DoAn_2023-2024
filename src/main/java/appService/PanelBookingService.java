package appService;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.PanelBooking.TinhTien;
import entity.Bill;

public interface PanelBookingService extends Remote{
	public void readDataToTablePhong(DefaultTableModel modelDichVu, DefaultTableModel modelPhong, JTextField tfMaHD) throws NumberFormatException, RemoteException;
	public void readDataToFieldThongTin(DefaultTableModel phongModel, JTable phongTable, JTextField tfMaHD, JTextField tfTenNhanVien, JTextField tfTenKH, JTextField tfSDTKhach, JTextField tfNgayThanhToan, JTextField tfGioThanhToan) throws RemoteException;
	public void readDataToTableDichVu(DefaultTableModel modelDichVu, JTextField tfMaHD) throws NumberFormatException, RemoteException;
	public void xuLyTinhTienDienVaoThongTin(JTextField tfThue, JTextField tfTienPhong, JTextField tfTienDichVu, JTextField tfTongCong, JTextField tfThanhTien, JTextField tfMaHD) throws NumberFormatException, RemoteException;
	public void xuLyThanhToan(DefaultTableModel phongModel, JTable phongTable, TinhTien tinhTien) throws RemoteException;
}
