DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `nike_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `head_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `vip_id` bigint DEFAULT NULL COMMENT 'VIP等级id',
  `address_id` bigint DEFAULT NULL COMMENT '收获地址表id',
  `bank_id` bigint DEFAULT NULL COMMENT '银行卡id',
  `sign` varchar(255) DEFAULT NULL COMMENT '个性签名',
  `money` bigint NOT NULL DEFAULT '0' COMMENT '可提现余额度',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  `is_disable` int NOT NULL DEFAULT '1' COMMENT '禁用 1不禁用 0禁用',
	 `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `login_time` timestamp(0) NULL DEFAULT NULL COMMENT '此次登录时间',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
  `last_login_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '最后一次登陆IP',
  `login_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '当前登录ip',
  `wechat` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信号码',
  `user_type` tinyint(4) NULL DEFAULT NULL COMMENT '1-用户，2-达人，3-机构，4-平台',
  `agency_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '代理id',
  `login_times` int(11) NOT NULL DEFAULT 0 COMMENT '登录次数',
  `gender` int(2) NULL DEFAULT 1 COMMENT '1-男,0-女',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话.',
  `upd_pwd_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后修改密码时间',
  `register_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户注册域名',
  `disable_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '禁用说明',
	`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
	`invite_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邀请码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `tb_user`
ADD COLUMN `is_talent` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否达人，0不是 1是' AFTER `invite_code`;



DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) DEFAULT NULL COMMENT '收获地址表id',
  `is_disable` int NOT NULL DEFAULT '1' COMMENT '禁用 1不禁用 0禁用',
	`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
	`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
	  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT '用户地址表';



DROP TABLE IF EXISTS `tb_user_agency`;
CREATE TABLE `tb_user_agency`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id`varchar(36) NOT NULL COMMENT '用户id',
  `p_user_id` bigint(20) NOT NULL,
  `num` int(11) NOT NULL DEFAULT 0 COMMENT '子代理数量',
		`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
	`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
	  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户代理表';

INSERT INTO `tb_user_agency`(`id`, `user_id`, `p_user_id`, `num`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
VALUES ('806493672093777921', '806488232119238656', -1, 3, '2021-02-03 11:59:19', '2021-02-03 12:05:49', NULL, NULL, 0);



DROP TABLE IF EXISTS `tb_user_bank`;
CREATE TABLE `tb_user_bank`  (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `bank` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户银行',
  `sub_bank_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户支行名称',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户姓名',
  `card_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `is_check` tinyint(4) NULL DEFAULT 1 COMMENT '是否审核 null-未审核，1-通过，0-不通过',
  `is_blacklist` tinyint(4) NULL DEFAULT 0 COMMENT '是否是黑名单 0-否，1-是',
  `is_default` tinyint(1) NULL DEFAULT 0,
  `province` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '城市',
			`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
	`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
	  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
		  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',

  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_card_number`(`card_number`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户银行卡管理' ;




DROP TABLE IF EXISTS `tb_turn_image`;
CREATE TABLE `tb_turn_image`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `turn_url` varchar(512) NULL DEFAULT NULL COMMENT '跳转地址',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `sort` bigint(32)  NULL DEFAULT NULL COMMENT '排序字段',
	`begin_time` timestamp(0) NOT NULL  COMMENT '开始时间',
	`end_time` timestamp(0) NOT NULL  COMMENT '结束时间',
	  `is_disable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否禁用：0，禁用，1，有效',
	`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
	`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
	 `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '首页轮播图配置';

		INSERT INTO `tb_turn_image`(`id`, `url`, `turn_url`, `title`, `sort`, `begin_time`, `end_time`, `is_disable`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
		VALUES ('244324345424', 'www.baidu.com', 'www.baidu.com', 'dddd', 1, '2021-02-03 13:36:54', '2021-04-15 13:36:57', 1, '2021-02-03 13:37:02', NULL, NULL, NULL, 0);

				INSERT INTO `tb_turn_image`(`id`, `url`, `turn_url`, `title`, `sort`, `begin_time`, `end_time`, `is_disable`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
		VALUES ('2443245654', 'www.baidu.com', 'www.baidu.com', 'ddddfdfdfd', 2, '2021-02-03 13:36:54', '2021-04-18 13:36:57', 1, '2021-02-03 13:37:02', NULL, NULL, NULL, 0);

				INSERT INTO `tb_turn_image`(`id`, `url`, `turn_url`, `title`, `sort`, `begin_time`, `end_time`, `is_disable`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
		VALUES ('24432456343454', 'www.baidu.com', 'www.baidu.com', 'ddddfdftttttttdfd', 2, '2021-02-02 13:36:54', '2021-02-01 13:36:57', 1, '2021-02-03 13:37:02', NULL, NULL, NULL, 0);



		DROP TABLE IF EXISTS `tb_community`;
CREATE TABLE `tb_community`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `title` varchar(32) NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接',
  `imageUrl` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片或者视频地址',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `type` tinyint(2) NULL DEFAULT 1 COMMENT '朋友圈类型:1 照片 2视频 3课程  4其他  ',
  `user_type` tinyint(2)  DEFAULT 1 COMMENT '1-用户，2-达人，3-机构，4-平台',
  `starNum` bigint(32)   DEFAULT 0 COMMENT '收藏数量',
  `goodNum` bigint(32)   DEFAULT 0 COMMENT '点赞数量',
  `forwardNum` bigint(32)   DEFAULT 0 COMMENT '转发数量',
	`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
	`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
	 `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '社区朋友圈表';


DROP TABLE IF EXISTS `tb_user_focus`;
CREATE TABLE `tb_user_focus`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `focus_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关注人',
	`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
	`create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
	 `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`) USING BTREE
	) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关注表';

