/*
Navicat MySQL Data Transfer

Source Server         : lq-local
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : fish

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-10-17 23:07:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_app_secret`
-- ----------------------------
DROP TABLE IF EXISTS `tb_app_secret`;
CREATE TABLE `tb_app_secret` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT 'user_name',
  `api_key` varchar(100) DEFAULT NULL COMMENT '用户api key',
  `secret_key` varchar(100) DEFAULT NULL COMMENT '用户secret',
  `feature` varchar(500) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_app_secret
-- ----------------------------
INSERT INTO `tb_app_secret` VALUES ('4', '4', null, 'f0ndkp4vrG', 'O8EYnUoKu7QADsMLr', null, '2017-07-11 23:16:26', '2017-07-11 23:16:26', '1');
INSERT INTO `tb_app_secret` VALUES ('5', '5', null, 'cLpbYwF6tOlCh', 'pUDCkIKb7FVLWg1Y', null, '2017-07-12 00:33:56', '2017-07-12 00:33:56', '1');
INSERT INTO `tb_app_secret` VALUES ('6', '6', null, 'INzorPZA3fhs', 'K2Gw6rzRvAeTI4SdLMU', null, '2017-07-13 00:55:02', '2017-07-13 00:55:02', '1');

-- ----------------------------
-- Table structure for `tb_artical`
-- ----------------------------
DROP TABLE IF EXISTS `tb_artical`;
CREATE TABLE `tb_artical` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `img` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_publish` tinyint(2) DEFAULT NULL,
  `article_type` tinyint(4) DEFAULT NULL,
  `content` text COLLATE utf8_unicode_ci,
  `feature` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_artical
-- ----------------------------
INSERT INTO `tb_artical` VALUES ('1', '17', '111111', null, '1', '1', 'asdasdasd撒大苏打阿三大苏打阿三大苏打啊实打实大苏打啊实打实打算', null, '2017-10-09 23:15:53', '2017-10-09 23:15:53', '1');

-- ----------------------------
-- Table structure for `tb_artical_fish`
-- ----------------------------
DROP TABLE IF EXISTS `tb_artical_fish`;
CREATE TABLE `tb_artical_fish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `img` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `water_type` tinyint(4) DEFAULT NULL,
  `bait` tinyint(4) DEFAULT NULL,
  `fish_type` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fish_lines` double(11,0) DEFAULT NULL,
  `fishing_func` tinyint(4) DEFAULT NULL,
  `fish_pole_length` double(11,0) DEFAULT NULL,
  `fish_pole_brand` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location_address` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `article_type` tinyint(4) DEFAULT NULL,
  `is_publish` tinyint(2) DEFAULT NULL,
  `content` text COLLATE utf8_unicode_ci,
  `feature` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_artical_fish
-- ----------------------------
INSERT INTO `tb_artical_fish` VALUES ('1', '123', '123', null, '123', '123', '123', '123', '123', '123', '123', '123', '123', '123', '123', null, '2017-07-24 01:08:22', '2017-07-24 01:08:22', null);
INSERT INTO `tb_artical_fish` VALUES ('3', '13', '1啊实打实的', null, '1', '1', '1', '1', '1', '1', '1', '啊实打实的', '1', '1', '啊实打实大苏打啊实打实大苏打啊实打实大苏打阿三大苏打', null, '2017-07-30 15:43:12', '2017-07-30 15:43:12', '1');
INSERT INTO `tb_artical_fish` VALUES ('4', '14', '1啊实打实的123', null, '1', '1', '1', '1', '1', '1', '1', '啊实打实的', '1', '1', '啊实打实大苏打啊实打实大苏打啊实打实大苏打阿三大苏打', null, '2017-07-30 15:45:09', '2017-07-30 15:45:09', '1');
INSERT INTO `tb_artical_fish` VALUES ('5', '13', '1啊实打实的12311', null, '1', '1', '1', '1', '1', '1', '1', '啊实打实的', '1', '1', '啊实打实大苏打啊实打实大苏打啊实打实大苏打阿三大苏打', null, '2017-07-30 15:45:14', '2017-07-30 15:45:14', '1');

-- ----------------------------
-- Table structure for `tb_fish_shop`
-- ----------------------------
DROP TABLE IF EXISTS `tb_fish_shop`;
CREATE TABLE `tb_fish_shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `introduce` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic0` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lng` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lat` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `site_phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `publish_type` tinyint(2) DEFAULT NULL,
  `feature` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_fish_shop
-- ----------------------------
INSERT INTO `tb_fish_shop` VALUES ('1', '13', 'qweqwe', 'qwe', '1', 'qwe', 'qwe', 'qwe', 'qwe', 'qwe', 'qwe', 'qwe', null, null, '2017-07-30 21:22:00', '2017-07-30 21:22:00', '1');

-- ----------------------------
-- Table structure for `tb_fish_site`
-- ----------------------------
DROP TABLE IF EXISTS `tb_fish_site`;
CREATE TABLE `tb_fish_site` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `introduce` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic0` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pic3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lng` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lat` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `site_type` tinyint(4) DEFAULT NULL,
  `site_fee_type` tinyint(4) DEFAULT NULL,
  `site_fish_type` tinyint(4) DEFAULT NULL,
  `site_phone` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `can_park` tinyint(2) DEFAULT NULL,
  `can_night` tinyint(2) DEFAULT NULL,
  `can_eat` tinyint(2) DEFAULT NULL,
  `can_hotel` tinyint(2) DEFAULT NULL,
  `feature` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_fish_site
-- ----------------------------
INSERT INTO `tb_fish_site` VALUES ('1', '13', '亲卫队请问', 'qqweqw', 'qwe', 'qwe', 'qwe', 'qwe', 'qwe', 'qwe', 'qwe', null, '2', '1', '1', '1', '1', '0', '1', null, '2017-07-30 21:00:19', '2017-07-30 21:00:19', '1');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source` tinyint(4) DEFAULT NULL COMMENT '用户来源 1电话 2微信 3 扣扣 4 微博',
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL,
  `nick_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `point` int(11) DEFAULT '0' COMMENT '积分',
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar_url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '缩略图',
  `tel` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(11) DEFAULT '0' COMMENT '等级',
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(1) DEFAULT NULL,
  `feature` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('12', null, null, '1', '2017-07-17 23:27:36', null, '烦你翻翻', '高新区啊', null, null, null, '18782420424', '0', '2017-07-14 00:02:04', '2017-07-17 23:55:31', '1', null);
INSERT INTO `tb_user` VALUES ('13', null, null, '0', '2017-07-17 23:16:04', null, 'heihei', null, '0', null, 'asdasdasd1', null, '0', '2017-07-17 23:18:42', '2017-07-17 23:31:25', '1', null);
INSERT INTO `tb_user` VALUES ('14', null, null, '0', '2017-07-20 23:40:46', null, 'string', null, null, null, 'string', null, null, '2017-07-20 23:42:34', '2017-07-20 23:46:54', '1', null);
INSERT INTO `tb_user` VALUES ('15', null, null, '0', '2017-07-20 00:00:00', null, 'asdasd', null, null, null, 'http://wx.qlogo.cn/mmopen/S1FLibdMXLaZqgxH0tfCRHVy7rVXm8cWdqoHR2lCZD4yopZicvJffEYicPCknlP7T1icNxO2vc6iaK330ICicMhaic6OYGemgmXR6ibI/0', null, null, '2017-07-20 23:49:22', '2017-07-21 00:06:09', '1', null);
INSERT INTO `tb_user` VALUES ('16', null, null, '0', '2017-07-22 12:46:59', null, 'string', null, null, null, 'string', null, null, '2017-07-22 12:47:11', '2017-07-22 12:47:11', '1', null);
INSERT INTO `tb_user` VALUES ('17', null, null, '1', '1970-01-01 08:00:00', null, '123', null, null, null, '123', null, null, '2017-07-22 12:55:28', '2017-07-22 12:55:28', '1', null);

-- ----------------------------
-- Table structure for `tb_user_auths`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_auths`;
CREATE TABLE `tb_user_auths` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `identity_type` int(20) DEFAULT NULL COMMENT '登录类型1 手机 2 邮箱 3 qq 4 微信 5 微博 6 用户名',
  `identifier` varchar(200) DEFAULT NULL COMMENT '标识（手机号 邮箱 用户名或第三方应用的唯一标识）',
  `credential` varchar(500) DEFAULT NULL COMMENT '密码凭证（站内的保存密码，站外的不保存或保存token）',
  `verified` tinyint(4) DEFAULT NULL COMMENT '是否已经验证 0 未验证 111 已验证',
  `feature` varchar(500) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_auths
-- ----------------------------
INSERT INTO `tb_user_auths` VALUES ('4', '4', '1', '123', '123', '1', null, '2017-07-11 23:16:22', '2017-07-11 23:16:22', '1');
INSERT INTO `tb_user_auths` VALUES ('5', '5', '1', '1234', '123', '1', null, '2017-07-12 00:33:56', '2017-07-12 00:33:56', '1');
INSERT INTO `tb_user_auths` VALUES ('12', '12', '1', '18782420424', '111111', '1', null, '2017-07-14 00:02:04', '2017-07-17 23:55:35', '1');
INSERT INTO `tb_user_auths` VALUES ('13', '13', '4', '111111', '123456', '1', null, '2017-07-17 23:19:01', '2017-07-17 23:31:25', '1');
INSERT INTO `tb_user_auths` VALUES ('14', '14', '3', 'string', 'string', '1', null, '2017-07-20 23:42:34', '2017-07-20 23:46:54', '1');
INSERT INTO `tb_user_auths` VALUES ('15', '15', '4', 'sdi5s6337s93j39f94', null, '1', null, '2017-07-20 23:49:22', '2017-07-21 00:06:09', '1');
INSERT INTO `tb_user_auths` VALUES ('16', '16', '0', 'string', 'string', '1', null, '2017-07-22 12:47:11', '2017-07-22 12:47:11', '1');
INSERT INTO `tb_user_auths` VALUES ('17', '17', '123', '13', '123', '1', null, '2017-07-22 12:55:28', '2017-07-22 12:55:28', '1');

-- ----------------------------
-- Table structure for `tb_user_login`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_login`;
CREATE TABLE `tb_user_login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `login_ip` varchar(255) DEFAULT NULL COMMENT '用户登录ip',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `longitude` double DEFAULT NULL COMMENT '用户登陆位置坐标',
  `location` varchar(100) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  `device_token` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT '',
  `feature` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_userId` (`user_id`) COMMENT '用户id 唯一'
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_login
-- ----------------------------
INSERT INTO `tb_user_login` VALUES ('4', '7', null, null, null, null, null, '2017-07-13 23:32:45', '2017-07-13 23:32:45', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('5', '8', null, null, null, null, null, '2017-07-13 23:39:33', '2017-07-13 23:39:33', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('6', '9', null, null, null, null, null, '2017-07-13 23:52:54', '2017-07-13 23:52:54', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('7', '10', null, null, null, null, null, '2017-07-13 23:56:16', '2017-07-13 23:56:16', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('8', '11', null, null, null, null, null, '2017-07-13 23:59:04', '2017-07-13 23:59:04', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('9', '12', null, '2017-07-17 23:55:36', null, null, null, '2017-07-14 00:02:04', '2017-07-17 23:55:36', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('10', '13', null, '2017-07-17 23:31:25', null, null, null, '2017-07-17 23:19:05', '2017-07-17 23:31:25', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('11', '14', null, '2017-07-20 23:46:54', null, null, null, '2017-07-20 23:42:34', '2017-07-20 23:46:54', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('12', '15', null, '2017-07-21 00:06:09', null, null, null, '2017-07-20 23:49:22', '2017-07-21 00:06:09', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('13', '16', null, null, null, null, null, '2017-07-22 12:47:11', '2017-07-22 12:47:11', null, null, null, null);
INSERT INTO `tb_user_login` VALUES ('14', '17', null, null, null, null, null, '2017-07-22 12:55:28', '2017-07-22 12:55:28', null, null, null, null);

-- ----------------------------
-- Table structure for `tb_user_social`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_social`;
CREATE TABLE `tb_user_social` (
  `id` bigint(20) NOT NULL,
  `user_from_id` bigint(20) DEFAULT NULL,
  `user_to_id` bigint(20) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tb_user_social
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user_valid`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_valid`;
CREATE TABLE `tb_user_valid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tel` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `valid_type` tinyint(4) DEFAULT NULL COMMENT '获取验证码类型 1 注册',
  `valid_code` varchar(10) DEFAULT NULL COMMENT '验证码',
  `valid_date` datetime DEFAULT NULL COMMENT '验证时间',
  `expire_time` int(11) DEFAULT NULL,
  `feature` varchar(500) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_valid
-- ----------------------------
INSERT INTO `tb_user_valid` VALUES ('70', '18782420424', '1', '428291', '2017-07-12 23:39:11', '60', null, '2017-07-13 23:32:22', '2017-07-13 23:32:45', '-1');
INSERT INTO `tb_user_valid` VALUES ('71', '18782420424', '1', '868877', '2017-07-12 23:39:11', '60', null, '2017-07-13 23:38:58', '2017-07-13 23:39:27', '-1');
INSERT INTO `tb_user_valid` VALUES ('72', '18782420424', '1', '405545', '2017-07-12 23:39:11', '60', null, '2017-07-13 23:52:10', '2017-07-13 23:52:10', '1');
INSERT INTO `tb_user_valid` VALUES ('73', '18782420424', '1', '813342', '2017-07-12 23:39:11', '60', null, '2017-07-13 23:58:19', '2017-07-13 23:58:19', '1');
INSERT INTO `tb_user_valid` VALUES ('74', '18782420424', '1', '682191', '2017-07-14 00:01:43', '60', null, '2017-07-14 00:01:42', '2017-07-14 00:02:02', '-1');
INSERT INTO `tb_user_valid` VALUES ('75', '18782420424', '1', '647660', '2017-07-17 22:32:23', '60', null, '2017-07-17 22:32:23', '2017-07-17 22:32:23', '1');
INSERT INTO `tb_user_valid` VALUES ('76', '18782420423', '2', '108350', '2017-07-17 22:32:34', '60', null, '2017-07-17 22:32:34', '2017-07-17 22:32:34', '1');
INSERT INTO `tb_user_valid` VALUES ('77', '18782420423', '2', '703188', '2017-07-17 22:33:15', '60', null, '2017-07-17 22:33:15', '2017-07-17 22:33:15', '1');
INSERT INTO `tb_user_valid` VALUES ('78', '18782420424', '2', '764193', '2017-07-17 23:43:44', '60', null, '2017-07-17 23:43:44', '2017-07-17 23:43:44', '1');
INSERT INTO `tb_user_valid` VALUES ('79', '18782420424', '2', '532959', '2017-07-17 23:44:03', '60', null, '2017-07-17 23:44:02', '2017-07-17 23:44:02', '1');
INSERT INTO `tb_user_valid` VALUES ('80', '18782420424', '2', '486443', '2017-07-17 23:49:44', '60', null, '2017-07-17 23:49:43', '2017-07-17 23:50:19', '-1');
