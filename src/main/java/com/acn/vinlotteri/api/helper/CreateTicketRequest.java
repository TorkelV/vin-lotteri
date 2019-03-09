package com.acn.vinlotteri.api.helper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTicketRequest {

    private String userName;
    private int numberOfTickets;

}
