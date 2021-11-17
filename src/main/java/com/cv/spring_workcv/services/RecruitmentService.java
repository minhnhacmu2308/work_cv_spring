package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentService{
    List<Recruitment> getAll(Sort sort);

    Recruitment save(Recruitment recruitment);

    Page<Recruitment> getRecruitmentByCompany(Company company, Pageable pageable);
}
