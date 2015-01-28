/* Удаляем и создаем базу тредов и постов */
DROP DATABASE IF EXISTS my_board;
CREATE DATABASE my_board CHARACTER SET utf8 COLLATE utf8_unicode_ci;


USE my_board;
/* Создаем таблицу тредов */
CREATE TABLE threads
(
	thread_number INT, /* Номер треда */
	last_update DATETIME, /* Время обновления треда */
	count_of_posts INT, /* Кол-во постов */
	
	PRIMARY KEY(thread_number)
);

/* Создаем таблицу постов */
CREATE TABLE posts
(
	post_number INT, /* Номер поста */
	thread_number INT, /* Номер треда, которому принадлежит данный пост */
	post_date DATETIME, /* Дата и время поста */
	post_name CHAR(100), /* Имя постящего */
	post_theme CHAR(255),  /* Тема поста */
	post_text TEXT(2047), /* Текст поста */
	image_name char(100), /* имя картинки */
	image_size INT, /*размер картинки (KB)*/
	image_width INT, /* ширина картинки */
	image_height INT, /* высота картинки */
	
	PRIMARY KEY(post_number)
);

/* Служебная таблица */
CREATE TABLE service_table
(
	last_post_number INT /* Номер последнего поста */
	admin_pass CHAR(20) /* Пароль администратора */
);

/* Устанавливаем ноль в поле количества постов на доске */
INSERT INTO service_table ( last_post_number ) VALUES ( 0 );
