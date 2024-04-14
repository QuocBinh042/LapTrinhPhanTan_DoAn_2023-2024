package Test;

import java.rmi.RemoteException;
import java.time.LocalDate;

import dao.CustomerDAO;
import dao.EmployeeDAO;
import dao.RoomDAO;
import entity.Customer;
import entity.Employee;
import entity.Room;
import entity.RoomType;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
	public static void main(String[] args) throws RemoteException {
//		RoomDAO roomDAO = new RoomDAO();
//		Room room = new Room(21, "114", "trong", " khong co", new RoomType("VIP", 15, 250000));
//		roomDAO.addRoom(room);
//		System.out.println(roomDAO.getAllRooms());
//		System.out.println(roomDAO.getRoomsByStatus("trong"));
//		System.out.println(roomDAO.getRoomsByType("VIP"));
//		System.out.println(roomDAO.updateRoom(room));
//		System.out.println(roomDAO.deleteRoom(21));
//		System.out.println(roomDAO.getRoomsByCapacity(15));

//		System.out.println(roomDAO.updateRoomStatusByRoomID("Trong", 1));
//		System.out.println(roomDAO.updateRoomStatusByRoomName("Da dat", "101"));
//		System.out.println(roomDAO.getRoomsByRoomName("101"));

//		CustomerDAO cDAO = new CustomerDAO();
//		Customer c = new Customer(22, "acb", "1234567", " ");
//		System.out.println(cDAO.addCustomer(c));		
//		System.out.println(cDAO.updateCustomer(c));
//		System.out.println(cDAO.getAllCustomers());
//		System.out.println(cDAO.deleteCustomer(22));
//		System.out.println(cDAO.findCustomersByPhoneNumber("1234"));

		Employee e = new Employee(21, "abcdc", LocalDate.now(), false, "1234577777", "123", "Le Tan", "1213", true);
		EmployeeDAO eDAO = new EmployeeDAO();
//		System.out.println(eDAO.addEmployee(e));
//		System.out.println(eDAO.updateEmployee(e));//		
//		System.out.println(eDAO.deleteEmployeeByID(21));
//		System.out.println(eDAO.getEmployeeByID(1));
//		System.out.println(eDAO.getEmployeesByPosition("Lễ tân"));
//		System.out.println(eDAO.getEmployeesByStatus(true));
//		System.out.println(eDAO.checkAccount("0386076296", "123456789"));
//		System.out.println(eDAO.updatePassword("0386076296", "123"));
//		System.out.println(eDAO.getAllEmployees());

	}
}
