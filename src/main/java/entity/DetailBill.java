package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DetailBill")
public class DetailBill implements Serializable {
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "RoomID", referencedColumnName = "RoomID")
	private Room room;
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BillID", referencedColumnName = "BillID")
	private Bill bill;
	@Column(name = "CheckinDate", nullable = false)
	private LocalDate checkinDate;
	@Column(name = "CheckoutDate", nullable = false)
	private LocalDate checkoutDate;
	public DetailBill(Room room, Bill bill, LocalDate checkinDate, LocalDate checkoutDate) {
		super();
		this.room = room;
		this.bill = bill;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
	}
	public DetailBill() {
		super();
	}
	@Override
	public String toString() {
		return "DetailBill [room=" + room + ", bill=" + bill + ", checkinDate=" + checkinDate + ", checkoutDate="
				+ checkoutDate + "]";
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
