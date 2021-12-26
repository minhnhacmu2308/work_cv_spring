package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface FollowCompanyService {
    FollowCompany save(FollowCompany followCompany);
    FollowCompany findFollowCompanyByCompanyAndUser(Company company, User user);
}
