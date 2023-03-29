<?php
    if($_SERVER["REQUEST_METHOD"] == "GET"){
        require_once "conexion.php";
        
        $my_querry = "Select * from coordinador WHERE titulo != 'MSc'";
        $result = $conn->query($my_querry);
        if($conn->affected_rows > 0){
            $json = "{ \"data\": [";
            while($row = $result->fetch_assoc()){
                $json = $json . json_encode($row, JSON_PRETTY_PRINT);
                $json = $json . ",";
            }
            $json = $json . "]}";
            echo $json;
        }
        $result->close();
        $conn->close();
    }
?>