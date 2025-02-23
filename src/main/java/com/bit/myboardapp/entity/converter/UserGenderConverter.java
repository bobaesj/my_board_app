package com.bit.myboardapp.entity.converter;

import com.bit.myboardapp.entity.enums.UserGender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// @Convert를 명시하지 않아도 자동으로 변환기 적용
@Converter(autoApply = true)
public class UserGenderConverter implements AttributeConverter<UserGender, String> {

    @Override
    public String convertToDatabaseColumn(UserGender userGender) {
        if (userGender == null) {
            return null;
        }
        // enum의 String값을 db에 저장
        return userGender.getValue();
    }

    @Override
    public UserGender convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        // db의 String값을 enum 변환
        return UserGender.fromValue(dbData);
    }
}
