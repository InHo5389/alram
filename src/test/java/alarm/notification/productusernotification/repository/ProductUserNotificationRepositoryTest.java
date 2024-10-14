package alarm.notification.productusernotification.repository;

import alarm.notification.productusernotification.service.ProductUserNotification;
import alarm.product.v1.repository.ProductRepository;
import alarm.product.v1.service.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ProductUserNotificationRepositoryTest {

    @Autowired
    private ProductUserNotificationRepository productUserNotificationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("ProductUserNotification 테이블에서 동일한 productId에 대해 isActive가 true, false 2개 데이터를 " +
            "넣으면 1개를 반환한다.")
    void findByProductIdAndIsActiveTrue1() {
        //given
        final int MAX_NOTIFICATIONS = 2;
        Long productId = 1L;
        Product product = productRepository.save(new Product(productId, "상품1", 20, 0));

        productUserNotificationRepository.save(new ProductUserNotification(1L, productId, 1L, 0, true, LocalDateTime.now(), LocalDateTime.now()));
        productUserNotificationRepository.save(new ProductUserNotification(2L, productId, 2L, 0, false, LocalDateTime.now(), LocalDateTime.now()));

        PageRequest pageRequest = PageRequest.of(0, MAX_NOTIFICATIONS);
        //when
        List<ProductUserNotification> productUserNotifications = productUserNotificationRepository.findByProductIdAndIsActiveTrue(productId, pageRequest);
        //then
        assertThat(productUserNotifications.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("ProductUserNotification 테이블에서 동일한 productId에 대해 isActive가 true인 데이터를 3개" +
            "넣으면 2개를 반환한다.")
    void findByProductIdAndIsActiveTrue2() {
        //given
        final int MAX_NOTIFICATIONS = 2;
        Long productId = 1L;
        Product product = productRepository.save(new Product(productId, "상품1", 20, 0));

        productUserNotificationRepository.save(new ProductUserNotification(1L, productId, 1L, 0, true, LocalDateTime.now(), LocalDateTime.now()));
        productUserNotificationRepository.save(new ProductUserNotification(2L, productId, 2L, 0, true, LocalDateTime.now(), LocalDateTime.now()));
        productUserNotificationRepository.save(new ProductUserNotification(2L, productId, 2L, 0, true, LocalDateTime.now(), LocalDateTime.now()));

        PageRequest pageRequest = PageRequest.of(0, MAX_NOTIFICATIONS);
        //when
        List<ProductUserNotification> productUserNotifications = productUserNotificationRepository.findByProductIdAndIsActiveTrue(productId, pageRequest);
        //then
        assertThat(productUserNotifications.size()).isEqualTo(MAX_NOTIFICATIONS);
    }
}