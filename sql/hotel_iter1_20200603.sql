-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: localhost    Database: Hotel
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `Coupon`
--

DROP TABLE IF EXISTS `Coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Coupon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `hotelId` int(11) DEFAULT '-1',
  `couponType` int(11) NOT NULL,
  `couponName` varchar(255) NOT NULL,
  `target_money` int(11) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `discount_money` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Coupon`
--

LOCK TABLES `Coupon` WRITE;
/*!40000 ALTER TABLE `Coupon` DISABLE KEYS */;
INSERT INTO `Coupon` VALUES (2,'满500-100优惠',2,3,'满减优惠券',500,0,1,NULL,NULL,100),
(3,'满100-10优惠',2,3,'满减优惠券',100,0,1,NULL,NULL,10),
(4,'满400-40优惠',2,3,'满减优惠券',400,0,1,NULL,NULL,40);
/*!40000 ALTER TABLE `Coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CreditRecord`
--

DROP TABLE IF EXISTS `CreditRecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CreditRecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `credit` double(255,0) NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(8) DEFAULT '1',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CreditRecord`
--

LOCK TABLES `CreditRecord` WRITE;
/*!40000 ALTER TABLE `CreditRecord` DISABLE KEYS */;
INSERT INTO `CreditRecord` VALUES (1,4,100,'2020-06-03 23:16:42','2020-06-03 23:16:42','1','用户注册，信用值初始化'),(2,5,100,'2020-06-03 23:16:42','2020-06-03 23:16:42','1','用户注册，信用值初始化'),(3,7,100,'2020-06-03 23:16:42','2020-06-03 23:16:42','1','用户注册，信用值初始化');
/*!40000 ALTER TABLE `CreditRecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Hotel`
--

DROP TABLE IF EXISTS `Hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Hotel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hotelName` varchar(255) NOT NULL,
  `hotelDescription` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `bizRegion` varchar(255) DEFAULT NULL,
  `hotelStar` varchar(255) DEFAULT NULL,
  `phoneNum` int(11) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `manager_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hotel`
--

LOCK TABLES `Hotel` WRITE;
/*!40000 ALTER TABLE `Hotel` DISABLE KEYS */;
INSERT INTO `Hotel` VALUES (1,'汉庭酒店','欢迎您入住','苏州园区','XiDan','Four',1829373819,4.8,1),(2,'儒家酒店','欢迎您入住','南京市鼓楼区珠江路268号','XiDan','Four',1829373819,4.8,2),(3,'桂圆酒店','欢迎您入住','南京市栖霞区珠江路268号','XiDan','Four',1829553719,4.8,3);
/*!40000 ALTER TABLE `Hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Member`
--
DROP TABLE IF EXISTS `Member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Member` (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `memberPoints` int(11) NOT NULL DEFAULT '0' COMMENT '会员积分',
  `birthday` datetime DEFAULT NULL COMMENT '会员生日',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Dumping data for table `Member`
--
LOCK TABLES `Member` WRITE;
/*!40000 ALTER TABLE `Member` DISABLE KEYS */;
INSERT INTO Member VALUES(4,444,'1999-11-04 00:00:00');
/*!40000 ALTER TABLE `Member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OrderList`
--

DROP TABLE IF EXISTS `OrderList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OrderList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `hotelId` int(11) DEFAULT NULL,
  `hotelName` varchar(255) DEFAULT NULL,
  `checkInDate` varchar(255) DEFAULT NULL,
  `checkOutDate` varchar(255) DEFAULT NULL,
  `roomType` varchar(255) DEFAULT NULL,
  `roomNum` int(255) DEFAULT NULL,
  `peopleNum` int(255) DEFAULT NULL,
  `haveChild` tinytext,
  `createDate` varchar(255) DEFAULT NULL,
  `price` decimal(65,0) DEFAULT NULL,
  `clientName` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL,
  `orderState` varchar(255) DEFAULT NULL,
  `cancelReason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OrderList`
--

LOCK TABLES `OrderList` WRITE;
/*!40000 ALTER TABLE `OrderList` DISABLE KEYS */;
INSERT INTO `OrderList`
VALUES (14,4,2,'儒家酒店','2020-06-23','2020-06-25','Family',3,3,'0','2020-06-03',2294,'测试一号','12345678919','已执行','未撤销'),
(15,4,2,'儒家酒店','2020-06-25','2020-06-30','Family',1,2,'0','2020-06-03',1895,'测试一号','12345678919','客户撤销','123'),
(16,5,2,'儒家酒店','2020-06-23','2020-06-25','Family',3,3,'0','2020-06-03',2294,'测试二号','12345678919','已执行','未撤销'),
(17,7,2,'儒家酒店','2020-06-23','2020-06-25','Family',3,3,'0','2020-06-03',2294,'测试三号','12345678919','已预订','未撤销');
/*!40000 ALTER TABLE `OrderList` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` double DEFAULT NULL,
  `curNum` int(11) DEFAULT NULL,
  `total` int(11) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `roomType` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

-- LOCK TABLES `Room` WRITE;
-- /*!40000 ALTER TABLE `Room` DISABLE KEYS */;
-- INSERT INTO `Room` VALUES (2,199,20,20,1,'BigBed'),(3,299,30,30,1,'DoubleBed'),(4,399,10,10,1,'Family'),(6,399,7,10,2,'Family');
-- /*!40000 ALTER TABLE `Room` ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Table structure for table `MyTag`
--

DROP TABLE IF EXISTS `MyTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MyTag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签编号',
  `tagName` varchar(255) NOT NULL COMMENT '标签名称',
  `hotelId` int(11) NOT NULL COMMENT '酒店编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MyTag`
--

LOCK TABLES `MyTag` WRITE;
/*!40000 ALTER TABLE `MyTag` DISABLE KEYS */;
INSERT INTO `MyTag` VALUES (1,'免费无线上网',1),(2,'叫醒服务',1),(3,'免费保存贵重物品（保险箱）',1),(4,'泳池',1),(5,'温泉',2),(6,'麻将馆',2),(7,'棋牌室',3),(8,'健身房',3);
/*!40000 ALTER TABLE `MyTag` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `CommentList`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CommentList` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `hotelId` int(11) DEFAULT NULL,
  `commentValue` int(11) DEFAULT NULL,
  `commentContent` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL ,
  `userName` varchar(255) DEFAULT NULL ,
  `reply` varchar(255) DEFAULT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

-- BEGIN;
-- /*!40000 ALTER TABLE `Room` DISABLE KEYS */;
-- INSERT INTO `CommentList` VALUES (3,2,1,4,'非常好');
-- /*!40000 ALTER TABLE `Room` ENABLE KEYS */;
-- COMMIT;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(11) DEFAULT NULL,
  `usertype` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(256) DEFAULT null,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User`
VALUES (0,'websiteAdmin@qq.com','d959caadac9b13dcb3e609440135cf54','网站管理员',NULL,'Manager','2000-01-01',NULL,'2020-06-01'),
(1,'hanTing@qq.com','d959caadac9b13dcb3e609440135cf54','汉庭酒店',NULL,'HotelManager','2000-02-01',NULL,'2020-06-01'),
(2,'ruJia@qq.com','d959caadac9b13dcb3e609440135cf54','儒家酒店',NULL,'HotelManager','2000-03-01',NULL,'2020-06-01'),
(3,'guiYuan@qq.com','d959caadac9b13dcb3e609440135cf54','桂圆酒店',NULL,'HotelManager','2000-04-01',NULL,'2020-06-02'),
(4,'client1@qq.com','d959caadac9b13dcb3e609440135cf54','测试一号','1391541689601234','Client','2000-05-01','1881bb12-3f09-459a-b288-59e724cb7c94.jpeg','2020-06-03'),
(5,'client2@qq.com','d959caadac9b13dcb3e609440135cf54','测试二号','12345678911','Client','2000-06-01','f24da812-79ef-4a8e-89f6-bb1b7831100e.jpeg','2020-06-11'),
(6,'operator1@qq.com','d959caadac9b13dcb3e609440135cf54','网站运营人员一',NULL,'Operator','2000-07-01',NULL,'2020-06-11'),
(7,'client3@qq.com','d959caadac9b13dcb3e609440135cf54','测试三号','12345678911','Client','2000-07-01',NULL,'2020-06-11'),
(8,'operator2@qq.com','d959caadac9b13dcb3e609440135cf54','网站运营人员二',NULL,'Operator','2000-08-01',NULL,'2020-06-11');
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-03 23:33:33
