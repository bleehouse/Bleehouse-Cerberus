INSERT INTO users(id, username, password, last_password_reset, authorities) VALUES(1, 'user', 'password', null, 'USER');
INSERT INTO users(id, username, password, last_password_reset, authorities) VALUES(2, 'admin', 'password', null, 'ADMIN');
INSERT INTO users(id, username, password, last_password_reset, authorities) VALUES(3, 'expired', 'password', '2050-12-05', 'USER');