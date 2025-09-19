package com.singleton.boardproject.dto;

import com.singleton.boardproject.entity.Member;
import lombok.Getter;

@Getter
public class MemberLoginResponseDto {

    private final Long id;
    private final String email;
    private final String message;

    public MemberLoginResponseDto(Member member, String message) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.message = message;
    }
}
