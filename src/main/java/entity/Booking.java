package entity;

import java.util.Date;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import org.hibernate.annotations.ManyToAny;

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

@Entity()
@Table(name = "Booking")
public class Booking implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2653333015931766654L;
	@Id
	@Column(name = "BookingID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "BookingDateTime", nullable = false)
	private LocalDateTime bookingDateTime;
	@Column(name = "BookingStatus", nullable = false)
	private int bookingStatus;
	@Column(name = "Describe", columnDefinition = "Nvarchar(250)", nullable = true)
	private String describe;
	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "RoomID", referencedColumnName = "RoomID")
	private Room room;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
	private Customer customer;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
	private Employee employee;
	
	public Booking() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(int bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public String toString() {
		return "Booking [id=" + id + ", bookingDateTime=" + bookingDateTime + ", bookingStatus=" + bookingStatus
				+ ", describe=" + describe + ", room=" + room + ", customer=" + customer + ", employee=" + employee
				+ "]";
	}
	public LocalDateTime getBookingDateTime() {
		return bookingDateTime;
	}
	public void setBookingDateTime(LocalDateTime bookingDateTime) {
		this.bookingDateTime = bookingDateTime;
	}
	public Booking(int id, LocalDateTime bookingDateTime, int bookingStatus, String describe, Room room,
			Customer customer, Employee employee) {
		super();
		this.id = id;
		this.bookingDateTime = bookingDateTime;
		this.bookingStatus = bookingStatus;
		this.describe = describe;
		this.room = room;
		this.customer = customer;
		this.employee = employee;
	}
}
