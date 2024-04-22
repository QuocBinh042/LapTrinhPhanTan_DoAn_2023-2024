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
import java.util.Calendar;
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

import app.PanelDatPhong.ChuyenPhong;
import app.PanelDatPhong.PhongCho;
import app.PanelDatPhong.ThuePhong;
import dao.BillDAO;
import dao.BookingDAO;
import dao.CustomerDAO;
import dao.DetailBillDAO;
import dao.EmployeeDAO;
import dao.RoomDAO;
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

public class PanelBooking extends JFrame {
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
		setSize(1400, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		roomDAO = new RoomDAO();
		customerDAO = new CustomerDAO();
		bookingDAO = new BookingDAO();
		billDAO = new BillDAO();
		employeeDAO = new EmployeeDAO();
		detailBillDAO = new DetailBillDAO();
		setManNV(maNV);

		Icon imgAdd = new ImageIcon("src/main/java/img/add2.png");
		Icon imgDel = new ImageIcon("src/main/java/img/del.png");
		Icon imgReset = new ImageIcon("src/main/java/img/refresh16.png");
		Icon imgEdit = new ImageIcon("src/main/java/img/edit.png");
		Icon imgOut = new ImageIcon("src/main/java/img/out.png");
		Icon imgSearch = new ImageIcon("src/main/java/img/search.png");
		Icon imgCheck = new ImageIcon("src/main/java/img/check16.png");
		Icon imgCancel = new ImageIcon("src/main/java/img/cancel16.png");

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
//		panePhong.setBorder(BorderFactory.createTitledBorder(blackLine, "Thông tin phòng"));
		panePhong.setBackground(Color.decode("#D0BAFB"));

		JPanel main = new JPanel();
		main.setLayout(new BorderLayout());
		main.add(panePhong, BorderLayout.CENTER);

		this.getContentPane().add(main);
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
		List<Room> dsP = roomDAO.getRoomsByCapacity(Integer.valueOf((String) soNguoiB.getSelectedItem()));
		if (((String) soNguoiB.getSelectedItem()).equalsIgnoreCase("Tất cả")) {
			getAllDataRooms();
		}
		for (Room room : dsP) {
			phongModel.addRow(new Object[] { room.getName(), room.getRoomType().getTypeRoom(),
					room.getRoomType().getCapacity(), room.getRoomStatus(), room.getRoomType().getPrice() });
		}
	}

	private void xuLyThuePhong() {
		Room room = roomDAO.getRoomsByRoomName((String) phongModel.getValueAt(phongTable.getSelectedRow(), 3)).get(0);
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

			Icon imgDel = new ImageIcon("src/main/java/img/del.png");
			Icon imgReset = new ImageIcon("src/main/java/img/refresh16.png");
			Icon imgEdit = new ImageIcon("src/main/java/img/edit.png");
			Icon imgOut = new ImageIcon("src/main/java/img/out.png");
			Icon imgSearch = new ImageIcon("src/main/java/img/search.png");
			Icon imgCheck = new ImageIcon("src/main/java/img/check16.png");
			Icon imgCancel = new ImageIcon("src/main/java/img/cancel16.png");
			Icon imgBack = new ImageIcon("src/main/java/img/back16.png");
			Icon imgAdd = new ImageIcon("src/main/java/img/add16.png");

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
			btnLamMoi.setPreferredSize(btnTimKiem.getPreferredSize());

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
			bottomPaneRight.add(btnChuyenPhong = new ButtonGradient("Chuyển phòng", imgChange));
			btnChuyenPhong.setBackground(Color.decode("#6fa8dc"));

			bottomPaneLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
			bottomPaneLeft.setBackground(Color.decode("#cccccc"));
			bottomPaneLeft.add(btnQuayLai = new ButtonGradient("Quay lại", imgBack));
			btnQuayLai.setBackground(Color.decode("#6fa8dc"));

			bottomPane.add(bottomPaneLeft);
			bottomPane.add(bottomPaneRight);

			mainPane.add(topPane, BorderLayout.NORTH);
			mainPane.add(leftPane, BorderLayout.WEST);
			mainPane.add(rightPane, BorderLayout.CENTER);
			mainPane.add(bottomPane, BorderLayout.SOUTH);

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
			btnTimKiem.addActionListener(e -> xuLyTimKiem());
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

		private void xuLyTimKiem() {

		}

		private void xuLyChuyenPhong() throws RemoteException {
			Room p = roomDAO
					.getRoomsByRoomName2((String) modelPhongTrong.getValueAt(tablePhongTrong.getSelectedRow(), 1))
					.get(0);
			DetailBill cthd = detailBillDAO.findDetailBillByRoomIDOrderByID(tenPhong).get(0);
			Bill bill = billDAO.getBillsByDetailBillID(Integer.valueOf(cthd.getId())).get(0);
			DetailBill chiTietHD = new DetailBill(0, p, bill, LocalDateTime.now(), null);
			cthd.setCheckout(LocalDateTime.now());
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

		}

		private void xuLyLocTheoSucChua() {

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
			tfThoiGianSuDung.setText(time + "");
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
			timKiemSDTBtn.addActionListener(e -> xuLyTimKiemSDT());
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

		private void xuLyTimKiemSDT() {

		}
	}
}
