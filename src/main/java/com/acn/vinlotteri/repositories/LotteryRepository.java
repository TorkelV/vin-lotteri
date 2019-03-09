package com.acn.vinlotteri.repositories;

import com.acn.vinlotteri.model.Lottery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


@Component
public interface LotteryRepository extends CrudRepository<Lottery, Long> {

}
