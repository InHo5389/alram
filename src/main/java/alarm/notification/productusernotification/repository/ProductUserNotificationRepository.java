package alarm.notification.productusernotification.repository;

import alarm.notification.productusernotification.service.ProductUserNotification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification,Long> {

    List<ProductUserNotification> findByProductId(Long productId);
    List<ProductUserNotification> findByProductIdAndIsActiveTrue(Long productId, Pageable pageable);
}
