<?php

class Lottery
{
    public $id;
    public $createdDate;
    public $isOpen;
    public $ticketCost;
    public $phoneNumber;

    public function __construct($id, $createdDate, $isOpen, $ticketCost, $phoneNumber)
    {
        $this->id = $id;
        $this->createdDate = $createdDate;
        $this->isOpen = $isOpen;
        $this->ticketCost = $ticketCost;
        $this->phoneNumber = $phoneNumber;
    }
}