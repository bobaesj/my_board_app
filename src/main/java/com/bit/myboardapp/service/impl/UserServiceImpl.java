package com.bit.myboardapp.service.impl;

import com.bit.myboardapp.dto.UserDto;
import com.bit.myboardapp.entity.User;
import com.bit.myboardapp.entity.UserStatus;
import com.bit.myboardapp.jwt.JwtProvider;
import com.bit.myboardapp.repository.UserRepository;
import com.bit.myboardapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public UserDto join(UserDto userDto) {

        if (userRepository.countByEmail(userDto.getEmail()) != 0) {
            throw new RuntimeException("exist email");
        }

        if (userRepository.countByNickname(userDto.getNickname()) != 0) {
            throw new RuntimeException("exist nickname");
        }

        if (userRepository.countByTel(userDto.getTel()) != 0){
            throw new RuntimeException("exist tel");
        }

        userDto.setStatus(UserStatus.Active);
        userDto.setCreatedDate(LocalDateTime.now());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserDto joinUserDto = userRepository.save(userDto.toEntity()).toDto();
        joinUserDto.setPassword("");
        log.info("join user: {}", joinUserDto);
        return joinUserDto;
    }

    @Override
    public UserDto login(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(
                () -> new RuntimeException("email not exist")
        );

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("wrong password");
        }

        if (!user.getStatus().equals(UserStatus.Active)) {
            throw new RuntimeException("not active");
        }

        UserDto loginUserDto = user.toDto();
        loginUserDto.setPassword("");
        loginUserDto.setToken(jwtProvider.createJwt(user));
        return loginUserDto;
    }

    @Override
    public Map<String, String> emailCheck(UserDto userDto) {
        Map<String, String> emailCheckMap = new HashMap<>();

        if (userRepository.countByEmail(userDto.getEmail()) == 0) {
            emailCheckMap.put("emailCheckMsg", "available email");
        } else {
            emailCheckMap.put("emailCheckMsg", "not available email");
        }
        return emailCheckMap;
    }

    @Override
    public Map<String, String> nicknameCheck(UserDto userDto) {
        Map<String, String> nicknameCheckMap = new HashMap<>();

        if (userRepository.countByNickname(userDto.getNickname()) == 0) {
            nicknameCheckMap.put("nicknameCheckMsg", "available nickname");
        } else {
            nicknameCheckMap.put("nicknameCheckMsg", "not available nickname");
        }
        return nicknameCheckMap;
    }

    @Override
    public Map<String, String> telCheck(UserDto userDto) {
        Map<String, String> telCheckMap = new HashMap<>();

        if (userRepository.countByTel(userDto.getTel()) == 0) {
            telCheckMap.put("telCheckMsg", "available tel");
        } else {
            telCheckMap.put("telCheckMsg", "not available tel");
        }
        return telCheckMap;
    }
}