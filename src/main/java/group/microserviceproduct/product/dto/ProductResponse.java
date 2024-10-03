package group.microserviceproduct.product.dto;


import group.microserviceproduct.product.entity.Product;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private Long price;
    private String imageUrl;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();

    }

}
