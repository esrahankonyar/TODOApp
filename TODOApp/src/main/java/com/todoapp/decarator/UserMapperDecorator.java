package com.todoapp.decarator;

import com.todoapp.dto.request.UserRequestDto;
import com.todoapp.entity.User;
import com.todoapp.mapper.UserMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class UserMapperDecorator implements UserMapper {

    @Setter(onMethod = @__({@Autowired}))
    private UserMapper userMapper;

    @Setter(onMethod = @__({@Autowired}))
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User userDtoToUser(UserRequestDto userRequestDTO) {
        User user = userMapper.userDtoToUser(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return user;
    }
}
