package com.acn.vinlotteri.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResourceWrapper {

    private Lottery lottery;
    private List<Ticket> ticketList;

}
