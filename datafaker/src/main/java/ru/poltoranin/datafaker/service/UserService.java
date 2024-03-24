package ru.poltoranin.datafaker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.poltoranin.datafaker.dto.UserCreateDTO;
import ru.poltoranin.datafaker.dto.UserDTO;
import ru.poltoranin.datafaker.dto.UserParamsDTO;
import ru.poltoranin.datafaker.dto.UserUpdateDTO;
import ru.poltoranin.datafaker.exception.ResourceNotFoundException;
import ru.poltoranin.datafaker.mapper.UserMapper;
import ru.poltoranin.datafaker.repository.UserRepository;
import ru.poltoranin.datafaker.specification.UserSpecification;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserSpecification specBuilder;

    public Page<UserDTO> getUsers(UserParamsDTO params, Integer page) {
        var spec = specBuilder.build(params);
        var users = userRepository.findAll(spec, PageRequest.of(page - 1, 10));
        return users.map(userMapper::map);
    }

    public UserDTO getUserById(Long id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found"));
        return userMapper.map(user);
    }

    public UserDTO getUserByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User with email " + email + " not found"));
        return userMapper.map(user);
    }

    public UserDTO saveUser(UserCreateDTO userData) {
        var user = userMapper.map(userData);
        userRepository.save(user);
        return userMapper.map(user);
    }

    public UserDTO updateUser(Long id, UserUpdateDTO userData) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id " + id + " not found"));
        userMapper.update(userData, user);
        userRepository.save(user);
        return userMapper.map(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
