---------------------------------------------------
-- Export file for user MMLCL                    --
-- Created by alavinas on 10/12/2010, 5:48:55 PM --
---------------------------------------------------


create table QRTZ_JOB_DETAILS
(
  job_name          VARCHAR2(80) not null,
  job_group         VARCHAR2(80) not null,
  description       VARCHAR2(120),
  job_class_name    VARCHAR2(128) not null,
  is_durable        VARCHAR2(1) not null,
  is_volatile       VARCHAR2(1) not null,
  is_stateful       VARCHAR2(1) not null,
  requests_recovery VARCHAR2(1) not null,
  job_data          BLOB
)
;
alter table QRTZ_JOB_DETAILS
  add constraint QRTZ_JOB_DETAILSP1 primary key (JOB_NAME, JOB_GROUP);

create table QRTZ_TRIGGERS
(
  trigger_name   VARCHAR2(80) not null,
  trigger_group  VARCHAR2(80) not null,
  job_name       VARCHAR2(80) not null,
  job_group      VARCHAR2(80) not null,
  is_volatile    VARCHAR2(1) not null,
  description    VARCHAR2(120),
  next_fire_time NUMBER(13),
  prev_fire_time NUMBER(13),
  priority       NUMBER(13),
  trigger_state  VARCHAR2(16) not null,
  trigger_type   VARCHAR2(8) not null,
  start_time     NUMBER(13) not null,
  end_time       NUMBER(13),
  calendar_name  VARCHAR2(80),
  misfire_instr  NUMBER(2),
  job_data       BLOB
)
;
alter table QRTZ_TRIGGERS
  add constraint QRTZ_TRIGGERSP1 primary key (TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_TRIGGERS
  add foreign key (JOB_NAME, JOB_GROUP)
  references QRTZ_JOB_DETAILS (JOB_NAME, JOB_GROUP);

create table QRTZ_BLOB_TRIGGERS
(
  trigger_name  VARCHAR2(80) not null,
  trigger_group VARCHAR2(80) not null,
  blob_data     BLOB
)
;
alter table QRTZ_BLOB_TRIGGERS
  add constraint QRTZ_BLOB_TRIGGERSP1 primary key (TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_BLOB_TRIGGERS
  add foreign key (TRIGGER_NAME, TRIGGER_GROUP)
  references QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP);


create table QRTZ_CALENDARS
(
  calendar_name VARCHAR2(80) not null,
  calendar      BLOB not null
)
;
alter table QRTZ_CALENDARS
  add constraint QRTZ_CALENDARSP1 primary key (CALENDAR_NAME);

create table QRTZ_CRON_TRIGGERS
(
  trigger_name    VARCHAR2(80) not null,
  trigger_group   VARCHAR2(80) not null,
  cron_expression VARCHAR2(80) not null,
  time_zone_id    VARCHAR2(80)
)
;
alter table QRTZ_CRON_TRIGGERS
  add constraint QRTZ_CRON_TRIGGERSP1 primary key (TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_CRON_TRIGGERS
  add foreign key (TRIGGER_NAME, TRIGGER_GROUP)
  references QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP);

create table QRTZ_FIRED_TRIGGERS
(
  entry_id          VARCHAR2(95) not null,
  trigger_name      VARCHAR2(80) not null,
  trigger_group     VARCHAR2(80) not null,
  is_volatile       VARCHAR2(1) not null,
  instance_name     VARCHAR2(80) not null,
  fired_time        NUMBER(13) not null,
  priority          NUMBER(13) not null,
  state             VARCHAR2(16) not null,
  job_name          VARCHAR2(80),
  job_group         VARCHAR2(80),
  is_stateful       VARCHAR2(1),
  requests_recovery VARCHAR2(1)
)
;
alter table QRTZ_FIRED_TRIGGERS
  add constraint QRTZ_FIRED_TRIGGERSP1 primary key (ENTRY_ID);

create table QRTZ_JOB_LISTENERS
(
  job_name     VARCHAR2(80) not null,
  job_group    VARCHAR2(80) not null,
  job_listener VARCHAR2(80) not null
)
;
alter table QRTZ_JOB_LISTENERS
  add constraint QRTZ_JOB_LISTENERSP1 primary key (JOB_NAME, JOB_GROUP, JOB_LISTENER);
alter table QRTZ_JOB_LISTENERS
  add foreign key (JOB_NAME, JOB_GROUP)
  references QRTZ_JOB_DETAILS (JOB_NAME, JOB_GROUP);


create table QRTZ_LOCKS
(
  lock_name VARCHAR2(40) not null
)
;
alter table QRTZ_LOCKS
  add constraint QRTZ_LOCKSP1 primary key (LOCK_NAME);


create table QRTZ_PAUSED_TRIGGER_GRPS
(
  trigger_group VARCHAR2(80) not null
)
;
alter table QRTZ_PAUSED_TRIGGER_GRPS
  add constraint QRTZ_PAUSED_TRIGGER_GRPSP1 primary key (TRIGGER_GROUP);

create table QRTZ_SCHEDULER_STATE
(
  instance_name     VARCHAR2(80) not null,
  last_checkin_time NUMBER(13) not null,
  checkin_interval  NUMBER(13) not null
)
;
alter table QRTZ_SCHEDULER_STATE
  add constraint QRTZ_SCHEDULER_STATEP1 primary key (INSTANCE_NAME);

create table QRTZ_SIMPLE_TRIGGERS
(
  trigger_name    VARCHAR2(80) not null,
  trigger_group   VARCHAR2(80) not null,
  repeat_count    NUMBER(7) not null,
  repeat_interval NUMBER(12) not null,
  times_triggered NUMBER(7) not null
)
;
alter table QRTZ_SIMPLE_TRIGGERS
  add constraint QRTZ_SIMPLE_TRIGGERSP1 primary key (TRIGGER_NAME, TRIGGER_GROUP);
alter table QRTZ_SIMPLE_TRIGGERS
  add foreign key (TRIGGER_NAME, TRIGGER_GROUP)
  references QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP);

create table QRTZ_TRIGGER_LISTENERS
(
  trigger_name     VARCHAR2(80) not null,
  trigger_group    VARCHAR2(80) not null,
  trigger_listener VARCHAR2(80) not null
)
;
alter table QRTZ_TRIGGER_LISTENERS
  add constraint QRTZ_TRIGGER_LISTENERSP1 primary key (TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_LISTENER);
alter table QRTZ_TRIGGER_LISTENERS
  add foreign key (TRIGGER_NAME, TRIGGER_GROUP)
  references QRTZ_TRIGGERS (TRIGGER_NAME, TRIGGER_GROUP);
