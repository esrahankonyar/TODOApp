package com.todoapp.service;

import com.todoapp.UserDetailModel.UserDetailModel;
import com.todoapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetailModel loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findUserByUserName(username);
        return new UserDetailModel().build(user);
    }

}
