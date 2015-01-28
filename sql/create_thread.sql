USE my_board;

DROP PROCEDURE IF EXISTS create_thread;

DELIMITER //
CREATE PROCEDURE create_thread
(
	IN arg_post_name CHAR(100), /* Имя постера */
	IN arg_post_theme CHAR(255), /* Тема поста */
	IN arg_post_text TEXT(2047), /* Текст поста */
	IN arg_post_image_name CHAR(100), /* Имя картинки поста */
	IN arg_post_image_size INT, /* Размер картинки */
	IN arg_post_image_width INT, /* Ширина картинки */
	IN arg_post_image_height INT /* Высота картинки */
)
LANGUAGE SQL
DETERMINISTIC
SQL SECURITY DEFINER
COMMENT ''
BEGIN
	DECLARE new_post_number INT;
	DECLARE new_thread_number INT;
	
	SET new_post_number = (SELECT last_post_number FROM service_table) + 1;
	SET new_thread_number = new_post_number;
	
	/* Создаем новый тред */
	INSERT INTO threads
	(
		thread_number,
		last_update,
		count_of_posts
	)
	VALUES
	(
		new_thread_number,
		NOW(),
		1
	);
	
	/* Добавляем нвый пост для этого треда */
	INSERT INTO posts
	(
		post_number,
		posts.thread_number,
		post_date,
		post_name,
		post_theme,
		post_text,
		image_name,
		image_size,
		image_width,
		image_height
	)
	VALUES
	(
		new_post_number,
		new_post_number,
		NOW(),
		arg_post_name,
		arg_post_theme,
		arg_post_text,
		arg_post_image_name,
		arg_post_image_size,
		arg_post_image_width,
		arg_post_image_height
	);
	
	/* Увеличиваем на единицу число постов в сервисной таблице */
	UPDATE service_table SET last_post_number = last_post_number + 1;
END//
