package alarm.notification.productnotification.repository;

import alarm.notification.productnotification.service.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory,Long> {
}
