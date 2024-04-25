package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import dao.BillDAO;
import dao.BookingDAO;
import dao.CustomerDAO;
import dao.DetailBillDAO;
import dao.DetailServiceRoomDAO;
import dao.EmployeeDAO;
import dao.RoomDAO;
import dao.ServiceDAO;
import entity.Employee;
import service.BillService;
import service.BookingService;
import service.CustomerService;
import service.DetailBillService;
import service.DetailServiceRoomService;
import service.EmployeeService;
import service.RoomService;
import service.ServiceService;

public class Server {
	private static final String URL = "rmi://DESKTOP-522U2I7:9571/";
	private BillService billService;
	private BookingService bookingService;
	private CustomerService customerService;
	private DetailBillService detailBillService;
	private DetailServiceRoomService detailServiceRoomService;
	private EmployeeService employeeService;
	private ServiceService serviceService;
	private RoomService roomService;
	public Server() throws RemoteException, NamingException{
		Context context = new InitialContext();
		billService = new BillDAO();
		bookingService = new BookingDAO();
		customerService = new CustomerDAO();
		detailBillService = new DetailBillDAO();
		detailServiceRoomService = new DetailServiceRoomDAO();
		employeeService = new EmployeeDAO();
		roomService = new RoomDAO();
		serviceService = new ServiceDAO();
		
		LocateRegistry.createRegistry(9571);
		context.bind(URL + "billService", billService);
		context.bind(URL + "bookingService", bookingService);
		context.bind(URL + "customerService", customerService);
		context.bind(URL + "detailBillService", detailBillService);
		context.bind(URL + "detailServiceRoomService", detailServiceRoomService);
		context.bind(URL + "employeeService", employeeService);
		context.bind(URL + "roomService", roomService);
		context.bind(URL + "serviceService", serviceService);
		System.out.println("Server is running ...");
	}
	
	public static void main(String[] args) throws RemoteException, NamingException {
		new Server();
	}
}
