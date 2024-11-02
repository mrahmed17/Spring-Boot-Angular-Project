-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: running
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `attendances`
--

DROP TABLE IF EXISTS `attendances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendances` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `clock_in_time` datetime(6) DEFAULT NULL,
  `clock_out_time` datetime(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8o39cn3ghqwhccyrrqdesttr8` (`user_id`),
  CONSTRAINT `FK8o39cn3ghqwhccyrrqdesttr8` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendances`
--

LOCK TABLES `attendances` WRITE;
/*!40000 ALTER TABLE `attendances` DISABLE KEYS */;
INSERT INTO `attendances` VALUES (1,'2024-09-22 04:25:20.335175','2024-09-22 04:25:26.427855','2024-09-22',1),(2,'2024-09-22 04:25:33.462374','2024-09-22 04:26:44.442739','2024-09-22',2),(3,'2024-09-22 04:25:40.915420','2024-09-22 04:25:59.586118','2024-09-22',3),(4,'2024-09-22 04:25:47.678720','2024-09-22 04:26:50.646297','2024-09-22',4),(5,'2024-09-22 04:25:54.747055','2024-09-22 04:26:37.610655','2024-09-22',6),(6,'2024-09-22 06:00:33.433313','2024-09-22 06:00:39.393409','2024-09-22',7),(7,'2024-09-22 06:09:11.571420','2024-09-22 06:19:11.631703','2024-09-22',8),(8,'2024-09-22 12:08:25.894785','2024-09-22 12:15:33.889501','2024-09-22',10);
/*!40000 ALTER TABLE `attendances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `address` varchar(255) DEFAULT NULL,
  `basic_salary` decimal(38,2) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `joined_date` date DEFAULT NULL,
  `national_id` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_photo` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','COMPANY','EMPLOYEE','MANAGER','PAYROLL') DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,1,'Mog bazar',70000.00,'6545645','1982-09-08','emransir@mail.com','Emran Sir','Male','2024-01-16','5456456','5456456','Raju_b77d957c-6c9e-45d9-8f3c-e46765a6b077.jpg','ADMIN','2024-09-22'),(2,1,'Dhanmondi',50000.00,'56489','2024-09-22','towhid@mail.com','Towhid Alam','Male','2024-01-16','5456456','123','Raju_b77d957c-6c9e-45d9-8f3c-e46765a6b077.jpg','EMPLOYEE','2024-09-22'),(3,1,'Dhanmondi',50000.00,'56489','2024-09-22','shaba@mail.com','Shabab','Male','2024-01-16','5456456','123','Raju_b77d957c-6c9e-45d9-8f3c-e46765a6b077.jpg','ADMIN','2024-09-22'),(4,1,'Lalbagh',50000.00,'56489','1996-09-17','raju@mail.com','Raju','Male','2024-01-16','5456456','123465','Raju_3ad99289-7e39-427d-86eb-2ee756b2c8d1.jpg','ADMIN','2024-09-22'),(6,0,'Azimput Colony',55000.00,'895652','2024-09-18','mostafa@mail.com','Mostafa Aminur Rashid','Male','2024-09-16','125346','123456','Mostafa_Aminur_Rashid_aa0d20f2-36cd-4a69-9d3d-ea63912a07d2.jpg','MANAGER','2024-09-22'),(7,0,'Mirpur Zoo',50000.00,'7484154658','2024-02-25','nusrat@gmail.com','Nusrat Jahan','Female','2024-09-08','44456487','465465','Nusrat_Jahan_733feba8-ab3a-49e4-8b62-d1220cbeef4e.jpg','PAYROLL','2024-09-22'),(8,0,'Kollanpur Dhanmondi',50000.00,'7484154658','2024-03-31','shamima@mail.com','Shamima','Female','2024-09-01','44456487','7897897','Shamima_9c4d276e-eccf-4c48-8cc5-e5005fd29d55.jpg','MANAGER','2024-09-22'),(9,0,'Mohammadpur',50000.00,'465456','2023-09-10','neyamul@mail.com','Neyamul','Male','2024-09-03','125346','46546546','Neyamul_32a6b55d-d7fb-4ed6-bda8-a0d4b434f77c.jpg','EMPLOYEE','2024-09-22'),(10,0,'Mohammadpur',50000.00,'465456','2024-09-01','admin@gmail.com','Neyamul','','2024-09-16','125346','123','Neyamul_6a7a3bfb-6423-48a7-b7ea-df719e53cbab.jpg','EMPLOYEE','2024-09-22');
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

-- Dump completed on 2024-09-22 13:57:43
