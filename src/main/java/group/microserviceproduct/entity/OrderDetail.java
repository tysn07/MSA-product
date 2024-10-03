package group.microserviceproduct.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long productId;

    @Column
    private String productName;

    @Column
    private Long price;

    @Column
    private Long orderId;

    @Column
    private Long quantity;



}


