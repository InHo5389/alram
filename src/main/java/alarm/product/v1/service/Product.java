package alarm.product.v1.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int stock;
    private int restockCount;

    public void increaseRestockCount(){
        this.restockCount++;
    }

    public boolean hasStock(){
        return this.stock > 0;
    }

    // 알림이 가면 재고를 감소하는 postman 테스트용 로직
    public void decreaseStock(){
        this.stock--;
    }
}
