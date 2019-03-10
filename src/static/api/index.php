<?php
include "db.php";
include "Lottery.php";
include "Ticket.php";
include "ResourceWrapper.php";
$path = $_POST['f'];

switch ($path){
    case "drawTicket":
        $response = new ResourceWrapper();
        $response->ticket = drawTicket($db);
        echo(json_encode($response));
        break;
    case "createLottery":
        $response = new ResourceWrapper();
        $response->lottery=createLottery($db, $_POST['ticketCost'],$_POST['phoneNumber']);
        echo(json_encode($response));
        break;
    case "getLottery":
        $response = new ResourceWrapper();
        $response->lottery=getLottery($db, $_POST['id']);
        echo(json_encode($response));
        break;
    case "createTickets":
        $response = new ResourceWrapper();
        $response->ticketList = createTickets($db,$_POST['id'],$_POST['userName'],$_POST['numberOfTickets']);
        echo(json_encode($response));
        break;
    case "getTickets":
        $response = new ResourceWrapper();
        $response->ticketList=getTickets($db);
        echo(json_encode($response));
        break;
    default:
        echo "";
}


function createTickets($db,$lotteryId,$userName,$numberOfTickets){
    $date = date_format(new DateTime(),"Y/m/d H:i:s");
    $tickets = [];
    $j = 0;
    for($i=0; $i<intval($numberOfTickets); $i++){
        $sql =  $db->prepare("INSERT INTO `ticket` (`lotteryId`, `userName`, `winner`, `createdDate`) VALUES (?, ?, ?, ?)");
        $winner = 0;
        $sql->bind_param("isis",$lotteryId,$userName,$winner,$date);
        $sql->execute();
        echo $sql->error;
        $tickets[$j++] = $sql->insert_id;
    }
    return $tickets;

}

function getTickets($db){
    $sql = "select * from ticket";
    $res = $db->query($sql);
    $rows = $res->fetch_all(MYSQLI_ASSOC);
    $counto = 0;
    $tickets = [];
    $i = 0;
    foreach($rows as $row){
        $tickets[$i++] = new Ticket($row['id'],$row['lotteryId'],$row['userName'],$row['winner'],$row['createdDate']);
    }
    return $tickets;
}

function drawTicket($db){
    $sql = "select * from ticket where winner!=1 ORDER BY RAND()";
    $res = $db->query($sql);
    $row = $res->fetch_assoc();
    $sql = "update ticket set winner=1 where id=".$row['id'];
    $db->query($sql);
    return new Ticket($row['id'],$row['lotteryId'],$row['userName'],$row['winner'],$row['createdDate']);
}

function getLottery($db, $id){
    $sql = "select * from lottery where id=".$id;
    $res = $db->query($sql);
    $row = $res->fetch_assoc();
    return new Lottery($row['id'],$row['createdDate'],$row['isOpen'],$row['ticketCost'], $row['phoneNumber']);
}

function createLottery($db, $ticketCost,$phoneNumber){
    $sql =  $db->prepare("INSERT INTO `lottery` (`createdDate`, `isOpen`, `ticketCost`, `phoneNumber`) VALUES (?, ?, ?, ?)");
    $date = date_format(new DateTime(),"Y/m/d H:i:s");
    $isOpen = "true";
    $sql->bind_param("ssss",$date,$isOpen,$ticketCost,$phoneNumber);
    if($sql->execute()){
        return getLottery($db, $sql->insert_id);
    }
}