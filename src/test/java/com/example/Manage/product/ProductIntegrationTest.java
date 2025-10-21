package com.example.Manage.product;

import com.example.Manage.product.model.Product;
import com.example.Manage.product.repository.ProductRepositoy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepositoy productRepositoy;

    @Test
    public void CreateProductTest() throws Exception{
        Product product = new Product();
        product.setName("ensemble dame");
        product.setCategorie("vêtement");
        product.setPrix(10000.0);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("ensemble dame"));
    }

    @Test
    public void getAllProductsTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/products/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductByIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/products/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void UpdateProductTest() throws Exception{
        Product product = new Product();
        product.setName("veston");
        product.setCategorie("vêtement");
        product.setPrix(3000.0);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/update/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("veston"));
    }

    @Test
    public void deleteProductTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}
