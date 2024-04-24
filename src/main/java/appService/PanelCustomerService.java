package appService;

import java.awt.event.MouseEvent;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public interface PanelCustomerService extends Remote{
	public JPanel createPanel(JLabel label, JComponent component) throws RemoteException;
	public void processAddCustomer(JTextField txtCustomerName, JTextField txtPhoneNumber, JTextField txaNote, DefaultTableModel tableModel) throws RemoteException;
	public void clearTable(JTable table) throws RemoteException;
	public void getAllCustomers(JTable table, DefaultTableModel tableModel) throws RemoteException;
	public boolean validateInput(JTextField txtCustomerName, JTextField txtPhoneNumber) throws RemoteException;
	public boolean processSearch(JTextField txtTimSDT, JTable table) throws RemoteException;
	public void refesh(JTextField txtMaKH, JTextField txtCustomerName, JTextField txtPhoneNumber, JTextArea txaNote) throws RemoteException;
	public void processUpdate(JTable table, JTextField txtCustomerName, JTextField txtPhoneNumber, JTextArea txaNote) throws RemoteException;
}
