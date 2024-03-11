-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 11, 2024 at 08:11 AM
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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
CREATE TABLE IF NOT EXISTS `activity` (
  `activity_id` int NOT NULL,
  `date_of_activity` date NOT NULL,
  `activity_title` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `barangay_id` int NOT NULL,
  PRIMARY KEY (`activity_id`) USING BTREE,
  KEY `barangay_id` (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `activity`
--

INSERT INTO `activity` (`activity_id`, `date_of_activity`, `activity_title`, `description`, `barangay_id`) VALUES
(12345, '2022-01-05', 'Community Clean-up', 'Cleaning streets and public areas', 101),
(23456, '2022-06-30', 'Community Outreach', 'Providing assistance to the less fortunate', 101),
(34567, '2022-08-20', 'Environment Day', 'Raising awareness about environmental issues', 101),
(45678, '2022-04-20', 'Sports Festival', 'Inter-barangay sports competition', 101),
(54321, '2022-03-15', 'Health and Wellness Seminar', 'Educational session on health and wellness', 101),
(56789, '2022-09-25', 'Blood Donation Drive', 'Encouraging blood donation for the community', 101),
(78901, '2022-07-15', 'Educational Workshop', 'Workshop on skills development', 101),
(87654, '2022-05-25', 'Cultural Night', 'Showcasing local talents and culture', 101),
(89012, '2022-10-30', 'Flea Market', 'Community fundraising event', 101),
(98765, '2022-02-10', 'Tree Planting', 'Planting trees in the barangay park', 101);

-- --------------------------------------------------------

--
-- Table structure for table `admin_user`
--

DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE IF NOT EXISTS `admin_user` (
  `admin_id` int NOT NULL,
  `username` text NOT NULL,
  `password` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `date_created` date NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `admin_user`
--

INSERT INTO `admin_user` (`admin_id`, `username`, `password`, `date_created`) VALUES
(1, 'punongbarangay', 'punongbarangay', '2024-03-07'),
(2, 'kagawad', 'kagawad', '2024-03-10'),
(3, 'secretary', 'secretary', '2024-03-01'),
(4, 'treasurer', 'treasurer', '2024-03-04');

-- --------------------------------------------------------

--
-- Table structure for table `barangay`
--

DROP TABLE IF EXISTS `barangay`;
CREATE TABLE IF NOT EXISTS `barangay` (
  `barangay_id` int NOT NULL,
  `barangay_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `municipality` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `province` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `country` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `postal_code` int NOT NULL,
  PRIMARY KEY (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `barangay`
--

INSERT INTO `barangay` (`barangay_id`, `barangay_name`, `municipality`, `province`, `country`, `postal_code`) VALUES
(101, 'Bonifacio', 'San Fernando', 'Camarines Sur', 'Philippines', 4415);

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
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `business`
--

INSERT INTO `business` (`business_id`, `barangay_id`, `business_name`, `business_type`, `first_name`, `middle_name`, `last_name`, `owner_phone_num`, `address`, `monthly_income`, `date_establishment`, `tin`, `vat_status`, `num_employees`, `date_registered`, `owner_email`, `active_status`) VALUES
(77, 28, 'Astrologo Business', 'Foods', 'James Alfred', 'Astrologo', 'Coronel', 1234567890, 'sfsdffsdfd', 100000, '2024-02-25', 123456789, 'Registered', 7, '2024-03-09 00:00:00', 'jamescoronel530@gmail.com', 'Active'),
(78, 29, 'Jan Business', 'Foods', 'Jan', 'Astrologo', 'Coronel', 9125412456, 'Canaman', 30000, '2024-02-27', 988532346, 'Not Registered', 6, '2024-03-09 00:00:00', 'jancoronel@gmail.com', 'Inactive');

-- --------------------------------------------------------

--
-- Table structure for table `cedula`
--

DROP TABLE IF EXISTS `cedula`;
CREATE TABLE IF NOT EXISTS `cedula` (
  `treasury_Id` int NOT NULL AUTO_INCREMENT,
  `barangay_Id` int NOT NULL,
  `purpose` text COLLATE utf8mb4_general_ci NOT NULL,
  `first_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `address` text COLLATE utf8mb4_general_ci NOT NULL,
  `gender` text COLLATE utf8mb4_general_ci NOT NULL,
  `age` int NOT NULL,
  `tin_no` text COLLATE utf8mb4_general_ci,
  `residency_status` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_issued` date NOT NULL,
  `income` int NOT NULL,
  `community_tax` double DEFAULT NULL,
  `addcomm_tax` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `salaray_tax` double DEFAULT NULL,
  `property_tax` double DEFAULT NULL,
  `salary_total` double DEFAULT NULL,
  `property_total` double DEFAULT NULL,
  `interest` double DEFAULT NULL,
  `final_total` double DEFAULT NULL,
  `height` int NOT NULL,
  `weight` int NOT NULL,
  `cedula_num` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`treasury_Id`),
  KEY `barangay_id` (`barangay_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=98821 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cedula`
--

INSERT INTO `cedula` (`treasury_Id`, `barangay_Id`, `purpose`, `first_name`, `middle_name`, `last_name`, `address`, `gender`, `age`, `tin_no`, `residency_status`, `date_issued`, `income`, `community_tax`, `addcomm_tax`, `total`, `salaray_tax`, `property_tax`, `salary_total`, `property_total`, `interest`, `final_total`, `height`, `weight`, `cedula_num`) VALUES
(1, 101, 'Business', 'Ethan', 'James', 'Brown', 'Bonifacio, San Fernando, Camarines Sur', 'Male', 36, 'None', 'Resident', '2024-03-05', 80000, 5, 80, 96, 6000, 5000, 6, 5, 10, 202, 185, 79, 'CCI2022 876534879'),
(2, 101, 'Permit', 'Wen', 'Paronda', 'Sy', 'Bikal Caramoan, Camarines Sur', 'Female', 20, '67899789896745', 'Non-Resident', '2024-03-05', 1000, 5, 1, 7.1, 500, 600, 0.5, 0.6, 6, 20.2, 164, 54, 'CCI2022 876587890'),
(3, 101, 'Business', 'Isabella', 'Rose', 'Chen', 'Bonifacio, San Fernando, Camarines Sur', 'Female', 30, 'None', 'Resident', '2024-03-05', 90000, 5, 90, 101, 5454, 545, 5.45, 0.55, 34, 236, 168, 56, 'CCI2022 876534897'),
(4, 101, 'Permit', 'Veltiandra', 'Corazon', 'Salcedo', 'Kanto Naga City Camarines Sur', 'Female', 22, 'None', 'Non-Resident', '2024-03-05', 0, 5, 0, 5, 0, 0, 0, 0, 0, 10, 145, 48, 'CCI2022 879087236'),
(5, 101, '', 'Ethan', 'James', 'Brown', 'Bonifacio, San Fernando, Camarines Sur', 'Male', 36, NULL, 'Resident', '2024-03-05', 80000, 5, 80, 85, 0, 0, 0, 0, 0, 170, 185, 79, 'CCI2022 891267124'),
(7, 101, 'Permit', 'Ethan', 'Michael', 'Davis', 'Bonifacio, San Fernando, Camarines Sur', 'Male', 37, 'None', 'Resident', '2024-03-05', 130000, 5, 130, 135, 0, 0, 0, 0, 0, 270, 175, 76, 'CCI2022 893423193'),
(8, 101, 'Business', 'Mia', 'Rose', 'Garcia', 'Bonifacio, San Fernando, Camarines Sur', 'Female', 29, 'None', 'Resident', '2024-03-05', 60000, 5, 60, 65, 0, 0, 0, 0, 0, 130, 160, 51, 'CCI2022 891234905'),
(9, 101, 'Business', 'David', 'William', 'Johnson', 'Bonifacio, San Fernando, Camarines Sur', 'Male', 42, '346523456745', 'Resident', '2024-03-05', 120000, 5, 120, 125, 0, 0, 0, 0, 0, 250, 190, 86, 'CCI2022 129034891');

-- --------------------------------------------------------

--
-- Table structure for table `certification`
--

DROP TABLE IF EXISTS `certification`;
CREATE TABLE IF NOT EXISTS `certification` (
  `id` varchar(12) NOT NULL,
  `resident_id` int NOT NULL,
  `firstName` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `middleName` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `lastName` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `suffix` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `address` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone_num` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `email` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `certification_type` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `date_requested` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `purpose` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `resident_id` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `certification`
--

INSERT INTO `certification` (`id`, `resident_id`, `firstName`, `middleName`, `lastName`, `suffix`, `address`, `phone_num`, `email`, `certification_type`, `date_requested`, `purpose`) VALUES
('CTFT24-11568', 1, 'Mark Anthony', 'Amican', 'Rodriguez', '', 'Zone 5, Bonifacio, San Fernando, Camarines Sur', '09637752208', 'markanthony.rodriguez@unc.edu.ph', 'Indigency', '2024-03-05 04:23:08', 'Medical'),
('CTFT24-12345', 2, 'Mark Anthony', 'Amican', 'Rodriguez', '', 'Zone 5, La Purisima, Lupi, Camarines Sur', '09637752208', 'markanthony.rodriguez@unc.edu.ph', 'Clearance', '2024-03-05 03:52:50', 'Scholarship'),
('CTFT24-28994', 1, 'Bjorn', 'Pili', 'Realubit', 'Jr.', 'Zone 5, Bonifacio, San Fernando, Camarines Sur', '09123456789', 'bjorn.realubit@unc.edu.ph', 'Clearance', '2024-03-05 18:19:50', 'Hehehe'),
('CTFT24-31297', 3, 'Carl Lester', '', 'Cruz', '', 'Zone 5, Bonifacio, San Fernando, Camarines Sur', '09123456783', 'enicneicnein@gmail.com', 'Clearance', '2024-03-11 11:47:57', 'Scholarship'),
('CTFT24-82243', 1, 'Jovel', 'Buena', 'Portuguez', '', 'Zone 4, Bonifacio, San Fernando, Camarines Sur', '09123456789', 'jovel.portuguez@unc.edu.ph', 'Clearance', '2024-03-05 11:48:47', 'Medical');

-- --------------------------------------------------------

--
-- Table structure for table `certificationtreasury`
--

DROP TABLE IF EXISTS `certificationtreasury`;
CREATE TABLE IF NOT EXISTS `certificationtreasury` (
  `id` varchar(12) NOT NULL,
  `document_id` varchar(12) NOT NULL,
  `stamp_fee` float NOT NULL,
  `document_cost` float NOT NULL,
  `amount_pay` float NOT NULL,
  `date_issued` date NOT NULL,
  `ctc_issued_date` date NOT NULL,
  `ctc_fee` float NOT NULL,
  `ctc_num` varchar(9) NOT NULL,
  `ctc_address_issued` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `document_id` (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `certificationtreasury`
--

INSERT INTO `certificationtreasury` (`id`, `document_id`, `stamp_fee`, `document_cost`, `amount_pay`, `date_issued`, `ctc_issued_date`, `ctc_fee`, `ctc_num`, `ctc_address_issued`) VALUES
('TRPT24-39238', 'CTFT24-82243', 100, 10, 200, '2024-03-11', '2024-03-03', 30, '513413563', 'Bonifacio, San Fernando, Camarines Sur'),
('TRPT24-50192', 'CTFT24-31297', 30, 30, 100, '2024-03-11', '2024-03-05', 30, '123456789', 'Sooc, Lupi, Camarines Sur');

-- --------------------------------------------------------

--
-- Stand-in structure for view `document_paid`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `document_paid`;
CREATE TABLE IF NOT EXISTS `document_paid` (
`id` varchar(12)
,`doc` mediumtext
,`address` mediumtext
,`purpose` mediumtext
,`OR` varchar(12)
,`stamp_fee` float
,`document_cost` float
,`amount_pay` float
,`date_issued` date
,`ctc_issued_date` date
,`ctc_fee` float
,`ctc_num` varchar(9)
,`ctc_address_issued` mediumtext
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `existresident`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `existresident`;
CREATE TABLE IF NOT EXISTS `existresident` (
`resident_id` int
,`first_name` text
,`middle_name` text
,`last_name` text
,`suffix` text
,`age` int
,`phone_num` bigint
,`email` text
,`birth_date` date
,`gender` text
,`zone` int
,`barangay_id` int
,`voter_status` text
,`nationality` text
,`religion` text
,`blood_type` text
,`occupation` text
,`source_of_income` text
,`living_duration` int
,`birth_place` text
,`civil_status` text
,`household_id` int
,`relation_to_family_head` text
,`weight` int
,`height` int
,`education_attainment` text
,`with_disability` text
,`personal_income` int
,`mother's_name` text
,`mother's_phone_num` bigint
,`father's_name` text
,`father's_phone_num` bigint
,`Status` text
,`date_registered` datetime
);

-- --------------------------------------------------------

--
-- Table structure for table `household`
--

DROP TABLE IF EXISTS `household`;
CREATE TABLE IF NOT EXISTS `household` (
  `household_id` int NOT NULL,
  `first_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `middle_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `last_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `suffix` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `civil_status` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `pos_in_the_fam` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `h_member` int NOT NULL,
  `zone` int NOT NULL,
  `monthly_income` int NOT NULL,
  `date_registered` date NOT NULL,
  PRIMARY KEY (`household_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `household`
--

INSERT INTO `household` (`household_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `civil_status`, `pos_in_the_fam`, `h_member`, `zone`, `monthly_income`, `date_registered`) VALUES
(1001, 'John', 'Doe', 'Smith', 'Jr.', 'Married', 'Father\r\n', 5, 6, 76305, '2013-02-05'),
(1002, 'Jane', 'Monzerille', 'Doe', '', 'Married', 'Mother', 4, 2, 50995, '2015-01-14'),
(1003, 'Bob', '', 'Johnson', '', 'Married', 'Father', 3, 1, 26060, '2012-06-12'),
(1004, 'Alice', 'Santos', 'Williams', '', 'Married', 'Mother', 6, 3, 77314, '2021-09-26'),
(1005, 'Charlie', '', 'Brown', '', 'Married', 'Father', 2, 4, 8391, '2020-07-30'),
(1006, 'John', 'Michael', 'Doe', '', 'Married', 'Father', 4, 3, 10013, '2018-12-04'),
(1007, 'Jane', 'Elizabeth', 'Smith', '', 'Married', 'Mother', 2, 6, 24890, '2017-12-26'),
(1009, 'Emily', 'Rose', 'Williams', '', 'Married', 'Mother', 3, 5, 97387, '2018-05-24'),
(1010, 'Daniel', 'Joseph', 'Miller', '', 'Married', 'Father', 2, 1, 3700, '2011-03-30'),
(1011, 'Sarah', 'Louise', 'Anderson', '', 'Married', 'Mother', 4, 3, 26337, '2011-09-04'),
(1012, 'Michael', 'James', 'Taylor', '', 'Married', 'Father', 2, 6, 20584, '2021-03-28'),
(1013, 'Emma', 'Grace', 'Clark', '', 'Married', 'Mother', 1, 2, 23908, '2014-01-23'),
(1014, 'Matthew', 'William', 'Brown', '', 'Married', 'Father', 3, 5, 57790, '2019-08-06'),
(1015, 'Olivia', 'Rose', 'Moore', '', 'Married', 'Mother', 2, 1, 17223, '2015-05-31'),
(1016, 'Benjamin', 'Robert', 'Ward', '', 'Married', 'Father', 4, 4, 12744, '2020-01-27'),
(1017, 'Ava', 'Sophia', 'Evans', '', 'Married', 'Mother', 2, 7, 12054, '2014-03-22'),
(1018, 'Ethan', 'Christopher', 'Hill', '', 'Married', 'Father', 1, 3, 22034, '2017-02-25'),
(1019, 'Isabella', 'Grace', 'Turner', '', 'Married', 'Mother', 3, 2, 74010, '2016-06-19'),
(1020, 'Mason', 'Alexander', 'Mitchell', '', 'Married', 'Mother', 2, 5, 3945, '2019-01-27'),
(1021, 'Grace', 'Oliver', 'Walker', '', 'Married', 'Mother', 4, 3, 97697, '2014-10-09'),
(1022, 'Liam', 'Andrew', 'Fisher', '', 'Married', 'Father', 2, 6, 76648, '2019-08-14'),
(1023, 'Chloe', 'Madison', 'Bennett', '', 'Married', 'Father', 1, 2, 90148, '2014-05-24'),
(1024, 'Noah', 'Elijah', 'Carter', '', 'Married', 'Father', 3, 5, 20795, '2016-07-11'),
(1025, 'Ava', 'Brooklyn', 'Sanders', '', 'Married', 'Mother', 2, 1, 33530, '2016-01-08'),
(1026, 'Elijah', 'Gabriel', 'Murray', '', 'Married', 'Mother', 4, 4, 5267, '2020-04-12'),
(2132, 'Francia ', 'Revilla', 'Booc', '', 'Single Mother', 'Mother', 5, 4, 423423, '2024-02-19');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
CREATE TABLE IF NOT EXISTS `logs` (
  `log_id` int NOT NULL,
  `log_date` date NOT NULL,
  `admin_id` int NOT NULL,
  `action` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `user_id` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- --------------------------------------------------------

--
-- Table structure for table `official`
--

DROP TABLE IF EXISTS `official`;
CREATE TABLE IF NOT EXISTS `official` (
  `official_id` int NOT NULL,
  `position` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `barangay_id` int NOT NULL,
  `first_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `middle_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `last_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `gender` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `term_start` year NOT NULL,
  `term_end` year NOT NULL,
  `phone_num` bigint NOT NULL,
  `email` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`official_id`),
  KEY `barangay_id` (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `official`
--

INSERT INTO `official` (`official_id`, `position`, `barangay_id`, `first_name`, `middle_name`, `last_name`, `gender`, `term_start`, `term_end`, `phone_num`, `email`) VALUES
(1, 'Punong Barangay', 101, 'Juan', 'Cruz', 'Santos', 'Male', '2022', '2025', 639112233445, 'juan.santos@example.com'),
(2, 'Kagawad', 101, 'Maria', 'Luz', 'Garcia', 'Female', '2022', '2025', 639998877665, 'maria.garcia@example.com'),
(3, 'Barangay Secretary', 101, 'Antonio', 'Santos', 'Reyes', 'Male', '2022', '2025', 639456789012, 'antonio.reyes@example.com'),
(4, 'Barangay Treasurer', 101, 'Elena', 'Lim', 'Torres', 'Female', '2022', '2025', 639765432109, 'elena.torres@example.com'),
(5, 'SK Chairperson', 101, 'Mark', 'Anthony', 'Cruz', 'Male', '2022', '2025', 639876543210, 'mark.cruz@example.com'),
(6, 'Kagawad', 101, 'Rosa', 'Marie', 'Lopez', 'Female', '2022', '2025', 639112233445, 'rosa.lopez@example.com'),
(7, 'Kagawad', 101, 'Fernando', 'Mendoza', 'Gomez', 'Male', '2022', '2025', 639998877665, 'fernando.gomez@example.com'),
(8, 'Kagawad', 101, 'Carmen', 'Santiago', 'Vargas', 'Female', '2022', '2025', 639456789012, 'carmen.vargas@example.com'),
(9, 'Kagawad', 101, 'Ramon', 'Gonzales', 'Cruz', 'Male', '2022', '2025', 639765432109, 'ramon.cruz@example.com'),
(10, 'SK Kagawad', 101, 'Sofia', 'Isabel', 'Luna', 'Female', '2022', '2025', 639876543210, 'sofia.luna@example.com');

-- --------------------------------------------------------

--
-- Table structure for table `permit`
--

DROP TABLE IF EXISTS `permit`;
CREATE TABLE IF NOT EXISTS `permit` (
  `id` varchar(12) NOT NULL,
  `resident_id` int NOT NULL,
  `firstName` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `middleName` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `lastName` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `suffix` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `address` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `phone_num` varchar(20) NOT NULL,
  `email` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `business_name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `business_type` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `business_address` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `permit_type` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `date_requested` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `purpose` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `resident_id` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `permit`
--

INSERT INTO `permit` (`id`, `resident_id`, `firstName`, `middleName`, `lastName`, `suffix`, `address`, `phone_num`, `email`, `business_name`, `business_type`, `business_address`, `permit_type`, `date_requested`, `purpose`) VALUES
('PRMT24-63684', 1, 'Mark Anthony', 'Amican', 'Rodriguez', '', 'Zone 5, Bonifacio, San Fernando, Camarines Sur', '09637752208', 'markanthony.rodriguez@unc.edu.ph', 'Mark Cafe', 'Cafe', 'Bonifacio, San Fernando, Camarines Sur', 'Business Clearance', '2024-03-05 03:52:58', 'Renewal'),
('PRMT24-92694', 1, 'Kyle Josh', 'Bagacina', 'Pornillos', '', 'Zone 1, Bonifacio, San Fernando, Camarines Sur', '09414514563', 'kylejosh.pornillos@unc.edu.ph', 'Kyle Agimat', 'IT Services', 'Bonifacio, San Fernando, Camarines Sur', 'Business Clearance', '2024-03-05 18:27:53', 'Renewal');

-- --------------------------------------------------------

--
-- Table structure for table `permittreasury`
--

DROP TABLE IF EXISTS `permittreasury`;
CREATE TABLE IF NOT EXISTS `permittreasury` (
  `id` varchar(12) NOT NULL,
  `document_id` varchar(12) NOT NULL,
  `stamp_fee` float NOT NULL,
  `document_cost` float NOT NULL,
  `amount_pay` float NOT NULL,
  `date_issued` date NOT NULL,
  `ctc_issued_date` date NOT NULL,
  `ctc_fee` float NOT NULL,
  `ctc_num` varchar(9) NOT NULL,
  `ctc_address_issued` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `document_id` (`document_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

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
(12, NULL, 'Jovel', 'Buena', 'Portuguez', '', 'Blotter', '2024-02-26', 'jovel@email.com', 9777862172, 'Sample', 'Solved', 0, 'Out'),
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

-- --------------------------------------------------------

--
-- Stand-in structure for view `requesteddocs`
-- (See below for the actual view)
--
DROP VIEW IF EXISTS `requesteddocs`;
CREATE TABLE IF NOT EXISTS `requesteddocs` (
`id` varchar(12)
,`firstName` mediumtext
,`middleName` mediumtext
,`lastName` mediumtext
,`suffix` mediumtext
,`first_name` mediumtext
,`middle_name` mediumtext
,`last_name` mediumtext
,`rqstSuffix` mediumtext
,`label` varchar(22)
,`document_type` mediumtext
,`date_requested` datetime
);

-- --------------------------------------------------------

--
-- Table structure for table `resident`
--

DROP TABLE IF EXISTS `resident`;
CREATE TABLE IF NOT EXISTS `resident` (
  `resident_id` int NOT NULL,
  `first_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `last_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `suffix` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `age` int NOT NULL,
  `phone_num` bigint DEFAULT NULL,
  `email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `birth_date` date NOT NULL,
  `gender` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `zone` int NOT NULL,
  `barangay_id` int NOT NULL,
  `voter_status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nationality` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `religion` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `blood_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `occupation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `source_of_income` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `living_duration` int NOT NULL,
  `birth_place` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `civil_status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `household_id` int NOT NULL,
  `relation_to_family_head` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `weight` int NOT NULL,
  `height` int NOT NULL,
  `education_attainment` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `with_disability` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `personal_income` int DEFAULT NULL,
  `mother's_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mother's_phone_num` bigint DEFAULT NULL,
  `father's_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `father's_phone_num` bigint DEFAULT NULL,
  `Status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date_registered` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`resident_id`),
  KEY `barangay_id` (`barangay_id`),
  KEY `household_id` (`household_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resident`
--

INSERT INTO `resident` (`resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `age`, `phone_num`, `email`, `birth_date`, `gender`, `zone`, `barangay_id`, `voter_status`, `nationality`, `religion`, `blood_type`, `occupation`, `source_of_income`, `living_duration`, `birth_place`, `civil_status`, `household_id`, `relation_to_family_head`, `weight`, `height`, `education_attainment`, `with_disability`, `personal_income`, `mother's_name`, `mother's_phone_num`, `father's_name`, `father's_phone_num`, `Status`, `date_registered`) VALUES
(1223, 'weneilyn ', 'ppaa', 'paronda', '', 20, 9123456789, 'graham@gmail.com', '2004-02-18', 'Female', 3, 101, 'Not Registered', 'filipino', 'born again', 'O', 'none', 'allowance', 21, 'dnahdadsad', 'Legally Married', 1011, 'daughter', 56, 163, 'College Undergraduate', 'Yes', 3000, 'zsdaddsds', 9123456789, 'dasdzdz', 9123456789, 'Resident', '2024-03-11 13:07:38'),
(1234, 'Graham russell', 'acuesta', 'gonzales', '', 20, 9123456789, 'graham@gmail.com', '2004-02-18', 'Male', 1, 101, 'Not Registered', 'filipino', 'Catholic', 'A', 'none', 'allowance', 12, 'buenasuerte', 'Single', 1011, 'son', 50, 160, 'Elementary Undergraduate', 'Yes', 1200, 'sfcasfasfsafafsafsaf', 9123456789, 'sfasfasfsafasfsa', 9123456789, 'Resident ', '2024-03-11 13:07:38'),
(3214, 'mark anthony', 'sdd', 'rofriguez', '', 20, 9123456789, 'mark@gmail.com', '2004-02-18', 'Male', 6, 101, 'Registered', 'filipino', 'catholic', 'B Rh+', 'adada', 'allowance', 20, 'asdasasda', 'Single', 1011, 'son', 34, 163, 'HighSchool Graduate', 'No', 5000, 'aadada', 9123456789, 'dasdasdsad', 9123456789, 'Resident ', '2024-03-11 13:07:38');

-- --------------------------------------------------------

--
-- Structure for view `document_paid`
--
DROP TABLE IF EXISTS `document_paid`;

DROP VIEW IF EXISTS `document_paid`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `document_paid`  AS SELECT `c`.`id` AS `id`, `c`.`certification_type` AS `doc`, `c`.`address` AS `address`, `c`.`purpose` AS `purpose`, `cr`.`id` AS `OR`, `cr`.`stamp_fee` AS `stamp_fee`, `cr`.`document_cost` AS `document_cost`, `cr`.`amount_pay` AS `amount_pay`, `cr`.`date_issued` AS `date_issued`, `cr`.`ctc_issued_date` AS `ctc_issued_date`, `cr`.`ctc_fee` AS `ctc_fee`, `cr`.`ctc_num` AS `ctc_num`, `cr`.`ctc_address_issued` AS `ctc_address_issued` FROM (`certification` `c` join `certificationtreasury` `cr` on((`c`.`id` = `cr`.`document_id`)))union all select `p`.`id` AS `id`,`p`.`permit_type` AS `doc`,`p`.`address` AS `address`,`p`.`purpose` AS `purpose`,`pr`.`id` AS `OR`,`pr`.`stamp_fee` AS `stamp_fee`,`pr`.`document_cost` AS `document_cost`,`pr`.`amount_pay` AS `amount_pay`,`pr`.`date_issued` AS `date_issued`,`pr`.`ctc_issued_date` AS `ctc_issued_date`,`pr`.`ctc_fee` AS `ctc_fee`,`pr`.`ctc_num` AS `ctc_num`,`pr`.`ctc_address_issued` AS `ctc_address_issued` from (`permit` `p` join `permittreasury` `pr` on((`p`.`id` = `pr`.`document_id`)))  ;

-- --------------------------------------------------------

--
-- Structure for view `existresident`
--
DROP TABLE IF EXISTS `existresident`;

DROP VIEW IF EXISTS `existresident`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `existresident`  AS SELECT `resident`.`resident_id` AS `resident_id`, `resident`.`first_name` AS `first_name`, `resident`.`middle_name` AS `middle_name`, `resident`.`last_name` AS `last_name`, `resident`.`suffix` AS `suffix`, `resident`.`age` AS `age`, `resident`.`phone_num` AS `phone_num`, `resident`.`email` AS `email`, `resident`.`birth_date` AS `birth_date`, `resident`.`gender` AS `gender`, `resident`.`zone` AS `zone`, `resident`.`barangay_id` AS `barangay_id`, `resident`.`voter_status` AS `voter_status`, `resident`.`nationality` AS `nationality`, `resident`.`religion` AS `religion`, `resident`.`blood_type` AS `blood_type`, `resident`.`occupation` AS `occupation`, `resident`.`source_of_income` AS `source_of_income`, `resident`.`living_duration` AS `living_duration`, `resident`.`birth_place` AS `birth_place`, `resident`.`civil_status` AS `civil_status`, `resident`.`household_id` AS `household_id`, `resident`.`relation_to_family_head` AS `relation_to_family_head`, `resident`.`weight` AS `weight`, `resident`.`height` AS `height`, `resident`.`education_attainment` AS `education_attainment`, `resident`.`with_disability` AS `with_disability`, `resident`.`personal_income` AS `personal_income`, `resident`.`mother's_name` AS `mother's_name`, `resident`.`mother's_phone_num` AS `mother's_phone_num`, `resident`.`father's_name` AS `father's_name`, `resident`.`father's_phone_num` AS `father's_phone_num`, `resident`.`Status` AS `Status`, `resident`.`date_registered` AS `date_registered` FROM `resident` WHERE (`resident`.`Status` = 'Resident') ;

-- --------------------------------------------------------

--
-- Structure for view `requesteddocs`
--
DROP TABLE IF EXISTS `requesteddocs`;

DROP VIEW IF EXISTS `requesteddocs`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `requesteddocs`  AS SELECT `c`.`id` AS `id`, `c`.`firstName` AS `firstName`, `c`.`middleName` AS `middleName`, `c`.`lastName` AS `lastName`, `c`.`suffix` AS `suffix`, `r`.`first_name` AS `first_name`, `r`.`middle_name` AS `middle_name`, `r`.`last_name` AS `last_name`, `r`.`suffix` AS `rqstSuffix`, 'Barangay Certification' AS `label`, `c`.`certification_type` AS `document_type`, `c`.`date_requested` AS `date_requested` FROM (`certification` `c` left join `resident` `r` on((`c`.`resident_id` = `r`.`resident_id`)))union select `p`.`id` AS `id`,`p`.`firstName` AS `firstName`,`p`.`middleName` AS `middleName`,`p`.`lastName` AS `lastName`,`p`.`suffix` AS `suffix`,`r`.`first_name` AS `first_name`,`r`.`middle_name` AS `middle_name`,`r`.`last_name` AS `last_name`,`r`.`suffix` AS `suffix`,'Barangay Permit' AS `label`,`p`.`permit_type` AS `document_type`,`p`.`date_requested` AS `date_requested` from (`permit` `p` left join `resident` `r` on((`p`.`resident_id` = `r`.`resident_id`)))  ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `activity`
--
ALTER TABLE `activity`
  ADD CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`barangay_id`) REFERENCES `barangay` (`barangay_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `admin_user`
--
ALTER TABLE `admin_user`
  ADD CONSTRAINT `admin_user_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `official` (`official_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `cedula`
--
ALTER TABLE `cedula`
  ADD CONSTRAINT `cedula_ibfk_1` FOREIGN KEY (`barangay_Id`) REFERENCES `barangay` (`barangay_id`);

--
-- Constraints for table `certificationtreasury`
--
ALTER TABLE `certificationtreasury`
  ADD CONSTRAINT `certificationtreasury_ibfk_1` FOREIGN KEY (`document_id`) REFERENCES `certification` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `admin_user` (`admin_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `official`
--
ALTER TABLE `official`
  ADD CONSTRAINT `official_ibfk_1` FOREIGN KEY (`barangay_id`) REFERENCES `barangay` (`barangay_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `permittreasury`
--
ALTER TABLE `permittreasury`
  ADD CONSTRAINT `permittreasury_ibfk_1` FOREIGN KEY (`document_id`) REFERENCES `permit` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `resident`
--
ALTER TABLE `resident`
  ADD CONSTRAINT `resident_ibfk_1` FOREIGN KEY (`household_id`) REFERENCES `household` (`household_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
