DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `head_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `vip_id` bigint DEFAULT NULL COMMENT 'VIP等级id',
  `sign` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个性签名',
  `money` bigint NOT NULL DEFAULT '0' COMMENT '可提现余额度',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  `is_disable` int NOT NULL DEFAULT '1' COMMENT '禁用 1不禁用 0禁用',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `login_time` timestamp NULL DEFAULT NULL COMMENT '此次登录时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `last_login_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后一次登陆IP',
  `login_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '当前登录ip',
  `wechat` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信号码',
  `user_type` tinyint DEFAULT NULL COMMENT '1-用户，2-达人，3-机构，4-平台',
  `agency_id` bigint NOT NULL DEFAULT '-1' COMMENT '代理id',
  `login_times` int NOT NULL DEFAULT '0' COMMENT '登录次数',
  `gender` int DEFAULT '1' COMMENT '1-女,0-男',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话.',
  `upd_pwd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改密码时间',
  `register_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户注册域名',
  `disable_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '禁用说明',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `invite_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邀请码',
  `is_talent` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否达人，0不是 1是',
  `province` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '省份',
  `city` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '城市',
  `weixin_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '绑定微信的id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_nickname` (`nick_name`) USING BTREE,
  KEY `idx_phone` (`phone`) USING BTREE,
  KEY `idx_invitecode` (`invite_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `tb_user`
    DROP COLUMN `bank_id`,
    ADD INDEX `index_nickname`(`nick_name`) USING BTREE,
    ADD INDEX `idx_phone`(`phone`) USING BTREE;
ALTER TABLE `tb_user`
    ADD INDEX `idx_invitecode`(`invite_code`) USING BTREE;
ALTER TABLE `tb_user`
ADD UNIQUE INDEX `wx_unique`(`weixin_id`) USING BTREE;


ALTER TABLE `db_0`.`tb_user`
MODIFY COLUMN `gender` int(0) NULL DEFAULT 1 COMMENT '1-女,0-男' AFTER `login_times`;


ALTER TABLE `db_0`.`tb_user`
ADD COLUMN `verified` int(1) NULL DEFAULT 0 COMMENT '是否实名认证 0否 1是' AFTER `weixin_id`,
ADD COLUMN `id_num` varchar(32) NULL COMMENT '身份证号码' AFTER `verified`,
ADD COLUMN `real_name` varchar(16) NULL COMMENT '真实姓名' AFTER `id_num`;



DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收获详细地址',
  `is_disable` int NOT NULL DEFAULT '0' COMMENT '禁用0不禁用 1禁用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  `phone` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收货手机号',
  `city` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '城市',
  `default_flag` int NOT NULL DEFAULT '0' COMMENT '是否默认地址 0非默认 1默认',
  `name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收货人',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`,`del_flag`,`is_disable`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户地址表';



DROP TABLE IF EXISTS `tb_user_agency`;
CREATE TABLE `tb_user_agency` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(36) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `p_user_id` bigint NOT NULL,
  `num` int NOT NULL DEFAULT '0' COMMENT '子代理数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`,`p_user_id`) USING BTREE,
  KEY `idx_puser_id` (`p_user_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户代理表';

INSERT INTO `tb_user_agency`(`id`, `user_id`, `p_user_id`, `num`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
VALUES ('806493672093777921', '806488232119238656', -1, 3, '2021-02-03 11:59:19', '2021-02-03 12:05:49', NULL, NULL, 0);



DROP TABLE IF EXISTS `tb_user_bank`;
CREATE TABLE `tb_user_bank` (
    `id` bigint NOT NULL COMMENT '主键id',
    `user_id` bigint NOT NULL COMMENT '用户id',
    `bank` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开户银行',
    `sub_bank_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开户支行名称',
    `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '开户姓名',
    `card_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '银行卡号',
    `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `is_check` tinyint DEFAULT '1' COMMENT '是否审核 null-未审核，1-通过，0-不通过',
    `is_blacklist` tinyint DEFAULT '0' COMMENT '是否是黑名单 0-否，1-是',
    `is_default` tinyint(1) DEFAULT '0',
    `province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份',
    `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
    `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
    `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_card_number` (`card_number`,`is_check`) USING BTREE,
    KEY `idx_user_id` (`user_id`,`is_check`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户银行卡管理';




DROP TABLE IF EXISTS `tb_turn_image`;
CREATE TABLE `tb_turn_image`  (
`id` varchar(32)  NOT NULL,
`url` varchar(512)  NULL DEFAULT NULL COMMENT '图片地址',
`turn_url` varchar(512) NULL DEFAULT NULL COMMENT '跳转地址',
`title` varchar(32)  NULL DEFAULT NULL COMMENT '标题',
`sort` bigint(32)  NULL DEFAULT NULL COMMENT '排序字段',
`begin_time` timestamp(0) NOT NULL  COMMENT '开始时间',
`end_time` timestamp(0) NOT NULL  COMMENT '结束时间',
`is_disable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否禁用：0，禁用，1，有效',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '首页轮播图配置';

INSERT INTO `tb_turn_image`(`id`, `url`, `turn_url`, `title`, `sort`, `begin_time`, `end_time`, `is_disable`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
VALUES ('244324345424', 'www.baidu.com', 'www.baidu.com', 'dddd', 1, '2021-02-03 13:36:54', '2021-04-15 13:36:57', 1, '2021-02-03 13:37:02', NULL, NULL, NULL, 0);

INSERT INTO `tb_turn_image`(`id`, `url`, `turn_url`, `title`, `sort`, `begin_time`, `end_time`, `is_disable`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
VALUES ('2443245654', 'www.baidu.com', 'www.baidu.com', 'ddddfdfdfd', 2, '2021-02-03 13:36:54', '2021-04-18 13:36:57', 1, '2021-02-03 13:37:02', NULL, NULL, NULL, 0);

INSERT INTO `tb_turn_image`(`id`, `url`, `turn_url`, `title`, `sort`, `begin_time`, `end_time`, `is_disable`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
VALUES ('24432456343454', 'www.baidu.com', 'www.baidu.com', 'ddddfdftttttttdfd', 2, '2021-02-02 13:36:54', '2021-02-01 13:36:57', 1, '2021-02-03 13:37:02', NULL, NULL, NULL, 0);



DROP TABLE IF EXISTS `tb_community`;
CREATE TABLE `tb_community` (
    `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `title` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
    `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
    `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接',
    `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片或者视频地址',
    `city` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '定位城市',
    `type` tinyint(1) DEFAULT '1' COMMENT '朋友圈类型:1 照片 2视频 3课程  4其他  ',
    `user_type` tinyint(1) DEFAULT '1' COMMENT '1-用户，2-达人，3-机构，4-平台',
    `star_num` bigint DEFAULT '0' COMMENT '收藏数量',
    `good_num` bigint DEFAULT '0' COMMENT '点赞数量',
    `forward_num` bigint DEFAULT '0' COMMENT '转发数量',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
    `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
    `watch_num` bigint DEFAULT '0' COMMENT '观看数量',
    `check_status` int DEFAULT '0' COMMENT '审核状态 0待审核 1 审核未通过2审核通过',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idex_user_id` (`user_id`,`check_status`),
    KEY `ind_city_type` (`city`,`type`,`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='社区朋友圈表';


DROP TABLE IF EXISTS `tb_user_focus`;
CREATE TABLE `tb_user_focus` (
 `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
 `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
 `focus_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关注人',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
 `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
 `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
 `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
 PRIMARY KEY (`id`) USING BTREE,
 KEY `idx_user_id` (`user_id`) USING BTREE,
 KEY `indx_foces` (`focus_user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='关注表';




DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course` (
 `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
 `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
 `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程地址',
 `image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面图片',
 `price` bigint DEFAULT '0' COMMENT '价格',
 `type` tinyint(1) NOT NULL DEFAULT '2' COMMENT '类型1 -收费,2 免费， 3限时免费',
 `watch_num` bigint DEFAULT '0' COMMENT '观看数量',
 `good_num` bigint DEFAULT '0' COMMENT '点赞数量',
 `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标识0-完结,1-正在更新',
 `sort` bigint DEFAULT NULL COMMENT '排序字段',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
 `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
 `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
 `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
 `course_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '课程类型 1-文章 2-视频',
 `city` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市',
 `star_num` bigint DEFAULT '0' COMMENT '收藏数量',
 `forward_num` bigint DEFAULT '0' COMMENT '转发数量',
 `content_type` int DEFAULT '1' COMMENT '内容分类 1亨氧APP项目 2医美创业项目 3引流爆破项目',
 `recommend` int DEFAULT '0' COMMENT '设为推荐 0 不推荐 1推荐',
 `banner` int DEFAULT '0' COMMENT '设为封面课程 0 不推荐 1推荐',
 PRIMARY KEY (`id`) USING BTREE,
 KEY `idx_type` (`type`,`title`,`course_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程表';


ALTER TABLE `tb_course`
ADD COLUMN `content` varchar(255) NULL COMMENT '文章简介' AFTER `banner`,
ADD COLUMN `course_info` varchar(255) NULL COMMENT '课程详细信息' AFTER `content`;
ALTER TABLE `tb_course`
ADD COLUMN `user_id` varchar(32) NULL COMMENT '作者' AFTER `course_info`;


DROP TABLE IF EXISTS `tb_user_course`;
CREATE TABLE `tb_user_course` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `course_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程id',
  `begin_time` timestamp NOT NULL COMMENT '开始时间',
  `end_time` timestamp NOT NULL COMMENT '结束时间',
  `price` bigint DEFAULT '0' COMMENT 'f价格',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户课程表';



DROP TABLE IF EXISTS `tb_talent_info`;
CREATE TABLE `tb_talent_info` (
      `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
      `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
      `effect` decimal(3,2) DEFAULT '0.00' COMMENT '效果评分',
      `attitude` decimal(3,2) DEFAULT '0.00' COMMENT '态度评分',
      `price` decimal(3,2) DEFAULT '0.00' COMMENT '价格评分',
      `average_score` decimal(3,2) DEFAULT '0.00' COMMENT '综合评分',
      `deposit` bigint DEFAULT '0' COMMENT '保证金',
      `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
      `id_card` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '身份证号码',
      `city` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市',
      `time` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '从业年限',
      `id_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否身份验证0-已验证,1-未验证',
      `authenticated` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否完成验证0-已完成,1-未完成',
      `contract_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否签署达人协议 0已签署,1-未签署',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
      `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
      `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
      `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
      `num` bigint DEFAULT '0' COMMENT '签约机构数量',
      `order_num` bigint DEFAULT '0' COMMENT '接单量',
      `advisory_num` bigint DEFAULT '0' COMMENT '咨询量',
      `like_num` bigint DEFAULT '0' COMMENT '点赞量',
      PRIMARY KEY (`id`) USING BTREE,
      KEY `idx_user_id` (`user_id`,`authenticated`),
      KEY `idx_city` (`city`,`authenticated`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='达人信息表';




DROP TABLE IF EXISTS `tb_case`;
CREATE TABLE `tb_case` (
`id` varchar(32) NOT NULL,
`user_id` varchar(32) NOT NULL,
`content` varchar(512) DEFAULT NULL COMMENT '项目描述内容',
`type` varchar(32) DEFAULT NULL COMMENT '项目分类',
`time` timestamp NOT NULL COMMENT '案例时间',
`source` varchar(64) DEFAULT NULL COMMENT '案例来源 ',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
`create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
`image_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
PRIMARY KEY (`id`) USING BTREE,
KEY `ind_userid_type` (`user_id`,`type`) USING BTREE
) ENGINE=InnoDB  COMMENT='达人机构案例表';


DROP TABLE IF EXISTS `tb_project`;
CREATE TABLE `tb_project` (
      `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
      `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名称',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
      `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
      `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
      `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
      PRIMARY KEY (`id`) USING BTREE,
      KEY `index_name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='项目（项目大类）表';



DROP TABLE IF EXISTS `tb_project_info`;
CREATE TABLE `tb_project_info` (
   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目id',
   `project_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名称',
   `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '子项目类型 ，1具体小项目 2 假体材料 3 杂项',
   `price_low` bigint DEFAULT '0' COMMENT '最低价格',
   `price_high` bigint DEFAULT '0' COMMENT '最高价格',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
   `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
   `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
   `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
   PRIMARY KEY (`id`) USING BTREE,
   KEY `idx_project_id` (`project_id`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='子类项目，材料，杂项详细表';

###关联表每个医院结构的材料，项目，杂项等价格都不一样
DROP TABLE IF EXISTS `tb_hospital_project`;
CREATE TABLE `tb_hospital_project` (
   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `project_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目id',
   `hospital_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构医院id',
   `price_low` bigint DEFAULT '0' COMMENT '最低价格',
   `price_high` bigint DEFAULT '0' COMMENT '最高价格',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
   `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
   `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
   `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
   PRIMARY KEY (`id`) USING BTREE,
   KEY `idx_project_id` (`hospital_id`,`project_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='机构材料项目表';

DROP TABLE IF EXISTS `tb_hospital`;
CREATE TABLE `tb_hospital` (
   `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '医院名称',
   `content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '医院简介',
   `image_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '医院图标图片',
   `business_license` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '医院营业执照',
   `licence` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '医院执业许可证',
   `video_url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '视频地址',
   `contract_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '签署的协议的超链接',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
   `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
   `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
   `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
   `user_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构认证上传人',
   `authenticated` int NOT NULL DEFAULT '0' COMMENT '是否完成验证 0未验证 1已验证',
   PRIMARY KEY (`id`) USING BTREE,
   KEY `idx_name` (`name`) USING BTREE,
   KEY `idx_flag` (`authenticated`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='机构(医院)表';

ALTER TABLE tb_hospital
    ADD COLUMN `user_id` varchar(32) NULL COMMENT '机构认证上传人' AFTER `del_flag`,
    ADD COLUMN `authenticated` int(1) NOT NULL DEFAULT 0 COMMENT '是否完成验证 0未验证 1已验证' AFTER `user_id`;


DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
    `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账号',
    `operation_type` int NOT NULL COMMENT '1-医美项目,2课程 ,3提现 4购买会员卡',
    `amount` bigint DEFAULT NULL COMMENT '用户请求金额，单位：分',
    `inside_card_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '充值卡号',
    `checker` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '提现审核人',
    `check_dt` datetime DEFAULT NULL COMMENT '审核时间',
    `remark` varchar(121) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
    `status` int DEFAULT '1' COMMENT '订单状态1：未审核 2：成功 3：失败',
    `pay_money` bigint DEFAULT '0' COMMENT '系统根据用户请求金额生成实际支付金额，单位：分',
    `outside_card_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '提现银行卡号',
    `opt_status` tinyint NOT NULL DEFAULT '0' COMMENT '操作状态0-未确认 1-已确认 2-成功 3-已取消 4-锁定 5-恢复 6-拒绝 ',
    `sys_ins_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '系统收款银行-收款人',
    `content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '订单简介',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
    `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
    `num` int DEFAULT '1' COMMENT '商品数量',
    `hospital_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构id',
    `talent_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '达人id',
    `course_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程id',
    `project_id` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '项目id',
    `effect` decimal(3,2) DEFAULT '0.00' COMMENT '效果评分',
    `attitude` decimal(3,2) DEFAULT '0.00' COMMENT '态度评分',
    `price` decimal(3,2) DEFAULT '0.00' COMMENT '价格评分',
    `average_score` decimal(3,2) DEFAULT '0.00' COMMENT '综合评分',
    `evaluate_status` int DEFAULT '0' COMMENT '订单是否已评价 0未评价 1已评价',
    `address_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货地址id',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_user_id` (`user_id`,`operation_type`),
    KEY `idx_talent_id` (`talent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='订单表';



ALTER TABLE `tb_order`
ADD COLUMN `pay_type` int(1) NULL DEFAULT 1 COMMENT '支付类型 1 银行卡 2微信 3支付宝' AFTER `address_id`;

DROP TABLE IF EXISTS `tb_party`;
CREATE TABLE `tb_party` (
    `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '派对标题',
    `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '封面图',
    `profiles` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '简介',
    `price` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '价格',
    `num` int DEFAULT '0' COMMENT '名额',
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发起人id、',
    `qr_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '微信群二维码',
    `start_time` timestamp NOT NULL COMMENT '开始时间',
    `end_time` timestamp NULL DEFAULT NULL COMMENT '结束时间',
    `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '活动地址',
    `detail_imgae` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '活动详情图片',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
    `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
    `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
    `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
    `watch_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '观看数量',
    `good_num` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '点赞数量',
    `star_num` bigint DEFAULT '0' COMMENT '收藏数量',
    `forward_num` bigint DEFAULT '0' COMMENT '转发数量',
    `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市',
    PRIMARY KEY (`id`),
    KEY `index_city` (`city`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




DROP TABLE IF EXISTS `tb_activity`;
CREATE TABLE `tb_activity`  (
`id` varchar(32)  NOT NULL,
`title` varchar(32)  NOT NULL COMMENT '活动标题',
`url` varchar(128)  NOT NULL COMMENT '活动地址',
`image` varchar(128)  NOT NULL COMMENT '封面图片',
`join_num` bigint(32)   DEFAULT 0 COMMENT '参与数量',
`good_num` bigint(32)   DEFAULT 0 COMMENT '点赞数量',
`star_num` bigint(32) DEFAULT 0 COMMENT '收藏数量',
`watch_num` bigint(32)   DEFAULT 0 COMMENT '观看数量',
`forward_num` bigint(32) DEFAULT 0 COMMENT '转发数量',
`state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标识0-完结,1-正在更新',
`sort` bigint(32)  NULL DEFAULT NULL COMMENT '排序字段',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
`city` varchar(32) DEFAULT NULL COMMENT '城市',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '活动表';



DROP TABLE IF EXISTS `tb_user_star`;
CREATE TABLE `tb_user_star` (
`id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
`user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
`community_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
`star` int NOT NULL DEFAULT '0' COMMENT '是否收藏',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
`update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
`del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
`good` int NOT NULL DEFAULT '0' COMMENT '是否点赞',
PRIMARY KEY (`id`) USING BTREE,
KEY `idx_user_id` (`user_id`,`community_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户点赞收藏朋友圈表';


ALTER TABLE `tb_user_star`
ADD COLUMN `page_type` int(1) NOT NULL COMMENT '内容分类 1 朋友圈 2 活动 3课程' AFTER `good`;


DROP TABLE IF EXISTS `tb_hot_search`;
CREATE TABLE `tb_hot_search` (
     `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '热搜标题',
     `content_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '热搜分类：1 首页热搜 2社区热搜 3朋友圈热搜',
     `search_num` bigint NOT NULL DEFAULT '0' COMMENT '搜索次数',
     `recommend` tinyint(1) NOT NULL DEFAULT '0' COMMENT '推荐标识0-正常,1-推荐',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
     `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
     `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
     `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
     PRIMARY KEY (`id`) USING BTREE,
     KEY `idx_type` (`content_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='热搜表';

DROP TABLE IF EXISTS `tb_course_info`;
CREATE TABLE `tb_course_info` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `course_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '课程地址',
  `image` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '封面图片',
  `price` bigint DEFAULT '0' COMMENT '价格',
  `sort` bigint DEFAULT NULL COMMENT '排序字段',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_course_id` (`course_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程章节表';


DROP TABLE IF EXISTS `tb_vip`;
CREATE TABLE `tb_vip` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vip_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `price_low` bigint DEFAULT '0' COMMENT '限时价格',
  `price_high` bigint DEFAULT '0' COMMENT '原价格',
  `commission` bigint DEFAULT '0' COMMENT '佣金百分比，例：百分之五十，数据为50',
  `bonus_high` bigint DEFAULT '0' COMMENT '奖金区间小',
  `bonus_low` bigint DEFAULT '0' COMMENT '奖金区间大',
  `discount` bigint DEFAULT '0' COMMENT '整形折扣百分比 例：五折，数据为50',
  `times` bigint DEFAULT '0' COMMENT '旅游次数',
  `area_commission` bigint DEFAULT '0' COMMENT '区域分红百分比',
  `quota_num` bigint DEFAULT '0' COMMENT '限时名额数量',
  `begin_time` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` timestamp NULL DEFAULT NULL COMMENT '结束日期',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  `image` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'vip卡图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='会员表';




DROP TABLE IF EXISTS `tb_user_income`;
CREATE TABLE `tb_user_income` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(36) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户id',
  `recharge_money` bigint DEFAULT '0' COMMENT '充值金额，单位：分',
  `extension_money` bigint DEFAULT '0' COMMENT '推广收入，单位：分',
  `project_money` bigint DEFAULT '0' COMMENT '医美项目支出，单位：分',
  `course_money` bigint DEFAULT '0' COMMENT '课程支出，单位：分',
  `commission_money` bigint DEFAULT '0' COMMENT '佣金收入，单位：分',
  `other_money` bigint DEFAULT '0' COMMENT '其他收入',
  `get_out_money` bigint DEFAULT '0' COMMENT '提现金额',
  `other_out_money` bigint DEFAULT '0' COMMENT '其它支出',
  `seq_unique` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '防止数据重复添加唯一索引',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_seq_unique` (`seq_unique`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户收入、支出日表';



DROP TABLE IF EXISTS `tb_user_income_detail`;
CREATE TABLE `tb_user_income_detail` (
     `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `user_id` bigint DEFAULT NULL,
     `income_type` tinyint DEFAULT '1' COMMENT '收入支出类型1:提现 2充值 推广奖励  4项目佣金 5购买课程 6购买项目  ',
     `income_content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
     `pay_money` bigint DEFAULT NULL COMMENT '金额',
     `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
     `pay_type` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付类型',
     `excess` bigint NOT NULL DEFAULT '0' COMMENT '操作后余额',
     `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
     `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
     `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
     `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
     PRIMARY KEY (`id`) USING BTREE,
     KEY `index_user_income_detail` (`user_id`) USING BTREE,
     KEY `idx_user_insert` (`user_id`,`create_time`,`id`) USING BTREE,
     KEY `idx_user_type` (`user_id`,`income_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户收入、支出明细';

ALTER TABLE `tb_user_income_detail`
    MODIFY COLUMN `income_type` tinyint(0) NULL DEFAULT 1 COMMENT '收入支出类型1:提现 2充值 推广奖励  4项目佣金 5购买课程 6项目定金' AFTER `user_id`;

DROP TABLE IF EXISTS `tb_talent_customer`;
CREATE TABLE `tb_talent_customer` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `talent_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '达人id',
  `order_money` bigint DEFAULT '0' COMMENT '成交总额',
  `order_num` bigint DEFAULT '0' COMMENT '成交订单数量',
  `commission_money` bigint DEFAULT '0' COMMENT '总提成',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_talent_id` (`talent_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='达人客户表';



DROP TABLE IF EXISTS `tb_talent_hospital`;
CREATE TABLE `tb_talent_hospital` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hospital_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构医院id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  `talent_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '达人id',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_talent_id` (`talent_id`,`hospital_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='达人机构(医院)表';



DROP TABLE IF EXISTS `tb_app_version`;
CREATE TABLE `tb_app_version` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `show_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '展示版本号',
  `sys_version` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '展示版本号',
  `content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新内容简介',
  `download_url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '下载地址',
  `update_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否强制更新 0-不用,1-强制更新',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='app升级版本号';




DROP TABLE IF EXISTS `tb_sms_config`;
CREATE TABLE `tb_sms_config` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
	 `send_api` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信接口地址',
  `login_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口账号 (必填)（企业登录名）',
	 `send_content` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信内容模板',
	 `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码 (必填)（企业账号对应密码）',
 	 `sign_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '签名（可为空）',
	 `fee_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计费套餐类型 (必填)2 行业套餐 3 为政务套餐',
	 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短信配置表';

DROP TABLE IF EXISTS `tb_verified_config`;
CREATE TABLE `tb_verified_config` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `send_api` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口地址',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'appId',
  `app_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'appKey',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='实名认证配置表';


DROP TABLE IF EXISTS `tb_bank`;
		CREATE TABLE `tb_bank` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `bank_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '银行名称',
  `icon` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图标',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='银行卡表';
