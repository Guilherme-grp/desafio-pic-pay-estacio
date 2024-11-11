--users
INSERT INTO public.picpay_user (cnpj, cpf, createdat, email, encryptedpassword, "type", updatedat) VALUES(null, '12345678909', now(), 'user1@mail.com', 'passwrd', 'DEFAULT', now());
INSERT INTO public.picpay_wallet (balance, owner_id) VALUES(100, (select id from picpay_user where email='user1@mail.com'));

INSERT INTO public.picpay_user (cnpj, cpf, createdat, email, encryptedpassword, "type", updatedat) VALUES(null, '50443669007', now(), 'user2@mail.com', 'passwrd', 'DEFAULT', now());
INSERT INTO public.picpay_wallet (balance, owner_id) VALUES(1000, (select id from picpay_user where email='user2@mail.com'));

--shopkeepers
INSERT INTO public.picpay_user (cnpj, cpf, createdat, email, encryptedpassword, "type", updatedat) VALUES('84261480000134', null, now(), 'shop1@mail.com', 'passwrd', 'SHOPKEEPER', now());
INSERT INTO public.picpay_wallet (balance, owner_id) VALUES(0, (select id from picpay_user where email='shop1@mail.com'));

INSERT INTO public.picpay_user (cnpj, cpf, createdat, email, encryptedpassword, "type", updatedat) VALUES('31975787000130', null, now(), 'shop2@mail.com', 'passwrd', 'SHOPKEEPER', now());
INSERT INTO public.picpay_wallet (balance, owner_id) VALUES(100, (select id from picpay_user where email='shop2@mail.com'));