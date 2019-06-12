create table docker_stack_layui
(
  id              varchar(50) not null primary key,
  name            varchar(100),
  dockerstackname varchar(50),
  yaml            varchar(3000),
  status          varchar(10),
  count           varchar(10),
  createuser      varchar(50),
  updatetime      datetime
);

create table docker_storage_layui
(
  id          varchar(50) not null primary key,
  name        varchar(50),
  type        varchar(10),
  rootdir     varchar(50),
  workspace   varchar(50),
  path        varchar(100),
  storagesize varchar(10),
  createuser  varchar(50),
  updatetime  datetime
);