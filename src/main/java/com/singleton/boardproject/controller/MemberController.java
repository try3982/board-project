package com.singleton.boardproject.controller;

import com.singleton.boardproject.dto.MemberRegisterDto;
import com.singleton.boardproject.dto.MemberResponseDto;
import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody MemberRegisterDto request) {
        Member member = memberService.register(request);
        return ResponseEntity.ok(
                Map.of(
                        "message", "회원가입이 완료되었습니다.",
                        "member", new MemberResponseDto(member)
                )
        );
    }

}
