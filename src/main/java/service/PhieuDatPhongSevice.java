package service;

import java.util.ArrayList;

import entity.PhieuDatPhong;

public interface PhieuDatPhongSevice {
	public boolean createPDP(PhieuDatPhong pdp);

	public ArrayList<PhieuDatPhong> getAllPDP();

	public ArrayList<PhieuDatPhong> searchPDPByID(String maPDP);

	public boolean updatePDPStatusByID(int tinhTrang, String maPDP);

}
