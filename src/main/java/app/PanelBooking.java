package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.github.lgooddatepicker.components.TimePicker;
import com.toedter.calendar.JDateChooser;
import dao.BillDAO;
import dao.BookingDAO;
import dao.CustomerDAO;
import dao.DetailBillDAO;
import dao.DetailServiceDAO;
import dao.EmployeeDAO;
import dao.RoomDAO;
import dao.ServiceDAO;
import entity.DetailServiceRoom;
import entity.DetailBill;
import entity.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import entity.Bill;
import entity.Customer;
import entity.Employee;
import entity.Booking;
import entity.Room;

public class PanelBooking extends JPanel {
	private JButton thuePBtn, phieuDatPhongBtn, datPBtn, chuyenPBtn, chiTietPBtn, dichVuPBtn, tinhTienPBtn, timKiemPBtn,
			lamMoiBtn;
	private JComboBox<String> tinhTrangB, soNguoiB, loaiPhongB;
	private JTextField phongF, giaPhongF;
	private JTable phongTable;
	private DefaultTableModel phongModel;
	private DigitalClock clock;
	private JLabel lbPhongTrong, lbPhongCho, lbPhongDangThue;
	private DecimalFormat formatter = new DecimalFormat("###,###,### VNĐ");
	private String manNV;
	private RoomDAO roomDAO;
	private BillDAO billDAO;
	private EmployeeDAO employeeDAO;
	private BookingDAO bookingDAO;
	private ChuyenPhong chuyenPhong;
	private ThuePhong thuePhong;
	private PhongCho phongCho;
	private CustomerDAO customerDAO;
	private DetailBillDAO detailBillDAO;
	private ChiTietPhong chiTietPhong;
	private DetailServiceDAO detailServiceDAO;
	private ServiceDAO serviceDAO;
	private DichVuPhong dichVuPhong;
	private TinhTien tinhTien;
	
	Icon imgAdd = new ImageIcon("src/main/java/img/add2.png");
	Icon imgDel = new ImageIcon("src/main/java/img/del.png");
	Icon imgReset = new ImageIcon("src/main/java/img/refresh16.png");
	Icon imgEdit = new ImageIcon("src/main/java/img/edit.png");
	Icon imgOut = new ImageIcon("src/main/java/img/out.png");
	Icon imgSearch = new ImageIcon("src/main/java/img/search.png");
	Icon imgCheck = new ImageIcon("src/main/java/img/check16.png");
	Icon imgCancel = new ImageIcon("src/main/java/img/cancel16.png");
	Icon imgBack = new ImageIcon("src/main/java/img/back16.png");
	Icon imgChange = new ImageIcon("src/img/change16.png");
	
	public static void main(String[] args) throws RemoteException {
		new PanelBooking("1").setVisible(true);
	}

	public String getManNV() {
		return manNV;
	}

	public void setManNV(String manNV) {
		this.manNV = manNV;
	}

	public PanelBooking(String maNV) throws RemoteException {
//		setSize(1400, 720);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setLocationRelativeTo(null);
		roomDAO = new RoomDAO();
		customerDAO = new CustomerDAO();
		bookingDAO = new BookingDAO();
		billDAO = new BillDAO();
		employeeDAO = new EmployeeDAO();
		detailBillDAO = new DetailBillDAO();
		detailServiceDAO = new DetailServiceDAO();
		serviceDAO = new ServiceDAO();
		setManNV(maNV);

		

		JLabel tinhTrangLb, soNguoiLb, loaiPLb, phongLb, giaPLb, locTinhTrangLb, sdtLb;
		JPanel mainPane, leftPane, rightPane, timePane, btnPane, panePhong, panePDP, paneBtnPhong, paneBtnPDP,
				paneTraCuuP;
		String[] headersTableP = { "Phòng", "Loại Phòng", "Số Người", "Tình Trạng", "Giá Phòng" };
		Border border = new LineBorder(Color.black);
		Border blackLine = BorderFactory.createLineBorder(Color.black);
		leftPane = new JPanel();
		rightPane = new JPanel();
		btnPane = new JPanel();

		// Pane left các chức năng
		GridLayout grid = new GridLayout(7, 0);
		grid.setVgap(20);
		JPanel btnMainPane = new JPanel(grid);
		btnMainPane.setBackground(Color.decode("#D0BAFB"));
		btnMainPane.add(thuePBtn = new ButtonGradient("Thuê Phòng"));
//		btnMainPane.add(datPBtn = new ButtonGradient("Đặt Phòng"));
		btnMainPane.add(phieuDatPhongBtn = new ButtonGradient("Phòng Chờ"));
		btnMainPane.add(chuyenPBtn = new ButtonGradient("Chuyển phòng"));
		btnMainPane.add(chiTietPBtn = new ButtonGradient("Chi tiết"));
		btnMainPane.add(dichVuPBtn = new ButtonGradient("Dịch vụ"));
		btnMainPane.add(tinhTienPBtn = new ButtonGradient("Thanh Toán"));
		thuePBtn.setFont(new Font("Sanserif", Font.BOLD, 20));
		thuePBtn.setBackground(Color.decode("#6fa8dc"));
		thuePBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, thuePBtn.getMinimumSize().height));

//		datPBtn.setFont(new Font("Sanserif", Font.BOLD, 20));
//		datPBtn.setBackground(Color.decode("#6fa8dc"));
//		datPBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, datPBtn.getMinimumSize().height));

		phieuDatPhongBtn.setFont(new Font("Sanserif", Font.BOLD, 20));
		phieuDatPhongBtn.setBackground(Color.decode("#6fa8dc"));
		phieuDatPhongBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, phieuDatPhongBtn.getMinimumSize().height));
		chuyenPBtn.setFont(new Font("Sanserif", Font.BOLD, 20));
		chuyenPBtn.setBackground(Color.decode("#6fa8dc"));
		chuyenPBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, chuyenPBtn.getMinimumSize().height));
		chiTietPBtn.setBackground(Color.decode("#6fa8dc"));
		chiTietPBtn.setFont(new Font("Sanserif", Font.BOLD, 20));
		chiTietPBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, chiTietPBtn.getMinimumSize().height));
		dichVuPBtn.setFont(new Font("Sanserif", Font.BOLD, 20));
		dichVuPBtn.setBackground(Color.decode("#6fa8dc"));
		dichVuPBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, dichVuPBtn.getMinimumSize().height));
		tinhTienPBtn.setFont(new Font("Sanserif", Font.BOLD, 20));
		tinhTienPBtn.setBackground(Color.decode("#6fa8dc"));
		tinhTienPBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, tinhTienPBtn.getMinimumSize().height));

		btnPane.add(btnMainPane);
//		btnPane.setBorder(border);
		btnPane.setBackground(Color.decode("#D0BAFB"));

		GridLayout gridForPane = new GridLayout(3, 0);
		gridForPane.setVgap(2);
		JPanel pane = new JPanel(gridForPane);
		pane.add(lbPhongCho = new JLabel("Phòng Chờ ()"));
		pane.add(lbPhongTrong = new JLabel("Phòng Trống ()"));
		pane.add(lbPhongDangThue = new JLabel("Phòng Đang Thuê ()"));
		pane.setBackground(Color.decode("#D0BAFB"));

		Box leftBox = Box.createVerticalBox();
//		leftBox.add(clock = new DigitalClock());
		leftBox.add(Box.createVerticalStrut(10));
		leftBox.add(btnPane);
		leftBox.add(Box.createVerticalStrut(5));
		leftBox.add(pane);
		leftPane.add(leftBox);
		leftPane.setBackground(Color.decode("#D0BAFB"));

		// Pane Right
		panePhong = new JPanel();
		panePhong.setLayout(new BorderLayout());
		paneBtnPhong = new JPanel(new BorderLayout());
		paneTraCuuP = new JPanel();

		// Table Phong
		phongModel = new DefaultTableModel(headersTableP, 0);
		phongTable = new JTable(phongModel) {
			@Override
			public Class<?> getColumnClass(int column) {
				if (convertColumnIndexToModel(column) == 3)
					return Double.class;
				return super.getColumnClass(column);
			}
		};
		phongTable.setAutoCreateRowSorter(true);
		phongTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		phongTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		phongTable.setRowHeight(40);
		phongTable.setFont(new Font("serif", Font.PLAIN, 17));
		phongTable.setDefaultRenderer(Double.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				c.setForeground(((String) value).equalsIgnoreCase("Đang thuê") ? Color.RED
						: ((String) value).equalsIgnoreCase("Còn trống") ? Color.green : Color.BLUE);
				return c;
			}
		});

//		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
//		phongTable.setDefaultRenderer(String.class, centerRenderer);

		JScrollPane scrollPhongTable = new JScrollPane(phongTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPhongTable.setBorder(BorderFactory.createTitledBorder(blackLine, "Danh sách thông tin phòng"));
		scrollPhongTable.setBackground(Color.decode("#D0BAFB"));
		JPanel pnlForTablePhong = new JPanel(new BorderLayout());
		pnlForTablePhong.add(scrollPhongTable, BorderLayout.CENTER);
		pnlForTablePhong.add(leftPane, BorderLayout.EAST);
		panePhong.add(pnlForTablePhong, BorderLayout.CENTER);

//		//Btn for phong
//		GridLayout grid2 = new GridLayout(5, 0);
//		grid2.setVgap(5);
//		Box boxForBtnPhong = Box.createVerticalBox();
//		boxForBtnPhong.add(thuePBtn);
//		boxForBtnPhong.add(chuyenPBtn);
//		boxForBtnPhong.add(chiTietPBtn);
//		boxForBtnPhong.add(dichVuPBtn);
//		boxForBtnPhong.add(tinhTienPBtn);
//		JPanel pnlForBoxForBtnPhong = new JPanel();
//		pnlForBoxForBtnPhong.add(boxForBtnPhong);
//		
//		panePhong.add(pnlForBoxForBtnPhong, BorderLayout.EAST);

		// Box các btn phòng
		Box btnPhongBox = Box.createHorizontalBox();

		// pane tra cứu phòng
		Box traCuuB = Box.createVerticalBox();
		Box traCuuB1 = Box.createHorizontalBox();
		Box traCuuB2 = Box.createHorizontalBox();

		JPanel panePhongLb = new JPanel();
		panePhongLb.setBackground(Color.decode("#D0BAFB"));
		panePhongLb.add(phongLb = new JLabel("Phòng"));
		traCuuB1.add(panePhongLb);

		JPanel panePhongF = new JPanel();
		panePhongF.setBackground(Color.decode("#D0BAFB"));
		panePhongF.add(phongF = new JTextField(10));
		phongF.setFont(new Font("Arial", Font.PLAIN, 16));
		traCuuB1.add(panePhongF);

		traCuuB1.add(timKiemPBtn = new ButtonGradient("Tìm kiếm", imgSearch));

		JPanel paneGiaLb = new JPanel();
		paneGiaLb.setBackground(Color.decode("#D0BAFB"));
		paneGiaLb.add(giaPLb = new JLabel("Giá phòng"));
		traCuuB2.add(paneGiaLb);

		JPanel paneGiaPhongF = new JPanel();
		paneGiaPhongF.setBackground(Color.decode("#D0BAFB"));
		paneGiaPhongF.add(giaPhongF = new JTextField(10));
		giaPhongF.setFont(new Font("Arial", Font.PLAIN, 16));
		traCuuB2.add(paneGiaPhongF);

		traCuuB2.add(lamMoiBtn = new ButtonGradient("Làm mới", imgReset));

		traCuuB.add(traCuuB1);
		traCuuB.add(Box.createVerticalStrut(10));
		traCuuB.add(traCuuB2);
		paneTraCuuP.add(traCuuB);
		paneTraCuuP.setBackground(Color.decode("#D0BAFB"));
		paneTraCuuP.setBorder(BorderFactory.createTitledBorder(blackLine, "Tra cứu"));

		phongLb.setPreferredSize(giaPLb.getPreferredSize());
		timKiemPBtn.setPreferredSize(lamMoiBtn.getPreferredSize());
		lamMoiBtn.setPreferredSize(timKiemPBtn.getPreferredSize());

		// Btn Phong
		String[] headersTinhTrang = { "Còn trống", "Tất cả", "Đang thuê", "Đã đặt trước" };
		String[] headersSoNguoi = { "Tất cả", "15", "25" };
		String[] headersLoaiPhong = { "Tất cả", "Vip", "Thường" };

		// pane combobox tình trạng
		Box btnPhongBox1 = Box.createHorizontalBox();
		btnPhongBox1.add(tinhTrangLb = new JLabel("Tình trạng phòng "));
		btnPhongBox1.add(tinhTrangB = new JComboBox<>(headersTinhTrang));
		JPanel paneCBTinhTrang = new JPanel();
		paneCBTinhTrang.setBackground(Color.decode("#D0BAFB"));
		paneCBTinhTrang.add(btnPhongBox1);
		btnPhongBox.add(paneCBTinhTrang);
		btnPhongBox.add(Box.createHorizontalStrut(5));

		// pane combobox số người
		Box btnPhongBox2 = Box.createHorizontalBox();
		btnPhongBox2.add(soNguoiLb = new JLabel("Số người "));
		btnPhongBox2.add(soNguoiB = new JComboBox<>(headersSoNguoi));
		JPanel paneCBSoNguoi = new JPanel();
		paneCBSoNguoi.setBackground(Color.decode("#D0BAFB"));
		paneCBSoNguoi.add(btnPhongBox2);
		btnPhongBox.add(paneCBSoNguoi);
		btnPhongBox.add(Box.createHorizontalStrut(5));

		// pane combobox loại phòng
		Box btnPhongBox3 = Box.createHorizontalBox();
		btnPhongBox3.add(loaiPLb = new JLabel("Loại phòng "));
		btnPhongBox3.add(loaiPhongB = new JComboBox<>(headersLoaiPhong));
		JPanel paneCBLoaiPhong = new JPanel();
		paneCBLoaiPhong.setBackground(Color.decode("#D0BAFB"));
		paneCBLoaiPhong.add(btnPhongBox3);
		btnPhongBox.add(paneCBLoaiPhong);
		btnPhongBox.add(Box.createHorizontalStrut(5));

		// Thêm pane tra cứu vào Box
		btnPhongBox.add(paneTraCuuP);

		soNguoiB.setPreferredSize(tinhTrangB.getPreferredSize());
		loaiPhongB.setPreferredSize(tinhTrangB.getPreferredSize());

		paneBtnPhong.add(clock = new DigitalClock(), BorderLayout.WEST);
		paneBtnPhong.add(btnPhongBox, BorderLayout.CENTER);
		paneBtnPhong.setBackground(Color.decode("#D0BAFB"));

		panePhong.add(paneBtnPhong, BorderLayout.NORTH);
		panePhong.setBackground(Color.decode("#D0BAFB"));
		
		setLayout(new BorderLayout());;
		add(panePhong, BorderLayout.CENTER);

//		JPanel main = new JPanel();
//		main.setLayout(new BorderLayout());
//		main.add(panePhong, BorderLayout.CENTER);

//		this.getContentPane().add(main);
		getAllDataRooms();

		thuePBtn.setMnemonic(KeyEvent.VK_F1);
		thuePBtn.setToolTipText("Click ALT F1");
		chuyenPBtn.setMnemonic(KeyEvent.VK_F2);
		chuyenPBtn.setToolTipText("Click ALT F2");
		chiTietPBtn.setMnemonic(KeyEvent.VK_F3);
		chiTietPBtn.setToolTipText("Click ALT F3");
		dichVuPBtn.setMnemonic(KeyEvent.VK_F4);
		dichVuPBtn.setToolTipText("Click ALT F4");
		tinhTienPBtn.setMnemonic(KeyEvent.VK_F5);
		tinhTienPBtn.setToolTipText("Click ALT F5");

		chiTietPBtn.addActionListener(e -> xuLyChiTietPhong());
		dichVuPBtn.addActionListener(e -> xuLyDichVuPhong());
		tinhTrangB.addActionListener(e -> xuLyLocTheoTinhTrangPhong());
		soNguoiB.addActionListener(e -> xuLyLocTheoSoNguoi());
		loaiPhongB.addActionListener(e -> xuLyLocTheoLoaiPhong());
		timKiemPBtn.addActionListener(e -> xuLyTimKiemPhong());
		thuePBtn.addActionListener(e -> xuLyThuePhong());
		chuyenPBtn.addActionListener(e -> {
			try {
				xuLyChuyenPhong();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		phieuDatPhongBtn.addActionListener(e -> {
			try {
				xuLyNhanPhongCho();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		tinhTienPBtn.addActionListener(e -> xuLyTinhTien());
	}

	private void getAllDataRooms() {
		phongModel.setRowCount(0);
		List<Room> rooms = roomDAO.getAllRooms();
		for (Room room : rooms) {
			phongModel.addRow(new Object[] { room.getName(), room.getRoomType().getTypeRoom(),
					room.getRoomType().getCapacity(), room.getRoomStatus(), room.getRoomType().getPrice() });
		}

		long soPhongCho = rooms.stream().filter(p -> p.getRoomStatus().equalsIgnoreCase("Đã đặt trước")).count();
		long soPhongThue = rooms.stream().filter(p -> p.getRoomStatus().equalsIgnoreCase("Đang Thuê")).count();
		long soPhongTrong = rooms.stream().filter(p -> p.getRoomStatus().equalsIgnoreCase("Còn trống")).count();
		lbPhongTrong.setText("Phòng Trống ( " + soPhongTrong + " )");
		lbPhongDangThue.setText("Phòng Thuê ( " + soPhongThue + " )");
		lbPhongCho.setText("Phòng Còn Trống ( " + soPhongCho + " )");
		lbPhongTrong.setFont(new Font("sanserif", Font.BOLD, 14));
		lbPhongCho.setFont(new Font("sanserif", Font.BOLD, 14));
		lbPhongDangThue.setFont(new Font("sanserif", Font.BOLD, 14));
	}

	private void xuLyTimKiemPhong() {
		if (phongF.getText().equals("") && giaPhongF.getText().equals("")) {

		} else {
			phongModel.setRowCount(0);
			List<Room> rooms = roomDAO.getAllRooms();
			if (!phongF.getText().trim().equals("")) {
				rooms = roomDAO.getRoomsByRoomName(phongF.getText().trim());
			}

			if (!giaPhongF.getText().trim().equals("")) {
				rooms = rooms.stream()
						.filter(r -> r.getRoomType().getPrice() == Double.valueOf(giaPhongF.getText().trim()))
						.collect(Collectors.toList());
			}

			for (Room room : rooms) {
				phongModel.addRow(new Object[] { room.getName(), room.getRoomType().getTypeRoom(),
						room.getRoomType().getCapacity(), room.getRoomStatus(), room.getRoomType().getPrice() });
			}
		}
	}

	public void xuLyLocTheoTinhTrangPhong() {
		phongModel.setRowCount(0);
		List<Room> rooms = roomDAO.getRoomsByStatus((String) tinhTrangB.getSelectedItem());
		if (((String) tinhTrangB.getSelectedItem()).equalsIgnoreCase("Tất cả")) {
			getAllDataRooms();
		}
		for (Room room : rooms) {
			phongModel.addRow(new Object[] { room.getName(), room.getRoomType().getTypeRoom(),
					room.getRoomType().getCapacity(), room.getRoomStatus(), room.getRoomType().getPrice() });
		}
	}

	private void xuLyLocTheoLoaiPhong() {
		phongModel.setRowCount(0);
		List<Room> rooms = roomDAO.getRoomsByType((String) loaiPhongB.getSelectedItem());
		if (((String) loaiPhongB.getSelectedItem()).equalsIgnoreCase("Tất cả")) {
			getAllDataRooms();

		}
		for (Room room : rooms) {
			phongModel.addRow(new Object[] { room.getName(), room.getRoomType().getTypeRoom(),
					room.getRoomType().getCapacity(), room.getRoomStatus(), room.getRoomType().getPrice() });
		}
	}

	private void xuLyLocTheoSoNguoi() {
		phongModel.setRowCount(0);
		List<Room> dsP = roomDAO.getRoomsByCapacity((String) soNguoiB.getSelectedItem());
		if (((String) soNguoiB.getSelectedItem()).equalsIgnoreCase("Tất cả")) {
			getAllDataRooms();
		}
		for (Room room : dsP) {
			phongModel.addRow(new Object[] { room.getName(), room.getRoomType().getTypeRoom(),
					room.getRoomType().getCapacity(), room.getRoomStatus(), room.getRoomType().getPrice() });
		}
	}

	private void xuLyThuePhong() {
		Room room = roomDAO.getRoomsByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0)).get(0);
		if (phongTable.getSelectedRow() != -1) {
			if (room.getRoomStatus().equalsIgnoreCase("Còn trống")) {
				thuePhong = new ThuePhong();
				thuePhong.setVisible(true);
				thuePhong.setAlwaysOnTop(true);
				thuePhong.setLocationRelativeTo(null);
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chỉ được thuê phòng đang trống!!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Hãy chọn phòng bạn muốn đặt!!");
		}
	}

	private void xuLyChuyenPhong() throws RemoteException {
		if (phongTable.getSelectedRow() != -1) {
			if (((String) phongModel.getValueAt(phongTable.getSelectedRow(), 3)).equalsIgnoreCase("Đang thuê")) {
				chuyenPhong = new ChuyenPhong((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0));
				chuyenPhong.setVisible(true);
				chuyenPhong.setAlwaysOnTop(true);
				chuyenPhong.setLocationRelativeTo(null);
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chỉ được chuyển phòng đang thuê!!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Hãy chọn phòng bạn muốn chuyển!!");
		}
	}

	private void xuLyNhanPhongCho() throws RemoteException {
		phongCho = new PhongCho();
		phongCho.setVisible(true);
		phongCho.setLocationRelativeTo(null);
	}
	
	private void xuLyChiTietPhong() {
		Room room = roomDAO.getRoomsByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0)).get(0);
		if (phongTable.getSelectedRow() != -1) {
			if (room.getRoomStatus().equalsIgnoreCase("Đang thuê")) {
				chiTietPhong = new ChiTietPhong();
				chiTietPhong.setVisible(true);
				chiTietPhong.setAlwaysOnTop(true);
				chiTietPhong.setLocationRelativeTo(null);
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chỉ được xem chi tiết phòng đang thuê!!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Hãy chọn phòng bạn muốn xem chi tiết!!");
		}
	}
	
	private void xuLyDichVuPhong() {
		if (phongTable.getSelectedRow() != -1) {
			if (((String) phongModel.getValueAt(phongTable.getSelectedRow(), 3)).equalsIgnoreCase("Đang thuê")) {
				dichVuPhong = new DichVuPhong();
				dichVuPhong.setVisible(true);
				dichVuPhong.setAlwaysOnTop(true);
				dichVuPhong.setLocationRelativeTo(null);
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chỉ được thêm dịch vụ vào phòng đang thuê!!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Hãy chọn phòng bạn muốn thêm dịch vụ!!");
		}
	}
	
	private void xuLyTinhTien() {
		if (phongTable.getSelectedRow() != -1) {
			if (((String) phongModel.getValueAt(phongTable.getSelectedRow(), 3)).equalsIgnoreCase("Đang thuê")) {
				tinhTien = new TinhTien();
				tinhTien.setVisible(true);
				tinhTien.setAlwaysOnTop(true);
				tinhTien.setLocationRelativeTo(null);
			} else {
				JOptionPane.showMessageDialog(this, "Bạn chỉ được thanh toán phòng đang thuê!!");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Hãy chọn phòng bạn muốn thanh toán!!");
		}

	}

	public class ThuePhong extends JFrame {
		private JTextField tenPhongF, loaiPhongF, giaPhongF, sucChuaF, tinhTrangF, sdtKhachF, tenKhachF, soLuongF;
		private JTextArea ghiChuA;
		private JDateChooser ngayNhanCD;
		private TimePicker gioNhanTP;
		private JButton kiemTraBtn, quayLaiBtn, thuePhongBtn;
		private JComboBox<String> cbLuaChon;
		private String tenKH = "", sdtKH = "";
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat gioFormat = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat dayFormat2 = new SimpleDateFormat("dd/MM/yyyy");

		public ThuePhong() {
			setSize(700, 550);

			JLabel luaChonLb, soLuongLb, tenPhongLb, loaiPhongLb, giaPhongLb, sucChuaLb, tinhTrangLb, sdtKhachLb,
					tenKhachLb, ngayNhanPhongLb, gioNhanPhongLb, ghiChuLb, tieuDeLb;
			JPanel titlePanel, mainPanel, leftPanel, rightPanel, bottomPanelRight, bottomPanelLeft, bottomPanel;
			Font font = new Font("Arial", Font.BOLD, 24);
			Border border = new LineBorder(Color.black);
			mainPanel = new JPanel(new BorderLayout());
			titlePanel = new JPanel();
			leftPanel = new JPanel();
			rightPanel = new JPanel();
			bottomPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			bottomPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
			bottomPanel = new JPanel(new GridLayout(1, 2));

			String[] headersLuaChon = { "Đặt Trước", "Thuê Liền" };
			cbLuaChon = new JComboBox<>(headersLuaChon);
			// set thông tin phòng
			leftPanel.setBackground(Color.decode("#cccccc"));
			leftPanel.setBorder(BorderFactory.createTitledBorder(border, "Thông tin phòng"));

			Box thongTinPhongBox = Box.createVerticalBox();

			Box tenPhongBox = Box.createHorizontalBox();
			tenPhongBox.add(tenPhongLb = new JLabel("Tên phòng:"));
			tenPhongBox.add(Box.createHorizontalStrut(5));
			tenPhongBox.add(tenPhongF = new JTextField(10));
			tenPhongF.setFont(new Font("Arial", Font.PLAIN, 16));
			tenPhongF.setBorder(null);
			tenPhongF.setEditable(false);
			tenPhongLb.setFont(new Font("Arial", Font.BOLD, 16));

			Box loaiPhongBox = Box.createHorizontalBox();
			loaiPhongBox.add(loaiPhongLb = new JLabel("Loại phòng:"));
			loaiPhongBox.add(Box.createHorizontalStrut(5));
			loaiPhongBox.add(loaiPhongF = new JTextField(10));
			loaiPhongF.setFont(new Font("Arial", Font.PLAIN, 16));
			loaiPhongF.setBorder(null);
			loaiPhongF.setEditable(false);
			loaiPhongLb.setFont(new Font("Arial", Font.BOLD, 16));

			Box giaPhongBox = Box.createHorizontalBox();
			giaPhongBox.add(giaPhongLb = new JLabel("Giá phòng:"));
			giaPhongBox.add(Box.createHorizontalStrut(5));
			giaPhongBox.add(giaPhongF = new JTextField(10));
			giaPhongF.setFont(new Font("Arial", Font.PLAIN, 16));
			giaPhongF.setBorder(null);
			giaPhongF.setEditable(false);
			giaPhongLb.setFont(new Font("Arial", Font.BOLD, 16));

			Box sucChuaBox = Box.createHorizontalBox();
			sucChuaBox.add(sucChuaLb = new JLabel("Sức chứa:"));
			sucChuaBox.add(Box.createHorizontalStrut(5));
			sucChuaBox.add(sucChuaF = new JTextField(10));
			sucChuaF.setFont(new Font("Arial", Font.PLAIN, 16));
			sucChuaF.setBorder(null);
			sucChuaF.setEditable(false);
			sucChuaLb.setFont(new Font("Arial", Font.BOLD, 16));

			Box tinhTrangBox = Box.createHorizontalBox();
			tinhTrangBox.add(tinhTrangLb = new JLabel("Tình trạng:"));
			tinhTrangBox.add(Box.createHorizontalStrut(5));
			tinhTrangBox.add(tinhTrangF = new JTextField(10));
			tinhTrangF.setFont(new Font("Arial", Font.PLAIN, 16));
			tinhTrangF.setBorder(null);
			tinhTrangF.setEditable(false);
			tinhTrangLb.setFont(new Font("Arial", Font.BOLD, 16));

			tenPhongF.setBackground(Color.decode("#cccccc"));
			loaiPhongF.setBackground(Color.decode("#cccccc"));
			giaPhongF.setBackground(Color.decode("#cccccc"));
			sucChuaF.setBackground(Color.decode("#cccccc"));
			tinhTrangF.setBackground(Color.decode("#cccccc"));

			thongTinPhongBox.add(tenPhongBox);
			thongTinPhongBox.add(Box.createVerticalStrut(25));
			thongTinPhongBox.add(loaiPhongBox);
			thongTinPhongBox.add(Box.createVerticalStrut(25));
			thongTinPhongBox.add(giaPhongBox);
			thongTinPhongBox.add(Box.createVerticalStrut(25));
			thongTinPhongBox.add(sucChuaBox);
			thongTinPhongBox.add(Box.createVerticalStrut(25));
			thongTinPhongBox.add(tinhTrangBox);
			leftPanel.add(thongTinPhongBox);

			tenPhongLb.setPreferredSize(loaiPhongLb.getPreferredSize());
			tinhTrangLb.setPreferredSize(loaiPhongLb.getPreferredSize());
			giaPhongLb.setPreferredSize(loaiPhongLb.getPreferredSize());
			sucChuaLb.setPreferredSize(loaiPhongLb.getPreferredSize());

			// set thông tin thuê phòng
			rightPanel = new JPanel();
			rightPanel.setBorder(BorderFactory.createTitledBorder(border, "Thông tin thuê phòng"));
			rightPanel.setBackground(Color.decode("#cccccc"));
			Box thongTinThuePhongBox = Box.createVerticalBox();
			Box boxForLuaChon = Box.createHorizontalBox();
			boxForLuaChon.add(luaChonLb = new JLabel("Lựa Chọn:"));
			boxForLuaChon.add(Box.createHorizontalStrut(5));
			boxForLuaChon.add(cbLuaChon);
			luaChonLb.setFont(new Font("Arial", Font.BOLD, 14));

			Box sdtKhachBox = Box.createHorizontalBox();
			sdtKhachBox.add(sdtKhachLb = new JLabel("SĐT Khách:"));
			JPanel sdtKhachPane = new JPanel();
			sdtKhachPane.add(sdtKhachF = new JTextField(15));
			sdtKhachPane.setBackground(Color.decode("#cccccc"));
			sdtKhachBox.add(sdtKhachPane);
			sdtKhachBox.add(Box.createHorizontalStrut(5));
			sdtKhachBox.add(kiemTraBtn = new ButtonGradient("Kiểm tra", imgCheck));
			kiemTraBtn.setBackground(Color.decode("#6fa8dc"));
			sdtKhachLb.setFont(new Font("Arial", Font.BOLD, 14));

			Box tenKhachBox = Box.createHorizontalBox();
			tenKhachBox.add(tenKhachLb = new JLabel("Tên khách:"));
			tenKhachBox.add(Box.createHorizontalStrut(5));
			tenKhachBox.add(tenKhachF = new JTextField(15));
			tenKhachLb.setFont(new Font("Arial", Font.BOLD, 14));

			Box soLuongBox = Box.createHorizontalBox();
			soLuongBox.add(soLuongLb = new JLabel("Số lượng khách:"));
			soLuongBox.add(Box.createHorizontalStrut(5));
			soLuongBox.add(soLuongF = new JTextField(15));
			soLuongLb.setFont(new Font("Arial", Font.BOLD, 14));

			Box ngayNhanBox = Box.createHorizontalBox();
			ngayNhanBox.add(ngayNhanPhongLb = new JLabel("Ngày nhận phòng:"));
			ngayNhanBox.add(Box.createHorizontalStrut(5));
			ngayNhanBox.add(ngayNhanCD = new JDateChooser());
			ngayNhanPhongLb.setFont(new Font("Arial", Font.BOLD, 14));

			Box gioNhanBox = Box.createHorizontalBox();
			gioNhanBox.add(gioNhanPhongLb = new JLabel("Giờ nhận phòng:"));
			gioNhanBox.add(Box.createHorizontalStrut(5));
			gioNhanBox.add(gioNhanTP = new TimePicker());
			gioNhanPhongLb.setFont(new Font("Arial", Font.BOLD, 14));

			Box ghiChuBox = Box.createHorizontalBox();
			ghiChuBox.add(ghiChuLb = new JLabel("Ghi chú:"));
			ghiChuBox.add(Box.createHorizontalStrut(5));
			ghiChuBox.add(ghiChuA = new JTextArea(3, 15));
			ghiChuLb.setFont(new Font("Arial", Font.BOLD, 14));

			thongTinThuePhongBox.add(boxForLuaChon);
			thongTinThuePhongBox.add(Box.createVerticalStrut(20));
			thongTinThuePhongBox.add(sdtKhachBox);
			thongTinThuePhongBox.add(Box.createVerticalStrut(20));
			thongTinThuePhongBox.add(tenKhachBox);
			thongTinThuePhongBox.add(Box.createVerticalStrut(20));
			thongTinThuePhongBox.add(soLuongBox);
			thongTinThuePhongBox.add(Box.createVerticalStrut(20));
			thongTinThuePhongBox.add(ngayNhanBox);
			thongTinThuePhongBox.add(Box.createVerticalStrut(20));
			thongTinThuePhongBox.add(gioNhanBox);
			thongTinThuePhongBox.add(Box.createVerticalStrut(20));
			thongTinThuePhongBox.add(ghiChuBox);
			rightPanel.add(thongTinThuePhongBox);

			tenKhachLb.setPreferredSize(ngayNhanPhongLb.getPreferredSize());
			gioNhanPhongLb.setPreferredSize(ngayNhanPhongLb.getPreferredSize());
			sdtKhachLb.setPreferredSize(ngayNhanPhongLb.getPreferredSize());
			ghiChuLb.setPreferredSize(ngayNhanPhongLb.getPreferredSize());
			luaChonLb.setPreferredSize(ngayNhanPhongLb.getPreferredSize());
			soLuongLb.setPreferredSize(ngayNhanPhongLb.getPreferredSize());

//			set Bottom pane

			bottomPanelLeft.add(quayLaiBtn = new ButtonGradient("Quay lại  ", imgBack));
			bottomPanelRight.add(thuePhongBtn = new ButtonGradient("Xác nhận", imgCheck));
			bottomPanelRight.setBackground(Color.decode("#D0BAFB"));
			bottomPanelLeft.setBackground(Color.decode("#D0BAFB"));
			bottomPanel.add(bottomPanelLeft);
			bottomPanel.add(bottomPanelRight);

			quayLaiBtn.setHorizontalAlignment(SwingConstants.RIGHT);
			quayLaiBtn.setBackground(Color.decode("#6fa8dc"));
			thuePhongBtn.setBackground(Color.decode("#6fa8dc"));
			quayLaiBtn.setPreferredSize(quayLaiBtn.getPreferredSize());
			thuePhongBtn.setPreferredSize(quayLaiBtn.getPreferredSize());
			kiemTraBtn.setPreferredSize(quayLaiBtn.getPreferredSize());

			// set tiêu đề
			titlePanel.setBackground(Color.decode("#990447"));
			titlePanel.add(tieuDeLb = new JLabel("THUÊ PHÒNG"));
			tieuDeLb.setFont(font);

			mainPanel.add(titlePanel, BorderLayout.NORTH);
			mainPanel.add(leftPanel, BorderLayout.WEST);
			mainPanel.add(rightPanel, BorderLayout.CENTER);
			mainPanel.add(bottomPanel, BorderLayout.SOUTH);
			this.getContentPane().add(mainPanel);
			this.setBackground(Color.decode("#e6dbd1"));

			readDataToFieldsTTPhong();
			cbLuaChon.addActionListener(e -> xuLyThoiGian());
			thuePhongBtn.addActionListener(e -> {
				try {
					xuLyThuePhong();
				} catch (NumberFormatException | RemoteException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			kiemTraBtn.addActionListener(e -> {
				try {
					xuLyKiemTraSDT();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}

		private void readDataToFieldsTTPhong() {
			Room room = roomDAO.getRoomsByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0))
					.get(0);
			tenPhongF.setText(room.getName());
			loaiPhongF.setText(room.getRoomType().getTypeRoom());
			giaPhongF.setText(formatter.format(room.getRoomType().getPrice()) + "");
			sucChuaF.setText(room.getRoomType().getCapacity() + "");
			tinhTrangF.setText(room.getRoomStatus());
		}

		private void xuLyThuePhong() throws NumberFormatException, ParseException, RemoteException {

			if (matchPhone() && matchSoNguoi() && matchNgayDatPhong() && matchGioNhanPhong()) {
				String hour = gioNhanTP.getTime().toString().substring(0, 2);
				String minute = gioNhanTP.getTime().toString().substring(3, 5);
				String time = hour + ":" + minute + ":" + "00";
				String date = dayFormat.format(ngayNhanCD.getDate());
				tenKH = tenKhachF.getText().trim();
				sdtKH = sdtKhachF.getText().trim();
				Customer customer;
				List<Customer> customers = customerDAO.findCustomersByPhoneNumber(sdtKhachF.getText());
				if (customers.size() == 0) {
					customer = new Customer(0, tenKH, sdtKH);
					customerDAO.addCustomer(customer);
				}
				if (((String) cbLuaChon.getSelectedItem()).equalsIgnoreCase("Đặt trước")) {
					Room phong = roomDAO.getRoomsByRoomName(tenPhongF.getText().trim()).get(0);
					Booking pdp = new Booking(0,
							LocalDateTime.of(
									ngayNhanCD.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
									gioNhanTP.getTime()),
							1, ghiChuA.getText(), phong,
							customerDAO.findCustomersByPhoneNumber(sdtKhachF.getText()).get(0),
							employeeDAO.getEmployeeByID(Integer.valueOf("1")));
					try {
						boolean b = bookingDAO.createBooking(pdp);
						System.out.println(b);
						if (b) {
							if (roomDAO.updateRoomStatusByRoomID("Đã đặt trước", phong.getId())) {
								JOptionPane.showMessageDialog(this, "Đặt phòng thành công!!!");
								tinhTrangB.setSelectedItem("Đã đặt trước");
								this.dispose();
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				else {
					Bill hd = new Bill(0,
							dateFormat.parse(date + " " + time).toInstant().atZone(ZoneId.systemDefault())
									.toLocalDate(),
							gioNhanTP.getTime(), employeeDAO.getEmployeeByID(Integer.valueOf("1")),
							customerDAO.findCustomersByPhoneNumber(sdtKhachF.getText()).get(0));
					boolean b1 = billDAO.createBill(hd);
					Room phong = roomDAO.getRoomsByRoomName2(tenPhongF.getText().trim()).get(0);
					try {
						if (b1) {
							boolean b2 = roomDAO.updateRoomStatusByRoomID("Đang thuê", phong.getId());
							if (b2) {
								DetailBill cthd = new DetailBill(0, phong, hd, LocalDateTime.now(), null);
								boolean b3 = detailBillDAO.createDetailBill(cthd);
								if (b3) {
									JOptionPane.showMessageDialog(this, "Thuê phòng thành công!!!");
									getAllDataRooms();
									tinhTrangB.setSelectedItem("Đang thuê");
									this.dispose();
								}
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			} else {
			}
		}

		private void xuLyThoiGian() {
			ngayNhanCD.setDate(new Date());
			if (((String) cbLuaChon.getSelectedItem()).equalsIgnoreCase("Thuê liền")) {
				gioNhanTP.setTime(LocalTime.now());

			} else {
			}
		}

		private void xuLyKiemTraSDT() throws RemoteException {
			Pattern pattern = Pattern.compile("^\\d{10}$");
			Matcher matcher = pattern.matcher(sdtKhachF.getText().trim());
			if (matcher.matches()) {
				tenKH = tenKhachF.getText().trim();
				sdtKH = sdtKhachF.getText().trim();
				List<Customer> customers = customerDAO.findCustomersByPhoneNumber(sdtKhachF.getText());
				if (customers.size() > 0) {
					tenKhachF.setText(customers.get(0).getCustomerName());
				} else {
					JOptionPane.showMessageDialog(this, "Khách hàng mới");
					tenKhachF.setText("");
				}
			} else {
				sdtKhachF.selectAll();
				sdtKhachF.requestFocus();
				JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 chữ số");
			}
		}

		private boolean matchPhone() {
			Pattern pattern = Pattern.compile("^\\d{10}$");
			Matcher matcher = pattern.matcher(sdtKhachF.getText().trim());
			if (!matcher.matches()) {
				sdtKhachF.selectAll();
				sdtKhachF.requestFocus();
				JOptionPane.showMessageDialog(this, "Số điện thoại phải gồm 10 chữ số");
			}
			return matcher.matches();
		}

		private boolean matchSoNguoi() {
			Room room = roomDAO.getRoomsByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0))
					.get(0);
			Pattern p = Pattern.compile("^\\d+$");
			Matcher matcher = p.matcher(soLuongF.getText().trim());
			if (!matcher.matches()) {
				soLuongF.selectAll();
				soLuongF.requestFocus();
				JOptionPane.showMessageDialog(this, "Số người phải là số!!");
			} else {
				if (Integer.valueOf(soLuongF.getText()) > room.getRoomType().getPrice()) {
					soLuongF.selectAll();
					soLuongF.requestFocus();
					JOptionPane.showMessageDialog(this, "Số người không phù hợp với sức chứa của phòng!!");
					return false;
				}
			}
			return matcher.matches();
		}

		private boolean matchNgayDatPhong() {
			String s = ((JTextField) ngayNhanCD.getDateEditor().getUiComponent()).getText();
			String[] s1 = s.split(" ");
			if (Integer.valueOf(s1[0]) < LocalDate.now().getDayOfMonth()) {
				JOptionPane.showMessageDialog(this, "Ngày đặt phòng không phù hợp!!");
				return false;
			}
			if (Integer.valueOf(s1[0]) - LocalDate.now().getDayOfMonth() > 1) {
				JOptionPane.showMessageDialog(this, "Chỉ được đặt phòng trong hôm nay hoặc ngày mai!!!");
				return false;
			}
			return true;
		}

		private boolean matchGioNhanPhong() {
			String[] s = gioNhanTP.getText().split(":");
			int hour = Integer.valueOf(s[0]);
			String str = String.valueOf(gioNhanTP.getText().charAt(gioNhanTP.getText().length() - 2));
			if (str.equalsIgnoreCase("p") && hour != 12)
				hour = hour + 12;

			if (((String) cbLuaChon.getSelectedItem()).equalsIgnoreCase("Thuê liền")) {
				if (hour < LocalTime.now().getHour() || hour > 22 || hour < 9) {
					JOptionPane.showMessageDialog(this, "giờ thuê phòng không hợp lệ!!!");
					return false;
				}
			} else if (((String) cbLuaChon.getSelectedItem()).equalsIgnoreCase("Đặt trước")) {
				if (hour > 21) {
					JOptionPane.showMessageDialog(this, "Không được đặt phòng sau 9g tối!!!");
					return false;
				}
				if (hour < 9) {
					JOptionPane.showMessageDialog(this, "Không được đặt phòng trước 9g sáng!!!");
					return false;
				}
			}
			return true;
		}
	}

	public class ChuyenPhong extends JFrame {
		private JLabel lbTenPhong, lbTenKhach, lbNgayThue, lbGioThue, lbGioHienTai, lbThoiGianSuDung, lbPhong,
				lbGiaPhong, lbLoaiPhong, lbSucChua;
		private JTextField tfTenPhong, tfTenKhach, tfGioThue, tfGioHienTai, tfThoiGianSuDung, tfPhong, tfGiaPhong;
		private JComboBox<String> cbLoaiPhong, cbSucChua;
		private JButton btnTimKiem, btnLamMoi, btnQuayLai, btnChuyenPhong;
		private JTable tablePhongTrong;
		private DefaultTableModel modelPhongTrong;
		private String tenPhong;
		private RoomDAO roomDAO;
		private CustomerDAO customerDAO;
		private BookingDAO bookingDAO;
		private BillDAO billDAO;
		private DetailBillDAO detailBillDAO;
		private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

		public String getTenPhong() {
			return tenPhong;
		}

		public void setTenPhong(String tenPhong) {
			this.tenPhong = tenPhong;
		}

		public ChuyenPhong(String tenPhong) throws RemoteException {
			billDAO = new BillDAO();
			roomDAO = new RoomDAO();
			customerDAO = new CustomerDAO();
			bookingDAO = new BookingDAO();
			detailBillDAO = new DetailBillDAO();

			setTenPhong(tenPhong);
			setSize(1050, 500);
			setLocationRelativeTo(null);
			

			JPanel mainPane, topPane, leftPane, rightPane, bottomPane, bottomPaneRight, bottomPaneLeft;
			JLabel lbTieuDe;
			String[] headersSoNguoi = { "Tất cả", "7", "10", "15" };
			String[] headersLoaiPhong = { "Tất cả", "Vip", "Thường" };
			String[] headersTable = { "Mã Phòng", "Tên Phòng", "Loại Phòng", "Sức Chứa", "Giá Phòng" };
			Font font = new Font("Arial", Font.BOLD, 14);

			mainPane = new JPanel(new BorderLayout());

			// set top pane
			topPane = new JPanel();
			topPane.setBackground(Color.decode("#6fa8dc"));
			topPane.add(lbTieuDe = new JLabel("CHUYỂN PHÒNG"));
			lbTieuDe.setFont(new Font("Arial", Font.BOLD, 24));

			// set left pane
			leftPane = new JPanel();
			leftPane.setBackground(Color.decode("#cccccc"));
			leftPane.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "Phòng đang sử dụng"));
			Box box = Box.createVerticalBox();
			Box boxPhong = Box.createHorizontalBox();
			boxPhong.add(lbTenPhong = new JLabel("Tên Phòng:"));
			boxPhong.add(Box.createHorizontalStrut(5));
			boxPhong.add(tfTenPhong = new JTextField(15));
			tfTenPhong.setBorder(null);
			tfTenPhong.setEditable(false);
			tfTenPhong.setBackground(Color.decode("#cccccc"));
			lbTenPhong.setFont(font);
			box.add(boxPhong);
			box.add(Box.createVerticalStrut(15));

			Box boxKhach = Box.createHorizontalBox();
			boxKhach.add(lbTenKhach = new JLabel("Tên Khách Hàng:"));
			boxKhach.add(Box.createHorizontalStrut(5));
			boxKhach.add(tfTenKhach = new JTextField(15));
			tfTenKhach.setBorder(null);
			tfTenKhach.setEditable(false);
			tfTenKhach.setBackground(Color.decode("#cccccc"));
			lbTenKhach.setFont(font);
			box.add(boxKhach);
			box.add(Box.createVerticalStrut(15));

			Box boxGioThue = Box.createHorizontalBox();
			boxGioThue.add(lbGioThue = new JLabel("Giờ Thuê Phòng:"));
			boxGioThue.add(Box.createHorizontalStrut(5));
			boxGioThue.add(tfGioThue = new JTextField(15));
			tfGioThue.setBorder(null);
			tfGioThue.setEditable(false);
			tfGioThue.setBackground(Color.decode("#cccccc"));
			lbGioThue.setFont(font);
			box.add(boxGioThue);
			box.add(Box.createVerticalStrut(15));

			Box boxGioHienTai = Box.createHorizontalBox();
			boxGioHienTai.add(lbGioHienTai = new JLabel("Giờ Hiện Tại:"));
			boxGioHienTai.add(Box.createHorizontalStrut(5));
			boxGioHienTai.add(tfGioHienTai = new JTextField(15));
			tfGioHienTai.setBorder(null);
			tfGioHienTai.setEditable(false);
			tfGioHienTai.setBackground(Color.decode("#cccccc"));
			lbGioHienTai.setFont(font);
			box.add(boxGioHienTai);
			box.add(Box.createVerticalStrut(15));

			Box boxThoiGianSuDung = Box.createHorizontalBox();
			boxThoiGianSuDung.add(lbThoiGianSuDung = new JLabel("Thời Gian Sử Dụng:"));
			boxThoiGianSuDung.add(Box.createHorizontalStrut(5));
			boxThoiGianSuDung.add(tfThoiGianSuDung = new JTextField(15));
			tfThoiGianSuDung.setBorder(null);
			tfThoiGianSuDung.setEditable(false);
			tfThoiGianSuDung.setBackground(Color.decode("#cccccc"));
			lbThoiGianSuDung.setFont(font);
			box.add(boxThoiGianSuDung);
			box.add(Box.createVerticalStrut(15));

			leftPane.add(box);
			// set pane right
			rightPane = new JPanel(new BorderLayout());
			rightPane.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "Danh sách phòng trống"));
			Box boxBtn = Box.createHorizontalBox();
			Box boxLoaiPhong = Box.createVerticalBox();
			boxLoaiPhong.add(lbLoaiPhong = new JLabel("Loại Phòng"));
			boxLoaiPhong.add(Box.createVerticalStrut(5));
			boxLoaiPhong.add(cbLoaiPhong = new JComboBox<String>(headersLoaiPhong));
			JPanel paneForLoaiPhong = new JPanel();
			paneForLoaiPhong.setBackground(Color.decode("#cccccc"));
			paneForLoaiPhong.add(boxLoaiPhong);
			boxBtn.add(paneForLoaiPhong);
			boxBtn.add(Box.createHorizontalStrut(40));

			Box boxSucChua = Box.createVerticalBox();
			boxSucChua.add(lbSucChua = new JLabel("Sức Chứa"));
			boxSucChua.add(Box.createVerticalStrut(5));
			boxSucChua.add(cbSucChua = new JComboBox<String>(headersSoNguoi));
			boxBtn.add(boxSucChua);
			JPanel paneForSucChua = new JPanel();
			paneForSucChua.setBackground(Color.decode("#cccccc"));
			paneForSucChua.add(boxSucChua);
			boxBtn.add(paneForSucChua);
			boxBtn.add(Box.createHorizontalStrut(40));

			// pane tra cuu
			JPanel paneTraCuuP = new JPanel();
			Box traCuuB = Box.createVerticalBox();
			Box traCuuB1 = Box.createHorizontalBox();
			Box traCuuB2 = Box.createHorizontalBox();

			JPanel panePhongLb = new JPanel();
			panePhongLb.setBackground(Color.decode("#cccccc"));
			panePhongLb.add(lbPhong = new JLabel("Phòng"));
			traCuuB1.add(panePhongLb);

			JPanel panePhongF = new JPanel();
			panePhongF.setBackground(Color.decode("#cccccc"));
			panePhongF.add(tfPhong = new JTextField(15));
			traCuuB1.add(panePhongF);

			traCuuB1.add(btnTimKiem = new ButtonGradient("Tìm kiếm", imgSearch));

			JPanel paneGiaLb = new JPanel();
			paneGiaLb.setBackground(Color.decode("#cccccc"));
			paneGiaLb.add(lbGiaPhong = new JLabel("Giá phòng"));
			traCuuB2.add(paneGiaLb);

			JPanel paneGiaPhongF = new JPanel();
			paneGiaPhongF.setBackground(Color.decode("#cccccc"));
			paneGiaPhongF.add(tfGiaPhong = new JTextField(15));
			traCuuB2.add(paneGiaPhongF);

			traCuuB2.add(btnLamMoi = new ButtonGradient("Làm mới", imgReset));

			traCuuB.add(traCuuB1);
			traCuuB.add(Box.createVerticalStrut(10));
			traCuuB.add(traCuuB2);
			paneTraCuuP.add(traCuuB);
			paneTraCuuP.setBackground(Color.decode("#cccccc"));
			paneTraCuuP.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black), "Tra cứu"));

			btnLamMoi.setBackground(Color.decode("#6fa8dc"));
			btnTimKiem.setBackground(Color.decode("#6fa8dc"));
			lbPhong.setPreferredSize(lbGiaPhong.getPreferredSize());
			
			boxBtn.add(paneTraCuuP);
			JPanel paneForBoxBtn = new JPanel();
			paneForBoxBtn.setBackground(Color.decode("#cccccc"));
			paneForBoxBtn.add(boxBtn);
			rightPane.add(paneForBoxBtn, BorderLayout.NORTH);

			// Table
			modelPhongTrong = new DefaultTableModel(headersTable, 0);
			tablePhongTrong = new JTable(modelPhongTrong);
			tablePhongTrong.setRowHeight(30);
			tablePhongTrong.setFont(new Font("serif", Font.PLAIN, 14));
			JScrollPane scroll = new JScrollPane(tablePhongTrong, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			rightPane.add(scroll, BorderLayout.CENTER);

			// Bottom Pane
			bottomPane = new JPanel(new GridLayout(1, 2));
			bottomPane.setBackground(Color.decode("#cccccc"));

			bottomPaneRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			bottomPaneRight.setBackground(Color.decode("#cccccc"));
			bottomPaneRight.add(btnChuyenPhong = new ButtonGradient("Chuyển phòng"));
			btnChuyenPhong.setFont(new Font("sanserif", Font.BOLD, 12));
			btnChuyenPhong.setBackground(Color.decode("#6fa8dc"));

			bottomPaneLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
			bottomPaneLeft.setBackground(Color.decode("#cccccc"));
			bottomPaneLeft.add(btnQuayLai = new ButtonGradient("Quay lại   ", imgBack));
			btnQuayLai.setBackground(Color.decode("#6fa8dc"));
			
			bottomPane.add(bottomPaneLeft);
			bottomPane.add(bottomPaneRight);

			mainPane.add(topPane, BorderLayout.NORTH);
			mainPane.add(leftPane, BorderLayout.WEST);
			mainPane.add(rightPane, BorderLayout.CENTER);
			mainPane.add(bottomPane, BorderLayout.SOUTH);
			
			btnChuyenPhong.setPreferredSize(btnQuayLai.getPreferredSize());
			btnLamMoi.setPreferredSize(btnTimKiem.getPreferredSize());

			tfTenPhong.setFont(new Font("Arial", Font.PLAIN, 16));
			tfTenPhong.setBorder(null);
			tfTenPhong.setEditable(false);

			tfTenKhach.setFont(new Font("Arial", Font.PLAIN, 16));
			tfTenKhach.setBorder(null);
			tfTenKhach.setEditable(false);

			tfGioThue.setFont(new Font("Arial", Font.PLAIN, 16));
			tfGioThue.setBorder(null);
			tfGioThue.setEditable(false);

			tfGioHienTai.setFont(new Font("Arial", Font.PLAIN, 16));
			tfGioHienTai.setBorder(null);
			tfGioHienTai.setEditable(false);

			tfThoiGianSuDung.setFont(new Font("Arial", Font.PLAIN, 16));
			tfThoiGianSuDung.setBorder(null);
			tfThoiGianSuDung.setEditable(false);

			this.getContentPane().add(mainPane);

			try {
				readDataToFieldsTTPhongDangThue();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			readDataToTablePhong("Còn trống");

			btnQuayLai.addActionListener(e -> this.dispose());
			btnLamMoi.addActionListener(e -> {
				tfPhong.setText("");
				tfTenPhong.setText("");
				tfPhong.requestFocus();
			});
			btnTimKiem.addActionListener(e -> {
				try {
					xuLyTimKiem();
				} catch (NumberFormatException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			btnChuyenPhong.addActionListener(e -> {
				try {
					xuLyChuyenPhong();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			cbLoaiPhong.addActionListener(e -> xuLyLocTheoLoaiPhong());
			cbSucChua.addActionListener(e -> xuLyLocTheoSucChua());
		}

		private void xuLyTimKiem() throws NumberFormatException, RemoteException {
			if(!tfPhong.getText().equals("") || !tfGiaPhong.getText().equals("")) {
				List<Room> rooms = new ArrayList<Room>();
				modelPhongTrong.setRowCount(0);
				if(!tfPhong.getText().equals("")) {
					rooms = roomDAO.getRoomsByRoomName(tfPhong.getText().trim());
					if(!tfGiaPhong.getText().equals("") && rooms.size() != 0) 
					rooms = rooms.stream().filter(r -> r.getRoomType().getPrice() == Double.valueOf(tfGiaPhong.getText().trim()))
								 .toList();
				}
				else
					rooms = roomDAO.getRoomsByPrice(Double.valueOf(tfGiaPhong.getText().trim()));
				
				rooms.forEach(r -> {
					modelPhongTrong.addRow(new Object[] {r.getId(), r.getName(), r.getRoomType().getTypeRoom(),
							r.getRoomType().getCapacity(), r.getRoomType().getPrice()});
				});
			}
		}

		private void xuLyChuyenPhong() throws RemoteException {
			Room p = roomDAO
					.getRoomsByRoomName2((String) modelPhongTrong.getValueAt(tablePhongTrong.getSelectedRow(), 1))
					.get(0);
			DetailBill cthd = detailBillDAO.findDetailBillByRoomIDOrderByID(tenPhong).get(0);
			cthd.setCheckout(LocalDateTime.now());
			cthd.setCheckout(LocalDateTime.now());
			Bill bill = billDAO.getBillsByDetailBillID(Integer.valueOf(cthd.getId())).get(0);
			DetailBill chiTietHD = new DetailBill(0, p, bill, LocalDateTime.now(), null);
			try {
				if (detailBillDAO.createDetailBill(chiTietHD) && detailBillDAO.updateCheckoutTime(cthd)) {
					if (roomDAO.updateRoomStatusByRoomName("Còn trống", tenPhong)
							&& roomDAO.updateRoomStatusByRoomName("Đang thuê", p.getName()))
						getAllDataRooms();
					readDataToTablePhong("Còn trống");
					JOptionPane.showMessageDialog(this, "Chuyển phòng thành công!!");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		private void xuLyLocTheoLoaiPhong() {
			modelPhongTrong.setRowCount(0);
			List<Room> rooms = roomDAO.getRoomsByType((String)cbLoaiPhong.getSelectedItem());
			rooms.forEach(r -> {
				modelPhongTrong.addRow(new Object[] {r.getId(), r.getName(), r.getRoomType().getTypeRoom(),
						r.getRoomType().getCapacity(), r.getRoomType().getPrice()});
			});
		}

		private void xuLyLocTheoSucChua() {
			modelPhongTrong.setRowCount(0);
			List<Room> rooms = roomDAO.getRoomsByCapacity((String) cbSucChua.getSelectedItem());
			rooms.forEach(r -> {
				modelPhongTrong.addRow(new Object[] {r.getId(), r.getName(), r.getRoomType().getTypeRoom(),
						r.getRoomType().getCapacity(), r.getRoomType().getPrice()});
			});
		}

		private void readDataToFieldsTTPhongDangThue() throws RemoteException {
			Room phong = roomDAO.getRoomsByRoomName2(tenPhong).get(0);
			DetailBill detailBill = detailBillDAO.findDetailBillByRoomIDOrderByID(phong.getName()).get(0);
			Bill bill = billDAO.getBillsByDetailBillID(detailBill.getId()).get(0);
			tfTenPhong.setText(tenPhong.trim());
			tfTenKhach.setText(bill.getCustomer().getCustomerName());
			tfGioThue.setText(
					detailBill.getCheckin().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) + "");
			tfGioHienTai
					.setText(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)) + "");
			double time = (LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()
					- detailBill.getCheckin().atZone(ZoneId.systemDefault()).toEpochSecond()) / 60;
			tfThoiGianSuDung.setText(time + " phút");
		}

		private void readDataToTablePhong(String status) {
			modelPhongTrong.setRowCount(0);
			List<Room> rooms = roomDAO.getRoomsByStatus(status);
			for (Room r : rooms) {
				modelPhongTrong.addRow(new Object[] { r.getId(), r.getName(), r.getRoomType().getTypeRoom(),
						r.getRoomType().getCapacity(), r.getRoomType().getPrice() });
			}
		}
	}

	public class PhongCho extends JFrame {
		private JButton nhanPBtn, huyPBtn, timKiemSDTBtn;
		private JTable phieuDatPTable;
		private DefaultTableModel phieuDatPModel;
		private JTextField sdtF;
		private JComboBox<String> tinhTrangPhieuB;
		private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		private SimpleDateFormat formatTime;
		private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		public PhongCho() throws RemoteException {
			formatTime = new SimpleDateFormat("hh:mm:ss");
//			formatTime.setTimeZone(TimeZone.getTimeZone("Asia/VietNam"));

			setSize(1200, 500);
			setLocationRelativeTo(null);

			Icon imgAdd = new ImageIcon("src/img/add2.png");
			Icon imgDel = new ImageIcon("src/img/del.png");
			Icon imgReset = new ImageIcon("src/img/refresh16.png");
			Icon imgEdit = new ImageIcon("src/img/edit.png");
			Icon imgOut = new ImageIcon("src/img/out.png");
			Icon imgSearch = new ImageIcon("src/img/search.png");
			Icon imgCheck = new ImageIcon("src/img/check16.png");
			Icon imgCancel = new ImageIcon("src/img/cancel16.png");

			Border blackLine = BorderFactory.createLineBorder(Color.black);

			JPanel panePDP = new JPanel(new BorderLayout());
			JPanel paneBtnPDP = new JPanel();
			JPanel titlePanel = new JPanel();
			JLabel locTinhTrangLb, sdtLb, tieuDeLb;
			String[] headersTablePDP = { "Mã đặt phòng", "Tên phòng", "Ngày đặt", "Giờ đặt", "Tên khách", "SĐT khách",
					"Nhân viên", "Tình trạng" };

			// set tiêu đề
			Font font = new Font("Arial", Font.BOLD, 24);
			titlePanel.setBackground(Color.decode("#990447"));
			titlePanel.add(tieuDeLb = new JLabel("PHÒNG CHỜ"));
			tieuDeLb.setFont(font);

			// Table PDP
			phieuDatPModel = new DefaultTableModel(headersTablePDP, 0);
			phieuDatPTable = new JTable(phieuDatPModel);
			phieuDatPTable.setRowHeight(30);
			phieuDatPTable.setFont(new Font("serif", Font.PLAIN, 15));
			phieuDatPTable.setAutoCreateRowSorter(true);
			phieuDatPTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			phieuDatPTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPDPTable = new JScrollPane(phieuDatPTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			panePDP.add(scrollPDPTable, BorderLayout.CENTER);
			phieuDatPTable.setRowHeight(25);

			// Box tra cứu phiếu đặt phòng
			Box btnPDPBox = Box.createHorizontalBox();

			// Box Combox box lọc theo tình trạng pdp
			String[] headersTinhTrangPDP = { "Tất cả", "chưa nhận phòng", "nhận phòng" };
			Box locTinhTrangBox = Box.createHorizontalBox();
			locTinhTrangBox.add(locTinhTrangLb = new JLabel("Lọc theo tình trạng"));
			locTinhTrangBox.add(Box.createHorizontalStrut(5));
			locTinhTrangBox.add(tinhTrangPhieuB = new JComboBox<>(headersTinhTrangPDP));
			JPanel paneForLocBox = new JPanel();
			paneForLocBox.setBackground(Color.decode("#D0BAFB"));
			paneForLocBox.add(locTinhTrangBox);
			btnPDPBox.add(paneForLocBox);
			btnPDPBox.add(Box.createHorizontalStrut(150));

			// Box tìm kiếm khách theo sdt
			Box sdtBox = Box.createHorizontalBox();

			JPanel paneForSdtLb = new JPanel();
			paneForSdtLb.add(sdtLb = new JLabel("SĐT Khách hàng"));
			paneForSdtLb.setBackground(Color.decode("#D0BAFB"));
			sdtBox.add(paneForSdtLb);

			JPanel paneForSdtF = new JPanel();
			paneForSdtF.add(sdtF = new JTextField(15));
			paneForSdtF.setBackground(Color.decode("#D0BAFB"));
			sdtBox.add(paneForSdtF);
			sdtBox.add(timKiemSDTBtn = new ButtonGradient("Tìm kiếm", imgSearch));
			timKiemSDTBtn.setBackground(Color.decode("#D0BAFB"));

			JPanel paneForSdtBox = new JPanel();
			paneForSdtBox.setBackground(Color.decode("#D0BAFB"));
			paneForSdtBox.add(sdtBox);
			btnPDPBox.add(paneForSdtBox);
			paneBtnPDP.add(btnPDPBox);
			paneBtnPDP.setBackground(Color.decode("#D0BAFB"));

			panePDP.add(paneBtnPDP, BorderLayout.NORTH);
			panePDP.setBorder(BorderFactory.createTitledBorder(blackLine, "Danh sách phiếu đặt phòng"));
			panePDP.setBackground(Color.decode("#D0BAFB"));

			// Box nút hủy, nhận phòng
//			Box btnNhanHuyBox = Box.createHorizontalBox();
//			btnNhanHuyBox.add(Box.createHorizontalStrut(1000));
//			btnNhanHuyBox.add(huyPBtn = new JButton("Hủy", imgCancel));
//			btnNhanHuyBox.add(Box.createHorizontalStrut(40));
//			btnNhanHuyBox.add(nhanPBtn = new JButton("Nhận", imgCheck));

			JPanel paneForBtnNhanHuyBox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			paneForBtnNhanHuyBox.add(huyPBtn = new ButtonGradient("Hủy", imgCancel));
			paneForBtnNhanHuyBox.add(Box.createHorizontalStrut(50));
			paneForBtnNhanHuyBox.add(nhanPBtn = new ButtonGradient("Nhận", imgCheck));
			paneForBtnNhanHuyBox.setBackground(Color.decode("#D0BAFB"));
			panePDP.add(paneForBtnNhanHuyBox, BorderLayout.SOUTH);

			JPanel pnlMain = new JPanel(new BorderLayout());
			pnlMain.add(titlePanel, BorderLayout.NORTH);
			pnlMain.add(panePDP, BorderLayout.CENTER);
			this.getContentPane().add(pnlMain);

			huyPBtn.setBackground(Color.decode("#D0BAFB"));
			nhanPBtn.setBackground(Color.decode("#D0BAFB"));

			readDateToTablePhongCho();

			huyPBtn.addActionListener(e -> xuLyHuyPhongCho());
			nhanPBtn.addActionListener(e -> xuLyNhanPhong());
			tinhTrangPhieuB.addActionListener(e -> xuLyLocTheoTinhTrangPDP());
			timKiemSDTBtn.addActionListener(e -> {
				try {
					xuLyTimKiemSDT();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}

		private void readDateToTablePhongCho() throws RemoteException {
			phieuDatPModel.setRowCount(0);
			List<Booking> bookings = bookingDAO.getBookingsByStatus(1);
			bookings.forEach(b -> {
				phieuDatPModel.addRow(new Object[] { b.getId(), b.getRoom().getName(),
						b.getBookingDateTime().toLocalDate()
								.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) + "",
						b.getBookingDateTime().toLocalTime() + "", b.getCustomer().getCustomerName(),
						b.getCustomer().getPhoneNumber(), b.getEmployee().getName() });
			});
		}

		private void xuLyLocTheoTinhTrangPDP() {

		}

		private void xuLyHuyPhongCho() {

		}

		private void xuLyNhanPhong() {
			try {
				Booking booking = bookingDAO
						.searchBookingByID((Integer) phieuDatPModel.getValueAt(phieuDatPTable.getSelectedRow(), 0));
				System.out.println(booking.getRoom().getName());
				Bill bill = new Bill(0, null, null, booking.getEmployee(), booking.getCustomer());
				DetailBill detailBill = new DetailBill(0, booking.getRoom(), bill, LocalDateTime.now(),
						LocalDateTime.now());
				if (billDAO.createBill(bill) && detailBillDAO.createDetailBill(detailBill)
						&& bookingDAO.updateBookingStatusByID(0, booking.getId())
						&& roomDAO.updateRoomStatusByRoomName("Đang thuê", booking.getRoom().getName())) {
					JOptionPane.showMessageDialog(this, "Nhận phòng thành công!");
					readDateToTablePhongCho();
					getAllDataRooms();
					tinhTrangB.setSelectedItem("Đang thuê");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void xuLyTimKiemSDT() throws RemoteException {
			phieuDatPModel.setRowCount(0);
			List<Booking> bookings = bookingDAO.getBookingsByPhoneNumber(sdtF.getText().trim());
			bookings.forEach(b -> {
				phieuDatPModel.addRow(new Object[] {b.getId(), b.getRoom().getName(), b.getBookingDateTime().toLocalDate()});
			});
		}
	}
	public class ChiTietPhong extends JFrame {
		private JTextField tfMaHD, tfTenKhach, tfNgayNhanP, tfTenNhanVien, tfSDT, tfTenPhong, tfSucChua,
				tfLoaiPhong, tfGiaPhong, tfGioThuePhong, tfGioHienTai, tfThoiGianSuDung, tfTienPhongCu,
				tfTienPhongHienTai, tfTongTienPhong, tfTongTienDich, tfGiamGia, tfTongCong, tfThueVAT, tfThanhTien;
		private JTable tableDichVu;
		private DefaultTableModel model;
		private JButton btnThemDV, btnChuyenPhong, btnQuayLai;
		private DateTimeFormatter gioFormatForDate = DateTimeFormatter.ofPattern("HH:mm");

		public ChiTietPhong() {
			setSize(1000, 600);
			setLocationRelativeTo(null);

			Icon imgDel = new ImageIcon("src/img/del.png");
			Icon imgReset = new ImageIcon("src/img/refresh16.png");
			Icon imgEdit = new ImageIcon("src/img/edit.png");
			Icon imgOut = new ImageIcon("src/img/out.png");
			Icon imgSearch = new ImageIcon("src/img/search.png");
			Icon imgCheck = new ImageIcon("src/img/check16.png");
			Icon imgCancel = new ImageIcon("src/img/cancel16.png");
			Icon imgBack = new ImageIcon("src/img/back16.png");
			Icon imgAdd = new ImageIcon("src/img/add16.png");
			Icon imgChange = new ImageIcon("src/img/change16.png");

			JPanel mainPane, topPane, bottomPane, pDPPane, thongTinPhongPane, thongTinDVPane, thongTinPDPPane, btnPane;

			JLabel lbMaPDP, lbTenKhach, lbNgayNhanP, lbTenNhanVien, lbSDT, lbGioNhan, lbTenPhong, lbSucChua,
					lbLoaiPhong, lbGiaPhong, lbGioThuePhong, lbGioHienTai, lbThoiGianSuDung, lbTienPhongCu,
					lbTienPhongHienTai, lbTongTienPhong, lbTongTienDich, lbGiamGia, lbTongCong, lbThueVAT, lbThanhTien,
					lbTuaDe;

			Border border = new LineBorder(Color.black);

			// set Top Pane
			topPane = new JPanel();
			lbTuaDe = new JLabel("CHI TIẾT PHÒNG");
			lbTuaDe.setFont(new Font("Arial", Font.BOLD, 24));
			topPane.add(lbTuaDe);
			topPane.setBackground(Color.decode("#6fa8dc"));

			// set Bottom Pane
			bottomPane = new JPanel(new BorderLayout());
			pDPPane = new JPanel();
			pDPPane.setBackground(Color.decode("#e6dbd1"));
			Box boxForPDPPane = Box.createHorizontalBox();

			// set up cột đầu tiên trong pane phiếu đặt phòng
			Box box1 = Box.createVerticalBox();
			Box boxForMaPDP = Box.createHorizontalBox();
			boxForMaPDP.add(lbMaPDP = new JLabel("Mã Hóa Đơn: "));
			boxForMaPDP.add(tfMaHD = new JTextField(15));
			box1.add(boxForMaPDP);
			box1.add(Box.createVerticalStrut(20));

			Box boxForTenNhanVien = Box.createHorizontalBox();
			boxForTenNhanVien.add(lbTenNhanVien = new JLabel("Tên Nhân Viên: "));
			boxForTenNhanVien.add(tfTenNhanVien = new JTextField(15));
			box1.add(boxForTenNhanVien);
			boxForPDPPane.add(box1);
			boxForPDPPane.add(Box.createHorizontalStrut(10));

			// set up cột thứ hai trong pane phiếu đặt phòng
			Box box2 = Box.createVerticalBox();
			Box boxForTenKhach = Box.createHorizontalBox();
			boxForTenKhach.add(lbTenKhach = new JLabel("Tên Khách Hàng: "));
			boxForTenKhach.add(tfTenKhach = new JTextField(15));
			box2.add(boxForTenKhach);
			box2.add(Box.createVerticalStrut(20));

			Box boxForSDT = Box.createHorizontalBox();
			boxForSDT.add(lbSDT = new JLabel("Số Điện Thoại: "));
			boxForSDT.add(tfSDT = new JTextField(15));
			box2.add(boxForSDT);
			boxForPDPPane.add(box2);
			boxForPDPPane.add(Box.createHorizontalStrut(10));

			// set up cột thứ ba trong pane phiếu đặt phòng
			Box box3 = Box.createVerticalBox();
			Box boxForNgayNhan = Box.createHorizontalBox();
			boxForNgayNhan.add(lbNgayNhanP = new JLabel("Ngày Nhận Phòng: "));
			boxForNgayNhan.add(tfNgayNhanP = new JTextField(15));
			box3.add(boxForNgayNhan);
			box3.add(Box.createVerticalStrut(20));

			// Thong tin phong hien tai, thong tin dich vu
			JPanel pane = new JPanel(new BorderLayout());
			thongTinPhongPane = new JPanel();
			Box boxForThongTinPhong = Box.createVerticalBox();

			Box boxForTenPhong = Box.createHorizontalBox();
			boxForTenPhong.add(lbTenPhong = new JLabel("Tên Phòng:"));
			boxForTenPhong.add(Box.createHorizontalStrut(5));
			boxForTenPhong.add(tfTenPhong = new JTextField(15));

			Box boxForSucChua = Box.createHorizontalBox();
			boxForSucChua.add(lbSucChua = new JLabel("Sức Chứa:"));
			boxForSucChua.add(Box.createHorizontalStrut(5));
			boxForSucChua.add(tfSucChua = new JTextField(15));

			Box boxForLoaiPhong = Box.createHorizontalBox();
			boxForLoaiPhong.add(lbLoaiPhong = new JLabel("Loại Phòng:"));
			boxForLoaiPhong.add(Box.createHorizontalStrut(5));
			boxForLoaiPhong.add(tfLoaiPhong = new JTextField(15));

			Box boxForGiaPhong = Box.createHorizontalBox();
			boxForGiaPhong.add(lbGiaPhong = new JLabel("Giá Phòng:"));
			boxForGiaPhong.add(Box.createHorizontalStrut(5));
			boxForGiaPhong.add(tfGiaPhong = new JTextField(15));

			Box boxForGioThuePhong = Box.createHorizontalBox();
			boxForGioThuePhong.add(lbGioThuePhong = new JLabel("Giờ Thuê Phòng:"));
			boxForGioThuePhong.add(Box.createHorizontalStrut(5));
			boxForGioThuePhong.add(tfGioThuePhong = new JTextField(15));

			Box boxForGioHienTai = Box.createHorizontalBox();
			boxForGioHienTai.add(lbGioHienTai = new JLabel("Giờ Hiện Tại:"));
			boxForGioHienTai.add(Box.createHorizontalStrut(5));
			boxForGioHienTai.add(tfGioHienTai = new JTextField(15));

			Box boxForThoiGian = Box.createHorizontalBox();
			boxForThoiGian.add(lbThoiGianSuDung = new JLabel("Thời Gian Sử Dụng: "));
			// boxForThoiGian.add(Box.createHorizontalStrut(5));
			boxForThoiGian.add(tfThoiGianSuDung = new JTextField(15));

			boxForThongTinPhong.add(Box.createVerticalStrut(5));
			boxForThongTinPhong.add(boxForTenPhong);
			boxForThongTinPhong.add(Box.createVerticalStrut(20));
			boxForThongTinPhong.add(boxForSucChua);
			boxForThongTinPhong.add(Box.createVerticalStrut(20));
			boxForThongTinPhong.add(boxForLoaiPhong);
			boxForThongTinPhong.add(Box.createVerticalStrut(20));
			boxForThongTinPhong.add(boxForGiaPhong);
			boxForThongTinPhong.add(Box.createVerticalStrut(20));
			boxForThongTinPhong.add(boxForGioThuePhong);
			boxForThongTinPhong.add(Box.createVerticalStrut(20));
			boxForThongTinPhong.add(boxForGioHienTai);
			boxForThongTinPhong.add(Box.createVerticalStrut(20));
			boxForThongTinPhong.add(boxForThoiGian);
			boxForThongTinPhong.add(Box.createVerticalStrut(20));

			thongTinPhongPane.add(boxForThongTinPhong);
			thongTinPhongPane.setBackground(Color.decode("#cccccc"));
			thongTinPhongPane.setBorder(BorderFactory.createTitledBorder(border, "Thông tin phòng hiện tại"));
			pane.add(thongTinPhongPane, BorderLayout.WEST);

			// set thong tin dich vu
			String[] headersDichVu = { "STT", "Tên dịch vụ", "Đơn vị", "Số lượng", "Đơn giá" };
			model = new DefaultTableModel(headersDichVu, 20);
			tableDichVu = new JTable(model);
			tableDichVu.setRowHeight(25);
			JScrollPane scroll = new JScrollPane(tableDichVu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setBackground(Color.decode("#cccccc"));
			scroll.setBorder(BorderFactory.createTitledBorder(border, "Thông tin dịch vụ"));
			pane.add(scroll, BorderLayout.CENTER);

			// set tổng tiền phiếu đặt phòng
			thongTinPDPPane = new JPanel();
			btnPane = new JPanel();

			Box boxForTTPDP = Box.createHorizontalBox();
			Box boxForCot1 = Box.createVerticalBox();
			Box boxForCot2 = Box.createVerticalBox();

			Box boxForTienPhongCu = Box.createHorizontalBox();
			boxForTienPhongCu.add(lbTienPhongCu = new JLabel("Tên Phòng Cũ (nếu có): "));
			boxForTienPhongCu.add(tfTienPhongCu = new JTextField(15));

			Box boxForTienPhongHienTai = Box.createHorizontalBox();
			boxForTienPhongHienTai.add(lbTienPhongHienTai = new JLabel("Tiền Phòng Hiện Tại:"));
			boxForTienPhongHienTai.add(Box.createHorizontalStrut(5));
			boxForTienPhongHienTai.add(tfTienPhongHienTai = new JTextField(15));

			Box boxForTongTienPhong = Box.createHorizontalBox();
			boxForTongTienPhong.add(lbTongTienPhong = new JLabel("Tổng Tiền Phòng:"));
			boxForTongTienPhong.add(Box.createHorizontalStrut(5));
			boxForTongTienPhong.add(tfTongTienPhong = new JTextField(15));

			Box boxForTongTienDich = Box.createHorizontalBox();
			boxForTongTienDich.add(lbTongTienDich = new JLabel("Tổng Tiền Dịch:"));
			boxForTongTienDich.add(Box.createHorizontalStrut(5));
			boxForTongTienDich.add(tfTongTienDich = new JTextField(15));

			boxForCot1.add(boxForTienPhongCu);
			boxForCot1.add(Box.createVerticalStrut(10));
			boxForCot1.add(boxForTienPhongHienTai);
			boxForCot1.add(Box.createVerticalStrut(10));
			boxForCot1.add(boxForTongTienPhong);
			boxForCot1.add(Box.createVerticalStrut(10));
			boxForCot1.add(boxForTongTienDich);

			boxForTTPDP.add(Box.createHorizontalStrut(20));
			boxForTTPDP.add(boxForCot1);
			boxForTTPDP.add(Box.createHorizontalStrut(100));

			Box boxForGiamGia = Box.createHorizontalBox();
			boxForGiamGia.add(lbGiamGia = new JLabel("Giảm Giá:"));
			boxForGiamGia.add(Box.createHorizontalStrut(5));
			boxForGiamGia.add(tfGiamGia = new JTextField(15));

			Box boxForTongCong = Box.createHorizontalBox();
			boxForTongCong.add(lbTongCong = new JLabel("Tổng Cộng:"));
			boxForTongCong.add(Box.createHorizontalStrut(5));
			boxForTongCong.add(tfTongCong = new JTextField(15));

			Box boxForThue = Box.createHorizontalBox();
			boxForThue.add(lbThueVAT = new JLabel("Thuế VAT:"));
			boxForThue.add(Box.createHorizontalStrut(5));
			boxForThue.add(tfThueVAT = new JTextField(15));

			Box boxForThanhTien = Box.createHorizontalBox();
			boxForThanhTien.add(lbThanhTien = new JLabel("Thành Tiền: "));
			boxForThanhTien.add(tfThanhTien = new JTextField(15));

			boxForCot2.add(boxForGiamGia);
			boxForCot2.add(Box.createVerticalStrut(10));
			boxForCot2.add(boxForTongCong);
			boxForCot2.add(Box.createVerticalStrut(10));
			boxForCot2.add(boxForThue);
			boxForCot2.add(Box.createVerticalStrut(10));
			boxForCot2.add(boxForThanhTien);

			boxForTTPDP.add(boxForCot2);
			boxForTTPDP.add(Box.createHorizontalStrut(20));

			thongTinPDPPane.add(boxForTTPDP);
			thongTinPDPPane.setBackground(Color.decode("#cccccc"));
			thongTinPDPPane.setBorder(BorderFactory.createTitledBorder(border, "Tổng Tiền Phiếu Đặt Phòng"));

			JPanel pane2 = new JPanel(new BorderLayout());
			pane2.add(thongTinPDPPane, BorderLayout.WEST);

			// set pane button
			btnPane = new JPanel();
			Box boxForBtnPane = Box.createVerticalBox();
			boxForBtnPane.add(Box.createVerticalStrut(10));
			boxForBtnPane.add(btnThemDV = new ButtonGradient("Thêm dịch vụ", imgAdd));
			boxForBtnPane.add(Box.createVerticalStrut(20));
			boxForBtnPane.add(btnChuyenPhong = new ButtonGradient("Chuyển phòng", imgChange));
			boxForBtnPane.add(Box.createVerticalStrut(20));
			boxForBtnPane.add(btnQuayLai = new ButtonGradient("Quay lại", imgBack));
			boxForBtnPane.add(Box.createVerticalStrut(10));
			btnPane.add(boxForBtnPane);
			btnPane.setBackground(Color.decode("#e6dbd1"));

			btnQuayLai.setBackground(Color.decode("#6fa8dc"));
			btnChuyenPhong.setBackground(Color.decode("#6fa8dc"));
			btnThemDV.setBackground(Color.decode("#6fa8dc"));

			Dimension maxButtonSize = new Dimension(Integer.MAX_VALUE, btnChuyenPhong.getPreferredSize().height);
			btnThemDV.setMaximumSize(maxButtonSize);
			btnQuayLai.setMaximumSize(maxButtonSize);

			pane2.add(btnPane, BorderLayout.CENTER);

			// set text field của pdp pane
			tfMaHD.setBorder(null);
			tfMaHD.setEditable(false);
			tfMaHD.setBackground(Color.decode("#e6dbd1"));
			tfTenNhanVien.setBorder(null);
			tfTenNhanVien.setEditable(false);
			tfTenNhanVien.setBackground(Color.decode("#e6dbd1"));
			tfTenKhach.setBorder(null);
			tfTenKhach.setEditable(false);
			tfTenKhach.setBackground(Color.decode("#e6dbd1"));
			tfSDT.setBorder(null);
			tfSDT.setEditable(false);
			tfSDT.setBackground(Color.decode("#e6dbd1"));
			tfNgayNhanP.setBorder(null);
			tfNgayNhanP.setEditable(false);
			tfNgayNhanP.setBackground(Color.decode("#e6dbd1"));
			tfTenPhong.setBorder(null);
			tfTenPhong.setEditable(false);
			tfTenPhong.setBackground(Color.decode("#cccccc"));
			tfSucChua.setBorder(null);
			tfSucChua.setEditable(false);
			tfSucChua.setBackground(Color.decode("#cccccc"));
			tfLoaiPhong.setBorder(null);
			tfLoaiPhong.setEditable(false);
			tfLoaiPhong.setBackground(Color.decode("#cccccc"));
			tfGiaPhong.setBorder(null);
			tfGiaPhong.setEditable(false);
			tfGiaPhong.setBackground(Color.decode("#cccccc"));
			tfGioThuePhong.setBorder(null);
			tfGioThuePhong.setEditable(false);
			tfGioThuePhong.setBackground(Color.decode("#cccccc"));
			tfGioHienTai.setBorder(null);
			tfGioHienTai.setEditable(false);
			tfGioHienTai.setBackground(Color.decode("#cccccc"));
			tfThoiGianSuDung.setBorder(null);
			tfThoiGianSuDung.setEditable(false);
			tfThoiGianSuDung.setBackground(Color.decode("#cccccc"));

			tfTienPhongCu.setBorder(null);
			tfTienPhongCu.setEditable(false);
			tfTienPhongCu.setBackground(Color.decode("#cccccc"));
			tfTienPhongHienTai.setBorder(null);
			tfTienPhongHienTai.setEditable(false);
			tfTienPhongHienTai.setBackground(Color.decode("#cccccc"));
			tfTongTienPhong.setBorder(null);
			tfTongTienPhong.setEditable(false);
			tfTongTienPhong.setBackground(Color.decode("#cccccc"));
			tfGiamGia.setBorder(null);
			tfGiamGia.setEditable(false);
			tfGiamGia.setBackground(Color.decode("#cccccc"));
			tfTongCong.setBorder(null);
			tfTongCong.setEditable(false);
			tfTongCong.setBackground(Color.decode("#cccccc"));
			tfThueVAT.setBorder(null);
			tfThueVAT.setEditable(false);
			tfThueVAT.setBackground(Color.decode("#cccccc"));
			tfThanhTien.setBorder(null);
			tfThanhTien.setEditable(false);
			tfThanhTien.setBackground(Color.decode("#cccccc"));
			tfTongTienDich.setBorder(null);
			tfTongTienDich.setEditable(false);
			tfTongTienDich.setBackground(Color.decode("#cccccc"));

			pDPPane.add(boxForPDPPane);
			bottomPane.add(pDPPane, BorderLayout.NORTH);
			bottomPane.add(pane, BorderLayout.CENTER);
			bottomPane.add(pane2, BorderLayout.SOUTH);
			// main Pane
			mainPane = new JPanel(new BorderLayout());
			mainPane.add(topPane, BorderLayout.NORTH);
			mainPane.add(bottomPane, BorderLayout.CENTER);
			mainPane.setBackground(Color.decode("#6fa8dc"));
			this.getContentPane().add(mainPane);

			btnQuayLai.addActionListener(e -> this.dispose());
			btnChuyenPhong.addActionListener(e -> xuLyChuyenPhong());
			btnThemDV.addActionListener(e -> xuLyThemDVPhong());

			tfTenNhanVien.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfMaHD.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfTenKhach.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfSDT.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfNgayNhanP.setFont(new Font("sanserif", Font.PLAIN, 14));
			lbMaPDP.setFont(new Font("sanserif", Font.BOLD, 14));
			lbTenNhanVien.setFont(new Font("sanserif", Font.BOLD, 14));
			lbTenKhach.setFont(new Font("sanserif", Font.BOLD, 14));
			lbSDT.setFont(new Font("sanserif", Font.BOLD, 14));
			lbNgayNhanP.setFont(new Font("sanserif", Font.BOLD, 14));
			lbSDT.setPreferredSize(lbTenKhach.getPreferredSize());
			lbMaPDP.setPreferredSize(lbTenNhanVien.getPreferredSize());

			tfTenPhong.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfSucChua.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfLoaiPhong.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfGiaPhong.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfGioThuePhong.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfGioHienTai.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfThoiGianSuDung.setFont(new Font("sanserif", Font.PLAIN, 13));
			lbTenPhong.setFont(new Font("sanserif", Font.BOLD, 13));
			lbSucChua.setFont(new Font("sanserif", Font.BOLD, 13));
			lbLoaiPhong.setFont(new Font("sanserif", Font.BOLD, 13));
			lbGiaPhong.setFont(new Font("sanserif", Font.BOLD, 13));
			lbGioThuePhong.setFont(new Font("sanserif", Font.BOLD, 13));
			lbGioHienTai.setFont(new Font("sanserif", Font.BOLD, 13));
			lbThoiGianSuDung.setFont(new Font("sanserif", Font.BOLD, 13));

			lbTenPhong.setPreferredSize(lbThoiGianSuDung.getPreferredSize());
			lbSucChua.setPreferredSize(lbThoiGianSuDung.getPreferredSize());
			lbLoaiPhong.setPreferredSize(lbThoiGianSuDung.getPreferredSize());
			lbGiaPhong.setPreferredSize(lbThoiGianSuDung.getPreferredSize());
			lbGioThuePhong.setPreferredSize(lbThoiGianSuDung.getPreferredSize());
			lbGioHienTai.setPreferredSize(lbThoiGianSuDung.getPreferredSize());

			tfThueVAT.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfTongTienPhong.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfTongTienDich.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfTienPhongHienTai.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfTienPhongCu.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfTongCong.setFont(new Font("sanserif", Font.PLAIN, 14));
			tfThanhTien.setFont(new Font("sanserif", Font.PLAIN, 14));

			lbThueVAT.setFont(new Font("sanserif", Font.BOLD, 14));
			lbTongTienPhong.setFont(new Font("sanserif", Font.BOLD, 14));
			lbTongTienDich.setFont(new Font("sanserif", Font.BOLD, 14));
			lbTienPhongHienTai.setFont(new Font("sanserif", Font.BOLD, 14));
			lbTienPhongCu.setFont(new Font("sanserif", Font.BOLD, 14));
			lbTongCong.setFont(new Font("sanserif", Font.BOLD, 14));
			lbThanhTien.setFont(new Font("sanserif", Font.BOLD, 14));
			lbGiamGia.setFont(new Font("sanserif", Font.BOLD, 14));

			lbTienPhongHienTai.setPreferredSize(lbTienPhongCu.getPreferredSize());
			lbTongTienPhong.setPreferredSize(lbTienPhongCu.getPreferredSize());
			lbTongTienDich.setPreferredSize(lbTienPhongCu.getPreferredSize());
			lbGiamGia.setPreferredSize(lbThanhTien.getPreferredSize());
			lbTongCong.setPreferredSize(lbThanhTien.getPreferredSize());
			lbThueVAT.setPreferredSize(lbThanhTien.getPreferredSize());
			
			try {
				readDataToFieldThongTin();
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				readDataToTableDichVu();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				readDataToFieldThanhToan();
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		private void readDataToFieldThongTin() throws RemoteException {
			List<DetailBill> detailBills = detailBillDAO
					.findDetailBillByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0));
			DetailBill detailBill = detailBills.get(detailBills.size() - 1);
			List<Bill> bills = billDAO.getBillsByDetailBillID(detailBill.getId());
			Bill bill = bills.get(bills.size() - 1);
			bill.getDetailBills().forEach(e -> System.out.println(e));
			tfTenNhanVien.setText(bill.getEmployee().getName());
			tfMaHD.setText(bill.getId() + "");
			tfTenKhach.setText(bill.getCustomer().getCustomerName());
			tfSDT.setText(bill.getCustomer().getPhoneNumber());
			tfNgayNhanP.setText(detailBill.getCheckin().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
			tfTenPhong.setText(detailBill.getRoom().getName());
			tfSucChua.setText(detailBill.getRoom().getRoomType().getCapacity() + "");
			tfLoaiPhong.setText(detailBill.getRoom().getRoomType().getTypeRoom());
			tfGiaPhong.setText(detailBill.getRoom().getRoomType().getPrice() + "");
			
			long l1 = (LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() - detailBill.getCheckin().atZone(ZoneId.systemDefault()).toEpochSecond()) / 60;
			
			tfGioThuePhong.setText(detailBill.getCheckin().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
			tfGioHienTai.setText(LocalTime.now().format(gioFormatForDate));
			tfThoiGianSuDung.setText(l1 + " phút");
		}
		
		

		private void readDataToTableDichVu() throws RemoteException {
			model.setRowCount(0);
			List<DetailServiceRoom> detailServiceRooms = detailServiceDAO.searchDetailServiceRoomByBillID(Integer.valueOf(tfMaHD.getText()));
			int i = 0;
			for (DetailServiceRoom dr : detailServiceRooms) {
				model.addRow(new Object[] { ++i, dr.getService().getName(), dr.getService().getUnit(),
						dr.getQuantity(), dr.getService().getPrice(), 0});
			}
		}
		
		private void readDataToFieldThanhToan() throws NumberFormatException, RemoteException {
			tfThueVAT.setText("10%");
			List<DetailBill> detailBills = detailBillDAO.findDetailBillByBillID(Integer.valueOf(tfMaHD.getText().trim()))
													    .stream()
													    .sorted(Comparator.comparingInt(DetailBill::getId))
													    .toList();
			DetailBill detailBill = detailBills.get((int) (detailBills.stream().count() - 1));
			
			detailBill.setCheckout(LocalDateTime.now());
			detailBillDAO.updateCheckoutTime(detailBill);
			Bill bill = billDAO.getBillsByBillID(Integer.valueOf(tfMaHD.getText().trim())).get(0);
			double totalMoneyRoom = bill.calTotalMoneyRoom();
			double totalCurrentRoomPrice = detailBill.translationRoomPrice();
			double totalMoneyOldRoom = detailBills.get((int) (detailBills.stream().count() - 2)).translationRoomPrice();
			tfTienPhongCu.setText(" " + formatter.format(totalMoneyOldRoom) + "");																									
			tfTongTienPhong.setText(formatter.format(totalMoneyRoom) + "");
			double totalMoneyService = bill.calTotalMoneyService();
			tfTongTienDich.setText(formatter.format(totalMoneyService) + "");
			double tong = totalMoneyRoom + totalMoneyService;
			double thanhTien = tong + tong * 0.01;
			tfTienPhongHienTai.setText(formatter.format(totalCurrentRoomPrice) + "");
			tfTongCong.setText(formatter.format(tong) + "");
			tfThanhTien.setText(formatter.format(thanhTien) + "");
		}

		private void xuLyChuyenPhong() {

		}

		private void xuLyThemDVPhong() {

		}
	}
	
	public class DichVuPhong extends JFrame {
		private JTextField tfMaHD, tfTenKH, tfSDT, tfTenPhong, tfTimKiemDV1, tfTimKiemDV2;
		private JButton btnQuayLai, btnThem, btnCapNhat, btnXoa;
		private JTable tableDVKho, tableDVPhong;
		private DefaultTableModel model1, model2;

		public DichVuPhong() {
			// TODO Auto-generated constructor stub
			setSize(1200, 600);
			setLocationRelativeTo(null);

			Icon imgDel = new ImageIcon("src/img/del.png");
			Icon imgReset = new ImageIcon("src/img/refresh16.png");
			Icon imgEdit = new ImageIcon("src/img/edit.png");
			Icon imgOut = new ImageIcon("src/img/out.png");
			Icon imgSearch = new ImageIcon("src/img/search.png");
			Icon imgCheck = new ImageIcon("src/img/check16.png");
			Icon imgCancel = new ImageIcon("src/img/cancel16.png");
			Icon imgBack = new ImageIcon("src/img/back16.png");
			Icon imgAdd = new ImageIcon("src/img/add16.png");
			Icon imgChange = new ImageIcon("src/img/change16.png");

			JPanel mainPane, topPane, bottomPane, thongTinPane;
			JPanel paneForTableDV, paneForTableDVPhong;
			Box boxForTable;
			JLabel lbMaPDP, lbTenKH, lbSDT, lbTenPhong, lbTieuDe, lbTimKiemDV1, lbTimKiemDV2;
			Font font = new Font("Arial", Font.BOLD, 24);
			Border border = new LineBorder(Color.black);

			// set top Pane
			lbTieuDe = new JLabel("DỊCH VỤ PHÒNG");
			lbTieuDe.setFont(font);
			topPane = new JPanel();
			topPane.setBackground(Color.decode("#990447"));
			topPane.add(lbTieuDe);

			// Thông tin phiếu đặt phòng
			bottomPane = new JPanel(new BorderLayout());
			thongTinPane = new JPanel(new BorderLayout());
			Box boxForThongTin = Box.createHorizontalBox();

			Box box1 = Box.createVerticalBox();
			Box boxForMaPDP = Box.createHorizontalBox();
			boxForMaPDP.add(lbMaPDP = new JLabel("Mã Hóa Đơn: "));
			boxForMaPDP.add(tfMaHD = new JTextField(15));
			Box boxForTenPhong = Box.createHorizontalBox();
			boxForTenPhong.add(lbTenPhong = new JLabel("Tên Phòng:"));
			boxForTenPhong.add(tfTenPhong = new JTextField(15));
			box1.add(Box.createVerticalStrut(10));
			box1.add(boxForMaPDP);
			box1.add(Box.createVerticalStrut(20));
			box1.add(boxForTenPhong);
			box1.add(Box.createVerticalStrut(10));

			Box box2 = Box.createVerticalBox();
			Box boxForTenKH = Box.createHorizontalBox();
			boxForTenKH.add(lbTenKH = new JLabel("Tên Khách Hàng:  "));
			boxForTenKH.add(tfTenKH = new JTextField(15));
			Box boxForSDT = Box.createHorizontalBox();
			boxForSDT.add(lbSDT = new JLabel("Số Điện Thoại:"));
			boxForSDT.add(tfSDT = new JTextField(15));
			box2.add(Box.createVerticalStrut(10));
			box2.add(boxForTenKH);
			box2.add(Box.createVerticalStrut(20));
			box2.add(boxForSDT);
			box2.add(Box.createVerticalStrut(10));

			tfMaHD.setBackground(Color.decode("#D0BAFB"));
			tfMaHD.setBorder(null);
			tfMaHD.setEditable(false);
			tfTenPhong.setBackground(Color.decode("#D0BAFB"));
			tfTenPhong.setBorder(null);
			tfTenPhong.setEditable(false);
			tfTenKH.setBackground(Color.decode("#D0BAFB"));
			tfTenKH.setBorder(null);
			tfTenKH.setEditable(false);
			tfSDT.setBackground(Color.decode("#D0BAFB"));
			tfSDT.setBorder(null);
			tfSDT.setEditable(false);

			boxForThongTin.add(Box.createHorizontalStrut(10));
			boxForThongTin.add(box1);
			boxForThongTin.add(Box.createHorizontalStrut(50));
			boxForThongTin.add(box2);
			boxForThongTin.add(Box.createHorizontalStrut(50));

			JPanel paneForBackBtn = new JPanel();
			paneForBackBtn.setBackground(Color.decode("#D0BAFB"));
			paneForBackBtn.add(btnQuayLai = new ButtonGradient("Quay Lại", imgBack));
			btnQuayLai.setBackground(Color.decode("#6fa8dc"));

			thongTinPane.add(boxForThongTin, BorderLayout.CENTER);
			thongTinPane.add(paneForBackBtn, BorderLayout.EAST);
			thongTinPane.setBackground(Color.decode("#D0BAFB"));
			bottomPane.add(thongTinPane, BorderLayout.NORTH);

			// Pane for table
			boxForTable = Box.createHorizontalBox();

			// Table dịch vụ kho
			paneForTableDV = new JPanel(new BorderLayout());
			paneForTableDV.setBorder(BorderFactory.createTitledBorder(border, "Danh Sách Dịch Vụ"));
			paneForTableDV.setBackground(Color.decode("#D0BAFB"));
			JPanel paneForBtnTableDV = new JPanel();
			Box boxForPaneForBtnTableDV = Box.createHorizontalBox();
			JPanel paneForTimKiemDV1 = new JPanel();
			paneForTimKiemDV1.setBackground(Color.decode("#D0BAFB"));
			paneForTimKiemDV1.add(lbTimKiemDV1 = new JLabel("Tìm Kiếm Dịch Vụ: "));
			paneForTimKiemDV1.add(tfTimKiemDV1 = new JTextField(15));
			boxForPaneForBtnTableDV.add(paneForTimKiemDV1);
			boxForPaneForBtnTableDV.add(Box.createHorizontalStrut(100));
			boxForPaneForBtnTableDV.add(btnThem = new ButtonGradient("Thêm", imgAdd));
			btnThem.setBackground(Color.decode("#6fa8dc"));
			paneForBtnTableDV.add(boxForPaneForBtnTableDV);
			paneForBtnTableDV.setBackground(Color.decode("#D0BAFB"));

			String[] headers1 = { "STT", "Tên Dịch Vụ", "Đơn Giá", "Đơn Vị", "Số Lượng Tồn" };
			model1 = new DefaultTableModel(headers1, 0);
			tableDVKho = new JTable(model1);
			tableDVKho.setRowHeight(25);
			tableDVKho.getColumnModel().getColumn(1).setPreferredWidth(200);

			JScrollPane scroll1 = new JScrollPane(tableDVKho, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			paneForTableDV.add(paneForBtnTableDV, BorderLayout.NORTH);
			paneForTableDV.add(scroll1, BorderLayout.CENTER);

			// Table dịch vụ phòng
			paneForTableDVPhong = new JPanel(new BorderLayout());
			paneForTableDVPhong.setBorder(BorderFactory.createTitledBorder(border, "Dịch Vụ Đã Thêm"));
			paneForTableDVPhong.setBackground(Color.decode("#D0BAFB"));
			JPanel paneForBtnTableDVP = new JPanel();
			Box boxForPaneForBtnTableDVP = Box.createHorizontalBox();
			JPanel paneForTimKiemDV2 = new JPanel();
			paneForTimKiemDV2.add(lbTimKiemDV2 = new JLabel("Tìm Kiếm Dịch Vụ: "));
			paneForTimKiemDV2.add(tfTimKiemDV2 = new JTextField(15));
			paneForTimKiemDV2.setBackground(Color.decode("#D0BAFB"));
			boxForPaneForBtnTableDVP.add(paneForTimKiemDV2);
			boxForPaneForBtnTableDVP.add(Box.createHorizontalStrut(100));
			boxForPaneForBtnTableDVP.add(btnXoa = new ButtonGradient("Xóa", imgDel));
			btnXoa.setBackground(Color.decode("#6fa8dc"));
			paneForBtnTableDVP.add(boxForPaneForBtnTableDVP);
			paneForBtnTableDVP.setBackground(Color.decode("#D0BAFB"));

			String[] headers2 = { "STT", "Tên Dịch Vụ", "Đơn Giá", "Số Lượng", "Thành Tiền" };
			model2 = new DefaultTableModel(headers2, 0);
			tableDVPhong = new JTable(model2);
			tableDVPhong.setRowHeight(25);

			tableDVPhong.getColumnModel().getColumn(1).setPreferredWidth(200);

			TableColumnModel colModelDVPhong = tableDVPhong.getColumnModel();
			TableColumn colTableDVPhong = colModelDVPhong.getColumn(3);
			colTableDVPhong.setCellEditor(new MySpinnerEditor());

			JScrollPane scroll2 = new JScrollPane(tableDVPhong, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			paneForTableDVPhong.add(paneForBtnTableDVP, BorderLayout.NORTH);
			paneForTableDVPhong.add(scroll2, BorderLayout.CENTER);

			boxForTable.add(paneForTableDV);
			boxForTable.add(paneForTableDVPhong);
			bottomPane.add(boxForTable);

			mainPane = new JPanel(new BorderLayout());
			mainPane.add(topPane, BorderLayout.NORTH);
			mainPane.add(bottomPane, BorderLayout.CENTER);
			this.getContentPane().add(mainPane);

			tfTenPhong.setFont(new Font("sanserif", Font.PLAIN, 15));
			tfTenKH.setFont(new Font("sanserif", Font.PLAIN, 15));
			tfMaHD.setFont(new Font("sanserif", Font.PLAIN, 15));
			tfSDT.setFont(new Font("sanserif", Font.PLAIN, 15));
			lbMaPDP.setFont(new Font("sanserif", Font.BOLD, 15));
			lbTenKH.setFont(new Font("sanserif", Font.BOLD, 15));
			lbSDT.setFont(new Font("sanserif", Font.BOLD, 15));
			lbTenPhong.setFont(new Font("sanserif", Font.BOLD, 15));

			lbTenPhong.setPreferredSize(lbTenKH.getPreferredSize());
			lbMaPDP.setPreferredSize(lbTenKH.getPreferredSize());
			lbSDT.setPreferredSize(lbTenKH.getPreferredSize());

			
			try {
				readDataToFieldThongTin();
				readDataToTableDichVu();
				readDataToTableDichVuPhong();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tableDVPhong.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					try {
						xuLyTangGiamSoLuongDichVuKho();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnThem.addActionListener(e -> {
				try {
					xuLyThemDVPhong();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			btnXoa.addActionListener(e -> {
				try {
					xuLyXoaDVPhong();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			btnQuayLai.addActionListener(e -> this.dispose());
		}
		
		private void xuLyThemDVPhong() throws RemoteException {
			int id = Integer.valueOf(tfMaHD.getText().trim());
			if (tableDVKho.getSelectedRow() != -1) {
				Service service = serviceDAO.getServiceByName(((String) model1.getValueAt(tableDVKho.getSelectedRow(), 1))).get(0);
				service.setInventoryNumber(service.getInventoryNumber() - 1);
				serviceDAO.updateService(service);
				List<DetailServiceRoom> detailServiceRooms = detailServiceDAO.searchDetailServiceRoomByBillID(id);
				List<Service> services = detailServiceRooms.stream().map(d -> d.getService()).toList();
				try {
					if(!services.contains(service)) {
						if (detailServiceDAO.createCTDVPhong(new DetailServiceRoom(0,
								billDAO.getBillsByBillID(Integer.valueOf(tfMaHD.getText().trim())).get(0), service, 1))) {
							JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!!!");
						}
					}
					else {
						DetailServiceRoom detailServiceRoom = detailServiceDAO.searchDetailServiceRoomByServiceName(id, service.getId()).get(0);
						detailServiceRoom.setQuantity(detailServiceRoom.getQuantity() + 1);
						if(detailServiceDAO.updateDetailService(detailServiceRoom)) {
							JOptionPane.showMessageDialog(this, "Cập nhật số lượng dịch vụ phòng thành công!!!");
						}
					}
					readDataToTableDichVu();
					readDataToTableDichVuPhong();
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(this, "Thêm dịch vụ không thành công!!!");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Hãy chọn dịch vụ bạn muốn thêm vào phòng!!!");
			}
		}
		
		private void xuLyXoaDVPhong() throws RemoteException {
			if (tableDVPhong.getSelectedRow() != -1) {
				Service service = serviceDAO.getServiceByName((String) model2.getValueAt(tableDVPhong.getSelectedRow(), 1)).get(0);
				try {
					int soLuong = (int) model2.getValueAt(tableDVPhong.getSelectedRow(), 3);
					System.out.println(soLuong);
					int soLuongBanDau = service.getInventoryNumber();
					service.setInventoryNumber(soLuong + soLuongBanDau);
					if(serviceDAO.updateService(service)) {
						if (detailServiceDAO.delete(Integer.valueOf(tfMaHD.getText().trim()), service.getId())) {
							readDataToTableDichVuPhong();
							readDataToTableDichVu();
							JOptionPane.showMessageDialog(this, "Xóa dịch vụ phòng thành công!!!");
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(this, "Xóa dịch vụ phòng không thành công!!!");
					e.printStackTrace();
				}
			}
			
		}
		
		private void readDataToFieldThongTin() throws RemoteException {
			Room r = roomDAO
					.getRoomsByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0))
					.get(0);
			List<DetailBill> detailBills = detailBillDAO.findDetailBillByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 0));
			DetailBill detailBill = detailBills.get(detailBills.size() - 1);
			List<Bill> bills = billDAO.getBillsByBillID(detailBill.getBill().getId());
			Bill hd = bills.get(bills.size() - 1);
			tfMaHD.setText(detailBill.getBill().getId() + "");
			tfTenPhong.setText(r.getName());
			tfTenKH.setText(hd.getCustomer().getCustomerName());
			tfSDT.setText(hd.getCustomer().getPhoneNumber());
		}
		
		private void readDataToTableDichVu() throws RemoteException {
			model1.setRowCount(0);
			List<Service> services = serviceDAO.getAllServices();
			int i = 0;
			for (Service service : services) {
				System.out.println(service.getInventoryNumber());
				if (service.getInventoryNumber() > 0)
					model1.addRow(new Object[] { ++i, service.getName(), service.getPrice(), service.getUnit(), service.getInventoryNumber() });
			}
		}
		
		private void readDataToTableDichVuPhong() throws NumberFormatException, RemoteException {
			model2.setRowCount(0);
			List<DetailServiceRoom> detailServiceRooms = detailServiceDAO.searchDetailServiceRoomByBillID(Integer.valueOf(tfMaHD.getText().trim()));
			int i = 0;
			for (DetailServiceRoom d : detailServiceRooms) {
				model2.addRow(new Object[] { ++i, d.getService().getName(), d.getService().getPrice(),
						d.getQuantity(), d.calculateMoneyService()});
			}
		}
		
		private void xuLyTangGiamSoLuongDichVuKho() throws RemoteException {
			Service service = serviceDAO.getServiceByName((String) model2.getValueAt(tableDVPhong.getSelectedRow(), 1))
					.get(0);
			DetailServiceRoom detailServiceRoom = detailServiceDAO.searchDetailServiceRoomByServiceName(Integer.valueOf(tfMaHD.getText().trim()), service.getId())
					.get(0);
			int soLuongBanDau = detailServiceRoom.getQuantity();
			int soLuongCapNhat = (int) model2.getValueAt(tableDVPhong.getSelectedRow(), 3);
			int soLuongTinhToan = service.getInventoryNumber() + (soLuongBanDau - soLuongCapNhat);
			
			detailServiceRoom.setQuantity(soLuongCapNhat);
			service.setInventoryNumber(soLuongTinhToan);
			
			if (soLuongCapNhat > 0) {
				if (soLuongTinhToan >= 0) {
					try {
						serviceDAO.updateService(service);
						detailServiceDAO.updateDetailService(detailServiceRoom);
						readDataToTableDichVu();
						readDataToTableDichVuPhong();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Số lượng dịch vụ trong kho không đủ!!!");
					model2.setValueAt(detailServiceRoom.getQuantity(), tableDVPhong.getSelectedRow(), 3);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0 !!!");
				model2.setValueAt(detailServiceRoom.getQuantity(), tableDVPhong.getSelectedRow(), 3);
			}
		}
	}
	
	public class TinhTien extends JFrame {
		private JTextField tfMaHD, tfTenKH, tfNgayThanhToan, tfTenNhanVien, tfSDTKhach, tfGioThanhToan, tfTienNhan,
				tfTienThua, tfTienPhong, tfTienDichVu, tfGiamGia, tfTongCong, tfThue, tfThanhTien;
		private JButton btnQuayLai, btnInHD, btnThanhToan;
		private JTable tablePhong, tableDichVu;
		private DefaultTableModel modelPhong, modelDichVu;
		private SimpleDateFormat gioFormat = new SimpleDateFormat("hh:mm");
		private DateTimeFormatter gioFormatForDate = DateTimeFormatter.ofPattern("HH:mm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		public TinhTien() {
			// TODO Auto-generated constructor stub
			setSize(650, 700);
			setLocationRelativeTo(null);
			dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
			Icon imgDel = new ImageIcon("src/img/del.png");
			Icon imgReset = new ImageIcon("src/img/refresh16.png");
			Icon imgEdit = new ImageIcon("src/img/edit.png");
			Icon imgOut = new ImageIcon("src/img/out.png");
			Icon imgSearch = new ImageIcon("src/img/search.png");
			Icon imgCheck = new ImageIcon("src/img/check16.png");
			Icon imgCancel = new ImageIcon("src/img/cancel16.png");
			Icon imgBack = new ImageIcon("src/img/back16.png");
			Icon imgAdd = new ImageIcon("src/img/add16.png");
			Icon imgChange = new ImageIcon("src/img/change16.png");

			JLabel lbMaHD, lbTenKH, lbNgayThanhToan, lbTenNhanVien, lbSDTKhach, lbGioThanhToan, lbTienNhan, lbTienThua,
					lbTienPhong, lbTienDichVu, lbGiamGia, lbTongCong, lbThue, lbThanhTien, lbTieuDe, lbTablePhong,
					lbTableDichVu;

			JPanel mainPane, topPane, bottomPane, thongTinHDPane, tablePhongPane, tableDVPane, leftBottomPane,
					rightBottomPane;
			Font font = new Font("Arial", Font.BOLD, 24);
			Border border = new LineBorder(Color.black);

			// Tieu De
			mainPane = new JPanel(new BorderLayout());
			topPane = new JPanel();
			topPane.setBackground(Color.decode("#990447"));
			lbTieuDe = new JLabel("Tính Tiền");
			lbTieuDe.setFont(font);
			topPane.add(lbTieuDe);

			bottomPane = new JPanel(new BorderLayout());
			// Thông tin hóa đơn
			thongTinHDPane = new JPanel();
			Box box = Box.createVerticalBox();
			JPanel paneForThongTinHD = new JPanel(new FlowLayout(FlowLayout.RIGHT));

			Box boxForThongTinHDLeft = Box.createVerticalBox();
			Box boxForMaHD = Box.createHorizontalBox();
			boxForMaHD.add(lbMaHD = new JLabel("Mã Hóa Đơn: "));
			boxForMaHD.add(tfMaHD = new JTextField(15));
			Box boxForTenKH = Box.createHorizontalBox();
			boxForTenKH.add(lbTenKH = new JLabel("Tên Khách Hàng: "));
			boxForTenKH.add(tfTenKH = new JTextField(15));
			Box boxForNgayThanhToan = Box.createHorizontalBox();
			boxForNgayThanhToan.add(lbNgayThanhToan = new JLabel("Ngày Thanh Toán: "));
			boxForNgayThanhToan.add(tfNgayThanhToan = new JTextField(15));

			boxForThongTinHDLeft.add(Box.createVerticalStrut(10));
			boxForThongTinHDLeft.add(boxForMaHD);
			boxForThongTinHDLeft.add(Box.createVerticalStrut(10));
			boxForThongTinHDLeft.add(boxForTenKH);
			boxForThongTinHDLeft.add(Box.createVerticalStrut(10));
			boxForThongTinHDLeft.add(boxForNgayThanhToan);
			boxForThongTinHDLeft.add(Box.createVerticalStrut(10));

			Box boxForThongTinHDRight = Box.createVerticalBox();
			Box boxForTenNV = Box.createHorizontalBox();
			boxForTenNV.add(lbTenNhanVien = new JLabel("Tên Nhân Viên: "));
			boxForTenNV.add(tfTenNhanVien = new JTextField(15));
			Box boxForSDTK = Box.createHorizontalBox();
			boxForSDTK.add(lbSDTKhach = new JLabel("Số Điện Thoại Khách: "));
			boxForSDTK.add(tfSDTKhach = new JTextField(15));
			Box boxForGioThanhToan = Box.createHorizontalBox();
			boxForGioThanhToan.add(lbGioThanhToan = new JLabel("Ngày vào hát: "));
			boxForGioThanhToan.add(tfGioThanhToan = new JTextField(15));

			boxForThongTinHDRight.add(Box.createVerticalStrut(10));
			boxForThongTinHDRight.add(boxForTenNV);
			boxForThongTinHDRight.add(Box.createVerticalStrut(10));
			boxForThongTinHDRight.add(boxForSDTK);
			boxForThongTinHDRight.add(Box.createVerticalStrut(10));
			boxForThongTinHDRight.add(boxForGioThanhToan);
			boxForThongTinHDRight.add(Box.createVerticalStrut(10));

			lbTenNhanVien.setPreferredSize(lbSDTKhach.getPreferredSize());
			lbGioThanhToan.setPreferredSize(lbSDTKhach.getPreferredSize());
			lbMaHD.setPreferredSize(lbSDTKhach.getPreferredSize());
			lbTenKH.setPreferredSize(lbSDTKhach.getPreferredSize());
			lbNgayThanhToan.setPreferredSize(lbSDTKhach.getPreferredSize());

			tfMaHD.setBorder(null);
			tfMaHD.setEditable(false);
			tfMaHD.setBackground(Color.white);
			tfTenKH.setBorder(null);
			tfTenKH.setEditable(false);
			tfTenKH.setBackground(Color.white);
			tfNgayThanhToan.setBorder(null);
			tfNgayThanhToan.setEditable(false);
			tfNgayThanhToan.setBackground(Color.white);
			tfTenNhanVien.setBorder(null);
			tfTenNhanVien.setEditable(false);
			tfTenNhanVien.setBackground(Color.white);
			tfSDTKhach.setBorder(null);
			tfSDTKhach.setEditable(false);
			tfSDTKhach.setBackground(Color.white);
			tfGioThanhToan.setBorder(null);
			tfGioThanhToan.setEditable(false);
			tfGioThanhToan.setBackground(Color.white);
			tfMaHD.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfTenKH.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfNgayThanhToan.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfTenNhanVien.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfSDTKhach.setFont(new Font("sanserif", Font.PLAIN, 13));
			tfGioThanhToan.setFont(new Font("sanserif", Font.PLAIN, 13));
			lbMaHD.setFont(new Font("sanserif", Font.BOLD, 13));
			lbTenKH.setFont(new Font("sanserif", Font.BOLD, 13));
			lbNgayThanhToan.setFont(new Font("sanserif", Font.BOLD, 13));
			lbTenNhanVien.setFont(new Font("sanserif", Font.BOLD, 13));
			lbSDTKhach.setFont(new Font("sanserif", Font.BOLD, 13));
			lbGioThanhToan.setFont(new Font("sanserif", Font.BOLD, 13));

			paneForThongTinHD.setBackground(Color.white);
			paneForThongTinHD.add(boxForThongTinHDLeft);
			paneForThongTinHD.add(boxForThongTinHDRight);
			thongTinHDPane.setBackground(Color.white);
			thongTinHDPane.add(paneForThongTinHD);

			// Thông tin Phòng
			tablePhongPane = new JPanel(new BorderLayout());
			JPanel titleTablePhongPane = new JPanel();
			titleTablePhongPane.setBackground(Color.white);
			lbTablePhong = new JLabel("Thông tin chi tiết sử dụng phòng");
			lbTablePhong.setFont(new Font("sanserif", Font.PLAIN, 18));
			titleTablePhongPane.add(lbTablePhong);
			String[] headerTablePhong = { "Tên Phòng", "Loại Phòng", "Giờ Nhận", "Giờ Trả", "Thời Lượng(phút)",
					"Giá Phòng", "Thành Tiền" };
			modelPhong = new DefaultTableModel(headerTablePhong, 0);
			tablePhong = new JTable(modelPhong);
			tablePhong.setRowHeight(25);
			tablePhong.getColumnModel().getColumn(4).setPreferredWidth(120);
			tablePhong.getColumnModel().getColumn(6).setPreferredWidth(150);

			JScrollPane scrollTablePhong = new JScrollPane(tablePhong, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tablePhongPane.add(scrollTablePhong, BorderLayout.CENTER);
			tablePhongPane.add(titleTablePhongPane, BorderLayout.NORTH);

			// Thông tin dịch vụ
			tableDVPane = new JPanel(new BorderLayout());
			JPanel titleTableDVPane = new JPanel();
			titleTableDVPane.setBackground(Color.white);
			lbTableDichVu = new JLabel("Thông tin chi tiết dịch vụ");
			lbTableDichVu.setFont(new Font("sanserif", Font.PLAIN, 18));
			titleTableDVPane.add(lbTableDichVu);
			String[] headerTableDV = { "STT", "Tên Dịch Vụ", "Đơn Vị", "Số Lượng", "Đơn Giá", "Thành Tiền" };
			modelDichVu = new DefaultTableModel(headerTableDV, 0);
			tableDichVu = new JTable(modelDichVu);
			tableDichVu.setRowHeight(25);
			JScrollPane scrollTableDichVu = new JScrollPane(tableDichVu, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			tableDVPane.add(scrollTableDichVu, BorderLayout.CENTER);
			tableDVPane.add(titleTableDVPane, BorderLayout.NORTH);

			box.add(thongTinHDPane);
			box.add(tablePhongPane);
			box.add(tableDVPane);
			bottomPane.add(box, BorderLayout.CENTER);

			// Thông tin tiền
			Box box2 = Box.createHorizontalBox();
			leftBottomPane = new JPanel(new BorderLayout());
			Box boxForTienNhan = Box.createHorizontalBox();
			boxForTienNhan.add(lbTienNhan = new JLabel("Tiền Nhận:   "));
			boxForTienNhan.add(tfTienNhan = new JTextField(15));

			JPanel paneForTienNhan = new JPanel();
			paneForTienNhan.add(boxForTienNhan);
			paneForTienNhan.setBackground(Color.white);

			Box boxForTienThua = Box.createHorizontalBox();
			boxForTienThua.add(lbTienThua = new JLabel("Tiền Thừa: "));
			boxForTienThua.add(tfTienThua = new JTextField(15));

			JPanel paneForTienThua = new JPanel();
			paneForTienThua.add(boxForTienThua);
			paneForTienThua.setBackground(Color.white);

			Box boxForTienNhanThua = Box.createVerticalBox();
			boxForTienNhanThua.add(Box.createVerticalStrut(50));
			boxForTienNhanThua.add(paneForTienNhan);
			boxForTienNhanThua.add(Box.createVerticalStrut(20));
			boxForTienNhanThua.add(paneForTienThua);
			boxForTienNhanThua.setBackground(Color.white);

			tfTienThua.setEditable(false);
			lbTienThua.setPreferredSize(lbTienNhan.getPreferredSize());

			JPanel paneForBtnBack = new JPanel(new FlowLayout(FlowLayout.LEFT));
			paneForBtnBack.setBackground(Color.white);
			paneForBtnBack.add(btnQuayLai = new ButtonGradient("Quay lại", imgBack));
			btnQuayLai.setBackground(Color.decode("#6fa8dc"));

			JPanel paneForBoxForTienNhanThua = new JPanel();
			paneForBoxForTienNhanThua.setBackground(Color.white);
			paneForBoxForTienNhanThua.add(boxForTienNhanThua);
			leftBottomPane.add(paneForBoxForTienNhanThua, BorderLayout.NORTH);
			leftBottomPane.add(paneForBtnBack, BorderLayout.SOUTH);
			box2.add(leftBottomPane);

			//
			rightBottomPane = new JPanel(new BorderLayout());
			rightBottomPane.setBackground(Color.white);

			Box boxForTienPhong = Box.createHorizontalBox();
			boxForTienPhong.add(lbTienPhong = new JLabel("Tiền Phòng: "));
			boxForTienPhong.add(tfTienPhong = new JTextField(15));

			Box boxForTienDichVu = Box.createHorizontalBox();
			boxForTienDichVu.add(lbTienDichVu = new JLabel("Tiền Dịch Vụ: "));
			boxForTienDichVu.add(tfTienDichVu = new JTextField(15));

			Box boxForGiamGia = Box.createHorizontalBox();
			boxForGiamGia.add(lbGiamGia = new JLabel("Giảm Giá: "));
			boxForGiamGia.add(tfGiamGia = new JTextField(15));

			Box boxForTongCong = Box.createHorizontalBox();
			boxForTongCong.add(lbTongCong = new JLabel("Tổng Cộng: "));
			boxForTongCong.add(tfTongCong = new JTextField(15));

			Box boxForThue = Box.createHorizontalBox();
			boxForThue.add(lbThue = new JLabel("Thuế VAT: "));
			boxForThue.add(tfThue = new JTextField(15));

			Box boxForThanhTien = Box.createHorizontalBox();
			boxForThanhTien.add(lbThanhTien = new JLabel("Thành Tiền: "));
			boxForThanhTien.add(tfThanhTien = new JTextField(15));

			lbTienPhong.setPreferredSize(lbTienDichVu.getPreferredSize());
			lbGiamGia.setPreferredSize(lbTienDichVu.getPreferredSize());
			lbTongCong.setPreferredSize(lbTienDichVu.getPreferredSize());
			lbTongCong.setPreferredSize(lbTienDichVu.getPreferredSize());
			lbThue.setPreferredSize(lbTienDichVu.getPreferredSize());
			lbThanhTien.setPreferredSize(lbTienDichVu.getPreferredSize());

			tfTienPhong.setBorder(null);
			tfTienPhong.setEditable(false);
			tfTienPhong.setBackground(Color.white);
			tfTienDichVu.setBorder(null);
			tfTienDichVu.setEditable(false);
			tfTienDichVu.setBackground(Color.white);
			tfGiamGia.setBorder(null);
			tfGiamGia.setEditable(false);
			tfGiamGia.setBackground(Color.white);
			tfTongCong.setBorder(null);
			tfTongCong.setEditable(false);
			tfTongCong.setBackground(Color.white);
			tfThue.setBorder(null);
			tfThue.setEditable(false);
			tfThue.setBackground(Color.white);
			tfThanhTien.setBorder(null);
			tfThanhTien.setEditable(false);
			tfThanhTien.setBackground(Color.white);

			Box boxForRight = Box.createVerticalBox();
			boxForRight.add(boxForTienPhong);
			boxForRight.add(Box.createVerticalStrut(10));
			boxForRight.add(boxForTienDichVu);
			boxForRight.add(Box.createVerticalStrut(10));
			boxForRight.add(boxForGiamGia);
			boxForRight.add(Box.createVerticalStrut(10));
			boxForRight.add(boxForTongCong);
			boxForRight.add(Box.createVerticalStrut(10));
			boxForRight.add(boxForThue);
			boxForRight.add(Box.createVerticalStrut(10));
			boxForRight.add(boxForThanhTien);

			JPanel paneForBtnThanhToan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			paneForBtnThanhToan.setBackground(Color.white);
			paneForBtnThanhToan.add(btnInHD = new ButtonGradient("In Hóa Đơn"));
			btnInHD.setBackground(Color.decode("#6fa8dc"));
			paneForBtnThanhToan.add(Box.createHorizontalStrut(20));
			paneForBtnThanhToan.add(btnThanhToan = new ButtonGradient("Thanh Toán"));
			btnThanhToan.setBackground(Color.decode("#6fa8dc"));

			JPanel paneForBoxForRight = new JPanel();
			paneForBoxForRight.add(boxForRight);
			rightBottomPane.add(paneForBoxForRight, BorderLayout.CENTER);
			paneForBoxForRight.setBackground(Color.white);
			rightBottomPane.add(paneForBtnThanhToan, BorderLayout.SOUTH);

			box2.add(rightBottomPane);

			bottomPane.add(box2, BorderLayout.SOUTH);
			box2.setBackground(Color.white);

			mainPane.add(topPane, BorderLayout.NORTH);
			mainPane.add(bottomPane, BorderLayout.CENTER);
			this.getContentPane().add(mainPane);

			try {
				readDataToFieldThongTin();
				readDataToTablePhong();
				readDataToTableDichVu();
				xuLyTinhTienDienVaoThongTin();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			btnQuayLai.addActionListener(e -> this.dispose());
			btnThanhToan.addActionListener(e -> {
				try {
					xuLyThanhToan();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			btnInHD.addActionListener(e -> xuLyInHD());

		}

		private void xuLyThanhToan() throws RemoteException {
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
						JOptionPane.showMessageDialog(this, "Thanh toán thành công");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Thanh toán không thành công");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		private void xuLyInHD() {

		}

		private void readDataToFieldThongTin() throws RemoteException {
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

		private void readDataToTablePhong() throws NumberFormatException, RemoteException {
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

		private void xuLyTinhTienDienVaoThongTin() throws NumberFormatException, RemoteException {
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

		private void readDataToTableDichVu() throws NumberFormatException, RemoteException {
			modelDichVu.setRowCount(0);
			List<DetailServiceRoom> detailServiceRooms = detailServiceDAO.searchDetailServiceRoomByBillID(Integer.valueOf(tfMaHD.getText().trim()));
			int i = 0;
			for (DetailServiceRoom d : detailServiceRooms) {
				modelDichVu.addRow(new Object[] { ++i, d.getService().getName(), d.getService().getUnit(),
						d.getQuantity(), d.getService().getPrice(), formatter.format(d.calculateMoneyService())});
			}
		}

	}
}
