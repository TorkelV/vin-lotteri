<?php

class Ticket
{
    public $id;
    public $lotteryId;
    public $userName;
    public $winner;
    public $createdDate;

    public function __construct($id, $lotteryId, $userName, $winner, $createdDate)
    {
        $this->id = $id;
        $this->lotteryId = $lotteryId;
        $this->userName = $userName;
        $this->winner = $winner;
        $this->createdDate = $createdDate;
    }
}