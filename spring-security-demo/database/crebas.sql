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
   url                  varchar(64) comment 'Ȩ��·��',
   path                 varchar(64) comment '·��·��',
   component            varchar(64) comment 'ģ��ҳ��·��',
   permission_name      varchar(64) comment '����',
   icon_cls             varchar(64) comment 'ͼ��',
   keep_alive           tinyint(1) default 0 comment 'ҳ�滺��',
   require_auth         tinyint(1) default 1 comment '����Ȩ��',
   parent_id            int(11) comment '�����˵�id',
   enabled              tinyint(1) default 1 comment '�Ƿ�����',
   primary key (id)
);

alter table sys_permission comment 'Ȩ�ޱ�';

/*==============================================================*/
/* Table: sys_permission_role                                   */
/*==============================================================*/
create table sys_permission_role
(
   id                   int(11) not null comment 'id',
   role_id              int(11) comment '��ɫid',
   permission_id        int(11) comment 'Ȩ��id',
   primary key (id)
);

alter table sys_permission_role comment 'Ȩ�޽�ɫ��ϵ��';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   int(11) not null auto_increment comment 'id',
   role_name            varchar(32) comment '��ɫ����',
   role_name_zh         varchar(32) comment '��ɫ����',
   primary key (id)
);

alter table sys_role comment '��ɫ��';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   int(11) not null auto_increment comment 'id',
   user_name_zh         varchar(8) comment '����',
   phone                char(13) comment '�ֻ�',
   address              varchar(255) comment '��ַ',
   enabled              char(1) comment '�Ƿ�����',
   user_name            varchar(32) comment '�˺�',
   password             varchar(64) comment '����',
   userface             varchar(255) comment '�û�ͷ��',
   remark               varchar(255) comment '��ע',
   primary key (id)
);

alter table sys_user comment '�û���';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   int(11) not null auto_increment comment 'id',
   user_id              int(11) comment '�û�id',
   role_id              int(11) comment '��ɫid',
   primary key (id)
);

alter table sys_user_role comment '�û���ɫ��ϵ��';

alter table sys_permission_role add constraint FK_Reference_3 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

alter table sys_permission_role add constraint FK_Reference_4 foreign key (permission_id)
      references sys_permission (id) on delete restrict on update restrict;

alter table sys_user_role add constraint FK_Reference_1 foreign key (user_id)
      references sys_user (id) on delete restrict on update restrict;

alter table sys_user_role add constraint FK_Reference_2 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

