package com.bit.myboardapp.service;

import com.bit.myboardapp.dto.UserDto;

public interface UserService {

    UserDto join(UserDto userDto);

    UserDto login(UserDto userDto);

}
