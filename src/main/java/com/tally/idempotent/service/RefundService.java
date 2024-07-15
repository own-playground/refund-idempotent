package com.tally.idempotent.service;

import com.tally.idempotent.service.dto.RefundRequest;
import com.tally.idempotent.service.dto.RefundResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {

    public RefundResponse refund(final RefundRequest refundRequest) {

        // PG 통신

        sleep(1000);

        return RefundResponse.of(generateRefundId(), refundRequest.getOrderId(), refundRequest.getAmount(), "SUCCESS");
    }

    private String generateRefundId() {
        return UUID.randomUUID().toString();
    }

    private void sleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
