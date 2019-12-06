/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/12/6 15:48:12                           */
/*==============================================================*/

drop table if exists sys_permission_role;

drop table if exists sys_user_role;

drop table if exists sys_permission;

drop table if exists sys_role;

drop table if exists sys_user;


/*==============================================================*/
/* Table: sys_permission                                        */
/*==============================================================*/
create table sys_permission
(
   id                   int(11) not null auto_increment comment 'id',
   url                  varchar(64) comment '权限路径',
   path                 varchar(64) comment '路由路径',
   component            varchar(64) comment '模块页面路径',
   permission_name      varchar(64) comment '名称',
   icon_cls             varchar(64) comment '图标',
   keep_alive           tinyint(1) default 0 comment '页面缓存',
   require_auth         tinyint(1) default 1 comment '访问权限',
   parent_id            int(11) comment '父级菜单id',
   enabled              tinyint(1) default 1 comment '是否启用',
   primary key (id)
);

alter table sys_permission comment '权限表';

/*==============================================================*/
/* Table: sys_permission_role                                   */
/*==============================================================*/
create table sys_permission_role
(
   id                   int(11) not null comment 'id',
   role_id              int(11) comment '角色id',
   permission_id        int(11) comment '权限id',
   primary key (id)
);

alter table sys_permission_role comment '权限角色关系表';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   int(11) not null auto_increment comment 'id',
   role_name            varchar(32) comment '角色代码',
   role_name_zh         varchar(32) comment '角色名称',
   primary key (id)
);

alter table sys_role comment '角色表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   int(11) not null auto_increment comment 'id',
   user_name_zh         varchar(8) comment '姓名',
   phone                char(13) comment '手机',
   address              varchar(255) comment '地址',
   enabled              char(1) comment '是否启动',
   user_name            varchar(32) comment '账号',
   password             varchar(64) comment '密码',
   userface             varchar(255) comment '用户头像',
   remark               varchar(255) comment '备注',
   primary key (id)
);

alter table sys_user comment '用户表';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   int(11) not null auto_increment comment 'id',
   user_id              int(11) comment '用户id',
   role_id              int(11) comment '角色id',
   primary key (id)
);

alter table sys_user_role comment '用户角色关系表';

alter table sys_permission_role add constraint FK_Reference_3 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

alter table sys_permission_role add constraint FK_Reference_4 foreign key (permission_id)
      references sys_permission (id) on delete restrict on update restrict;

alter table sys_user_role add constraint FK_Reference_1 foreign key (user_id)
      references sys_user (id) on delete restrict on update restrict;

alter table sys_user_role add constraint FK_Reference_2 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

