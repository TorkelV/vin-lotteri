<?php
class kobling extends mysqli {
    public function __construct(
        $host = "localhost",
        $base = "",
        $user = "",
        $pw = "",
        $port = "3306") {
        parent::__construct($host,$user,$pw,$base,$port);
    }
}
if (!($db =new kobling())) {
    echo("No db connection");
}
?>