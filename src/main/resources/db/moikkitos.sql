CREATE DATABASE  IF NOT EXISTS `blog` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `blog`;
-- MySQL dump 10.13  Distrib 8.0.12, for macos10.13 (x86_64)
--
-- Host: localhost    Database: blog
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `feed`
--

DROP TABLE IF EXISTS `feed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `feed` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `user_id` int(11) NOT NULL,
                        `user_name` varchar(50) DEFAULT NULL,
                        `content` varchar(500) NOT NULL DEFAULT '',
                        `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                        `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feed`
--

LOCK TABLES `feed` WRITE;
/*!40000 ALTER TABLE `feed` DISABLE KEYS */;
INSERT INTO `feed` VALUES 
                          (3,3,'chunyang','test create feed','2023-05-16 08:57:58','2023-05-16 08:57:58'),
                          (4,3,'chunyang','test create feed 666','2023-05-16 09:00:09','2023-05-16 09:00:09'),
                          (5,3,'chunyang','test create feed 666','2023-05-16 09:00:46','2023-05-16 09:00:46'),
                          (6,3,'chunyang','test create feed 666','2023-05-16 09:05:32','2023-05-16 09:05:32'),
                          (16,3,'lixiao','hello','2023-05-17 07:30:21','2023-05-17 07:30:21'),
                          (17,3,'lixiao','hello','2023-05-17 15:07:32','2023-05-17 15:07:32');
/*!40000 ALTER TABLE `feed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `follow` (
                          `id` int(11) NOT NULL AUTO_INCREMENT,
                          `follower_id` int(11) NOT NULL,
                          `followed_id` int(11) NOT NULL,
                          `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          KEY `follower_id` (`follower_id`),
                          KEY `followed_id` (`followed_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
INSERT INTO `follow` VALUES (1,1,2,'2023-05-15 02:18:36','2023-05-15 02:18:36'),
                            (2,2,1,'2023-05-15 02:18:50','2023-05-15 02:18:52'),
                            (9,3,1,'2023-05-16 15:07:17','2023-05-16 15:07:17');
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `permission` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `name` varchar(255) NOT NULL,
                              `description` varchar(255) NOT NULL,
                              `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'create_feed','create feed','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                                (2,'edit_feed','edit feed','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                                (3,'delete_feed','delete feed','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                                (4,'follow_user','follow a user','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                                (5,'unfollow_user','unfollow a user','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                                (6,'view_feed','view feeds','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                                (7,'search_users','search users','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                                (8,'view_following','view following users','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                                (9,'view_followers','view followers','2023-05-16 12:38:10','2023-05-16 12:38:10');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) NOT NULL,
                        `description` varchar(255) NOT NULL,
                        `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'admin','system admin','2023-05-16 12:38:10','2023-05-16 12:38:10'),
                          (2,'user','normal user','2023-05-16 12:38:10','2023-05-16 12:38:10');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission`
--

DROP TABLE IF EXISTS `role_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `role_permission` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `role_id` int(11) NOT NULL,
                                   `permission_id` int(11) NOT NULL,
                                   `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `role_id` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;
INSERT INTO `role_permission` VALUES (1,1,1,'2023-05-16 12:38:10'),
                                     (2,1,2,'2023-05-16 12:38:10'),
                                     (3,1,3,'2023-05-16 12:38:10'),
                                     (4,1,4,'2023-05-16 12:38:10'),
                                     (5,1,5,'2023-05-16 12:38:10'),
                                     (6,1,6,'2023-05-16 12:38:10'),
                                     (7,1,7,'2023-05-16 12:38:10'),
                                     (8,1,8,'2023-05-16 12:38:10'),
                                     (9,1,9,'2023-05-16 12:38:10'),
                                     (10,2,1,'2023-05-16 12:38:10'),
                                     (11,2,2,'2023-05-16 12:38:10'),
                                     (12,2,3,'2023-05-16 12:38:10'),
                                     (13,2,4,'2023-05-16 12:38:10'),
                                     (14,2,5,'2023-05-16 12:38:10'),
                                     (15,2,6,'2023-05-16 12:38:10'),
                                     (16,2,7,'2023-05-16 12:38:10'),
                                     (17,2,8,'2023-05-16 12:38:10'),
                                     (18,2,9,'2023-05-16 12:38:10');
/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `name` varchar(60) NOT NULL,
                        `email` varchar(60) NOT NULL,
                        `password` varchar(60) NOT NULL,
                        `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `idx_name` (`name`),
                        UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'chunyang','11281918@qq.com','e10adc3949ba59abbe56e057f20f883e','2023-05-15 02:08:12','2023-05-17 10:29:09'),
                          (2,'yangliu','892532131@qq.com','e10adc3949ba59abbe56e057f20f883e','2023-05-15 02:08:15','2023-05-17 10:29:09'),
                          (3,'lixiao','2280810856@qq.com','e10adc3949ba59abbe56e057f20f883e','2023-05-15 07:31:43','2023-05-15 07:31:43');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-16 15:50:05
