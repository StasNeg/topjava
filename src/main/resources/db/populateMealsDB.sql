
DELETE FROM meals;
ALTER SEQUENCE global_seq_meal RESTART WITH 100000;

INSERT INTO meals (datetime, description, calories)
VALUES (now(), 'user@yandex.ru', 'password');

