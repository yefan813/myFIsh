/*
Navicat MySQL Data Transfer

Source Server         : lq-local
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : fish

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-06-30 02:07:42
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  `role` tinyint(4) DEFAULT NULL,
  `nick_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `passwrod` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `point` int(11) DEFAULT NULL COMMENT '积分',
  `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar_url` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '缩略图',
  `tel` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  `yn` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

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
  `valid_code` varchar(10) DEFAULT NULL COMMENT '验证码',
  `valid_date` datetime DEFAULT NULL COMMENT '验证时间',
  `expire_time` int(11) DEFAULT NULL,
  `feature` varchar(500) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `yn` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
