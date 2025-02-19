package com.bit.myboardapp.controller;

import com.bit.myboardapp.dto.ResponseDto;
import com.bit.myboardapp.dto.UserDto;
import com.bit.myboardapp.jwt.JwtProvider;
import com.bit.myboardapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserDto userDto) {
        ResponseDto<UserDto> responseDto = new ResponseDto<>();

        try {
            log.info("join userDto: {}", userDto);
            UserDto joinUserDto = userService.join(userDto);
            responseDto.setStatusCode(HttpStatus.CREATED.value());
            responseDto.setStatusMessage("created");
            responseDto.setItem(joinUserDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e){
            log.error("join error: {}", e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) {
        ResponseDto<UserDto> responseDto = new ResponseDto<>();

        try {
            log.info("login userDto: {}", userDto);
            UserDto LoginUserDto = userService.login(userDto);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("ok");
            responseDto.setItem(LoginUserDto);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("login error: {}", e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
        ResponseDto<Map<String, String>> responseDto = new ResponseDto<>();

        try {
            String token = authHeader.replace("Bearer ", "");
            if(!jwtProvider.isValidToken(token)){
                throw new IllegalArgumentException("invalid token");
            }
            Map<String, String> logoutMsgMap = new HashMap<>();
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(null);
            SecurityContextHolder.setContext(securityContext);
            logoutMsgMap.put("message", "logout successful");
            return ResponseEntity.ok(logoutMsgMap);
        } catch (Exception e){
            log.error("logout error: {}", e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    // 이메일 중복 체크
    @GetMapping("/email-check")
    public ResponseEntity<?> emailCheck(@RequestBody UserDto userDto) {
        ResponseDto<Map<String, String>> responseDto = new ResponseDto<>();

        try {
            log.info("emailCheck userDto: {}", userDto);
            Map<String, String> emailCheckMap = userService.emailCheck(userDto);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("email check successful");
            responseDto.setItem(emailCheckMap);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e){
            log.error("emailCheck error: {}", e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    // 닉네임 중복 체크
    @GetMapping("/nickname-check")
    public ResponseEntity<?> nicknameCheck(@RequestBody UserDto userDto) {
        ResponseDto<Map<String, String>> responseDto = new ResponseDto<>();

        try {
            log.info("nicknameCheck userDto: {}", userDto);
            Map<String, String> nicknameCheckMap = userService.nicknameCheck(userDto);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("nickname check successful");
            responseDto.setItem(nicknameCheckMap);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("nicknameCheck error: {}", e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    // 전화번호
    @GetMapping("/tel-check")
    public ResponseEntity<?> telCheck(@RequestBody UserDto userDto) {
        ResponseDto<Map<String, String>> responseDto = new ResponseDto<>();

        try {
            log.info("telCheck userDto: {}", userDto);
            Map<String, String> telcheckMap = userService.telCheck(userDto);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("tel check successful");
            responseDto.setItem(telcheckMap);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("telCheck error: {}", e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

    // 유저 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        ResponseDto<UserDto> responseDto = new ResponseDto<>();

        try{
            log.info("getUserById id: {}", id);
            UserDto userInfo = userService.getUserById(id);
            responseDto.setStatusCode(HttpStatus.OK.value());
            responseDto.setStatusMessage("ok");
            responseDto.setItem(userInfo);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e){
            log.error("getUserById error: {}", e.getMessage());
            responseDto.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            responseDto.setStatusMessage(e.getMessage());
            return ResponseEntity.internalServerError().body(responseDto);
        }
    }

}
