package group.microserviceproduct.product.controller;

import group.microserviceproduct.product.entity.Product;
import group.microserviceproduct.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external")
public class FiegnProductController {

    private final ProductService productService;

    @GetMapping("/products/{productId}")
    public Product getProducts(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }
}
