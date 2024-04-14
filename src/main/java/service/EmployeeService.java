package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.Employee;

public interface EmployeeService extends Remote {
	public List<Employee> getAllEmployees() throws RemoteException;

	public boolean addEmployee(Employee employee) throws RemoteException;

	public boolean updateEmployee(Employee employee) throws RemoteException;

	public boolean updatePassword(String password, String phoneNumber) throws RemoteException;

	public boolean deleteEmployeeByID(int employeeID) throws RemoteException;

	public Employee checkAccount(String phoneNumber, String password) throws RemoteException;

	public Employee getEmployeeByID(int employeeID) throws RemoteException;

	public List<Employee> getEmployeesByPosition(String position) throws RemoteException;

	public List<Employee> getEmployeesByStatus(Boolean EmployeeStatus) throws RemoteException;
}
