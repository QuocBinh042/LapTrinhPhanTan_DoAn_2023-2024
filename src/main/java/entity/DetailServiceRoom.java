package entity;

import java.io.Serializable;
import java.util.Objects;

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
@Table(name = "DetailServiceRoom")
public class DetailServiceRoom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2474794631979949052L;

	@Id
	@Column(name = "DetailServiceBillID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BillID", referencedColumnName = "BillID")
	private Bill bill;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ServiceID", referencedColumnName = "ServiceID")
	private Service service;
	@Column(name = "Quantity", nullable = false)
	private int quantity;
	public DetailServiceRoom() {
		super();
	}
	public DetailServiceRoom(int id, Bill bill, Service service, int quantity) {
		super();
		this.id = id;
		this.bill = bill;
		this.service = service;
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "DetailServiceRoom [id=" + id + ", bill=" + bill + ", service=" + service + ", quantity=" + quantity
				+ "]";
	}
	public Bill getBill() {
		return bill;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Double calculateMoneyService() {
		return service.getPrice() * quantity;
	}
}
