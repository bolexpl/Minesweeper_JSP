<?php
session_start();
$title = "Moje rekordy";
$page = "records_user";

if(!isset($_SESSION['login'])){
  header("Location: index.php");
}

require_once "parts/header.php";
?>

<section class="records">

  <form method="post" action="api/set_avatar.php" enctype="multipart/form-data">

    <div class="form-group" style="width: 50%;">
      <label for="width">Zmiana awatara</label>
      <input type="file" name="avatar" class="form-control" title="Aby usunąć awatar nie podawaj obrazka" id="avatar" placeholder="Awatar">
    </div>

    <button class="btn btn-default" type="submit" style="display: inline-block;">Zmień</button>
  </form>

  <hr/>

    <?php
    require_once "php/connect.php";

    $db = new PDO('mysql:host=' . $db_host . ";dbname=$db_name;charset=utf8", $db_user, $db_password,
        array(
            PDO::ATTR_EMULATE_PREPARES => false,
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
        )
    );

    try {
        $sql = "SELECT records.id, user_id, czas, board, login FROM records INNER JOIN users ON records.user_id = users.id WHERE user_id=:user_id ORDER BY records.czas";

        $stmt = $db->prepare($sql);
        $stmt->bindValue(":user_id", $_SESSION['id'], PDO::PARAM_INT);
        $stmt->execute();

        if ($stmt->rowCount() == 0):
            echo "Brak wyników.";
        else:
            ?>

            <h3>
              Moje wyniki
            </h3>
          <table class="table table-bordered table-striped">
            <thead>
            <tr>
              <td>#</td>
              <td>Czas</td>
              <td>Plansza</td>
              <td></td>
            </tr>
            </thead>
            <tbody>

            <?php
            $i = 1;
            foreach ($stmt->fetchAll(PDO::FETCH_ASSOC) as $row):
                ?>
              <tr>
                <td><?= $i++ ?>.</td>
                <td><?= $row['czas'] ?>s</td>
                <td><?= $row['board'] ?></td>
                <td>
                  <a href="php/delete_record.php?id=<?= $row['id'] ?>&page=records_user">
                    <button type="button" class="btn btn-danger">Usuń</button>
                  </a>
                </td>
              </tr>
                <?php
            endforeach;
            ?>

            </tbody>
          </table>
            <?php
        endif;

    } catch (PDOException $e) {
        echo "Błąd pobrania wyników.";
    }
    ?>
</section>


<?php
require_once "parts/footer.php";
?>
