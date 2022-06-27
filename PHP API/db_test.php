<?php

$driver = 'firebird'; //Драйвер PDO

$db_name = '192.168.0.103:d:\\college.fdb'; //Адрес базы данных

$db_user = 'dubrovskiy'; //Пользователь

$db_pass = 'edu-759'; //Пароль

$charset = 'utf8'; //Кодировка

$options = [PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION]; //Опции подключения(обработка ошибок)

$str_connect = "$driver:dbname=$db_name;charset=$charset"; //Строка подключения

?>