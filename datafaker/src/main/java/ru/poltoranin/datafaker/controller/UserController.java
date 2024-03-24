package ru.poltoranin.datafaker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import ru.poltoranin.datafaker.dto.UserCreateDTO;
import ru.poltoranin.datafaker.dto.UserDTO;
import ru.poltoranin.datafaker.dto.UserParamsDTO;
import ru.poltoranin.datafaker.dto.UserUpdateDTO;
import ru.poltoranin.datafaker.service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDTO> getUsers(UserParamsDTO params, @RequestParam(defaultValue = "1") int page) {
        return userService.getUsers(params, page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/findByEmail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid UserCreateDTO userData) {
        return userService.saveUser(userData);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO putMethodName(@PathVariable Long id,
            @RequestBody @Valid UserUpdateDTO userData) {
        return userService.updateUser(id, userData);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(Long id) {
        userService.deleteUserById(id);
    }
}
