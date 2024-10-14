package alarm.notification.productusernotification.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 상품 + 유저별 알림 히스토리
 * 개별 사용자 단위로 알림 발송 현황을 추적하고, 문제 발생 시 디버깅에 활용
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUserNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;
    private int restockCount;
    private LocalDateTime sendAt;

    public ProductUserNotificationHistory(Long productId, Long userId, int restockCount) {
        this.productId = productId;
        this.userId = userId;
        this.restockCount = restockCount;
        this.sendAt = LocalDateTime.now();
    }
}
