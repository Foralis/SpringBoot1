package com.example.Project2Boot.services;

import com.example.Project2Boot.models.User;
import com.example.Project2Boot.repositories.UsersRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UsersRepository usersRepository;
    private final OrdersService ordersService;
    private final EntityManager entityManager;

    @Autowired
    public UsersService(UsersRepository usersRepository, OrdersService ordersService, EntityManager entityManager) {
        this.usersRepository = usersRepository;
        this.ordersService = ordersService;
        this.entityManager = entityManager;
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(int id, User user) {
        user.setId(id);
        usersRepository.save(user);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }


    public User showUserWhoOrderedBook(int bookId) {
        Session session = entityManager.unwrap(Session.class);
        return (User) session.createQuery("from User as u where u.id = (select ord.user.id from Order as ord where ord.book.id = :bookId)")
                .setParameter("bookId", bookId)
                .getSingleResult();
    }
}
