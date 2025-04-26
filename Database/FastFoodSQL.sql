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

--
-- Table structure for table `calam`
--

DROP TABLE IF EXISTS `calam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calam` (
  `maCa` varchar(20) NOT NULL,
  `moTa` varchar(255) DEFAULT NULL,
  `gioBD` varchar(10) DEFAULT NULL,
  `gioKT` varchar(10) DEFAULT NULL,
  `trangThai` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`maCa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calam`
--

LOCK TABLES `calam` WRITE;
/*!40000 ALTER TABLE `calam` DISABLE KEYS */;
INSERT INTO `calam` 
VALUES ('CA010','Tối','17:00','23:00',1),
('CA002','Trưa','12:00','16:00',1),
('CA004','Sáng','08:00','15:00',1),
('CA005','Sáng','08:00','16:00',1),
('CA006','Trưa','10:00','16:00',1),
('CA007','Sáng','08:00','14:00',1),
('CA008','Tối','15:00','23:00',1),
('CA009','Tối','16:00','23:00',1);
/*!40000 ALTER TABLE `calam` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitietcongthuc`
--

DROP TABLE IF EXISTS `chitietcongthuc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietcongthuc` (
  `maCongThuc` varchar(20) NOT NULL,
  `maNguyenLieu` varchar(20) NOT NULL,
  `soLuong` decimal(10,2) DEFAULT NULL,
  `donViTinh` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`maCongThuc`,`maNguyenLieu`),
  KEY `maNguyenLieu` (`maNguyenLieu`),
  CONSTRAINT `chitietcongthuc_ibfk_1` FOREIGN KEY (`maCongThuc`) REFERENCES `congthuc` (`maCongThuc`),
  CONSTRAINT `chitietcongthuc_ibfk_2` FOREIGN KEY (`maNguyenLieu`) REFERENCES `nguyenlieu` (`maNguyenLieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietcongthuc`
--

LOCK TABLES `chitietcongthuc` WRITE;
/*!40000 ALTER TABLE `chitietcongthuc` DISABLE KEYS */;
INSERT INTO `chitietcongthuc` 
VALUES ('CT001','NL001',2.50,'kg'),
('CT001','NL002',1.00,'kg'),
('CT002','NL001',3.00,'kg');
/*!40000 ALTER TABLE `chitietcongthuc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitiethoadon`
--

DROP TABLE IF EXISTS `chitiethoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitiethoadon` (
  `maHoaDon` varchar(20) NOT NULL,
  `maThucAn` varchar(20) NOT NULL,
  `soLuongBan` int DEFAULT NULL,
  `thanhTien` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`maHoaDon`,`maThucAn`),
  KEY `maThucAn` (`maThucAn`),
  CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`maHoaDon`) REFERENCES `hoadon` (`maHoaDon`),
  CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`maThucAn`) REFERENCES `thucan` (`maThucAn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitiethoadon`
--

LOCK TABLES `chitiethoadon` WRITE;
/*!40000 ALTER TABLE `chitiethoadon` DISABLE KEYS */;
INSERT INTO `chitiethoadon` 
VALUES ('HD001','TA001',2,300.00),
('HD001','TA002',1,200.00),
('HD002','TA002',3,450.00);
/*!40000 ALTER TABLE `chitiethoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitietkhuyenmai`
--

DROP TABLE IF EXISTS `chitietkhuyenmai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietkhuyenmai` (
  `maKhuyenMai` varchar(20) NOT NULL,
  `maThucAn` varchar(20) NOT NULL,
  `giaTriKhuyenMai` decimal(10,2) DEFAULT NULL,
  `trangThai` tinyint DEFAULT NULL,
  PRIMARY KEY (`maKhuyenMai`,`maThucAn`),
  KEY `chitietkhuyenmai_ibfk_2_idx` (`maThucAn`),
  CONSTRAINT `chitietkhuyenmai_ibfk_1` FOREIGN KEY (`maKhuyenMai`) REFERENCES `khuyenmai` (`maKhuyenMai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietkhuyenmai`
--

LOCK TABLES `chitietkhuyenmai` WRITE;
/*!40000 ALTER TABLE `chitietkhuyenmai` DISABLE KEYS */;
INSERT INTO `chitietkhuyenmai` 
VALUES ('KM001','TA001',23.00,1),
('KM001','TA002',22.00,1),
('KM003','TA001',35000.00,1);
/*!40000 ALTER TABLE `chitietkhuyenmai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitietphieunhap`
--

DROP TABLE IF EXISTS `chitietphieunhap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietphieunhap` (
  `maPhieuNhap` varchar(20) NOT NULL,
  `maNguyenLieu` varchar(20) NOT NULL,
  `soLuongNhap` int DEFAULT NULL,
  `giaNhap` decimal(10,2) DEFAULT NULL,
  `thanhTien` decimal(10,2) DEFAULT NULL,
  `ghiChu` varchar(255) DEFAULT NULL,
  `trangThai` tinyint DEFAULT NULL,
  PRIMARY KEY (`maPhieuNhap`,`maNguyenLieu`),
  KEY `maNguyenLieu` (`maNguyenLieu`),
  CONSTRAINT `chitietphieunhap_ibfk_1` FOREIGN KEY (`maPhieuNhap`) REFERENCES `phieunhap` (`maPhieuNhap`),
  CONSTRAINT `chitietphieunhap_ibfk_2` FOREIGN KEY (`maNguyenLieu`) REFERENCES `nguyenlieu` (`maNguyenLieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietphieunhap`
--

LOCK TABLES `chitietphieunhap` WRITE;
/*!40000 ALTER TABLE `chitietphieunhap` DISABLE KEYS */;
INSERT INTO `chitietphieunhap` 
VALUES ('PN001','NL001',10,15.00,150.00,NULL,1),
('PN001','NL002',5,20.00,100.00,NULL,1),
('PN002','NL001',8,15.00,120.00,NULL,1),
('PN008','NL001',2,15.00,30.00,'',1),
('PN008','NL002',2,20.00,40.00,'',1),
('PN009','NL001',2,15.00,30.00,'',1),
('PN009','NL002',2,20.00,40.00,'',1),
('PN010','NL001',3,15.00,45.00,'',1),
('PN010','NL002',3,20.00,60.00,'',1),
('PN011','NL001',5,15.00,75.00,'',1),
('PN011','NL002',2,20.00,40.00,'',1),
('PN012','NL001',2,15.00,30.00,'',1),
('PN012','NL002',2,20.00,40.00,'',1),
('PN013','NL001',2,15.00,30.00,'',1),
('PN013','NL002',2,20.00,40.00,'',1),
('PN014','NL001',13,15.00,195.00,'',1),
('PN015','NL002',20,20.00,400.00,'',1),
('PN016','NL002',19,20.00,380.00,'',1),
('PN017','NL001',5,15.00,75.00,'',1),
('PN018','NL001',18,15.00,270.00,'',1),
('PN019','NL002',15,20.00,300.00,'',1),
('PN020','NL001',25,15.00,375.00,'',1);
/*!40000 ALTER TABLE `chitietphieunhap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chucvu`
--

DROP TABLE IF EXISTS `chucvu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chucvu` (
  `maChucVu` varchar(20) NOT NULL,
  `tenChucVu` varchar(100) DEFAULT NULL,
  `trangThai` tinyint DEFAULT NULL,
  `luongTheoGio` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`maChucVu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chucvu`
--

LOCK TABLES `chucvu` WRITE;
/*!40000 ALTER TABLE `chucvu` DISABLE KEYS */;
INSERT INTO `chucvu` 
VALUES ('CV001','StorageManager',1,25000.00),
('CV002','Manager',1,35000.00),
('CV003','Accountant',1,30000.00),
('CV004','Admin',1,35000.00),
('CV005','Cashier',1,26000.00);
/*!40000 ALTER TABLE `chucvu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `congthuc`
--

DROP TABLE IF EXISTS `congthuc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `congthuc` (
  `maCongThuc` varchar(20) NOT NULL,
  `tenCongThuc` varchar(100) DEFAULT NULL,
  `moTa` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`maCongThuc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `congthuc`
--

LOCK TABLES `congthuc` WRITE;
/*!40000 ALTER TABLE `congthuc` DISABLE KEYS */;
INSERT INTO `congthuc` 
VALUES ('CT001','Cơm gà','Công thức cơm gà'),
('CT002','Hamburger gà','Công thức hamburger gà');
/*!40000 ALTER TABLE `congthuc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hoadon`
--

DROP TABLE IF EXISTS `hoadon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hoadon` (
  `maHoaDon` varchar(20) NOT NULL,
  `ngayLap` date DEFAULT NULL,
  `maNhanVien` varchar(20) DEFAULT NULL,
  `maKhachHang` varchar(20) DEFAULT NULL,
  `tongTien` decimal(10,2) DEFAULT NULL,
  `pttt` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`maHoaDon`),
  KEY `maNhanVien` (`maNhanVien`),
  KEY `maKhachHang` (`maKhachHang`),
  CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`maNhanVien`) REFERENCES `nhanvien` (`maNhanVien`),
  CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`maKhachHang`) REFERENCES `khachhang` (`maKhachHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoadon`
--

LOCK TABLES `hoadon` WRITE;
/*!40000 ALTER TABLE `hoadon` DISABLE KEYS */;
INSERT INTO `hoadon` 
VALUES ('HD001','2023-03-15','NV001','KH001',500.00),
('HD002','2023-03-16','NV002','KH002',750.00);
/*!40000 ALTER TABLE `hoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang` (
  `maKhachHang` varchar(20) NOT NULL,
  `tenKhachHang` varchar(100) DEFAULT NULL,
  `gioiTinh` varchar(20) NOT NULL,
  `SDT` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `diaChi` varchar(255) DEFAULT NULL,
  `soDiem` int DEFAULT NULL,
  PRIMARY KEY (`maKhachHang`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang`
--

LOCK TABLES `khachhang` WRITE;
/*!40000 ALTER TABLE `khachhang` DISABLE KEYS */;
INSERT INTO `khachhang` 
VALUES ('KH001','Lê Văn C','Nam','0912345123','c@example.com','Hà Nội',100),
('KH002','Phạm Thị D','Nữ','0987651234','d@example.com','HCM',200);
/*!40000 ALTER TABLE `khachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khuyenmai`
--

DROP TABLE IF EXISTS `khuyenmai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khuyenmai` (
  `maKhuyenMai` varchar(20) NOT NULL,
  `tenKhuyenMai` varchar(100) DEFAULT NULL,
  `donViKhuyenMai` varchar(50) DEFAULT NULL,
  `ngayBatDau` date DEFAULT NULL,
  `ngayKetThuc` date DEFAULT NULL,
  `dieuKienApDung` varchar(255) DEFAULT NULL,
  `giaTriKhuyenMai` decimal NOT NULL,
  `trangThai` tinyint DEFAULT NULL,
  PRIMARY KEY (`maKhuyenMai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyenmai`
--

LOCK TABLES `khuyenmai` WRITE;
/*!40000 ALTER TABLE `khuyenmai` DISABLE KEYS */;
INSERT INTO `khuyenmai` 
VALUES ('KM001','Khuyến mãi A','Phần trăm','2023-03-01','2023-03-31','Áp dụng cho hóa đơn trên 1000',10,1),
('KM002','Khuyến mãi B','Số tiền','2023-04-01','2023-04-30','Áp dụng cho hóa đơn trên 2000',20000,1),
('KM003','Khuyến mãi Test','Số tiền','2025-03-31','2025-04-02','Không có',0,1),
('KM004','Khuyến mãi Testt','Phần trăm','2025-03-24','2025-04-02','Không cóo',0,1);
/*!40000 ALTER TABLE `khuyenmai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lichlamviec`
--

DROP TABLE IF EXISTS `lichlamviec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lichlamviec` (
  `maLichLam` varchar(50) NOT NULL,
  `maNhanVien` varchar(50) NOT NULL,
  `ngayLamViec` datetime DEFAULT NULL,
  `maCaLam` varchar(50) DEFAULT NULL,
  `trangThai` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`maLichLam`,`maNhanVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lichlamviec`
--

LOCK TABLES `lichlamviec` WRITE;
/*!40000 ALTER TABLE `lichlamviec` DISABLE KEYS */;
INSERT INTO `lichlamviec` 
VALUES ('LL001','NV001','2025-03-17 00:00:00','CA002',1),
('LL002','NV002','2025-03-18 00:00:00','CA002',1),
('LL003','NV001','2025-03-30 00:00:00','CA007',1),
('LL006','NV002','2025-03-30 00:00:00','CA002',1),
('LL008','NV001','2025-03-31 00:00:00','CA006',1),
('LL009','NV002','2025-03-31 00:00:00','CA005',1);
/*!40000 ALTER TABLE `lichlamviec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nguyenlieu`
--

DROP TABLE IF EXISTS `nguyenlieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nguyenlieu` (
  `maNguyenLieu` varchar(20) NOT NULL,
  `tenNguyenLieu` varchar(100) DEFAULT NULL,
  `loaiNguyenLieu` varchar(50) DEFAULT NULL,
  `ngayNhap` date DEFAULT NULL,
  `ngayHetHan` date DEFAULT NULL,
  `soLuong` decimal(10,2) DEFAULT NULL,
  `donViDo` varchar(50) DEFAULT NULL,
  `gia` decimal NOT NULL,
  PRIMARY KEY (`maNguyenLieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguyenlieu`
--

LOCK TABLES `nguyenlieu` WRITE;
/*!40000 ALTER TABLE `nguyenlieu` DISABLE KEYS */;
INSERT INTO `nguyenlieu` 
VALUES ('NL001','Bánh phở','Nguyên liệu chính','2023-01-01','2023-12-31',100,'kg',175000.00),
('NL002','Thịt bò','Thịt','2023-01-15','2023-12-31',100,'kg',115000.00);
/*!40000 ALTER TABLE `nguyenlieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhacungcap`
--

DROP TABLE IF EXISTS `nhacungcap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhacungcap` (
  `maNhaCungCap` varchar(20) NOT NULL,
  `tenNhaCungCap` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `SDT` varchar(20) DEFAULT NULL,
  `diaChi` varchar(255) DEFAULT NULL,
  `trangThai` tinyint DEFAULT NULL,
  PRIMARY KEY (`maNhaCungCap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhacungcap`
--

LOCK TABLES `nhacungcap` WRITE;
/*!40000 ALTER TABLE `nhacungcap` DISABLE KEYS */;
INSERT INTO `nhacungcap` 
VALUES ('NCC001','Công ty TNHH ABC','contact@abc.com','0123456789','123 Đường A, Hà Nội',1),
('NCC002','Công ty TNHH XYZ','info@xyz.com','0987654321','456 Đường B, HCM',1);
/*!40000 ALTER TABLE `nhacungcap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanvien`
--

DROP TABLE IF EXISTS `nhanvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanvien` (
  `maNhanVien` varchar(20) NOT NULL,
  `tenNhanVien` varchar(100) DEFAULT NULL,
  `SDT` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `ngaySinh` date DEFAULT NULL,
  `gioiTinh` varchar(10) DEFAULT NULL,
  `diaChi` varchar(255) DEFAULT NULL,
  `trangThai` varchar(50) DEFAULT NULL,
  `maChucVu` varchar(20) DEFAULT NULL,
  `tenDangNhap` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`maNhanVien`),
  KEY `maChucVu` (`maChucVu`),
  KEY `tenDangNhap` (`tenDangNhap`),
  CONSTRAINT `nhanvien_ibfk_1` FOREIGN KEY (`maChucVu`) REFERENCES `chucvu` (`maChucVu`),
  CONSTRAINT `nhanvien_ibfk_2` FOREIGN KEY (`tenDangNhap`) REFERENCES `taikhoan` (`tenDangNhap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanvien`
--

LOCK TABLES `nhanvien` WRITE;
/*!40000 ALTER TABLE `nhanvien` DISABLE KEYS */;
INSERT INTO `nhanvien` 
VALUES ('NV001','Nguyễn Văn A','0912345678','a@example.com','1990-05-01','Nam','Hà Nội','Active','CV001','user1'),
('NV002','Trần Thị B','0987654321','b@example.com','1992-08-15','Nữ','HCM','Active','CV002','admin');
/*!40000 ALTER TABLE `nhanvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phanphoi`
--

DROP TABLE IF EXISTS `phanphoi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phanphoi` (
  `maNCC` varchar(45) NOT NULL,
  `maNguyenLieu` varchar(45) NOT NULL,
  `giaNhap` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`maNCC`,`maNguyenLieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phanphoi`
--

LOCK TABLES `phanphoi` WRITE;
/*!40000 ALTER TABLE `phanphoi` DISABLE KEYS */;
INSERT INTO `phanphoi` 
VALUES ('NCC001','NL001',15.00),
('NCC002','NL002',20.00);
/*!40000 ALTER TABLE `phanphoi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phieunhap`
--

DROP TABLE IF EXISTS `phieunhap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phieunhap` (
  `maPhieuNhap` varchar(20) NOT NULL,
  `ngayNhap` date DEFAULT NULL,
  `maNhanVien` varchar(20) DEFAULT NULL,
  `maNCC` varchar(45) DEFAULT NULL,
  `tongTien` decimal(10,2) DEFAULT NULL,
  `trangThai` tinyint DEFAULT NULL,
  PRIMARY KEY (`maPhieuNhap`),
  KEY `maNhanVien` (`maNhanVien`),
  CONSTRAINT `phieunhap_ibfk_1` FOREIGN KEY (`maNhanVien`) REFERENCES `nhanvien` (`maNhanVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phieunhap`
--

LOCK TABLES `phieunhap` WRITE;
/*!40000 ALTER TABLE `phieunhap` DISABLE KEYS */;
INSERT INTO `phieunhap` 
VALUES ('PN001','2023-02-15','NV001','NCC001',250.00,1),
('PN002','2023-02-16','NV002','NCC002',120.00,1),
('PN008','2025-04-02','NV001','NCC001',70.00,1),
('PN009','2025-04-02','NV001','NCC001',70.00,1),
('PN010','2025-04-02','NV001','NCC001',105.00,0),
('PN011','2025-04-02','NV001','NCC001',115.00,1),
('PN012','2025-04-02','NV001','NCC001',70.00,1),
('PN013','2025-04-02','NV001','NCC001',70.00,1),
('PN014','2025-04-03','NV001','NCC001',195.00,1),
('PN015','2025-04-03','NV001','NCC002',400.00,1),
('PN016','2025-04-03','NV001','NCC002',380.00,1),
('PN017','2025-04-03','NV001','NCC001',75.00,1),
('PN018','2025-04-03','NV001','NCC001',270.00,1),
('PN019','2025-04-03','NV001','NCC002',300.00,1),
('PN020','2025-04-03','NV001','NCC001',375.00,1);
/*!40000 ALTER TABLE `phieunhap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taikhoan`
--

DROP TABLE IF EXISTS `taikhoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taikhoan` (
  `tenDangNhap` varchar(50) NOT NULL,
  `matKhau` varchar(255) DEFAULT NULL,
  `trangThai` varchar(50) DEFAULT NULL,
  `ngayTao` date DEFAULT NULL,
  `vaiTro` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tenDangNhap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taikhoan`
--

LOCK TABLES `taikhoan` WRITE;
/*!40000 ALTER TABLE `taikhoan` DISABLE KEYS */;
INSERT INTO `taikhoan` 
VALUES ('admin','adminpass','Active','2023-01-01','Admin'),
('user1','pass1','Active','2023-01-01','Manager');
/*!40000 ALTER TABLE `taikhoan` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Table structure for table `thucan`
--

DROP TABLE IF EXISTS `thucan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thucan` (
  `maThucAn` varchar(20) NOT NULL,
  `tenThucAn` varchar(100) DEFAULT NULL,
  `anhThucAn` varchar(100) DEFAULT NULL,
  `moTa` varchar(255) DEFAULT NULL,
  `loaiMonAn` varchar(50) DEFAULT NULL,
  `gia` decimal(10,2) DEFAULT NULL,
  `soLuong` int DEFAULT NULL,
  `maCongThuc` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`maThucAn`),
  KEY `maCongThuc` (`maCongThuc`),
  CONSTRAINT `thucan_ibfk_1` FOREIGN KEY (`maCongThuc`) REFERENCES `congthuc` (`maCongThuc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thucan`
--

LOCK TABLES `thucan` WRITE;
/*!40000 ALTER TABLE `thucan` DISABLE KEYS */;
INSERT INTO `thucan` 
VALUES ('TA001','Cơm gà','Resources\Image\ComGa.png','Cơm gà thơm ngon','Món chính',150.00,100,'CT001'),
('TA002','Hamburger gà','Resources\Image\HamburgerGa.png','Hamburger gà ngon lành','Món chính',200.00,100,'CT002');
/*!40000 ALTER TABLE `thucan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-04  5:08:08
