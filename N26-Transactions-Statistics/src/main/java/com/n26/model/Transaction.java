package com.n26.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @NotNull
    private BigDecimal amount;
    @NotNull
    private LocalDateTime timestamp;
}
