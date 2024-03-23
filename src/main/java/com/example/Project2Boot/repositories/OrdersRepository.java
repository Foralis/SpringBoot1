package com.example.Project2Boot.repositories;

import com.example.Project2Boot.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
    void deleteOrdersByBookId(int bookId);
    void deleteByBookId(int id);
    //boolean findOrderByBookId(int id);
    //boolean findOrderByBookIdExists(int id);
    boolean existsByBookId(int id);
}
