package com.example.demo.port.input;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

public interface UserInputPort {
    User createUser(UserDto userDto);
}
