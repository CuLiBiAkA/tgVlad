package org.example.repository;

import org.example.model.Order;
import org.example.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends CrudRepository<Order, Long> {
    Order findOrderById(Long id);

    Order findOrderByFlagAndUser(Boolean flag, User user);

    List<Order> findOrdersByFlag(Boolean flag);
}
