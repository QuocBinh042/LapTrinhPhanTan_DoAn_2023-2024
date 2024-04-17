package entity;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Bill")
@NamedQueries(@NamedQuery(name = "getAllBills", query = "select b from Bill b"))
public class Bill implements Serializable{
	@Id
	@Column(name = "BillID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "PaymentDate", nullable = false)
	private LocalDate paymentDate;
	@Column(name = "PaymentTime", nullable = false)
	private LocalTime paymentTime;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
	private Employee employee;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
	private Customer customer;
	@Column(name = "Total", nullable = false)
	private Double total;
	
	private transient ArrayList<DetailBill> CTHD;
	private transient ArrayList<DetailServiceRoom> CTDVP;
	
	public Bill(int id, LocalDate paymentDate, LocalTime paymentTime, Employee employee, Customer customer,
			Double total) {
		super();
		this.id = id;
		this.paymentDate = paymentDate;
		this.paymentTime = paymentTime;
		this.employee = employee;
		this.customer = customer;
		this.total = total;
	}
	public Bill(int id, LocalDate paymentDate, LocalTime paymentTime, Employee employee, Customer customer,
			Double total, ArrayList<DetailBill> cTHD, ArrayList<DetailServiceRoom> cTDVP) {
		super();
		this.id = id;
		this.paymentDate = paymentDate;
		this.paymentTime = paymentTime;
		this.employee = employee;
		this.customer = customer;
		this.total = total;
		CTHD = cTHD;
		CTDVP = cTDVP;
	}
	public Bill() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}
	public LocalTime getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(LocalTime paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "Bill [id=" + id + ", paymentDate=" + paymentDate + ", paymentTime=" + paymentTime + ", employee="
				+ employee + ", customer=" + customer + ", total=" + total + "]";
	}
}
