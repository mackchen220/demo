DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
`id` varchar(36)  NOT NULL,
`user_name` varchar(64) NOT NULL COMMENT '用户名',
`nike_name` varchar(64) DEFAULT NULL COMMENT '昵称',
`head_image` varchar(255) DEFAULT NULL COMMENT '头像',
`vip_id` bigint DEFAULT NULL COMMENT 'VIP等级id',
`address_id` bigint DEFAULT NULL COMMENT '收获地址表id',
`bank_id` bigint DEFAULT NULL COMMENT '银行卡id',
`sign` varchar(255) DEFAULT NULL COMMENT '个性签名',
`money` bigint NOT NULL DEFAULT '0' COMMENT '可提现余额度',
`del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
`is_disable` int NOT NULL DEFAULT '1' COMMENT '禁用 1不禁用 0禁用',
`password` varchar(100) NOT NULL COMMENT '密码',
`last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
`login_time` timestamp(0) NULL DEFAULT NULL COMMENT '此次登录时间',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`last_login_ip` varchar(30) NULL DEFAULT NULL COMMENT '最后一次登陆IP',
`login_ip` varchar(30) NULL DEFAULT NULL COMMENT '当前登录ip',
`wechat` varchar(64) NULL DEFAULT NULL COMMENT '微信号码',
`user_type` tinyint(4) NULL DEFAULT NULL COMMENT '1-用户，2-达人，3-机构，4-平台',
`agency_id` bigint(20) NOT NULL DEFAULT -1 COMMENT '代理id',
`login_times` int(11) NOT NULL DEFAULT 0 COMMENT '登录次数',
`gender` int(2) NULL DEFAULT 1 COMMENT '1-男,0-女',
`birthday` date NULL DEFAULT NULL COMMENT '出生日期',
`phone` varchar(24) NULL DEFAULT NULL COMMENT '电话.',
`upd_pwd_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最后修改密码时间',
`register_ip` varchar(30) NULL DEFAULT NULL COMMENT '用户注册域名',
`disable_remark` varchar(255) NULL DEFAULT NULL COMMENT '禁用说明',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`invite_code` varchar(16)  NOT NULL COMMENT '邀请码',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB ;

ALTER TABLE `tb_user`
ADD COLUMN `is_talent` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否达人，0不是 1是' AFTER `invite_code`;



DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `id` varchar(36) NOT NULL,
  `address` varchar(255) DEFAULT NULL COMMENT '收获详细地址',
  `is_disable` int NOT NULL DEFAULT '1' COMMENT '禁用 1不禁用 0禁用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  `user_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  `phone` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收货手机号',
  `city` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '城市',
  `default_flag` int NOT NULL DEFAULT '0' COMMENT '是否默认地址 0非默认 1默认',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户地址表';



DROP TABLE IF EXISTS `tb_user_agency`;
CREATE TABLE `tb_user_agency`  (
`id` varchar(36)  NOT NULL,
`user_id`varchar(36) NOT NULL COMMENT '用户id',
`p_user_id` bigint(20) NOT NULL,
`num` int(11) NOT NULL DEFAULT 0 COMMENT '子代理数量',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '用户代理表';

INSERT INTO `tb_user_agency`(`id`, `user_id`, `p_user_id`, `num`, `create_time`, `update_time`, `create_by`, `update_by`, `del_flag`)
VALUES ('806493672093777921', '806488232119238656', -1, 3, '2021-02-03 11:59:19', '2021-02-03 12:05:49', NULL, NULL, 0);



DROP TABLE IF EXISTS `tb_user_bank`;
CREATE TABLE `tb_user_bank`  (
`id` bigint(20) NOT NULL COMMENT '主键id',
`user_id` bigint(20) NOT NULL COMMENT '用户id',
`bank` varchar(64)  NULL DEFAULT NULL COMMENT '开户银行',
`sub_bank_name` varchar(64)  NULL DEFAULT NULL COMMENT '开户支行名称',
`real_name` varchar(64)  NULL DEFAULT NULL COMMENT '开户姓名',
`card_number` varchar(32)  NULL DEFAULT NULL COMMENT '银行卡号',
`remark` varchar(128)  NULL DEFAULT NULL COMMENT '备注',
`is_check` tinyint(4) NULL DEFAULT 1 COMMENT '是否审核 null-未审核，1-通过，0-不通过',
`is_blacklist` tinyint(4) NULL DEFAULT 0 COMMENT '是否是黑名单 0-否，1-是',
`is_default` tinyint(1) NULL DEFAULT 0,
`province` varchar(100)  NULL DEFAULT NULL COMMENT '省份',
`city` varchar(100)  NULL DEFAULT NULL COMMENT '城市',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
`icon` varchar(128)  NULL DEFAULT NULL COMMENT '图标',

PRIMARY KEY (`id`) USING BTREE,
INDEX `idx_card_number`(`card_number`) USING BTREE
) ENGINE = InnoDB COMMENT = '用户银行卡管理' ;




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
CREATE TABLE `tb_community`  (
`id` varchar(32)  NOT NULL,
`user_id` varchar(32)  NOT NULL,
`title` varchar(32) NULL DEFAULT NULL COMMENT '标题',
`content` varchar(512)  NULL DEFAULT NULL COMMENT '内容',
`url` varchar(128)  NULL DEFAULT NULL COMMENT '链接',
`image_url` varchar(128)  NULL DEFAULT NULL COMMENT '图片或者视频地址',
`city` varchar(64)  NULL DEFAULT NULL COMMENT '图片地址',
`type` tinyint(2) NULL DEFAULT 1 COMMENT '朋友圈类型:1 照片 2视频 3课程  4其他  ',
`user_type` tinyint(2)  DEFAULT 1 COMMENT '1-用户，2-达人，3-机构，4-平台',
`star_num` bigint(32)   DEFAULT 0 COMMENT '收藏数量',
`good_num` bigint(32)   DEFAULT 0 COMMENT '点赞数量',
`forward_num` bigint(32)   DEFAULT 0 COMMENT '转发数量',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '社区朋友圈表';


DROP TABLE IF EXISTS `tb_user_focus`;
CREATE TABLE `tb_user_focus`  (
`id` varchar(32)  NOT NULL,
`user_id` varchar(32)  NOT NULL,
`focus_user_id` varchar(32)  NOT NULL COMMENT '关注人',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '关注表';




DROP TABLE IF EXISTS `tb_course`;
CREATE TABLE `tb_course`  (
`id` varchar(32)  NOT NULL,
`title` varchar(32)  NOT NULL,
`url` varchar(128)  NOT NULL COMMENT '课程地址',
`image` varchar(128)  NOT NULL COMMENT '封面图片',
`price` bigint(32)   DEFAULT 0 COMMENT '价格',
`type` tinyint(1) NOT NULL DEFAULT '2' COMMENT '类型1 -收费,2 免费， 3限时免费',
`watch_num` bigint(32)   DEFAULT 0 COMMENT '观看数量',
`good_num` bigint(32)   DEFAULT 0 COMMENT '点赞数量',
`state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '标识0-完结,1-正在更新',
`sort` bigint(32)  NULL DEFAULT NULL COMMENT '排序字段',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '课程表';




DROP TABLE IF EXISTS `tb_user_course`;
CREATE TABLE `tb_user_course`  (
`id` varchar(32)  NOT NULL,
`user_id` varchar(32)  NOT NULL,
`course_id` varchar(32)  NOT NULL COMMENT '课程id',
`begin_time` timestamp(0) NOT NULL  COMMENT '开始时间',
`end_time` timestamp(0) NOT NULL  COMMENT '结束时间',
`price` bigint(32)   DEFAULT 0 COMMENT 'f价格',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '用户课程表';

ALTER TABLE `tb_user`
CHANGE COLUMN `nike_name` `nick_name` varchar(64) NULL DEFAULT NULL COMMENT '昵称' AFTER `user_name`;


DROP TABLE IF EXISTS `tb_talent_info`;
CREATE TABLE `tb_talent_info`  (
`id` varchar(32)  NOT NULL,
`user_id` varchar(32)  NOT NULL,
`effect` decimal(3, 2)  DEFAULT 0 COMMENT '效果评分',
`attitude` decimal(3, 2)  DEFAULT 0 COMMENT '态度评分',
`price` decimal(3, 2)  DEFAULT 0 COMMENT '价格评分',
`average_score` decimal(3, 2)  DEFAULT 0 COMMENT '综合评分',
`deposit` bigint(32)   DEFAULT 0 COMMENT '保证金',
`name` varchar(32)  ,
`id_card` varchar(32)  NULL DEFAULT NULL  COMMENT '身份证号码',
`city` varchar(32) NULL DEFAULT NULL COMMENT '城市',
`time` varchar(16)  DEFAULT 0  COMMENT '从业年限',
`id_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否身份验证0-已验证,1-未验证',
`authenticated` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否完成验证0-已完成,1-未完成',
`contract_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否签署达人协议 0已签署,1-未签署',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(32)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '达人信息表';




ALTER TABLE `tb_talent_info`
ADD COLUMN `num` bigint(32) NULL DEFAULT 0 COMMENT '签约机构数量' AFTER `del_flag`;


ALTER TABLE `tb_community`
MODIFY COLUMN `city` varchar(64)  NULL DEFAULT NULL COMMENT '定位城市';

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
) ENGINE=InnoDB DEFAULT COMMENT='达人机构案例表';


DROP TABLE IF EXISTS `tb_project`;
CREATE TABLE `tb_project`  (
`id` varchar(32)  NOT NULL,
`name` varchar(32)  NOT NULL COMMENT '项目名称',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '项目（项目大类）表';

DROP TABLE IF EXISTS `tb_project_info`;
CREATE TABLE `tb_project_info`  (
`id` varchar(32)  NOT NULL,
`project_id` varchar(32)  NOT NULL COMMENT '项目id',
`project_name` varchar(32)  NOT NULL COMMENT '项目名称',
`materials_name` varchar(32)  NOT NULL COMMENT '材料名称',
`other_project` varchar(32)  NOT NULL COMMENT '杂项',
`type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '子项目类型 ，1具体小项目 2 假体材料 3 杂项',
`price_low` BIGINT(32)  DEFAULT 0 COMMENT '最低价格',
`price_high` BIGINT(32)  DEFAULT 0 COMMENT '最高价格',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '子类项目，材料，杂项详细表';


###关联表每个医院结构的材料，项目，杂项等价格都不一样
DROP TABLE IF EXISTS `tb_hospital_project`;
CREATE TABLE `tb_hospital_project`(
`id` varchar(32)  NOT NULL,
`project_id` varchar(32)  NOT NULL  COMMENT '项目id',
`price_low` BIGINT(32)  DEFAULT 0 COMMENT '最低价格',
`price_high` BIGINT(32)  DEFAULT 0 COMMENT '最高价格',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '机构材料项目表';

DROP TABLE IF EXISTS `tb_hospital`;
CREATE TABLE `tb_hospital` (
`id` varchar(32) NOT NULL,
`name` varchar(32) NOT NULL COMMENT '医院名称',
`content` varchar(128) DEFAULT NULL COMMENT '医院简介',
`image_url` varchar(512) DEFAULT NULL COMMENT '医院图标图片',
`business_license` varchar(512) DEFAULT NULL COMMENT '医院营业执照',
`licence` varchar(512) DEFAULT NULL COMMENT '医院执业许可证',
`video_url` varchar(512) DEFAULT NULL COMMENT '视频地址',
`contract_url` varchar(128) DEFAULT NULL COMMENT '签署的协议的超链接',
`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
`create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='机构(医院)表';



DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL COMMENT '账号',
  `operation_type` int NOT NULL COMMENT '1-医美项目,2课程 ,3提现',
  `amount` bigint DEFAULT NULL COMMENT '用户请求金额，单位：分',
  `inside_card_num` varchar(32) DEFAULT NULL COMMENT '充值卡号',
  `checker` varchar(255) DEFAULT NULL COMMENT '提现审核人',
  `check_dt` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(121) DEFAULT NULL COMMENT '备注',
  `status` int DEFAULT '1' COMMENT '订单状态1：未审核 2：成功 3：失败',
  `pay_money` bigint DEFAULT '0' COMMENT '系统根据用户请求金额生成实际支付金额，单位：分',
  `outside_card_num` varchar(32) DEFAULT NULL COMMENT '提现银行卡号',
  `opt_status` tinyint NOT NULL DEFAULT '0' COMMENT '操作状态0-未确认 1-已确认 2-成功 3-已取消 4-锁定 5-恢复 6-拒绝 ',
  `sys_ins_name` varchar(32) DEFAULT NULL COMMENT '系统收款银行-收款人',
  `content` varchar(128) DEFAULT NULL COMMENT '订单简介',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` int NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  `num` int DEFAULT '1' COMMENT '商品数量',
  `hospital_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构id',
  `talent_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '达人id',
  `course_id` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '课程id',
  `project_id` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '项目id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='订单表';

DROP TABLE IF EXISTS `tb_party`;
CREATE TABLE tb_party  (
  `id` varchar(32) NOT NULL,
  `title` varchar(128) NOT NULL COMMENT '派对标题',
  `image` varchar(255) NULL COMMENT '封面图',
  `profiles` varchar(255) NULL COMMENT '简介',
  `watch` varchar(32) NULL DEFAULT 0 COMMENT '观看数量',
  `star` varchar(32) NULL DEFAULT 0 COMMENT '点赞数量',
  `price` varchar(32) NULL DEFAULT 0 COMMENT '价格',
  `num` int(32) NULL DEFAULT 0 COMMENT '名额',
  `user_id` varchar(32) NULL COMMENT '发起人id、',
  `qr_code` varchar(255) NULL COMMENT '微信群二维码',
  `start_time` timestamp NOT NULL COMMENT '开始时间',
  `end_time` timestamp NULL COMMENT '结束时间',
  `address` varchar(128) NULL COMMENT '活动地址',
  `detail_imgae` varchar(255) NULL COMMENT '活动详情图片',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
  PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='社群派对表';

###案例类型自定义的
ALTER TABLE `tb_case`
MODIFY COLUMN `type` varchar(32) NULL DEFAULT NULL COMMENT '项目分类' AFTER `content`,
MODIFY COLUMN `source` varchar(64)  NULL DEFAULT NULL COMMENT '案例来源 ' AFTER `time`;


ALTER TABLE `tb_talent_info` ADD COLUMN `order_num` bigint(32) NULL DEFAULT 0 COMMENT '接单量';
ALTER TABLE `tb_talent_info` ADD COLUMN `advisory_num` bigint(32) NULL DEFAULT 0 COMMENT '咨询量';
ALTER TABLE `tb_talent_info` ADD COLUMN `like_num` bigint(32) NULL DEFAULT 0 COMMENT '点赞量';

ALTER TABLE `tb_user` ADD COLUMN `province` varchar(50) DEFAULT NULL COMMENT '省份';
ALTER TABLE `tb_user` ADD COLUMN `city` varchar(50) DEFAULT NULL COMMENT '城市';
ALTER TABLE `tb_user`
DROP COLUMN `address_id`;

DROP TABLE IF EXISTS `tb_top_search`;
CREATE TABLE `tb_top_search`(
`id` varchar(32)  NOT NULL,
`top_search` varchar(50)  NOT NULL  COMMENT '热搜关键词',
`create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新日期',
`create_by` varchar(32)  NULL DEFAULT NULL COMMENT '创建人',
`update_by` varchar(50)  NULL DEFAULT NULL COMMENT '更新人',
`del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识0-正常,1-已删除',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB COMMENT = '热搜关键字表';

ALTER TABLE `tb_course`
ADD COLUMN `course_type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '课程类型 1-文章 2-视频';


ALTER TABLE `tb_course`
ADD COLUMN `city` varchar(32) DEFAULT NULL COMMENT '城市';

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

ALTER TABLE `tb_course`
ADD COLUMN `star_num` bigint(32) DEFAULT 0 COMMENT '收藏数量';
ALTER TABLE `tb_course`
ADD COLUMN `forward_num` bigint(32) DEFAULT 0 COMMENT '转发数量';

ALTER TABLE `tb_community`
ADD COLUMN `watch_num` bigint(32)   DEFAULT 0 COMMENT '观看数量';

ALTER TABLE `tb_party`
ADD COLUMN  `watch_num` varchar(32) NULL DEFAULT 0 COMMENT '观看数量',
ADD COLUMN `good_num` varchar(32) NULL DEFAULT 0 COMMENT '点赞数量',
ADD COLUMN `star_num` bigint(32) DEFAULT 0 COMMENT '收藏数量',
ADD COLUMN `forward_num` bigint(32) DEFAULT 0 COMMENT '转发数量',
ADD COLUMN `city` varchar(32) DEFAULT NULL COMMENT '城市';

ALTER TABLE `tb_party`
DROP COLUMN `watch`,
DROP COLUMN `star`;

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户点赞收藏朋友圈表';
