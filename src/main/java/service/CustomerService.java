package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Customer;

public interface CustomerService extends Remote {
	public List<Customer> getAllCustomers() throws RemoteException;

	public boolean addCustomer(Customer customer) throws RemoteException;

	public boolean updateCustomer(Customer customer) throws RemoteException;

	public boolean deleteCustomer(int customerID) throws RemoteException;

	public List<Customer> findCustomersByPhoneNumber(String phoneNumber) throws RemoteException;

}
