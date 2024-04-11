package service;

import java.util.ArrayList;

import entity.Employee;

public interface NhanVienService {
	public ArrayList<Employee> getAllEmployees();

	public ArrayList<Employee> searchEmployeesByID(String maNV);

	public ArrayList<Employee> getEmployeesByPosition(String chucVu);

	public ArrayList<Employee> getEmployeesByStatus(Boolean tinhTrangNV);

	public boolean addEmployee(Employee nv);

	public boolean updateEmployee(Employee nv);

	public Employee checkAccount(String maNV, String matKhau);

	public void updatePassword(String matKhau, String soDienThoai);

	public void deleteEmployeeByName(String tenNV);

}
