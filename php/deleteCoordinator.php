<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    require_once 'conexion.php';
    $idC = $_POST["idC"];
    $my_query = "DELETE FROM coordinador
    WHERE idC = '".$idC."'";
    $result = $conn -> query($my_query);
    if($result == true){
        echo "\n Registro eliminado satisfactoriamente...";
    }else{
        echo "Error al eliminado registro...";
    }
}else{
    echo"Error desconocido";
}
?>