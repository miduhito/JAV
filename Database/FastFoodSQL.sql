-- =============================================
-- 1. Bảng CaLam
-- =============================================
CREATE TABLE CaLam (
    maCa VARCHAR(20) PRIMARY KEY,
    moTa VARCHAR(255),
    gioBD VARCHAR(10),
    gioKT VARCHAR(10),
    trangThai VARCHAR(50)
);

INSERT INTO CaLam (maCa, moTa, gioBD, gioKT, trangThai)
VALUES
('C1', 'Sáng', '07:00', '11:00', 'Active'),
('C2', 'Trưa', '12:00', '16:00', 'Active'),
('C3', 'Tối', '17:00', '21:00', 'Inactive');

-- =============================================
-- 2. Bảng CongThuc (Công thức)
-- =============================================
CREATE TABLE CongThuc (
    maCongThuc VARCHAR(20) PRIMARY KEY,
    tenCongThuc VARCHAR(100),
    moTa VARCHAR(255)
);

INSERT INTO CongThuc (maCongThuc, tenCongThuc, moTa)
VALUES
('CT1', 'Công thức Phở', 'Công thức truyền thống cho Phở bò'),
('CT2', 'Công thức Bún chả', 'Công thức đặc trưng của Hà Nội');

-- =============================================
-- 3. Bảng NguyenLieu (Nguyên liệu)
-- =============================================
CREATE TABLE NguyenLieu (
    maNguyenLieu VARCHAR(20) PRIMARY KEY,
    tenNguyenLieu VARCHAR(100),
    loaiNguyenLieu VARCHAR(50),
    ngayNhap DATE,
    ngayHetHan DATE,
    soLuong DECIMAL(10,2),
    donViDo VARCHAR(50)
);

INSERT INTO NguyenLieu (maNguyenLieu, tenNguyenLieu, loaiNguyenLieu, ngayNhap, ngayHetHan, soLuong, donViDo)
VALUES
('NL1', 'Bánh phở', 'Nguyên liệu chính', '2023-01-01', '2023-12-31', 100.00, 'kg'),
('NL2', 'Thịt bò', 'Thịt', '2023-01-15', '2023-12-31', 50.00, 'kg');

-- =============================================
-- 4. Bảng ChiTietCongThuc
-- =============================================
CREATE TABLE ChiTietCongThuc (
    maCongThuc VARCHAR(20),
    maNguyenLieu VARCHAR(20),
    soLuong DECIMAL(10,2),
    donViTinh VARCHAR(50),
    PRIMARY KEY (maCongThuc, maNguyenLieu),
    FOREIGN KEY (maCongThuc) REFERENCES CongThuc(maCongThuc),
    FOREIGN KEY (maNguyenLieu) REFERENCES NguyenLieu(maNguyenLieu)
);

INSERT INTO ChiTietCongThuc (maCongThuc, maNguyenLieu, soLuong, donViTinh)
VALUES
('CT1', 'NL1', 2.5, 'kg'),
('CT1', 'NL2', 1.0, 'kg'),
('CT2', 'NL1', 3.0, 'kg');

-- =============================================
-- 5. Bảng ChucVu (Chức vụ)
-- =============================================
CREATE TABLE ChucVu (
    maChucVu VARCHAR(20) PRIMARY KEY,
    tenChucVu VARCHAR(100),
    trangThai VARCHAR(50),
    luongTheoGio DECIMAL(10,2)
);

INSERT INTO ChucVu (maChucVu, tenChucVu, trangThai, luongTheoGio)
VALUES
('CV1', 'Nhân viên', 'Active', 50.00),
('CV2', 'Quản lý', 'Active', 100.00);

-- =============================================
-- 6. Bảng TaiKhoan (Tài khoản)
-- =============================================
CREATE TABLE TaiKhoan (
    tenDangNhap VARCHAR(50) PRIMARY KEY,
    matKhau VARCHAR(255),
    trangThai VARCHAR(50),
    ngayTao DATE,
    vaiTro VARCHAR(50)
);

INSERT INTO TaiKhoan (tenDangNhap, matKhau, trangThai, ngayTao, vaiTro)
VALUES
('user1', 'pass1', 'Active', '2023-01-01', 'Employee'),
('admin', 'adminpass', 'Active', '2023-01-01', 'Admin');

-- =============================================
-- 7. Bảng NhanVien (Nhân viên)
-- =============================================
CREATE TABLE NhanVien (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    tenNhanVien VARCHAR(100),
    SDT INT,
    email VARCHAR(100),
    ngaySinh DATE,
    gioiTinh VARCHAR(10),
    diaChi VARCHAR(255),
    trangThai VARCHAR(50),
    maChucVu VARCHAR(20),
    tenDangNhap VARCHAR(50),
    FOREIGN KEY (maChucVu) REFERENCES ChucVu(maChucVu),
    FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap)
);

INSERT INTO NhanVien (maNhanVien, tenNhanVien, SDT, email, ngaySinh, gioiTinh, diaChi, trangThai, maChucVu, tenDangNhap)
VALUES
('NV001', 'Nguyễn Văn A', 912345678, 'a@example.com', '1990-05-01', 'Nam', 'Hà Nội', 'Active', 'CV1', 'user1'),
('NV002', 'Trần Thị B', 987654321, 'b@example.com', '1992-08-15', 'Nữ', 'HCM', 'Active', 'CV2', 'admin');

-- =============================================
-- 8. Bảng HoaDon (Hóa đơn)
-- =============================================
CREATE TABLE HoaDon (
    maHoaDon VARCHAR(20) PRIMARY KEY,
    ngayLap DATE,
    maNhanVien VARCHAR(20),
    maKhachHang VARCHAR(20),
    tongTien DECIMAL(10,2),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKH)
);

INSERT INTO HoaDon (maHoaDon, ngayLap, maNhanVien, maKhachHang, tongTien)
VALUES
('HD001', '2023-03-15', 'NV001', 'KH001', 500.00),
('HD002', '2023-03-16', 'NV002', 'KH002', 750.00);

-- =============================================
-- 9. Bảng KhachHang (Khách hàng)
-- =============================================
CREATE TABLE KhachHang (
    maKH VARCHAR(20) PRIMARY KEY,
    tenKH VARCHAR(100),
    SDT INT,
    email VARCHAR(100),
    diaChi VARCHAR(255),
    soDiem INT,
    tenDangNhap VARCHAR(50),
    FOREIGN KEY (tenDangNhap) REFERENCES TaiKhoan(tenDangNhap)
);

INSERT INTO KhachHang (maKH, tenKH, SDT, email, diaChi, soDiem, tenDangNhap)
VALUES
('KH001', 'Lê Văn C', 912345123, 'c@example.com', 'Hà Nội', 100, 'user1'),
('KH002', 'Phạm Thị D', 987651234, 'd@example.com', 'HCM', 200, 'admin');

-- =============================================
-- 10. Bảng ChiTietHoaDon (Chi tiết hóa đơn)
-- =============================================
CREATE TABLE ChiTietHoaDon (
    maHoaDon VARCHAR(20),
    maThucAn VARCHAR(20),
    soLuongBan INT,
    thanhTien DECIMAL(10,2),
    PRIMARY KEY (maHoaDon, maThucAn),
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maThucAn) REFERENCES ThucAn(maThucAn)
);

INSERT INTO ChiTietHoaDon (maHoaDon, maThucAn, soLuongBan, thanhTien)
VALUES
('HD001', 'TA001', 2, 300.00),
('HD001', 'TA002', 1, 200.00),
('HD002', 'TA002', 3, 450.00);

-- =============================================
-- 11. Bảng KhuyenMai (Khuyến mãi)
-- =============================================
CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(20) PRIMARY KEY,
    tenKhuyenMai VARCHAR(100),
    hinhThucKhuyenMai VARCHAR(50),
    ngayBatDau DATE,
    ngayKetThuc DATE,
    giaTriKhuyenMai DECIMAL(10,2),
    dieuKienApDung VARCHAR(255)
);

INSERT INTO KhuyenMai (maKhuyenMai, tenKhuyenMai, hinhThucKhuyenMai, ngayBatDau, ngayKetThuc, giaTriKhuyenMai, dieuKienApDung)
VALUES
('KM001', 'Khuyến mãi A', 'Phần trăm', '2023-03-01', '2023-03-31', 10.00, 'Áp dụng cho hóa đơn trên 1000'),
('KM002', 'Khuyến mãi B', 'Số tiền', '2023-04-01', '2023-04-30', 50.00, 'Áp dụng cho hóa đơn trên 2000');

-- =============================================
-- 12. Bảng ChiTietKhuyenMai (Chi tiết khuyến mãi)
-- =============================================
CREATE TABLE ChiTietKhuyenMai (
    maKhuyenMai VARCHAR(20),
    maHoaDon VARCHAR(20),
    PRIMARY KEY (maKhuyenMai, maHoaDon),
    FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai),
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon)
);

INSERT INTO ChiTietKhuyenMai (maKhuyenMai, maHoaDon)
VALUES
('KM001', 'HD001'),
('KM002', 'HD002');

-- =============================================
-- 13. Bảng PhieuNhap (Phiếu nhập)
-- =============================================
CREATE TABLE PhieuNhap (
    maPhieuNhap VARCHAR(20) PRIMARY KEY,
    ngayNhap DATE,
    maNhanVien VARCHAR(20),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
);

INSERT INTO PhieuNhap (maPhieuNhap, ngayNhap, maNhanVien)
VALUES
('PN001', '2023-02-15', 'NV001'),
('PN002', '2023-02-16', 'NV002');

-- =============================================
-- 14. Bảng ChiTietPhieuNhap (Chi tiết phiếu nhập)
-- =============================================
CREATE TABLE ChiTietPhieuNhap (
    maPhieuNhap VARCHAR(20),
    maNguyenLieu VARCHAR(20),
    soLuongNhap INT,
    giaNhap DECIMAL(10,2),
    thanhTien DECIMAL(10,2),
    PRIMARY KEY (maPhieuNhap, maNguyenLieu),
    FOREIGN KEY (maPhieuNhap) REFERENCES PhieuNhap(maPhieuNhap),
    FOREIGN KEY (maNguyenLieu) REFERENCES NguyenLieu(maNguyenLieu)
);

INSERT INTO ChiTietPhieuNhap (maPhieuNhap, maNguyenLieu, soLuongNhap, giaNhap, thanhTien)
VALUES
('PN001', 'NL1', 10, 15.00, 150.00),
('PN001', 'NL2', 5, 20.00, 100.00),
('PN002', 'NL1', 8, 15.00, 120.00);

-- =============================================
-- 15. Bảng ThucAn (Thức ăn)
-- =============================================
CREATE TABLE ThucAn (
    maThucAn VARCHAR(20) PRIMARY KEY,
    tenThucAn VARCHAR(100),
    moTa VARCHAR(255),
    loaiMonAn VARCHAR(50),
    gia DECIMAL(10,2),
    maCongThuc VARCHAR(20),
    FOREIGN KEY (maCongThuc) REFERENCES CongThuc(maCongThuc)
);

INSERT INTO ThucAn (maThucAn, tenThucAn, moTa, loaiMonAn, gia, maCongThuc)
VALUES
('TA001', 'Phở bò', 'Phở bò thơm ngon', 'Phở', 150.00, 'CT1'),
('TA002', 'Bún chả', 'Bún chả chuẩn Hà Nội', 'Bún', 200.00, 'CT2');

-- =============================================
-- 16. Bảng NhaCungCap (Nhà cung cấp)
-- =============================================
CREATE TABLE NhaCungCap (
    maNhaCungCap VARCHAR(20) PRIMARY KEY,
    tenNhaCungCap VARCHAR(100),
    email VARCHAR(100),
    SDT INT,
    diaChi VARCHAR(255)
);

INSERT INTO NhaCungCap (maNhaCungCap, tenNhaCungCap, email, SDT, diaChi)
VALUES
('NCC001', 'Công ty TNHH ABC', 'contact@abc.com', 123456789, '123 Đường A, Hà Nội'),
('NCC002', 'Công ty TNHH XYZ', 'info@xyz.com', 987654321, '456 Đường B, HCM');