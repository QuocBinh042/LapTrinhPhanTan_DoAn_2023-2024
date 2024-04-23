package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Bill")
@NamedQueries(@NamedQuery(name = "getAllBills", query = "select b from Bill b where b.paymentDate is not null and b.paymentTime is not null"))
public class Bill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8585969201104158952L;
	@Id
	@Column(name = "BillID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "PaymentDate", nullable = true)
	private LocalDate paymentDate;
	@Column(name = "PaymentTime", nullable = true)
	private LocalTime paymentTime;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "EmployeeID", referencedColumnName = "EmployeeID")
	private Employee employee;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
	private Customer customer;
	@Column(name = "Total", nullable = true)
	private Double total;
	
	@OneToMany(mappedBy = "bill")
	Set<DetailServiceRoom> detailServiceRooms;
	
	@OneToMany(mappedBy = "bill")
	Set<DetailBill> detailBills;

	public Bill(int id, LocalDate paymentDate, LocalTime paymentTime, Employee employee, Customer customer) {
		super();
		this.id = id;
		this.paymentDate = paymentDate;
		this.paymentTime = paymentTime;
		this.employee = employee;
		this.customer = customer;
	}

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

	public void setTotal() {
		this.total = totalMoneyRoom() + totalMoneyService();
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", paymentDate=" + paymentDate + ", paymentTime=" + paymentTime + ", employee="
				+ employee + ", customer=" + customer + ", total=" + total + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bill other = (Bill) obj;
		return id == other.id;
	}

	public Set<DetailServiceRoom> getDetailServiceRooms() {
		return detailServiceRooms;
	}

	public void setDetailServiceRooms(Set<DetailServiceRoom> detailServiceRooms) {
		this.detailServiceRooms = detailServiceRooms;
	}

	public Set<DetailBill> getDetailBills() {
		return detailBills;
	}

	public void setDetailBills(Set<DetailBill> detailBills) {
		this.detailBills = detailBills;
	}
	
	public double totalMoneyRoom() {
		return detailBills.stream().mapToDouble(d -> d.translationRoomPrice()).sum();
	}
	
	public double totalMoneyService() {
		return detailServiceRooms.stream().mapToDouble(d -> d.calculateMoneyService()).sum();
	}
}
