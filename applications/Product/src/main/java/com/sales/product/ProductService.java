package com.sales.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> listAll() {
        return repo.findAll();
    }

    public void save(Product product) {
        repo.save(product);
    }

    public void delete(Long id){
         repo.deleteById(id);
    }

    public Product findById(Long id){
        Optional<Product> product = repo.findById(id);
        if (product.isPresent()) {
            return product.get();
        }else{
            throw new ProductNotFoundException();
        }
    }
}


