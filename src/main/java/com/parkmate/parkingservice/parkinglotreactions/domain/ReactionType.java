package com.parkmate.parkingservice.parkinglotreactions.domain;

import lombok.Getter;

@Getter
public enum ReactionType {

    LIKE("좋아요"),
    DISLIKE("싫어요")
    ;

    private final String description;

    ReactionType(String description) {
        this.description = description;
    }
}
