package com.singleton.boardproject.dto;

import com.singleton.boardproject.entity.Member;
import lombok.Getter;

@Getter
public class MemberRegisterResponseDto {

    private String email;
    private String password;

    public MemberRegisterResponseDto(Member member) {
        this.email = member.getEmail();
        this.password = member.getPassword();
    }

}
