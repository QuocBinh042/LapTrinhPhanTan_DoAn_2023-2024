package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "EmployeeID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "EmployeeName", columnDefinition = "nvarchar(250)", nullable = false)
	private String name;
	@Column(name = "BirthDate", nullable = false)
	private LocalDate birth;
	@Column(name = "Gender", nullable = false)
	private boolean gender;
	@Column(name = "PhoneNumer", columnDefinition = "varchar(10)", nullable = false)
	private String phoneNumber;
	@Column(name = "CI", columnDefinition = "varchar(15)", nullable = false)
	private String CI; //citizen identification
	@Column(name = "Position", columnDefinition = "Nvarchar(50)", nullable = false)
	private String position;
	@Column(name = "Password", columnDefinition = "Nvarchar(50)", nullable = false)
	private String password;
	@Column(name = "EmployeeStatus")
	private boolean employeeStatus;
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
	public LocalDate getBirth() {
		return birth;
	}
	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCI() {
		return CI;
	}
	public void setCI(String cI) {
		CI = cI;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(boolean employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", birth=" + birth + ", gender=" + gender + ", phoneNumber="
				+ phoneNumber + ", CI=" + CI + ", position=" + position + ", password=" + password + ", employeeStatus="
				+ employeeStatus + "]";
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Employee(int id, String name, LocalDate birth, boolean gender, String phoneNumber, String cI,
			String position, String password, boolean employeeStatus) {
		super();
		this.id = id;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		CI = cI;
		this.position = position;
		this.password = password;
		this.employeeStatus = employeeStatus;
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
		Employee other = (Employee) obj;
		return Objects.equals(id, other.id);
	}
}
