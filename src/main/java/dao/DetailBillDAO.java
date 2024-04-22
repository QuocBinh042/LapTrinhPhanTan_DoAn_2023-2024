package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import entity.Bill;
import entity.DetailBill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import service.DetailBillService;

public class DetailBillDAO extends UnicastRemoteObject implements DetailBillService{
	private EntityManager em;
	
	public DetailBillDAO() throws RemoteException{
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	}
	
	public static void main(String[] args) {
		try {
			DetailBillDAO dao = new DetailBillDAO();
//			dao.findDetailBillByRoomID(2).forEach(e -> System.out.println(e));
//			dao.findDetailBillByRoomName("102").forEach(e -> System.out.println(e));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean createDetailBill(DetailBill detailBill) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();			
			em.merge(detailBill);
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
	public List<DetailBill> findDetailBillByRoomID(int roomID) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from DetailBill b where b.room.id = :roomID", DetailBill.class)
				 .setParameter("roomID", roomID)
				 .getResultList();
	}
	
	@Override
	public List<DetailBill> findDetailBillByRoomIDOrderByID(String roomName) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from DetailBill b where b.room.roomName = :roomName ORDER BY b.id DESC", DetailBill.class)
				 .setParameter("roomName", roomName)
				 .getResultList();
	}

	@Override
	public List<DetailBill> findDetailBillByRoomName(String roomName) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from DetailBill b where b.room.roomName = :roomName", DetailBill.class)
				 .setParameter("roomName", roomName)
				 .getResultList();
	}

	@Override
	public List<DetailBill> findDetailBillByBillID(int BillID) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("select b from DetailBill b where b.bill.id = :billID", DetailBill.class)
				 .setParameter("billID", BillID)
				 .getResultList();
	}

	@Override
	public boolean updateCheckoutTime(DetailBill detailBill) throws RemoteException {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();		
			em.merge(detailBill);
			tx.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}
		return false;
	}

}
