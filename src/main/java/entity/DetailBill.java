package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
	@Column(name = "Checkin", nullable = false)
	private LocalDateTime checkin;
	@Column(name = "Checkout", nullable = true)
	private LocalDateTime checkout;
	
	public Double translationRoomPrice() {
		Double money;
		money = room.getRoomType().getPrice() * (calculateTimeUsingRoomByMinute() / 60);
		double moneyExtra = calculateTimeUsingRoomByMinute() % 60;;
		if(calculateTimeUsingRoomByMinute() > 0) {
			if(moneyExtra >= 0 && moneyExtra < 15) {
				money += 0;
			} else if (moneyExtra >= 15 && moneyExtra < 30) {
				money += room.getRoomType().getPrice() * 0.5;
			} else if (moneyExtra >= 30 && moneyExtra < 50) {
				money += room.getRoomType().getPrice() * 0.75;
			} else 
				money += room.getRoomType().getPrice();
		}
		return money;	
	}
	
	public long calculateTimeUsingRoomByMinute() {
		return (checkout.atZone(ZoneId.systemDefault()).toEpochSecond() - checkin.atZone(ZoneId.systemDefault()).toEpochSecond()) / 60;
	}

	public DetailBill(int id, Room room, Bill bill, LocalDateTime checkin, LocalDateTime checkout) {
		super();
		this.id = id;
		this.room = room;
		this.bill = bill;
		this.checkin = checkin;
		this.checkout = checkout;
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
		return "DetailBill [id=" + id + ", room=" + room + ", bill=" + bill + ", checkin=" + checkin + ", checkout="
				+ checkout + "]";
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

	public LocalDateTime getCheckin() {
		return checkin;
	}

	public void setCheckin(LocalDateTime checkin) {
		this.checkin = checkin;
	}

	public LocalDateTime getCheckout() {
		return checkout;
	}

	public void setCheckout(LocalDateTime checkout) {
		this.checkout = checkout;
	}
}
