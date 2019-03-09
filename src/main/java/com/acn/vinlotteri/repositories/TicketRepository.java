package com.acn.vinlotteri.repositories;

import com.acn.vinlotteri.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    List<Ticket> findAllByLotteryId(long lotteryId);
    List<Ticket> findAllByLotteryIdAndWinner(long lotteryId, boolean winner);
    List<Ticket> findAllByLotteryIdAndUserName(long lotteryId, String userName);
}
