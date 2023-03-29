<?php
    $conn = mysqli_connect("localhost", "root","", "myuca", "3306");
    if(!$conn){
        echo "Error de conexion";
    }
?>