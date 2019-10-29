package com.sales.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import java.util.List;

@RestController
public class OrderController {

    private OrderService service;

    private RestOperations op;

    @Autowired
    public OrderController(RestOperations op, OrderService service) {
        this.op = op;
        this.service = service;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {
        return service.listAll();
    }

    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Order getOneOrder(@PathVariable Long id) {
        return service.listById(id);
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String orderNotFound(OrderNotFoundException ex){
        return "No order found!";
    }

    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody Order order){
        String url = "http://localhost:8081/product/";
        Product product = op.getForObject(url+order.getProductId(), Product.class);
        service.save(order);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<Order> update(@PathVariable long id, @RequestBody Order order){
        Order oldOrder = service.listById(id);
        oldOrder.setName(order.getName());
        oldOrder.setBrand(order.getBrand());
        oldOrder.setDescription(order.getDescription());
        oldOrder.setPrice(order.getPrice());
        service.save(oldOrder);
        return new ResponseEntity<>(oldOrder,HttpStatus.OK);

    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<Order> delete(@PathVariable long id) {
        Order order = service.listById(id);
        service.delete(order);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
