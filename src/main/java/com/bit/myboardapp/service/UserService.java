package com.bit.myboardapp.service;

import com.bit.myboardapp.dto.UserDto;

import java.util.Map;

public interface UserService {

    UserDto join(UserDto userDto);

    UserDto login(UserDto userDto);

    Map<String, String> emailCheck(UserDto userDto);

    Map<String, String> nicknameCheck(UserDto userDto);

    Map<String, String> telCheck(UserDto userDto);
}
