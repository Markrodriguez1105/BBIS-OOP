SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

DROP TABLE IF EXISTS `household_records`;
CREATE TABLE IF NOT EXISTS `household_records` (
  `householdNumber` int(11) NOT NULL,
  `headOfTheFamily` varchar(255) NOT NULL,
  `position` varchar(255) NOT NULL,
  `householdSize` int(11) NOT NULL,
  `occupation` varchar(255) NOT NULL,
  `purok` int(11) NOT NULL,
  `monthlyIncome` double NOT NULL,
  PRIMARY KEY (`householdNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `household_records` (`householdNumber`, `headOfTheFamily`, `position`, `householdSize`, `occupation`, `purok`, `monthlyIncome`) VALUES
(1, 'John Doe', 'Head', 4, 'Engineer', 1, 5000.0),
(2, 'Jane Doe', 'Spouse', 4, 'Doctor', 1, 6000.0),
(3, 'Michael Johnson', 'Head', 3, 'Teacher', 2, 4500.0),
(4, 'Emily Smith', 'Spouse', 2, 'Nurse', 3, 5500.0),
(5, 'David Brown', 'Head', 5, 'Lawyer', 2, 7000.0),
(6, 'Sophia Miller', 'Spouse', 5, 'Architect', 2, 6500.0),
(7, 'Daniel Wilson', 'Head', 2, 'Artist', 1, 4000.0),
(8, 'Olivia Davis', 'Spouse', 2, 'Chef', 1, 4500.0),
(9, 'Liam Taylor', 'Head', 3, 'IT Specialist', 3, 5500.0),
(10, 'Ava Hernandez', 'Spouse', 3, 'Marketing Manager', 3, 6000.0),
(11, 'Ethan White', 'Head', 4, 'Scientist', 4, 8000.0),
(12, 'Emma Turner', 'Spouse', 4, 'Journalist', 4, 7500.0),
(13, 'Noah Baker', 'Head', 3, 'Entrepreneur', 5, 9000.0),
(14, 'Sophie Brooks', 'Spouse', 3, 'Psychologist', 5, 8500.0),
(15, 'Mason Mitchell', 'Head', 6, 'Archaeologist', 3, 7200.0);

COMMIT;
