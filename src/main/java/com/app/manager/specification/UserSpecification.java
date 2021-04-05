package com.app.manager.specification;

import com.app.manager.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

public class UserSpecification {
    public static Specification<User> containsTextInAttributes(String text, List<String> attributes) {
        if (!text.contains("%")) {
            text = "%" + text + "%";
        }
        String finalText = text;
        return (root, query, builder) -> builder.or(root.getModel().getDeclaredSingularAttributes().stream()
                .filter(a -> attributes.contains(a.getName()))
                .map(a -> builder.like(root.get(a.getName()), finalText))
                .toArray(Predicate[]::new)
        );
    }

    public static Specification<User> containsTextInName(String text) {
        return containsTextInAttributes(text, Arrays.asList("firstName", "lastName", "email"));
    }
}
