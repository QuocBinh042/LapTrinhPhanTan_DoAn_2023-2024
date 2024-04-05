package service;

import java.util.ArrayList;

import entity.NhanVien;

public interface NhanVienService {
	public ArrayList<NhanVien> getAllEmployees();

	public ArrayList<NhanVien> searchEmployeesByID(String maNV);

	public ArrayList<NhanVien> getEmployeesByPosition(String chucVu);

	public ArrayList<NhanVien> getEmployeesByStatus(Boolean tinhTrangNV);

	public boolean addEmployee(NhanVien nv);

	public boolean updateEmployee(NhanVien nv);

	public NhanVien checkAccount(String maNV, String matKhau);

	public void updatePassword(String matKhau, String soDienThoai);

	public void deleteEmployeeByName(String tenNV);

}
