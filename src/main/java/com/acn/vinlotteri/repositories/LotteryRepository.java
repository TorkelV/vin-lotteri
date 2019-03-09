package com.acn.vinlotteri.repositories;

import com.acn.vinlotteri.model.Lottery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LotteryRepository extends CrudRepository<Lottery, Long> {

    Lottery findById(long id);
}
