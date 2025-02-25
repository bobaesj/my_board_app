package com.bit.myboardapp.entity;


import com.bit.myboardapp.dto.UserDto;
import com.bit.myboardapp.entity.converter.UserGenderConverter;
import com.bit.myboardapp.entity.converter.UserStatusConverter;
import com.bit.myboardapp.entity.enums.UserGender;
import com.bit.myboardapp.entity.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@SequenceGenerator(
        name = "userSeqGenerator",
        sequenceName = "USER_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "userSeqGenerator"
    )
    private Long userId;

    private String email;
    private String password;
    private String nickname;
    private String tel;

    @Convert(converter = UserGenderConverter.class)
    private UserGender gender;
    @Convert(converter = UserStatusConverter.class)
    private UserStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    public UserDto toDto() {
        return UserDto.builder()
                .userId(userId)
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