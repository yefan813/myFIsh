/*
Navicat MySQL Data Transfer

Source Server         : fish-local
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : fish

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-10-23 22:53:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_app_secret
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
-- Table structure for tb_artical
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
-- Table structure for tb_artical_fish
-- ----------------------------
DROP TABLE IF EXISTS `tb_artical_fish`;
CREATE TABLE `tb_artical_fish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `img` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `water_type` tinyint(4) DEFAULT NULL,
  `bait` tinyint(4) DEFAULT NULL,
  `fish_time` datetime DEFAULT NULL,
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
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `topic_id` bigint(20) DEFAULT NULL,
  `topic_type` int(11) DEFAULT NULL,
  `content` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `from_uid` bigint(20) DEFAULT NULL,
  `from_uname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `from_uavtor` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `feature` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for tb_fish_shop
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
-- Table structure for tb_fish_site
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
-- Table structure for tb_reply
-- ----------------------------
DROP TABLE IF EXISTS `tb_reply`;
CREATE TABLE `tb_reply` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `comment_id` bigint(11) DEFAULT NULL,
  `reply_id` bigint(11) DEFAULT NULL,
  `reply_type` int(11) DEFAULT NULL,
  `content` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `from_uid` bigint(11) DEFAULT NULL,
  `from_uname` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `from_uavtor` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `feature` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Table structure for tb_user
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
-- Table structure for tb_user_auths
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
-- Table structure for tb_user_login
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
-- Table structure for tb_user_social
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
-- Table structure for tb_user_valid
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
