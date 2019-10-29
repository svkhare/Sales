package com.sales.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productService.listAll();
    }
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> delete(@PathVariable long id) throws ProductNotFoundException {
        Product product = productService.findById(id);
        productService.delete(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @GetMapping("product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getOneProduct(@PathVariable Long id) throws ProductNotFoundException {
        return productService.findById(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String productNotFound(ProductNotFoundException ex){
        return "No product found!";
    }


    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        Product oldprod = productService.findById(id);
        oldprod.setName(product.getName());
        oldprod.setBrand(product.getBrand());
        oldprod.setDescription(product.getDescription());
        oldprod.setPrice(product.getPrice());
        productService.save(oldprod);
        return new ResponseEntity<>(oldprod, HttpStatus.OK);

    }


    @PostMapping("/")
    public ResponseEntity create(@RequestBody Product product) {
        productService.save(product);
        return new ResponseEntity(HttpStatus.CREATED);
    }


}


