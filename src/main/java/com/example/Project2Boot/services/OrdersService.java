package com.example.Project2Boot.services;


import com.example.Project2Boot.models.Order;
import com.example.Project2Boot.models.User;
import com.example.Project2Boot.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final BooksService booksService;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, BooksService booksService) {
        this.ordersRepository = ordersRepository;
        this.booksService = booksService;
    }

    public List<Order> findAll() {
        return ordersRepository.findAll();
    }

    public boolean isBooked(int id) {
        return ordersRepository.existsByBookId(id);
    }

    @Transactional
    public void deleteOrderByBookId(int bookId) {
        ordersRepository.deleteOrdersByBookId(bookId);
    }

    @Transactional
    public void order(User user, int bookId) {
        Order order = new Order();
        order.setUser(user);
        order.setBook(booksService.showBook(bookId));
        System.out.println(order.getUser());
        System.out.println(order.getBook());
        ordersRepository.save(order);
    }
}
