USE my_board;

DROP PROCEDURE IF EXISTS add_post;

DELIMITER //
CREATE PROCEDURE add_post
(
	IN arg_thread_number INT, /* Номер треда */
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
	DECLARE thread_count_of_posts INT; /* Переменная для хранения кол-ва постов в треде */
	
	SET new_post_number = (SELECT last_post_number FROM service_table) + 1;
	
	/* Добавляем пост в тред с номером arg_thread_number */
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
		arg_thread_number,
		NOW(),
		arg_post_name,
		arg_post_theme,
		arg_post_text,
		arg_post_image_name,
		arg_post_image_size,
		arg_post_image_width,
		arg_post_image_height
	);
	
	/* Обновляем дату последнего поста в тред с номером arg_thread_number */
	/* и увеличиваем число с количеством постов */
	UPDATE threads 
	SET last_update = NOW(),
	count_of_posts = count_of_posts + 1
	WHERE thread_number = arg_thread_number;
	
	/* Увеличиваем на единицу число постов в сервисной таблице */
	UPDATE service_table SET last_post_number = last_post_number + 1;
END//
