CREATE DATABASE  IF NOT EXISTS `agenda`;
USE `agenda`;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(21) NOT NULL,
  `name` VARCHAR(100) NULL,
  `start` TIMESTAMP NULL,
  `end` TIMESTAMP NULL,
  PRIMARY KEY (`id`));
