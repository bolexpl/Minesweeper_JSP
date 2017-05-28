<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title><?= $title ?></title>
  <link rel="stylesheet" href="css/bootstrap-theme.min.css">
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="container">

  <nav class="navbar navbar-inverse">
    <div class="container-fluid">

      <!--       Brand and toggle get grouped for better mobile display-->
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="index.php">Saper</a>
      </div>


      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">
          <li <?= $page == "game" ? "class=\"active\"" : "" ?>>
            <a href="game.php">Gra <?= $page == "game" ? "<span class=\"sr-only\">(current)</span>" : "" ?></a>
          </li>
          <li <?= $page == "records" ? "class=\"active\"" : "" ?>>
            <a href="records.php">Wyniki <?= $page == "records" ? "<span class=\"sr-only\">(current)</span>" : "" ?></a>
          </li>
        </ul>

          <?php
          if (isset($_SESSION['login'])):
              ?>
            <div class="btn-group navbar-right">
              <ul class="nav navbar-nav">
                <li <?= $page == "records_user" ? "class=\"active\"" : "" ?>>
                  <a href="records_user.php">Zalogowany
                    jako: <?= $_SESSION['login'] ?> <?= $page == "game" ? "<span class=\"sr-only\">(current)</span>" : "" ?>
                    <img src="avatars/<?=$_SESSION['avatar']?>" alt="" class="avatar">
                  </a>
                </li>
              </ul>
              <div class="btn-group" role="group">
                <a href="php/logout.php">
                  <button type="button" class="btn btn-default navbar-btn">
                    Wyloguj
                  </button>
                </a>
              </div>
            </div>
              <?php
          else:
              ?>
            <div class="btn-group navbar-right">
              <div class="btn-group" role="group">
                <a href="login_form.php">
                  <button type="button" class="btn btn-default navbar-btn">
                    Zaloguj
                  </button>
                </a>
              </div>
              <div class="btn-group" role="group">
                <a href="register_form.php">
                  <button type="button" class="btn btn-default navbar-btn">
                    Zarejestruj
                  </button>
                </a>
              </div>
            </div>
              <?php
          endif;
          ?>

      </div>
    </div>
  </nav>


  <div class="alert alert-danger alert-dismissible <?= !isset($_SESSION['error']) ? 'hidden' : '' ?>" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <strong>Ostrzeżenie!</strong>
      <?php
      echo isset($_SESSION['error']) ? $_SESSION['error'] : '';
      unset($_SESSION['error']);
      ?>
  </div>

  <div class="alert alert-success alert-dismissible <?= !isset($_SESSION['success']) ? 'hidden' : '' ?>" role="alert">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
    </button>
    <strong>Sukces!</strong>
      <?php
      echo isset($_SESSION['success']) ? $_SESSION['success'] : '';
      unset($_SESSION['success']);
      ?>
  </div>
