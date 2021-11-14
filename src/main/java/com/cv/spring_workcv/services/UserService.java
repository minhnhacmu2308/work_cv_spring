package com.cv.spring_workcv.services;


import com.cv.spring_workcv.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User save(User user);

    User checkEmailExist(String email);

    User checkLogin(String email,String password);

    User getUserById(int id);
}
