<?php
    session_start();
    if ($_SESSION['nonce'] === $_POST['nonce'] && !empty($_FILES['payload'])) {
        $info = pathinfo($_FILES['payload']['name']);
        $fname = $_FILES['payload']['tmp_name'];
        // new file must be less than 10mb
        if (filesize($fname) < 10 * pow(1024, 2))
            move_uploaded_file($fname, "./audio.wav");
        $_SESSION['nonce'] = '';
    }

    exit;
