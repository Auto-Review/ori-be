package org.example.autoreview.domain.member.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MemberConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role attribute) {
        if(Role.USER.equals(attribute)){
            return 1;
        }
        return 0;
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        if(dbData == 1){
            return Role.USER;
        }
        return Role.GUEST;
    }
}
