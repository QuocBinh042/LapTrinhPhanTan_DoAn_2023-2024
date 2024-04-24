package appService;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public interface PanelRoomService extends Remote{
	public void processAdd(JTextField txtRoomID, JTextField txtRoomName,
			JTextField txtRoomType, JTextField txtCapacity, JTextField txtPrice, JTextArea txaDescription, DefaultTableModel tableModel) throws RemoteException;
	public void processUpdate(JTable table, JTextField txtRoomName, JTextField txtRoomType, JTextField txtCapacity, JTextField txtPrice, JTextArea txaDescription)throws RemoteException;
	public void processRefesh(JComboBox<String> cbRoomType, JComboBox<String> cbStatus, JTextField txtRoomID,
			JTextField txtRoomName, JTextField txtRoomType, JTextField txtPrice,JTextField txtCapacity, JTextField txtStatus, JTextField txaDescription)throws RemoteException;
	public void processDelete(JTable table, DefaultTableModel tableModel)throws RemoteException;
	public boolean validateInput(JTextField txtRoomName, JTextField txtRoomType,JTextField txtCapacity, JTextField txtPrice)throws RemoteException;
	public boolean processSearch(JTextField txtTimPhong, JTable table)throws RemoteException;
	public void clearTable(JTable table)throws RemoteException;
	public void filterRoomsByStatus(JComboBox<String> cbStatus, DefaultTableModel tableModel, JTable table)throws RemoteException;
	public void filterRoomsByType(JTable table, JComboBox<String> cbRoomType, DefaultTableModel tableModel)throws RemoteException;
	public void fetchAllRooms(DefaultTableModel tableModel)throws RemoteException;
	public JPanel createPanel(JLabel label, JComponent component)throws RemoteException;
}
