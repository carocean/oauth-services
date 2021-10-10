create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

INSERT INTO `oauth_client_details` VALUES ('client1', NULL, '$2a$10$oM/4QiWITscUiyCkxy2gMOQ5aXlQ.s9IBtJ10Hg1X79rwvQJtbr5i', 'all', 'authorization_code,refresh_token', 'http://client1.com/client1/login', NULL, 7200, 3600, NULL, 'true');
INSERT INTO `oauth_client_details` VALUES ('client2', NULL, '$2a$10$itMmLoas1ClL.4KlIQtM6u4Wffb2sF4.O6oUo0Ug9Lt/PFJVsnt0W', 'all', 'authorization_code,refresh_token', 'http://client2.com/client2/login', NULL, 7200, 3600, NULL, 'true');


create table oauth_client_token (
  token_id VARCHAR(256),
  token VARCHAR(256),
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256)
);

create table oauth_access_token (
  token_id VARCHAR(256),
  token VARCHAR(256),
  authentication_id VARCHAR(256) PRIMARY KEY,
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication VARCHAR(256),
  refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token VARCHAR(256),
  authentication VARCHAR(256)
);

create table oauth_code (
  code VARCHAR(256),
  authentication VARCHAR(256)
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
  appId VARCHAR(256) PRIMARY KEY,
  resourceIds VARCHAR(256),
  appSecret VARCHAR(256),
  scope VARCHAR(256),
  grantTypes VARCHAR(256),
  redirectUrl VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(256)
);

CREATE TABLE `client_details_test` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  client_id VARCHAR(256),
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256),
  PRIMARY KEY (`id`)
);

INSERT INTO `client_details_test` VALUES (1, 'client1', NULL, '$2a$10$oM/4QiWITscUiyCkxy2gMOQ5aXlQ.s9IBtJ10Hg1X79rwvQJtbr5i', 'all', 'authorization_code,refresh_token', 'http://client1.com/client1/login', NULL, 7200, 3600, NULL, 'true');
INSERT INTO `client_details_test` VALUES (2, 'client2', NULL, '$2a$10$itMmLoas1ClL.4KlIQtM6u4Wffb2sF4.O6oUo0Ug9Lt/PFJVsnt0W', 'all', 'authorization_code,refresh_token', 'http://client2.com/client2/login', NULL, 7200, 3600, NULL, 'true');
