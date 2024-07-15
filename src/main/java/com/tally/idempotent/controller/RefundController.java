package com.tally.idempotent.controller;

import com.tally.idempotent.annotation.Idempotent;
import com.tally.idempotent.service.RefundService;
import com.tally.idempotent.service.dto.RefundRequest;
import com.tally.idempotent.service.dto.RefundResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/api/v1/refund")
    @Idempotent(key = "refund", ttl = 3600)
    public RefundResponse refund(@RequestBody RefundRequest refundRequest) {
        final RefundResponse refunded = refundService.refund(refundRequest);

        return refunded;
    }
}
