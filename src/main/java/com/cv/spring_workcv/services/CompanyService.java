package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    Company getCompanyByUser(User user);

    Company save(Company company);
}
