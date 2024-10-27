-- Tạo cơ sở dữ liệu QLDT và sử dụng nó
CREATE DATABASE QLDT;
USE QLDT;

-- Tạo bảng ChiNhanh
CREATE TABLE ChiNhanh (
    MaCN NVARCHAR(10) PRIMARY KEY,
    TenCN NVARCHAR(100),
    DiaChi NVARCHAR(255),
    SDT VARCHAR(15)
);

-- Tạo bảng Kho
CREATE TABLE Kho (
    MaKho NVARCHAR(10) PRIMARY KEY,
    MaCN NVARCHAR(10),
    TenKho NVARCHAR(100),
    DiaChi NVARCHAR(255),
    FOREIGN KEY (MaCN) REFERENCES ChiNhanh(MaCN)
);

-- Tạo bảng NhanVien
CREATE TABLE NhanVien (
    MaNV NVARCHAR(10) PRIMARY KEY,
    MatKhau NVARCHAR(255),
    Quyen NVARCHAR(50),
    HoNV NVARCHAR(50),
    TenNV NVARCHAR(50),
    SDT VARCHAR(15),
    DiaChi NVARCHAR(255),
    NgaySinh DATE,
    GioiTinh NVARCHAR(10),
    MaCN NVARCHAR(10),
    Luong DECIMAL(18, 2),
    GhiChu NVARCHAR(255),
    FOREIGN KEY (MaCN) REFERENCES ChiNhanh(MaCN)
);

-- Tạo bảng KhachHang
CREATE TABLE KhachHang (
    MaKH NVARCHAR(10) PRIMARY KEY,
    TenKH NVARCHAR(100),
    DiaChi NVARCHAR(255),
    SDT VARCHAR(15)
);

-- Tạo bảng DienThoai
CREATE TABLE DienThoai (
    MaDT NVARCHAR(50) PRIMARY KEY,
    HinhAnh NVARCHAR(MAX),
    TenDT NVARCHAR(100),
    DonGia DECIMAL(18, 2),
    ManHinh VARCHAR(50),
    HeDieuHanh VARCHAR(50),
    Camera VARCHAR(50),
    Ram VARCHAR(50),
    BoNho VARCHAR(50),
    DungLuongPin VARCHAR(50)
);

-- Tạo bảng HoaDon
CREATE TABLE HoaDon (
    MaHD NVARCHAR(10) PRIMARY KEY,
    MaNV NVARCHAR(10),
    MaKho NVARCHAR(10),
    MaKH NVARCHAR(10),
	MaDT NVARCHAR(50),
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    FOREIGN KEY (MaKho) REFERENCES Kho(MaKho),
    FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH),
	FOREIGN KEY (MaDT) REFERENCES DienThoai(MaDT),
);

-- Tạo bảng ChiTietHoaDon
CREATE TABLE ChiTietHoaDon (
    MaCTHD NVARCHAR(10) PRIMARY KEY,
    MaHD NVARCHAR(10),
    MaDT NVARCHAR(50),
    SoLuong INT,  -- Thêm trường SoLuong
    GhiChu NVARCHAR(255),
    FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD),
    FOREIGN KEY (MaDT) REFERENCES DienThoai(MaDT)
);

-- Chèn dữ liệu vào bảng ChiNhanh
INSERT INTO ChiNhanh (MaCN, TenCN, DiaChi, SDT)
VALUES
('CN01', 'Chi Nhanh Ha Noi', '123 Duong Pho Hue, Quan Hai Ba Trung, Ha Noi', '0912345678'),
('CN02', 'Chi Nhanh TP.HCM', '456 Duong Le Loi, Quan 1, TP.HCM', '0987654321');

-- Chèn dữ liệu vào bảng Kho
INSERT INTO Kho (MaKho, MaCN, TenKho, DiaChi)
VALUES
('K001', 'CN01', 'Kho Chinh Ha Noi', 'Kho A, 123 Duong Pho Hue, Quan Hai Ba Trung, Ha Noi'),
('K002', 'CN01', 'Kho Phu Ha Noi', 'Kho B, 125 Duong Pho Hue, Quan Hai Ba Trung, Ha Noi'),
('K003', 'CN02', 'Kho Chinh TP.HCM', 'Kho A, 456 Duong Le Loi, Quan 1, TP.HCM'),
('K004', 'CN02', 'Kho Phu TP.HCM', 'Kho B, 458 Duong Le Loi, Quan 1, TP.HCM'),
('K005', 'CN02', 'Kho Du Tru TP.HCM', 'Kho C, 460 Duong Le Loi, Quan 1, TP.HCM');

-- Chèn dữ liệu vào bảng NhanVien
INSERT INTO NhanVien (MaNV, MatKhau, Quyen, HoNV, TenNV, SDT, DiaChi, NgaySinh, GioiTinh, MaCN, Luong, GhiChu)
VALUES
('NV01', '123456', 'QuanLyCuaHang', 'Nguyen', 'An', '0911223344', '12 Pho Hue, Ha Noi', '1990-01-01', 'Nam', 'CN01', 20000000, 'Quan ly chi nhánh Ha Noi'),
('NV02', 'abcdef', 'NhanVien', 'Pham', 'Binh', '0911555666', '34 Le Duan, Ha Noi', '1992-02-02', 'Nam', 'CN01', 15000000, 'Nhan vien ban hang Ha Noi'),
('NV03', '789xyz', 'QuanLyCuaHang', 'Tran', 'Cam', '0988223344', '12 Tran Hung Dao, TP.HCM', '1988-03-03', 'Nu', 'CN02', 22000000, 'Quan ly chi nhánh TP.HCM'),
('NV04', 'ghijk', 'NhanVien', 'Do', 'Dung', '0977555666', '56 Le Loi, TP.HCM', '1995-04-04', 'Nu', 'CN02', 14000000, 'Nhan vien ban hang TP.HCM'),
('NV05', 'mno789', 'NhanVien', 'Le', 'Hung', '0909777888', '78 Nguyen Van Troi, TP.HCM', '1994-05-05', 'Nam', 'CN02', 15000000, 'Nhan vien kho TP.HCM');

-- Chèn dữ liệu vào bảng KhachHang
INSERT INTO KhachHang (MaKH, TenKH, DiaChi, SDT)
VALUES
('KH01', 'Nguyen Thi Thu', '123 Cau Giay, Ha Noi', '0901122334'),
('KH02', 'Tran Van Tung', '456 Hai Ba Trung, Ha Noi', '0911456789'),
('KH03', 'Pham Thi Mai', '789 Nguyen Van Troi, TP.HCM', '0933555666'),
('KH04', 'Le Huu Minh', '123 Nguyen Hue, TP.HCM', '0901223344'),
('KH05', 'Hoang Anh Tu', '456 Le Duan, TP.HCM', '0987555666');

-- Chèn dữ liệu vào bảng DienThoai
INSERT INTO DienThoai (MaDT, HinhAnh, TenDT, DonGia, ManHinh, HeDieuHanh, Camera, Ram, BoNho, DungLuongPin)
VALUES
('DT01', 'C:\Users\quinh\Pictures\iphone-7.jpg', 'iPhone 7', 30000000, '6.7 inch', 'iOS', '12 MP', '6 GB', '128 GB', '4352 mAh'),
('DT02', 'C:\Users\quinh\Pictures\samsung.png', 'Samsung Galaxy S21', 20000000, '6.2 inch', 'Android', '64 MP', '8 GB', '256 GB', '4000 mAh'),
('DT03', 'C:\Users\quinh\Pictures\iphone-15.jpg', 'iPhone 15', 15000000, '6.81 inch', 'Android', '108 MP', '8 GB', '128 GB', '4600 mAh'),
('DT04', 'C:\Users\quinh\Pictures\oppo-A3.png', 'Oppo A3', 24000000, '6.7 inch', 'Android', '50 MP', '12 GB', '256 GB', '4500 mAh'),
('DT05', 'C:\Users\quinh\Pictures\realme.jpg', 'Realme', 17000000, '6.56 inch', 'Android', '48 MP', '12 GB', '256 GB', '4200 mAh');

-- Chèn dữ liệu vào bảng HoaDon
INSERT INTO HoaDon (MaHD, MaNV, MaKho, MaKH, MaDT)
VALUES
('HD01', 'NV02', 'K001', 'KH01', 'DT01'),
('HD02', 'NV02', 'K002', 'KH02', 'DT02'),
('HD03', 'NV05', 'K003', 'KH03', 'DT03'),
('HD04', 'NV04', 'K004', 'KH04', 'DT04'),
('HD05', 'NV05', 'K005', 'KH05', 'DT05');

-- Chèn dữ liệu vào bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (MaCTHD, MaHD, MaDT, SoLuong, GhiChu)
VALUES
('CTHD01', 'HD01', 'DT01', 2, 'Khuyen mai 10%'),
('CTHD02', 'HD02', 'DT02', 1, 'Khuyen mai 5%'),
('CTHD03', 'HD03', 'DT03', 5, 'Khuyen mai 15%'),
('CTHD04', 'HD04', 'DT04', 3, 'Khuyen mai 20%'),
('CTHD05', 'HD05', 'DT05', 4, 'Khuyen mai 10%');

ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_MaNV
FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV)
ON DELETE CASCADE;


SELECT c.MaCTHD, kh.TenKH, dt.TenDT, dt.DonGia, c.SoLuong, c.GhiChu 
FROM ChiTietHoaDon c 
JOIN HoaDon h ON c.MaHD = h.MaHD 
JOIN KhachHang kh ON h.MaKH = kh.MaKH 
JOIN DienThoai dt ON c.MaDT = dt.MaDT 
WHERE c.MaHD = '" + maHD + "'

SELECT * FROM DienThoai;

SELECT MaCTHD FROM ChiTietHoaDon;

ALTER TABLE HoaDon
ADD MaDT NVARCHAR(50);

-- Cập nhật bảng HoaDon để thêm khóa ngoại đến bảng DienThoai
ALTER TABLE HoaDon
ADD FOREIGN KEY (MaDT) REFERENCES DienThoai(MaDT);

SELECT * 
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_NAME = 'DienThoai';

-- Thực hiện trên Publisher
ALTER TABLE DienThoai
ADD MaDT NVARCHAR(50) PRIMARY KEY; -- Chỉ nếu MaDT chưa tồn tại

ALTER TABLE HoaDon
ADD MaDT NVARCHAR(50); -- Thêm cột MaDT vào HoaDon

ALTER TABLE HoaDon
ADD FOREIGN KEY (MaDT) REFERENCES DienThoai(MaDT); -- Thêm ràng buộc khóa ngoại

