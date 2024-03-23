package com.example.Project2Boot.controllers.users;

import com.example.Project2Boot.models.User;
import com.example.Project2Boot.services.BooksService;
import com.example.Project2Boot.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;
    private final BooksService booksService;

    @Autowired
    public UsersController(UsersService usersService, BooksService booksService) {
        this.usersService = usersService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", usersService.findAll());
        //model.addAttribute("users", userDao.getAllUsers());
        return "users/users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping("")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/new";
        }
        usersService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        User user = usersService.findOne(id);
        model.addAttribute("user", user);
        model.addAttribute("books", booksService.getAllBooksOrderedByUser(user));
        return "users/showUser";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", usersService.findOne(id));
        return "users/editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }

        usersService.update(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        usersService.delete(id);
        return "redirect:/users";
    }
}
