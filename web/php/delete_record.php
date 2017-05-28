<?php
session_start();
require_once "connect.php";

if (isset($_SESSION['error'])) {
    unset($_SESSION['error']);
}
if (isset($_SESSION['success'])) {
    unset($_SESSION['success']);
}

$db = new PDO('mysql:host=' . $db_host . ";dbname=$db_name;charset=utf8", $db_user, $db_password,
    array(
        PDO::ATTR_EMULATE_PREPARES => false,
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
    )
);

try {
    $db->beginTransaction();

    $stmt = $db->prepare("DELETE FROM records WHERE id=:id");
    $stmt->bindValue(":id", $_GET['id'], PDO::PARAM_INT);
    $stmt->execute();

    $db->commit();

} catch (PDOException $e) {
    $db->rollBack();
    $_SESSION['error'] = "Błąd usuwania wyniku";
    echo $e;
}

if (isset($_GET['board'])) {
    header("Location: ../" . $_GET['page'] . ".php?board=" . $_GET['board']);
} else {
    header("Location: ../" . $_GET['page'] . ".php");
}

