package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.repository.UserRepository;
import com.cv.spring_workcv.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error at [getAll]", e);
        }
        return null;
    }

    @Override
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error at [save]", e);
        }
        return null;
    }

    @Override
    public User checkEmailExist(String email) {
        try {
            return userRepository.findUserByEmail(email);
        } catch (Exception e) {
            log.error("Error at [save]", e);
        }
        return null;
    }

    @Override
    public User checkLogin(String email, String password) {
        try {
            return userRepository.findUserByEmailAndPassword(email,password);
        } catch (Exception e) {
            log.error("Error at [checkLogin]", e);
        }
        return null;
    }

    @Override
    public User getUserById(int id) {
        try {
            return userRepository.getById(id);
        } catch (Exception e) {
            log.error("Error at [checkLogin]", e);
        }
        return null;
    }
}
