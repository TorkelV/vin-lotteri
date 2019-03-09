package com.acn.vinlotteri.repositories;

import com.acn.vinlotteri.model.Lottery;
import com.acn.vinlotteri.model.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Ticket findById(long id);
}
