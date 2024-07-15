package com.tally.idempotent.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundRequest {

    private String orderId;
    private double amount;
    private String reason;

}
