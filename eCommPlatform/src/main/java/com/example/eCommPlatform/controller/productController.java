package com.example.eCommPlatform.controller;

import com.example.eCommPlatform.entity.product;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/products")
public class productController {

    private final Map<Long, product> products = new ConcurrentHashMap<>();
    private Long productIdSequence = 0L;

    @PostMapping
    public product createProduct(@RequestBody product product) {
        product.setProductId(++productIdSequence);
        products.put(product.getProductId(), product);
        return product;
    }

    @GetMapping("/{id}")
    public product getProductById(@PathVariable Long id) {
        return products.get(id);
    }

    @PutMapping("/{id}")
    public product updateProduct(@PathVariable Long id, @RequestBody product product) {
        if (products.containsKey(id)) {
            product.setProductId(id);
            products.put(id, product);
            return product;
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        products.remove(id);
    }

    @PostMapping("/{id}/{apply-discount}")
    public product applyDiscount(@PathVariable Long id, @RequestBody product discountPercentage) {
        if (!products.containsKey(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }

        product product = products.get(id);
        double discountedPrice = product.getPrice() * (1 - (product.getDiscountPercentage() / 100));
        product.setPrice(discountedPrice);

        return product;
    }

    @PostMapping("/{id}/apply-tax")
    public product applyTax(@PathVariable Long id, @RequestBody product taxRate) {
        if (!products.containsKey(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }

        product product = products.get(id);
        double taxedPrice = product.getPrice() * (1 + (product.getTaxRate() / 100));
        product.setPrice(taxedPrice);

        return product;
    }
}
