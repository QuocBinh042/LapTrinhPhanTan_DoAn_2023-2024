package entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RoomType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1348974394433574892L;
	@Column(name = "TypeRoom", columnDefinition = "nvarchar(250)", nullable = false)
	private String typeRoom;
	@Column(name = "Capacity", nullable = false)
	private int capacity;
	@Column(name = "Price", nullable = false)
	private double price;
	@Override
	public String toString() {
		return "RoomType [typeRoom=" + typeRoom + ", capacity=" + capacity + ", price=" + price + "]";
	}
	public RoomType(String typeRoom, int capacity, double price) {
		super();
		this.typeRoom = typeRoom;
		this.capacity = capacity;
		this.price = price;
	}
	public RoomType() {
		super();
	}
	public String getTypeRoom() {
		return typeRoom;
	}
	public void setTypeRoom(String typeRoom) {
		this.typeRoom = typeRoom;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
