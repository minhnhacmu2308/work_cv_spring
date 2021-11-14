package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email,String password);

    User findUserById(int id);
}
