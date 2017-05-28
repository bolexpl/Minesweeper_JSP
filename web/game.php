<?php
session_start();
$title = "Gra";
$page = "game";

if(!isset($_SESSION['login'])){
  header("Location: login_form.php?back=game");
}

require_once "parts/header.php";
?>

    <section class="board" id="container">
        <?php
        require_once "form.php";
        ?>
    </section>

<?php
require_once "parts/footer.php";
?>