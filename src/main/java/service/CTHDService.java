package service;

import java.util.ArrayList;

import entity.DetailBill;

public interface CTHDService {
	public boolean createCTPhong(DetailBill cthd);

	public String timKiemCTHDTheoMaPhong(String tenPhong);

	public ArrayList<DetailBill> timKiemCTHDTheoTenPhong(String tenPhong);

	public ArrayList<DetailBill> timKiemCTHDTheoMaHD(String maHD);

	public boolean capNhatGioTraPhong(DetailBill chiTietHoaDon);

}
