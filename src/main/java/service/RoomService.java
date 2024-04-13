package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import entity.Room;

public interface RoomService extends Remote {
	public List<Room> getAllRooms() throws RemoteException;

	public List<Room> getRoomsByStatus(String status) throws RemoteException;

	public List<Room> getRoomsByType(String type) throws RemoteException;

	public boolean addRoom(Room room) throws RemoteException;

	public boolean updateRoom(Room room) throws RemoteException;

	public boolean deleteRoom(int roomID) throws RemoteException;

	public boolean updateRoomStatusByRoomID(String status, int roomID) throws RemoteException;

	public boolean updateRoomStatusByRoomName(String status, String nameRoom) throws RemoteException;

	public ArrayList<Room> searchRoomsByRoomType(String typeRoom) throws RemoteException;

	public ArrayList<Room> searchRoomsByRoomStatus(String status) throws RemoteException;

	public ArrayList<Room> searchRoomsByCapacity(int capacity) throws RemoteException;

	public ArrayList<Room> searchRoomsByRoomName(String nameRoom) throws RemoteException;

}