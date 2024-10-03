package group.microserviceproduct.product.service;


import group.microserviceproduct.product.dto.ProductRequestDto;
import group.microserviceproduct.product.dto.ProductResponse;
import group.microserviceproduct.product.dto.ProductUpdateRequest;
import group.microserviceproduct.product.entity.Product;
import group.microserviceproduct.product.repository.ProductRepository;
import group.microserviceproduct.product.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    String bucketName = "sonshop-product";

    public void createProduct(ProductRequestDto productRequest) {

        Product product = Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .stock(productRequest.getStock())
                .build();

        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest productRequest) {
        Product product = getProduct(productId);
        product.update(productRequest);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("해당 상품이 존재하지 않습니다.")
        );
    }

    @Transactional
    public void deleteProduct(Long productId){
        Product product = getProduct(productId);
        productRepository.delete(product);
    }
/*
    public List<ProductResponse> getAllProducts(){
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(ProductResponse::new).toList();

    }
*/

    public void uploadProductImage(Long productId, MultipartFile file) throws IOException {
        String imageKey = UUID.randomUUID().toString();
        String format = "product-images/%s/%s".formatted(productId,
                imageKey) + ".PNG";
        s3Service.putObject(
                bucketName, format,
                file);
        String url = "https://" + bucketName + ".s3" + ".ap-northeast-2.amazonaws.com/" + format;
        Product product = getProduct(productId);
        product.updateImageUrl(url);
        productRepository.save(product);
    }

    public String getProductImage(Long productId) {
        try {
            return getProduct(productId).getImageUrl();
        } catch (NoSuchKeyException e) {
            throw new RuntimeException("요청한 상품 이미지가 S3 버킷에 존재하지 않습니다. 이미지 키를 확인해주세요.");
        }
    }
    private List<ProductResponse> getPageResponse(Page<Product> productPage) {
        return productPage.getContent().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return getPageResponse(productPage);
    }

}
