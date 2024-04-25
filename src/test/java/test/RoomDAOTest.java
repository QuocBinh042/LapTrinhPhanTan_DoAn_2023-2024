package test;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.RemoteException;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dao.RoomDAO;
import entity.Room;
import entity.RoomType;

class RoomDAOTest {

    private static RoomDAO roomDAO;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        roomDAO = new RoomDAO();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        roomDAO = null;
    }

    @Test
    void testAddRoom() throws RemoteException {
        Room room = new Room(21, "114", "trong", "khong co", new RoomType("VIP", 15, 250000));
        assertTrue(roomDAO.addRoom(room));
    }

    @Test
    void testGetAllRooms() throws RemoteException {
        List<Room> allRooms = roomDAO.getAllRooms();
        assertNotNull(allRooms);
        assertTrue(allRooms.size() > 0);
    }

    @Test
    void testGetRoomsByStatus() throws RemoteException {
        List<Room> roomsByStatus = roomDAO.getRoomsByStatus("Còn trống");
        assertNotNull(roomsByStatus);
        assertTrue(roomsByStatus.size() > 0);
    }

    @Test
    void testGetRoomsByType() throws RemoteException {
        List<Room> roomsByType = roomDAO.getRoomsByType("VIP");
        assertNotNull(roomsByType);
        assertTrue(roomsByType.size() > 0);
    }

    @Test
    void testUpdateRoom() throws RemoteException {
        Room room = new Room(21, "114", "trong", "khong co", new RoomType("VIP", 15, 250000));
        assertTrue(roomDAO.updateRoom(room));
    }

    @Test
    void testDeleteRoom() throws RemoteException {
        assertFalse(roomDAO.deleteRoom(21));
    }

    @Test
    void testUpdateRoomStatusByRoomID() throws RemoteException {
    	assertTrue(roomDAO.updateRoomStatusByRoomID("Trống", 1)); 
        assertFalse(roomDAO.updateRoomStatusByRoomID("Trong", 0)); 
        
    }

    @Test
    void testUpdateRoomStatusByRoomName() throws RemoteException {
        assertTrue(roomDAO.updateRoomStatusByRoomName("Đã đặt", "101")); 
    }

    @Test
    void testGetRoomsByRoomName() throws RemoteException {
        List<Room> roomsByRoomName = roomDAO.getRoomsByRoomName("101");
        assertNotNull(roomsByRoomName);
        assertEquals(1, roomsByRoomName.size());
    }
}
