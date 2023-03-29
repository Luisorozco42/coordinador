<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $nombre = $_POST["nombre"];
    $apellido = $_POST["apellido"];
    $fechaNac  = $_POST["fechaNac"];
    $titulo = $_POST["titulo"];
    $email = $_POST["email"];
    $facultad = $_POST["facultad"];
    $my_query = "INSERT INTO coordinador(nombre, apellido, fechaNac, titulo, email, facultad)
    values('".$nombre."', '".$apellido."', '".$fechaNac."', '".$titulo."', '".$email."', '".$facultad."')";
    $result = $conn -> query($my_query);
    if($result == true){
        echo "\n Registro guardado satisfactoriamente...";
    }else{
        echo "Error al guardar registro...";
    }
}else{
    echo"Error desconocido";
}
?>