package entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Service")
public class Service implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7382314846166790216L;	

	@Id
	@Column(name = "ServiceID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "ServiceName", columnDefinition = "Nvarchar(250)", nullable = false)
	private String name;
	@Column(name = "Price", nullable = false)
	private double price;
	@Column(name = "Unit", columnDefinition = "Nvarchar(50)", nullable = false)
	private String unit;
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
		Service other = (Service) obj;
		return id == other.id;
	}
	@Column(name = "Quantity", nullable = false)
	private int inventoryNumber;
	@Column(name = "Status", columnDefinition = "Nvarchar(250)", nullable = false)
	private String status;
	
	@OneToMany(mappedBy = "service")
	Set<DetailServiceRoom> detailServiceRooms;
	
	public Service(int id, String name, double price, String unit, int inventoryNumber, String status) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.inventoryNumber = inventoryNumber;
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
	public int getInventoryNumber() {
		return inventoryNumber;
	}
	public void setInventoryNumber(int inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", price=" + price + ", unit=" + unit + ", quantity=" + inventoryNumber
				+ ", status=" + status + "]";
	}
}
