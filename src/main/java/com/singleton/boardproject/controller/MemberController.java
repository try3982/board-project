package com.singleton.boardproject.controller;

import com.singleton.boardproject.dto.MemberLoginRequestDto;
import com.singleton.boardproject.dto.MemberRegisterRequestDto;
import com.singleton.boardproject.dto.MemberRegisterResponseDto;
import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody MemberRegisterRequestDto request) {
        Member member = memberService.register(request);
        return ResponseEntity.ok(
                Map.of(
                        "message", "회원가입이 완료되었습니다.",
                        "member", new MemberRegisterResponseDto(member)
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody MemberLoginRequestDto request,
                                   HttpSession session) {
        Member member = memberService.authenticate(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(
                Map.of(
                        "message", "로그인 성공",
                        "member", Map.of(
                                "id", member.getId(),
                                "email", member.getEmail()
                        )
                )
        );
    }

    @DeleteMapping()
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "로그아웃 완료"));
    }

}
