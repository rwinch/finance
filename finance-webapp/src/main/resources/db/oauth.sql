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
	additional_information VARCHAR(4096)
);

create table oauth_access_token (
	token_id VARCHAR(256),
	token LONGVARBINARY,
	authentication_id VARCHAR(256),
	user_name VARCHAR(256),
	client_id VARCHAR(256),
	authentication LONGVARBINARY,
	refresh_token VARCHAR(256)
);

create table oauth_refresh_token (
	token_id VARCHAR(256),
	token LONGVARBINARY,
	authentication LONGVARBINARY
);

create table oauth_code (
	code VARCHAR(256), authentication LONGVARBINARY
);

insert into oauth_client_details (client_id,client_secret,scope,authorized_grant_types,authorities) values (
	'mobile-finance','password','supervisor,user','password,authorization_code,refresh_token,implicit','ROLE_CLIENT');