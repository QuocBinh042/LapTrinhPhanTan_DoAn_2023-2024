package service;

import java.util.ArrayList;

import entity.Phong;

public interface PhongService {
	public ArrayList<Phong> getAllRooms();

	public ArrayList<Phong> getRoomsByStatus(String tinhTrang);

	public ArrayList<Phong> getRoomsByType(String tinhTrang);

	public boolean addRoom(Phong phong);

	public boolean updateRoom(Phong phong);

	public void deleteRoom(String maPhong);

	public boolean updateRoomStatusByRoomID(String tinhTrang, String maPhong);

	public boolean updateRoomStatusByRoomName(String tinhTrang, String tenPhong);

	public ArrayList<Phong> searchRoomsByRoomType(String loaiPhong);

	public ArrayList<Phong> searchRoomsByRoomStatus(String tinhTrangPhong);

	public ArrayList<Phong> searchRoomsByCapacity(int sucChua);

	public ArrayList<Phong> searchRoomsByRoomName(String tenPhong);

}
