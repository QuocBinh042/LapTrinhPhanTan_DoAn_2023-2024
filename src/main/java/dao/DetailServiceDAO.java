package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import entity.DetailServiceRoom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import service.DetailServiceRoomService;

public class DetailServiceDAO extends UnicastRemoteObject implements DetailServiceRoomService{

	private EntityManager em;
	public DetailServiceDAO() throws RemoteException {
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	}
	
	@Override
	public boolean createCTDVPhong(DetailServiceRoom ctdvPhong) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(ctdvPhong);
			tx.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
		return false;
	}
	
	@Override
	public boolean updateDetailService(DetailServiceRoom detailServiceRoom) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(detailServiceRoom);
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
	public ArrayList<DetailServiceRoom> getAllCTDVPhong() throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<DetailServiceRoom>) em.createQuery("select ds from DetailServiceRoom ds", DetailServiceRoom.class).getResultList();
	}
	@Override
	public ArrayList<DetailServiceRoom> searchDetailServiceRoomByBillID(int billID) throws RemoteException {
		// TODO Auto-generated method stub
		return (ArrayList<DetailServiceRoom>) em.createQuery("select ds from DetailServiceRoom ds where ds.bill.id = :billID", DetailServiceRoom.class)
				 .setParameter("billID", billID)
				 .getResultList();
	}
	@Override
	public ArrayList<DetailServiceRoom> searchDetailServiceRoomByServiceName(int billID, int serviceID)
			throws RemoteException {	
		return (ArrayList<DetailServiceRoom>) em.createQuery("select ds from DetailServiceRoom ds where ds.bill.id = :billID and ds.service.id = :serviceID", DetailServiceRoom.class)
				 .setParameter("billID", billID)
				 .setParameter("serviceID", serviceID)
				 .getResultList();
	}
	@Override
	public boolean updateAmountService(int quantity, int billID, int serviceID) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			int result = em.createQuery("update DetailServiceRoom ds "
					+ "set ds.quantity = :quantity "
					+ "where ds.bill.id = :billID and ds.service.id = :serviceID")
					.setParameter("quantity", quantity)
					.setParameter("serviceID", serviceID)
					.setParameter("billID", billID)
					.executeUpdate();
			tx.commit();
			return result == 1 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
		return false;
	}
	@Override
	public boolean delete(int billID, int serviceID) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			int result = em.createQuery("delete from DetailServiceRoom ds "
					+ "where ds.bill.id = :billID and ds.service.id = :serviceID")
			  .setParameter("serviceID", serviceID)
			  .setParameter("billID", billID)
			  .executeUpdate();
			tx.commit();
			return result == 1 ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
		return false;
	}
}
