CREATE DATABASE  IF NOT EXISTS `fastfood` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `fastfood`;
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
INSERT INTO `calam` VALUES ('C1','Sáng','07:00','11:00',1),('C2','Trưa','12:00','16:00',1),('C3','Tối','17:00','21:00',1);
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
INSERT INTO `chitietcongthuc` VALUES ('CT1','NL1',2.50,'kg'),('CT1','NL2',1.00,'kg'),('CT2','NL1',3.00,'kg');
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
INSERT INTO `chitiethoadon` VALUES ('HD001','TA001',2,300.00),('HD001','TA002',1,200.00),('HD002','TA002',3,450.00);
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
  `maSanPham` varchar(20) NOT NULL,
  `giaTriKhuyenMai` float DEFAULT NULL,
  PRIMARY KEY (`maKhuyenMai`,`maSanPham`),
  KEY `chitietkhuyenmai_ibfk_2_idx` (`maSanPham`),
  CONSTRAINT `chitietkhuyenmai_ibfk_1` FOREIGN KEY (`maKhuyenMai`) REFERENCES `khuyenmai` (`maKhuyenMai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietkhuyenmai`
--

LOCK TABLES `chitietkhuyenmai` WRITE;
/*!40000 ALTER TABLE `chitietkhuyenmai` DISABLE KEYS */;
INSERT INTO `chitietkhuyenmai` VALUES ('KM001','HD001',10),('KM002','HD002',20000);
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
INSERT INTO `chitietphieunhap` VALUES ('PN001','NL1',10,15.00,150.00),('PN001','NL2',5,20.00,100.00),('PN002','NL1',8,15.00,120.00);
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
  `trangThai` varchar(50) DEFAULT NULL,
  `luongTheoGio` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`maChucVu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chucvu`
--

LOCK TABLES `chucvu` WRITE;
/*!40000 ALTER TABLE `chucvu` DISABLE KEYS */;
INSERT INTO `chucvu` VALUES ('CV1','Nhân viên','Active',50.00),('CV2','Quản lý','Active',100.00);
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
INSERT INTO `congthuc` VALUES ('CT1','Công thức Phở','Công thức truyền thống cho Phở bò'),('CT2','Công thức Bún chả','Công thức đặc trưng của Hà Nội');
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
  PRIMARY KEY (`maHoaDon`),
  KEY `maNhanVien` (`maNhanVien`),
  KEY `maKhachHang` (`maKhachHang`),
  CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`maNhanVien`) REFERENCES `nhanvien` (`maNhanVien`),
  CONSTRAINT `hoadon_ibfk_2` FOREIGN KEY (`maKhachHang`) REFERENCES `khachhang` (`maKH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hoadon`
--

LOCK TABLES `hoadon` WRITE;
/*!40000 ALTER TABLE `hoadon` DISABLE KEYS */;
INSERT INTO `hoadon` VALUES ('HD001','2023-03-15','NV001','KH001',500.00),('HD002','2023-03-16','NV002','KH002',750.00);
/*!40000 ALTER TABLE `hoadon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang` (
  `maKH` varchar(20) NOT NULL,
  `tenKH` varchar(100) DEFAULT NULL,
  `SDT` int DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `diaChi` varchar(255) DEFAULT NULL,
  `soDiem` int DEFAULT NULL,
  `tenDangNhap` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`maKH`),
  KEY `tenDangNhap` (`tenDangNhap`),
  CONSTRAINT `khachhang_ibfk_1` FOREIGN KEY (`tenDangNhap`) REFERENCES `taikhoan` (`tenDangNhap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang`
--

LOCK TABLES `khachhang` WRITE;
/*!40000 ALTER TABLE `khachhang` DISABLE KEYS */;
INSERT INTO `khachhang` VALUES ('KH001','Lê Văn C',912345123,'c@example.com','Hà Nội',100,'user1'),('KH002','Phạm Thị D',987651234,'d@example.com','HCM',200,'admin');
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
  PRIMARY KEY (`maKhuyenMai`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyenmai`
--

LOCK TABLES `khuyenmai` WRITE;
/*!40000 ALTER TABLE `khuyenmai` DISABLE KEYS */;
INSERT INTO `khuyenmai` VALUES ('KM001','Khuyến mãi A','Phần trăm','2023-03-01','2023-03-31','Áp dụng cho hóa đơn trên 1000'),('KM002','Khuyến mãi B','Số tiền','2023-04-01','2023-04-30','Áp dụng cho hóa đơn trên 2000');
/*!40000 ALTER TABLE `khuyenmai` ENABLE KEYS */;
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
  PRIMARY KEY (`maNguyenLieu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nguyenlieu`
--

LOCK TABLES `nguyenlieu` WRITE;
/*!40000 ALTER TABLE `nguyenlieu` DISABLE KEYS */;
INSERT INTO `nguyenlieu` VALUES ('NL1','Bánh phở','Nguyên liệu chính','2023-01-01','2023-12-31',100.00,'kg'),('NL2','Thịt bò','Thịt','2023-01-15','2023-12-31',50.00,'kg');
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
  `SDT` int DEFAULT NULL,
  `diaChi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`maNhaCungCap`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhacungcap`
--

LOCK TABLES `nhacungcap` WRITE;
/*!40000 ALTER TABLE `nhacungcap` DISABLE KEYS */;
INSERT INTO `nhacungcap` VALUES ('NCC001','Công ty TNHH ABC','contact@abc.com',123456789,'123 Đường A, Hà Nội'),('NCC002','Công ty TNHH XYZ','info@xyz.com',987654321,'456 Đường B, HCM');
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
  `SDT` int DEFAULT NULL,
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
INSERT INTO `nhanvien` VALUES ('NV001','Nguyễn Văn A',912345678,'a@example.com','1990-05-01','Nam','Hà Nội','Active','CV1','user1'),('NV002','Trần Thị B',987654321,'b@example.com','1992-08-15','Nữ','HCM','Active','CV2','admin');
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
INSERT INTO `phanphoi` VALUES ('NCC001','NL1',15.00),('NCC002','NL2',20.00);
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
INSERT INTO `phieunhap` VALUES ('PN001','2023-02-15','NV001','NCC001',250.00),('PN002','2023-02-16','NV002','NCC002',120.00);
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
INSERT INTO `taikhoan` VALUES ('admin','adminpass','Active','2023-01-01','Admin'),('user1','pass1','Active','2023-01-01','Employee');
/*!40000 ALTER TABLE `taikhoan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thucan`
--

DROP TABLE IF EXISTS `thucan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thucan` (
  `maThucAn` varchar(20) NOT NULL,
  `tenThucAn` varchar(100) DEFAULT NULL,
  `moTa` varchar(255) DEFAULT NULL,
  `loaiMonAn` varchar(50) DEFAULT NULL,
  `gia` decimal(10,2) DEFAULT NULL,
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
INSERT INTO `thucan` VALUES ('TA001','Phở bò','Phở bò thơm ngon','Phở',150.00,'CT1'),('TA002','Bún chả','Bún chả chuẩn Hà Nội','Bún',200.00,'CT2');
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

-- Dump completed on 2025-03-29  4:01:00
