USE MYBOARD;

/* Создаем дефолтные треды */
INSERT INTO thread ( thread_number, last_update ) VALUES ( 1, NOW() );
INSERT INTO thread ( thread_number, last_update ) VALUES ( 2, NOW() );

/* Добавляем дефолтные посты в тред 1 */
INSERT INTO post
(
	post_number,
	thread_number,
	post_date,
	post_name,
	post_theme,
	post_text,
	post_pass,
	image_name,
	image_size,
	image_width,
	image_height
)
VALUES
(
	1,
	1,
	NOW(),
	'anon',
	'oppost',
	'blah blah',
	'123456',
	'govno.jpg',
	1,
	2,
	3
);


INSERT INTO post
(
	post_number,
	thread_number,
	post_date,
	post_name,
	post_theme,
	post_text,
	post_pass,
	image_name,
	image_size,
	image_width,
	image_height
)
VALUES
(
	3,
	1,
	NOW(),
	'anon',
	'второй пост',
	'blah blah',
	'123456',
	'govno.jpg',
	1,
	2,
	3
);


INSERT INTO post
(
	post_number,
	thread_number,
	post_date,
	post_name,
	post_theme,
	post_text,
	post_pass,
	image_name,
	image_size,
	image_width,
	image_height
)
VALUES
(
	4,
	1,
	NOW(),
	'anon',
	'третий пост',
	'Ну давай разберем по частям тобою написанное )))',
	'123456',
	'no_image',
	1,
	2,
	3
);


/* Добавляем дефолтные посты в тред 2 */
INSERT INTO post
(
	post_number,
	thread_number,
	post_date,
	post_name,
	post_theme,
	post_text,
	post_pass,
	image_name,
	image_size,
	image_width,
	image_height
)
VALUES
(
	2,
	2,
	NOW(),
	'anon',
	'oppost, thread 2',
	'blah blah blaaaaah',
	'1234',
	'niger.jpg',
	11,
	22,
	33
);
