-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pharmacy
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (1,'r','r','r',_binary ''),(2,'mail@vlad','VladG','vlad',_binary '');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drug`
--

DROP TABLE IF EXISTS `drug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `stock` varchar(255) DEFAULT NULL,
  `rating` varchar(255) DEFAULT NULL,
  `discount` varchar(255) DEFAULT NULL,
  `ybuys` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drug`
--

LOCK TABLES `drug` WRITE;
/*!40000 ALTER TABLE `drug` DISABLE KEYS */;
INSERT INTO `drug` VALUES (1,'Buna la toate','Aspirina','1.00','raceala','45','5.0/5 din 3 rates','3','4'),(2,'Ibuprofen','Nurofen','44.23','durere','3','4.4/5 din 7 rates','6','5'),(3,'Fructe de padure','Ceai','3.64','raceala','4','3.4/5 din 11 rates','8','2'),(4,'Capsule','Vitamina C','16.21','raceala','1','2.5/5 din 2 rates','4','4'),(5,'Unguent','Lioton Gel','78.02','durere','1','0.0/0 din 0 rates','0','0'),(6,'Hidratant','Crema Maini','32.32','ingrijire','3','3.8/5 din 15 rates','11','6'),(8,'Pt durere in gat','Strepsils','41.99','durere','22','4.8/5 din 6 rates','9','2'),(9,'Pt par uscat','Sampon L\'oreal','66.68','ingrijire','0','5.0/5 din 6 rates','0','0'),(10,'Trei bucati clasic','Prezervative','12.07','sexual','5','3.8/5 din 7 rates','0','0'),(11,'Cu capsunele','Lubrifiant','27.56','sexual','30','4.1/5 din 9 rates','2','3'),(12,'Diverse culori','Gel unghii','30.55','ingrijire','12','3.7/5 din 5 rates','4','3'),(13,'Reduce febra','Paracetamol','8.01','raceala','2','4.6/5 din 8 rates','0','0'),(16,'Reduce febra','Algocalmin','55.77','raceala','0','4.7/5 din 4 rates','0','0');
/*!40000 ALTER TABLE `drug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `drug_favorite`
--

DROP TABLE IF EXISTS `drug_favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug_favorite` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drug_favorite`
--

LOCK TABLES `drug_favorite` WRITE;
/*!40000 ALTER TABLE `drug_favorite` DISABLE KEYS */;
INSERT INTO `drug_favorite` VALUES (1,'2020/04/01','Aspirina','1.00'),(2,'2020/04/23','Nurofen','44.23'),(3,'2020/04/23','Ceai','3.64'),(4,'2020/04/23','Vitamina C','16.21'),(5,'2020/04/24','Lioton Gel','78.02'),(6,'2020/04/24','Crema Maini','32.32'),(7,'2020/04/24','Strepsils','41.99'),(8,'2020/04/25','Sampon L\'oreal','66.68'),(9,'2020/04/25','Prezervative','12.07'),(10,'2020/04/28','Lubrifiant','27.56'),(11,'2020/04/28','Gel unghii','30.55'),(12,'2020/04/29','Paracetamol','8.01'),(13,'2020/04/29','Algocalmin','55.77');
/*!40000 ALTER TABLE `drug_favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (2),(2);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacy`
--

DROP TABLE IF EXISTS `pharmacy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pharmacy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `money` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacy`
--

LOCK TABLES `pharmacy` WRITE;
/*!40000 ALTER TABLE `pharmacy` DISABLE KEYS */;
INSERT INTO `pharmacy` VALUES (1,'5.02');
/*!40000 ALTER TABLE `pharmacy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mail` varchar(255) DEFAULT NULL,
  `money` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (3,'mail@vlad','115.01','Vlad','vlad',_binary '\0'),(6,'mail@rusu','37.88','Rusu Daniel','rusu',_binary '\0'),(10,'mail@spaniol','55.66','Spaniol Horatiu','spaniol',_binary '\0'),(13,'mail@catalin','316.64','Catalin Maghiar','cata',_binary '\0'),(14,'mail@adi','6.48','Adi Florea','adi',_binary '\0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_drug`
--

DROP TABLE IF EXISTS `user_drug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_drug` (
  `user_id` int NOT NULL,
  `drug_id` int NOT NULL,
  `id` int NOT NULL,
  PRIMARY KEY (`user_id`,`drug_id`,`id`),
  KEY `FK3twl5o53oa3wbwvnopikhfwdq` (`drug_id`),
  CONSTRAINT `FK3twl5o53oa3wbwvnopikhfwdq` FOREIGN KEY (`drug_id`) REFERENCES `drug` (`id`),
  CONSTRAINT `FKiidi6jd47we7wf6evb7gptvb4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_drug`
--

LOCK TABLES `user_drug` WRITE;
/*!40000 ALTER TABLE `user_drug` DISABLE KEYS */;
INSERT INTO `user_drug` VALUES (3,6,0),(3,13,1);
/*!40000 ALTER TABLE `user_drug` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-07 13:11:22
