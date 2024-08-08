package com.br.acmpay.application.domains.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDomain {
    private long id;

    private LocalDateTime dataTransaction;

    private AccountDomain sourceAccount;

    private AccountDomain destinationAccount;

    private BigDecimal amount;
}
