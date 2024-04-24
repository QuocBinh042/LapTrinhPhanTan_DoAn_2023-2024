package AppEvent;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import appService.PanelRoomService;
import dao.RoomDAO;
import entity.Room;
import entity.RoomType;

public class PanelRoomEvent extends UnicastRemoteObject implements PanelRoomService{
	private RoomDAO daoRoom;
	private List<Room> listRoom;
	private DecimalFormat formatter = new DecimalFormat("###");
	public PanelRoomEvent() throws RemoteException {
		 daoRoom = new RoomDAO();
	}
	
	// Xu ly them moi phong
		public void processAdd(JTextField txtRoomID, JTextField txtRoomName,
				JTextField txtRoomType, JTextField txtCapacity, JTextField txtPrice, JTextArea txaDescription, DefaultTableModel tableModel) throws RemoteException{		    
			if (!validateInput(txtRoomName, txtRoomType, txtCapacity, txtPrice)) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin phòng!");
				return;
			}
			txtRoomID.setText((daoRoom.getAllRooms().size() + 1) + "");
			String roomID = txtRoomID.getText();
			String roomName = txtRoomName.getText();
			String roomType = txtRoomType.getText();
			int capacity = Integer.parseInt(txtCapacity.getText());
			double price = Double.parseDouble(txtPrice.getText());
			String description = txaDescription.getText();
			RoomType typeRoom = new RoomType(roomType, capacity, price);
			Room room = new Room(Integer.parseInt(roomID), roomName, "Còn trống", description, typeRoom);

			int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thêm mới phòng không ?", "Chú ý!",
					JOptionPane.YES_OPTION);
			if (confirmation == JOptionPane.YES_OPTION && daoRoom.addRoom(room)) {
				Object[] rowData = { roomID, roomName, roomType, capacity, price, "Còn trống", description };
				tableModel.addRow(rowData);
				JOptionPane.showMessageDialog(null, "Thêm mới phòng thành công!");
			}
		}

		// Xu ly cap nhat thong tin phong
		public void processUpdate(JTable table, JTextField txtRoomName, JTextField txtRoomType, JTextField txtCapacity, JTextField txtPrice, JTextArea txaDescription) throws RemoteException{
			int selectedRow = table.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn phòng cần cập nhật!");
				return;
			}

			if (!validateInput(txtRoomName, txtRoomType, txtCapacity, txtPrice)) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin phòng!");
				return;
			}

			int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn cập nhật phòng?", "Chú ý!",
					JOptionPane.YES_NO_OPTION);
			if (confirmation != JOptionPane.YES_OPTION) {
				return;
			}

			String roomName = txtRoomName.getText();
			String roomType = txtRoomType.getText();
			int capacity = Integer.parseInt(txtCapacity.getText());
			double price = Double.parseDouble(txtPrice.getText());
			String description = txaDescription.getText();

			table.setValueAt(roomName, selectedRow, 1);
			table.setValueAt(roomType, selectedRow, 2);
			table.setValueAt(capacity, selectedRow, 3);
			table.setValueAt(formatter.format(price), selectedRow, 4);
			table.setValueAt(description, selectedRow, 6);

			String currentStatus = table.getValueAt(selectedRow, 5).toString();
			RoomType roomTypeObject = new RoomType(roomType, capacity, price);
			Room room = new Room(Integer.valueOf(table.getValueAt(selectedRow, 0).toString()), roomName, currentStatus,
					description, roomTypeObject);
			daoRoom.updateRoom(room);

			JOptionPane.showMessageDialog(null, "Cập nhật thông tin phòng thành công!");
		}

		// Xu ly lam moi
		public void processRefesh(JComboBox<String> cbRoomType, JComboBox<String> cbStatus, JTextField txtRoomID,
				JTextField txtRoomName, JTextField txtRoomType, JTextField txtPrice,JTextField txtCapacity, JTextField txtStatus, JTextField txaDescription) throws RemoteException{
			cbRoomType.setSelectedIndex(0);
			cbStatus.setSelectedIndex(0);
			txtRoomID.setText("");
			txtRoomName.setText("");
			txtRoomType.setText("");
			txtPrice.setText("");
			txtCapacity.setText("");
			txtStatus.setText("");
			txaDescription.setText("");
		}

		// Xu ly xoa phong
		public void processDelete(JTable table, DefaultTableModel tableModel) throws RemoteException{
			int row = table.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn phòng cần xóa!");
				return;
			}

			int confirmation = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa phòng không?", "Chú ý!",
					JOptionPane.YES_NO_OPTION);
			if (confirmation != JOptionPane.YES_OPTION) {
				return;
			}

			int roomID = Integer.parseInt(table.getValueAt(row, 0).toString());
			daoRoom.deleteRoom(roomID);
			tableModel.removeRow(row);
			JOptionPane.showMessageDialog(null, "Xóa phòng thành công!");
		}

		public boolean validateInput(JTextField txtRoomName, JTextField txtRoomType,JTextField txtCapacity, JTextField txtPrice) throws RemoteException{
			String roomName = txtRoomName.getText();
			String roomType = txtRoomType.getText();
			String capacity = txtCapacity.getText();
			String price = txtPrice.getText();
			if (roomName.equals("") || roomType.equals("") || capacity.equals("") || price.equals("")) {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin phòng!");
				return false;
			}
			return true;
		}

		// Xu ly tim kiem phong
		public boolean processSearch(JTextField txtTimPhong, JTable table) throws RemoteException{
			String roomIDToSearch = txtTimPhong.getText();
			for (int i = 0; i < table.getRowCount(); i++) {
				if (table.getValueAt(i, 1).equals(roomIDToSearch)) {
					table.setRowSelectionInterval(i, i);
					JOptionPane.showMessageDialog(null, "Phòng được tìm thấy!");
					return true;
				}
			}
			JOptionPane.showMessageDialog(null, "Phòng không tồn tại!");
			return false;
		} 

		// Xoa toan bo loai phong
		public void clearTable(JTable table) {
			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			tableModel.setRowCount(0);
		}

		// Xu ly combobox loc theo tinh trang
		public void filterRoomsByStatus(JComboBox<String> cbStatus, DefaultTableModel tableModel, JTable table) throws RemoteException{
			clearTable(table);
			String selectedStatus = cbStatus.getSelectedItem().toString();
			List<Room> listRooms = daoRoom.getRoomsByStatus(selectedStatus);
			listRooms.forEach(room -> {
				RoomType roomType = room.getRoomType();
				Object[] rowData = new Object[] { 
						room.getId(), 
						room.getName(), 
						roomType.getTypeRoom(),
						roomType.getCapacity(), 
						formatter.format(roomType.getPrice()), 
						room.getRoomStatus(),
						room.getDescribe() 
				};
				tableModel.addRow(rowData);
			});
		}
	 
		// Xu ly combobox loc theo loai phong
		public void filterRoomsByType(JTable table, JComboBox<String> cbRoomType, DefaultTableModel tableModel) throws RemoteException{
		    clearTable(table);
		    String selectedRoomType = cbRoomType.getSelectedItem().toString();
		    List<Room> listRooms = daoRoom.getRoomsByType(selectedRoomType);
		    
		    for (Room room : listRooms) {
		        RoomType roomType = room.getRoomType();
		        Object[] rowData = new Object[] { 
		            room.getId(), 
		            room.getName(), 
		            roomType.getTypeRoom(),
		            roomType.getCapacity(),  
		            formatter.format(roomType.getPrice()),    
		            room.getRoomStatus(), 				    
		            room.getDescribe() 
		        };
		        tableModel.addRow(rowData);
		    }
		}
		
		public void fetchAllRooms(DefaultTableModel tableModel) throws RemoteException{
			listRoom = daoRoom.getAllRooms();
			listRoom.forEach(r -> {
				tableModel.addRow(new Object[] { 
					    r.getId(), 
					    r.getName(), 
					    r.getRoomType().getTypeRoom(),
					    r.getRoomType().getCapacity(),  
					    r.getRoomType().getPrice(),    				    			    
					    r.getRoomStatus(), 	
					    r.getDescribe(),
					   
					});
			});
		}
		
		public JPanel createPanel(JLabel label, JComponent component) throws RemoteException{
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.LEFT));
			panel.add(label);
			panel.add(component);
			label.setFont(new Font("Sanserif", Font.BOLD, 13));
			panel.setBackground(Color.decode("#D0BAFB"));
			return panel;
		}
}
