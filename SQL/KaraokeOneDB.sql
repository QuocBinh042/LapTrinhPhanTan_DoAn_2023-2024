--Tạo cơ sở dữ liệu
drop database KaraokeOneDB
go
create database KaraokeOneDB
go
use KaraokeOneDB
go
--------------Create Table

create table Phong(
	MaPhong nvarchar(20) not null primary key,
	TenPhong nvarchar(50) not null,
	LoaiPhong nvarchar(20) not null,
	SucChua float CHECK (SucChua > 0),
    GiaPhong float CHECK (GiaPhong > 0),	
	TinhTrangPhong nvarchar(20) not null, --TinhTrangPhong(con trong,dang thue,da dat truoc,da xoa)
	MoTa nvarchar(100),
)
create table DichVu(
	MaDV nvarchar(20) not null primary key,
	TenDV nvarchar(50) not null,
	DonGia float not null CHECK (DonGia>0),
	DonVi nvarchar(20),
	SoLuongTonKho int,	
	TinhTrangDV nvarchar(20) not null --TinhTrangDV (Co 4 loai: Con hang(SL>10), sap het hang(10>SL>0), het hang(SL=0), da xoa(SL=0))
)
go
create table KhachHang(
	MaKH nvarchar(20) not null primary key,
	TenKH nvarchar(50) not null,	
	LoaiKH bit, --LoaiKhachHang(0: thuong, 1: vip)s	
	GioiTinh bit, --GioiTinh(0: nam, 1: nu)
	SoDienThoai varchar(10) not null,
	Email nvarchar(50),
	SoGioDaThue int,
	GhiChu nvarchar(50) 
)
go
create table NhanVien(
	MaNV nvarchar(20) not null primary key,
	TenNV  nvarchar(50) not null,
	NamSinh date CHECK (NamSinh < GETDATE()),	
	GioiTinh bit, --GioiTinh (0: nam, 1: nu)
	SoDienThoai nvarchar(10) not null,
	CCCD nvarchar(12) not null,	
	ChucVu nvarchar(20)not null, --ChucVu (nhan vien quan ly, le tan,phuc vu)
	MatKhau nvarchar(30) not null,	
	TinhTrangNV bit --TinhTrangNV(0: nghi viec, 1: dang lam)
)
go
create table PhieuDatPhong(
	MaPDP nvarchar(20) not null primary key,	
	MaNV nvarchar(20) not null,
	MaPhong nvarchar(20) not null,
	ThoiGianDatPhong datetime,
	ThoiGianNhanPhong datetime,
	SoLuongKhach int CHECK (SoLuongKhach>0),	
	TinhTrangPDP bit, --TinhTrangPDP(0: chua thanh toan, 1: da thanh toan)
	GhiChu nvarchar(50),
	CONSTRAINT fk_phong FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong),
	CONSTRAINT fk_nv FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
)
go

create table HoaDon(
	MaHD nvarchar(20) not null primary key,
	GioThanhToan time,
	NgayThanhToan date,
	MaNV nvarchar(20) not null,
	MaKH nvarchar(20) not null,
	TongHoaDon float
	CONSTRAINT fk_n_vien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
	CONSTRAINT fk_k_hang FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH)
)
go
create table CTDVPhong(
	MaHD nvarchar(20) not null, 
	MaDV nvarchar(20) not null,
	SoLuong int CHECK (SoLuong>0),
	CONSTRAINT fk_hd FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
	CONSTRAINT fk_dv FOREIGN KEY (MaDV) REFERENCES DichVu(MaDV),
	PRIMARY KEY (MaHD,MaDV)
)
go
create table ChiTietHoaDon(
	MaPhong nvarchar(20) not null,
	MaHD nvarchar(20) not null,
	ThoiGianNhanPhong datetime,
	ThoiGianTraPhong datetime,
	CONSTRAINT fk_maP FOREIGN KEY (MaPhong) REFERENCES Phong(MaPhong),
	CONSTRAINT fk_mahd FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
	--CONSTRAINT CK_ThoiGianTraPhong CHECK (ThoiGianTraPhong > ThoiGianNhanPhong),
	PRIMARY KEY (MaPhong,MaHD)
)
go

-----------Insert data
----------------------------PHONG---------------------------------------------------
--INSERT INTO Phong(MaPhong, TenPhong, LoaiPhong, SucChua, GiaPhong, TinhTrangPhong, MoTa)
--TinhTrangPhong(0: con trong, 1: dang thue, 2: da dat truoc, 3: da xoa)
INSERT INTO Phong values(N'P0001', N'101', N'THƯỜNG', 15, N'Đã đặt trước',N'')
INSERT INTO Phong values(N'P0002', N'102', N'THƯỜNG', 15, N'Đã đặt trước',N'')
INSERT INTO Phong values(N'P0003', N'103', N'THƯỜNG', 15, N'Còn trống',N'')
INSERT INTO Phong values(N'P0004', N'104', N'THƯỜNG', 15, N'Đã xóa',N'')
INSERT INTO Phong values(N'P0005', N'105', N'VIP', 25, N'Còn trống',N'')

INSERT INTO Phong values(N'P0006', N'201', N'VIP', 25, N'Còn trống',N'')
INSERT INTO Phong values(N'P0007', N'202', N'THƯỜNG', 15, N'Đang thuê',N'')
INSERT INTO Phong values(N'P0008', N'203', N'THƯỜNG', 15, N'Đang thuê',N'')
INSERT INTO Phong values(N'P0009', N'204', N'THƯỜNG', 15, N'Đã xóa',N'')
INSERT INTO Phong values(N'P0010', N'205', N'VIP', 25, N'Đang thuê',N'')

INSERT INTO Phong values(N'P0011', N'301', N'VIP', 25, N'Đã đặt trước',N'')
INSERT INTO Phong values(N'P0012', N'302', N'THƯỜNG', 15, N'Đã đặt trước',N'')
INSERT INTO Phong values(N'P0013', N'303', N'THƯỜNG', 15, N'Đã xóa',N'')
INSERT INTO Phong values(N'P0014', N'304', N'THƯỜNG', 15, N'Đang thuê',N'')
INSERT INTO Phong values(N'P0015', N'305', N'VIP', 25, N'Đang thuê',N'')

INSERT INTO Phong values(N'P0016', N'401', N'VIP', 25, N'Đã xóa',N'')
INSERT INTO Phong values(N'P0017', N'402', N'VIP', 25, N'Đã đặt trước',N'')
INSERT INTO Phong values(N'P0018', N'403', N'VIP', 25, N'Còn trống',N'')
INSERT INTO Phong values(N'P0019', N'404', N'VIP', 25, N'Đang thuê',N'')
INSERT INTO Phong values(N'P0020', N'405', N'VIP', 25, N'Còn trống',N'')


--------------------------------DICH VU-----------------------------------------------
--INSERT INTO DichVu(MaDV, TenDV, DonGia, DonVi, SoLuongTonKho, TinhTrangDV) 
--TinhTrangDV (Co 4 loai: Con hang(SL>10), sap het hang(10>SL>0), het hang(SL=0), da xoa(SL=0))
INSERT INTO DichVu values(N'DV0001', N'Khăn giấy', 5000, N'Gói', 200, N'Còn hàng')
INSERT INTO DichVu values(N'DV0002', N'Bia Heineken 330ml', 26000, N'Lon', 100, N'Còn hàng')
INSERT INTO DichVu values(N'DV0003', N'Coca cola', 15000, N'Lon', 7, N'Sắp hết hàng')
INSERT INTO DichVu values(N'DV0004', N'Trái cây dĩa',  70000, N'Dĩa', 0, N'Hết hàng')
INSERT INTO DichVu values(N'DV0005', N'Khăn giấy ướt', 8000, N'Gói', 100, N'Còn hàng')
INSERT INTO DichVu values(N'DV0006', N'Cocktail',  50000, N'Lon', 50, N'Còn hàng')
INSERT INTO DichVu values(N'DV0007', N'Cà phê sữa đá',  18000, N'Ly', 70, N'Còn hàng')
INSERT INTO DichVu values(N'DV0008', N'Đậu phộng',  20000, N'Gói', 5, N'Sắp hết hàng')
INSERT INTO DichVu values(N'DV0009', N'Bia Tiger 500ml ', 35000, N'Lon', 100, N'Còn hàng')
INSERT INTO DichVu values(N'DV0010', N'Cơm chiên hải sản',  40000, N'Dĩa', 3, N'Sắp hết hàng')
INSERT INTO DichVu values(N'DV0011', N'Sụn gà rang muối',  40000, N'Dĩa', 0, N'Đã xóa')
INSERT INTO DichVu values(N'DV0012', N'Nước suối Aquafina 500ml',  7000, N'Chai', 150, N'Còn hàng')
INSERT INTO DichVu values(N'DV0013', N'Mì xào hải sản',  40000, N'Dĩa', 8, N'Sắp hết hàng')
INSERT INTO DichVu values(N'DV0014', N'Thùng 24 lon bia Hà Nội 330ml',  315000, N'Thùng', 30, N'Còn hàng')
INSERT INTO DichVu values(N'DV0015', N'Đậu hũ xóc tỏi ớt',  35000, N'Dĩa', 3, N'Sắp hết hàng')
INSERT INTO DichVu values(N'DV0016', N'Bia Sài Gòn Chill 330ml dạng lốc 6 lon',  130000, N'Lốc', 0, N'Hết hàng')
INSERT INTO DichVu values(N'DV0017', N'Xúc xích nướng tiêu',  45000, N'Dĩa', 100, N'Còn hàng')
INSERT INTO DichVu values(N'DV0018', N'Bia Huda 330ml',  15000, N'Lon', 0, N'Đã xóa')
INSERT INTO DichVu values(N'DV0019', N'Chả giò',  40000, N'Dĩa', 20, N'Còn hàng')
INSERT INTO DichVu values(N'DV0020', N'6 lon bia Heineken Silver 330ml', 150000, N'Lốc', 40, N'Còn hàng')

--------------------------------NHAN VIEN-----------------------------------------------
--INSERT INTO NhanVien(MaNV, TenNV, NamSinh, GioiTinh, SoDienThoai, CCCD, ChucVu, MatKhau, TinhTrangNV)
--ChucVu (0: nhan vien quan ly, 1: le tan, 2: phuc vu)
INSERT INTO NhanVien values(N'NV0001', N'Nguyễn Thị Yến Nhi', '2003/10/05', 1, N'0386076296', N'082594657912', N'Lễ tân', N'123456789', 1)
INSERT INTO NhanVien values(N'NV0002', N'Nguyễn Trung Kiên', '1999/07/21', 0, N'0205578931', N'064769123700', N'Nhân viên quản lý', N'123456789', 1)
INSERT INTO NhanVien values(N'NV0003', N'Vũ Đức Thắng', '2004/02/15', 0, N'0702447601', N'076591236064', N'Nhân viên quản lý', N'thang54t', 1)
INSERT INTO NhanVien values(N'NV0004', N'Lâm Thúy Hiền', '2002/05/18', 1, N'0452603567', N'084769431052', N'Phục vụ', N'hienthuy59r', 0)
INSERT INTO NhanVien values(N'NV0005', N'Nguyễn Văn Thanh', '2001/11/05', 0, N'0160567832', N'073976012433', N'Phục vụ', N'vanthanh10', 1)
INSERT INTO NhanVien values(N'NV0006', N'Nguyễn Hữu Nhật', '2000/04/23', 0, N'0870553419', N'087923408723', N'Lễ tân', N'nhathuu@07', 0)
INSERT INTO NhanVien values(N'NV0007', N'Nguyễn Đỗ Gia Hân', '1998/06/01', 1, N'0573960875', N'062769236591', N'Phục vụ', N'giahan1314', 1)
INSERT INTO NhanVien values(N'NV0008', N'Nguyễn Ngọc Anh Thư', '2001/04/29', 1, N'0952566034', N'073658012563', N'Nhân viên quản lý', N'anhthu542', 1)
INSERT INTO NhanVien values(N'NV0009', N'Trần Anh Tuấn', '2003/09/25', 0, N'0651273390', N'088425070237', N'Phục vụ', N'tuananh76@a', 0)
INSERT INTO NhanVien values(N'NV0010', N'Nguyễn Văn Phương', '2000/12/07', 0, N'0305671489', N'084072359038', N'Phục vụ', N'baoky3415', 0)
INSERT INTO NhanVien values(N'NV0011', N'Trần Hoàng Nghĩa', '2001/05/22', 0, N'0582632176', N'076548160533', N'Lễ tân', N'hoangnghia25#', 1)
INSERT INTO NhanVien values(N'NV0012', N'Nguyễn Ngọc Phương Anh', '2000/01/15', 1, N'0154787932', N'072976002137', N'Phục vụ', N'panhhh5932', 0)
INSERT INTO NhanVien values(N'NV0013', N'Trần Thị Thảo Trang', '2002/10/28', 1, N'0748500751', N'086942660432', N'Phục vụ', N'trangtr298', 0)
INSERT INTO NhanVien values(N'NV0014', N'Nguyễn Cao Minh Khôi', '2002/03/09', 0, N'0952633601', N'069432866015', N'Phục vụ', N'minhkhoi3@#', 1)
INSERT INTO NhanVien values(N'NV0015', N'Hà Bảo Ngọc', '2003/09/21', 1, N'0367927602', N'083602658934', N'Phục vụ', N'ngocbao639', 1)
INSERT INTO NhanVien values(N'NV0016', N'Nguyễn Minh Nhựt', '2000/11/06', 0, N'0638477036', N'073965234671', N'Phục vụ', N'nhutminh532', 0)
INSERT INTO NhanVien values(N'NV0017', N'Phạm Quế Trân', '2004/03/25', 1, N'0838614401', N'063634789932', N'Phục vụ', N'quetran406#', 1)
INSERT INTO NhanVien values(N'NV0018', N'Châu Văn Tấn', '1999/07/18', 0, N'0269743064', N'083852704766', N'Phục vụ', N'tantan396@', 0)
INSERT INTO NhanVien values(N'NV0019', N'Huỳnh Chí Phong', '2002/06/10', 0, N'0478280031', N'071603449254', N'Phục vụ', N'chiphongeu4', 0)
INSERT INTO NhanVien values(N'NV0020', N'Trần Thị Quỳnh Như', '1999/04/17', 1, N'0386370342', N'084598701225', N'Phục vụ', N'nhuquynhh45', 1)


--------------------------------KHACH HANG-----------------------------------------------
--INSERT INTO KhachHang(MaKH, TenKH, LoaiKH, GioiTinh, SoDienThoai, Email, SoGioDaThue, GhiChu)
INSERT INTO KhachHang values(N'KH0001', N'Nguyễn Bùi Phát Đạt', 1, 0, '0649248221', N'phatdat123@gmail.com', 50, '')
INSERT INTO KhachHang values(N'KH0002', N'Lê Thị Thảo Vy', 1, 1, '0492568801', N'vyvy444@gmail.com', 50, '')
INSERT INTO KhachHang values(N'KH0003', N'Trần Mạnh Công', 0, 0, '0581943912', N'congmanh45@gmail.com', 19, '')
INSERT INTO KhachHang values(N'KH0004', N'Dương Đình Toàn', 0, 0, '0194369932', N'dinhtoan17@gmail.com', 12, '')
INSERT INTO KhachHang values(N'KH0005', N'Nguyễn Thu Sương', 0, 1, '0395354914', N'suongthu00@gmail.com', 23, '')
INSERT INTO KhachHang values(N'KH0006', N'Nguyễn Thị Anh Thư', 1, 1, '0825164023', N'thuthu032@gmail.com', 54, '')
INSERT INTO KhachHang values(N'KH0007', N'Nguyễn Toàn Vương', 0, 0, '0292165032', N'manhvuong@gmail.com', 25, '')
INSERT INTO KhachHang values(N'KH0008', N'Tống Nguyên Kiệt', 0, 0, '0938654812', N'kiettongoo@gmail.com', 41, '')
INSERT INTO KhachHang values(N'KH0009', N'Vũ Anh Thư', 0, 1, '0159264902', N'anhthu201@gmail.com', 10, '')
INSERT INTO KhachHang values(N'KH0010', N'Nguyễn Thanh Thanh', 1, 0, '0730118701', N'thanhthanh@gmail.com', 60, '')
INSERT INTO KhachHang values(N'KH0011', N'Nguyễn Thị Bích Loan', 1, 1, '0193276520', N'bichloan3@gmail.com', 50, '')
INSERT INTO KhachHang values(N'KH0012', N'Vũ Minh Huy', 0, 0, '0569370341', N'huyhuy3498@gmail.com', 3, '')
INSERT INTO KhachHang values(N'KH0013', N'Huỳnh Thanh Lộc', 0, 0, '0340554068', N'locthanh37@gmail.com', 11, '')
INSERT INTO KhachHang values(N'KH0014', N'Lê Thị Yến Nhi', 0, 1, '0297450377', N'yennhi12@gmail.com', 41, '')
INSERT INTO KhachHang values(N'KH0015', N'Nguyễn Thành Luân', 0, 0, '0409339601', N'luann65@gmail.com', 16, '')
INSERT INTO KhachHang values(N'KH0016', N'Nguyễn Phương Khánh', 0, 0, '0692270465', N'phuongk333@gmail.com', 12, '')
INSERT INTO KhachHang values(N'KH0017', N'Lê Phạm Công Khanh', 0, 0, '0592669130', N'khanhktt@gmail.com', 13, '')
INSERT INTO KhachHang values(N'KH0018', N'Nguyễn Minh Nguyệt', 0, 1, '0856976509', N'minhn583@gmail.com', 15, '')
INSERT INTO KhachHang values(N'KH0019', N'Nguyễn Ngọc Kim Liên', 0, 1, '0927550871', N'kimlien91@gmail.com', 14, '')
INSERT INTO KhachHang values(N'KH0020', N'Đặng Hoàng Phúc', 0, 0, '0749205481', N'phucphuc32@gmail.com', 12, '')


----------------------------------PHIEU DAT PHONG-----------------------------------------------
----INSERT INTO PhieuDatPhong(MaPDP, MaNV, MaKH, MaPhong,ThoiGianDatPhong, ThoiGianNhanPhong, SoLuongKhach, TinhTrangPDP, MoTa)
INSERT INTO PhieuDatPhong VALUES (N'PDP0001', N'NV0001', N'KH0001', N'P0001', '2023-09-01 10:00:00', '2023-09-01 12:00:00', 3, 0, N'Business meeting')
INSERT INTO PhieuDatPhong VALUES (N'PDP0002', N'NV0002', N'KH0002', N'P0002', '2023-09-02 14:00:00', '2023-09-03 12:00:00', 2, 0, N'Family vacation')
INSERT INTO PhieuDatPhong VALUES (N'PDP0003', N'NV0003', N'KH0003', N'P0003', '2023-10-03 16:30:00', '2023-10-03 20:30:00', 5, 0, N'Wedding party')

INSERT INTO PhieuDatPhong VALUES (N'PDP0004', N'NV0004', N'KH0004', N'P0004', '2023-10-04 18:00:00', '2023-10-05 12:00:00', 2, 0, N'Weekend getaway')
INSERT INTO PhieuDatPhong VALUES (N'PDP0005', N'NV0005', N'KH0005', N'P0005', '2023-10-05 09:00:00', '2023-10-05 17:00:00', 10, 0, N'')
INSERT INTO PhieuDatPhong VALUES (N'PDP0006', N'NV0006', N'KH0006', N'P0006', '2023-10-06 20:00:00', '2023-10-07 10:00:00', 15, 0, N'Birthday party')
INSERT INTO PhieuDatPhong VALUES (N'PDP0007', N'NV0007', N'KH0007', N'P0007', '2023-10-07 10:00:00', '2023-10-07 12:00:00', 5, 0, N'Business meeting')

INSERT INTO PhieuDatPhong VALUES (N'PDP0008', N'NV0008', N'KH0008', N'P0008', '2023-11-01 14:00:00', '2023-11-02 12:00:00', 4, 0, N'Family vacation')
INSERT INTO PhieuDatPhong VALUES (N'PDP0009', N'NV0009', N'KH0009', N'P0009', '2023-11-02 08:00:00', '2023-11-02 18:00:00', 15, 0, N'Học sinh')
INSERT INTO PhieuDatPhong VALUES (N'PDP0010', N'NV0002', N'KH0001', N'P0001', '2023-11-02 15:00', '2023-11-03 12:00', 2, 1, N'');
INSERT INTO PhieuDatPhong VALUES (N'PDP0011', N'NV0011', N'KH0012', N'P0002', '2023-11-04 11:00', '2023-11-04 20:00', 1, 1, N'Nghỉ ngơi và thư giãn');

INSERT INTO PhieuDatPhong VALUES (N'PDP0012', N'NV0002', N'KH0017', N'P0011', '2023-11-10 08:00', '2023-11-10 18:00', 5, 1, N'Họp công ty');
INSERT INTO PhieuDatPhong VALUES (N'PDP0013', N'NV0005', N'KH0012', N'P0012', '2023-11-10 14:00', '2023-11-12 10:00', 3, 1, N'Tiệc gia đình');
INSERT INTO PhieuDatPhong VALUES (N'PDP0014', N'NV0010', N'KH0015', N'P0017', '2023-11-15 09:00', '2023-11-16 16:00', 4, 1, N'Sinh viên');
INSERT INTO PhieuDatPhong VALUES (N'PDP0015', N'NV0008', N'KH0010', N'P0021', '2023-11-20 12:00', '2023-11-21 10:00', 2, 1, N'Tiệc gia đình');

-----------------------------------HOA DON-----------------------------------
----INSERT INTO HoaDon(MaHD, GioThanhToan, NgayThanhToan, MaNV, MaKH, TongHoaDon, TinhTrang)
INSERT INTO HoaDon VALUES (N'HD0001', '15:30:00', '2023-09-01', N'NV0001', N'KH0001', 500000)
INSERT INTO HoaDon VALUES (N'HD0002', '15:45:00', '2023-09-02', N'NV0002', N'KH0002', 1500000)
INSERT INTO HoaDon VALUES (N'HD0003', '21:30:00', '2023-10-03', N'NV0003', N'KH0003', 3000000)

INSERT INTO HoaDon VALUES (N'HD0004', '13:30:00', '2023-10-04', N'NV0006', N'KH0004', 800000)
INSERT INTO HoaDon VALUES (N'HD0005', '13:30:00', '2023-10-05', N'NV0001', N'KH0005', 1200000)
INSERT INTO HoaDon VALUES (N'HD0006', '15:30:00', '2023-10-06', N'NV0006', N'KH0006', 5000000)
INSERT INTO HoaDon VALUES (N'HD0007', '13:30:00', '2023-10-07', N'NV0002', N'KH0007', 500000)

INSERT INTO HoaDon VALUES (N'HD0008', '13:30:00', '2023-11-01', N'NV0003', N'KH0008', 3000000)
INSERT INTO HoaDon VALUES (N'HD0009', '19:30:00', '2023-11-02', N'NV0006', N'KH0009',1500000)
INSERT INTO HoaDon VALUES (N'HD0010', '16:00', '2023-11-02', N'NV0002', N'KH0001', 0);
INSERT INTO HoaDon VALUES (N'HD0011', '21:00', '2023-11-04', N'NV0003', N'KH0012', 0);

INSERT INTO HoaDon VALUES (N'HD0012', '19:30', '2023-11-10', N'NV0006', N'KH0017', 0);
INSERT INTO HoaDon VALUES (N'HD0013', '15:00', '2023-11-12', N'NV0001', N'KH0012', 0);
INSERT INTO HoaDon VALUES (N'HD0014', '18:30', '2023-11-16', N'NV0002', N'KH0015', 0);
INSERT INTO HoaDon VALUES (N'HD0015', '12:30', '2023-11-21', N'NV0003', N'KH0010', 0);

-----------------------------------CHI TIET HOA DON-----------------------------------
----INSERT INTO ChiTietHoaDon(MaPhong, MaHD, ThoiGianNhanPhong, ThoiGianTraPhong)
INSERT INTO ChiTietHoaDon VALUES (N'P0001', N'HD0001', '2022-09-01 12:00:00', '2022-09-01 15:30:00')
INSERT INTO ChiTietHoaDon VALUES (N'P0002', N'HD0002', '2023-09-03 12:00:00', '2023-09-02 15:45:00')
INSERT INTO ChiTietHoaDon VALUES (N'P0003', N'HD0003', '2023-10-03 20:30:00', '2023-10-03 21:30:00')

INSERT INTO ChiTietHoaDon VALUES (N'P0004', N'HD0004', '2023-10-05 12:00:00', '2023-10-04 13:30:00')
INSERT INTO ChiTietHoaDon VALUES (N'P0005', N'HD0005', '2023-10-05 17:00:00', '2023-10-05 13:30:00')
INSERT INTO ChiTietHoaDon VALUES (N'P0006', N'HD0006', '2023-10-07 10:00:00', '2023-10-06 15:30:00')
INSERT INTO ChiTietHoaDon VALUES (N'P0007', N'HD0007', '2023-10-07 12:00:00', '2023-10-07 13:30:00')

INSERT INTO ChiTietHoaDon VALUES (N'P0008', N'HD0008', '2023-11-02 12:00:00', '2023-11-01 13:30:00')
INSERT INTO ChiTietHoaDon VALUES (N'P0009', N'HD0009', '2023-11-02 18:00:00', '2023-11-02 19:30:00')
INSERT INTO ChiTietHoaDon VALUES (N'P0001', N'HD0010', '2023-11-03 12:00', '2023-11-02 16:00');
INSERT INTO ChiTietHoaDon VALUES (N'P0002', N'HD0011', '2023-11-04 20:00', '2023-11-04 21:00');

INSERT INTO ChiTietHoaDon VALUES (N'P0011', N'HD0012', '2023-11-10 18:00', '2023-11-10 19:30');
INSERT INTO ChiTietHoaDon VALUES (N'P0012', N'HD0013', '2023-11-12 10:00', '2023-11-12 15:00');
INSERT INTO ChiTietHoaDon VALUES (N'P0017', N'HD0014', '2023-11-16 16:00', '2023-11-16 18:30');
INSERT INTO ChiTietHoaDon VALUES (N'P0021', N'HD0015', '2023-11-21 10:00', '2023-11-21 12:30');

-----------------------------------CTDV PHONG-----------------------------------
----INSERT INTO CTDVPhong(MaHD, MaDV, SoLuong)
INSERT INTO CTDVPhong VALUES (N'HD0001', N'DV0001', 2)
INSERT INTO CTDVPhong VALUES (N'HD0001', N'DV0002', 1)

INSERT INTO CTDVPhong VALUES (N'HD0002', N'DV0003', 1)
INSERT INTO CTDVPhong VALUES (N'HD0002', N'DV0004', 1)

INSERT INTO CTDVPhong VALUES (N'HD0003', N'DV0005', 5)
INSERT INTO CTDVPhong VALUES (N'HD0003', N'DV0006', 2)

INSERT INTO CTDVPhong VALUES (N'HD0004', N'DV0001', 1)
INSERT INTO CTDVPhong VALUES (N'HD0004', N'DV0003', 2)

INSERT INTO CTDVPhong VALUES (N'HD0005', N'DV0002', 10)
INSERT INTO CTDVPhong VALUES (N'HD0005', N'DV0004', 1)

INSERT INTO CTDVPhong VALUES (N'HD0006', N'DV0005', 15)
INSERT INTO CTDVPhong VALUES (N'HD0006', N'DV0006', 5)

INSERT INTO CTDVPhong VALUES (N'HD0007', N'DV0002', 5)
INSERT INTO CTDVPhong VALUES (N'HD0007', N'DV0004', 1)

INSERT INTO CTDVPhong VALUES (N'HD0008', N'DV0001', 4)
INSERT INTO CTDVPhong VALUES (N'HD0008', N'DV0003', 1)

INSERT INTO CTDVPhong VALUES (N'HD0009', N'DV0002', 15)
INSERT INTO CTDVPhong VALUES (N'HD0009', N'DV0005', 1)


--Xem toan bo Phong
select *from Phong
--Xem toan bo KhachHang
select *from KhachHang
--Xem toan bo NhanVien
select *from NhanVien
