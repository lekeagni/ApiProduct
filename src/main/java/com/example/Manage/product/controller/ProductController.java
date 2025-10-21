package com.example.Manage.product.controller;

import com.example.Manage.product.dto.ProductDTO;
import com.example.Manage.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = " endpoints d'APIs RESTfull de gestion des produits")
@RequestMapping("products")
public class ProductController {
    private  final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/save")
    @Operation(summary = "add product", description = "save product in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "saved product successful"),
            @ApiResponse(responseCode = "400", description = "failed to create product"),
            @ApiResponse(responseCode = "500", description = "error server")
    })
public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.createProduct(dto));
    }

    @Operation(summary = "get all products", description = "get products in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get product successful"),
            @ApiResponse(responseCode = "400", description = "failed to get product"),
            @ApiResponse(responseCode = "500", description = "error server")
    })
    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.getAllProducts());
    }
    @Operation(summary = "get product", description = "get product in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get product successful"),
            @ApiResponse(responseCode = "400", description = "failed to get product"),
            @ApiResponse(responseCode = "500", description = "error server")
    })
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDTO>getProduct(@PathVariable int product_id){
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.getProductById(product_id));
    }

    @Operation(summary = "update product", description = "update product in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "update product successful"),
            @ApiResponse(responseCode = "400", description = "failed to updated product"),
            @ApiResponse(responseCode = "500", description = "error server")
    })
    @PutMapping("/update/{product_id}")
    public ResponseEntity<ProductDTO> update(@PathVariable int product_id, @RequestBody ProductDTO dto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.productService.updateProduct(product_id,dto));
    }

    @DeleteMapping("/delete/{product_id}")
    @Operation(summary = "delete product", description = "delete product in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deleted product successful"),
            @ApiResponse(responseCode = "400", description = "failed to delete product"),
            @ApiResponse(responseCode = "500", description = "error server")
    })
    public ResponseEntity<Boolean> delete(@PathVariable int product_id){
        boolean exist = this.productService.deleteProduct(product_id);
        if (exist){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
        }
return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
    }
}
