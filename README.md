## 과제 설명

- 상품이 재입고 되었을 때, 재입고 알림을 설정한 유저들에게 재입고 알림을 보내줍니다.

## **비즈니스 요구 사항**

- 재입고 알림을 전송하기 전, 상품의 재입고 회차를 1 증가 시킨다.
    - 실제 서비스에서는 다른 형태로 관리하지만, 과제에서는 직접 관리한다.
- 상품이 재입고 되었을 때, 재입고 알림을 설정한 유저들에게 알림 메시지를 전달해야 한다.
- 재입고 알림은 재입고 알림을 설정한 유저 순서대로 메시지를 전송한다.
- 회차별 재입고 알림을 받은 유저 목록을 저장해야 한다.
- 재입고 알림을 보내던 중 재고가 모두 없어진다면 알림 보내는 것을 중단합니다.
- 재입고 알림 전송의 상태를 DB 에 저장해야 한다.
    - IN_PROGRESS (발송 중)
    - CANCELED_BY_SOLD_OUT (품절에 의한 발송 중단)
    - CANCELED_BY_ERROR (예외에 의한 발송 중단)
    - COMPLETED (완료)
 
## ERD
![image](https://github.com/user-attachments/assets/f0ad7605-3699-4cfd-93b8-669fd82150f8)

## 기술적 고민
- 구현이 쉽고 단일 인스턴스에서 속도 제한을 적용할 경우, 간단한 프로젝트에 적합하다고 생각하여 구글 Guava 채택
