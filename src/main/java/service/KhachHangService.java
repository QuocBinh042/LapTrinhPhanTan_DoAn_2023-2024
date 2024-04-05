package service;

import java.util.ArrayList;

import entity.KhachHang;

public interface KhachHangService {
	public ArrayList<KhachHang> getAllCustomers();

	public ArrayList<KhachHang> getCustomersByType(Boolean loaiKhachHang);

	public boolean addCustomer(KhachHang khachHang);

	public boolean updateCustomer(KhachHang khachHang);

	public void deleteCustomer(String tenKhachHang);

	public ArrayList<KhachHang> searchCustomersByPhoneNumber(String SDT);

}
