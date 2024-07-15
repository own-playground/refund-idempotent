package com.tally.idempotent.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefundResponse {

    private String refundId;
    private String orderId;
    private double amount;
    private String status;

    private RefundResponse(final String refundId, final String orderId, final double amount, final String status) {
        this.refundId = refundId;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    public static RefundResponse of(final String refundId, final String orderId, final double amount, final String status) {
        return new RefundResponse(refundId, orderId, amount, status);
    }

}
