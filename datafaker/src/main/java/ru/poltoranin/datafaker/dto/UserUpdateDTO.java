package ru.poltoranin.datafaker.dto;

import org.openapitools.jackson.nullable.JsonNullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    @NotBlank
    private JsonNullable<String> firstName;
    @NotBlank
    private JsonNullable<String> lastName;
}
