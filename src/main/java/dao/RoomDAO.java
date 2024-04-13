package dao;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
	public List<Room> getAllRooms() {
		// TODO Auto-generated method stub
		TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r", Room.class);
		return query.getResultList();
	}

	@Override
	public List<Room> getRoomsByStatus(String status) {
		// TODO Auto-generated method stub
		TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r WHERE r.roomStatus = :status", Room.class);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<Room> getRoomsByType(String type) {
		// TODO Auto-generated method stub
		TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r WHERE r.roomType.typeRoom = :type", Room.class);
		query.setParameter("type", type);
		return query.getResultList();
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
		return false;
	}

	@Override
	public boolean updateRoomStatusByRoomName(String status, String nameRoom) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Room> searchRoomsByRoomType(String typeRoom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Room> searchRoomsByRoomStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Room> searchRoomsByCapacity(int capacity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Room> searchRoomsByRoomName(String nameRoom) {
		// TODO Auto-generated method stub
		return null;
	}

}
