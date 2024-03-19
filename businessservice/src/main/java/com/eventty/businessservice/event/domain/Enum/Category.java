package com.eventty.businessservice.event.domain.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    concert(1L, "concert"),
    classical(2L, "classical"),
    exhibition(3L, "exhibition"),
    sports(4L, "sports"),
    camping(5L, "camping"),
    children(6L, "children"),
    movie(7L, "movie"),
    it(8L, "it"),
    culture(9L, "culture"),
    topic(10L, "topic");

    private final Long id;
    private final String name;

    public static String getNamefromId(Long id) {
        for (Category category : Category.values()) {
            if (category.getId().equals(id)) {
                return category.name;
            }
        }
        return null;
    }

}
