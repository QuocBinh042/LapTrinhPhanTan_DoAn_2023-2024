package entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "DetailServiceBill")
public class DetailServiceRoom implements Serializable{
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BillID", referencedColumnName = "BillID")
	private Bill bill;
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ServiceID", referencedColumnName = "ServiceID")
	private Service service;
	@Column(name = "Quantity", nullable = false)
	private int quantity;
	public DetailServiceRoom(Bill bill, Service service, int quantity) {
		super();
		this.bill = bill;
		this.service = service;
		this.quantity = quantity;
	}
	public DetailServiceRoom() {
		super();
	}
	@Override
	public String toString() {
		return "DetailServiceRoom [bill=" + bill + ", service=" + service + ", quantity=" + quantity + "]";
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
}
