package com.gdyd.gdydcore.domain.member;

import com.gdyd.gdydsupport.annotation.EnableEnumDeserializer;

@EnableEnumDeserializer
public enum LikeType {
    HIGH_SCHOOL_STUDENT_QUESTION, // 고등학생 질문
    UNIVERSITY_STUDENT_ANSWER, // 대학생 답변
    POST, // 게시글
    COMMENT // 댓글
}
