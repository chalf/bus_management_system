-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: busapp
-- ------------------------------------------------------
-- Server version	8.4.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `buses`
--

DROP TABLE IF EXISTS `buses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buses` (
  `bus_id` int NOT NULL AUTO_INCREMENT,
  `bus_number` varchar(20) NOT NULL,
  `route_id` int DEFAULT NULL,
  PRIMARY KEY (`bus_id`),
  KEY `route_id` (`route_id`),
  CONSTRAINT `buses_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `routes` (`route_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `buses`
--

LOCK TABLES `buses` WRITE;
/*!40000 ALTER TABLE `buses` DISABLE KEYS */;
INSERT INTO `buses` VALUES (1,'123',1),(2,'112',3),(3,'115',2);
/*!40000 ALTER TABLE `buses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favoriteroutes`
--

DROP TABLE IF EXISTS `favoriteroutes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favoriteroutes` (
  `favorite_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `route_id` int NOT NULL,
  PRIMARY KEY (`favorite_id`),
  KEY `user_id` (`user_id`),
  KEY `route_id` (`route_id`),
  CONSTRAINT `favoriteroutes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `favoriteroutes_ibfk_2` FOREIGN KEY (`route_id`) REFERENCES `routes` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favoriteroutes`
--

LOCK TABLES `favoriteroutes` WRITE;
/*!40000 ALTER TABLE `favoriteroutes` DISABLE KEYS */;
/*!40000 ALTER TABLE `favoriteroutes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routes`
--

DROP TABLE IF EXISTS `routes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routes` (
  `route_id` int NOT NULL AUTO_INCREMENT,
  `route_name` varchar(100) NOT NULL,
  `direction` enum('outbound','inbound') NOT NULL,
  `start_point` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `end_point` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`route_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routes`
--

LOCK TABLES `routes` WRITE;
/*!40000 ALTER TABLE `routes` DISABLE KEYS */;
INSERT INTO `routes` VALUES (1,'Phan Huy Thực - Nguyễn Hữu Thọ','outbound','Phan Huy Thực','Nguyễn Hữu Thọ',NULL,'2024-08-26 02:51:09','2024-08-26 09:43:52'),(2,'cong quynh','inbound','36 cong quynh','55 nguyen trai',NULL,'2024-08-26 04:00:23','2024-08-26 04:00:23'),(3,'Cho Ben Thanh - Quan 8','outbound','Cho Ben Thanh','Quan 8',NULL,NULL,NULL),(4,'lÃª vÄng lÆ°Æ¡ng','inbound','Cho Ben Thanh','pha huy thuc',NULL,NULL,NULL);
/*!40000 ALTER TABLE `routes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `routestops`
--

DROP TABLE IF EXISTS `routestops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `routestops` (
  `route_stop_id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NOT NULL,
  `stop_id` int NOT NULL,
  `direction` enum('outbound','inbound') NOT NULL,
  `stop_order` int NOT NULL,
  PRIMARY KEY (`route_stop_id`),
  KEY `route_id` (`route_id`),
  KEY `stop_id` (`stop_id`),
  CONSTRAINT `routestops_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `routes` (`route_id`),
  CONSTRAINT `routestops_ibfk_2` FOREIGN KEY (`stop_id`) REFERENCES `stops` (`stop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `routestops`
--

LOCK TABLES `routestops` WRITE;
/*!40000 ALTER TABLE `routestops` DISABLE KEYS */;
INSERT INTO `routestops` VALUES (1,1,1,'inbound',1),(2,1,2,'inbound',2),(4,1,5,'outbound',3),(5,3,2,'outbound',1);
/*!40000 ALTER TABLE `routestops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedules`
--

DROP TABLE IF EXISTS `schedules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedules` (
  `schedule_id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NOT NULL,
  `bus_id` int NOT NULL,
  `direction` enum('outbound','inbound') NOT NULL,
  `departure_time` time NOT NULL,
  `arrival_time` time NOT NULL,
  `day_of_week` enum('Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday') NOT NULL,
  PRIMARY KEY (`schedule_id`),
  KEY `route_id` (`route_id`),
  KEY `bus_id` (`bus_id`),
  CONSTRAINT `schedules_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `routes` (`route_id`),
  CONSTRAINT `schedules_ibfk_2` FOREIGN KEY (`bus_id`) REFERENCES `buses` (`bus_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedules`
--

LOCK TABLES `schedules` WRITE;
/*!40000 ALTER TABLE `schedules` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stops`
--

DROP TABLE IF EXISTS `stops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stops` (
  `stop_id` int NOT NULL AUTO_INCREMENT,
  `stop_name` varchar(100) NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `address` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`stop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stops`
--

LOCK TABLES `stops` WRITE;
/*!40000 ALTER TABLE `stops` DISABLE KEYS */;
INSERT INTO `stops` VALUES (1,'Phan Huy Thực',10.74761520,106.70661890,'104 Phan Huy Thực, P, Quận 7, Hồ Chí Minh 72913, Việt Nam','2024-08-26 07:39:06','2024-08-26 07:39:35'),(2,'Lê Văn Lương',10.74685590,106.70458980,'219 Đ. Lê Văn Lương, Tân Kiểng, Quận 7, Hồ Chí Minh, Việt Nam','2024-08-26 07:41:05','2024-08-26 07:41:05'),(5,'Nguyễn Hữu Thọ',10.73981700,106.70092000,'358 Đ. Nguyễn Hữu Thọ, Tân Hưng, Quận 7, Hồ Chí Minh, Việt Nam','2024-08-26 09:04:35','2024-08-26 09:04:35');
/*!40000 ALTER TABLE `stops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trafficreports`
--

DROP TABLE IF EXISTS `trafficreports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trafficreports` (
  `report_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `report_type` enum('traffic jam','accident','other') NOT NULL,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`report_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `trafficreports_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trafficreports`
--

LOCK TABLES `trafficreports` WRITE;
/*!40000 ALTER TABLE `trafficreports` DISABLE KEYS */;
/*!40000 ALTER TABLE `trafficreports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `role` enum('ROLE_CITIZEN','ROLE_ADMIN') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'hieu','$2a$10$DcjPJIdYl9Er1LFkmDwM/OuQDNMyryB.S97KanI7FGZQX6SOovGKu','hieu@gmail.com','Duong Hieu','https://res.cloudinary.com/dsfdkyanf/image/upload/v1724040995/bnsygaltrlurcrvhxmso.jpg','ROLE_CITIZEN','2024-08-19 04:16:36','2024-08-24 13:54:48'),(6,'test','$2a$10$r20lXo5VpNOIC640329L9.VGfCrWy8fnCZvJgyYyYa12x33Qfjkmi','test@gmail.com','dddddd','https://res.cloudinary.com/dsfdkyanf/image/upload/v1724041225/ppnzy0o2dajxxtzt4dju.jpg','ROLE_CITIZEN','2024-08-19 04:20:26','2024-08-24 13:54:48'),(15,'test00','$2a$10$gVzMqHWmOYS.5pX0l1bXzOZJrQV66X9HiWFoFS5gtFw7Is9ynlyKC','tt44@gmail.com','Hiếu','https://res.cloudinary.com/dsfdkyanf/image/upload/v1724077513/wuzmacetkufufatnmtym.jpg','ROLE_CITIZEN','2024-08-19 14:25:16','2024-08-24 13:54:48'),(26,'admin','$2a$10$hOoe2YqviKEso4CEWAZmpuwT5woHgXHZmqU9AnlB7smkDDxky3rly','admin@busplanner.com','Duong Hieu','https://res.cloudinary.com/dsfdkyanf/image/upload/v1724388467/nxvpkvhr04zzkn3exsl6.jpg','ROLE_ADMIN','2024-08-23 04:47:48','2024-08-24 13:55:22'),(27,'thuan','$2a$10$Thhj02wbvV9L6IZL3fbQse6dX9JLRx8T2dXXDCanqn4NXPWyniNi.','thuan@gmail.com','phan thuan','https://res.cloudinary.com/dsfdkyanf/image/upload/v1724556952/tjadip0srvudzflvivlz.png','ROLE_CITIZEN','2024-08-25 03:35:52','2024-08-25 03:35:52');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-26 16:58:57
