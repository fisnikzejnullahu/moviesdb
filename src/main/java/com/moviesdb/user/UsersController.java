package com.moviesdb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(path = "/register")
    public String register(UserRegisterForm userRegisterForm) {
        return "users/register";
    }

    @PostMapping(path = "/register")
    public String create(@ModelAttribute("userRegisterForm") @Valid UserRegisterForm userRegisterForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(userRegisterForm.getUsername());
            System.out.println(userRegisterForm.getPassword());
            bindingResult.getAllErrors().forEach(objectError -> {
                System.out.println(objectError.getObjectName() + ", " + objectError.getDefaultMessage());
            });
            return "users/register";
        }

        try {
            usersService.create(userRegisterForm.getUsername(), userRegisterForm.getPassword());
        }catch (DataIntegrityViolationException ex) {
            bindingResult.addError(new FieldError("username", "username", "Username is taken! Try another one."));
            return "users/register";
        }

        return "login";
    }
}
