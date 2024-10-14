package alarm.product.v1.repository;

import alarm.product.v1.service.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
