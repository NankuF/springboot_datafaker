package ru.poltoranin.datafaker.specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.poltoranin.datafaker.dto.UserParamsDTO;
import ru.poltoranin.datafaker.model.User;

@Component
public class UserSpecification {

    public Specification<User> build(UserParamsDTO params) {
        return withFirstNameEqual(params.getFirstNameEqual())
                .and(withLastNameEqual(params.getLastNameEqual()))
                .and(withEmailCont(params.getEmailCont()))
                .and(withCreatedAtGt(params.getCreatedAtGt()))
                .and(withCreatedAtLt(params.getCreatedAtLt()))
                .and(withUpdatedAtGt(params.getUpdatedAtGt()))
                .and(withUpdatedAtLt(params.getUpdatedAtLt()));
    }

    private Specification<User> withFirstNameEqual(String firstName) {
        return (root, query, cb) -> firstName == null ? cb.conjunction()
                : cb.equal(root.get("firstName"), firstName);
    }

    private Specification<User> withLastNameEqual(String lastName) {
        return (root, query, cb) -> lastName == null ? cb.conjunction()
                : cb.equal(root.get("lastName"), lastName);
    }

    private Specification<User> withEmailCont(String substring) {
        return (root, query, cb) -> substring == null ? cb.conjunction()
                : cb.like(cb.lower(root.get("email")), "%" + substring + "%");
    }

    private Specification<User> withCreatedAtGt(String createdAt) {
        return (root, query, cb) -> createdAt == null ? cb.conjunction()
                : cb.greaterThan(root.get("createdAt"), stringToDateConverter(createdAt));
    }

    private Specification<User> withCreatedAtLt(String createdAt) {
        return (root, query, cb) -> createdAt == null ? cb.conjunction()
                : cb.lessThan(root.get("createdAt"), stringToDateConverter(createdAt));
    }

    private Specification<User> withUpdatedAtGt(String updatedAt) {
        return (root, query, cb) -> updatedAt == null ? cb.conjunction()
                : cb.greaterThan(root.get("updatedAt"), stringToDateConverter(updatedAt));
    }

    private Specification<User> withUpdatedAtLt(String updatedAt) {
        return (root, query, cb) -> updatedAt == null ? cb.conjunction()
                : cb.lessThan(root.get("updatedAt"), stringToDateConverter(updatedAt));
    }

    private LocalDateTime stringToDateConverter(String date) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
