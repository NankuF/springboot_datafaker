package ru.poltoranin.datafaker.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserParamsDTO {

    private String firstNameEqual;
    private String lastNameEqual;
    private String emailCont;
    private String createdAtGt;
    private String createdAtLt;
    private String updatedAtGt;
    private String updatedAtLt;
}
