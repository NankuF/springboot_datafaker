package ru.poltoranin.datafaker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.poltoranin.datafaker.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
