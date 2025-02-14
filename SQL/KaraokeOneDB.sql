﻿--DROP DATABASE KaraokeOneDB
--CREATE DATABASE KaraokeOneDB
USE KaraokeOneDB
go
SET IDENTITY_INSERT [dbo].[Room] ON
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (1, N'101', N'Thường', 15, 150000, N'Đã đặt trước', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (2, N'102', N'Thường', 15, 150000, N'Đã đặt trước', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (3, N'103', N'Thường', 15, 150000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (4, N'104', N'Thường', 15, 150000, N'Đã xóa', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (5, N'105', N'VIP', 25, 250000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (6, N'201', N'VIP', 25, 250000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (7, N'202', N'Thường', 15, 150000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (8, N'203', N'Thường', 15, 150000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (9, N'204', N'Thường', 15, 150000, N'Đã xóa', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (10, N'205', N'VIP', 25, 250000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (11, N'301', N'VIP', 25, 250000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (12, N'302', N'Thường', 15, 150000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (13, N'303', N'Thường', 15, 150000, N'Đã xóa', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (14, N'304', N'Thường', 15, 150000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (15, N'305', N'VIP', 25, 250000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (16, N'401', N'VIP', 25, 250000, N'Đã xóa', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (17, N'402', N'VIP', 25, 250000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (18, N'403', N'VIP', 25, 250000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (19, N'404', N'VIP', 25, 250000, N'Còn trống', N'')
INSERT INTO [dbo].[Room] ([RoomID], [RoomName], [TypeRoom], [Capacity], [Price], [RoomStatus], [Describe])
VALUES (20, N'405', N'VIP', 25, 250000, N'Còn trống', N'')
go
SET IDENTITY_INSERT [dbo].[Room] OFF
go
SET IDENTITY_INSERT [dbo].[Service] ON
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status])
VALUES (1, N'Khăn giấy', 5000, N'Gói', 200, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (2, N'Bia Heineken 330ml', 26000, N'Lon', 100, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (3, N'Coca cola', 15000, N'Lon', 7, N'Sắp hết hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (4, N'Trái cây dĩa',  70000, N'Dĩa', 0, N'Hết hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (5, N'Khăn giấy ướt', 8000, N'Gói', 100, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (6, N'Cocktail',  50000, N'Lon', 50, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (7, N'Cà phê sữa đá',  18000, N'Ly', 70, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (8, N'Đậu phộng',  20000, N'Gói', 5, N'Sắp hết hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (9, N'Bia Tiger 500ml ', 35000, N'Lon', 100, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (10, N'Cơm chiên hải sản',  40000, N'Dĩa', 3, N'Sắp hết hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (11, N'Sụn gà rang muối',  40000, N'Dĩa', 0, N'Đã xóa')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (12, N'Nước suối Aquafina 500ml',  7000, N'Chai', 150, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (13, N'Mì xào hải sản',  40000, N'Dĩa', 8, N'Sắp hết hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (14, N'Thùng 24 lon bia Hà Nội 330ml',  315000, N'Thùng', 30, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (15, N'Đậu hũ xóc tỏi ớt',  35000, N'Dĩa', 3, N'Sắp hết hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (16, N'Bia Sài Gòn Chill 330ml dạng lốc 6 lon',  130000, N'Lốc', 0, N'Hết hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (17, N'Xúc xích nướng tiêu',  45000, N'Dĩa', 100, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (18, N'Bia Huda 330ml',  15000, N'Lon', 0, N'Đã xóa')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (19, N'Chả giò',  40000, N'Dĩa', 20, N'Còn hàng')
INSERT INTO [dbo].[Service] ([ServiceID], [ServiceName], [Price], [Unit], [Quantity], [Status]) 
VALUES (20, N'6 lon bia Heineken Silver 330ml', 150000, N'Lốc', 40, N'Còn hàng')
go
SET IDENTITY_INSERT [dbo].[Service] OFF
go
SET IDENTITY_INSERT [dbo].[Employee] ON
go
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus])
VALUES (1, N'Nguyễn Thị Yến Nhi', '2003/10/05', 1, N'0386076296', N'082594657912', N'Lễ tân', N'123456789', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (2, N'Nguyễn Trung Kiên', '1999/07/21', 0, N'0205578931', N'064769123700', N'Nhân viên quản lý', N'123456789', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (3, N'Vũ Đức Thắng', '2004/02/15', 0, N'0702447601', N'076591236064', N'Nhân viên quản lý', N'thang54t', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (4, N'Lâm Thúy Hiền', '2002/05/18', 1, N'0452603567', N'084769431052', N'Phục vụ', N'hienthuy59r', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (5, N'Nguyễn Văn Thanh', '2001/11/05', 0, N'0160567832', N'073976012433', N'Phục vụ', N'vanthanh10', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (6, N'Nguyễn Hữu Nhật', '2000/04/23', 0, N'0870553419', N'087923408723', N'Lễ tân', N'nhathuu@07', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (7, N'Nguyễn Đỗ Gia Hân', '1998/06/01', 1, N'0573960875', N'062769236591', N'Phục vụ', N'giahan1314', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (8, N'Nguyễn Ngọc Anh Thư', '2001/04/29', 1, N'0952566034', N'073658012563', N'Nhân viên quản lý', N'anhthu542', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (9, N'Trần Anh Tuấn', '2003/09/25', 0, N'0651273390', N'088425070237', N'Phục vụ', N'tuananh76@a', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (10, N'Nguyễn Văn Phương', '2000/12/07', 0, N'0305671489', N'084072359038', N'Phục vụ', N'baoky3415', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (11, N'Trần Hoàng Nghĩa', '2001/05/22', 0, N'0582632176', N'076548160533', N'Lễ tân', N'hoangnghia25#', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (12, N'Nguyễn Ngọc Phương Anh', '2000/01/15', 1, N'0154787932', N'072976002137', N'Phục vụ', N'panhhh5932', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (13, N'Trần Thị Thảo Trang', '2002/10/28', 1, N'0748500751', N'086942660432', N'Phục vụ', N'trangtr298', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (14, N'Nguyễn Cao Minh Khôi', '2002/03/09', 0, N'0952633601', N'069432866015', N'Phục vụ', N'minhkhoi3@#', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (15, N'Hà Bảo Ngọc', '2003/09/21', 1, N'0367927602', N'083602658934', N'Phục vụ', N'ngocbao639', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (16, N'Nguyễn Minh Nhựt', '2000/11/06', 0, N'0638477036', N'073965234671', N'Phục vụ', N'nhutminh532', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (17, N'Phạm Quế Trân', '2004/03/25', 1, N'0838614401', N'063634789932', N'Phục vụ', N'quetran406#', 1)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (18, N'Châu Văn Tấn', '1999/07/18', 0, N'0269743064', N'083852704766', N'Phục vụ', N'tantan396@', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (19, N'Huỳnh Chí Phong', '2002/06/10', 0, N'0478280031', N'071603449254', N'Phục vụ', N'chiphongeu4', 0)
INSERT INTO [dbo].[Employee] ([EmployeeID], [EmployeeName], [BirthDate], [Gender], [PhoneNumer], [CI], [Position], [Password], [EmployeeStatus]) 
VALUES (20, N'Trần Thị Quỳnh Như', '1999/04/17', 1, N'0386370342', N'084598701225', N'Phục vụ', N'nhuquynhh45', 1)
go
SET IDENTITY_INSERT [dbo].[Employee] OFF
go

SET IDENTITY_INSERT [dbo].[Customer] ON
go
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note])
VALUES (1, N'Nguyễn Bùi Phát Đạt', '0649248221', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (2, N'Lê Thị Thảo Vy', '0492568801', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (3, N'Trần Mạnh Công', '0581943912', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (4, N'Dương Đình Toàn', '0194369932', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (5, N'Nguyễn Thu Sương', '0395354914', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (6, N'Nguyễn Thị Anh Thư', '0825164023', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (7, N'Nguyễn Toàn Vương', '0292165032', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (8, N'Tống Nguyên Kiệt', '0938654812', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (9, N'Vũ Anh Thư', '0159264902', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (10, N'Nguyễn Thanh Thanh', '0730118701', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (11, N'Nguyễn Thị Bích Loan', '0193276520', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (12, N'Vũ Minh Huy', '0569370341', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (13, N'Huỳnh Thanh Lộc', '0340554068', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (14, N'Lê Thị Yến Nhi', '0297450377', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (15, N'Nguyễn Thành Luân','0409339601', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (16, N'Nguyễn Phương Khánh', '0692270465','')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (17, N'Lê Phạm Công Khanh','0592669130', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (18, N'Nguyễn Minh Nguyệt', '0856976509', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (19, N'Nguyễn Ngọc Kim Liên', '0927550871', '')
INSERT INTO [dbo].[Customer] ([CustomerID], [CustomerName], [PhoneNumber], [Note]) 
VALUES (20, N'Đặng Hoàng Phúc', '0749205481', '')
go
SET IDENTITY_INSERT [dbo].[Customer] OFF
go

SET IDENTITY_INSERT [dbo].[Booking] ON
go
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (1, 1, 1, 1, '2023-09-01 10:00:00', 0, N'Business meeting')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (2, 2, 2, 2, '2023-09-02 14:00:00',0, N'Family vacation')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (3, 3, 3, 3, '2023-10-03 16:30:00',  0, N'Wedding party')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (4, 4, 4, 4, '2023-10-04 18:00:00',  0, N'Weekend getaway')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (5, 5, 5, 5, '2023-10-05 09:00:00', 0, N'')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (6, 6, 6, 6, '2023-10-06 20:00:00', 0, N'Birthday party')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (7, 7, 7, 7, '2023-10-07 10:00:00', 0, N'Business meeting')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (8, 8, 8, 8, '2023-11-01 14:00:00', 0, N'Family vacation')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (9, 9, 9, 9, '2023-11-02 08:00:00', 0, N'Học sinh')
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (10, 10, 1, 1, '2023-11-02 15:00',1, N'');
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (11, 11, 12, 2, '2023-11-04 11:00', 1, N'Nghỉ ngơi và thư giãn');
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (12, 2, 17, 11, '2023-11-10 08:00', 0, N'Họp công ty');
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (13, 5, 12, 12, '2023-11-10 14:00', 0, N'Tiệc gia đình');
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (14, 10, 15, 17, '2023-11-15 09:00', 0, N'Sinh viên');
INSERT INTO [dbo].[Booking] ([BookingID], [EmployeeID], [CustomerID], [RoomID], [BookingDateTime], [BookingStatus], [Describe])
VALUES  (15, 8, 10,20, '2023-11-20 12:00', 0, N'Tiệc gia đình');
go
SET IDENTITY_INSERT [dbo].[Booking] OFF
go
SET IDENTITY_INSERT [dbo].[Bill] ON
go
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES  (1, '15:30:00', '2023-09-01', 1, 1, 500000)
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES (2, '15:45:00', '2023-09-02', 2, 2, 1500000)
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total]) 
VALUES (3, '21:30:00', '2023-10-03', 3, 3, 3000000)

INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES  (4, '13:30:00', '2023-10-04', 6, 4, 800000)
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES  (5, '13:30:00', '2023-10-05',1, 5, 1200000)
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])  
VALUES  (6, '15:30:00', '2023-10-06', 6, 6, 5000000)
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total]) 
VALUES  (7, '13:30:00', '2023-10-07', 2, 7, 500000)

INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES  (8, '13:30:00', '2023-11-01', 3, 8, 3000000)
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES  (9, '19:30:00', '2023-11-02', 6, 9,1500000)
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])  
VALUES  (10, '16:00', '2023-11-02', 2, 1, 0);
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES  (11, '21:00', '2023-11-04', 3, 12, 0);

INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES  (12, '19:30', '2023-11-10', 6, 17, 0);
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total]) 
VALUES  (13, '15:00', '2023-11-12', 1, 12, 0);
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total]) 
VALUES  (14, '18:30', '2023-11-16', 2, 15, 0);
INSERT INTO [dbo].[Bill] ([BillID], [PaymentTime], [PaymentDate], [CustomerID], [EmployeeID], [Total])
VALUES  (15, '12:30', '2023-11-21', 3, 10, 0);
go
SET IDENTITY_INSERT [dbo].[Bill] OFF
SET IDENTITY_INSERT [dbo].[DetailBill] ON
go
INSERT INTO [dbo].[DetailBill] ([DetailBillID], [RoomID], [BillID], [Checkin], [Checkout])  
VALUES  (1,1, 1, '2022-09-01 12:00:00', '2022-09-01 15:30:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (2,2, 2, '2023-09-03 12:00:00', '2023-09-02 15:45:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (3,3, 3, '2023-10-03 20:30:00', '2023-10-03 21:30:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (4,4, 4, '2023-10-05 12:00:00', '2023-10-04 13:30:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (5,5, 5, '2023-10-05 17:00:00', '2023-10-05 13:30:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (6,6, 6, '2023-10-07 10:00:00', '2023-10-06 15:30:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (7,7, 7, '2023-10-07 12:00:00', '2023-10-07 13:30:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (8,8, 8, '2023-11-02 12:00:00', '2023-11-01 13:30:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (9,9, 9, '2023-11-02 18:00:00', '2023-11-02 19:30:00')
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (10,1, 10, '2023-11-03 12:00', '2023-11-02 16:00');
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (11,2, 11, '2023-11-04 20:00', '2023-11-04 21:00');
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (12,11, 12, '2023-11-10 18:00', '2023-11-10 19:30');
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (13,12, 13, '2023-11-12 10:00', '2023-11-12 15:00');
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (14,17, 14, '2023-11-16 16:00', '2023-11-16 18:30');
INSERT INTO [dbo].[DetailBill] ([DetailBillID],[RoomID], [BillID], [Checkin], [Checkout]) 
VALUES  (15,20, 15, '2023-11-21 10:00', '2023-11-21 12:30');
go
SET IDENTITY_INSERT [dbo].[DetailBill] OFF
SET IDENTITY_INSERT [dbo].[DetailServiceRoom] ON
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (1,1, 1, 2)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (2,1, 2, 1)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (3,2, 3, 1)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (4,2, 4, 1)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (5,3, 5, 5)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (6,3, 6, 2)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (7,4, 1, 1)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (8,4, 3, 2)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (9,5, 2, 10)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (10,5, 4, 1)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (11,6, 5, 15)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (12,6, 6, 5)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (13,7, 2, 5)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (14,7, 4, 1)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (15,8, 1, 4)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (16,8, 3, 1)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (17,9, 2, 15)
INSERT INTO [dbo].[DetailServiceRoom] ([DetailServiceBillID], [BillID], [ServiceID], [Quantity])
VALUES  (18,9, 5, 1)

select * from [dbo].[Room]
select * from [dbo].[Employee]
select * from [dbo].[Customer]
select * from [dbo].[Service]
select * from [dbo].[Booking]
select * from [dbo].[DetailBill]
select * from [dbo].[DetailServiceRoom]
select * from [dbo].[Bill]