package com.n26.controller;

import com.n26.model.StatisticsSummary;
import com.n26.service.IStatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.n26.constant.Constants.ENDPOINT_STATISTICS;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticsController.class)
public class StatisticsControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    IStatisticsService service;


    @Test
    public void validate_statistics() throws Exception {
        StatisticsSummary mockStatistics = new StatisticsSummary(
                new BigDecimal(4.00),
                new BigDecimal(2.00),
                new BigDecimal(3.00),
                new BigDecimal(1.00),
                new BigDecimal(2.00)
        );

        doReturn(mockStatistics).when(service).getStatistics();

        mvc.perform(get(ENDPOINT_STATISTICS)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sum", is("4.00")))
                .andExpect(jsonPath("$.avg", is("2.00")))
                .andExpect(jsonPath("$.max", is("3.00")))
                .andExpect(jsonPath("$.min", is("1.00")))
                .andExpect(jsonPath("$.count", is(2)));

        verify(service, times(1)).getStatistics();

    }
}