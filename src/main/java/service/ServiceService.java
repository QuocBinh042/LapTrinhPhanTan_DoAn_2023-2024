package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Service;

public interface ServiceService extends Remote {

	public boolean addService(Service service) throws RemoteException;

	public boolean updateService(Service service) throws RemoteException;

	public boolean deleteService(int serviceID) throws RemoteException;

	public List<Service> getAllServices() throws RemoteException;

	public List<Service> getServiceByName(String serviceName) throws RemoteException;

	public List<Service> getActiveServices(String status) throws RemoteException;

}
