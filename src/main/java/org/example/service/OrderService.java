package org.example.service;

import org.example.model.Order;
import org.example.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderDao repository;

    public void saveOrder(Order order){
        repository.save(order);
    }

    public Order getOrderById(Long id){
       return repository.findOrderById(id);
    }

    public Iterable<Order> getAllOrder() {
        return repository.findAll();
    }
}
