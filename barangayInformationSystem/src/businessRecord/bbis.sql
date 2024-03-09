-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 18, 2024 at 03:38 AM
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
  `activity_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `barangay_id` int NOT NULL,
  PRIMARY KEY (`activity_id`) USING BTREE,
  KEY `barangay_id` (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date_created` date NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_user`
--

INSERT INTO `admin_user` (`admin_id`, `password`, `date_created`) VALUES
(1, 'admin1', '2024-01-17'),
(2, 'admin2', '2024-01-17'),
(4, 'admin4', '2024-01-17');

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
  `postal_code` int NOT NULL,
  PRIMARY KEY (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `business_id` int NOT NULL,
  `resident_id` int NOT NULL,
  `business_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `business_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `owner` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `owner_phone_num` bigint NOT NULL,
  `coordinates` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` text COLLATE utf8mb4_general_ci NOT NULL,
  `monthly_income` int NOT NULL,
  `status` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_establishment` date NOT NULL,
  `tin` int NOT NULL,
  `vat_status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `num_employees` int NOT NULL,
  `business_logo_url` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_registered` date NOT NULL,
  PRIMARY KEY (`business_id`),
  KEY `resident_id` (`resident_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `business`
--

INSERT INTO `business` (`business_id`, `resident_id`, `business_name`, `business_type`, `owner`, `owner_phone_num`, `coordinates`, `address`, `monthly_income`, `status`, `date_establishment`, `tin`, `vat_status`, `num_employees`, `business_logo_url`, `date_registered`) VALUES
(4610, 17, 'Sip & Savor Cafe', 'Cafe', 'Daniel Kim', 639876543210, '16.0123, -23.4567', '234 Coffee Street, Barangay 101', 30000, 'Active', '2023-07-05', 333, 'Not Registered', 5, 'https://example.com/logo19.jpg', '2021-04-26'),
(12345, 5, 'Sunrise Bakery', 'Food - Bakery', 'Luis Garcia', 639998877665, '10.1234, -45.6789', '789 Sunrise Avenue, Barangay 101', 40000, 'Active', '2022-05-01', 789, 'Registered', 10, 'https://example.com/logo1.jpg', '2012-10-31'),
(13579, 18, 'Green Energy Solutions', 'Renewable Energy', 'Elena Rodriguez', 639987654321, '11.1122, -33.4455', '456 Pine Street, Barangay 101', 45000, 'Active', '2022-08-20', 777, 'Registered', 6, 'https://example.com/logo8.jpg', '2011-07-22'),
(23456, 29, 'Blossom Florist', 'Flower Shop', 'Victoria Lee', 639912345678, '15.7890, -67.8901', '567 Rose Street, Barangay 101', 35000, 'Active', '2023-06-10', 111, 'Registered', 8, 'https://example.com/logo18.jpg', '2021-08-27'),
(23556, 29, 'Blossom Florist', 'Flower Shop', 'Victoria Lee', 639912345678, '15.7890, -67.8901', '567 Rose Street, Barangay 101', 35000, 'Active', '2023-06-10', 111, 'Registered', 8, 'https://example.com/logo18.jpg', '2012-01-14'),
(24680, 28, 'Fitness Junction', 'Fitness Center', 'Ramon Cruz', 639555443322, '8.7654, -12.3456', '567 Oak Avenue, Barangay 101', 30000, 'Active', '2022-09-15', 222, 'Not Registered', 8, 'https://example.com/logo9.jpg', '2018-06-03'),
(43210, 17, 'Sip & Savor Cafe', 'Cafe', 'Daniel Kim', 639876543210, '16.0123, -23.4567', '234 Coffee Street, Barangay 101', 30000, 'Active', '2023-07-05', 333, 'Not Registered', 5, 'https://example.com/logo19.jpg', '2011-04-06'),
(54321, 12, 'XYZ Grocery', 'Retail - Grocery', 'Jane Smith', 639876543210, '11.2233, -45.6677', '456 Oak Avenue, Barangay 101', 30000, 'Active', '2022-02-20', 987, 'Registered', 8, 'https://example.com/logo2.jpg', '2016-10-18'),
(67890, 15, 'Fashion Hub', 'Retail - Apparel', 'Isabella Gomez', 639876543210, '9.8765, -23.4567', '678 Main Street, Barangay 101', 28000, 'Active', '2022-10-01', 444, 'Registered', 5, 'https://example.com/logo10.jpg', '2012-05-22'),
(78901, 14, 'Tech Haven', 'IT Services', 'Andrew Taylor', 639998877665, '14.5678, -45.1234', '123 Oak Lane, Barangay 101', 58000, 'Active', '2023-05-01', 456, 'Not Registered', 12, 'https://example.com/logo17.jpg', '2013-10-11'),
(78904, 14, 'Tech Haven', 'IT Services', 'Andrew Taylor', 639998877665, '14.5678, -45.1234', '123 Oak Lane, Barangay 101', 58000, 'Active', '2023-05-01', 456, 'Not Registered', 12, 'https://example.com/logo17.jpg', '2017-11-20'),
(98765, 20, 'Fresh Mart', 'Retail - Grocery', 'Michael Johnson', 635551234567, '9.8765, -34.5678', '789 Pine Street, Barangay 101', 25000, 'Active', '2022-03-10', 111, 'Registered', 12, 'https://example.com/logo3.jpg', '2013-06-08'),
(98865, 26, 'Digital Solutions', 'Digital Marketing', 'Sophie Martin', 633344556677, '17.2345, -34.5678', '789 Digital Avenue, Barangay 101', 65000, 'Active', '2023-08-20', 666, 'Registered', 15, 'https://example.com/logo20.jpg', '2018-11-27');

-- --------------------------------------------------------

--
-- Table structure for table `cedula`
--

DROP TABLE IF EXISTS `cedula`;
CREATE TABLE IF NOT EXISTS `cedula` (
  `treasury_id` int NOT NULL,
  `barangay_id` int NOT NULL,
  `document_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `first_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gender` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `birthdate` date NOT NULL,
  `tin_no` int DEFAULT NULL,
  `official_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_issued` date NOT NULL,
  `comm_tax` int NOT NULL,
  `tax_amount` int NOT NULL,
  `total` int NOT NULL,
  `date_registered` date NOT NULL,
  PRIMARY KEY (`treasury_id`),
  KEY `barangay_id` (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cedula`
--

INSERT INTO `cedula` (`treasury_id`, `barangay_id`, `document_type`, `first_name`, `middle_name`, `last_name`, `address`, `gender`, `birthdate`, `tin_no`, `official_name`, `date_issued`, `comm_tax`, `tax_amount`, `total`, `date_registered`) VALUES
(12345, 101, 'Barangay Clearance', 'John', 'Alexander', 'Doe', '123 Main St', 'Male', '1990-05-15', 123456789, 'Official Org A', '2022-01-01', 200, 250, 450, '2014-09-18'),
(23456, 101, 'Barangay Permit', 'Emma', 'Faith', 'White', '789 Cedar St', 'Female', '1987-12-30', 567890123, 'Official Org F', '2022-06-10', 220, 270, 490, '2020-11-02'),
(34567, 101, 'Barangay Certification', 'Olivia', 'Grace', 'Miller', '890 Oak St', 'Female', '1993-08-22', 789012345, 'Official Org H', '2022-08-12', 260, 310, 570, '2018-02-11'),
(45678, 101, 'Barangay Certification', 'Alice', 'Danielle', 'Williams', '101 Pine St', 'Female', '1995-07-05', 456789012, 'Official Org D', '2022-04-02', 210, 260, 470, '2019-03-12'),
(54321, 101, 'Barangay ID', 'Bob', 'Christopher', 'Johnson', '789 Maple St', 'Male', '1988-03-10', 654321987, 'Official Org C', '2022-03-15', 180, 220, 400, '2020-04-01'),
(56789, 101, 'Barangay Clearance', 'William', 'Henry', 'Taylor', '678 Elm St', 'Male', '1984-06-28', 890123456, 'Official Org I', '2022-09-17', 280, 330, 610, '2012-07-25'),
(78901, 101, 'Barangay ID', 'David', 'Gabriel', 'Brown', '456 Pine St', 'Male', '1998-04-18', 678901234, 'Official Org G', '2022-07-05', 240, 290, 530, '2021-02-08'),
(87654, 101, 'Barangay Clearance', 'Charlie', 'Ethan', 'Jones', '202 Oak St', 'Male', '1992-11-12', 345678901, 'Official Org E', '2022-05-20', 190, 240, 430, '2015-06-28'),
(89012, 101, 'Barangay Permit', 'Sophia', 'Isabella', 'Clark', '345 Maple St', 'Female', '1991-01-14', 901234567, 'Official Org J', '2022-10-25', 300, 350, 650, '2013-12-18'),
(98765, 101, 'Barangay Permit', 'Jane', 'Bianca', 'Smith', '456 Elm St', 'Female', '1985-09-20', 987654321, 'Official Org B', '2022-02-01', 150, 200, 350, '2017-07-04');

-- --------------------------------------------------------

--
-- Table structure for table `certification`
--

DROP TABLE IF EXISTS `certification`;
CREATE TABLE IF NOT EXISTS `certification` (
  `certification_id` int NOT NULL,
  `resident_id` int DEFAULT NULL,
  `firstName` text COLLATE utf8mb4_general_ci NOT NULL,
  `middleName` text COLLATE utf8mb4_general_ci NOT NULL,
  `lastName` text COLLATE utf8mb4_general_ci NOT NULL,
  `address` text COLLATE utf8mb4_general_ci NOT NULL,
  `phone_num` bigint NOT NULL,
  `email` text COLLATE utf8mb4_general_ci NOT NULL,
  `certification_type` text COLLATE utf8mb4_general_ci NOT NULL,
  `certification_issued` date NOT NULL,
  `certification_expiry` date NOT NULL,
  `certification_fee` int NOT NULL,
  `payment_date` date NOT NULL,
  `official_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_issuance` date NOT NULL,
  `purpose` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`certification_id`),
  KEY `resident_id` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `certification`
--

INSERT INTO `certification` (`certification_id`, `resident_id`, `firstName`, `middleName`, `lastName`, `address`, `phone_num`, `email`, `certification_type`, `certification_issued`, `certification_expiry`, `certification_fee`, `payment_date`, `official_name`, `date_issuance`, `purpose`) VALUES
(2, NULL, 'Jane', 'Beatrice', 'Smith', '456 Elm St', 635555678, 'jane.smith@email.com', 'Certification Type B', '2022-02-01', '2023-02-01', 250, '2022-02-02', 'Official Org B', '2022-02-02', 'Certification Purpose B'),
(3, NULL, 'Bob', 'Christopher', 'Johnson', '789 Maple St', 635559012, 'bob.johnson@email.com', 'Certification Type C', '2022-03-01', '2023-03-01', 300, '2022-03-02', 'Official Org C', '2022-03-02', 'Certification Purpose C'),
(4, NULL, 'Alice', 'Diana', 'Williams', '101 Pine St', 635553456, 'alice.williams@email.com', 'Certification Type D', '2022-04-01', '2023-04-01', 350, '2022-04-02', 'Official Org D', '2022-04-02', 'Certification Purpose D'),
(5, NULL, 'Charlie', 'Edward', 'Jones', '202 Oak St', 635557890, 'charlie.jones@email.com', 'Certification Type E', '2022-05-01', '2023-05-01', 400, '2022-05-02', 'Official Org E', '2022-05-02', 'Certification Purpose E'),
(6, NULL, 'Eva', 'Fiona', 'Johnson', '405 Pine St', 635551122, 'eva.johnson@email.com', 'Certification Type F', '2022-06-01', '2023-06-01', 300, '2022-06-02', 'Official Org F', '2022-06-02', 'Certification Purpose F'),
(7, NULL, 'David', 'George', 'Brown', '708 Oak St', 635553344, 'david.brown@email.com', 'Certification Type G', '2022-07-01', '2023-07-01', 250, '2022-07-02', 'Official Org G', '2022-07-02', 'Certification Purpose G'),
(8, NULL, 'Grace', 'Helen', 'Miller', '901 Elm St', 635555566, 'grace.miller@email.com', 'Certification Type H', '2022-08-01', '2023-08-01', 200, '2022-08-02', 'Official Org H', '2022-08-02', 'Certification Purpose H'),
(9, NULL, 'Frank', 'Isaac', 'Smith', '112 Maple St', 635557788, 'frank.smith@email.com', 'Certification Type I', '2022-09-01', '2023-09-01', 350, '2022-09-02', 'Official Org I', '2022-09-02', 'Certification Purpose I'),
(10, NULL, 'Helen', 'Jane', 'Davis', '304 Oak St', 635559900, 'helen.davis@email.com', 'Certification Type J', '2022-10-01', '2023-10-01', 400, '2022-10-02', 'Official Org J', '2022-10-02', 'Certification Purpose J'),
(1111, NULL, 'John', 'Alexander', 'Doe', '123 Main St', 635551234, 'john.doe@email.com', 'Certification Type A', '2022-01-01', '2023-01-01', 200, '2022-01-02', 'Official Org A', '2022-01-02', 'Certification Purpose A');

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
  `monthly_income` int NOT NULL,
  `date_registered` date NOT NULL,
  PRIMARY KEY (`household_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `household`
--

INSERT INTO `household` (`household_id`, `first_name`, `middle_name`, `last_name`, `pos_in_the_fam`, `h_member`, `zone`, `monthly_income`, `date_registered`) VALUES
(1001, 'John', 'Doe', 'Smith', 'Head', 5, 6, 7000, '2013-02-05'),
(1002, 'Jane', 'M.', 'Doe', 'Spouse', 4, 2, 6000, '2015-01-14'),
(1003, 'Bob', '', 'Johnson', 'Child', 3, 1, 2000, '2012-06-12'),
(1004, 'Alice', 'S.', 'Williams', 'Parent', 6, 3, 8000, '2021-09-26'),
(1005, 'Charlie', '', 'Brown', 'Sibling', 2, 4, 5000, '2020-07-30'),
(1006, 'John', 'Michael', 'Doe', 'Head', 4, 3, 5000, '2018-12-04'),
(1007, 'Jane', 'Elizabeth', 'Smith', 'Spouse', 2, 6, 6000, '2017-12-26'),
(1008, 'David', 'Lee', 'Johnson', 'Child', 1, 2, 3000, '2011-12-25'),
(1009, 'Emily', 'Rose', 'Williams', 'Child', 3, 5, 4000, '2018-05-24'),
(1010, 'Daniel', 'Joseph', 'Miller', 'Child', 2, 1, 3500, '2011-03-30'),
(1011, 'Sarah', 'Louise', 'Anderson', 'Head', 4, 3, 5500, '2011-09-04'),
(1012, 'Michael', 'James', 'Taylor', 'Spouse', 2, 6, 6200, '2021-03-28'),
(1013, 'Emma', 'Grace', 'Clark', 'Child', 1, 2, 3200, '2014-01-23'),
(1014, 'Matthew', 'William', 'Brown', 'Child', 3, 5, 4200, '2019-08-06'),
(1015, 'Olivia', 'Rose', 'Moore', 'Child', 2, 1, 3700, '2015-05-31'),
(1016, 'Benjamin', 'Robert', 'Ward', 'Head', 4, 4, 4800, '2020-01-27'),
(1017, 'Ava', 'Sophia', 'Evans', 'Spouse', 2, 7, 5500, '2014-03-22'),
(1018, 'Ethan', 'Christopher', 'Hill', 'Child', 1, 3, 3100, '2017-02-25'),
(1019, 'Isabella', 'Grace', 'Turner', 'Child', 3, 2, 4100, '2016-06-19'),
(1020, 'Mason', 'Alexander', 'Mitchell', 'Child', 2, 5, 3900, '2019-01-27'),
(1021, 'Grace', 'Oliver', 'Walker', 'Head', 4, 3, 5700, '2014-10-09'),
(1022, 'Liam', 'Andrew', 'Fisher', 'Spouse', 2, 6, 6300, '2019-08-14'),
(1023, 'Chloe', 'Madison', 'Bennett', 'Child', 1, 2, 3300, '2014-05-24'),
(1024, 'Noah', 'Elijah', 'Carter', 'Child', 3, 5, 4300, '2016-07-11'),
(1025, 'Ava', 'Brooklyn', 'Sanders', 'Child', 2, 1, 3800, '2016-01-08'),
(1026, 'Elijah', 'Gabriel', 'Murray', 'Head', 4, 4, 4900, '2020-04-12'),
(1027, 'Sophia', 'Avery', 'Dixon', 'Spouse', 2, 7, 5600, '2018-09-14'),
(1028, 'Lucas', 'Jackson', 'Richardson', 'Child', 1, 3, 3200, '2011-06-14'),
(1029, 'Mia', 'Scarlett', 'Wallace', 'Child', 3, 2, 4200, '2021-11-03'),
(1030, 'Jackson', 'Henry', 'Harrison', 'Child', 2, 5, 4000, '2016-07-13');

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

DROP TABLE IF EXISTS `logs`;
CREATE TABLE IF NOT EXISTS `logs` (
  `log_id` int NOT NULL,
  `log_date` date NOT NULL,
  `admin_id` int NOT NULL,
  `action` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`log_id`),
  KEY `user_id` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `logs`
--

INSERT INTO `logs` (`log_id`, `log_date`, `admin_id`, `action`) VALUES
(32421, '2024-01-17', 2, 'Change Password'),
(52131, '2024-01-18', 2, 'Log In');

-- --------------------------------------------------------

--
-- Table structure for table `official`
--

DROP TABLE IF EXISTS `official`;
CREATE TABLE IF NOT EXISTS `official` (
  `official_id` int NOT NULL,
  `position` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `barangay_id` int NOT NULL,
  `first_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `middle_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `last_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gender` text COLLATE utf8mb4_general_ci NOT NULL,
  `term_start` year NOT NULL,
  `term_end` year NOT NULL,
  `phone_num` bigint NOT NULL,
  `email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`official_id`),
  KEY `barangay_id` (`barangay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `official`
--

INSERT INTO `official` (`official_id`, `position`, `barangay_id`, `first_name`, `middle_name`, `last_name`, `gender`, `term_start`, `term_end`, `phone_num`, `email`) VALUES
(1, 'Barangay Captain', 101, 'Juan', 'Cruz', 'Santos', 'Male', 2022, 2025, 639112233445, 'juan.santos@example.com'),
(2, 'Kagawad', 101, 'Maria', 'Luz', 'Garcia', 'Female', 2022, 2025, 639998877665, 'maria.garcia@example.com'),
(3, 'Barangay Secretary', 101, 'Antonio', 'Santos', 'Reyes', 'Male', 2022, 2025, 639456789012, 'antonio.reyes@example.com'),
(4, 'Barangay Treasurer', 101, 'Elena', 'Lim', 'Torres', 'Female', 2022, 2025, 639765432109, 'elena.torres@example.com'),
(5, 'SK Chairperson', 101, 'Mark', 'Anthony', 'Cruz', 'Male', 2022, 2025, 639876543210, 'mark.cruz@example.com'),
(6, 'Kagawad', 101, 'Rosa', 'Marie', 'Lopez', 'Female', 2022, 2025, 639112233445, 'rosa.lopez@example.com'),
(7, 'Kagawad', 101, 'Fernando', 'Mendoza', 'Gomez', 'Male', 2022, 2025, 639998877665, 'fernando.gomez@example.com'),
(8, 'Kagawad', 101, 'Carmen', 'Santiago', 'Vargas', 'Female', 2022, 2025, 639456789012, 'carmen.vargas@example.com'),
(9, 'Kagawad', 101, 'Ramon', 'Gonzales', 'Cruz', 'Male', 2022, 2025, 639765432109, 'ramon.cruz@example.com'),
(10, 'SK Kagawad', 101, 'Sofia', 'Isabel', 'Luna', 'Female', 2022, 2025, 639876543210, 'sofia.luna@example.com');

-- --------------------------------------------------------

--
-- Table structure for table `permit`
--

DROP TABLE IF EXISTS `permit`;
CREATE TABLE IF NOT EXISTS `permit` (
  `permit_id` int NOT NULL,
  `resident_id` int DEFAULT NULL,
  `firstName` text COLLATE utf8mb4_general_ci NOT NULL,
  `middleName` text COLLATE utf8mb4_general_ci NOT NULL,
  `lastName` text COLLATE utf8mb4_general_ci NOT NULL,
  `address` text COLLATE utf8mb4_general_ci NOT NULL,
  `phone_num` bigint NOT NULL,
  `email` text COLLATE utf8mb4_general_ci NOT NULL,
  `business_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `business_type` text COLLATE utf8mb4_general_ci NOT NULL,
  `business_address` text COLLATE utf8mb4_general_ci NOT NULL,
  `permit_type` text COLLATE utf8mb4_general_ci NOT NULL,
  `permit_issued` date NOT NULL,
  `permit_expired` date NOT NULL,
  `permit_fee` int NOT NULL,
  `payment_date` date NOT NULL,
  `official_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_issuance` date NOT NULL,
  `purpose` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`permit_id`),
  KEY `resident_id` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `permit`
--

INSERT INTO `permit` (`permit_id`, `resident_id`, `firstName`, `middleName`, `lastName`, `address`, `phone_num`, `email`, `business_name`, `business_type`, `business_address`, `permit_type`, `permit_issued`, `permit_expired`, `permit_fee`, `payment_date`, `official_name`, `date_issuance`, `purpose`) VALUES
(2, NULL, 'Jane', 'Beatrice', 'Smith', '456 Elm St', 555, 'jane.smith@email.com', 'XYZ Corporation', 'Service', '789 Pine St', 'Business Permit B', '2022-02-01', '2023-02-01', 250, '2022-02-02', 'Official Org B', '2022-02-02', 'Business Purpose B'),
(3, NULL, 'Bob', 'Christopher', 'Johnson', '789 Maple St', 555, 'bob.johnson@email.com', '123 Industries', 'Manufacturing', '101 Oak St', 'Business Permit C', '2022-03-01', '2023-03-01', 300, '2022-03-02', 'Official Org C', '2022-03-02', 'Business Purpose C'),
(4, NULL, 'Alice', 'Diana', 'Williams', '101 Pine St', 555, 'alice.williams@email.com', 'ABC Services', 'Consulting', '202 Maple St', 'Business Permit D', '2022-04-01', '2023-04-01', 350, '2022-04-02', 'Official Org D', '2022-04-02', 'Business Purpose D'),
(5, NULL, 'Charlie', 'Edward', 'Jones', '202 Oak St', 555, 'charlie.jones@email.com', 'XYZ Ventures', 'Technology', '303 Elm St', 'Business Permit E', '2022-05-01', '2023-05-01', 400, '2022-05-02', 'Official Org E', '2022-05-02', 'Business Purpose E'),
(6, NULL, 'Eva', 'Fiona', 'Johnson', '405 Pine St', 555, 'eva.johnson@email.com', 'EFG Corporation', 'Finance', '607 Elm St', 'Business Permit F', '2022-06-01', '2023-06-01', 300, '2022-06-02', 'Official Org F', '2022-06-02', 'Business Purpose F'),
(7, NULL, 'David', 'George', 'Brown', '708 Oak St', 555, 'david.brown@email.com', 'XYZ Innovations', 'Technology', '910 Maple St', 'Business Permit G', '2022-07-01', '2023-07-01', 250, '2022-07-02', 'Official Org G', '2022-07-02', 'Business Purpose G'),
(8, NULL, 'Grace', 'Helen', 'Miller', '901 Elm St', 555, 'grace.miller@email.com', '123 Services', 'Consulting', '112 Pine St', 'Business Permit H', '2022-08-01', '2023-08-01', 200, '2022-08-02', 'Official Org H', '2022-08-02', 'Business Purpose H'),
(9, NULL, 'Frank', 'Isaac', 'Smith', '112 Maple St', 555, 'frank.smith@email.com', 'ABC Ventures', 'Retail', '304 Oak St', 'Business Permit I', '2022-09-01', '2023-09-01', 350, '2022-09-02', 'Official Org I', '2022-09-02', 'Business Purpose I'),
(10, NULL, 'Helen', 'Jane', 'Davis', '304 Oak St', 555, 'helen.davis@email.com', 'XYZ Solutions', 'Service', '506 Pine St', 'Business Permit J', '2022-10-01', '2023-10-01', 400, '2022-10-02', 'Official Org J', '2022-10-02', 'Business Purpose J'),
(1000, NULL, 'John', 'Alexander', 'Doe', '123 Main St', 555, 'john.doe@email.com', 'ABC Enterprises', 'Retail', '456 Oak St', 'Business Permit A', '2022-01-01', '2023-01-01', 200, '2022-01-02', 'Official Org A', '2022-01-02', 'Business Purpose A');

-- --------------------------------------------------------

--
-- Table structure for table `public_user`
--

DROP TABLE IF EXISTS `public_user`;
CREATE TABLE IF NOT EXISTS `public_user` (
  `public_user_id` int NOT NULL,
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date_created` date NOT NULL,
  PRIMARY KEY (`public_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `public_user`
--

INSERT INTO `public_user` (`public_user_id`, `password`, `date_created`) VALUES
(2, 'alice', '2024-01-17');

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
CREATE TABLE IF NOT EXISTS `report` (
  `report_id` int NOT NULL,
  `resident_id` int DEFAULT NULL,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `report_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `date_recorded` date NOT NULL,
  `email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone_num` bigint NOT NULL,
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`report_id`),
  KEY `report_ibfk_1` (`resident_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `report`
--

INSERT INTO `report` (`report_id`, `resident_id`, `name`, `report_type`, `date_recorded`, `email`, `phone_num`, `reason`, `status`) VALUES
(1, NULL, 'John Doe', 'Incident Report', '2022-01-15', 'john.doe@email.com', 635512345678, 'Safety Concern', 'pending'),
(2, NULL, 'Jane Smith', 'Complaint', '2022-02-20', 'jane.smith@email.com', 635555555555, 'Noise Complaint', 'pending'),
(3, NULL, 'Bob Johnson', 'Maintenance Request', '2022-03-10', 'bob.johnson@email.com', 635590123456, 'Leaky Faucet', 'solve'),
(4, NULL, 'Alice Williams', 'Security Concern', '2022-04-05', 'alice.williams@email.com', 635534567890, 'Unauthorized Entry', 'solve'),
(5, NULL, 'Charlie Jones', 'Feedback', '2022-05-12', 'charlie.jones@email.com', 635578901234, 'Service Improvement', 'pending'),
(6, NULL, 'Eva Johnson', 'Maintenance Request', '2022-06-25', 'eva.johnson@email.com', 635511122333, 'Broken Window', 'solve'),
(7, NULL, 'David Brown', 'Complaint', '2022-07-18', 'david.brown@email.com', 635533344455, 'Parking Issue', 'pending'),
(8, NULL, 'Grace Miller', 'Incident Report', '2022-08-03', 'grace.miller@email.com', 635555566677, 'Fire Alarm', 'solve'),
(9, NULL, 'Frank Smith', 'Feedback', '2022-09-14', 'frank.smith@email.com', 635577788899, 'Customer Satisfaction', 'solve'),
(10, NULL, 'Helen Davis', 'Maintenance Request', '2022-10-29', 'helen.davis@email.com', 635599900011, 'Electrical Issue', 'pending');

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
  `suffix` text COLLATE utf8mb4_general_ci NOT NULL,
  `age` int NOT NULL,
  `phone_num` bigint NOT NULL,
  `email` text COLLATE utf8mb4_general_ci NOT NULL,
  `birth_date` date NOT NULL,
  `gender` text COLLATE utf8mb4_general_ci NOT NULL,
  `zone` int NOT NULL,
  `barangay_id` int NOT NULL,
  `voter_status` text COLLATE utf8mb4_general_ci NOT NULL,
  `nationality` text COLLATE utf8mb4_general_ci NOT NULL,
  `religion` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `blood_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `occupation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `occupation_duration` int NOT NULL,
  `birth_place` text COLLATE utf8mb4_general_ci NOT NULL,
  `civil_status` text COLLATE utf8mb4_general_ci NOT NULL,
  `household_id` int NOT NULL,
  `relation_to_family_head` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `weight` int NOT NULL,
  `height` int NOT NULL,
  `education_attainment` text COLLATE utf8mb4_general_ci NOT NULL,
  `educational_status` text COLLATE utf8mb4_general_ci NOT NULL,
  `with_disability` text COLLATE utf8mb4_general_ci NOT NULL,
  `personal_income` int NOT NULL,
  `mother's_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `mother's_phone_num` bigint NOT NULL,
  `father's_name` text COLLATE utf8mb4_general_ci NOT NULL,
  `father's_phone_num` bigint NOT NULL,
  `resident_photo_url` text COLLATE utf8mb4_general_ci NOT NULL,
  `date_registered` date NOT NULL,
  PRIMARY KEY (`resident_id`),
  KEY `barangay_id` (`barangay_id`),
  KEY `household_id` (`household_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `resident`
--

INSERT INTO `resident` (`resident_id`, `first_name`, `middle_name`, `last_name`, `suffix`, `age`, `phone_num`, `email`, `birth_date`, `gender`, `zone`, `barangay_id`, `voter_status`, `nationality`, `religion`, `blood_type`, `occupation`, `occupation_duration`, `birth_place`, `civil_status`, `household_id`, `relation_to_family_head`, `weight`, `height`, `education_attainment`, `educational_status`, `with_disability`, `personal_income`, `mother's_name`, `mother's_phone_num`, `father's_name`, `father's_phone_num`, `resident_photo_url`, `date_registered`) VALUES
(1, 'John', 'Michael', 'Doe', 'Jr', 30, 631234567890, 'john.doe@email.com', '1992-05-15', 'Male', 1, 101, 'Yes', 'American', 'Christian', 'O+', 'Software Engineer', 5, 'New York, USA', 'Single', 1001, 'Self', 76, 180, 'Bachelor\'s Degree', 'Graduate', 'No', 70000, 'Jane Doe', 639876543210, 'John Doe Sr', 639998887777, 'http://example.com/johndoe.jpg', '2019-06-13'),
(2, 'Alice', 'Marie', 'Smith', '', 25, 639876543210, 'alice.smith@email.com', '1997-09-23', 'Female', 2, 101, 'Yes', 'Canadian', 'Atheist', 'B-', 'Teacher', 3, 'Toronto, Canada', 'Married', 1001, 'Spouse', 60, 165, 'Master\'s Degree', 'Graduate', 'No', 50000, 'Mary Smith', 638765432109, 'Robert Smith', 638889990000, 'http://example.com/alicesmith.jpg', '2012-07-09'),
(3, 'David', 'William', 'Johnson', '', 40, 635551112222, 'david.johnson@email.com', '1982-02-10', 'Male', 3, 101, 'Yes', 'British', 'Jewish', 'AB+', 'Doctor', 10, 'London, UK', 'Divorced', 1001, 'Relative', 86, 190, 'Doctorate Degree', 'Graduate', 'Yes', 120000, 'Lucy Johnson', 633334445555, 'George Johnson', 631112223333, 'http://example.com/davidjohnson.jpg', '2015-06-01'),
(4, 'Mia', 'Rose', 'Garcia', '', 28, 637778889999, 'mia.garcia@email.com', '1994-07-31', 'Female', 4, 101, 'Yes', 'Filipino', 'Catholic', 'O-', 'Nurse', 6, 'Manila, Philippines', 'Single', 1001, 'Friend', 51, 160, 'Bachelor\'s Degree', 'Graduate', 'No', 60000, 'Maria Garcia', 635556667777, 'Carlos Garcia', 634443332222, 'http://example.com/miagarcia.jpg', '2019-10-26'),
(5, 'Robert', 'Anthony', 'Lee', 'Sr', 55, 632223334444, 'robert.lee@email.com', '1969-12-05', 'Male', 5, 101, 'Yes', 'Chinese', 'Buddhist', 'A+', 'Businessman', 20, 'Beijing, China', 'Widowed', 1001, 'Self', 70, 175, 'Bachelor\'s Degree', 'Graduate', 'No', 150000, 'Lily Lee', 631112223333, 'Not Applicable', 0, 'http://example.com/robertlee.jpg', '2017-05-26'),
(6, 'Sophia', 'Grace', 'Miller', '', 22, 639998887777, 'sophia.miller@email.com', '2000-08-18', 'Female', 6, 101, 'No', 'Australian', 'Protestant', 'B+', 'Student', 2, 'Sydney, Australia', 'Single', 1001, 'Daughter', 53, 163, 'Bachelor\'s Degree', 'Undergraduate', 'No', 0, 'Olivia Miller', 637776665555, 'Daniel Miller', 6333322221111, 'http://example.com/sophiamiller.jpg', '2012-10-24'),
(7, 'Ethan', 'James', 'Brown', '', 35, 631112223333, 'ethan.brown@email.com', '1987-11-30', 'Male', 7, 101, 'No', 'Irish', 'Catholic', 'A-', 'Architect', 8, 'Dublin, Ireland', 'Married', 1001, 'Head', 79, 185, 'Master\'s Degree', 'Graduate', 'No', 80000, 'Olivia Brown', 634445556666, 'Michael Brown', 639998887777, 'http://example.com/ethanbrown.jpg', '2017-06-11'),
(8, 'Isabella', 'Rose', 'Chen', '', 29, 637776665555, 'isabella.chen@email.com', '1993-04-12', 'Female', 1, 101, 'Yes', 'Chinese', 'Buddhist', 'O+', 'Accountant', 7, 'Shanghai, China', 'Divorced', 1001, 'Self', 56, 168, 'Bachelor\'s Degree', 'Graduate', 'No', 90000, 'Sophia Chen', 638889990000, 'Not Applicable', 0, 'http://example.com/isabellachen.jpg', '2018-09-20'),
(9, 'Liam', 'Alexander', 'Taylor', '', 32, 635556667777, 'liam.taylor@email.com', '1989-09-08', 'Male', 2, 101, 'Yes', 'British', 'Atheist', 'AB-', 'Engineer', 9, 'Manchester, UK', 'Single', 1001, 'Self', 68, 178, 'Bachelor\'s Degree', 'Graduate', 'No', 75000, 'Emma Taylor', 634443332222, 'Not Applicable', 0, 'http://example.com/liamtaylor.jpg', '2013-04-14'),
(10, 'Ava', 'Elizabeth', 'Gomez', '', 26, 638889990000, 'ava.gomez@email.com', '1995-12-20', 'Female', 3, 101, 'No', 'Filipino', 'Catholic', 'B+', 'Marketing Manager', 4, 'Quezon City, Philippines', 'Married', 1001, 'Spouse', 49, 155, 'Master\'s Degree', 'Graduate', 'No', 100000, 'Carlos Gomez', 637778889999, 'Maria Gomez', 635556667777, 'http://example.com/avagomez.jpg', '2018-07-14'),
(11, 'Oliver', 'Thomas', 'White', '', 34, 637771112222, 'oliver.white@email.com', '1988-08-12', 'Male', 4, 101, 'Yes', 'British', 'Atheist', 'O-', 'Engineer', 8, 'London, UK', 'Married', 1002, 'Head', 79, 183, 'Master\'s Degree', 'Graduate', 'No', 85000, 'Sophie White', 639992223333, 'Michael White', 638884445555, 'http://example.com/oliverwhite.jpg', '2016-03-04'),
(12, 'Emma', 'Grace', 'Turner', '', 29, 635554443333, 'emma.turner@email.com', '1993-11-25', 'Female', 2, 101, 'No', 'Australian', 'Christian', 'B+', 'Teacher', 6, 'Sydney, Australia', 'Single', 1002, 'Daughter', 55, 170, 'Bachelor\'s Degree', 'Graduate', 'No', 55000, 'Liam Turner', 636667778888, 'Sarah Turner', 637778889999, 'http://example.com/emmaturner.jpg', '2018-01-27'),
(13, 'Jackson', 'Henry', 'Miller', '', 42, 639997775555, 'jackson.miller@email.com', '1980-04-03', 'Male', 3, 101, 'Yes', 'Canadian', 'Atheist', 'A+', 'Architect', 12, 'Toronto, Canada', 'Divorced', 1002, 'Self', 86, 188, 'Doctorate Degree', 'Graduate', 'No', 120000, 'Olivia Miller', 635556667777, 'Daniel Miller', 6333322221111, 'http://example.com/jacksonmiller.jpg', '2019-05-30'),
(14, 'Ava', 'Sophia', 'Jones', '', 28, 638883334444, 'ava.jones@email.com', '1994-07-14', 'Female', 1, 101, 'Yes', 'American', 'Protestant', 'O+', 'Graphic Designer', 7, 'New York, USA', 'Single', 1002, 'Self', 51, 165, 'Bachelor\'s Degree', 'Graduate', 'No', 70000, 'Emily Jones', 637778889999, 'William Jones', 639991112222, 'http://example.com/avajones.jpg', '2013-09-28'),
(15, 'Ethan', 'Michael', 'Davis', '', 36, 631114445555, 'ethan.davis@email.com', '1986-09-20', 'Male', 5, 101, 'Yes', 'Filipino', 'Catholic', 'A-', 'Doctor', 10, 'Manila, Philippines', 'Married', 1002, 'Head', 76, 175, 'Doctorate Degree', 'Graduate', 'No', 130000, 'Sophie Davis', 632223334444, 'Daniel Davis', 635556667777, 'http://example.com/ethandavis.jpg', '2013-01-02'),
(16, 'Olivia', 'Isabel', 'Clark', '', 31, 637776665555, 'olivia.clark@email.com', '1991-02-18', 'Female', 6, 101, 'No', 'British', 'Catholic', 'B-', 'Marketing Manager', 8, 'London, UK', 'Single', 1002, 'Daughter', 59, 172, 'Master\'s Degree', 'Graduate', 'No', 90000, 'Andrew Clark', 633334445555, 'Sophie Clark', 637778889999, 'http://example.com/oliviaclark.jpg', '2013-12-30'),
(17, 'Liam', 'Joseph', 'Cooper', '', 45, 633334445555, 'liam.cooper@email.com', '1977-12-10', 'Male', 2, 101, 'Yes', 'Australian', 'Atheist', 'AB+', 'Engineer', 15, 'Sydney, Australia', 'Widowed', 1002, 'Self', 80, 180, 'Doctorate Degree', 'Graduate', 'No', 110000, 'Sophie Cooper', 635556667777, 'Not Applicable', 0, 'http://example.com/liamcooper.jpg', '2015-06-25'),
(18, 'Sophia', 'Emily', 'Lopez', '', 27, 632223334444, 'sophia.lopez@email.com', '1995-05-05', 'Female', 4, 101, 'No', 'Filipino', 'Catholic', 'O-', 'Software Developer', 5, 'Makati City, Philippines', 'Single', 1002, 'Daughter', 54, 167, 'Bachelor\'s Degree', 'Graduate', 'No', 75000, 'Carlos Lopez', 631112223333, 'Maria Lopez', 634445556666, 'http://example.com/sophialopez.jpg', '2015-10-06'),
(19, 'Noah', 'Benjamin', 'Williams', '', 33, 637778889999, 'noah.williams@email.com', '1989-11-28', 'Male', 7, 101, 'Yes', 'Canadian', 'Protestant', 'B+', 'Financial Analyst', 9, 'Vancouver, Canada', 'Married', 1002, 'Head', 74, 185, 'Master\'s Degree', 'Graduate', 'No', 95000, 'Olivia Williams', 635556667777, 'Michael Williams', 636667778888, 'http://example.com/noahwilliams.jpg', '2018-10-21'),
(20, 'Aria', 'Gabrielle', 'Nguyen', '', 24, 639991112222, 'aria.nguyen@email.com', '1998-03-22', 'Female', 3, 101, 'No', 'Vietnamese', 'Buddhist', 'A-', 'Graphic Designer', 3, 'Ho Chi Minh City, Vietnam', 'Single', 1002, 'Daughter', 49, 160, 'Bachelor\'s Degree', 'Undergraduate', 'No', 60000, 'Liam Nguyen', 637778889999, 'Sophie Nguyen', 634445556666, 'http://example.com/arianguyen.jpg', '2021-01-03'),
(21, 'Ella', 'Sophie', 'Moore', '', 28, 6377711122333, 'ella.moore@email.com', '1994-09-15', 'Female', 5, 101, 'Yes', 'Canadian', 'Atheist', 'A-', 'Engineer', 6, 'Vancouver, Canada', 'Single', 1003, 'Self', 55, 167, 'Master\'s Degree', 'Graduate', 'No', 80000, 'Olivia Moore', 635556667788, 'Daniel Moore', 632223334444, 'http://example.com/ellamoore.jpg', '2018-07-01'),
(22, 'Mason', 'Alexander', 'Taylor', '', 32, 6311223344555, 'mason.taylor@email.com', '1989-06-20', 'Male', 3, 101, 'Yes', 'British', 'Atheist', 'B+', 'Software Developer', 8, 'Manchester, UK', 'Married', 1003, 'Head', 71, 175, 'Bachelor\'s Degree', 'Graduate', 'No', 90000, 'Emma Taylor', 634443332222, 'Liam Taylor', 635556667777, 'http://example.com/masontaylor.jpg', '2021-01-05'),
(23, 'Aria', 'Grace', 'Nguyen', '', 26, 636667778899, 'aria.nguyen@email.com', '1996-02-28', 'Female', 1, 101, 'No', 'Vietnamese', 'Buddhist', 'O+', 'Graphic Designer', 5, 'Ho Chi Minh City, Vietnam', 'Single', 1003, 'Daughter', 50, 162, 'Bachelor\'s Degree', 'Graduate', 'No', 60000, 'Liam Nguyen', 637778889999, 'Sophie Nguyen', 634445556666, 'http://example.com/arianguyen.jpg', '2021-05-07'),
(24, 'Noah', 'James', 'Cooper', '', 40, 637778889900, 'noah.cooper@email.com', '1982-12-05', 'Male', 6, 101, 'Yes', 'Australian', 'Atheist', 'AB-', 'Architect', 12, 'Sydney, Australia', 'Divorced', 1003, 'Self', 75, 180, 'Doctorate Degree', 'Graduate', 'No', 110000, 'Sophie Cooper', 635556667777, 'Not Applicable', 0, 'http://example.com/noahcooper.jpg', '2012-01-25'),
(25, 'Sophia', 'Olivia', 'Lee', '', 32, 639992223344, 'sophia.lee@email.com', '1989-08-18', 'Female', 2, 101, 'Yes', 'Chinese', 'Buddhist', 'O-', 'Marketing Manager', 9, 'Beijing, China', 'Married', 1003, 'Head', 59, 170, 'Master\'s Degree', 'Graduate', 'No', 120000, 'Robert Lee', 632223334444, 'Lily Lee', 631112223333, 'http://example.com/sophialee.jpg', '2011-09-27'),
(26, 'Ethan', 'Daniel', 'Davis', '', 35, 631112223344, 'ethan.davis@email.com', '1987-11-30', 'Male', 4, 101, 'Yes', 'Filipino', 'Catholic', 'A-', 'Financial Analyst', 8, 'Makati City, Philippines', 'Married', 1003, 'Head', 80, 185, 'Master\'s Degree', 'Graduate', 'No', 130000, 'Sophie Davis', 632223334444, 'Daniel Davis', 635556667777, 'http://example.com/ethandavis.jpg', '2022-01-16'),
(27, 'Ava', 'Elizabeth', 'Gomez', '', 29, 637776665544, 'ava.gomez@email.com', '1993-12-20', 'Female', 6, 101, 'No', 'Filipino', 'Catholic', 'B+', 'Teacher', 7, 'Quezon City, Philippines', 'Single', 1003, 'Daughter', 52, 165, 'Bachelor\'s Degree', 'Graduate', 'No', 50000, 'Carlos Gomez', 635556667777, 'Maria Gomez', 638889990000, 'http://example.com/avagomez.jpg', '2020-10-11'),
(28, 'Liam', 'Alexander', 'Johnson', '', 37, 63, 'liam.johnson@email.com', '1985-09-08', 'Male', 1, 101, 'No', 'British', 'Atheist', 'AB-', 'Engineer', 10, 'London, UK', 'Married', 1003, 'Head', 70, 178, 'Bachelor\'s Degree', 'Graduate', 'No', 90000, 'Emma Johnson', 634443332222, 'Not Applicable', 0, 'http://example.com/liamjohnson.jpg', '2012-12-22'),
(29, 'Olivia', 'Isabella', 'Clark', '', 28, 632223334411, 'olivia.clark@email.com', '1994-02-18', 'Female', 3, 101, 'Yes', 'British', 'Catholic', 'B-', 'Graphic Designer', 6, 'London, UK', 'Single', 1003, 'Self', 56, 168, 'Bachelor\'s Degree', 'Graduate', 'No', 75000, 'Andrew Clark', 633334445566, 'Sophie Clark', 637778889999, 'http://example.com/oliviaclark.jpg', '2020-02-17'),
(30, 'Elijah', 'Benjamin', 'Williams', '', 31, 6399911122333, 'elijah.williams@email.com', '1991-11-28', 'Male', 5, 101, 'Yes', 'Canadian', 'Protestant', 'B+', 'IT Consultant', 9, 'Toronto, Canada', 'Married', 1003, 'Head', 78, 182, 'Master\'s Degree', 'Graduate', 'No', 100000, 'Olivia Williams', 635556667777, 'Michael Williams', 636667778888, 'http://example.com/elijahwilliams.jpg', '2015-04-02'),
(31, 'Ella', 'Sophie', 'Moore', '', 28, 6377711122333, 'ella.moore@email.com', '1994-09-15', 'Female', 5, 101, 'Yes', 'Canadian', 'Atheist', 'A-', 'Engineer', 6, 'Vancouver, Canada', 'Single', 1001, 'Self', 55, 167, 'Master\'s Degree', 'Graduate', 'No', 80000, 'Olivia Moore', 635556667788, 'Daniel Moore', 632223334444, 'http://example.com/ellamoore.jpg', '2021-03-31'),
(32, 'Mason', 'Alexander', 'Taylor', '', 32, 6311223344555, 'mason.taylor@email.com', '1989-06-20', 'Male', 3, 101, 'Yes', 'British', 'Atheist', 'B+', 'Software Developer', 8, 'Manchester, UK', 'Married', 1002, 'Head', 71, 175, 'Bachelor\'s Degree', 'Graduate', 'No', 90000, 'Emma Taylor', 634443332222, 'Liam Taylor', 635556667777, 'http://example.com/masontaylor.jpg', '2011-12-30'),
(33, 'Aria', 'Grace', 'Nguyen', '', 26, 636667778899, 'aria.nguyen@email.com', '1996-02-28', 'Female', 1, 101, 'No', 'Vietnamese', 'Buddhist', 'O+', 'Graphic Designer', 5, 'Ho Chi Minh City, Vietnam', 'Single', 1003, 'Daughter', 50, 162, 'Bachelor\'s Degree', 'Graduate', 'No', 60000, 'Liam Nguyen', 637778889999, 'Sophie Nguyen', 634445556666, 'http://example.com/arianguyen.jpg', '2013-04-20'),
(34, 'Noah', 'James', 'Cooper', '', 40, 637778889900, 'noah.cooper@email.com', '1982-12-05', 'Male', 6, 101, 'Yes', 'Australian', 'Atheist', 'AB-', 'Architect', 12, 'Sydney, Australia', 'Divorced', 1004, 'Self', 75, 180, 'Doctorate Degree', 'Graduate', 'No', 110000, 'Sophie Cooper', 635556667777, 'Not Applicable', 0, 'http://example.com/noahcooper.jpg', '2021-05-12'),
(35, 'Sophia', 'Olivia', 'Lee', '', 32, 639992223344, 'sophia.lee@email.com', '1989-08-18', 'Female', 2, 101, 'Yes', 'Chinese', 'Buddhist', 'O-', 'Marketing Manager', 9, 'Beijing, China', 'Married', 1005, 'Head', 59, 170, 'Master\'s Degree', 'Graduate', 'No', 120000, 'Robert Lee', 632223334444, 'Lily Lee', 631112223333, 'http://example.com/sophialee.jpg', '2018-09-25'),
(36, 'Ethan', 'Daniel', 'Davis', '', 35, 631112223344, 'ethan.davis@email.com', '1987-11-30', 'Male', 4, 101, 'Yes', 'Filipino', 'Catholic', 'A-', 'Financial Analyst', 8, 'Makati City, Philippines', 'Married', 1001, 'Head', 80, 185, 'Master\'s Degree', 'Graduate', 'No', 130000, 'Sophie Davis', 632223334444, 'Daniel Davis', 635556667777, 'http://example.com/ethandavis.jpg', '2014-04-27'),
(37, 'Ava', 'Elizabeth', 'Gomez', '', 29, 637776665544, 'ava.gomez@email.com', '1993-12-20', 'Female', 6, 101, 'No', 'Filipino', 'Catholic', 'B+', 'Teacher', 7, 'Quezon City, Philippines', 'Single', 1002, 'Daughter', 52, 165, 'Bachelor\'s Degree', 'Graduate', 'No', 50000, 'Carlos Gomez', 635556667777, 'Maria Gomez', 638889990000, 'http://example.com/avagomez.jpg', '2021-01-14'),
(38, 'Liam', 'Alexander', 'Johnson', '', 37, 635556667777, 'liam.johnson@email.com', '1985-09-08', 'Male', 1, 101, 'No', 'British', 'Atheist', 'AB-', 'Engineer', 10, 'London, UK', 'Married', 1003, 'Head', 70, 178, 'Bachelor\'s Degree', 'Graduate', 'No', 90000, 'Emma Johnson', 634443332222, 'Not Applicable', 0, 'http://example.com/liamjohnson.jpg', '2015-06-12'),
(39, 'Olivia', 'Isabella', 'Clark', '', 28, 632223334411, 'olivia.clark@email.com', '1994-02-18', 'Female', 3, 101, 'Yes', 'British', 'Catholic', 'B-', 'Graphic Designer', 6, 'London, UK', 'Single', 1004, 'Self', 56, 168, 'Bachelor\'s Degree', 'Graduate', 'No', 75000, 'Andrew Clark', 633334445566, 'Sophie Clark', 637778889999, 'http://example.com/', '2014-09-24');

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
-- Constraints for table `business`
--
ALTER TABLE `business`
  ADD CONSTRAINT `business_ibfk_1` FOREIGN KEY (`resident_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `cedula`
--
ALTER TABLE `cedula`
  ADD CONSTRAINT `cedula_ibfk_1` FOREIGN KEY (`barangay_id`) REFERENCES `barangay` (`barangay_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `certification`
--
ALTER TABLE `certification`
  ADD CONSTRAINT `certification_ibfk_1` FOREIGN KEY (`resident_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

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
-- Constraints for table `permit`
--
ALTER TABLE `permit`
  ADD CONSTRAINT `permit_ibfk_1` FOREIGN KEY (`resident_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `public_user`
--
ALTER TABLE `public_user`
  ADD CONSTRAINT `public_user_ibfk_1` FOREIGN KEY (`public_user_id`) REFERENCES `resident` (`resident_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;