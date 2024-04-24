package AppEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import app.PanelBooking.TinhTien;
import appService.PanelBookingService;
import dao.BillDAO;
import dao.DetailBillDAO;
import dao.DetailServiceRoomDAO;
import dao.RoomDAO;
import entity.Bill;
import entity.DetailBill;
import entity.DetailServiceRoom;
import entity.Room;

public class PanelBookingEvent extends UnicastRemoteObject implements PanelBookingService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6662661400679080953L;
	private BillDAO billDAO;
	private DetailBillDAO detailBillDAO;
	private RoomDAO roomDAO;
	private DetailServiceRoomDAO detailServiceDAO;
	private DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
	public PanelBookingEvent() throws RemoteException {
		billDAO = new BillDAO();
		detailServiceDAO = new DetailServiceRoomDAO();
		detailBillDAO = new DetailBillDAO();
		roomDAO = new RoomDAO();
	}

	public void xuLyTinhTienDienVaoThongTin(JTextField tfThue, JTextField tfTienPhong, JTextField tfTienDichVu, JTextField tfTongCong, JTextField tfThanhTien, JTextField tfMaHD) throws NumberFormatException, RemoteException {
		tfThue.setText("10%");
		Bill bill = billDAO.getBillsByBillID(Integer.valueOf(tfMaHD.getText().trim())).get(0);
		double tongTienPhong = bill.calTotalMoneyRoom();
		tfTienPhong.setText(formatter.format(tongTienPhong) + "");
		double tongTienDV = bill.calTotalMoneyService();
		tfTienDichVu.setText(formatter.format(tongTienDV) + "");
		double tong = tongTienPhong + tongTienDV;
		double thanhTien = tong + tong * 0.01;
		tfTongCong.setText(formatter.format(tong) + "");
		tfThanhTien.setText(formatter.format(thanhTien) + "");
	}

	@Override
	public void readDataToTableDichVu(DefaultTableModel modelDichVu, JTextField tfMaHD)
		throws NumberFormatException, RemoteException {
		// TODO Auto-generated method stub
		modelDichVu.setRowCount(0);
		List<DetailServiceRoom> detailServiceRooms = detailServiceDAO.searchDetailServiceRoomByBillID(Integer.valueOf(tfMaHD.getText().trim()));
		int i = 0;
		for (DetailServiceRoom d : detailServiceRooms) {
			modelDichVu.addRow(new Object[] { ++i, d.getService().getName(), d.getService().getUnit(),
					d.getQuantity(), d.getService().getPrice(), formatter.format(d.calculateMoneyService())});
		}
	}
	
	public void readDataToFieldThongTin(DefaultTableModel phongModel, JTable phongTable, JTextField tfMaHD, JTextField tfTenNhanVien, JTextField tfTenKH, JTextField tfSDTKhach, JTextField tfNgayThanhToan, JTextField tfGioThanhToan) throws RemoteException {
		List<DetailBill> detailBills = detailBillDAO
				.findDetailBillByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0));
		DetailBill detailBill = detailBills.get(detailBills.size() - 1);
		List<Bill> bills = billDAO.getBillsByBillID(detailBill.getBill().getId());
		Bill bill = bills.get(bills.size() - 1);
		bill.setPaymentDate(LocalDate.now());
		if(billDAO.updateBillAfterPayment(bill)) {
			tfMaHD.setText(bill.getId() + "");
			tfTenNhanVien.setText(bill.getEmployee().getName());
			tfTenKH.setText(bill.getCustomer().getCustomerName());
			tfSDTKhach.setText(bill.getCustomer().getPhoneNumber());
			tfNgayThanhToan.setText(bill.getPaymentDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
			tfGioThanhToan.setText(detailBill.getCheckin().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
		}
	}
	
	public void readDataToTablePhong(DefaultTableModel modelDichVu, DefaultTableModel modelPhong, JTextField tfMaHD) throws NumberFormatException, RemoteException {
		modelDichVu.setRowCount(0);
		List<DetailBill> detailBills = detailBillDAO.findDetailBillByBillID(Integer.valueOf(tfMaHD.getText().trim())).stream()
				.sorted(Comparator.comparing(DetailBill::getId))
				.toList();
		DetailBill detailBill = detailBills.get(detailBills.size() - 1);
		detailBill.setCheckout(LocalDateTime.now());
		detailBillDAO.updateCheckoutTime(detailBill);
		try {
			for (DetailBill d : detailBills) {
				modelPhong.addRow(new Object[] { d.getRoom().getName(),
						d.getRoom().getRoomType().getTypeRoom(), d.getCheckin().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")),
						d.getCheckout().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")), d.calculateTimeUsingRoomByMinute(),
						d.getRoom().getRoomType().getPrice(), formatter.format(d.translationRoomPrice())});
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void xuLyThanhToan(DefaultTableModel phongModel, JTable phongTable, TinhTien tinhTien) throws RemoteException {
		String roomName = (String) phongModel.getValueAt(phongTable.getSelectedRow(), 0);
		Room r = roomDAO.getRoomsByRoomName(roomName).get(0);
		List<DetailBill> detailBills = detailBillDAO.findDetailBillByRoomName(roomName);
		DetailBill detailBill = detailBills.get(detailBills.size() - 1);
		List<Bill> bills = billDAO.searchBillsByBillID(detailBill.getBill().getId());
		Bill bill = bills.get(bills.size() - 1);
		bill.setPaymentTime(LocalTime.now());
		bill.setTotal();
		r.setRoomStatus("Còn trống");
		try {
			if (billDAO.updateBillAfterPayment(bill)) {
				if (roomDAO.updateRoom(r)) {
					JOptionPane.showMessageDialog(tinhTien, "Thanh toán thành công");
				}
			} else {
				JOptionPane.showMessageDialog(tinhTien, "Thanh toán không thành công");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
