package ru.poltoranin.datafaker.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import ru.poltoranin.datafaker.dto.UserCreateDTO;
import ru.poltoranin.datafaker.mapper.UserMapper;
import ru.poltoranin.datafaker.model.User;
import ru.poltoranin.datafaker.repository.UserRepository;
import java.util.List;
import java.util.ArrayList;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private Faker faker;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    DataInitializerProperties dataInitializerProperties;

    public void run(ApplicationArguments args) throws Exception {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < dataInitializerProperties.getCount(); i++) {
            var userData = new UserCreateDTO();
            userData.setFirstName(faker.name().firstName());
            userData.setLastName(faker.name().lastName());
            var user = userMapper.map(userData);
            users.add(user);
        }
        userRepository.saveAll(users);
    }
}
