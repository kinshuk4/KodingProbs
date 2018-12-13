package com.n26.model.response;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class StatisticsResponse {
    private final String sum;
    private final String avg;
    private final String max;
    private final String min;
    private final Long count;

}
