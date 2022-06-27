<?php

require_once 'db_test.php';

//пример запроса
//http://188.68.31.11:8080/index_dubrovskiy.php?dataStart=11.05.2000&dataEnd=11.05.2022&worId=0&groId=0&rooId=0


$dataStart = 'null';
if (isset($_GET['dataStart'])) {
    $dataStart = $_GET['dataStart'];
}

$dataEnd = 'null';
if (isset($_GET['dataEnd'])) {
    $dataEnd = $_GET['dataEnd'];
}

$worId = 'null';
if(isset($_GET['worId'])){
    $worId = $_GET['worId'];
}

$groId = 'null';
if(isset($_GET['groId'])){
    $groId = $_GET['groId'];
}

$rooId = 'null';
if(isset($_GET['rooId'])){
    $rooId = $_GET['rooId'];
}

try {
    $dbh = new PDO($str_connect, $db_user, $db_pass, $options); //Подключение к базе

    // все выходные параметры
    // "ACV_ID, ATP_ID, ACTIVITY_TYPE, NAME, SBD_ID, SUBDIVISION, LOCAL, cont_wor_id, cont_wor_name, PLAN_DATE, PLAN_START_TIME, PLAN_END_TIME, PLAN_DATE_TIME_STR, PLACE, EXEC_DATE, RESPONSIBLE_WORKERS, SIGNED, CANCELED"
    //sql-запрос
    $sql = "select NAME from acv_rep_01('" . $dataStart . "', '" . $dataEnd . "', null, null, 1, 1)";

    $result = $dbh->query($sql); //Выборка данных в переменную RESULT

    $data = array(); //Создание массива
    while ($row = $result->fetch(PDO::FETCH_ASSOC)) { //Перебор записей
        $data[] = $row; //Внесение записей в массив
    }

    echo(json_encode($data)); //Конвертирование в JSON
    // Освобождаем переменную, отвечающаю за строчку массива
    unset($row);
    // Освобождаем переменную, отвечающаю за строчку массива
    unset($result);
    $dbh = null; //Отключение от базы
} catch (PDOException $e) {
    print "Error!: " . $e->getMessage() . "<br/>"; //Обработка ошибок
    die();
}
