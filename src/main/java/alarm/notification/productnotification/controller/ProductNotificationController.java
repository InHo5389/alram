package alarm.notification.productnotification.controller;

import alarm.notification.productnotification.service.ProductNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductNotificationController {

    private final ProductNotificationService productNotificationService;

    @PostMapping("/products/{productId}/notifications/re-stock")
    public ResponseEntity<Void> sendRestockNotifications(@PathVariable Long productId) {
        productNotificationService.sendRestockNotifications(productId);
        return ResponseEntity.ok().build();
    }
}
