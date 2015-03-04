echo "Проливаю скрипт: init.sql";
mysql -u root --password=123456 < init.sql;

echo "Проливаю скрипт: default_data.sql";
mysql -u root --password=123456 < default_data.sql;
