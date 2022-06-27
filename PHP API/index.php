<?php

require_once 'db.php';

try{

	$dbh = new PDO($str_connect,$db_user,$db_pass,$options); //Подключение к базе

	$sql = 'select G.GRO_ID as groId, GH.FULL_NAME as fullName, GH.COURSE as cource, C.EDF_ID as edfId, G.GRT_ID as grtId ';
	$sql .= 'from GROUPS G ';
	$sql .= 'inner join CURRICULUMS C on (G.CUR_ID = C.CUR_ID) ';
	$sql .= 'inner join GROUP_HIST GH on ((G.GRO_ID = GH.GRO_ID) ';
	$sql .= '  and (GH.GROH_AD = ( ';
	$sql .= '     select GROH_AD ';
	$sql .= '     from GET_GROH_AD(G.GRO_ID, current_date)))) ';
	$sql .= 'where (C.PRL_ID in (0,2)) and (GH.FINISHED = 0) '; //SQL-запрос

	$result = $dbh->query($sql); //Выборка данных в переменную RESULT

	$data = array(); //Создание массива
	while ($row = $result->fetch(PDO::FETCH_ASSOC)){ //Перебор записей
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
?>