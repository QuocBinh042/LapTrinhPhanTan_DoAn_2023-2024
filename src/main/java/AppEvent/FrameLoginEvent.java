package AppEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import appService.FrameLoginService;
import dao.EmployeeDAO;
import service.EmployeeService;

public class FrameLoginEvent extends UnicastRemoteObject implements FrameLoginService{
	private EmployeeDAO employeeDAO;
	public FrameLoginEvent() throws RemoteException {
		employeeDAO = new EmployeeDAO();
	}
}
