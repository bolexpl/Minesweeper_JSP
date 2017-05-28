<?php
session_start();
$title = "Wyniki";
$page = "records";
require_once "parts/header.php";
?>

<section class="records">

  <form method="get">
    <div class="form-group" style="width: 50%;">
      <label for="plansza">Rozmiar planszy:</label>
      <select class="form-control" id="plansza" name="board" style="width: 50%;display: inline-block;">
        <option value="all" <?= (!isset($_GET['board']) || $_GET['board'] == "all") ? "selected" : "" ?>>Wszystkie
        </option>
        <option value="8x8" <?= (isset($_GET['board']) && $_GET['board'] == "8x8") ? "selected" : "" ?>>8x8</option>
        <option value="16x16" <?= (isset($_GET['board']) && $_GET['board'] == "16x16") ? "selected" : "" ?>>16x16
        </option>
        <option value="30x16" <?= (isset($_GET['board']) && $_GET['board'] == "30x16") ? "selected" : "" ?>>30x16 lub
          16x30
        </option>
        <option value="custom" <?= (isset($_GET['board']) && $_GET['board'] == "custom") ? "selected" : "" ?>>Własne
          ustawienia
        </option>
      </select>
      <button class="btn btn-default" type="submit" style="display: inline-block;">Filtruj</button>
    </div>
  </form>

    <?php
    require_once "php/connect.php";

    $db = new PDO('mysql:host=' . $db_host . ";dbname=$db_name;charset=utf8", $db_user, $db_password,
        array(
            PDO::ATTR_EMULATE_PREPARES => false,
            PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
        )
    );

    try {
        $sql = "SELECT records.id, user_id, czas, board, login, avatar FROM records INNER JOIN users ON records.user_id = users.id ORDER BY records.czas";

        if (isset($_GET['board'])) {
            switch ($_GET['board']) {
                case "8x8":
                    $sql = "SELECT records.id, user_id, czas, board, login FROM records INNER JOIN users ON records.user_id = users.id WHERE board='8x8' ORDER BY records.czas";
                    break;
                case "16x16":
                    $sql = "SELECT records.id, user_id, czas, board, login FROM records INNER JOIN users ON records.user_id = users.id WHERE board='16x16' ORDER BY records.czas";
                    break;
                case "16x30":
                case "30x16":
                    $sql = "SELECT records.id, user_id, czas, board, login FROM records INNER JOIN users ON records.user_id = users.id WHERE board='30x16' OR board='16x30' ORDER BY records.czas";
                    break;
                case "custom":
                    $sql = "SELECT records.id, user_id, czas, board, login FROM records INNER JOIN users ON records.user_id = users.id WHERE board NOT IN('8x8','16x16','30x16','16x30') ORDER BY records.czas";
                    break;
            }
        }
        $stmt = $db->prepare($sql);
        $stmt->execute();

        if ($stmt->rowCount() == 0):
            echo "Brak wyników.";
        else:
            ?>
          <table class="table table-bordered table-striped">
            <thead>
            <tr>
              <td>#</td>
              <td>Czas</td>
              <td>Plansza</td>
              <td>Gracz</td>
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
                <td><img src="avatars/<?=$row['avatar']?>" alt="" class="avatar"> <?= $row['login'] ?></td>
                <td>
                  <a href="php/delete_record.php?id=<?= $row['id'] ?>&page=records&param1=board&param2=<?= $_GET['board'] ?>">
                    <button type="button" class="btn btn-danger">
                      Usuń
                    </button>
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
