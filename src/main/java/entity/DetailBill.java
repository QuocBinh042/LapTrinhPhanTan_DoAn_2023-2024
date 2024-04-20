package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DetailBill")
public class DetailBill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 648789103374928020L;
	
	@Id
	@Column(name = "DetailBillID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "RoomID")
	private Room room;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "BillID")
	private Bill bill;
	@Column(name = "CheckinDate", nullable = false)
	private LocalDate checkinDate;
	@Column(name = "CheckoutDate", nullable = true)
	private LocalDate checkoutDate;
	
	public DetailBill(int id, Room room, Bill bill, LocalDate checkinDate, LocalDate checkoutDate) {
		super();
		this.id = id;
		this.room = room;
		this.bill = bill;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
	}
	
	public DetailBill() {
		super();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "DetailBill [id=" + id + ", room=" + room + ", bill=" + bill + ", checkinDate=" + checkinDate
				+ ", checkoutDate=" + checkoutDate + "]";
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public LocalDate getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(LocalDate checkinDate) {
		this.checkinDate = checkinDate;
	}
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
}
