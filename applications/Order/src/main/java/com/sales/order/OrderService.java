package com.sales.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository productRepo) {
        this.orderRepository = productRepo;
    }

    public Order listById(Long id){
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }else{
            throw new OrderNotFoundException();
        }
    }


    public List<Order> listAll() {
        return orderRepository.findAll();
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void delete(Order order){ orderRepository.delete(order); }
}
