-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 08, 2025 at 05:18 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `chdidongg`
--

-- --------------------------------------------------------

--
-- Table structure for table `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `idHD` int(11) NOT NULL,
  `idCTSP` int(11) NOT NULL,
  `SOLUONG` int(11) NOT NULL,
  `GIABAN` decimal(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `chitietkhuyenmai`
--

CREATE TABLE `chitietkhuyenmai` (
  `idHD` int(11) NOT NULL,
  `MAKHUYENMAI` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `chitietphieunhap`
--

CREATE TABLE `chitietphieunhap` (
  `idPN` int(11) NOT NULL,
  `idCTSP` int(11) NOT NULL,
  `SOLUONG` int(11) NOT NULL,
  `GIANHAP` decimal(10,2) NOT NULL,
  `DIEUCHINHGIA` decimal(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitietphieunhap`
--

INSERT INTO `chitietphieunhap` (`idPN`, `idCTSP`, `SOLUONG`, `GIANHAP`, `DIEUCHINHGIA`) VALUES
(1, 1, 10, 50000.00, 0.00),
(1, 2, 5, 100000.00, 5000.00),
(1, 3, 20, 25000.00, 0.00),
(1, 4, 3, 250000.00, 0.00),
(1, 5, 8, 75000.00, 1000.00),
(1, 6, 12, 40000.00, 0.00),
(1, 7, 15, 60000.00, 2000.00),
(1, 8, 6, 80000.00, 0.00),
(1, 9, 9, 90000.00, 0.00),
(1, 10, 4, 120000.00, 3000.00);

-- --------------------------------------------------------

--
-- Table structure for table `chitietsanpham`
--

CREATE TABLE `chitietsanpham` (
  `idCTSP` int(11) NOT NULL,
  `idSP` int(11) NOT NULL,
  `MAUSAC` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `DUNGLUONG` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `DIEUCHINHGIA` float DEFAULT 0,
  `IMG` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `chitietsanpham`
--

INSERT INTO `chitietsanpham` (`idCTSP`, `idSP`, `MAUSAC`, `DUNGLUONG`, `DIEUCHINHGIA`, `IMG`) VALUES
(1, 1, 'Đen', '128GB', 0, 'iphone-16-den.jpg'),
(2, 1, 'Đen', '256GB', 3000000, 'iphone-16-den.jpg'),
(3, 1, 'Đen', '512GB', 6000000, 'iphone-16-den.jpg'),
(4, 1, 'Trắng', '128GB', 0, 'iphone-16-trang.jpg'),
(5, 1, 'Trắng', '256GB', 3000000, 'iphone-16-trang.jpg'),
(6, 1, 'Xanh', '128GB', 0, 'iphone-16-xanh-luu-ly.jpg'),
(7, 1, 'Xanh', '256GB', 3000000, 'iphone-16-xanh-luu-ly.jpg'),
(8, 2, 'Đen', '128GB', 3000000, 'iphone-16-den.jpg'),
(9, 2, 'Đen', '256GB', 0, 'iphone-16-den.jpg'),
(10, 2, 'Đen', '512GB', 3000000, 'iphone-16-den.jpg'),
(11, 2, 'Trắng', '128GB', -3000000, 'iphone-16-trang.jpg'),
(12, 2, 'Trắng', '256GB', 0, 'iphone-16-trang.jpg'),
(13, 2, 'tea', '128GB', -3000000, 'iphone-16-teal.jpg'),
(14, 2, 'Xanh dương', '128GB', -3000000, 'iPhone-16-Ultramarine1.jpg'),
(15, 2, 'Xanh lá', '128GB', -3000000, 'Iphone 16.jpg'),
(31, 3, 'Đen', '256GB', 0, 'SamSung Galaxy Z Flip 6.jpg'),
(32, 3, 'Đen', '512GB', 4000000, 'SamSung Galaxy Z Flip 6.jpg'),
(33, 3, 'Xanh', '256GB', 0, 'samsung-galaxy-z-flip6-mint.jpg'),
(34, 3, 'Xanh', '512GB', 4000000, 'samsung-galaxy-z-flip6-mint.jpg'),
(35, 3, 'Vàng', '256GB', 0, 'samsung-galaxy-z-flip6-yellow.jpg'),
(36, 4, 'Đen Titan', '256GB', 0, 'iphone-16-pro-max-titan-den.jpg'),
(37, 4, 'Đen Titan', '512GB', 5000000, 'iphone-16-pro-max-titan-den.jpg'),
(38, 4, 'Đen Titan', '1TB', 10000000, 'iphone-16-pro-max-titan-den.jpg'),
(39, 4, 'Trắng Titan', '256GB', 0, 'iphone-16-pro-max-titan-trang.jpg'),
(40, 4, 'Trắng Titan', '512GB', 5000000, 'iphone-16-pro-max-titan-trang.jpg'),
(41, 4, 'Vàng Titan', '256GB', 0, 'iphone-16-pro-max-titan-sa-mac.jpg'),
(68, 5, 'Trắng', 'KHÔNG CÓ', 500000, 'Airpods pro 2_Trắng.png'),
(69, 5, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(70, 6, 'Xám', '128GB', 0, 'samsung-galaxy-s21-xam.jpg'),
(71, 6, 'Xám', '256GB', 2000000, 'samsung-galaxy-s21-xam.jpg'),
(72, 6, 'Trắng', '128GB', 0, 'samsung-galaxy-s21-trang.jpg'),
(73, 6, 'Trắng', '256GB', 2000000, 'samsung-galaxy-s21-trang.jpg'),
(74, 6, 'Tím', '128GB', 0, 'samsung-galaxy-s21-tim.jpg'),
(75, 6, 'Tím', '256GB', 2000000, 'samsung-galaxy-s21-tim.jpg'),
(76, 7, 'Đen', '128GB', 0, 'iphone-13-den.jpg'),
(77, 7, 'Đen', '256GB', 3000000, 'iphone-13-den.jpg'),
(78, 7, 'Trắng', '128GB', 0, 'iphone-13-trang.jpg'),
(79, 7, 'Đen', '512GB', 0, 'iphone-13-den.jpg'),
(80, 7, 'Trắng', '256GB', 0, 'iphone-13-trang.jpg'),
(81, 8, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(82, 8, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(83, 8, 'Vàng', 'KHÔNG CÓ', 0, '.jpg'),
(84, 9, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(85, 9, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(86, 10, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(87, 10, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(123, 19, 'Đen', 'KHÔNG CÓ', 0, 'Cáp sạc Type C Zmi AL303-AL873_Đen.jpg'),
(124, 19, 'Đen', 'KHÔNG CÓ', 50000, '.jpg'),
(125, 19, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(126, 19, 'Trắng', 'KHÔNG CÓ', 50000, 'Cáp sạc Type C Zmi AL303-AL873_Trắng.jpg'),
(127, 20, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(128, 20, 'Đen', 'KHÔNG CÓ', 30000, '.jpg'),
(129, 20, 'Đen', 'KHÔNG CÓ', 50000, '.jpg'),
(130, 21, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(131, 21, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(132, 22, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(133, 22, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(134, 23, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(135, 23, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(136, 24, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(137, 24, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(138, 25, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(139, 25, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(140, 25, 'Vàng', 'KHÔNG CÓ', 0, '.jpg'),
(141, 26, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(142, 26, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(143, 26, 'Đen', 'KHÔNG CÓ', 0, '.jpg'),
(144, 26, 'Trắng', 'KHÔNG CÓ', 0, '.jpg'),
(145, 27, 'Đen', '128GB', 0, 'xiaomi-redmi-note-14-black.jpg'),
(146, 27, 'Trắng', '128GB', 0, 'xiaomi-redmi-note-14-purple.jpg'),
(147, 27, 'Xanh', '128GB', 0, 'xiaomi-redmi-note-14-xanh.jpg'),
(148, 27, 'Xạnh', '256GB', 0, 'xiaomi-redmi-note-14-xanh.jpg'),
(149, 27, 'Trắng', '256GB', 0, 'xiaomi-redmi-note-14-purple.jpg'),
(150, 28, 'Đen', '256GB', 0, 'xiaomi-redmi-note-14-5g-black.jpg'),
(151, 28, 'Xanh', '256GB', 0, 'xiaomi-redmi-note-14-5g-green.jpg'),
(152, 28, 'Tím', '128GB', 0, 'xiaomi-redmi-note-14-5g-purple.jpg'),
(153, 28, 'Tím', '256GB', 0, 'xiaomi-redmi-note-14-5g-purple.jpg'),
(154, 28, 'Xanh', '512GB', 0, 'xiaomi-redmi-note-14-5g-green.jpg'),
(155, 29, 'Đen', '128GB', 0, '.jpg'),
(156, 29, 'Xanh', '128GB', 0, '.jpg'),
(157, 29, 'Trắng', '128GB', 0, '.jpg'),
(158, 29, 'Tím', '128GB', 0, '.jpg'),
(159, 29, 'Xám', '128GB', 0, '.jpg'),
(160, 30, 'Đen', '128GB', 0, 'xiaomi-redmi-note-13-pro-den.jpg'),
(161, 30, 'Xanh', '128GB', 0, 'xiaomi-redmi-note-13-pro-xanh.jpg'),
(162, 30, 'Xanh Lam', '128GB', 0, 'xiaomi-redmi-note-13-pro-5g-xalanh.jpg'),
(163, 30, 'Tím', '128GB', 0, 'xiaomi-redmi-note13-pro-tim.jpg'),
(165, 105, 'Trắng', '256GB', 10000, 'iPhone 16e_Trắng_256GB.jpg'),
(166, 105, 'Đen', '1TB', 100000, 'iPhone 16e_Đen_1TB.jpg'),
(167, 105, 'Đen', '128GB', 0, 'iPhone 16e_Đen_128GB.jpg'),
(168, 105, 'Trắng', '1TB', 0, 'iPhone 16e_Trắng_1TB.jpg'),
(169, 105, 'Trắng', '128GB', 0, 'iPhone 16e_Trắng_128GB.jpg'),
(170, 106, 'Trắng', '128GB', 0, 'iPhone 16 SE_Trắng_128GB.jpg'),
(171, 91, 'Trắng', 'KHÔNG CÓ', 0, '.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `chucnang`
--

CREATE TABLE `chucnang` (
  `idCN` int(11) NOT NULL,
  `TENCN` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ICON` varchar(255) NOT NULL,
  `TRANGTHAI` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chucnang`
--

INSERT INTO `chucnang` (`idCN`, `TENCN`, `ICON`, `TRANGTHAI`) VALUES
(1, 'Tài khoản', 'icon tài khoản ( chức năng ).png', 1),
(2, 'Nhân viên', 'icon nhân viên.png', 1),
(3, 'Phân quyền', 'icon phân quyền.png', 1),
(4, 'Sản phẩm', 'icon sản phẩm.png', 1),
(5, 'Loại sản phẩm', 'icon loại sản phẩm.png', 1),
(6, 'Nhà cung cấp', 'icon nhà cung cấp.png', 1),
(7, 'Nhà sản xuất', 'icon nhà sản xuất.png', 1),
(8, 'Phiếu nhập', 'icon phiếu nhập.png', 1),
(9, 'Đơn hàng', 'icon hóa đơn.png', 1),
(10, 'Khuyến mãi', 'icon khuyến mãi.png', 1),
(11, 'Thống kê', 'icon thống kê.png', 1);

-- --------------------------------------------------------

--
-- Table structure for table `danhmuc`
--

CREATE TABLE `danhmuc` (
  `idDM` int(11) NOT NULL,
  `LOAISP` varchar(20) NOT NULL,
  `TRANGTHAI` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `danhmuc`
--

INSERT INTO `danhmuc` (`idDM`, `LOAISP`, `TRANGTHAI`) VALUES
(1, 'Điện thoại', 1),
(2, 'Củ sạc (Adapter)', 1),
(3, 'Dây sạc', 1),
(4, 'Tai Nghe', 1);

-- --------------------------------------------------------

--
-- Table structure for table `donhang`
--

CREATE TABLE `donhang` (
  `idHD` int(11) NOT NULL,
  `idTK` int(11) NOT NULL,
  `idkh` int(11) NOT NULL,
  `THANHTIEN` float NOT NULL,
  `NGAYMUA` date NOT NULL,
  `DIACHI` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MAKHUYENMAI` int(11) DEFAULT NULL,
  `TRANGTHAI` int(11) NOT NULL DEFAULT 1,
  `PTTHANHTOAN` enum('CASH','BANK_TRANSFER') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `hang`
--

CREATE TABLE `hang` (
  `idHANG` int(11) NOT NULL,
  `TENHANG` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TRANGTHAI` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `hang`
--

INSERT INTO `hang` (`idHANG`, `TENHANG`, `TRANGTHAI`) VALUES
(1, 'Apple', 1),
(2, 'Xiaomi', 1),
(3, 'Samsung', 1),
(4, 'Oppo', 1),
(5, 'Sony', 1),
(6, 'ZMI', 1),
(7, 'HOCO', 1),
(8, 'Remax', 1),
(11, 'TCL', 1),
(12, 'SHENZEN', 1),
(13, 'HANOI SEOWONINTECH', 1),
(14, 'Ugreen', 1);

-- --------------------------------------------------------

--
-- Table structure for table `imei`
--

CREATE TABLE `imei` (
  `idCTSP` int(11) NOT NULL,
  `imei` varchar(15) NOT NULL DEFAULT '0',
  `STATUS` enum('ĐÃ BÁN','CÒN HÀNG') NOT NULL DEFAULT 'CÒN HÀNG'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `imei`
--

INSERT INTO `imei` (`idCTSP`, `imei`, `STATUS`) VALUES
(1, '123456789012345', 'CÒN HÀNG');

-- --------------------------------------------------------

--
-- Table structure for table `khachhang`
--

CREATE TABLE `khachhang` (
  `idkh` int(11) NOT NULL,
  `hoten` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `sdt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `diachi` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khachhang`
--

INSERT INTO `khachhang` (`idkh`, `hoten`, `sdt`, `diachi`) VALUES
(1, 'Nguyễn Thanh Bình', '0902487107', '65 Nguyễn Du phường An Phú Quận 12 TPHCM'),
(2, 'Trương Xuân Cảnh', '0902483119', '102 Nguyễn Công Trứ phường An Phú Quận 12 TPHCM'),
(3, 'Vũ Hào', '0977453631', '1 An Định phường Xuân Thiều Quận 3 TPHCM'),
(4, 'Nguyễn Minh Thành', '0907885606', '70 Đinh Bộ Lĩnh phường An Thành Quận 2 Hà Nội');

-- --------------------------------------------------------

--
-- Table structure for table `khuyenmai`
--

CREATE TABLE `khuyenmai` (
  `MAKHUYENMAI` int(11) NOT NULL,
  `CODE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `MOTA` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `IS_PERCENT` tinyint(1) NOT NULL,
  `GIATRI` decimal(10,2) NOT NULL,
  `PHANTRAM` int(2) NOT NULL,
  `SOLUONG` int(11) NOT NULL DEFAULT 0,
  `NGAYAPDUNG` date NOT NULL,
  `HANSUDUNG` date NOT NULL,
  `HANG` int(11) DEFAULT NULL,
  `DANHMUC` int(11) DEFAULT NULL,
  `TRANGTHAI` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `khuyenmai`
--

INSERT INTO `khuyenmai` (`MAKHUYENMAI`, `CODE`, `MOTA`, `IS_PERCENT`, `GIATRI`, `PHANTRAM`, `SOLUONG`, `NGAYAPDUNG`, `HANSUDUNG`, `HANG`, `DANHMUC`, `TRANGTHAI`) VALUES
(1, 'SALE10', '', 0, 10000.00, 0, 89, '2024-01-01', '2024-12-31', 1, 2, 1),
(2, 'SALE20', '', 0, 20000.00, 0, 50, '2024-02-01', '2024-06-30', 2, 3, 1),
(3, 'DISCOUNT15', '', 0, 15000.00, 0, 30, '2024-03-01', '2024-07-31', 3, 1, 1),
(4, 'NEWYEAR25', '', 0, 25000.00, 0, 18, '2024-04-01', '2026-05-31', 4, 4, 1),
(5, 'SUMMER30', '', 0, 30000.00, 0, 1, '2024-05-01', '2026-09-16', 5, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `idNCC` int(11) NOT NULL,
  `TENNCC` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `SDT` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DIACHI` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TRANGTHAI` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `nhacungcap`
--

INSERT INTO `nhacungcap` (`idNCC`, `TENNCC`, `SDT`, `DIACHI`, `TRANGTHAI`) VALUES
(1, 'Công ty TNHH A', '0123456789', 'Ngõ 11, Đường Tôn Thất Tùng, Đống Đa Hà Nội', 1),
(2, 'Công ty TNHH B', '0123456789', 'Huỳnh Tấn Phát – Tổ 12 – Khu Phố 2 – TT.Nhà Bè', 1),
(3, 'X Kong ty', '0123456789', 'Phố Tô Vĩnh Diễn, phường Khương Trung, quận Thanh ', 1),
(4, 'Công ty C', '0371956740', 'Đường Lê Lợi, phường Bến Nghé, Quận 1, Thành phố H', 0),
(5, 'Công ty TNHH Duy', '0123456780', 'Đ.Trần Xuân Soạn, Tân Hưng, Quận 7, TP.HCM', 1),
(6, 'Công ty D', '0234523984', 'Đường Trần Duy Hưng, Quận Cầu Giấy , TP Hà Nội', 1);

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--

CREATE TABLE `nhanvien` (
  `idTK` int(11) NOT NULL,
  `GIOITINH` tinyint(1) NOT NULL,
  `NGAYSINH` date NOT NULL,
  `DIACHI` varchar(255) NOT NULL,
  `TINHTRANG` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `phanquyen`
--

CREATE TABLE `phanquyen` (
  `idQUYEN` int(11) NOT NULL,
  `idCN` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phanquyen`
--

INSERT INTO `phanquyen` (`idQUYEN`, `idCN`) VALUES
(0, 1),
(0, 2),
(0, 3),
(0, 4),
(0, 5),
(0, 6),
(0, 7),
(0, 8),
(0, 9),
(0, 10),
(0, 11);

-- --------------------------------------------------------

--
-- Table structure for table `phieunhap`
--

CREATE TABLE `phieunhap` (
  `idPN` int(11) NOT NULL,
  `idNCC` int(11) NOT NULL,
  `NGAYNHAP` date NOT NULL,
  `THANHTIEN` decimal(10,2) DEFAULT NULL,
  `LOINHUAN` int(11) NOT NULL,
  `TRANGTHAI` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `phieunhap`
--

INSERT INTO `phieunhap` (`idPN`, `idNCC`, `NGAYNHAP`, `THANHTIEN`, `LOINHUAN`, `TRANGTHAI`) VALUES
(1, 1, '2025-09-17', 500000.00, 100000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `quyen`
--

CREATE TABLE `quyen` (
  `idQUYEN` int(11) NOT NULL,
  `TENQUYEN` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `LUONG` decimal(10,2) DEFAULT 0.00,
  `TRANGTHAI` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `quyen`
--

INSERT INTO `quyen` (`idQUYEN`, `TENQUYEN`, `LUONG`, `TRANGTHAI`) VALUES
(0, 'admin', 96153.85, 1),
(1, 'Nhân viên bán hàng', 57692.31, 1),
(2, 'Nhân viên kho', 52884.62, 1);

-- --------------------------------------------------------

--
-- Table structure for table `sanpham`
--

CREATE TABLE `sanpham` (
  `idSP` int(11) NOT NULL,
  `TENSP` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `HANG` int(11) NOT NULL,
  `GIANHAP` decimal(10,2) NOT NULL,
  `idDM` int(11) NOT NULL,
  `IMG` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `MOTA` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TRANGTHAI` tinyint(1) NOT NULL DEFAULT 1,
  `GIABAN` decimal(10,2) NOT NULL,
  `DISCOUNT` int(2) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sanpham`
--

INSERT INTO `sanpham` (`idSP`, `TENSP`, `HANG`, `GIANHAP`, `idDM`, `IMG`, `MOTA`, `TRANGTHAI`, `GIABAN`, `DISCOUNT`) VALUES
(1, 'iPhone 16', 1, 19200000.00, 1, 'Iphone 16.jpeg', 'Hiệu năng vượt trội với chip A18rnVới lần nâng cấp này, Apple đã mạnh tay sử dụng chip A18 trên toàn bộ iPhone 16 Series, bao gồm iPhone 16 256GB. Đây là thế hệ chip 3nm thứ 2 của TSMC, công nghệ chip hiện đại nhất hiện nay, mang tới hiệu năng, tốc độ xử lý nhanh và tiết kiệm pin hơn so với chip A16 Bionic của iPhone 15 256GB.rnrnVề hệ điều hành, không còn là đồn đoán, gã khổng lồ công nghệ đã thực sự ứng dụng hệ điều hành iOS 18 tiên tiến trên điện thoại iPhone 16 phiên bản Tiêu Chuẩn. Hệ điều hành mới được cải tiến đặc biệt về AI, bổ sung tính năng tin nhắn mới, cập nhật Apple Maps, Siri, hỗ trợ RCS… mang đến nhiều tiện ích và nâng cấp trải nghiệm người dùng hơn iPhone 15 256GB', 1, 19392000.00, 0),
(2, 'iPhone 16 plus', 1, 28000000.00, 1, 'Iphone 16 pờ lếch.jpeg', 'iPhone 16 Plus 512GB dự kiến sẽ là sản phẩm cháy hàng trong thời gian tới vì dung lượng lưu trữ lớn và có nhiều thay đổi về mặt thiết kế - công nghệ so với mức giá. Hãy cùng điểm mặt 10 lý do bạn nên mua iPhone 16 Plus 512GB ngay khi ra mắt qua bài viết sau nhé!', 1, 28280000.00, 0),
(3, 'SamSung Galaxy Z Flip 6', 3, 19120000.00, 1, 'SamSung Galaxy Z Flip 6.jpeg', 'Galaxy Z Flip 6 ra mắt đã mở ra một kỷ nguyên AI di động với sức mạnh của Galaxy AI tiên tiến. Bên cạnh đó, thiết bị còn cuốn hút mọi người với vẻ ngoài siêu mỏng nhỏ gọn, hiệu năng mạnh mẽ, thời lượng sử dụng bền bỉ và camera nâng tầm nhiếp ảnh. Tất cả hứa hẹn sẽ mang đến cho bạn trải nghiệm sử dụng mới lạ, hấp dẫn, đáp ứng tốt mọi nhu cầu của bạn trong cuộc sống hiện đại ngày nay.', 1, 23900000.00, 0),
(4, 'iPhone 16 Pro Max', 1, 26232000.00, 1, 'iphone 16 Pro Max.jpeg', 'iPhone 16 Plus 512GB dự kiến sẽ là sản phẩm cháy hàng trong thời gian tới vì dung lượng lưu trữ lớn và có nhiều thay đổi về mặt thiết kế - công nghệ so với mức giá. Hãy cùng điểm mặt 10 lý do bạn nên mua iPhone 16 Plus 512GB ngay khi ra mắt qua bài viết sau nhé!', 1, 32790000.00, 0),
(5, 'Airpods pro 2', 1, 4952000.00, 4, 'Airpods pro 2.jpg', 'Trải nghiệm chất lượng âm thanh vô song với tính năng Chủ Động Khử Tiếng Ồn đẳng cấp Pro,Chú thích³ Âm Thanh Thích Ứng để kiểm soát tiếng ồn phù hợp trong mọi môi trường, cùng chế độ Xuyên Âm giúp bạn nghe thấy thế giới xung quanh mình,Chú thích² và tính năng Nhận Biết Cuộc Hội Thoại để giảm âm lượng của nội dung đang phát một cách liền mạch khi bạn đang nói chuyện với ai đó ở gần.Chú thích¹³ Giảm thiểu mức độ tiếp xúc của bạn với tiếng ồn lớn bằng tính năng Giảm Âm Thanh Lớn, sử dụng Tăng Cường Hội Thoại để tập trung vào giọng nói ngay trước mặt bạn, và phát Âm Thanh Trong Nền êm dịu như tiếng đại dương hoặc tiếng mưa rơi để chặn bớt tiếng ồn không mong muốn xung quanh. Cả AirPods Pro 2 và Hộp Sạc MagSafe không dây (USB-C) đều có khả năng chống bụi, chống mồ hôi và chống nước đạt chuẩn IP54,Chú thích¹² và bạn có thể sử dụng ứng dụng Tìm để theo dõi vị trí của các thiết bị này.Chú thích', 1, 6190000.00, 0),
(6, 'Samsung Galaxy S21', 3, 6399200.00, 1, 'Samsung Galaxy S21.jpg', 'Smartphone Samsung S21', 1, 7999000.00, 0),
(7, 'iPhone 13', 1, 15200000.00, 1, 'iphone 13.jpeg', 'Smartphone iPhone 13', 1, 19000000.00, 0),
(8, 'Sony-1000XM4-Gold-A', 5, 2400000.00, 4, 'Sony-1000XM4-Gold-A.jpg', 'Tai nghe Sony chống ồn', 1, 3000000.00, 0),
(9, 'Củ sạc Xiaomi', 2, 119200.00, 2, 'Cu-Sac-Nhanh-Type-C-20W-Xiaomi-AD201-Quoc-Te-chinh-hang-mi360-3.jpg', 'Củ sạc nhanh Xiaomi AD201 20W Xiaomi AD201 Quốc Tế từ thương hiệu uy tín, công suất mạnh mẽ, hiệu quả sạc ổn định sẽ một lựa chọn hợp lý giúp cung cấp khả năng sạc nhanh cho các thiết bị. Bên cạnh thiết kế nhỏ gọn, dễ lưu trữ và mang đi. Củ sạc nhanh Xiaomi AD201 còn cho độ tương thích cao với nhiều dòng smartphone, máy tính bảng,…với công suất sạc lên đến 20W. Đặc biệt, cốc sạc còn hỗ trợ tính năng sạc nhanh giúp rút ngắn đáng kể thời gian sạc hơn so với những cốc sạc thông thường.', 1, 149000.00, 0),
(10, 'Củ sạc Samsung', 3, 47200.00, 2, 'cu-sac-samsung-mi360.jpg', '– Củ sạc nhanh Samsung 5V-2A được bán tại shop phụ kiện Samsung cam kết là hàng chính hãng 100% của thương hiệu Samsung.rn– Bộ sạc được shop nhập từ hai nhà cung cấp đó là nhà máy Samsung Việt Nam và Trung Quốc, bạn hoàn toàn yên tâm sử dụng.rn– Củ sạc nhanh Samsung 5V-2A có thiết kế nhỏ gọn, trọng lượng nhẹ hơn thế phần củ và dây cáp thiết kế tách biệt nhau cho nên sản phẩm giúp bạn dễ dàng di chuyển mọi lúc mọi nơi.', 1, 59000.00, 0),
(19, 'Cáp sạc Type C Zmi AL303-AL873', 6, 143200.00, 3, 'Cáp sạc Type C Zmi AL303-AL873.jpg', 'Bạn đang tìm kiếm một sợi cáp sạc chất lượng với giá cả hợp lý, đến từ thương hiệu uy tín, đồng thời hỗ trợ sạc nhanh thì Cáp sạc Type C Zmi AL303-AL873 chính là sự lựa chọn mà bạn không thể bỏ qua.rnLí do nên trang bị Cáp sạc Type C Zmi AL303-AL873rnCáp sạc Type C Zmi AL303-AL873 là một cáp sạc đến từ tương hiệu ZMI, hỗ trợ dòng điện lên tới 3A giúp quá trình sạc và truyền dữ liệu nhanh hơn. Bên cạnh đó thiết kế siêu bền bỉ cùng chiều dài lên tới 1m sẽ là những ưu điểm đáng để bạn cân nhắc.', 1, 179000.00, 0),
(20, 'Cáp sạc Type C ZMI AL706', 6, 159200.00, 3, 'Cap-type-C-sieu-ben-Xiaomi-ZMI-AL706-chinh-hang-mi360.jpg', 'Cáp sạc Type C ZMI AL706 có chiều dài dây 1 mét2 mét tiêu chuẩn tương tự như các loại cáp sạc phổ biến. Tuy nhiên điểm đặc biệt chính là toàn bộ thân dây được bao bọc bởi một lớp dây dù bện nylon nhằm gia cố chắc chắn cho sợi cáp, giúp cho sợi cáp cứng cáp mà không gặp phải trường hợp tưa dây hở mạch. Ngoài ra, nó còn chống rối dây hiệu quả khi bạn để trong balo túi xách và có thể chịu lực uốn cong liên tục lên đến 30.000 lần mà không bị hư hỏng.', 1, 199000.00, 0),
(21, 'Củ sạc nhanh Zmi HA612', 2, 79200.00, 2, 'Cu-Sac-Nhanh-Xiaomi-Zmi-HA716-chinh-hang-mi360-3.png', 'Bạn đang tìm một cốc sạc chất lượng, có thể hoạt động ổn định và đặc biệt là phải có tính năng sạc nhanh. Bạn đang phân vân và không biết nên chọn sản phẩm nào để vừa có thể đáp ứng tốt như cầu của mình nhưng lại phải phù hợp với túi tiền của mình. Vâng nếu như thế thì sản phẩm dưới đây, Củ sạc nhanh Zmi HA612 18W Chính Hãng chính là sản phẩm mà bạn đang tìm kiếm.', 1, 99000.00, 0),
(22, 'Củ sạc nhanh HOCO 3USB HK1', 7, 132000.00, 2, 'Củ sạc nhanh HOCO 3USB HK1.png', 'Củ sạc nhanh HOCO 3USB HK1 3 cổng 5A một thiết bị adapter để sạc cho các thiết bị di động, trang bị 3 cổng USB cho phép sạc cùng lúc cho cả 3 thiết bị. Hỗ trợ sạc tương thích với cả điện thoại và máy tính bảng.rnCủ sạc nhanh HOCO 3USB HK1 3 cổng 5A được làm hoàn toàn từ nhựa ABS-PC. Các cạnh xung quanh đều được bo cong để cảm giác cầm nắm tốt hơn và bớt phần đơn điệu. Bề mặt được làm dạng bóng giúp sản phẩm sang trọng và cao cấp hơn.', 1, 165000.00, 0),
(23, 'Củ sạc nhanh Xiaomi AD332EU', 2, 199200.00, 2, 'Củ sạc nhanh Xiaomi AD332EU.jpg', 'Củ sạc nhanh Xiaomi AD332EU được trang bị công nghệ sạc nhanh lên đến 30W cho tốc độ vượt trội. Bên cạnh đó, với việc được trang bị cả 2 cổng đầu ra phổ biến nhất hiện nay là USB-A và USB type C thì đây là một điểm rất tiện lợi cho người sử dụng. Cả 2 cổng đầu ra đều có khả năng sạc nhanh, đối với cổng USB type C thì cốc sạc có công suất sạc nhanh là 30W, khi sử dụng cổng sạc USB-A thì công suất tối đa là 27W. Còn khi sử dụng đồng thời cả 2 cổng thì công suất đạt tối đa là 24W chia đều cho 2 cổng sạc.', 1, 201192.00, 0),
(24, 'Củ sạc nhanh Zmi 1A1C HA722', 6, 183200.00, 2, 'Củ sạc nhanh Zmi 1A1C HA722.jpg', 'Củ sạc nhanh Zmi 1A1C HA722 là sản phẩm được rất nhiều người yêu thích sử dụng hiện nay. Với thiết kế nhỏ gọn, trang bị 2 cổng ra với công suất sạc nhanh lên tới 30W, cùng với cổng USB type C hỗ trợ PD sản phẩm có thể sạc được cho Laptop, Macbook. Đây là lựa chọn tuyệt vời dành cho những ai sở hữu nhiều thiết bị di động.', 1, 229000.00, 0),
(25, 'Tai nghe Bluetooth Business Remax RB T15', 8, 199200.00, 4, 'Tai nghe Bluetooth Business Remax RB T15.jpg', 'Tai nghe Bluetooth Business Remax RB T15 được thiết kế với kiểu dáng hiện đại, sang trọng, giúp bạn dễ dàng mang theo bên mình và sử dụng trong khi di chuyển mọi nơi, thích hợp cho những người bận rộn và phải đàm thoại nhiều.rnKích thước của Tai nghe Bluetooth Business Remax RB T15 tuy nhỏ 51×13.8×9.15mm, trọng lượng nhẹ chỉ với 675g, nhưng chiếc tai nghe này được cấu tạo bao gồm 46 bộ phận chi tiết nhỏ được cấu tạo tỉ mỉ và lắp ráp cẩn thận thận từ ngoài vỏ cho tới bên trong.', 1, 249000.00, 0),
(26, 'Tai nghe In-Ear Headphones Basic', 2, 119200.00, 4, 'Tai nghe In-Ear Headphones Basic.jpg', 'Tai nghe Tai nghe In-Ear Headphones Basic được thiết kế với kiểu dáng hiện đại, sang trọng, giúp bạn dễ dàng mang theo bên mình và sử dụng trong khi di chuyển mọi nơi, thích hợp cho những người bận rộn và phải đàm thoại nhiều.rnKích thước của Tai nghe In-Ear Headphones Basic tuy nhỏ 51×13.8×9.15mm, trọng lượng nhẹ chỉ với 675g, nhưng chiếc tai nghe này được cấu tạo bao gồm 46 bộ phận chi tiết nhỏ được cấu tạo tỉ mỉ và lắp ráp cẩn thận thận từ ngoài vỏ cho tới bên trong.', 1, 149000.00, 0),
(27, 'Xiaomi Redmi Note 14 8GB128GB', 2, 4392000.00, 1, 'Xiaomi Redmi Note 14 8GB-128GB.jpg', 'Hệ điều hànhrnXiaomi HyperOS (Android 14)rnChip xử lý (CPU)rnMediaTek Helio G99-Ultra 8 nhânrnTốc độ CPUrn2 nhân 2.2 GHz & 6 nhân 2.0 GHzrnChip đồ họa (GPU)rnMali-G57 MC2rnRAMrn8 GBrnDung lượng lưu trữrn128 GBrnDung lượng còn lại (khả dụng) khoảngrn104 GBrnThẻ nhớrnMicroSD, hỗ trợ tối đa 1 TBrnDanh bạrnKhông giới hạn', 1, 5490000.00, 0),
(28, 'Xiaomi Redmi Note 14 5G (12GB256GB) Dimensity 7025 Ultra Ne', 2, 4472000.00, 1, 'Xiaomi Redmi Note 14 5G (12GB-256GB) Dimensity 7025 Ultra.png', 'Nâng cấp lớn về ngoại hình, thiết kế lịch lãm nhất dòng NoternBắt đầu từ dòng Note 13 năm ngoái, Redmi đã củng cố thiết kế ngoại hình của dòng Note. Do đó, ở dòng Note 14 mới, chúng ta thấy một thiết kế rất đặc biệt và kết cấu thân máy có thể so sánh với một chiếc hạm. Dòng Note 14 áp dụng thiết kế ống kính gắn ở giữa để nâng cao cảm giác sang trọng tổng thể, Redmi cũng sử dụng một vòng tròn có thiết kế họa tiết được chạm khắc tinh xảo ở vòng ngoài. Mặt sau thân máy cũng áp dụng thiết kế hyperboloid, giúp cải thiện đáng kể độ bám của toàn bộ máy.', 1, 5590000.00, 0),
(29, 'Xiaomi Redmi Note 13 5G (6128) Dimensity 6080', 2, 3272000.00, 1, 'Xiaomi Redmi Note 13 5G (6-128) Dimensity 6080.png', 'Xiaomi gần đây đã ra mắt sản phẩm tiếp theo của thương hiệu Redmi tại Trung Quốc , đó là Redmi Note 13 Series ngày 21 tháng 09 năm 2023 . Ở Note 13 series sẽ có các phiên bản sau  Redmi Note 13 ,  Redmi Note 13 Pro ,  Redmi Note 13 Pro Plus . Hứa hẹn là mẫu smartphone hàng đầu hiện nay ở phân khúc giá rẻ tầm trung khi được trang bị chipset mạnh mẽ cùng mức giá cực tốt . ', 1, 4090000.00, 0),
(30, 'Xiaomi Redmi Note 13 Pro 5G (8128GB) Snap 7s Gen 2', 2, 3352000.00, 1, 'Xiaomi Redmi Note 13 Pro 5G (8-128GB) Snap 7s Gen 2.png', 'Xiaomi gần đây đã ra mắt sản phẩm tiếp theo của thương hiệu Redmi tại Trung Quốc , đó là Redmi Note 13 Series ngày 21 tháng 09 năm 2023 . Ở Note 13 series sẽ có các phiên bản sau  Redmi Note 13 ,  Redmi Note 13 Pro ,  Redmi Note 13 Pro Plus . Hứa hẹn là mẫu smartphone hàng đầu hiện nay ở phân khúc giá rẻ tầm trung khi được trang bị chipset mạnh mẽ cùng mức giá cực tốt.', 1, 4190000.00, 0),
(32, 'Xiaomi Redmi Note 13 Pro+ 5G (12256GB) Dimensity 7200 Ultra', 2, 4312000.00, 1, 'Xiaomi Redmi Note 13 Pro+ 5G (12-256GB) Dimensity 7200 Ultra.png', 'Xiaomi gần đây đã ra mắt sản phẩm tiếp theo của thương hiệu Redmi tại Trung Quốc , đó là Redmi Note 13 Series ngày 21 tháng 09 năm 2023 . Ở Note 13 series sẽ có các phiên bản sau  Redmi Note 13 ,  Redmi Note 13 Pro ,  Redmi Note 13 Pro Plus . Hứa hẹn là mẫu smartphone hàng đầu hiện nay ở phân khúc giá rẻ tầm trung khi được trang bị chipset mạnh mẽ cùng mức giá cực tốt.', 1, 5390000.00, 0),
(59, 'Tai nghe Bluetooth True Wireless OPPO ENCO Buds 2 ETE41', 4, 632000.00, 4, 'Tai nghe Bluetooth True Wireless OPPO ENCO Buds 2 ETE41.jpg', 'Thời lượng pin tai nghernDùng 7 giờ - Sạc 1.5 giờrnThời lượng pin hộp sạcrnDùng 28 giờ - Sạc 3 giờrnCổng sạcrnType-CrnCông nghệ âm thanhrncodec SBCrnCông nghệ ENCrncodec AACrnTương thíchrnAndroidrniOS (iPhone)rnỨng dụng kết nốirnHeyMelody ApprnTiện íchrnChống nước IPX4rnGame ModernCó mic thoạirnChụp ảnh nhanhrnKết nối cùng lúcrn1 thiết bịrnCông nghệ kết nốirnBluetooth 5.2rnĐiều khiểnrnCảm ứng chạmrnPhím điều khiểnrnTănggiảm âm lượngrnChuyển bài hátrnNhậnNgắt cuộc gọirnKích thướcrnDài 2.1 cm - Rộng 2.2 cm - Cao 3.4 cmrnKhối lượngrn4 g', 1, 790000.00, 0),
(61, 'Tai nghe TWS Samsung Galaxy Buds3 Pro R630N', 3, 4072000.00, 4, 'Tai nghe TWS Samsung Galaxy Buds3 Pro R630N.jpg', 'Thời lượng pin tai nghernDùng 7 giờ - Sạc Hãng không công bốrnThời lượng pin hộp sạcrnDùng 30 giờ - Sạc Hãng không công bốrnCổng sạcrnType-CrnCông nghệ âm thanhrnDolby AtmosrnCông nghệ phát sóng AuracastrnAdaptive Noise ControlrnAdaptive EQrn360 Reality AudiornTương thíchrnmacOSrnAndroid, iOS, WindowsrnỨng dụng kết nốirnGalaxy WearablernTiện íchrnPhiên dịch trực tiếprnThanh ánh sáng Blade LightsrnChống nước & bụi IP57rnSạc không dâyrnTự động chuyển đổi kết nối linh hoạt (Auto Switch)rnCó mic thoạirnSạc nhanhrn360 Reality AudiornGalaxy AIrnKết nối cùng lúcrn1 thiết bịrnCông nghệ kết nốirnBluetooth 5.4rnĐiều khiểnrnCảm ứng chạmvuốtrnGiọng nói (Tiếng Anh, Hàn)rnPhím điều khiểnrnTănggiảm âm lượngrnPhátdừng chơi nhạcrnChuyển bài hátrnChuyển chế độrnNhậnNgắt cuộc gọirnKích thướcrnDài 3.2 cm - Rộng 2.02 cm - Cao 1.8 cmrnKhối lượngrn5.4 g', 1, 5090000.00, 0),
(62, 'Tai nghe Bluetooth True Wireless OPPO ENCO Air 4 ETEE1', 4, 1032000.00, 4, 'Tai nghe Bluetooth True Wireless OPPO ENCO Air 4 ETEE1.jpg', 'Thời lượng pin tai nghernDùng 11.5 giờ - Sạc 1 giờrnThời lượng pin hộp sạcrnDùng 42 giờ - Sạc Khoảng 80 phútrnCổng sạcrnType-CrnCông nghệ âm thanhrnChống ồn chủ độngrnTương thíchrnmacOSrnAndroid, iOS, WindowsrnỨng dụng kết nốirnHeyMelody ApprnTiện íchrnChống nước & bụi IP55rnCó mic thoạirn2 Micro hỗ trợ AIrnKết nối cùng lúcrn2 thiết bịrnCông nghệ kết nốirnBluetooth 5.4rnĐiều khiểnrnCảm ứngrnPhím điều khiểnrnPhátdừng chơi nhạcrnChuyển bài hátrnChuyển chế độrnNhậnNgắt cuộc gọirnKích thướcrnDài 2.8 cm - Rộng 2.1 cm - Cao 1.7 cmrnKhối lượngrn4.2 g', 1, 1290000.00, 0),
(63, 'Tai nghe Bluetooth True Wireless OPPO Enco Buds 2 Pro E510A', 4, 816000.00, 4, 'Tai nghe Bluetooth True Wireless OPPO Enco Buds 2 Pro E510A.jpg', 'Thời lượng pin tai nghernDùng 8 giờ - Sạc 2 giờrnThời lượng pin hộp sạcrnDùng 38 giờ - Sạc 2 giờrnCổng sạcrnType-CrnCông nghệ âm thanhrnDolby AtmosrnDirac Audio TunerrnDynamic Driver 12.4 mmrnCông nghệ ENCrnTương thíchrnmacOSrnAndroid, iOS, WindowsrnỨng dụng kết nốirnHeyMelody ApprnTiện íchrnChống nước & bụi IP55rnCó mic thoạirn2 Micro hỗ trợ AIrnKết nối cùng lúcrn1 thiết bịrnCông nghệ kết nốirnBluetooth 5.3rnĐiều khiểnrnCảm ứng chạmrnPhím điều khiểnrnPhátdừng chơi nhạcrnChuyển bài hátrnNhậnNgắt cuộc gọirnKích thướcrnDài 2.85 cm - Rộng 2.02 cm - Cao 2.3 cmrnKhối lượngrn4.3 g ± 0.1 g', 1, 1020000.00, 0),
(64, 'Tai nghe Bluetooth True Wireless OPPO ENCO Air 3 ETE31', 4, 952000.00, 4, 'Tai nghe Bluetooth True Wireless OPPO ENCO Air 3 ETE31.png', 'Thời lượng pin tai nghernDùng 6 giờ - Sạc 1 giờrnThời lượng pin hộp sạcrnDùng 25 giờ - Sạc 2 giờrnCổng sạcrnType-CrnCông nghệ âm thanhrnÂm thanh Hi-FirnÂm thanh vòm OPPO AlivernTương thíchrnAndroidrniOS (iPhone)rnmacOS (Macbook, iMac)rnWindowsrnỨng dụng kết nốirnHeyMelody ApprnTiện íchrnChống nước & bụi IP54rnHỗ trợ chụp ảnhrnTrợ lý ảo GooglernKhử tiếng ồn AIrnKết nối cùng lúcrn2 thiết bịrnCông nghệ kết nốirnBluetooth 5.3rnĐiều khiểnrnCảm ứng chạmrnPhím điều khiểnrnTănggiảm âm lượngrnPhátdừng chơi nhạcrnChuyển bài hátrnBật trợ lí ảornNhậnNgắt cuộc gọirnHỗ trợ chụp ảnhrnKích thướcrnDài 3.31 cm - Rộng 1.845 cm - Cao 1.71 cmrnKhối lượngrn3.75 g', 1, 1190000.00, 0),
(65, 'Tai nghe TWS Samsung Galaxy Buds3 R530N', 3, 2872000.00, 4, 'Tai nghe TWS Samsung Galaxy Buds3 R530N.jpg', 'Thời lượng pin tai nghernDùng 6 giờ - Sạc Hãng không công bốrnThời lượng pin hộp sạcrnDùng 30 giờ - Sạc Hãng không công bốrnCổng sạcrnType-CrnCông nghệ âm thanhrnCông nghệ phát sóng AuracastrnÂm thanh Hi-FirnActive Noise CancellingrnAdaptive EQrn360 Reality AudiornTương thíchrnAndroidrniOS (iPhone)rnWindowsrnỨng dụng kết nốirnGalaxy WearablernTiện íchrnPhiên dịch trực tiếprnSạc không dâyrnTự động chuyển đổi kết nối linh hoạt (Auto Switch)rnChống nước IP57rnCó mic thoạirnSạc nhanhrn360 Reality AudiornChống ồnrnGalaxy AIrnKết nối cùng lúcrn1 thiết bịrnCông nghệ kết nốirnBluetooth 5.4rnĐiều khiểnrnCảm ứng chạmvuốtrnGiọng nói (Tiếng Anh, Hàn)rnPhím điều khiểnrnTănggiảm âm lượngrnPhátdừng chơi nhạcrnChuyển bài hátrnChuyển chế độrnNhậnNgắt cuộc gọirnBậtTắt chống ồnrnKích thướcrnDài 3.1 cm - Rộng 1.78 cm - Cao 1.7 cmrnKhối lượngrn4.7 g', 1, 3590000.00, 0),
(91, 'Cáp Type C 1m Ugreen 60126', 14, 128000.00, 3, 'Cáp Type C 1m Ugreen 60126.jpg', 'Chức năngrnTruyền dữ liệurnSạcrnĐầu vàornUSB Type-ArnĐầu rarnType C 5V - 3ArnĐộ dài dâyrn1 mrnCông suất tối đarn15 W', 1, 160000.00, 0),
(92, 'Cáp Lightning 1m Xmobile DR-L001X', 14, 152000.00, 3, 'Cáp Lightning 1m Xmobile DR-L001X.jpg', 'Đặc điểm nổi bậtrnrnThiết kế nhỏ gọn, màu sắc nổi bật, trẻ trung, đẹp mắt.rnChiều dài dây cáp 1 m sử dụng tiện lợi.rnVỏ bọc bằng sợi nylon dai bền, hạn chế xoắn rối hay đứt gãy.rnDòng sạc tối đa 5V – 2.1A tương đương 10.5 W, cho tốc độ sạc nhanh, đường truyền ổn định.rnTương thích với các thiết bị iPhone 5, iPad 4 trở lên và các thiết bị dùng cổng Lightning.rnĐầu cáp USB kết nối tốt với adapter, sạc dự phòng, PC, laptop.rnDùng để sạc pin, sao chép, đồng bộ dữ liệu cho các thiết bị.', 1, 190000.00, 0),
(93, 'Cáp Type C - Lightning MFI 1m Ugreen 60759', 14, 152000.00, 3, 'Cáp Type C - Lightning MFI 1m Ugreen 60759.jpg', 'Công nghệTiện íchrnHỗ trợ sạc nhanhrnChức năngrnSạcrnTruyền dữ liệurnĐầu vàornUSB Type-CrnĐầu rarnLightning 60 WrnĐộ dài dâyrn1 mrnCông suất tối đarn60 W', 1, 190000.00, 0),
(94, 'Cáp Lightning 1m Xmobile DR-L001X', 14, 288000.00, 3, 'Cáp Lightning 1m Xmobile DR-L001X.jpg', 'Đặc điểm nổi bậtrnrnThiết kế nhỏ gọn, màu sắc nổi bật, trẻ trung, đẹp mắt.rnChiều dài dây cáp 1 m sử dụng tiện lợi.rnVỏ bọc bằng sợi nylon dai bền, hạn chế xoắn rối hay đứt gãy.rnDòng sạc tối đa 5V – 2.1A tương đương 10.5 W, cho tốc độ sạc nhanh, đường truyền ổn định.rnTương thích với các thiết bị iPhone 5, iPad 4 trở lên và các thiết bị dùng cổng Lightning.rnĐầu cáp USB kết nối tốt với adapter, sạc dự phòng, PC, laptop.rnDùng để sạc pin, sao chép, đồng bộ dữ liệu cho các thiết bị.', 1, 360000.00, 0),
(95, 'Cáp Type C - Type C 1m Ugreen 70427', 14, 200000.00, 3, 'Cáp Type C - Type C 1m Ugreen 70427.jpg', 'Công nghệTiện íchrnChip E-markerrnChức năngrnSạcrnTruyền dữ liệurnĐầu vàornUSB Type-CrnĐầu rarnType C 100 WrnĐộ dài dâyrn1 mrnCông suất tối đarn100 W', 1, 250000.00, 0),
(96, 'Cáp Type C - Type C 1m Ugreen 50997', 14, 104000.00, 3, 'Cáp Type C - Type C 1m Ugreen 50997.jpg', 'Chức năngrnTruyền dữ liệurnSạcrnĐầu vàornUSB Type-CrnĐầu rarnType C 5V - 3A, 9V - 2A, 12V - 3A, 15V - 3A, 20V - 3A (Max 60 W)rnĐộ dài dâyrn1 mrnCông suất tối đarn60 W', 1, 130000.00, 0),
(97, 'Bộ Adapter Sạc 3 cổng Type C PD QC 4.0+ GaN 65W kèm Cáp Type', 14, 856000.00, 2, 'Bộ Adapter Sạc 3 cổng Type C PD QC 4.0+ GaN 65W kèm Cáp Type C - Type C 1.5m Ugreen X755 25870.jpg', 'ModelrnX755 25870rnChức năngrnSạcrnĐầu vàorn100-240V~5060Hz 1.8ArnĐầu rarnUSB 5V - 3A, 9V - 2A, 12V - 1.5A, 10V - 2.25A (Max 22.5W)rnType C1 5V - 3A, 9V - 3A, 12V - 3A, 15V - 3A, 20V - 3.25A, 3.3-21V - 3A (Max 65W)rnType C2 5V - 3A, 9V - 3A, 12V - 3A, 15V - 3A, 20V - 3.25A, 21V - 3A (Max 65W)rnDòng sạc tối đarn65 WrnKích thướcrnDài 5.4 cm - Ngang 4 cm - Cao 3.2 cmrnCông nghệTiện íchrnCông nghệ GaNrnPower DeliveryrnQuick Charge 4.0+', 1, 1070000.00, 0),
(98, 'Adapter Sạc 2 cổng USB Type C PD QC 3.0 GaN 35W Ugreen Nexod', 14, 856000.00, 2, 'Adapter Sạc 2 cổng USB Type C PD QC 3.0 GaN 35W Ugreen Nexode CD350 15539.jpg', 'ModelrnCD350 15539rnChức năngrnSạcrnĐầu vàorn100-240V~5060Hz, 900mArnĐầu rarnUSB 5V - 3A, 9V - 2A, 12V - 1.5A, 12V - 2.25A (Max 22.5W)rnType C 5V - 3A, 9V - 3A, 12V - 2.9A, 15V - 2.33A, 20V - 1.75A (Max 35W)rnDòng sạc tối đarn35 WrnKích thướcrnDài 8.4 cm - Ngang 3.6 cm - Cao 3.6 cmrnCông nghệTiện íchrnCông nghệ GaNrnPower DeliveryrnQuick Charge 4.0+', 1, 1070000.00, 0),
(99, 'Adapter Sạc Type C PD QC 4.0+ GaN 30W Ugreen Nexode CD319', 14, 280000.00, 2, 'Adapter Sạc Type C PD QC 4.0+ GaN 30W Ugreen Nexode CD319.jpg', 'ModelrnCD319rnChức năngrnSạcrnĐầu vàorn100-240V~5060Hz, 800mArnĐầu rarnType C 5V - 3A, 9V - 3A, 12V - 2.5A, 15V - 2A, 20V - 1.5A (Max 30W)rnDòng sạc tối đarn30 WrnCông nghệTiện íchrnCông nghệ GaNrnPower DeliveryrnQuick Charge 4.0+', 1, 350000.00, 0),
(100, 'Adapter Sạc Type C PD GaN 30W Ugreen Robot Nexode 15550', 7, 480000.00, 2, 'Adapter Sạc Type C PD GaN 30W Ugreen Robot Nexode 15550.jpg', 'ModelrnCD319rnChức năngrnSạcrnĐầu vàorn100-240V~5060Hz, 800mArnĐầu rarnType C 5V - 3A, 9V - 3A, 12V - 2.5A, 15V - 2A, 20V - 1.5A (Max 30W)rnDòng sạc tối đarn30 WrnCông nghệTiện íchrnCông nghệ GaNrnPower DeliveryrnQuick Charge 4.0+', 1, 600000.00, 0),
(105, 'iPhone 16e', 1, 13000000.00, 1, 'iPhone 16e_Trắng_256GB.jpg', 'iPhone 16e - Lựa chọn hoàn hảo cho hiệu suất đỉnh cao!nThông số iPhone 16e có gì mớinHiệu năng vượt trội với chip A18 đời mớinMàn hình Super Retina XDR sắc nét hơnnKết nối mạnh mẽ với Apple C1 5GnCamera 48MP 2 trong 1 – Chụp ảnh đỉnh caonPin ‘’trâu’’ hơn kết hợp với sạc nhanh USB-CnThiết kế bền bỉ với hai màu cơ bản sang trọngnSo sánh iPhone 16e với iPhone SE và iPhone 15nSo sánh iPhone 16e và iPhone SE (2022) – Sự nâng cấp đáng giánSo sánh iPhone 16e và iPhone 15 – Hiệu năng mạnh hơn, AI thông minh hơn, pin bền hơnnNhững câu hỏi thường gặp khi mua iP 16e mớinĐiện thoại iPhone 16e ra mắt khi nàoniPhone 16e giá bao nhiêu tiềnniPhone 16e có những màu sắc nàonĐiện thoại iPhone 16e sử dụng con chip nàonDung lượng pin của iPhone 16e có tốt hơn iPhone 16 khôngn16e có hỗ trợ AI khôngniPhone 16e có chống nước không', 1, 13390000.00, 0),
(106, 'iPhone 16 SE', 1, 0.00, 1, 'iPhone 16 SE_Trắng_128GB.jpg', '', 1, 0.00, 0);

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `idTK` int(11) NOT NULL,
  `USERNAME` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `PASSWORD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `SDT` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `EMAIL` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `HOTEN` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idQUYEN` int(11) NOT NULL DEFAULT 1,
  `TRANGTHAI` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`idTK`, `USERNAME`, `PASSWORD`, `SDT`, `EMAIL`, `HOTEN`, `idQUYEN`, `TRANGTHAI`) VALUES
(0, 'admin', '12345', '0901234567', 'admin@myapp.com', 'Nguyễn Văn A', 0, 1),
(1, 'manager', '5e884898da28047151d0e56f8dc6292773603d0d6aabfca13f8c053ec3f7e52b', '0911223344', 'manager@myapp.com', 'Lê Thị B', 1, 1),
(2, 'editor', '4a2d5e751a02b374d6c4e16d4e0e572013f9f30e0b3c675c92c42c77d48d3c5b', '0887654321', 'editor@myapp.com', 'Trần Văn C', 2, 1),
(3, 'user1', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '0709988776', 'user1@myapp.com', 'Phạm Thu D', 2, 1),
(4, 'guest', '9625394d60d2b1f814524c878e1b655f46995079c6d36e2cc4de40a7a012a64c', NULL, NULL, 'Vô Danh', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `thongsokythuat`
--

CREATE TABLE `thongsokythuat` (
  `idKT` int(11) NOT NULL,
  `idSP` int(11) DEFAULT NULL,
  `ManHinh` varchar(255) DEFAULT NULL,
  `ChipXuLy` varchar(255) DEFAULT NULL,
  `RAM` varchar(50) DEFAULT NULL,
  `BoNhoTrong` varchar(100) DEFAULT NULL,
  `CameraSauTruoc` varchar(100) DEFAULT NULL,
  `Pin` varchar(100) DEFAULT NULL,
  `HeDieuHanh` varchar(100) DEFAULT NULL,
  `CongSac` varchar(50) DEFAULT NULL,
  `TanSoQuet` varchar(50) DEFAULT NULL,
  `ChongNuocBui` varchar(50) DEFAULT NULL,
  `TrongLuong` varchar(50) DEFAULT NULL,
  `ChatLieu` varchar(100) DEFAULT NULL,
  `Loa` varchar(50) DEFAULT NULL,
  `SacNhanh` varchar(50) DEFAULT NULL,
  `TinhNangDacBiet` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `thongsokythuat`
--

INSERT INTO `thongsokythuat` (`idKT`, `idSP`, `ManHinh`, `ChipXuLy`, `RAM`, `BoNhoTrong`, `CameraSauTruoc`, `Pin`, `HeDieuHanh`, `CongSac`, `TanSoQuet`, `ChongNuocBui`, `TrongLuong`, `ChatLieu`, `Loa`, `SacNhanh`, `TinhNangDacBiet`) VALUES
(1, 1, '6.1 OLED', 'A17 Bionic', '6GB', '128GB256GB', '48MP12MP', '4000mAh, 20W', 'iOS 17', 'Lightning', '120Hz', 'IP68', '172g', 'Kính, thép không gỉ', 'Stereo', 'Có', 'Face ID, Dolby Vision'),
(2, 2, '6.7 OLED', 'A17 Bionic', '6GB', '256GB512GB', '48MP12MP', '4320mAh, 20W', 'iOS 17', 'Lightning', '120Hz', 'IP68', '203g', 'Kính, thép không gỉ', 'Stereo', 'Có', 'Face ID, Dolby Vision'),
(3, 3, '6.7 AMOLED gập, 120Hz', 'Snapdragon 8 Gen 3', '8GB', '256GB512GB', '12MP kép10MP', '3700mAh, 25W', 'Android 13', 'USB-C', '120Hz', 'IPX8', '198g', 'Kính, nhôm', 'Stereo', 'Có', 'Gập đôi, Flex Mode'),
(4, 4, '6.7 Super Retina XDR', 'A17 Pro', '8GB', '256GB512GB', '48MP12MP', '4320mAh, 20W', 'iOS 17', 'Lightning', '120Hz', 'IP68', '228g', 'Kính, thép không gỉ', 'Stereo', 'Có', 'Face ID, Dolby Vision'),
(5, 5, NULL, NULL, NULL, NULL, NULL, '24 giờ (kèm hộp)', 'Firmware Apple', 'USB-C', NULL, 'IPX4', NULL, 'Nhựa', 'Mono', 'Có', 'Chống ồn chủ động (ANC)'),
(6, 6, '6.2 AMOLED, 120Hz', 'Exynos 2100Snapdragon 888', '8GB', '128GB256GB', '12MP kép10MP', '4000mAh, 25W', 'Android 12', 'USB-C', '120Hz', 'IP68', '169g', 'Kính, nhôm', 'Stereo', 'Có', 'Sạc nhanh, Dolby Atmos'),
(7, 7, '6.1 Super Retina XDR', 'A15 Bionic', '4GB', '128GB256GB512GB', '12MP kép12MP', '3240mAh, 20W', 'iOS 15', 'Lightning', '60Hz', 'IP68', '174g', 'Kính, nhôm', 'Stereo', 'Có', 'Face ID, Dolby Vision'),
(8, 8, NULL, NULL, NULL, NULL, NULL, '30 giờ (kèm hộp)', NULL, 'USB-C', NULL, 'IPX4', NULL, 'Nhựa', 'Mono', 'Có', 'Chống ồn chủ động (ANC)'),
(9, 9, '6.6 AMOLED', 'MediaTek Helio G88', '6GB', '128GB', '50MP kép8MP', '5000mAh, 33W', 'Android 13', 'USB-C', '90Hz', 'IP53', '190g', 'Nhựa', 'Stereo', 'Có', 'Sạc nhanh, vân tay cạnh bên'),
(10, 10, '6.5 PLS LCD', 'Exynos 850', '4GB', '64GB128GB', '50MP5MP', '5000mAh, 15W', 'Android 12', 'USB-C', '60Hz', NULL, '195g', 'Nhựa', 'Mono', 'Không', 'Mở khoá vân tay cạnh bên'),
(19, 19, '6.7 OLED', 'Apple M2', '8GB', '256GB512GB', '12MP12MP', 'Unknown', 'iPadOS 16', 'USB-C', '120Hz', NULL, '450g', 'Nhôm', 'Stereo', 'Không', 'Face ID, hỗ trợ Apple Pencil'),
(20, 20, '10.9 Liquid Retina', 'Apple M1', '8GB', '64GB256GB', '12MP12MP', 'Unknown', 'iPadOS 15', 'USB-C', '60Hz', NULL, '470g', 'Nhôm', 'Stereo', 'Không', 'Hỗ trợ Magic Keyboard, Pencil'),
(21, 21, '6.6 AMOLED', 'MediaTek Helio G99', '8GB', '128GB256GB', '64MP16MP', '5000mAh, 33W', 'Android 13', 'USB-C', '120Hz', 'IP53', '185g', 'Nhựa', 'Stereo', 'Có', 'Vân tay cạnh bên, loa kép'),
(22, 22, '6.67 AMOLED', 'Snapdragon 7 Gen 1', '8GB', '256GB', '200MP16MP', '5000mAh, 67W', 'Android 13', 'USB-C', '120Hz', 'IP54', '190g', 'Nhôm', 'Stereo', 'Có', 'Camera 200MP, vân tay dưới màn'),
(23, 23, '6.5 IPS LCD', 'MediaTek Helio G85', '4GB', '64GB128GB', '50MP8MP', '5000mAh, 18W', 'Android 12', 'USB-C', '90Hz', NULL, '195g', 'Nhựa', 'Mono', 'Không', 'Giá rẻ, pin trâu'),
(24, 24, '6.6 AMOLED', 'Snapdragon 680', '6GB', '128GB', '50MP16MP', '5000mAh, 33W', 'Android 13', 'USB-C', '90Hz', NULL, '182g', 'Nhựa', 'Stereo', 'Có', 'Mở khóa vân tay cạnh bên'),
(25, 25, '6.8 AMOLED', 'Snapdragon 8 Gen 2', '12GB', '256GB512GB', '108MP32MP', '5000mAh, 45W', 'Android 13', 'USB-C', '120Hz', 'IP68', '234g', 'Kính, kim loại', 'Stereo', 'Có', 'Hỗ trợ bút S Pen, chống nước'),
(26, 26, '6.7 OLED', 'Apple M2', '8GB', '256GB512GB', '12MP12MP', 'Unknown', 'iPadOS 16', 'USB-C', '120Hz', NULL, '470g', 'Nhôm', 'Stereo', 'Không', 'Face ID, hỗ trợ Magic Keyboard'),
(27, 27, '6.8 AMOLED', 'Snapdragon 8 Gen 1', '8GB', '128GB256GB', '50MP32MP', '4700mAh, 80W', 'Android 12', 'USB-C', '120Hz', 'IP53', '204g', 'Nhôm', 'Stereo', 'Có', 'Vân tay dưới màn, loa JBL'),
(28, 28, '6.5 AMOLED', 'Snapdragon 695', '8GB', '128GB', '64MP16MP', '5000mAh, 33W', 'Android 13', 'USB-C', '120Hz', NULL, '189g', 'Nhựa', 'Stereo', 'Có', 'Vân tay cạnh bên, loa kép'),
(29, 29, '6.6 AMOLED', 'Snapdragon 870', '8GB', '256GB', '50MP16MP', '4700mAh, 67W', 'Android 12', 'USB-C', '120Hz', NULL, '196g', 'Nhôm', 'Stereo', 'Có', 'Vân tay dưới màn, HDR10+'),
(30, 30, '6.5 AMOLED', 'Snapdragon 732G', '6GB', '128GB', '64MP16MP', '5020mAh, 33W', 'Android 11', 'USB-C', '120Hz', NULL, '193g', 'Nhựa', 'Stereo', 'Có', 'Pin lớn, màn hình mượt'),
(32, 32, '6.8 AMOLED', 'Snapdragon 8 Gen 2', '12GB', '256GB512GB', '200MP32MP', '5000mAh, 45W', 'Android 13', 'USB-C', '120Hz', 'IP68', '234g', 'Kính, nhôm', 'Stereo', 'Có', 'Bút S Pen, camera 200MP'),
(59, 59, '6.43 AMOLED', 'Snapdragon 680', '6GB', '128GB', '50MP8MP', '5000mAh, 33W', 'Android 12', 'USB-C', '90Hz', NULL, '180g', 'Nhựa', 'Mono', 'Không', 'Giá mềm, màn đẹp'),
(61, 61, 'NA', 'NA', 'NA', 'NA', 'NA', '29 giờ (kèm hộp)', 'NA', 'USB-C', 'NA', 'IPX7', 'NA', 'Nhựa', 'Stereo', 'Có', 'Chống ồn chủ động (ANC)'),
(62, 62, 'NA', 'NA', 'NA', 'NA', 'NA', '30 giờ (kèm hộp)', 'NA', 'USB-C', 'NA', 'IP54', 'NA', 'Nhựa', 'Stereo', 'Có', 'Chống ồn thụ động'),
(63, 63, 'NA', 'NA', 'NA', 'NA', 'NA', '28 giờ (kèm hộp)', 'NA', 'USB-C', 'NA', 'IP54', 'NA', 'Nhựa', 'Stereo', 'Có', 'Chống ồn chủ động (ANC)'),
(64, 64, 'NA', 'NA', 'NA', 'NA', 'NA', '25 giờ (kèm hộp)', 'NA', 'USB-C', 'NA', 'IP54', 'NA', 'Nhựa', 'Stereo', 'Có', 'Chống ồn thụ động'),
(65, 65, 'NA', 'NA', 'NA', 'NA', 'NA', '28 giờ (kèm hộp)', 'NA', 'USB-C', 'NA', 'IPX7', 'NA', 'Nhựa', 'Stereo', 'Có', 'Chống ồn chủ động (ANC)'),
(91, 91, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Không', 'Dài 1m'),
(92, 92, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Không', 'Dài 1m'),
(93, 93, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Không', 'MFI, dài 1m'),
(94, 94, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Không', 'Dài 1m'),
(95, 95, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Không', 'Dài 1m'),
(96, 96, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Không', 'Dài 1m'),
(97, 97, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Có', 'Công suất 65W'),
(98, 98, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Có', 'Công suất 35W'),
(99, 99, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Có', 'Công suất 30W'),
(100, 100, 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'NA', 'Không', 'NA', 'NA', 'NA', 'Có', 'Công suất 30W');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD KEY `chitiethoadon_ibfk_1` (`idCTSP`),
  ADD KEY `hd-cthd` (`idHD`);

--
-- Indexes for table `chitietkhuyenmai`
--
ALTER TABLE `chitietkhuyenmai`
  ADD PRIMARY KEY (`idHD`,`MAKHUYENMAI`),
  ADD KEY `fk_ctkm_km` (`MAKHUYENMAI`);

--
-- Indexes for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD KEY `pn-ctpn` (`idPN`),
  ADD KEY `sp-ctpn` (`idCTSP`);

--
-- Indexes for table `chitietsanpham`
--
ALTER TABLE `chitietsanpham`
  ADD PRIMARY KEY (`idCTSP`),
  ADD KEY `idSP` (`idSP`);

--
-- Indexes for table `chucnang`
--
ALTER TABLE `chucnang`
  ADD PRIMARY KEY (`idCN`);

--
-- Indexes for table `danhmuc`
--
ALTER TABLE `danhmuc`
  ADD PRIMARY KEY (`idDM`);

--
-- Indexes for table `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`idHD`),
  ADD KEY `TK-HD` (`idTK`),
  ADD KEY `Status-hd` (`TRANGTHAI`),
  ADD KEY `KH_HD` (`idkh`);

--
-- Indexes for table `hang`
--
ALTER TABLE `hang`
  ADD PRIMARY KEY (`idHANG`);

--
-- Indexes for table `imei`
--
ALTER TABLE `imei`
  ADD PRIMARY KEY (`idCTSP`),
  ADD UNIQUE KEY `imei` (`imei`);

--
-- Indexes for table `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`idkh`);

--
-- Indexes for table `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD PRIMARY KEY (`MAKHUYENMAI`),
  ADD KEY `hang-km` (`HANG`),
  ADD KEY `dm-km` (`DANHMUC`);

--
-- Indexes for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`idNCC`);

--
-- Indexes for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD KEY `tk-nv` (`idTK`);

--
-- Indexes for table `phanquyen`
--
ALTER TABLE `phanquyen`
  ADD KEY `quyen-pq` (`idQUYEN`),
  ADD KEY `cn-pq` (`idCN`);

--
-- Indexes for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`idPN`),
  ADD KEY `ncc-pn` (`idNCC`);

--
-- Indexes for table `quyen`
--
ALTER TABLE `quyen`
  ADD PRIMARY KEY (`idQUYEN`);

--
-- Indexes for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`idSP`),
  ADD KEY `DM-SP` (`idDM`),
  ADD KEY `Hang` (`HANG`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`idTK`),
  ADD KEY `quyen-tk` (`idQUYEN`);

--
-- Indexes for table `thongsokythuat`
--
ALTER TABLE `thongsokythuat`
  ADD PRIMARY KEY (`idKT`),
  ADD KEY `idSP` (`idSP`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chitietsanpham`
--
ALTER TABLE `chitietsanpham`
  MODIFY `idCTSP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=172;

--
-- AUTO_INCREMENT for table `chucnang`
--
ALTER TABLE `chucnang`
  MODIFY `idCN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- AUTO_INCREMENT for table `danhmuc`
--
ALTER TABLE `danhmuc`
  MODIFY `idDM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `donhang`
--
ALTER TABLE `donhang`
  MODIFY `idHD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT for table `hang`
--
ALTER TABLE `hang`
  MODIFY `idHANG` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `idkh` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `khuyenmai`
--
ALTER TABLE `khuyenmai`
  MODIFY `MAKHUYENMAI` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `idNCC` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `phieunhap`
--
ALTER TABLE `phieunhap`
  MODIFY `idPN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `quyen`
--
ALTER TABLE `quyen`
  MODIFY `idQUYEN` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `idSP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT for table `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `idTK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `thongsokythuat`
--
ALTER TABLE `thongsokythuat`
  MODIFY `idKT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`idCTSP`) REFERENCES `chitietsanpham` (`idCTSP`),
  ADD CONSTRAINT `hd-cthd` FOREIGN KEY (`idHD`) REFERENCES `donhang` (`idHD`);

--
-- Constraints for table `chitietkhuyenmai`
--
ALTER TABLE `chitietkhuyenmai`
  ADD CONSTRAINT `fk_ctkm_hd` FOREIGN KEY (`idHD`) REFERENCES `donhang` (`idHD`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ctkm_km` FOREIGN KEY (`MAKHUYENMAI`) REFERENCES `khuyenmai` (`MAKHUYENMAI`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD CONSTRAINT `ctsp-ctpn` FOREIGN KEY (`idCTSP`) REFERENCES `chitietsanpham` (`idCTSP`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pn-ctpn` FOREIGN KEY (`idPN`) REFERENCES `phieunhap` (`idPN`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `chitietsanpham`
--
ALTER TABLE `chitietsanpham`
  ADD CONSTRAINT `chitietsanpham_ibfk_1` FOREIGN KEY (`idSP`) REFERENCES `sanpham` (`idSP`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `KH_HD` FOREIGN KEY (`idkh`) REFERENCES `khachhang` (`idkh`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `TK-HD` FOREIGN KEY (`idTK`) REFERENCES `taikhoan` (`idTK`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `imei`
--
ALTER TABLE `imei`
  ADD CONSTRAINT `imei_ibfk_1` FOREIGN KEY (`idCTSP`) REFERENCES `chitietsanpham` (`idCTSP`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD CONSTRAINT `dm-km` FOREIGN KEY (`DANHMUC`) REFERENCES `danhmuc` (`idDM`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `hang-km` FOREIGN KEY (`HANG`) REFERENCES `hang` (`idHANG`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD CONSTRAINT `tk-nv` FOREIGN KEY (`idTK`) REFERENCES `taikhoan` (`idTK`);

--
-- Constraints for table `phanquyen`
--
ALTER TABLE `phanquyen`
  ADD CONSTRAINT `cn-pq` FOREIGN KEY (`idCN`) REFERENCES `chucnang` (`idCN`),
  ADD CONSTRAINT `quyen-pq` FOREIGN KEY (`idQUYEN`) REFERENCES `quyen` (`idQUYEN`);

--
-- Constraints for table `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `ncc-pn` FOREIGN KEY (`idNCC`) REFERENCES `nhacungcap` (`idNCC`);

--
-- Constraints for table `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `DM-SP` FOREIGN KEY (`idDM`) REFERENCES `danhmuc` (`idDM`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Hang-SP` FOREIGN KEY (`HANG`) REFERENCES `hang` (`idHANG`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD CONSTRAINT `quyen-tk` FOREIGN KEY (`idQUYEN`) REFERENCES `quyen` (`idQUYEN`);

--
-- Constraints for table `thongsokythuat`
--
ALTER TABLE `thongsokythuat`
  ADD CONSTRAINT `thongsokythuat_ibfk_1` FOREIGN KEY (`idSP`) REFERENCES `sanpham` (`idSP`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
