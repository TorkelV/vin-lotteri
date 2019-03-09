package com.acn.vinlotteri.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceWrapper {

    private Lottery lottery;
    private List<Ticket> ticketList;
    private Ticket ticket;

}
