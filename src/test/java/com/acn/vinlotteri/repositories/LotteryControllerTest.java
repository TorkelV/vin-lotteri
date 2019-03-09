package com.acn.vinlotteri.repositories;

import com.acn.vinlotteri.MyRestTestConfig;
import com.acn.vinlotteri.api.LotteryController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = LotteryController.class, secure = false)
@SpringJUnitWebConfig(MyRestTestConfig.class)
class LotteryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LotteryRepository lotteryRepository;
    @MockBean
    private TicketRepository ticketRepository;

    @Test
    @Disabled
    void createLottery() throws Exception {

        mvc.perform(post("/lotteries", "41762525", 10L))
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
    void getLottery() throws Exception {

        mvc.perform(get("/lotteries/{lotteryId}", 10L))
                .andExpect(status().isOk());
    }
}