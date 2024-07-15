# 환불 요청 (멱등성 & 재시도)

## 환불 요청 API Specification


- [x] 주문에 대해 환불하는 기능을 구현한다.
  - [ ] 환불 요청을 처리하기전에 주문의 완료 상태인지 확인한다.
  - [ ] 유효성 검증에 실패했을 경우 예외를 발생시킨다.
  - [ ] 주문 및 환불 정보를 별도 테이블로 관리한다. 
- [x] 각 요청은 멱등성을 보장하고, 동일한 요청이 여러번 발생해도 상태가 일관되도록 한다.
- [ ] 환불 처리 상태를 기록한다.
- [ ] 동일 주문에 대한 동시 환불 요청(동시성)에 대한 처리도 고려한다. (step 1)
  - [ ] 스케일 아웃이 가능하도록 설계한다. (step 2)
  - [ ] 100TPS에서의 환경이라고 가정하여 테스트를 진행한다. (step 3)

---

## Quickstart Guide

```shell
curl -X POST http://localhost:8080/api/v1/refund \
     -H "Content-Type: application/json" \
     -H "X-Idempotency-Key: unique-key-123" \
     -d '{"orderId":"202407160001", "amount":10000.00, "reason":"Customer request"}'
```
