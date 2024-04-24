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

import app.FrameLogin;
import appService.FrameLoginService;
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
	private BillService billDAO;
	private BookingService bookingDAO;
	private CustomerService customerDAO;
	private DetailBillService detailBillDAO;
	private DetailServiceRoomService  detailServiceDAO;
	private EmployeeService employeeDAO;
	private ServiceService serviceDAO;
	private RoomService roomDAO;
	private FrameLoginService frameLogin;
	public Server() throws RemoteException, NamingException{
		Context context = new InitialContext();
		billDAO = new BillDAO();
		bookingDAO = new BookingDAO();
		customerDAO = new CustomerDAO();
		detailBillDAO = new DetailBillDAO();
		detailServiceDAO = new DetailServiceRoomDAO();
		employeeDAO = new EmployeeDAO();
		roomDAO = new RoomDAO();
		serviceDAO = new ServiceDAO();
		
		LocateRegistry.createRegistry(9571);
		context.bind(URL + "billDAO", billDAO);
		context.bind(URL + "bookingDAO", bookingDAO);
		context.bind(URL + "customerDAO", customerDAO);
		context.bind(URL + "detailBillDAO", detailBillDAO);
		context.bind(URL + "detailServiceDAO", detailServiceDAO);
		context.bind(URL + "employeeDAO", employeeDAO);
		context.bind(URL + "roomDAO", roomDAO);
		context.bind(URL + "serviceDAO", serviceDAO);
		context.bind(URL + "frameLogin", frameLogin);
		System.out.println("Server is running ...");
	}
	
	public static void main(String[] args) throws RemoteException, NamingException {
		new Server();
	}
}
