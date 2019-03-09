package com.acn.vinlotteri.api;

import com.acn.vinlotteri.model.Lottery;
import com.acn.vinlotteri.model.ResourceWrapper;
import com.acn.vinlotteri.model.Ticket;
import com.acn.vinlotteri.repositories.LotteryRepository;
import com.acn.vinlotteri.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    @Autowired
    private LotteryRepository lotteryRepository;
    private TicketRepository ticketRepository;

    @PostMapping
    public ResponseEntity<ResourceWrapper> createLottery(@RequestParam(value = "phoneNumber") String phoneNumber,
                           @RequestParam(value = "ticketCost") Long ticketCost) {

        Lottery lottery = lotteryRepository.save(Lottery.builder()
                .phoneNumber(phoneNumber)
                .ticketCost(ticketCost)
                .open(true)
                .build());

        ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                .lottery(lottery)
                .build();

        return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
    }

    @GetMapping("/{lotteryId}")
    public ResponseEntity<ResourceWrapper> getLottery(@PathVariable(value = "lotteryId") Long lotteryId) {

        Optional<Lottery> lottery = lotteryRepository.findById(lotteryId);

        ResourceWrapper resourceWrapper;

        if (lottery.isPresent()) {
             resourceWrapper = ResourceWrapper.builder()
                    .lottery(lottery.get())
                    .build();
            return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{lotteryId}/tickets")
    public ResponseEntity<ResourceWrapper> createTickets(@PathVariable(value = "lotteryId") long lotteryId,
                                                         @RequestParam(value = "userName") String userName,
                                                         @RequestParam(value = "numberOfTickets") Integer numberOfTickets){
        List<Ticket> ticketList = null;

        ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                .ticketList(ticketList)
                .build();

        return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{lotteryId}/tickets")
    public ResponseEntity<ResourceWrapper> deleteTickets(@PathVariable(value = "lotteryId") long lotteryId,
                                                         @RequestParam(value = "userName") String userName){
        List<Ticket> ticketList = null;

        ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                .ticketList(ticketList)
                .build();

        return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
    }

    @GetMapping("/{lotteryId}/tickets")
    public ResponseEntity<ResourceWrapper> getTickets(@PathVariable(value = "lotteryId") long lotteryId){

        List<Ticket> ticketList = null;

        ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                .ticketList(ticketList)
                .build();

        return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
    }
}

