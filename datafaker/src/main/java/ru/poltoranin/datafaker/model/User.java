package ru.poltoranin.datafaker.model;

import static jakarta.persistence.GenerationType.IDENTITY;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;


@Data
@Entity // сущность в БД
@Table(name = "users") // название таблицы в БД
@EqualsAndHashCode(of = {"id"}) // сравнение сущностей по id
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank // для строк, не может быть пустой строкой/null
    private String firstName;
    @NotBlank
    private String lastName;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
