package com.example.Project2Boot.controllers.books;


import com.example.Project2Boot.models.Book;
import com.example.Project2Boot.models.User;
import com.example.Project2Boot.services.BooksService;
import com.example.Project2Boot.services.OrdersService;
import com.example.Project2Boot.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/books")
public class BooksController {


    private final UsersService usersService;
    private final BooksService booksService;
    private final OrdersService ordersService;

    @Autowired
    public BooksController(UsersService usersService, BooksService booksService, OrdersService ordersService) {
        this.usersService = usersService;
        this.booksService = booksService;
        this.ordersService = ordersService;
    }

    @GetMapping()
    public String getAllBooks(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/books";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping("")
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.showBook(id));
        if (ordersService.isBooked(id)) {
            model.addAttribute("isOrdered", true);
            model.addAttribute("user", usersService.showUserWhoOrderedBook(id));
        } else {
            model.addAttribute("users", usersService.findAll());
            model.addAttribute("isOrdered", false);
            model.addAttribute("user", new User());
        }
        return "books/showBook";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.showBook(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/editBook";
        }

        booksService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksService.deleteBook(id);
        return "redirect:/books";
    }

    @DeleteMapping("/order/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        ordersService.deleteOrderByBookId(id);
        System.out.println("FINISH\n");
        return "redirect:/books";
    }

    @PostMapping("/order/{id}")
    String orderBook(@PathVariable("id") int id, @ModelAttribute("user") User user) {
        System.out.println(user);
        System.out.println(id);
        ordersService.order(user, id);
        return "redirect:/books";
    }
}
