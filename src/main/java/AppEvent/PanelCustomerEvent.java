package AppEvent;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import appService.PanelCustomerService;
import dao.CustomerDAO;
import entity.Customer;

public class PanelCustomerEvent extends UnicastRemoteObject implements PanelCustomerService{
	private CustomerDAO daoCustomer;
	private List<Customer> listCustomer;

	public PanelCustomerEvent() throws RemoteException {
		daoCustomer = new CustomerDAO();
	}

	@Override
	public JPanel createPanel(JLabel label, JComponent component) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
		panel.add(component);
		label.setFont(new Font("Sanserif", Font.BOLD, 13));
		panel.setBackground(Color.decode("#D0BAFB"));
		return panel;
	}
	
	public void processAddCustomer(JTextField txtCustomerName, JTextField txtPhoneNumber, JTextField txaNote, DefaultTableModel tableModel) throws RemoteException {
		if (!validateInput(txtCustomerName, txtPhoneNumber)) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin khách hàng!");
			return;
		}

		String customerID = Integer.toString(daoCustomer.getAllCustomers().size() + 1);
		String customerName = txtCustomerName.getText();
		String phoneNumber = txtPhoneNumber.getText();
		String note = txaNote.getText();

		Customer customer = new Customer(Integer.parseInt(customerID), customerName, phoneNumber, note);

		int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm mới khách hàng không ?",
				"Chú ý!", JOptionPane.YES_OPTION);
		if (confirmation == JOptionPane.YES_OPTION && daoCustomer.addCustomer(customer)) {
			Object[] rowData = { customerID, customerName, phoneNumber, note };
			tableModel.addRow(rowData);
			JOptionPane.showMessageDialog(null, "Thêm mới khách hàng thành công!");
		} else {
			JOptionPane.showMessageDialog(null, "Thêm mới khách hàng không thành công!");
		}
	}	

	// Xoa toan bo khach hang
	public void clearTable(JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);
	}

	// Lay toan bo khach hang
	public void getAllCustomers(JTable table, DefaultTableModel tableModel) throws RemoteException {
		clearTable(table);
		listCustomer = daoCustomer.getAllCustomers();
		listCustomer.forEach(c -> {
			tableModel.addRow(new Object[] { 
					c.getId(), 
					c.getCustomerName(), 
					c.getPhoneNumber(), 
					c.getNote() 
				});

		});
	}

	// Kiem tra rang buoc
	public boolean validateInput(JTextField txtCustomerName, JTextField txtPhoneNumber) throws RemoteException{
		String tenKH = txtCustomerName.getText();
		String sdt = txtPhoneNumber.getText();
		Pattern p = Pattern.compile("[a-zA-Z]+");
		if (!(p.matcher(tenKH).find())) {
			JOptionPane.showMessageDialog(null, "Tên khách hàng không hợp lệ!");
			return false;
		}

		Pattern p1 = Pattern.compile("[0-9]{10}");
		if (!(p1.matcher(sdt).find())) {
			JOptionPane.showMessageDialog(null, "Số điện thoại chỉ được nhập chữ số!");
			return false;
		}

		return true;
	}

	public void processUpdate(JTable table, JTextField txtCustomerName, JTextField txtPhoneNumber, JTextArea txaNote) throws RemoteException {
		int selectedRow = table.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn khách hàng cần cập nhật!");
			return;
		}

		if (!validateInput(txtCustomerName, txtPhoneNumber)) {
			JOptionPane.showMessageDialog(null, "Vui lòng kiểm tra lại thông tin đã nhập!");
			return;
		}

		int confirmation = JOptionPane.showConfirmDialog(null, "Có chắc chắn muốn cập nhật thông tin khách hàng không?",
				"Chú ý", JOptionPane.YES_NO_OPTION);
		if (confirmation != JOptionPane.YES_OPTION) {
			return;
		}

		String customerName = txtCustomerName.getText();
		String phoneNumber = txtPhoneNumber.getText();
		String note = txaNote.getText();

		table.setValueAt(customerName, selectedRow, 1);
		table.setValueAt(phoneNumber, selectedRow, 2);
		table.setValueAt(note, selectedRow, 3);

		String customerId = table.getValueAt(selectedRow, 0).toString();
		Customer customer = new Customer(Integer.parseInt(customerId), customerName, phoneNumber, note);
		daoCustomer.updateCustomer(customer);

		JOptionPane.showMessageDialog(null, "Cập nhật thông tin khách hàng thành công!");
	}

	public void refesh(JTextField txtMaKH, JTextField txtCustomerName, JTextField txtPhoneNumber, JTextArea txaNote) {
		// TODO Auto-generated method stub
		txtMaKH.setText("");
		txtCustomerName.setText("");
		txtPhoneNumber.setText("");
		txaNote.setText("");
	}

	public boolean processSearch(JTextField txtTimSDT, JTable table) {
		String phoneNumber = txtTimSDT.getText();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 2).equals(phoneNumber)) {
				table.setRowSelectionInterval(i, i);
				JOptionPane.showMessageDialog(null, "Khách hàng được tìm thấy!");
				return true;
			}
		}
		JOptionPane.showMessageDialog(null, "Không tìm thấy số điện thoại!");
		return false;
	}
}
