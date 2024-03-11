-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 11, 2024 at 10:28 AM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bbis`
--

-- --------------------------------------------------------

--
-- Table structure for table `business`
--

DROP TABLE IF EXISTS `business`;
CREATE TABLE IF NOT EXISTS `business` (
  `business_id` int NOT NULL AUTO_INCREMENT,
  `barangay_id` int NOT NULL DEFAULT '1',
  `business_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `business_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `first_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `owner_phone_num` bigint NOT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `monthly_income` int NOT NULL,
  `date_establishment` date NOT NULL,
  `tin` int NOT NULL,
  `vat_status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `num_employees` int NOT NULL,
  `date_registered` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `owner_email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `active_status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`business_id`) USING BTREE,
  KEY `barangay_id` (`barangay_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `business`
--

INSERT INTO `business` (`business_id`, `barangay_id`, `business_name`, `business_type`, `first_name`, `middle_name`, `last_name`, `owner_phone_num`, `address`, `monthly_income`, `date_establishment`, `tin`, `vat_status`, `num_employees`, `date_registered`, `owner_email`, `active_status`) VALUES
(77, 28, 'Astrologo Business', 'Foods', 'James Alfred', 'Astrologo', 'Coronel', 1234567890, 'sfsdffsdfd', 100000, '2024-02-25', 123456789, 'Registered', 7, '2024-03-09 00:00:00', 'jamescoronel530@gmail.com', 'Active'),
(78, 29, 'Jan Business', 'Foods', 'Jan', 'Astrologo', 'Coronel', 9125412456, 'Canaman', 30000, '2024-02-27', 988532346, 'Not Registered', 6, '2024-03-09 00:00:00', 'jancoronel@gmail.com', 'Inactive'),
(79, 30, 'dasdad', 'dsadad', 'Dsdsa', 'TDSAD', 'DASD', 9123456789, 'dasad', 323232, '2024-02-26', 123456789, 'Registered', 231, '2024-03-11 00:00:00', 'ASDSD@gmail.com', 'Inactive'),
(80, 31, 'sdadaasda', 'asdad', 'ddssdad', 'asdadsad', 'sadd', 9123456789, 'daasdad', 3213123, '2023-11-26', 123456789, 'Not Registered', 2321, '2024-03-11 00:00:00', 'sadadasd@gmail.com', 'Active');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
