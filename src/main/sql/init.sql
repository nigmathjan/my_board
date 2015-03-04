DROP DATABASE IF EXISTS MYBOARD;


/* Создаем чистую базу */
CREATE DATABASE MYBOARD CHARACTER SET utf8 COLLATE utf8_unicode_ci;


USE MYBOARD;


/* Таблица тредов */
CREATE TABLE thread
(
	thread_number INT, /* Номер треда */
	last_update DATETIME, /* Дата последнего обновления */
	
	PRIMARY KEY(thread_number)
);


/* Создаем таблицу постов */
CREATE TABLE post
(
	post_number INT, /* Номер поста */
	thread_number INT, /* Номер треда */
	post_date DATETIME, /* Дата и время поста */
	post_name CHAR(100), /* Имя постящего */
	post_theme CHAR(255),  /* Тема поста */
	post_text TEXT(2047), /* Текст поста */
	post_pass TEXT(100), /* Пароль поста */
	image_name char(100), /* имя картинки */
	image_size INT, /*размер картинки (KB)*/
	image_width INT, /* ширина картинки */
	image_height INT, /* высота картинки */
	
	PRIMARY KEY(post_number)
);


/* Служебная таблица */
CREATE TABLE service_table
(
	id INT, /* Номер последнего поста */
	admin_pass CHAR(20) /* Пароль администратора */
);
