package service;

import java.util.ArrayList;

import entity.Service;

public interface DichVuService {
	public ArrayList<Service> getAllServices();

	public ArrayList<Service> searchServiceByName(String tenDichVu);

	public ArrayList<Service> getActiveServices(String tinhTrang);

	public boolean addService(Service dichVu);

	public boolean updateService(Service dichVu);

	public void deleteService(String maDichVu);

}
