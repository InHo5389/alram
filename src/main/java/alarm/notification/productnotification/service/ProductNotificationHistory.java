package alarm.notification.productnotification.service;

import alarm.product.v1.service.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품별 재입고 알림 히스토리
 * 재입고 알림 프로세스의 전체적인 상태와 히스토리를 추적
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int restockCount;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private Long latestNotificationUserId;

    public ProductNotificationHistory(Long userId, NotificationStatus status, Product product) {
        this.productId = product.getId();
        this.restockCount = product.getRestockCount();
        this.status = status;
        this.latestNotificationUserId = userId;
    }

    public void updateStatus(NotificationStatus notificationStatus) {
        this.status = notificationStatus;
    }
}
