/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50610
Source Host           : 127.0.0.1:3306
Source Database       : zuul-routes

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2020-03-26 16:00:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for zuul_route
-- ----------------------------
DROP TABLE IF EXISTS `zuul_route`;
CREATE TABLE `zuul_route` (
  `id` varchar(255) NOT NULL,
  `path` varchar(50) DEFAULT NULL,
  `service_id` varchar(50) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `strip_prefix` varchar(255) DEFAULT NULL,
  `retryable` varchar(255) DEFAULT NULL,
  `enabled` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
