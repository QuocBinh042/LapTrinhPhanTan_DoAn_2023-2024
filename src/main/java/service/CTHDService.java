package service;

import java.util.ArrayList;

import entity.ChiTietHoaDon;

public interface CTHDService {
	public boolean createCTPhong(ChiTietHoaDon cthd);

	public String timKiemCTHDTheoMaPhong(String tenPhong);

	public ArrayList<ChiTietHoaDon> timKiemCTHDTheoTenPhong(String tenPhong);

	public ArrayList<ChiTietHoaDon> timKiemCTHDTheoMaHD(String maHD);

	public boolean capNhatGioTraPhong(ChiTietHoaDon chiTietHoaDon);

}
