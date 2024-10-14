package alarm.notification.productusernotification.repository;

import alarm.notification.productusernotification.service.ProductUserNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUserNotificationHistoryRepository extends JpaRepository<ProductUserNotificationHistory,Long> {
}
