<?php
session_start();
require_once "connect.php";

if(isset($_SESSION['error'])){
    unset($_SESSION['error']);
}
if(isset($_SESSION['success'])){
    unset($_SESSION['success']);
}

$login = $_POST['login'];
$pass = $_POST['pass'];

$db = new PDO('mysql:host=' . $db_host . ";dbname=$db_name;charset=utf8", $db_user, $db_password,
    array(
        PDO::ATTR_EMULATE_PREPARES => false,
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
    )
);

try {
    $stmt = $db->prepare("select * from users where login=:login or email=:email");
    $stmt->bindValue(":login", $login, PDO::PARAM_STR);
    $stmt->bindValue(":email", $login, PDO::PARAM_STR);
    $stmt->execute();

    if($stmt->rowCount() != 1){
        $_SESSION['error'] = "Zły login lub hasło";
    }else{
        $result = $stmt->fetchAll(PDO::FETCH_ASSOC)[0];

        if(password_verify($pass,$result["pass"])){
            $_SESSION['success'] = "Zalogowano";
            $_SESSION['id'] = $result['id'];
            $_SESSION['login'] = $result['login'];
            $_SESSION['avatar'] = $result['avatar'];
        }else{
            $_SESSION['error'] = "Zły login lub hasło";
        }
    }

} catch (PDOException $e) {
    $_SESSION['error'] = "Błąd logowania";
    echo $e;
}

if(isset($_SESSION['error'])){
    header("Location: ../login_form.php");
}else{
    if(isset($_GET['back']) && $_GET['back'] == "game"){
        header("Location: ../game.php");
    }else{
        header("Location: ../index.php");
    }
}