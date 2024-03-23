package ru.poltoranin.datafaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.poltoranin.datafaker.dto.UserDTO;
import ru.poltoranin.datafaker.dto.UserUpdateDTO;
import ru.poltoranin.datafaker.mapper.UserMapper;
import ru.poltoranin.datafaker.repository.UserRepository;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getUsers() {
        var users = userRepository.findAll();
        return users.stream().map(userMapper::map).toList();
    }

    public UserDTO updateUser(Long id, UserUpdateDTO userData) {
        var user = userRepository.findById(id).orElseThrow();
        userMapper.update(userData, user);
        userRepository.save(user);
        return userMapper.map(user);
    }
}
