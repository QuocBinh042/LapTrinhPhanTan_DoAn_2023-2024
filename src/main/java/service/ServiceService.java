package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.Service;

public interface ServiceService extends Remote {
	public ArrayList<Service> getAllServices() throws RemoteException;

	public ArrayList<Service> findServiceByName(String serviceName) throws RemoteException;

	public ArrayList<Service> getActiveServices(String status) throws RemoteException;

	public boolean addService(Service service) throws RemoteException;

	public boolean updateService(Service service) throws RemoteException;

	public void deleteService(String serviceID) throws RemoteException;

}
