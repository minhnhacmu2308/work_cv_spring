package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.repository.FollowCompanyRepository;
import com.cv.spring_workcv.services.FollowCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowCompanyServiceImpl implements FollowCompanyService {

    @Autowired
    FollowCompanyRepository followCompanyRepository;

    @Override
    public FollowCompany save(FollowCompany followCompany) {
        return followCompanyRepository.save(followCompany);
    }

    @Override
    public FollowCompany findFollowCompanyByCompanyAndUser(Company company, User user) {
        return followCompanyRepository.findFollowCompanyByCompanyAndUser(company,user);
    }
}
