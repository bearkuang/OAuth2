package com.social.test.controller;

import com.social.test.config.auth.LoginUser;
import com.social.test.dto.ResponseDto;
import com.social.test.dto.user.UserReqDto;
import com.social.test.dto.user.UserRespDto;
import com.sqld.test.handler.ex.CustomApiException;
import com.social.test.service.CustomOAuth2UserService;
import com.social.test.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final CustomOAuth2UserService customOAuth2UserService;

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserReqDto.JoinReqDto joinReqDto, BindingResult bindingResult) {
        UserRespDto.JoinRespDto joinRespDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinRespDto), HttpStatus.CREATED);
    }

    //아이디 찾기
    @PostMapping("/find-username")
    public ResponseEntity<?> findUsername(@RequestBody @Valid UserReqDto.FindUsernameReqDto findUsernameReqDto, BindingResult bindingResult) {
        UserRespDto.FindUsernameRespDto findUsernameRespDto = userService.findUsername(findUsernameReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "아이디 찾기 성공", findUsernameRespDto), HttpStatus.OK);
    }

    @GetMapping("/user-name/{username}/exists")
    public ResponseEntity<?> checkIdDuplicate(@PathVariable @Valid String username) {

        // 1. username 중복 값 확인
        if (userService.checkUsernameDuplicate(username)) {
            return new ResponseEntity<>(new ResponseDto<>(-1, "이미 사용 중인 아이디 입니다.", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto<>(1, "사용 가능한 아이디 입니다.", null), HttpStatus.OK);
        }
    }

    @GetMapping("/nickname/{nickname}/exists")
    public ResponseEntity<?> checkNicknameDuplicate(@PathVariable @Valid String nickname) {

        // 1. username 중복 값 확인
        if (userService.checkNicknameDuplicate(nickname)) {
            return new ResponseEntity<>(new ResponseDto<>(-1, "이미 사용 중인 닉네임입니다.", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto<>(1, "사용 가능한 닉네임입니다.", null), HttpStatus.OK);
        }
    }

    @GetMapping("/user-email/{email}/exists")
    public ResponseEntity<?> checkEmailDuplicate(@PathVariable @Valid String email) {

        // 1. email 중복 값 확인
        if (userService.checkEmailDuplicate(email)) {
            return new ResponseEntity<>(new ResponseDto<>(-1, "이미 사용 중인 이메일 입니다.", null), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto<>(1, "사용 가능한 이메일 입니다.", null), HttpStatus.OK);
        }
    }
}