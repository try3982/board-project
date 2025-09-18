package com.singleton.boardproject.dto;

import com.singleton.boardproject.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private String email;
    private String password;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
    }

}
