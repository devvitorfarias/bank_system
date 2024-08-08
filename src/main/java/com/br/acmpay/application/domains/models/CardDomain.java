package com.br.acmpay.application.domains.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDomain {
    private long id;

    private String flag;

    private BigDecimal card_limit;

    private AccountDomain cardAccount;
}
