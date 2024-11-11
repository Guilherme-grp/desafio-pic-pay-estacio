--users
INSERT INTO picpay_user (cnpj, cpf, createdat, email, encryptedpassword, type, updatedat) VALUES(null, '12345678909', CURRENT_TIMESTAMP(), 'user1@mail.com', 'passwrd', 'DEFAULT', CURRENT_TIMESTAMP());
INSERT INTO picpay_wallet (balance, owner_id) VALUES(1000, (select id from picpay_user where email='user1@mail.com'));

INSERT INTO picpay_user (cnpj, cpf, createdat, email, encryptedpassword, type, updatedat) VALUES(null, '50443669007', CURRENT_TIMESTAMP(), 'user2@mail.com', 'passwrd', 'DEFAULT', CURRENT_TIMESTAMP());
INSERT INTO picpay_wallet (balance, owner_id) VALUES(1000, (select id from picpay_user where email='user2@mail.com'));

--shopkeepers
INSERT INTO picpay_user (cnpj, cpf, createdat, email, encryptedpassword, type, updatedat) VALUES('84261480000134', null, CURRENT_TIMESTAMP(), 'shop1@mail.com', 'passwrd', 'SHOPKEEPER', CURRENT_TIMESTAMP());
INSERT INTO picpay_wallet (balance, owner_id) VALUES(0, (select id from picpay_user where email='shop1@mail.com'));

INSERT INTO picpay_user (cnpj, cpf, createdat, email, encryptedpassword, type, updatedat) VALUES('31975787000130', null, CURRENT_TIMESTAMP(), 'shop2@mail.com', 'passwrd', 'SHOPKEEPER', CURRENT_TIMESTAMP());
INSERT INTO picpay_wallet (balance, owner_id) VALUES(100, (select id from picpay_user where email='shop2@mail.com'));