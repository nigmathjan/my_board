echo "Проливаю скрипт: create_base_and_tables.sql"
mysql -u root --password=123456 < create_base_and_tables.sql
echo "Проливаю скрипт: create_thread.sql"
mysql -u root --password=123456 < create_thread.sql
echo "Проливаю скрипт: add_new_post.sql"
mysql -u root --password=123456 < add_new_post.sql
#echo "Проливаю скрипт: add_default_posts.sql"
#mysql -u root --password=123456 < add_default_posts.sql
