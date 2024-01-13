-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 12, 2024 at 03:34 AM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
CREATE TABLE IF NOT EXISTS `activity` (
  `activity_id` int NOT NULL,
  `date_of_activity` date NOT NULL,
  `activity_title` text COLLATE utf8mb4_general_ci NOT NULL,
  `description` text COLLATE utf8mb4_general_ci NOT NULL,
  `barangay_id` int NOT NULL,
  PRIMARY KEY (`activity_id`) USING BTREE,
  KEY `barangay_id` (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`activity_id`, `date_of_activity`, `activity_title`, `description`, `barangay_id`) VALUES
(4642, '2023-11-16', 'Tree Planting', 'Planting tree', 101);

-- --------------------------------------------------------

--
-- Table structure for table `activity_photo`
--

DROP TABLE IF EXISTS `activity_photo`;
CREATE TABLE IF NOT EXISTS `activity_photo` (
  `activity_photo_id` int NOT NULL,
  `activity_id` int NOT NULL,
  PRIMARY KEY (`activity_photo_id`),
  KEY `activity_id` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `activity_photo`
--

INSERT INTO `activity_photo` (`activity_photo_id`, `activity_id`) VALUES
(121, 4642),
(4214, 4642);

-- --------------------------------------------------------

--
-- Table structure for table `barangay`
--

DROP TABLE IF EXISTS `barangay`;
CREATE TABLE IF NOT EXISTS `barangay` (
  `barangay_id` int NOT NULL,
  `barangay_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `municipality` text COLLATE utf8mb4_general_ci NOT NULL,
  `province` text COLLATE utf8mb4_general_ci NOT NULL,
  `country` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `barangay`
--

INSERT INTO `barangay` (`barangay_id`, `barangay_name`, `municipality`, `province`, `country`) VALUES
(101, 'Bonifacio', 'San Fernando', 'Camarines Sur', 'Philippines'),
(102, 'La Purisima', 'Lupi', 'Camarines Sur', 'Philippines');

-- --------------------------------------------------------

--
-- Table structure for table `business`
--

DROP TABLE IF EXISTS `business`;
CREATE TABLE IF NOT EXISTS `business` (
  `business_id` int NOT NULL,
  `resident_id` int NOT NULL,
  `business_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `business_type` text COLLATE utf8mb4_general_ci NOT NULL,
  `location` text COLLATE utf8mb4_general_ci NOT NULL,
  `monthly_income` int NOT NULL,
  `monthly_cost` int NOT NULL,
  PRIMARY KEY (`business_id`),
  KEY `resident_id` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `business`
--

INSERT INTO `business` (`business_id`, `resident_id`, `business_name`, `business_type`, `location`, `monthly_income`, `monthly_cost`) VALUES
(4145, 2201124, 'MR Photography', 'Photography', '13.5644964, 123.1444122', 1000, 800);

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
CREATE TABLE IF NOT EXISTS `documents` (
  `document_id` int NOT NULL,
  `document_type` text COLLATE utf8mb4_general_ci NOT NULL,
  `resident_id` int NOT NULL,
  `first_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `contact_number` bigint DEFAULT NULL,
  `email` text COLLATE utf8mb4_general_ci,
  `business_name` text COLLATE utf8mb4_general_ci,
  `business_type` text COLLATE utf8mb4_general_ci,
  `business_address` text COLLATE utf8mb4_general_ci,
  `purpose` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_request` date NOT NULL,
  `date_issued` date NOT NULL,
  `expiry_date` date NOT NULL,
  `person_issued` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_issuance` date NOT NULL,
  `remarks` text COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`document_id`),
  KEY `resident_id` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `household`
--

DROP TABLE IF EXISTS `household`;
CREATE TABLE IF NOT EXISTS `household` (
  `household_id` int NOT NULL,
  `first_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pos_in_the_fam` text COLLATE utf8mb4_general_ci NOT NULL,
  `h_member` int NOT NULL,
  `zone` int NOT NULL,
  PRIMARY KEY (`household_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `household`
--

INSERT INTO `household` (`household_id`, `first_name`, `middle_name`, `last_name`, `pos_in_the_fam`, `h_member`, `zone`) VALUES
(1111, 'Anthony', 'Catalon', 'Rodriguez', 'Father', 4, 5);

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
CREATE TABLE IF NOT EXISTS `logs` (
  `log_id` int NOT NULL,
  `log_date` date NOT NULL,
  `user_id` int NOT NULL,
  `action` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`log_id`, `log_date`, `user_id`, `action`) VALUES
(32421, '2023-12-16', 2201124, 'Log In'),
(52131, '2023-12-16', 2201124, 'Change Password');

-- --------------------------------------------------------

--
-- Table structure for table `official`
--

DROP TABLE IF EXISTS `official`;
CREATE TABLE IF NOT EXISTS `official` (
  `official_id` int NOT NULL,
  `position` text COLLATE utf8mb4_general_ci NOT NULL,
  `barangay_id` int NOT NULL,
  `first_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `term_start` year NOT NULL,
  `term_end` year NOT NULL,
  `phone_num` bigint NOT NULL,
  `email` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`official_id`),
  KEY `barangay_id` (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `official`
--

INSERT INTO `official` (`official_id`, `position`, `barangay_id`, `first_name`, `middle_name`, `last_name`, `term_start`, `term_end`, `phone_num`, `email`) VALUES
(1, 'Punong Barangay', 101, 'Jose San', 'Puring', 'Pascual', 2016, 2023, 9123456789, 'josesan.pascual@gmail.com'),
(2, 'Kagawad', 101, 'Miguel', 'Sancos', 'Dexson', 2016, 2023, 9123456789, 'miguel.dexson@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report` (
  `report_id` int NOT NULL,
  `resident_id` int NOT NULL,
  `name` text COLLATE utf8mb4_general_ci NOT NULL,
  `report_type` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_recorded` date NOT NULL,
  `email` text COLLATE utf8mb4_general_ci NOT NULL,
  `phone_num` bigint NOT NULL,
  `reason` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`report_id`),
  KEY `report_ibfk_1` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`report_id`, `resident_id`, `name`, `report_type`, `date_recorded`, `email`, `phone_num`, `reason`) VALUES
(934893, 2201124, 'Mark Anthony A. Rodriguez', '', '2023-12-16', 'markanthony.rodriguez@gmail.com', 9123456789, 'Broken Street Light');

-- --------------------------------------------------------

--
-- Table structure for table `resident`
--

DROP TABLE IF EXISTS `resident`;
CREATE TABLE IF NOT EXISTS `resident` (
  `resident_id` int NOT NULL,
  `first_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `age` int NOT NULL,
  `birth_date` date NOT NULL,
  `gender` text COLLATE utf8mb4_general_ci NOT NULL,
  `barangay_id` int NOT NULL,
  `nationality` text COLLATE utf8mb4_general_ci NOT NULL,
  `religion` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `blood_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `occupation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `birth_place` text COLLATE utf8mb4_general_ci NOT NULL,
  `civil_status` text COLLATE utf8mb4_general_ci NOT NULL,
  `household_id` int NOT NULL,
  `relation_to_head` text COLLATE utf8mb4_general_ci NOT NULL,
  `weight` int NOT NULL,
  `height` int NOT NULL,
  `education_attainment` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`resident_id`),
  KEY `barangay_id` (`barangay_id`),
  KEY `household_id` (`household_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resident`
--

INSERT INTO `resident` (`resident_id`, `first_name`, `middle_name`, `last_name`, `age`, `birth_date`, `gender`, `barangay_id`, `nationality`, `religion`, `blood_type`, `occupation`, `birth_place`, `civil_status`, `household_id`, `relation_to_head`, `weight`, `height`, `education_attainment`) VALUES
(2201124, 'Mark Anthony', 'Amican', 'Rodriguez', 20, '2003-11-05', 'Male', 101, 'Filipino', 'Born Again', 'O+', '', 'Zone 5, La Purisima, Lupi, Camarines Sur', 'Single', 1111, 'Father', 60, 175, 'Senior High');

-- --------------------------------------------------------

--
-- Table structure for table `treasury`
--

DROP TABLE IF EXISTS `treasury`;
CREATE TABLE IF NOT EXISTS `treasury` (
  `treasury_id` int NOT NULL,
  `barangay_id` int NOT NULL,
  `document_type` text COLLATE utf8mb4_general_ci NOT NULL,
  `first_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `address` text COLLATE utf8mb4_general_ci NOT NULL,
  `sex` text COLLATE utf8mb4_general_ci NOT NULL,
  `birthdate` date NOT NULL,
  `citizenship` text COLLATE utf8mb4_general_ci NOT NULL,
  `tin_no` int DEFAULT NULL,
  `date_issued` date NOT NULL,
  `comm_tax` int NOT NULL,
  `tax_amount` int NOT NULL,
  `total` int NOT NULL,
  PRIMARY KEY (`treasury_id`),
  KEY `barangay_id` (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int NOT NULL,
  `username` text COLLATE utf8mb4_general_ci NOT NULL,
  `password` text COLLATE utf8mb4_general_ci NOT NULL,
  `type` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_created` date NOT NULL,
  `email` text COLLATE utf8mb4_general_ci NOT NULL,
  `phone_num` bigint NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `password`, `type`, `date_created`, `email`, `phone_num`) VALUES
(2201124, 'makky', 'makky', 'Resident', '2023-12-16', 'markanthony.rodriguez@gmail.com', 9123456789);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`barangay_id`) REFERENCES `barangay` (`barangay_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `activity_photo`
--
ALTER TABLE `activity_photo`
  ADD CONSTRAINT `activity_photo_ibfk_1` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`activity_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `business`
--
ALTER TABLE `business`
  ADD CONSTRAINT `business_ibfk_1` FOREIGN KEY (`resident_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `documents`
--
ALTER TABLE `documents`
  ADD CONSTRAINT `documents_ibfk_1` FOREIGN KEY (`resident_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `official`
--
ALTER TABLE `official`
  ADD CONSTRAINT `official_ibfk_1` FOREIGN KEY (`barangay_id`) REFERENCES `barangay` (`barangay_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_ibfk_1` FOREIGN KEY (`resident_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `resident`
--
ALTER TABLE `resident`
  ADD CONSTRAINT `resident_ibfk_1` FOREIGN KEY (`barangay_id`) REFERENCES `barangay` (`barangay_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `resident_ibfk_2` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `treasury`
--
ALTER TABLE `treasury`
  ADD CONSTRAINT `treasury_ibfk_1` FOREIGN KEY (`barangay_id`) REFERENCES `barangay` (`barangay_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
