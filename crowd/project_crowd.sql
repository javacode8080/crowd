/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.5.27 : Database - project_crowd
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`project_crowd` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `project_crowd`;

/*Table structure for table `inner_admin_role` */

DROP TABLE IF EXISTS `inner_admin_role`;

CREATE TABLE `inner_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `inner_admin_role` */

insert  into `inner_admin_role`(`id`,`admin_id`,`role_id`) values (36,969,237),(37,969,239),(38,970,238),(39,970,240);

/*Table structure for table `inner_role_auth` */

DROP TABLE IF EXISTS `inner_role_auth`;

CREATE TABLE `inner_role_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `auth_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `inner_role_auth` */

insert  into `inner_role_auth`(`id`,`role_id`,`auth_id`) values (24,239,1),(25,239,8),(28,240,1),(29,240,3),(30,240,4),(31,240,5);

/*Table structure for table `t_address` */

DROP TABLE IF EXISTS `t_address`;

CREATE TABLE `t_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `receive_name` char(100) DEFAULT NULL COMMENT '收件人',
  `phone_num` char(100) DEFAULT NULL COMMENT '手机号',
  `address` char(200) DEFAULT NULL COMMENT '收货地址',
  `member_id` int(11) DEFAULT NULL COMMENT '用户 id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_address` */

insert  into `t_address`(`id`,`receive_name`,`phone_num`,`address`,`member_id`) values (1,'孙建','17806275104','中国石油大学(华东)',4),(2,'张三','14789562563','中国石油大学(北京)',4);

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_acct` varchar(255) NOT NULL,
  `user_pswd` char(100) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `create_time` char(19) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_acct` (`login_acct`)
) ENGINE=InnoDB AUTO_INCREMENT=974 DEFAULT CHARSET=utf8;

/*Data for the table `t_admin` */

insert  into `t_admin`(`id`,`login_acct`,`user_pswd`,`user_name`,`email`,`create_time`) values (969,'adminOperator','$2a$10$ucdma3RFSGVgNc30nGxu9Oku66A4zHNbaxTgxRq4ucpbw7ZmeK.8m','AAOO','AAOO@qq.com',NULL),(970,'roleOperator','$2a$10$ucdma3RFSGVgNc30nGxu9Oku66A4zHNbaxTgxRq4ucpbw7ZmeK.8m','RROO','RROO@qq.com',NULL),(971,'admin01','$2a$10$ucdma3RFSGVgNc30nGxu9Oku66A4zHNbaxTgxRq4ucpbw7ZmeK.8m','admin01','admin01@qq.com',NULL),(972,'admin02','$2a$10$ucdma3RFSGVgNc30nGxu9Oku66A4zHNbaxTgxRq4ucpbw7ZmeK.8m','admin02','admin02@qq.com',NULL),(973,'admin03','$2a$10$ucdma3RFSGVgNc30nGxu9Oku66A4zHNbaxTgxRq4ucpbw7ZmeK.8m','admin03','admin03@qq.com',NULL);

/*Table structure for table `t_auth` */

DROP TABLE IF EXISTS `t_auth`;

CREATE TABLE `t_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `t_auth` */

insert  into `t_auth`(`id`,`name`,`title`,`category_id`) values (1,'','用户模块',NULL),(2,'user:delete','删除',1),(3,'user:get','查询',1),(4,'','角色模块',NULL),(5,'role:delete','删除',4),(6,'role:get','查询',4),(7,'role:add','新增',4),(8,'user:save','保存',1);

/*Table structure for table `t_member` */

DROP TABLE IF EXISTS `t_member`;

CREATE TABLE `t_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginacct` varchar(255) NOT NULL,
  `userpswd` char(200) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `authstatus` int(4) DEFAULT NULL COMMENT '实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证',
  `usertype` int(4) DEFAULT NULL COMMENT ' 0 - 个人， 1 - 企业',
  `realname` varchar(255) DEFAULT NULL,
  `cardnum` varchar(255) DEFAULT NULL,
  `accttype` int(4) DEFAULT NULL COMMENT '0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府',
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginacct` (`loginacct`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `t_member` */

insert  into `t_member`(`id`,`loginacct`,`userpswd`,`username`,`email`,`authstatus`,`usertype`,`realname`,`cardnum`,`accttype`) values (1,'jack','$2a$10$q2LwqlKbcijeqmkIo6lBuesAurxuUNi8dprnCZT6z/sgUFFQVKQ/u','杰克','jack@qq.com',1,1,'杰克','123123',2),(2,'sunjian','$2a$10$OaZ1Lq9YQaGq4Zec4Q3ateiRpSAgv0/gZMTqiB/L1ND577Hb/3tsy','S19050001','sun_jian2019@163.com',NULL,NULL,NULL,NULL,NULL),(4,'zhangsan','$2a$10$sIClv9rtDBgVfFChw3760ujOKYxtJM5dV4HXze0EtdNYSIPNM0ZPq','zhangsan','1505010319@s.upc.edu.cn',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `t_member_confirm_info` */

DROP TABLE IF EXISTS `t_member_confirm_info`;

CREATE TABLE `t_member_confirm_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL COMMENT '会员 id',
  `paynum` varchar(200) DEFAULT NULL COMMENT '易付宝企业账号',
  `cardnum` varchar(200) DEFAULT NULL COMMENT '法人身份证号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_member_confirm_info` */

insert  into `t_member_confirm_info`(`id`,`memberid`,`paynum`,`cardnum`) values (1,4,'18801282948','123456789'),(2,4,'2342134','234234234');

/*Table structure for table `t_member_launch_info` */

DROP TABLE IF EXISTS `t_member_launch_info`;

CREATE TABLE `t_member_launch_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `memberid` int(11) DEFAULT NULL COMMENT '会员 id',
  `description_simple` varchar(255) DEFAULT NULL COMMENT '简单介绍',
  `description_detail` varchar(255) DEFAULT NULL COMMENT '详细介绍',
  `phone_num` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `service_num` varchar(255) DEFAULT NULL COMMENT '客服电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_member_launch_info` */

insert  into `t_member_launch_info`(`id`,`memberid`,`description_simple`,`description_detail`,`phone_num`,`service_num`) values (1,4,'i am mao','我是猫哥','123456','654321'),(2,4,'i am mao','我是猫哥','123456','654321');

/*Table structure for table `t_menu` */

DROP TABLE IF EXISTS `t_menu`;

CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `t_menu` */

insert  into `t_menu`(`id`,`pid`,`name`,`url`,`icon`) values (1,NULL,'系统权限菜单',NULL,'glyphicon glyphicon-th-list'),(2,1,'控制面板','main.htm','glyphicon glyphicon-dashboard'),(3,1,'权限管理',NULL,'glyphicon glyphicon glyphicon-tasks'),(4,3,' 用 户 维 护 ','user/index.htm','glyphicon glyphicon-user'),(5,3,'角 色 维 护','role/index.htm','glyphicon glyphicon-king'),(6,3,' 菜 单 维 护 ','permission/index.htm','glyphicon glyphicon-lock'),(7,1,'业务审核','','glyphicon glyphicon-ok'),(8,7,' 实 名 认 证 审 核 ','auth_cert/index.htm','glyphicon glyphicon-check'),(9,7,' 广 告 审 核 ','auth_adv/index.htm','glyphicon glyphicon-check'),(10,7,' 项 目 审 核 ','auth_project/index.htm','glyphicon glyphicon-check'),(11,1,'业务管理','','glyphicon glyphicon-th-large'),(12,11,' 资 质 维 护 ','cert/index.htm','glyphicon glyphicon-picture'),(13,11,' 分 类 管 理 ','certtype/index.htm','glyphicon glyphicon-equalizer'),(14,11,' 流 程 管 理 ','process/index.htm','glyphicon glyphicon-random'),(15,11,' 广 告 管 理 ','advert/index.htm','glyphicon glyphicon-hdd'),(16,11,' 消 息 模 板 ','message/index.htm','glyphicon glyphicon-comment'),(17,11,' 项 目 分 类 ','projectType/index.htm','glyphicon glyphicon-list'),(18,11,' 项 目 标 签 ','tag/index.htm','glyphicon glyphicon-tags'),(19,1,'参数管理','param/index.htm','glyphicon glyphicon-list-alt');

/*Table structure for table `t_order` */

DROP TABLE IF EXISTS `t_order`;

CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_num` char(100) DEFAULT NULL COMMENT '订单号',
  `pay_order_num` char(100) DEFAULT NULL COMMENT '支付宝流水号',
  `order_amount` double(10,5) DEFAULT NULL COMMENT '订单金额',
  `invoice` int(11) DEFAULT NULL COMMENT '是否开发票（0 不开， 1 开） ',
  `invoice_title` char(100) DEFAULT NULL COMMENT '发票抬头',
  `order_remark` char(100) DEFAULT NULL COMMENT '订单备注',
  `address_id` char(100) DEFAULT NULL COMMENT '收货地址 id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_order` */

/*Table structure for table `t_order_project` */

DROP TABLE IF EXISTS `t_order_project`;

CREATE TABLE `t_order_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_name` char(200) DEFAULT NULL COMMENT '项目名称',
  `launch_name` char(100) DEFAULT NULL COMMENT '发起人',
  `return_content` char(200) DEFAULT NULL COMMENT '回报内容',
  `return_count` int(11) DEFAULT NULL COMMENT '回报数量',
  `support_price` int(11) DEFAULT NULL COMMENT '支持单价',
  `freight` int(11) DEFAULT NULL COMMENT '配送费用',
  `order_id` int(11) DEFAULT NULL COMMENT '订单表的主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_order_project` */

/*Table structure for table `t_project` */

DROP TABLE IF EXISTS `t_project`;

CREATE TABLE `t_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `project_description` varchar(255) DEFAULT NULL COMMENT '项目描述',
  `money` bigint(11) DEFAULT NULL COMMENT '筹集金额',
  `day` int(11) DEFAULT NULL COMMENT '筹集天数',
  `status` int(4) DEFAULT NULL COMMENT '0-即将开始， 1-众筹中， 2-众筹成功， 3-众筹失败',
  `deploydate` varchar(10) DEFAULT NULL COMMENT '项目发起时间',
  `supportmoney` bigint(11) DEFAULT NULL COMMENT '已筹集到的金额',
  `supporter` int(11) DEFAULT NULL COMMENT '支持人数',
  `completion` int(3) DEFAULT NULL COMMENT '百分比完成度',
  `memberid` int(11) DEFAULT NULL COMMENT '发起人的会员 id',
  `createdate` varchar(19) DEFAULT NULL COMMENT '项目创建时间',
  `follower` int(11) DEFAULT NULL COMMENT '关注人数',
  `header_picture_path` varchar(255) DEFAULT NULL COMMENT '头图路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `t_project` */

insert  into `t_project`(`id`,`project_name`,`project_description`,`money`,`day`,`status`,`deploydate`,`supportmoney`,`supporter`,`completion`,`memberid`,`createdate`,`follower`,`header_picture_path`) values (5,'brotherMao','就是帅！',100000,30,1,'2021-08-14',50000,25815,NULL,4,'2021-08-15',88,'\\JAVA\\codeLoding\\aaa\\static\\img\\header\\20210815171646_426249088.png'),(6,'brotherMao','就是帅！',100000,30,0,'2021-08-01',20000,6352,NULL,4,'2021-08-16',88,'/JAVA/codeLoding/aaa/static/img/header/20210816105150_2146958433.png');

/*Table structure for table `t_project_item_pic` */

DROP TABLE IF EXISTS `t_project_item_pic`;

CREATE TABLE `t_project_item_pic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) DEFAULT NULL,
  `item_pic_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `t_project_item_pic` */

insert  into `t_project_item_pic`(`id`,`projectid`,`item_pic_path`) values (1,5,'\\JAVA\\codeLoding\\aaa\\static\\img\\detail\\20210815171646_1492236310.png'),(2,6,'/JAVA/codeLoding/aaa/static/img/detail/20210816105150_1079233868.jpg'),(3,6,'/JAVA/codeLoding/aaa/static/img/detail/20210816105150_1346044949.png'),(4,6,'/JAVA/codeLoding/aaa/static/img/detail/20210816105150_559568242.png'),(5,6,'/JAVA/codeLoding/aaa/static/img/detail/20210816105150_1723290321.png');

/*Table structure for table `t_project_tag` */

DROP TABLE IF EXISTS `t_project_tag`;

CREATE TABLE `t_project_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) DEFAULT NULL,
  `tagid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `t_project_tag` */

insert  into `t_project_tag`(`id`,`projectid`,`tagid`) values (1,5,4),(2,5,7),(3,5,9),(4,6,4),(5,6,7),(6,6,9);

/*Table structure for table `t_project_type` */

DROP TABLE IF EXISTS `t_project_type`;

CREATE TABLE `t_project_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) DEFAULT NULL,
  `typeid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `t_project_type` */

insert  into `t_project_type`(`id`,`projectid`,`typeid`) values (9,5,2),(10,5,4),(11,6,1),(12,6,2),(13,6,3),(14,6,4);

/*Table structure for table `t_return` */

DROP TABLE IF EXISTS `t_return`;

CREATE TABLE `t_return` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectid` int(11) DEFAULT NULL,
  `type` int(4) DEFAULT NULL COMMENT '0 - 实物回报， 1 虚拟物品回报',
  `supportmoney` int(11) DEFAULT NULL COMMENT '支持金额',
  `content` varchar(255) DEFAULT NULL COMMENT '回报内容',
  `count` int(11) DEFAULT NULL COMMENT '回报产品限额， “0” 为不限回报数量',
  `signalpurchase` int(11) DEFAULT NULL COMMENT '是否设置单笔限购',
  `purchase` int(11) DEFAULT NULL COMMENT '具体限购数量',
  `freight` int(11) DEFAULT NULL COMMENT '运费， “0” 为包邮',
  `invoice` int(4) DEFAULT NULL COMMENT '0 - 不开发票， 1 - 开发票',
  `returndate` int(11) DEFAULT NULL COMMENT '项目结束后多少天向支持者发送回报',
  `describ_pic_path` varchar(255) DEFAULT NULL COMMENT '说明图片路径',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_return` */

insert  into `t_return`(`id`,`projectid`,`type`,`supportmoney`,`content`,`count`,`signalpurchase`,`purchase`,`freight`,`invoice`,`returndate`,`describ_pic_path`) values (1,5,NULL,10,'以身相许',5,0,8,0,NULL,15,'\\JAVA\\codeLoding\\aaa\\static\\img\\return\\20210815171652_620679707.png'),(2,6,0,10,'以身相许',5,1,8,0,NULL,15,'/JAVA/codeLoding/aaa/static/img/return/20210816105156_2029068533.jpg');

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`id`,`name`) values (237,'经理'),(238,'部长'),(239,'经理操作者'),(240,'部长操作者');

/*Table structure for table `t_tag` */

DROP TABLE IF EXISTS `t_tag`;

CREATE TABLE `t_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_tag` */

/*Table structure for table `t_type` */

DROP TABLE IF EXISTS `t_type`;

CREATE TABLE `t_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '分类介绍',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `t_type` */

insert  into `t_type`(`id`,`name`,`remark`) values (1,'科技','开启智慧未来'),(2,'设计','创建改变未来'),(3,'农业','网罗天下肥美'),(4,'公益','汇聚点点爱心');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
