-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 16, 2025 at 10:32 AM
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

--
-- Indexes for dumped tables
--

--
-- Indexes for table `imei`
--
ALTER TABLE `imei`
  ADD PRIMARY KEY (`idCTSP`),
  ADD UNIQUE KEY `imei` (`imei`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `imei`
--
ALTER TABLE `imei`
  ADD CONSTRAINT `imei_ibfk_1` FOREIGN KEY (`idCTSP`) REFERENCES `chitietsanpham` (`idCTSP`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
