package group.microserviceproduct.product.service;


import group.microserviceproduct.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductCacheService {

    private final ProductService productService;
    private final RedissonClient redissonClient;

    public List<ProductResponse> getAllProducts(Pageable pageable) {
        String cacheKey = "products:page:" + pageable.getPageNumber();
        RBucket<List<ProductResponse>> bucket = redissonClient.getBucket(cacheKey);
        List<ProductResponse> cachedProducts = bucket.get();
        if (cachedProducts != null) {
            System.out.println("\"데이터가 레디스에 존재합니다. : " + cacheKey);
            return cachedProducts;
        }
        List<ProductResponse> productResponseList = productService.getAllProducts(pageable);
        if (pageable.getPageNumber() == 0) {
            bucket.setAsync(productResponseList, 5, TimeUnit.MINUTES);
        }
        System.out.println("데이터를 레디스에 저장합니다.: " + cacheKey);
        return productResponseList;
    }
}