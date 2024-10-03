package group.microserviceproduct.entity;

import group.microserviceproduct.global.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(value = EnumType.STRING)
    private OrderState state;

    @Column
    private Long userId;

    @Column
    private Long addressId;

    @Column
    private String KakaoTid;

}
