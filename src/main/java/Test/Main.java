package Test;

import java.rmi.RemoteException;

import dao.RoomDAO;
import entity.Room;
import entity.RoomType;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
	public static void main(String[] args) throws RemoteException {
		RoomDAO roomDAO = new RoomDAO();
		Room room = new Room(21, "114", "trong", " khong co", new RoomType("VIP", 15, 250000));
//		roomDAO.addRoom(room);
//		System.out.println(roomDAO.getAllRooms());
//		System.out.println(roomDAO.getRoomsByStatus("trong"));
//		System.out.println(roomDAO.getRoomsByType("VIP"));
//		System.out.println(roomDAO.updateRoom(room));
//		System.out.println(roomDAO.deleteRoom(21));
//		System.out.println(roomDAO.getRoomsByCapacity(15));

//		System.out.println(roomDAO.updateRoomStatusByRoomID("Trong", 1));
		System.out.println(roomDAO.updateRoomStatusByRoomName("Da dat", "101"));
//		System.out.println(roomDAO.getRoomsByRoomName("101"));
	}
}
