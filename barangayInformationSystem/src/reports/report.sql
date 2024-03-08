-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Mar 08, 2024 at 08:08 AM
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
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report` (
  `report_id` int NOT NULL,
  `resident_id` int DEFAULT NULL,
  `first_name` text NOT NULL,
  `middle_name` text NOT NULL,
  `last_name` text NOT NULL,
  `suffix` text NOT NULL,
  `report_type` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `date_recorded` date NOT NULL,
  `email` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone_num` bigint NOT NULL,
  `reason` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `status` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `report_status` int NOT NULL,
  `InOutBarangay` text NOT NULL,
  PRIMARY KEY (`report_id`),
  KEY `report_ibfk_1` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`report_id`, `resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `report_type`, `date_recorded`, `email`, `phone_num`, `reason`, `status`, `report_status`, `InOutBarangay`) VALUES
(11, NULL, 'John', 'Michael', 'Doe', 'Jr', 'Blotter', '2024-02-26', 'john.doe@email.com', 631234567890, 'Sample', 'Solved', 0, 'In'),
(12, NULL, 'Jovel', 'Buena', 'Portuguez', '', 'Blotter', '2024-02-26', 'jovel@email.com', 9777862172, 'Sample', 'Pending', 1, 'Out'),
(13, NULL, 'Alice', 'Marie', 'Smith', '', 'Maintanance', '2024-02-26', 'alice.smith@email.com', 639876543210, 'Sample', 'pending', 1, 'In'),
(14, NULL, 'Mark', 'Anthony', 'Rodriguez', '', 'Leakage', '2024-02-27', 'mark@email.com', 9777862172, 'Sample', 'pending', 1, 'Out'),
(15, NULL, 'Liam', 'Alexander', 'Taylor', '', 'Blotter', '2024-02-27', 'liam.taylor@email.com', 635556667777, 'Sample', 'Solved', 0, 'In'),
(16, NULL, 'Ava', 'Elizabeth', 'Gomez', '', 'Feedback', '2024-02-27', 'ava.gomez@email.com', 638889990000, 'Sample', 'pending', 1, 'In'),
(17, NULL, 'Isabella', 'Rose', 'Chen', '', 'Maintanance', '2024-02-27', 'isabella.chen@email.com', 637776665555, 'Sample', 'Pending', 1, 'In'),
(18, NULL, 'Jovel', 'Buena', 'Pogi', '', 'Blotter', '2024-02-27', 'sample@email.com', 9777862172, 'Sample', 'pending', 1, 'In'),
(19, NULL, 'John', 'Michael', 'Doe', 'Jr', 'Blotter', '2024-02-27', 'john.doe@email.com', 631234567890, 'Sample', 'pending', 1, 'In'),
(20, NULL, 'Sample', 'Sample', 'Sample', '', 'Blotter', '2024-03-05', 'sample@email.com', 9777862172, 'Sample', 'pending', 1, 'Out'),
(21, NULL, 'Jovel', 'Buena', 'Portuguez', '', 'Maintanance', '2024-03-07', 'jovel@email.com', 9777862172, 'Sample Report', 'pending', 1, 'Out'),
(22, NULL, 'Alice', 'Marie', 'Smith', '', 'Blotter', '2024-03-07', 'alice.smith@email.com', 639876543210, 'Sample Report', 'pending', 1, 'In'),
(23, NULL, 'Sophia', 'Olivia', 'Lee', '', 'Feedback', '2024-03-07', 'sophia.lee@email.com', 639992223344, 'Sample Report', 'pending', 1, 'In'),
(24, NULL, 'Mark', 'Anthony', 'Rodriguez', '', 'Leakage', '2024-03-07', 'mark@email.com', 9777862173, 'Sample Report', 'Solved', 0, 'Out'),
(25, NULL, 'Mark', 'Anthony', 'Rodriguez', '', 'Leakage', '2024-03-07', 'mark@email.com', 9777862172, 'Sample Report', 'Solved', 1, 'Out');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`resident_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
