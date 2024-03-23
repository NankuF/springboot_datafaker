package ru.poltoranin.datafaker.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ru.poltoranin.datafaker.dto.UserDTO;
import ru.poltoranin.datafaker.dto.UserUpdateDTO;
import ru.poltoranin.datafaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/{id}")
    public UserDTO putMethodName(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO userData) {
        return userService.updateUser(id, userData);
    }
}
