package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import entity.Room;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import service.RoomService;

public class RoomDAO extends UnicastRemoteObject implements RoomService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager em;

	public RoomDAO() throws RemoteException {
		em = Persistence.createEntityManagerFactory("KaraokeOneDB").createEntityManager();
	} 
 
	@Override
	public boolean addRoom(Room room) {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if (em.find(Room.class, Integer.toString(room.getId())) != null) {
				return false;
			}
			em.persist(em.merge(room));
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
	public boolean updateRoom(Room room) {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			em.merge(room);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean deleteRoom(int roomID) {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Room room = em.find(Room.class, roomID);
			em.remove(room);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateRoomStatusByRoomID(String status, int roomID) {
		// TODO Auto-generated method stub
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Room room = em.find(Room.class, roomID);
			if (room != null) {
				room.setRoomStatus(status);
				tx.commit();
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateRoomStatusByRoomName(String status, String nameRoom) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin(); // Start the transaction

			Room room = em.createQuery("SELECT r FROM Room r WHERE r.roomName = :nameRoom", Room.class)
					.setParameter("nameRoom", nameRoom).getSingleResult();
			if (room != null) {
				room.setRoomStatus(status);
				tx.commit(); // Commit the transaction after updating the room status
				return true;
			}
		} catch (Exception e) {
			// Handle exception
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Room> getAllRooms() {
		// TODO Auto-generated method stub
		String string = "Đã xóa";
		return em.createQuery("SELECT r FROM Room r where r.roomStatus != :string", Room.class)
				 .setParameter("string", string)
				 .getResultList();
	}

	@Override
	public List<Room> getRoomsByStatus(String status) {
		// TODO Auto-generated method stub
		if ("Tất cả".equals(status)) {
	        return em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
	    }
		return em.createQuery("SELECT r FROM Room r WHERE r.roomStatus = :status", Room.class)
				.setParameter("status", status).getResultList();
	}

	@Override
	public List<Room> getRoomsByType(String type) {
		// TODO Auto-generated method stub
		if ("Tất cả".equals(type)) {
	        return em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
	    }
		return em.createQuery("SELECT r FROM Room r WHERE LOWER(r.roomType.typeRoom) = LOWER(:type)", Room.class)
				.setParameter("type", type).getResultList();
	}

	@Override
	public List<Room> getRoomsByCapacity(String capacity) {
		// TODO Auto-generated method stub
		if ("Tất cả".equals(capacity)) {
	        return em.createQuery("SELECT r FROM Room r", Room.class).getResultList();
	    }
		int i = Integer.valueOf(capacity);
		return em.createQuery("SELECT r FROM Room r WHERE r.roomType.capacity = :capacity", Room.class)
				.setParameter("capacity", i).getResultList();
	}

	@Override
	public List<Room> getRoomsByRoomName(String nameRoom) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT r FROM Room r WHERE r.roomName like :nameRoom", Room.class)
				.setParameter("nameRoom", "%" + nameRoom + "%").getResultList();
	}

	@Override
	public List<Room> getRoomsByRoomName2(String nameRoom) {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT r FROM Room r WHERE r.roomName = :nameRoom", Room.class)
				.setParameter("nameRoom", nameRoom).getResultList();
	}

	@Override
	public List<Room> getRoomsByPrice(Double price) throws RemoteException {
		// TODO Auto-generated method stub
		return em.createQuery("SELECT r FROM Room r WHERE r.roomType.price = :price", Room.class)
				.setParameter("price", price).getResultList();
	}

}
