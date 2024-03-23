package com.example.Project2Boot.services;

import com.example.Project2Boot.models.Book;
import com.example.Project2Boot.models.User;
import com.example.Project2Boot.repositories.BooksRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final EntityManager entityManager;
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(EntityManager entityManager, BooksRepository booksRepository) {
        this.entityManager = entityManager;
        this.booksRepository = booksRepository;
    }


    //почему делает N+1 запрос? Все данные же приходят книги
    /*
Hibernate: select book0_.id as id1_0_, book0_.author as author2_0_, book0_.established as establis3_0_, book0_.name as name4_0_ from Book book0_ where book0_.id in (select order1_.bookId from orders order1_ where order1_.userId=?)
Hibernate: select order0_.id as id1_1_0_, order0_.bookId as bookid2_1_0_, order0_.userId as userid3_1_0_, book1_.id as id1_0_1_, book1_.author as author2_0_1_, book1_.established as establis3_0_1_, book1_.name as name4_0_1_, order2_.id as id1_1_2_, order2_.bookId as bookid2_1_2_, order2_.userId as userid3_1_2_, user3_.id as id1_2_3_, user3_.birthDate as birthdat2_2_3_, user3_.name as name3_2_3_ from orders order0_ left outer join Book book1_ on order0_.bookId=book1_.id left outer join orders order2_ on book1_.id=order2_.id left outer join User user3_ on order0_.userId=user3_.id where order0_.id=?
Hibernate: select order0_.id as id1_1_0_, order0_.bookId as bookid2_1_0_, order0_.userId as userid3_1_0_, book1_.id as id1_0_1_, book1_.author as author2_0_1_, book1_.established as establis3_0_1_, book1_.name as name4_0_1_, order2_.id as id1_1_2_, order2_.bookId as bookid2_1_2_, order2_.userId as userid3_1_2_, user3_.id as id1_2_3_, user3_.birthDate as birthdat2_2_3_, user3_.name as name3_2_3_ from orders order0_ left outer join Book book1_ on order0_.bookId=book1_.id left outer join orders order2_ on book1_.id=order2_.id left outer join User user3_ on order0_.userId=user3_.id where order0_.id=?

     */
    public List<Book> getAllBooksOrderedByUser(User user) {
        Session session = entityManager.unwrap(Session.class);
        List<Book> foundBooks =
                session.createQuery("from Book as b where b.id in (select ord.book.id from Order as ord where ord.user.id = :userId)")
                        .setParameter("userId", user.getId())
                .getResultList();
        return foundBooks;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        System.out.println(book);
        //book.setEstablished();
        booksRepository.save(book);
    }

    public Book showBook(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        booksRepository.save(book);
    }

    @Transactional
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }
}
