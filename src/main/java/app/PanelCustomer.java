package app;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import AppEvent.PanelCustomerEvent;
import appService.PanelCustomerService;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.regex.Pattern;
import java.util.List;
import dao.CustomerDAO;
import entity.Customer;

public class PanelCustomer extends JPanel implements MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblTenKhachHang, lblSoDienThoai, lblGhiChu, lblMaKH;
	private JTextField txtCustomerName, txtPhoneNumber, txtEmail, txtTimSDT, txtTimTenKH, txtMaKH;
	private JTextArea txaNote;
	private JButton btnThemMoi, btnCapNhat, btnLamMoi, btnTim, btnLamMoiKH, btnLuu;
	private String[] headers = { "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Ghi chú" };
	private JTable table;
	private DefaultTableModel tableModel;
	private CustomerDAO daoCustomer = new CustomerDAO();
	private List<Customer> listCustomer;
	private PanelCustomerEvent customerEvent = new PanelCustomerEvent();

	public PanelCustomer() throws RemoteException {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		createUI();
		customerEvent.getAllCustomers(table, tableModel);
		// sự kiện
		btnLamMoi.addActionListener(e -> customerEvent.refesh(txtMaKH, txtCustomerName, txtPhoneNumber, txaNote));
		btnThemMoi.addActionListener(e -> {
			try {
				customerEvent.processAddCustomer(txtCustomerName, txtPhoneNumber, txtCustomerName, tableModel);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnCapNhat.addActionListener(e -> {
			try {
				customerEvent.processUpdate(table, txtCustomerName, txtPhoneNumber, txaNote);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnTim.addActionListener(e -> customerEvent.processSearch(txtTimSDT, table));
		table.addMouseListener(this);

	}

	public void createUI() {
		Icon img_add = new ImageIcon("src/main/java/img/add16.png");
		Icon img_del = new ImageIcon("src/main/java/img/bin.png");
		Icon img_reset = new ImageIcon("src/main/java/img/refresh16.png");
		Icon img_edit = new ImageIcon("src/main/java/img/edit16.png");
		Icon img_out = new ImageIcon("src/main/java/img/out.png");
		Icon img_search = new ImageIcon("src/main/java/img/search.png");
		Icon img_refresh = new ImageIcon("src/main/java/img/refresh16.png");
		Border line = BorderFactory.createLineBorder(Color.BLACK);

		// Thông tin kháchh hàng
		JPanel pnlInput = new JPanel();
		pnlInput.setLayout(new GridLayout(3, 2, 40, 0));
		pnlInput.add(customerEvent.createPanel(lblMaKH = new JLabel("Mã khách hàng"), txtMaKH = new JTextField()));
		pnlInput.add(customerEvent.createPanel(lblTenKhachHang = new JLabel("Tên khách hàng"), txtCustomerName = new JTextField()));
		pnlInput.add(customerEvent.createPanel(lblSoDienThoai = new JLabel("Số điện thoại"), txtPhoneNumber = new JTextField()));
		pnlInput.add(customerEvent.createPanel(lblGhiChu = new JLabel("Ghi chú"), txaNote = new JTextArea()));

		// Nút chức năng
		JPanel pnlChucNang = new JPanel();
		pnlChucNang.setBackground(Color.decode("#cccccc"));
		pnlChucNang.setLayout(new GridLayout(3, 2));
		pnlChucNang.add(btnThemMoi = new ButtonGradient("Thêm mới", img_add));
		pnlChucNang.add(btnCapNhat = new ButtonGradient("Cập nhật", img_edit));
		pnlChucNang.add(btnLamMoi = new ButtonGradient("Làm mới", img_reset));

		Box bThongTinKH = Box.createHorizontalBox();
		bThongTinKH.add(pnlInput);
		bThongTinKH.add(Box.createHorizontalStrut(50));
		bThongTinKH.add(pnlChucNang);
		JPanel pnlThongTinKH = new JPanel();
		pnlThongTinKH.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
		pnlThongTinKH.add(bThongTinKH);

		// Set kích thước
		Dimension dimension = new Dimension(250, 30);
		txtMaKH.setPreferredSize(dimension);
		txtCustomerName.setPreferredSize(dimension);
		txtPhoneNumber.setPreferredSize(dimension);
		txaNote.setPreferredSize(dimension);
		lblTenKhachHang.setPreferredSize(lblTenKhachHang.getPreferredSize());
		lblSoDienThoai.setPreferredSize(lblTenKhachHang.getPreferredSize());
		lblGhiChu.setPreferredSize(lblTenKhachHang.getPreferredSize());
		int preferredWidth = 300;
		Dimension preferredSize = new Dimension(preferredWidth, pnlChucNang.getPreferredSize().height);
		pnlChucNang.setPreferredSize(preferredSize);

		// Tìm
		JPanel pnlTim = new JPanel();
		pnlTim.add(lblSoDienThoai = new JLabel("Số điện thoại"));
		pnlTim.add(txtTimSDT = new JTextField(15));
		pnlTim.add(btnTim = new ButtonGradient("Tìm kiếm", img_search));

		// Table
		JPanel pnlTable = new JPanel();
		pnlTable.setBorder(BorderFactory.createTitledBorder(line, "Danh sách khách hàng "));
		table = new JTable();
		tableModel = new DefaultTableModel(headers, 0);
		table.setModel(tableModel);
		table.setRowHeight(25);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setViewportView(table);
		pnlTable.setLayout(new BorderLayout());
		pnlTable.add(pnlTim, BorderLayout.NORTH);
		pnlTable.add(scroll, BorderLayout.CENTER);

		// Set editable
		txtMaKH.setEnabled(false);

		// Add vào giao diện
		setLayout(new BorderLayout());
		add(pnlThongTinKH, BorderLayout.NORTH);
		add(pnlTable, BorderLayout.CENTER);
		pnlTim.setBackground(Color.decode("#D0BAFB"));
		pnlTable.setBackground(Color.decode("#D0BAFB"));
		pnlInput.setBackground(Color.decode("#D0BAFB"));
		pnlChucNang.setBackground(Color.decode("#D0BAFB"));
		pnlThongTinKH.setBackground(Color.decode("#D0BAFB"));
	}


	// Xoa toan bo khach hang

	// Kiem tra rang buoc

	@Override
	public void mousePressed(MouseEvent e) {
		int row = table.getSelectedRow();
		txtMaKH.setText(table.getValueAt(row, 0).toString());
		txtCustomerName.setText(table.getValueAt(row, 1).toString());
		txtPhoneNumber.setText(table.getValueAt(row, 2).toString());
		txaNote.setText(table.getValueAt(row, 3).toString());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
