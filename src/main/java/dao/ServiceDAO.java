package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import entity.Room;
import entity.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import service.ServiceService;

public class ServiceDAO  extends UnicastRemoteObject implements ServiceService {
	private EntityManager em;

	public ServiceDAO() throws RemoteException{
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	} 

	@Override
	public boolean addService(Service service) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			if (!tx.isActive()) {
	            tx.begin();
	        }
			if (em.find(Service.class, service.getId()) != null) {
				return false;
			}
			em.persist(em.merge(service));
			if (tx.isActive() && tx.getRollbackOnly()) {
	            tx.rollback();
	        } else {
	            tx.commit();
	        }
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			if (tx.isActive()) {
	            tx.rollback();
	        }
	        e.printStackTrace();		}
		return false;
	}

	@Override
	public boolean updateService(Service service) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(service);
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
	public boolean deleteService(int serviceID) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			if (!tx.isActive()) {
	            tx.begin();
	        }
			Service s = em.find(Service.class, serviceID);
			if (s != null) {
				em.remove(s);
				
			}
			if (tx.isActive() && tx.getRollbackOnly()) {
	            tx.rollback();
	        } else {
	            tx.commit();
	        }
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Service> getAllServices() throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT s FROM Service s", Service.class).getResultList();
	}

	@Override
	public List<Service> getServiceByName(String serviceName) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT s FROM Service s WHERE s.name = :serviceName", Service.class)
				.setParameter("serviceName", serviceName).getResultList();
	}

	@Override
	public List<Service> getActiveServices(String status) throws RemoteException {
		// TODO Auto-generated method stub
		if ("Tất cả".equals(status)) {
	        return em.createQuery("SELECT s FROM Service s", Service.class).getResultList();
	    }
		return em.createQuery("SELECT s FROM Service s WHERE s.status = :status", Service.class)
				.setParameter("status", status).getResultList();
	}

}
