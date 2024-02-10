package com.example.eCommPlatform.controllerTest;

import com.example.eCommPlatform.controller.productController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.eCommPlatform.entity.product;
//import com.example.eCommerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(productController.class)
public class productControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private productController productController;

    private product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new product(1L, "Test Product", "Description", 10.0, 100);
    }

    @Test
    public void testGetProductById() throws Exception {
        // Perform GET request and validate response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", testProduct.getProductId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testProduct.getName()));
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Perform POST request and validate response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products")
                        .content(asJsonString(testProduct))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testProduct.getName()));
    }

    @Test
    public void testApplyDiscount() throws Exception {
        double discountPercentage = 10.0; // 10% discount
        testProduct.setPrice(testProduct.getPrice() - (testProduct.getPrice() * (discountPercentage / 100)));

        // Perform POST request to apply discount and validate response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/{id}/apply-discount", testProduct.getProductId())
                        .param("discountPercentage", String.valueOf(discountPercentage))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(testProduct.getPrice()));
    }

    @Test
    public void testApplyTax() throws Exception {
        double taxRate = 5.0; // 5% tax
        testProduct.setPrice(testProduct.getPrice() + (testProduct.getPrice() * (taxRate / 100)));
        // Perform POST request to apply tax and validate response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/{id}/apply-tax", testProduct.getProductId())
                        .param("taxRate", String.valueOf(taxRate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(testProduct.getPrice()));
    }

    // Helper method to convert object to JSON string
    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
