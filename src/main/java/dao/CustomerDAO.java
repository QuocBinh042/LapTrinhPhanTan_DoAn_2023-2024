package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import service.CustomerService;

public class CustomerDAO extends UnicastRemoteObject implements CustomerService {
	private EntityManager em;

	public CustomerDAO() throws RemoteException{
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	}

	@Override
	public List<Customer> getAllCustomers() throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
	}

	@Override
	public boolean addCustomer(Customer customer) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if (em.find(Customer.class, customer.getId()) != null) {
				return false;
			}
			em.persist(em.merge(customer));
			tx.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateCustomer(Customer customer) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(customer);
			tx.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteCustomer(int customerID) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Customer c = em.find(Customer.class, customerID);
			if (c != null) {
				em.remove(c);
				tx.commit();
				return true;
			}

		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public List<Customer> findCustomersByPhoneNumber(String phoneNumber) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber", Customer.class)
				.setParameter("phoneNumber", phoneNumber).getResultList();
	}

}
