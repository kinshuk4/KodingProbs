package com.n26.controller;

import com.n26.methodstat.MethodStats;
import com.n26.model.StatisticsSummary;
import com.n26.model.response.StatisticsResponse;
import com.n26.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.n26.constant.Constants.ENDPOINT_STATISTICS;
import static com.n26.utils.ModelTransformerUtils.convertStatisticsSummaryToResponse;

@RestController
@RequestMapping(ENDPOINT_STATISTICS)
public class StatisticsController {

    @Autowired
    private IStatisticsService statisticsService;

    @GetMapping
    @MethodStats
    public ResponseEntity<StatisticsResponse> getStatistics() {
        StatisticsSummary statisticsSummary = statisticsService.getStatistics();
        return new ResponseEntity<>(convertStatisticsSummaryToResponse(statisticsSummary), HttpStatus.OK);
    }


}
