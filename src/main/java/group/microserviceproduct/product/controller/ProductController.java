package group.microserviceproduct.product.controller;


import group.microserviceproduct.product.dto.ProductRequestDto;
import group.microserviceproduct.product.dto.ProductResponse;
import group.microserviceproduct.product.dto.ProductUpdateRequest;
import group.microserviceproduct.product.service.ProductCacheService;
import group.microserviceproduct.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductCacheService productCacheService;

    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<String> createAdminProduct(@RequestBody ProductRequestDto requestDto) {
        productService.createProduct(requestDto);
        return ResponseEntity.status(201)
                .body("Product created successfully");
    }
   // @Secured("ROLE_ADMIN")
    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest productRequest) {
        productService.updateProduct(productId, productRequest);
        return ResponseEntity.status(200)
                .body("Product update successfully");
    }
    //@Secured("ROLE_ADMIN")
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.status(200)
                .body("Product delete successfully");
    }
/*
    @GetMapping("/all")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }
*/
    @PostMapping("{productId}/image")
    public void uploadProductImage(@PathVariable Long productId, @RequestParam("file") MultipartFile file) throws IOException {
        productService.uploadProductImage(productId,file);
    }

    @GetMapping("{productId}/image")
    public String getProductImage(@PathVariable Long productId) throws IOException {
        return productService.getProductImage(productId);
    }

    @GetMapping("/all")
    public List<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return productCacheService.getAllProducts(pageable);
    }

}
