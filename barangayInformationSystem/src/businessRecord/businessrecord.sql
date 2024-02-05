/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  James
 * Created: Jan 13, 2024
 */

SET SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO';
                    SET AUTOCOMMIT = 0;
                    START TRANSACTION;
                    SET time_zone = '+00:00';
                    +
                    DROP TABLE IF EXISTS business_records;
                    CREATE TABLE IF NOT EXISTS business_records ( 
                    id int(11) NOT NULL AUTO_INCREMENT, 
                    name varchar(255) NOT NULL, 
                    address varchar(255), 
                    type varchar(255), 
                    income int(11), 
                    cost int(11),  
                    PRIMARY KEY (id)   
                    ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
              
                    INSERT INTO business_records (name, address, type, income, cost) VALUES
                    ('Business1', 'Address1', 'Type1', 5000, 2000),
                    ('Business2', 'Address2', 'Type2', 6000, 2500),
                    ('Business3', 'Address3', 'Type3', 7000, 3000),
                    ('Business4', 'Address4', 'Type4', 8000, 3500),
                    ('Business5', 'Address5', 'Type5', 9000, 4000);
                    
                    COMMIT;