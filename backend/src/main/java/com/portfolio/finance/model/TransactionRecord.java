package com.portfolio.finance.model;

import java.math.BigDecimal;

public record TransactionRecord(
        String id, String reference, String account, BigDecimal amount,
        String currency, String source, String status) {}
