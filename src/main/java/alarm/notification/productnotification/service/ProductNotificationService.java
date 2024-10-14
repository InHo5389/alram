package alarm.notification.productnotification.service;

import alarm.notification.productnotification.repository.ProductNotificationHistoryRepository;
import alarm.notification.productusernotification.repository.ProductUserNotificationHistoryRepository;
import alarm.notification.productusernotification.repository.ProductUserNotificationRepository;
import alarm.notification.productusernotification.service.ProductUserNotification;
import alarm.notification.productusernotification.service.ProductUserNotificationHistory;
import alarm.product.v1.repository.ProductRepository;
import alarm.product.v1.service.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductNotificationService {
    private final ProductRepository productRepository;
    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;


    @Transactional
    public void sendRestockNotifications(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을수 없습니다."));
        product.increaseRestockCount();

        // 상품별 재입고 알림 테이블에서 상품 아이디로 find하고 userId들을 반환
        List<Long> restockSettingUserIds = getUserIdsFromRestockNotification(productId);
        sendRestockNotification(productId, restockSettingUserIds, product);
    }

    private List<Long> getUserIdsFromRestockNotification(Long productId) {
        List<ProductUserNotification> productUserNotifications = productUserNotificationRepository.findByProductId(productId);
        List<Long> restockSettingUserIds = productUserNotifications.stream()
                .map(ProductUserNotification::getUserId)
                .toList();
        return restockSettingUserIds;
    }

    private void sendRestockNotification(Long productId, List<Long> userIds, Product product) {
        ProductNotificationHistory notificationHistory = createNotificationHistory(productId, product);

        for (Long userId : userIds) {

            if (!product.hasStock()) {
                notificationHistory.updateStatus(NotificationStatus.CANCELED_BY_SOLD_OUT);
                break;
            }

            try {
                sendNotificationToUser(product, userId);

            } catch (Exception e) {
                notificationHistory.updateStatus(NotificationStatus.CANCELED_BY_ERROR);
                break;
            }
        }

        if (notificationHistory.getStatus() == NotificationStatus.IN_PROGRESS) {
            notificationHistory.updateStatus(NotificationStatus.COMPLETED);
        }
    }

    private ProductNotificationHistory createNotificationHistory(Long productId, Product product) {
        ProductNotificationHistory history = new ProductNotificationHistory(productId, NotificationStatus.IN_PROGRESS, product);
        return productNotificationHistoryRepository.save(history);
    }

    private void sendNotificationToUser(Product product, Long userId) {
        ProductUserNotificationHistory notificationHistory = new ProductUserNotificationHistory(
                product.getId(), userId, product.getRestockCount());

        productUserNotificationHistoryRepository.save(notificationHistory);
    }
}

