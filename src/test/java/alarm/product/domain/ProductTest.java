package alarm.product.domain;

import alarm.product.v1.service.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("재입고 횟수를 증가시키면 +1씩 증가된다.")
    void increaseRestock(){
        //given
        int reStock = 1;
        Product product = new Product(1L, "상품1", 2, reStock);
        //when
        product.increaseRestockCount();
        //then
        assertThat(product.getRestockCount()).isEqualTo(reStock+1);
    }

}