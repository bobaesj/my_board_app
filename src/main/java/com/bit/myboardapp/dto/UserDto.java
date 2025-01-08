package com.bit.myboardapp.dto;

import com.bit.myboardapp.entity.User;
import com.bit.myboardapp.entity.UserGender;
import com.bit.myboardapp.entity.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDto {

    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String tel;
    private UserGender gender;
    private UserStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String token;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .tel(tel)
                .gender(gender)
                .status(status)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();
    }
}
