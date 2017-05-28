<?php
session_start();
require_once "../php/connect.php";

if(isset($_SESSION['error'])){
    unset($_SESSION['error']);
}
if(isset($_SESSION['success'])){
    unset($_SESSION['success']);
}

header("Content-type:application/json");

if(!isset($_SESSION['login'])){
    echo json_encode("end");
    die();
}

$user_id = $_SESSION['id'];
$time = $_POST['time'];
$board = $_POST['board'];

$db = new PDO('mysql:host=' . $db_host . ";dbname=$db_name;charset=utf8", $db_user, $db_password,
    array(
        PDO::ATTR_EMULATE_PREPARES => false,
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
    )
);

try {
    $db->beginTransaction();

    $stmt = $db->prepare("INSERT INTO records (user_id, czas, board) VALUE (:user_id, :czas, :board)");
    $stmt->bindValue(":user_id", $user_id, PDO::PARAM_INT);
    $stmt->bindValue(":czas", $time, PDO::PARAM_INT);
    $stmt->bindValue(":board", $board, PDO::PARAM_STR);
    $stmt->execute();

    echo json_encode(true);

    $db->commit();

} catch (PDOException $e) {
    $db->rollBack();
    echo json_encode($e);
}
