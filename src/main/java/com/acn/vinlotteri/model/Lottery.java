package com.acn.vinlotteri.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String createdDate;
    private Boolean open;
    private Long ticketCost;
    private String phoneNumber;

}
