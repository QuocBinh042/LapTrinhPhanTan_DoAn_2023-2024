package entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Room")
public class Room implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "RoomID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "RoomName", columnDefinition = "nvarchar(250)", nullable = false)
	private String roomName;
	@Column(name = "RoomStatus", columnDefinition = "nvarchar(250)", nullable = false)
	private String roomStatus;
	@Column(name = "Describe", columnDefinition = "nvarchar(250)", nullable = false)
	private String describe;
	@Embedded
	private RoomType roomType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return roomName;
	}

	public void setName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Room(int id, String roomName, String roomStatus, String describe, RoomType roomType) {
		super();
		this.id = id;
		this.roomName = roomName;
		this.roomStatus = roomStatus;
		this.describe = describe;
		this.roomType = roomType;
	}

	public Room(int id, String roomName, String roomStatus, String describe) {
		super();
		this.id = id;
		this.roomName = roomName;
		this.roomStatus = roomStatus;
		this.describe = describe;
	}

	public Room() {
		super();
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomName=" + roomName + ", roomStatus=" + roomStatus + ", describe=" + describe
				+ ", roomType=" + roomType + "]";
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
		Room other = (Room) obj;
		return id == other.id;
	}
}
