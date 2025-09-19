package com.singleton.boardproject.service;

import com.singleton.boardproject.dto.MemberRegisterRequestDto;
import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.repository.MemberRepository;
import com.singleton.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.singleton.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public Member register(MemberRegisterRequestDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(DUPICATE_EMAIL);
        }
        Member member = Member.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member authenticate(String email, String rawPassword) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(INVALID_LOGIN));

        if (!encoder.matches(rawPassword, member.getPassword())) {
            throw new CustomException(INVALID_LOGIN);
        }

        return member;
    }

}
