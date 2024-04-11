package service;

import java.util.ArrayList;

import entity.Customer;

public interface KhachHangService {
	public ArrayList<Customer> getAllCustomers();

	public ArrayList<Customer> getCustomersByType(Boolean loaiKhachHang);

	public boolean addCustomer(Customer khachHang);

	public boolean updateCustomer(Customer khachHang);

	public void deleteCustomer(String tenKhachHang);

	public ArrayList<Customer> searchCustomersByPhoneNumber(String SDT);

}
