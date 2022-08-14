package org.example.service;

import org.example.model.Order;
import org.example.model.User;
import org.example.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderDao repository;

    public void saveOrder(Order order){
        repository.save(order);
    }
}
