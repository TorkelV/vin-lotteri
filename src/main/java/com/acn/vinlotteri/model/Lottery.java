package com.acn.vinlotteri.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
public class Lottery {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @CreatedDate
    private LocalDateTime createdDate;
    private Boolean open;
    private Long ticketCost;
    private String phoneNumber;


}
