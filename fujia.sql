/*
Navicat MySQL Data Transfer

Source Server         : test
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : fujia

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2017-06-23 13:31:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `stuId` varchar(100) CHARACTER SET utf8 NOT NULL,
  `stuName` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stuAge` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stuSex` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `stuSchool` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`stuId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('2015214038', '黄卿怡', '20', '男', '重庆邮电大学');
INSERT INTO `student` VALUES ('2015214061', '王佳嘉', '20', '女', '重庆邮电大学');
INSERT INTO `student` VALUES ('2015214058', '冯兰越', '20', '女', '重庆医科大学');
INSERT INTO `student` VALUES ('2015214062', 'wea', '20', '女', 'www');
INSERT INTO `student` VALUES ('2015214063', 'sd', '20', '女', 'dddd');
INSERT INTO `student` VALUES ('2015214064', 'ss', '20', '女', 'ss');
INSERT INTO `student` VALUES ('2015214065', 'ccc', '20', '女', 'aa');
INSERT INTO `student` VALUES ('2015214066', 'vvv', '20', '女', 'aa');
