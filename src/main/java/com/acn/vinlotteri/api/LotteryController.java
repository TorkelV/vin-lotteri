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
import java.util.Random;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/lotteries")
public class LotteryController {

    @Autowired
    private LotteryRepository lotteryRepository;
    @Autowired
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
                                                         @RequestParam(value = "numberOfTickets") int numberOfTickets) {
        IntStream.range(0, numberOfTickets).forEach(
                i -> ticketRepository.save(Ticket.builder()
                        .lotteryId(lotteryId)
                        .userName(userName)
                        .winner(false)
                        .build())
        );

        List<Ticket> ticketList = ticketRepository.findAllByLotteryId(lotteryId);

        ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                .ticketList(ticketList)
                .build();

        return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
    }

    @DeleteMapping("/{lotteryId}/tickets")
    public ResponseEntity<ResourceWrapper> deleteTickets(@PathVariable(value = "lotteryId") long lotteryId,
                                                         @RequestParam(value = "userName") String userName) {

        List<Ticket> ticketList = ticketRepository.findAllByLotteryIdAndUserName(lotteryId, userName);
        ticketRepository.deleteAll(ticketList);

        ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                .ticketList(ticketList)
                .build();

        return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
    }

    @GetMapping("/{lotteryId}/tickets")
    public ResponseEntity<ResourceWrapper> getTickets(@PathVariable(value = "lotteryId") long lotteryId) {

        List<Ticket> ticketList = ticketRepository.findAllByLotteryId(lotteryId);

        ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                .ticketList(ticketList)
                .build();

        return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
    }

    @PutMapping("/{lotteryId}/tickets")
    public ResponseEntity<ResourceWrapper> resetTicket(@PathVariable(value = "lotteryId") long lotteryId,
                                                       @RequestParam(value = "ticketId") long ticketId) {

        Optional<Ticket> ticket = ticketRepository.findById(ticketId);

        if (ticket.isPresent()) {
            ticketRepository.save(Ticket.builder()
                    .lotteryId(lotteryId)
                    .id(ticketId)
                    .winner(false)
                    .build());

            ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                    .ticket(ticket.get())
                    .build();

            return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{lotteryId}/tickets")
    public ResponseEntity<ResourceWrapper> drawTicket(@PathVariable(value = "lotteryId") long lotteryId) {

        List<Ticket> ticketList = ticketRepository.findAllByLotteryIdAndWinner(lotteryId, false);

        Random random = new Random();
        Ticket randomTicket = ticketList.get(random.nextInt(ticketList.size()));

        randomTicket.setWinner(true);

        ticketRepository.save(randomTicket);

        ResourceWrapper resourceWrapper = ResourceWrapper.builder()
                .ticket(randomTicket)
                .build();

        return new ResponseEntity<>(resourceWrapper, HttpStatus.OK);

    }
}

