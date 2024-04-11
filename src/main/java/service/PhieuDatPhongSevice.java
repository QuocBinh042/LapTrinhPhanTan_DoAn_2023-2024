package service;

import java.util.ArrayList;

import entity.Booking;

public interface PhieuDatPhongSevice {
	public boolean createPDP(Booking pdp);

	public ArrayList<Booking> getAllPDP();

	public ArrayList<Booking> searchPDPByID(String maPDP);

	public boolean updatePDPStatusByID(int tinhTrang, String maPDP);

}
