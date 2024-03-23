package com.example.Project2Boot.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "name should not be empty")
    @Size(min = 3, max = 255, message = "name should be between 1 and 255")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "author should not be empty")
    @Size(min = 3, max = 255, message = "author should be between 1 and 255")
    @Column(name = "author")
    private String author;

    //@Pattern(regexp = "\\d{4}", message = "date should be in format yyyy")
    // с паттерном выше выдает ошибку, без него техническое сообщение от спринга при нарушении паттерна
    @Column(name = "established")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy")
    private Date established;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "bookId")
    private Order order;

    public Book(int id, String name, String author, Date established) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.established = established;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getEstablished() {
        return established;
    }

    public void setEstablished(Date established) {
        this.established = established;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", established=" + established +
                ", order=" + order +
                '}';
    }
}
