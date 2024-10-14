package alarm.notification.productnotification.service;

import alarm.notification.productnotification.repository.ProductNotificationHistoryRepository;
import alarm.notification.productusernotification.repository.ProductUserNotificationHistoryRepository;
import alarm.notification.productusernotification.repository.ProductUserNotificationRepository;
import alarm.notification.productusernotification.service.ProductUserNotification;
import alarm.notification.productusernotification.service.ProductUserNotificationHistory;
import alarm.product.v1.repository.ProductRepository;
import alarm.product.v1.service.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductNotificationService {

    private static final int MAX_NOTIFICATIONS = 550;
    private static final int RATE_LIMIT = 500;

    private final ProductRepository productRepository;
    private final ProductUserNotificationRepository productUserNotificationRepository;
    private final ProductNotificationHistoryRepository productNotificationHistoryRepository;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;


    @Transactional
    public void sendRestockNotifications(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        product.increaseRestockCount();

        List<ProductUserNotification> productUserNotifications = getProductUserNotificationsForRestock(productId);
        sendRestockNotification(productUserNotifications, product);
    }

    private List<ProductUserNotification> getProductUserNotificationsForRestock(Long productId) {
        PageRequest pageRequest = PageRequest.of(0, MAX_NOTIFICATIONS);
        return productUserNotificationRepository.findByProductIdAndIsActiveTrue(productId, pageRequest);
    }

    private void sendRestockNotification(List<ProductUserNotification> userNotifications, Product product) {
        if (userNotifications.isEmpty()) {
            throw new RuntimeException("재입고 알림 신청을 한 유저가 없습니다.");
        }
        ProductNotificationHistory notificationHistory = null;
        for (ProductUserNotification userNotification : userNotifications) {
            notificationHistory = createNotificationHistory(userNotification.getUserId(), product);
            if (!product.hasStock()) {
                notificationHistory.updateStatus(NotificationStatus.CANCELED_BY_SOLD_OUT);
                break;
            }
            try {
                sendNotificationToUser(product, userNotification);
                product.decreaseStock();
            } catch (Exception e) {
                notificationHistory.updateStatus(NotificationStatus.CANCELED_BY_ERROR);
                break;
            }
            if (notificationHistory != null && notificationHistory.getStatus() == NotificationStatus.IN_PROGRESS) {
                notificationHistory.updateStatus(NotificationStatus.COMPLETED);
            }
        }
    }

    private ProductNotificationHistory createNotificationHistory(Long userId, Product product) {
        ProductNotificationHistory history = new ProductNotificationHistory(userId, NotificationStatus.IN_PROGRESS, product);
        return productNotificationHistoryRepository.save(history);
    }

    private void sendNotificationToUser(Product product, ProductUserNotification productUserNotification) {
        ProductUserNotificationHistory notificationHistory = new ProductUserNotificationHistory(
                product.getId(), productUserNotification.getUserId(), product.getRestockCount());
        productUserNotificationHistoryRepository.save(notificationHistory);

        productUserNotification.deActivate();
    }
}

