package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.models.CompanyDto;
import com.cv.spring_workcv.repository.CompanyrRepository;
import com.cv.spring_workcv.services.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.tools.Klist;

import java.util.List;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyrRepository companyrRepository;

    @Override
    public Company getCompanyByUser(User user) {
        try {
            return companyrRepository.findCompanyByUser(user);
        } catch (Exception e) {
            log.error("Error at [getCompanyById]", e);
        }
        return null;
    }

    @Override
    public Company save(Company company) {
        try {
            return companyrRepository.save(company);
        } catch (Exception e) {
            log.error("Error at [save]", e);
        }
        return null;
    }

    @Override
    public List<Object[]> getAll(){
        return companyrRepository.getAll();
    }
}
