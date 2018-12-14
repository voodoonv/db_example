CREATE DATABASE `db_camp`;

USE `db_camp`;

DROP TABLE IF EXISTS `Developers`;

CREATE TABLE `Developers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullName` varchar(100) NOT NULL,
  `displayTitle` varchar(100) DEFAULT NULL,
  `ts_create` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ_name` (`fullName`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;