package service;

import java.util.ArrayList;

import entity.DichVu;

public interface DichVuService {
	public ArrayList<DichVu> getAllServices();

	public ArrayList<DichVu> searchServiceByName(String tenDichVu);

	public ArrayList<DichVu> getActiveServices(String tinhTrang);

	public boolean addService(DichVu dichVu);

	public boolean updateService(DichVu dichVu);

	public void deleteService(String maDichVu);

}
