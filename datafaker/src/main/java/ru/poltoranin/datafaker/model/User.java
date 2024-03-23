package ru.poltoranin.datafaker.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity // сущность в БД
@Table(name = "users") // название таблицы в БД
@EqualsAndHashCode(of = {"id"}) // сравнение сущностей по id
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank // для строк, не может быть пустой строкой/null
    private String firstName;
    @NotBlank
    private String lastName;

}
