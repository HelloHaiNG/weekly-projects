/*
Navicat MySQL Data Transfer

Source Server         : db
Source Server Version : 50627
Source Host           : localhost:3306
Source Database       : db_weekly

Target Server Type    : MYSQL
Target Server Version : 50627
File Encoding         : 65001

Date: 2019-05-06 16:23:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_backuser
-- ----------------------------
DROP TABLE IF EXISTS `t_backuser`;
CREATE TABLE `t_backuser` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_backuser
-- ----------------------------
INSERT INTO `t_backuser` VALUES ('1', 'hong.liao', '$2a$10$jghjYIsyGIeOM..EboDauuKWSO8APUs8U7ujhdwNulK8PlcxDypXS', '1');

-- ----------------------------
-- Table structure for t_frontuser
-- ----------------------------
DROP TABLE IF EXISTS `t_frontuser`;
CREATE TABLE `t_frontuser` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `kindOf` varchar(255) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_frontuser
-- ----------------------------
INSERT INTO `t_frontuser` VALUES ('4', 'hong.liao', '$2a$10$62p8DlShW1puiI3B/m17ZefNiEckEDWbkl6PHijSbiKpVoHbFwOce', '1', '0', '2');
INSERT INTO `t_frontuser` VALUES ('5', 'jack', '$2a$10$suG01LGuc0jFY/dEYSvAfO4NpxmbNE14aHY26NK4Ls34RzaSijCey', '1', '0', '1');
INSERT INTO `t_frontuser` VALUES ('6', 'lucy', '$2a$10$72reDr54WcjRgt9ABlU5qOABzFvoeyAnHNXkJNj3Qkq.ThRH7x5g6', '1', '0', '1');

-- ----------------------------
-- Table structure for t_weekly
-- ----------------------------
DROP TABLE IF EXISTS `t_weekly`;
CREATE TABLE `t_weekly` (
  `weeklyId` int(11) NOT NULL AUTO_INCREMENT,
  `weeklyName` varchar(255) DEFAULT NULL,
  `creatTime` datetime DEFAULT NULL,
  `modifyUser` varchar(255) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `downloadCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`weeklyId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_weekly
-- ----------------------------
INSERT INTO `t_weekly` VALUES ('1', '2018年11月第一周', '2018-11-01 09:20:32', 'hong.liao', '2018-11-01 09:21:36', '1', '4');
INSERT INTO `t_weekly` VALUES ('6', '2018年11月第二周', '2018-11-08 09:22:25', 'hong.liao', '2018-11-08 09:22:41', '1', '5');
INSERT INTO `t_weekly` VALUES ('7', '2018年11月第三周', '2018-11-15 09:23:04', 'hong.liao', '2018-11-15 09:23:15', '1', '16');
INSERT INTO `t_weekly` VALUES ('8', '2018年11月第四周', '2018-12-21 09:23:45', 'hong.liao', '2018-11-21 09:24:04', '1', '27');
INSERT INTO `t_weekly` VALUES ('9', '2018年02月第一周', '2018-12-28 15:49:35', 'hong.liao', '2018-12-28 15:49:35', '0', '0');
INSERT INTO `t_weekly` VALUES ('10', '2018年04月第三周', '2018-12-28 15:53:40', 'hong.liao', '2018-12-29 16:21:21', '1', '0');

-- ----------------------------
-- Table structure for t_weeklydetail
-- ----------------------------
DROP TABLE IF EXISTS `t_weeklydetail`;
CREATE TABLE `t_weeklydetail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `weeklyId` int(11) DEFAULT NULL,
  `weeklyName` varchar(255) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `groupId` int(11) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `editorTime` datetime DEFAULT NULL,
  `thisWeekContent` varchar(255) DEFAULT NULL,
  `nextWeekContent` varchar(255) DEFAULT NULL,
  `trouble` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_weeklydetail
-- ----------------------------
INSERT INTO `t_weeklydetail` VALUES ('9', '8', '2018年11月第四周', '4', null, 'hong.liao', '2018-12-03 11:47:11', '单反流口水的', '地方', '当时法国');
INSERT INTO `t_weeklydetail` VALUES ('12', '8', '2018年11月第四周', '5', null, 'jack', '2018-12-03 14:19:20', '1、简单学习了使用zookeeper相关api（connect，create，update，delete等）。<br />2、学习了小程序的开发流程和相关知识。<br />3、开发一个简单的天气预报小程序，目前已经发布，可以在微信搜索小程序&ldquo;天气预报微程序&rdquo;进行查看（界面不是很美观）。<br />4、上周使用websocket进行了简单的消息发送，本周根据websocket写了一个简单的网页聊天室（实现了私聊+多对多聊天）', '1、继续学习zookeeper', '1、对于zookeeper的学习，目前还是处于特别浅显的地方，其中的命名服务，分布式锁，负载均衡等功能还没有仔细了解和学习<br />2、在开发小程序时，一开始对于微信开发工具的项目结构不是很清楚，包括项目中每个文件的含义以及作用也不是很明白，根据查看博客以及微信公众平台小程序的相关介绍慢慢清楚如果进行代码的编写。');
INSERT INTO `t_weeklydetail` VALUES ('13', '8', '2018年11月第四周', '6', null, 'lucy', '2018-12-03 15:31:45', 'gsrhrtg', 'rgt5rgs', 'retgrsh');
INSERT INTO `t_weeklydetail` VALUES ('14', '7', '2018年11月第三周', '4', '1', 'hong.liao', '2018-12-19 10:31:25', 'efrhtgs', 'rtgw', 'ertgwet');

-- ----------------------------
-- Table structure for t_weeklygroup
-- ----------------------------
DROP TABLE IF EXISTS `t_weeklygroup`;
CREATE TABLE `t_weeklygroup` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(255) DEFAULT NULL,
  `modifyId` int(11) DEFAULT NULL,
  `modifyUser` varchar(255) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_weeklygroup
-- ----------------------------
INSERT INTO `t_weeklygroup` VALUES ('1', '秦奋组', '1', 'hong.liao', '2018-12-29 16:23:49', '0');
INSERT INTO `t_weeklygroup` VALUES ('2', '赵振组', '1', 'hong.liao', '2018-12-11 15:31:50', '1');
INSERT INTO `t_weeklygroup` VALUES ('3', '徐大权组', '1', 'hong.liao', '2018-12-29 16:23:47', '0');
INSERT INTO `t_weeklygroup` VALUES ('4', '杨俊德组', '1', 'hong.liao', '2018-12-29 14:02:03', '1');
INSERT INTO `t_weeklygroup` VALUES ('5', '石纯山组', '1', 'hong.liao', '2018-12-29 14:24:56', '1');
INSERT INTO `t_weeklygroup` VALUES ('6', '叶桂玲组', '1', 'hong.liao', '2018-12-29 14:27:52', '1');
INSERT INTO `t_weeklygroup` VALUES ('7', '陈吉组', '1', 'hong.liao', '2018-12-29 14:29:33', '1');
INSERT INTO `t_weeklygroup` VALUES ('8', '付宗旺组', '1', 'hong.liao', '2018-12-29 14:37:03', '1');
INSERT INTO `t_weeklygroup` VALUES ('9', '张三组', '1', 'hong.liao', '2018-12-29 16:23:03', '1');
INSERT INTO `t_weeklygroup` VALUES ('10', '王五组', '1', 'hong.liao', '2018-12-29 16:23:06', '1');
