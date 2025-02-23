package com.bit.myboardapp.entity.converter;

import com.bit.myboardapp.entity.enums.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {
    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if(userStatus == null){
            return null;
        }
        return userStatus.getValue();
    }

    @Override
    public UserStatus convertToEntityAttribute(String dbData) {
        if(dbData == null || dbData.isEmpty()){
            return null;
        }
        return UserStatus.fromValue(dbData);
    }
}
