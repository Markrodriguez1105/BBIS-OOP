
USE businessrecord;

CREATE TABLE IF NOT EXISTS record (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL COLLATE utf8mb4_general_ci,
    Address VARCHAR(255) COLLATE utf8mb4_general_ci,
    Type VARCHAR(255) COLLATE utf8mb4_general_ci,
    Income INT,
    Cost INT

INSERT INTO record (Name, Address, Type, Income, Cost) VALUES
('John Doe', '123 Main St', 'Retail', 50000, 20000),
('Jane Smith', '456 Oak St', 'Service', 60000, 25000),
('Bob Johnson', '789 Pine St', 'Manufacturing', 75000, 30000);

);
