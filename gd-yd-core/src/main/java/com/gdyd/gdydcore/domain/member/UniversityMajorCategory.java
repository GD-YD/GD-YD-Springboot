package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydsupport.annotation.EnableEnumDeserializer;
import lombok.Getter;

@Getter
@EnableEnumDeserializer
public enum UniversityMajorCategory {
    // HumanitiesSocial
    LANGUAGE_LITERATURE("언어문학"),
    SOCIAL_SCIENCE("사회과학"),
    HUMANITIES("인문"),
    BUSINESS_ECONOMICS("경영경제"),
    LAW("법학"),

    // NaturalScience
    MATHEMATICS("수학"),
    PHYSICS("물리"),
    ASTRONOMY("천문"),
    EARTH_SCIENCE("지구"),
    CHEMISTRY("화학"),
    BIOLOGY("생명"),
    ENVIRONMENT("환경"),

    // Engineering
    CONSTRUCTION("건설"),
    MECHANICAL("기계"),
    MATERIALS("재료"),
    ELECTRICAL("전기"),
    ELECTRONIC("전자"),
    COMPUTER_SW("컴퓨터/SW"),
    CHEMICAL_ENGINEERING("화학공학"),
    POLYMER("고분자"),
    ENERGY("에너지"),
    INDUSTRIAL_SAFETY("산업안전"),

    // ArtsPhysical
    DANCE("무용"),
    SPORTS("체육"),
    MUSIC("음악"),
    KOREAN_MUSIC("국악"),
    THEATER_FILM("연극/영화"),
    APPLIED_ARTS("응용예술"),
    FINE_ARTS("미술"),

    // Others
    AGRICULTURE("농림수산"),
    AVIATION("항공"),
    EDUCATION("교육"),
    MILITARY("군사"),
    MEDICINE("의학"),
    PHARMACY("약학"),
    HEALTH("보건");

    private final String value;

    UniversityMajorCategory(String value) {
        this.value = value;
    }
}
