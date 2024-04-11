package entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Service")
public class Service implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ServiceID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "Name", columnDefinition = "Nvarchar(250)", nullable = false)
	private String name;
	@Column(name = "Price", nullable = false)
	private double price;
	@Column(name = "Unit", columnDefinition = "Nvarchar(50)", nullable = false)
	private String unit;
	@Column(name = "Quantity", nullable = false)
	private int quantity;
	@Column(name = "Status", columnDefinition = "Nvarchar(250)", nullable = false)
	private String status;
	public Service(int id, String name, double price, String unit, int quantity, String status) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.quantity = quantity;
		this.status = status;
	}
	public Service() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", price=" + price + ", unit=" + unit + ", quantity=" + quantity
				+ ", status=" + status + "]";
	}
}
