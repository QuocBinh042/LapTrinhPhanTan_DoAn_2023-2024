package service;

import java.util.ArrayList;

import entity.Room;

public interface PhongService {
	public ArrayList<Room> getAllRooms();

	public ArrayList<Room> getRoomsByStatus(String tinhTrang);

	public ArrayList<Room> getRoomsByType(String tinhTrang);

	public boolean addRoom(Room phong);

	public boolean updateRoom(Room phong);

	public void deleteRoom(String maPhong);

	public boolean updateRoomStatusByRoomID(String tinhTrang, String maPhong);

	public boolean updateRoomStatusByRoomName(String tinhTrang, String tenPhong);

	public ArrayList<Room> searchRoomsByRoomType(String loaiPhong);

	public ArrayList<Room> searchRoomsByRoomStatus(String tinhTrangPhong);

	public ArrayList<Room> searchRoomsByCapacity(int sucChua);

	public ArrayList<Room> searchRoomsByRoomName(String tenPhong);

}
