-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: fastfood
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Tạo bảng với khóa chính và khóa ngoại
CREATE TABLE CaLam (
    maCa VARCHAR(255) PRIMARY KEY,
    moTa TEXT,
    gioBD TIME,
    gioKT TIME,
    trangThai VARCHAR(50)
);

CREATE TABLE ChiTietCongThuc (
    maCongThuc VARCHAR(255),
    maNguyenLieu VARCHAR(255),
    soLuong DOUBLE,
    donViTinh VARCHAR(50),
    PRIMARY KEY (maCongThuc, maNguyenLieu)
);

CREATE TABLE ChiTietHoaDon (
    maHoaDon VARCHAR(255),
    maThucAn VARCHAR(255),
    soLuongBan INT,
    thanhTien DOUBLE,
    PRIMARY KEY (maHoaDon, maThucAn)
);

CREATE TABLE ChiTietKhuyenMai (
    maKhuyenMai VARCHAR(255),
    maThucAn VARCHAR(255),
    giaTriKhuyenMai DECIMAL(10, 2),
    trangThai VARCHAR(255),
    PRIMARY KEY (maKhuyenMai, maThucAn)
);

CREATE TABLE ChiTietPhieuNhap (
    maPhieuNhap VARCHAR(255),
    soLuongNhap INT,
    maNguyenLieu VARCHAR(255),
    giaNhap DOUBLE,
    thanhTien DOUBLE,
    ghiChu VARCHAR(255),
    trangThai tinyint,
    PRIMARY KEY (maPhieuNhap, maNguyenLieu)
);

CREATE TABLE ChucVu (
    maChucVu VARCHAR(255) PRIMARY KEY,
    tenChucVu VARCHAR(255),
    trangThai VARCHAR(50),
    luongTheoGio DECIMAL(10, 2)
);

CREATE TABLE CongThuc (
    maCongThuc VARCHAR(255) PRIMARY KEY,
    tenCongThuc VARCHAR(255),
    moTa TEXT
);

CREATE TABLE HoaDon (
    maHoaDon VARCHAR(255) PRIMARY KEY,
    ngayLap DATE,
    maNhanVien VARCHAR(255),
    maKhachHang VARCHAR(255),
    tongTien DOUBLE
);

CREATE TABLE KhachHang (
    maKhachHang VARCHAR(255) PRIMARY KEY,
    tenKhachHang VARCHAR(255),
    gioiTinh VARCHAR(50),
    SDT VARCHAR(10),
    email VARCHAR(255),
    diaChi TEXT,
    soDiem INT
);

CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(255) PRIMARY KEY,
    tenKhuyenMai VARCHAR(255),
    donViKhuyenMai VARCHAR(255),
    ngayBatDau DATE,
    ngayKetThuc DATE,
    giaTriKhuyenMai DOUBLE,
    dieuKienApDung TEXT,
    trangThai tinyint
);

CREATE TABLE LichLamViec (
    maLichLam VARCHAR(255),
    maNhanVien VARCHAR(255),
    ngayLamViec VARCHAR(255),
    maCaLam VARCHAR(255),
    trangThai tinyint,
    PRIMARY KEY (maLichLam, maNhanVien)
);

CREATE TABLE NguyenLieu (
    maNguyenLieu VARCHAR(255) PRIMARY KEY,
    tenNguyenLieu VARCHAR(255),
    loaiNguyenLieu VARCHAR(255),
    ngayNhap DATE,
    ngayHetHan DATE,
    soLuong DOUBLE,
    donViDo VARCHAR(50),
    gia DOUBLE
);

CREATE TABLE NhaCungCap (
    maNhaCungCap VARCHAR(255) PRIMARY KEY,
    tenNhaCungCap VARCHAR(255),
    email VARCHAR(255),
    SDT VARCHAR(15),
    diaChi TEXT,
    trangThai tinyint
);

CREATE TABLE PhanPhoi (
    maNCC VARCHAR(255),
    maNguyenLieu VARCHAR(255),
    giaNhap DECIMAL(10, 2),
    PRIMARY KEY (maNCC, maNguyenLieu)
);

CREATE TABLE NhanVien (
    maNhanVien VARCHAR(255) PRIMARY KEY,
    tenNhanVien VARCHAR(255),
    SDT VARCHAR(10),
    email VARCHAR(255),
    ngaySinh DATE,
    gioiTinh VARCHAR(50),
    diaChi TEXT,
    trangThai VARCHAR(50),
    maChucVu VARCHAR(255),
    tenDangNhap VARCHAR(255)
);

CREATE TABLE PhieuNhap (
    maPhieuNhap VARCHAR(255) PRIMARY KEY,
    ngayNhap DATE,
    maNhanVien VARCHAR(255),
    maNCC VARCHAR(255),
    tongTien VARCHAR(255),
    trangThai tinyint
);

CREATE TABLE TaiKhoan (
    tenDangNhap VARCHAR(255) PRIMARY KEY,
    matKhau VARCHAR(255),
    trangThai VARCHAR(50),
    ngayTao DATE,
    vaiTro VARCHAR(255)
);

DELIMITER //

CREATE PROCEDURE editTaiKhoan(
    IN newTenDangNhap VARCHAR(255),
    IN newMatKhau VARCHAR(255),
    IN newTrangThai VARCHAR(50),
    IN newVaiTro VARCHAR(50),
    IN oldTenDangNhap VARCHAR(255)
)
BEGIN
    -- 1. Cập nhật tạm bảng nhanvien sang giá trị tạm (tránh lỗi khoá ngoại)
    UPDATE nhanvien 
    SET tenDangNhap = NULL 
    WHERE tenDangNhap = oldTenDangNhap;

    -- 2. Cập nhật bảng taikhoan
    UPDATE taikhoan 
    SET tenDangNhap = newTenDangNhap,
        matKhau = newMatKhau,
        trangThai = newTrangThai,
        vaiTro = newVaiTro
    WHERE tenDangNhap = oldTenDangNhap;

    -- 3. Cập nhật lại bảng nhanvien với tenDangNhap mới
    UPDATE nhanvien 
    SET tenDangNhap = newTenDangNhap 
    WHERE tenDangNhap IS NULL;
END //

DELIMITER ;
DELIMITER //

CREATE PROCEDURE deleteTaiKhoanProc(IN pTenDangNhap VARCHAR(255))
BEGIN
    -- Bước 1: Set NULL trong nhanvien
    UPDATE nhanvien SET tenDangNhap = NULL WHERE tenDangNhap = pTenDangNhap;

    -- Bước 2: Xóa trong taikhoan
    DELETE FROM taikhoan WHERE tenDangNhap = pTenDangNhap;
END //

DELIMITER ;
DELIMITER //
CREATE PROCEDURE insertTaiKhoan(
    IN tenDangNhap VARCHAR(255),
    IN matKhau VARCHAR(255),
    IN ngayTao DATE,
    IN trangThai VARCHAR(50),
    IN vaiTro VARCHAR(50),
    IN maNV VARCHAR(50)
)
BEGIN
    INSERT INTO taikhoan (tenDangNhap, matKhau, trangThai, ngayTao, vaiTro) 
    VALUES (tenDangNhap, matKhau, trangThai, ngayTao, vaiTro);
    
    UPDATE nhanvien 
    SET tenDangNhap = tenDangNhap 
    WHERE maNhanVien = maNV;
END //
DELIMITER ;

CREATE TABLE ThucAn (
    maThucAn VARCHAR(255) PRIMARY KEY,
    tenThucAn VARCHAR(255),
    anhThucAn VARCHAR(255),
    moTa TEXT,
    loaiMonAn VARCHAR(255),
    gia DOUBLE,
    soLuong INT,
    maCongThuc VARCHAR(255)
);

-- Thêm khóa ngoại vào bảng ChiTietCongThuc
ALTER TABLE ChiTietCongThuc
ADD FOREIGN KEY (maCongThuc) REFERENCES CongThuc(maCongThuc),
ADD FOREIGN KEY (maNguyenLieu) REFERENCES NguyenLieu(maNguyenLieu);

-- Thêm khóa ngoại vào bảng ChiTietHoaDon
ALTER TABLE ChiTietHoaDon
ADD FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
ADD FOREIGN KEY (maThucAn) REFERENCES ThucAn(maThucAn);

-- Thêm khóa ngoại vào bảng ChiTietKhuyenMai
ALTER TABLE ChiTietKhuyenMai
ADD FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai),
ADD FOREIGN KEY (maThucAn) REFERENCES ThucAn(maThucAn);

-- Thêm khóa ngoại vào bảng ChiTietPhieuNhap
ALTER TABLE ChiTietPhieuNhap
ADD FOREIGN KEY (maPhieuNhap) REFERENCES PhieuNhap(maPhieuNhap),
ADD FOREIGN KEY (maNguyenLieu) REFERENCES NguyenLieu(maNguyenLieu);

-- Thêm khóa ngoại vào bảng HoaDon
ALTER TABLE HoaDon
ADD FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),

-- Thêm khóa ngoại vào bảng KhachHang
ALTER TABLE KhachHang
ADD FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap);

-- Thêm khóa ngoại vào bảng NhanVien
ALTER TABLE NhanVien
ADD FOREIGN KEY (maChucVu) REFERENCES ChucVu(maChucVu),
ADD FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap);

-- Thêm khóa ngoại vào bảng PhieuNhap
ALTER TABLE PhieuNhap
ADD FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien);

-- Thêm khóa ngoại vào bảng ThucAn
ALTER TABLE ThucAn
ADD FOREIGN KEY (maCongThuc) REFERENCES CongThuc(maCongThuc);

ALTER TABLE LichLamViec
ADD FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
ADD FOREIGN KEY (maCaLam) REFERENCES CaLam(maCa);

ALTER TABLE PhanPhoi
ADD FOREIGN KEY (maNCC) REFERENCES NhaCungCap(maNhaCungCap),
ADD FOREIGN KEY (maNguyenLieu) REFERENCES NguyenLieu(maNguyenLieu);

-- Thêm dữ liệu mẫu
INSERT INTO NhanVien (maNhanVien, tenNhanVien, SDT, email, ngaySinh, gioiTinh, diaChi, trangThai, maChucVu, tenDangNhap) 
VALUES 
('NV001', 'Nguyen Van A', "0123456789", 'nguyenvana@gmail.com', '1990-01-01', 'Nam', 'Hanoi', 'Đang làm việc', 'CV001', 'user1'),
('NV002', 'Tran Thi B', "0987654321", 'tranthib@gmail.com', '1992-05-10', 'Nữ', 'HCMC', 'Đã nghỉ việc', 'CV002', 'user2');

-- Thêm dữ liệu mẫu vào bảng CaLam
INSERT INTO CaLam (maCa, moTa, gioBD, gioKT, trangThai) VALUES 
('CA001', 'Ca sáng', '08:00:00', '12:00:00', 'Đang hoạt động'),
('CA002', 'Ca chiều', '13:00:00', '17:00:00', 'Đã ngừng hoạt động');

-- Thêm dữ liệu mẫu vào bảng ChiTietCongThuc
INSERT INTO ChiTietCongThuc (maCongThuc, maNguyenLieu, soLuong, donViTinh) VALUES 
('CT001', 'NL001', 2.5, 'kg'),
('CT001', 'NL002', 1.0, 'lít');

-- Thêm dữ liệu mẫu vào bảng ChiTietHoaDon
INSERT INTO ChiTietHoaDon (maHoaDon, maThucAn, soLuongBan, thanhTien) VALUES 
('HD001', 'TA001', 2, 50000),
('HD002', 'TA002', 3, 75000);

-- Thêm dữ liệu mẫu vào bảng ChiTietKhuyenMai
INSERT INTO ChiTietKhuyenMai (maKhuyenMai, maHoaDon) VALUES 
('KM001', 'HD001'),
('KM002', 'HD002');

-- Thêm dữ liệu mẫu vào bảng ChiTietPhieuNhap
INSERT INTO ChiTietPhieuNhap (maPhieuNhap, soLuongNhap, maNguyenLieu, giaNhap, thanhTien) VALUES 
('PN001', 50, 'NL001', 20000, 1000000),
('PN002', 30, 'NL002', 10000, 300000);

-- Thêm dữ liệu mẫu vào bảng ChucVu
INSERT INTO ChucVu (maChucVu, tenChucVu, trangThai, heSoLuong) VALUES 
('CV001', 'Quản lý', 'Đang hoạt động', 1.2),
('CV002', 'Nhân viên', 'Đang hoạt động', 1.1);

-- Thêm dữ liệu mẫu vào bảng CongThuc
INSERT INTO CongThuc (maCongThuc, tenCongThuc, moTa) VALUES 
('CT001', 'Công thức món A', 'Hướng dẫn làm món A'),
('CT002', 'Công thức món B', 'Hướng dẫn làm món B');

-- Thêm dữ liệu mẫu vào bảng HoaDon
INSERT INTO HoaDon (maHoaDon, ngayLap, maNhanVien, maKhachHang, tongTien) VALUES 
('HD001', '2025-04-01', 'NV001', 'KH001', 50000),
('HD002', '2025-04-02', 'NV002', 'KH002', 75000);

-- Thêm dữ liệu mẫu vào bảng KhachHang
INSERT INTO KhachHang (maKhachHang, tenKhachHang, gioiTinh, SDT, email, diaChi, soDiem, tenDangNhap) VALUES 
('KH001', 'Le Van C', 'Nam', 123456789, 'levanc@gmail.com', 'Hanoi', 100, 'user1'),
('KH002', 'Nguyen Thi D', 'Nữ', 987654321, 'nguyenthid@gmail.com', 'HCMC', 200, 'user2');

-- Thêm dữ liệu mẫu vào bảng KhuyenMai
INSERT INTO KhuyenMai (maKhuyenMai, tenKhuyenMai, hinhThucKhuyenMai, ngayBatDau, ngayKetThuc, giaTriKhuyenMai, dieuKienApDung) VALUES 
('KM001', 'Giảm giá 10%', 'Giảm giá', '2025-03-01', '2025-04-01', 10, 'Tổng tiền > 100,000'),
('KM002', 'Giảm giá 20%', 'Giảm giá', '2025-03-15', '2025-04-15', 20, 'Tổng tiền > 200,000');

-- Thêm dữ liệu mẫu vào bảng NguyenLieu
INSERT INTO NguyenLieu (maNguyenLieu, tenNguyenLieu, loaiNguyenLieu, ngayNhap, ngayHetHan, soLuong, donViDo) VALUES 
('NL001', 'Thịt bò', 'Thực phẩm tươi sống', '2025-03-20', '2025-03-30', 100, 'kg'),
('NL002', 'Nước mắm', 'Gia vị', '2025-03-25', '2025-04-10', 50, 'lít');

-- Thêm dữ liệu mẫu vào bảng NhaCungCap
INSERT INTO NhaCungCap (maNhaCungCap, tenNhaCungCap, email, SDT, diaChi) VALUES 
('NCC001', 'Công ty A', 'congtya@gmail.com', 123456789, 'Hanoi'),
('NCC002', 'Công ty B', 'congtyb@gmail.com', 987654321, 'HCMC');

-- Thêm dữ liệu mẫu vào bảng PhieuNhap
INSERT INTO PhieuNhap (maPhieuNhap, ngayNhap, maNhanVien) VALUES 
('PN001', '2025-03-20', 'NV001'),
('PN002', '2025-03-25', 'NV002');

-- Thêm dữ liệu mẫu vào bảng TaiKhoan
INSERT INTO TaiKhoan (tenDangNhap, matKhau, trangThai, ngayTao, vaiTro) VALUES 
('user1', 'password1', 'Hoạt động', '2025-01-01', 'Admin'),
('user2', 'password2', 'Hoạt động', '2025-02-01', 'User');

-- Thêm dữ liệu mẫu vào bảng ThucAn
INSERT INTO ThucAn (maThucAn, tenThucAn, anhThucAn, moTa, loaiMonAn, gia, soLuong, maCongThuc) VALUES 
('TA001', 'Cơm gà', 'Resources\\Image\\ComGa.png','Cơm với thịt gà', 'Món chính', 25000, 100, 'CT001'),
('TA002', 'Gà rán', 'Resources\\Image\\GaRan.png','Gà được rán', 'Món chính', 30000, 100, 'CT002');