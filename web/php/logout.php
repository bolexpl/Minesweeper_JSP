<?php
session_start();

if(isset($_SESSION['error'])){
    unset($_SESSION['error']);
}
if(isset($_SESSION['success'])){
    unset($_SESSION['success']);
}

if(isset($_SESSION['login'])){
    unset($_SESSION['id']);
    unset($_SESSION['login']);
    $_SESSION['success'] = "Wylogowano";
}

header("Location: ../index.php");