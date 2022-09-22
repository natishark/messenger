package com.messenger.mess.model.converters;

import com.messenger.mess.model.User;
import com.messenger.mess.model.dtos.UserSignUpDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserSignUpDtoToUserConverter implements Converter<UserSignUpDto, User> {
    @Override
    public User convert(UserSignUpDto dto) {
        return new User(dto.getLogin(), dto.getEmail(), dto.getPassword());
    }
}
