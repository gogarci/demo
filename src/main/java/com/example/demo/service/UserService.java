package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.exception.EmailAlreadyRegisteredException;
import com.example.demo.model.Phone;
import com.example.demo.model.User;
import com.example.demo.port.input.UserInputPort;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserInputPort {
    @Value("${validation.email.regex}")
    private String EMAIL_REGEX;

    private final UserRepository userRepository;

    @Override
    public User createUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            throw new EmailAlreadyRegisteredException("El correo ya esta registrado");
        }

        if (!isValidEmail(userDto.getEmail())) {
            throw new EmailAlreadyRegisteredException("El formato del correo es inválido");
        }

        User user = buildUser(userDto);

        return userRepository.save(user);
    }

    private User buildUser(UserDto userDto){
        return User.builder()
                .id(UUID.randomUUID())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .created(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .token(UUID.randomUUID().toString())
                .isActive(true)
                .phones(userDto.getPhones().stream().map(phoneDto -> Phone.builder()
                        .number(phoneDto.getNumber())
                        .citycode(phoneDto.getCitycode())
                        .contrycode(phoneDto.getContrycode())
                        .build()).collect(Collectors.toList()))
                .build();
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}
