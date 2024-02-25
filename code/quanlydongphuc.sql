-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 25, 2024 at 01:05 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanlydongphuc`
--

-- --------------------------------------------------------

--
-- Table structure for table `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `maCTDH` varchar(10) NOT NULL,
  `maDH` varchar(10) NOT NULL,
  `maSP` varchar(10) NOT NULL,
  `soLuongMua` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`maCTDH`, `maDH`, `maSP`, `soLuongMua`) VALUES
('CT1', 'DH1', 'SP1', 1),
('CT2', 'DH1', 'SP26', 1);

-- --------------------------------------------------------

--
-- Table structure for table `donhang`
--

CREATE TABLE `donhang` (
  `maDH` varchar(10) NOT NULL,
  `maKH` varchar(10) NOT NULL,
  `hoTen` varchar(50) NOT NULL,
  `soDT` varchar(15) NOT NULL,
  `diaChi` varchar(50) NOT NULL,
  `ghiChu` text NOT NULL,
  `trangThai` varchar(20) NOT NULL,
  `tongTien` double NOT NULL,
  `ngayDat` varchar(20) NOT NULL,
  `loaiDon` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `donhang`
--

INSERT INTO `donhang` (`maDH`, `maKH`, `hoTen`, `soDT`, `diaChi`, `ghiChu`, `trangThai`, `tongTien`, `ngayDat`, `loaiDon`) VALUES
('DH1', 'KH3', 'Nguyễn Văn A', '0382952063', 'Bắc Ninh', 'Giao hàng nhanh', 'Chờ duyệt', 220000, '2024-02-24', '0');

-- --------------------------------------------------------

--
-- Table structure for table `giohang`
--

CREATE TABLE `giohang` (
  `maGioHang` varchar(10) NOT NULL,
  `maKH` varchar(10) NOT NULL,
  `maSP` varchar(10) NOT NULL,
  `soLuongDat` int(11) NOT NULL,
  `ngayTao` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `giohang`
--

INSERT INTO `giohang` (`maGioHang`, `maKH`, `maSP`, `soLuongDat`, `ngayTao`) VALUES
('GH1', 'KH3', 'SP11', 1, '2024-02-24');

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `maKH` varchar(10) NOT NULL,
  `hoTen` varchar(30) DEFAULT NULL,
  `ngaySinh` varchar(30) DEFAULT NULL,
  `diaChi` varchar(50) DEFAULT NULL,
  `soDT` varchar(15) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `Khoa` varchar(50) DEFAULT NULL,
  `Lop` varchar(30) DEFAULT NULL,
  `avatar` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`maKH`, `hoTen`, `ngaySinh`, `diaChi`, `soDT`, `email`, `Khoa`, `Lop`, `avatar`) VALUES
('KH01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
('KH3', 'Nguyễn Văn A', '31/10/2002', 'Bắc Ninh', '0382952063', 'viethai31102002@gmail.com', 'CNTT', 'KTPM01', 'logo.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `phanhoi`
--

CREATE TABLE `phanhoi` (
  `maPH` varchar(10) NOT NULL,
  `maKH` varchar(10) NOT NULL,
  `hinhAnh` varchar(20) NOT NULL,
  `diemDG` varchar(10) NOT NULL,
  `nhanXet` text NOT NULL,
  `maDH` varchar(30) NOT NULL,
  `ngayTao` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `maSP` varchar(10) NOT NULL,
  `tenSP` varchar(30) NOT NULL,
  `mau` varchar(30) NOT NULL,
  `size` varchar(10) NOT NULL,
  `donGia` double NOT NULL,
  `soLuongCon` int(11) NOT NULL,
  `avatar` varchar(30) NOT NULL,
  `loai` varchar(30) NOT NULL,
  `gioiTinh` varchar(30) NOT NULL,
  `tinhTrang` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`maSP`, `tenSP`, `mau`, `size`, `donGia`, `soLuongCon`, `avatar`, `loai`, `gioiTinh`, `tinhTrang`) VALUES
('SP1', 'Áo cộc', 'Trắng', 'S', 100000, 99, 'aococ (1).png', 'Mua', 'Nam', 'Đang bán'),
('SP10', 'Áo cộc', 'Trắng', '2XL', 100000, 100, 'aococ (1).png', 'Mua', 'Nữ', 'Đang bán'),
('SP11', 'Áo khoác', 'Đỏ', 'S', 120000, 100, 'Áo khoác (1).jpg', 'Mua', 'Nam', 'Đang bán'),
('SP12', 'Áo khoác', 'Đỏ', 'M', 120000, 100, 'Áo khoác (1).jpg', 'Mua', 'Nam', 'Đang bán'),
('SP13', 'Áo khoác', 'Đỏ', 'L', 120000, 100, 'Áo khoác (1).jpg', 'Mua', 'Nam', 'Đang bán'),
('SP14', 'Áo khoác', 'Đỏ', 'XL', 120000, 100, 'Áo khoác (1).jpg', 'Mua', 'Nam', 'Đang bán'),
('SP15', 'Áo khoác', 'Đỏ', '2XL', 120000, 100, 'Áo khoác (1).jpg', 'Mua', 'Nam', 'Đang bán'),
('SP16', 'Áo khoác', 'Đỏ', 'S', 120000, 50, 'Áo khoác (1).jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP17', 'Áo khoác', 'Đỏ', 'M', 120000, 50, 'Áo khoác (1).jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP18', 'Áo khoác', 'Đỏ', 'L', 120000, 50, 'Áo khoác (1).jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP19', 'Áo khoác', 'Đỏ', 'XL', 120000, 50, 'Áo khoác (1).jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP2', 'Áo cộc', 'Trắng', 'M', 100000, 100, 'aococ (1).png', 'Mua', 'Nam', 'Đang bán'),
('SP20', 'Áo khoác', 'Đỏ', '2XL', 120000, 50, 'Áo khoác (1).jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP21', 'Áo thực hành', 'Xanh', 'S', 120000, 100, 'thực hành.jpg', 'Mua', 'Nam', 'Đang bán'),
('SP22', 'Áo thực hành', 'Xanh', 'M', 120000, 100, 'thực hành.jpg', 'Mua', 'Nam', 'Đang bán'),
('SP23', 'Áo thực hành', 'Xanh', 'L', 120000, 100, 'thực hành.jpg', 'Mua', 'Nam', 'Đang bán'),
('SP24', 'Áo thực hành', 'Xanh', 'XL', 120000, 100, 'thực hành.jpg', 'Mua', 'Nam', 'Đang bán'),
('SP25', 'Áo thực hành', 'Xanh', '2XL', 120000, 100, 'thực hành.jpg', 'Mua', 'Nam', 'Đang bán'),
('SP26', 'Áo thực hành', 'Xanh', 'S', 120000, 119, 'thực hành.jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP27', 'Áo thực hành', 'Xanh', 'M', 120000, 120, 'thực hành.jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP28', 'Áo thực hành', 'Xanh', 'L', 120000, 120, 'thực hành.jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP29', 'Áo thực hành', 'Xanh', 'XL', 120000, 120, 'thực hành.jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP3', 'Áo cộc', 'Trắng', 'L', 100000, 100, 'aococ (1).png', 'Mua', 'Nam', 'Đang bán'),
('SP30', 'Áo thực hành', 'Xanh', '2XL', 120000, 120, 'thực hành.jpg', 'Mua', 'Nữ', 'Đang bán'),
('SP4', 'Áo cộc', 'Trắng', 'XL', 100000, 100, 'aococ (1).png', 'Mua', 'Nam', 'Đang bán'),
('SP5', 'Áo cộc', 'Trắng', '2XL', 100000, 100, 'aococ (1).png', 'Mua', 'Nam', 'Đang bán'),
('SP6', 'Áo cộc', 'Trắng', 'S', 100000, 100, 'aococ (1).png', 'Mua', 'Nữ', 'Đang bán'),
('SP7', 'Áo cộc', 'Trắng', 'M', 100000, 100, 'aococ (1).png', 'Mua', 'Nữ', 'Đang bán'),
('SP8', 'Áo cộc', 'Trắng', 'L', 100000, 100, 'aococ (1).png', 'Mua', 'Nữ', 'Đang bán'),
('SP9', 'Áo cộc', 'Trắng', 'XL', 100000, 100, 'aococ (1).png', 'Mua', 'Nữ', 'Đang bán');

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `tenTK` varchar(10) NOT NULL,
  `matKhau` varchar(20) NOT NULL,
  `role` varchar(10) NOT NULL,
  `tinhTrang` varchar(30) NOT NULL,
  `maKH` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`tenTK`, `matKhau`, `role`, `tinhTrang`, `maKH`) VALUES
('admin', '123456', '1', 'Enable', 'KH01'),
('user', '123456', '0', 'Enable', 'KH3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`maCTDH`),
  ADD KEY `fk_ctdh_sp` (`maSP`),
  ADD KEY `fk_ctdh_dh` (`maDH`);

--
-- Indexes for table `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`maDH`),
  ADD KEY `fk_dh_kh` (`maKH`);

--
-- Indexes for table `giohang`
--
ALTER TABLE `giohang`
  ADD PRIMARY KEY (`maGioHang`),
  ADD KEY `fk_gh_kh` (`maKH`),
  ADD KEY `fk_gh_sp` (`maSP`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`maKH`);

--
-- Indexes for table `phanhoi`
--
ALTER TABLE `phanhoi`
  ADD PRIMARY KEY (`maPH`),
  ADD KEY `fk_ph_kh` (`maKH`),
  ADD KEY `fk_ph_dh` (`maDH`);

--
-- Indexes for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`maSP`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`tenTK`),
  ADD KEY `fk_tk_kh` (`maKH`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `fk_ctdh_dh` FOREIGN KEY (`maDH`) REFERENCES `donhang` (`maDH`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_ctdh_sp` FOREIGN KEY (`maSP`) REFERENCES `sanpham` (`maSP`) ON DELETE CASCADE;

--
-- Constraints for table `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `fk_dh_kh` FOREIGN KEY (`maKH`) REFERENCES `khachhang` (`maKH`);

--
-- Constraints for table `giohang`
--
ALTER TABLE `giohang`
  ADD CONSTRAINT `fk_gh_kh` FOREIGN KEY (`maKH`) REFERENCES `khachhang` (`maKH`),
  ADD CONSTRAINT `fk_gh_sp` FOREIGN KEY (`maSP`) REFERENCES `sanpham` (`maSP`);

--
-- Constraints for table `phanhoi`
--
ALTER TABLE `phanhoi`
  ADD CONSTRAINT `fk_ph_dh` FOREIGN KEY (`maDH`) REFERENCES `donhang` (`maDH`),
  ADD CONSTRAINT `fk_ph_kh` FOREIGN KEY (`maKH`) REFERENCES `khachhang` (`maKH`);

--
-- Constraints for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `fk_tk_kh` FOREIGN KEY (`maKH`) REFERENCES `khachhang` (`maKH`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
