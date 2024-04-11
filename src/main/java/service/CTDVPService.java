package service;

import java.util.ArrayList;

import entity.DetailServiceRoom;

public interface CTDVPService {
	public boolean createCTDVPhong(DetailServiceRoom ctdvPhong);

	public ArrayList<DetailServiceRoom> getAllCTDVPhong();

	public ArrayList<DetailServiceRoom> timKiemCTDVPhongTheoMaHD(String maHD);

	public ArrayList<DetailServiceRoom> timKiemCTDVPhongTheoTenDV(String maHD, String maDV);

	public boolean capNhatSoLuongCTDVPhong(int soLuong, String maDV, String maHD);

	public boolean capNhatSoLuongDichVu(int soLuong, String tenDV);

	public boolean delete(String maDV);

}
