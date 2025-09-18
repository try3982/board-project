package com.singleton.boardproject.service;

import com.singleton.boardproject.dto.MemberRegisterDto;
import com.singleton.boardproject.entity.Member;
import com.singleton.boardproject.repository.MemberRepository;
import com.singleton.exception.custom.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.singleton.exception.ErrorCode.DUPICATE_EMAIL;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Transactional
    public Member register(MemberRegisterDto request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(DUPICATE_EMAIL);
        }
        Member member = Member.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
        return memberRepository.save(member);
    }

}
