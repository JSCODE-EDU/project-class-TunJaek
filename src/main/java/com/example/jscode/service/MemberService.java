package com.example.jscode.service;

import com.example.jscode.dto.MemberRequestDTO;
import com.example.jscode.dto.MemberResponseDTO;
import com.example.jscode.entity.Member;
import com.example.jscode.exception.MemberEmailAlreadyExistsException;
import com.example.jscode.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    @Transactional
    public Member join(MemberRequestDTO memberRequestDTO){
        validateDuplicated(memberRequestDTO.getEmail());
        Member member = memberRepository.save(memberRequestDTO.toEntity());
        return member;
    }
    public void validateDuplicated(String email) {
        if (memberRepository.findByEmail(email).isPresent())
            throw new MemberEmailAlreadyExistsException();
    }
}
