package com.social.test.service;

import com.social.test.domain.user.User;
import com.social.test.domain.user.UserRepository;
import com.social.test.dto.user.UserReqDto;
import com.social.test.dto.user.UserRespDto;
import com.sqld.test.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UserRespDto.JoinRespDto join(UserReqDto.JoinReqDto joinReqDto) {
        // 1. 동일 유저네임 존재 검사
        Optional<User> userOP = userRepository.findByUsername(joinReqDto.getUsername());
        if (userOP.isPresent()) {
            throw new CustomApiException("동일한 username이 존재합니다.");
        }

        // 2. 동일한 이메일 존재 검사
        Optional<User> userEM = userRepository.findByEmail(joinReqDto.getEmail());
        if (userEM.isPresent()) {
            throw new CustomApiException("동일한 email 로 가입된 계정이 존재합니다.");
        }

        // 3. 패스워드 인코딩 + 회원가입
        User userPS = userRepository.save(joinReqDto.toEntity(passwordEncoder));

        // 4. dto 응답
        return new UserRespDto.JoinRespDto(userPS);
    }

    @Transactional
    public boolean checkUsernameDuplicate(String username) {
        // 1. 회원가입 시 username 중복확인
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public boolean checkEmailDuplicate(String email) {
        // 1. 회원가입 시 email 중복확인
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public boolean checkNicknameDuplicate(String nickname){
        // 1. 회원가입 시 email 중복확인
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public UserRespDto.FindUsernameRespDto findUsername(UserReqDto.FindUsernameReqDto findUsernameReqDto) {
        // 1. 이메일로 user 정보 조회
        User userPS = userRepository.findByEmail(findUsernameReqDto.getEmail()).orElseThrow(() -> new CustomApiException("존재하지 않는 사용자입니다."));

        // 2. dto 응답
        return new UserRespDto.FindUsernameRespDto(userPS.getUsername());

    }
}
