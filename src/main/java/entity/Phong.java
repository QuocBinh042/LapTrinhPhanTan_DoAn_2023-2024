package entity;

import java.io.Serializable;
import java.util.Objects;

public class Phong implements Serializable {
	private String maPhong;
	private String tenPhong;
	private String loaiPhong;
	private String tinhTrangPhong;
	private double sucChua;
	private double donGia;
	private String moTa;

	public Phong() {
		super();
	}

	public Phong(String maPhong, String tenPhong, String loaiPhong, String tinhTrangPhong, double sucChua, double donGia,
			String moTa) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.loaiPhong = loaiPhong;
		this.tinhTrangPhong = tinhTrangPhong;
		this.sucChua = sucChua;
		this.donGia = donGia;
		this.moTa = moTa;
	}

	public String getMaPhong() {
		return maPhong;
	}

	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}

	public String getTenPhong() {
		return tenPhong;
	}

	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}

	public String getLoaiPhong() {
		return loaiPhong;
	}

	public void setLoaiPhong(String loaiPhong) {
		this.loaiPhong = loaiPhong;
	}

	public String getTinhTrangPhong() {
		return tinhTrangPhong;
	}

	public void setTinhTrangPhong(String tinhTrangPhong) {
		this.tinhTrangPhong = tinhTrangPhong;
	}

	public double getSucChua() {
		return sucChua;
	}

	public void setSucChua(double sucChua) {
		this.sucChua = sucChua;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPhong);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phong other = (Phong) obj;
		return Objects.equals(maPhong, other.maPhong);
	}

	@Override
	public String toString() {
		return "Phong [maPhong=" + maPhong + ", tenPhong=" + tenPhong + ", loaiPhong=" + loaiPhong + ", tinhTrangPhong="
				+ tinhTrangPhong + ", sucChua=" + sucChua + ", donGia=" + donGia + ", moTa=" + moTa + "]";
	}

}
