package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entity.Room;

public interface RoomService extends Remote {

	public boolean addRoom(Room room) throws RemoteException;

	public boolean updateRoom(Room room) throws RemoteException;

	public boolean deleteRoom(int roomID) throws RemoteException;

	public boolean updateRoomStatusByRoomID(String status, int roomID) throws RemoteException;

	public boolean updateRoomStatusByRoomName(String status, String nameRoom) throws RemoteException;

	public List<Room> getAllRooms() throws RemoteException;

	public List<Room> getRoomsByStatus(String status) throws RemoteException;

	public List<Room> getRoomsByType(String type) throws RemoteException;

	public List<Room> getRoomsByCapacity(String capacity) throws RemoteException;

	public List<Room> getRoomsByRoomName(String nameRoom) throws RemoteException;
	
	public List<Room> getRoomsByRoomName2(String nameRoom) throws RemoteException;
	
	public List<Room> getRoomsByPrice(Double price) throws RemoteException;
}
