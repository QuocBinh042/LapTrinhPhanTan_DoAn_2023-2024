package service;

import java.util.ArrayList;

import entity.CTDVPhong;

public interface CTDVPService {
	public boolean createCTDVPhong(CTDVPhong ctdvPhong);

	public ArrayList<CTDVPhong> getAllCTDVPhong();

	public ArrayList<CTDVPhong> timKiemCTDVPhongTheoMaHD(String maHD);

	public ArrayList<CTDVPhong> timKiemCTDVPhongTheoTenDV(String maHD, String maDV);

	public boolean capNhatSoLuongCTDVPhong(int soLuong, String maDV, String maHD);

	public boolean capNhatSoLuongDichVu(int soLuong, String tenDV);

	public boolean delete(String maDV);

}
