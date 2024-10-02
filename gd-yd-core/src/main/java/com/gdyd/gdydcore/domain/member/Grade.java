package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydsupport.annotation.EnableEnumDeserializer;
import lombok.Getter;

@Getter
@EnableEnumDeserializer
public enum Grade {
    DEFAULT("없음"),
    FIRST("1학년"),
    SECOND("2학년"),
    THIRD("3학년"),
    FOURTH("4학년"),
    FIFTH("5학년"),
    SIXTH("6학년"),
    SEVENTH("7학년"),
    EIGHTH("8학년"),
    NINTH("9학년"),
    TENTH("10학년");

    private final String value;

    Grade(String value) {
        this.value = value;
    }
}
