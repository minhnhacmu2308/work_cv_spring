package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.repository.RecruitmentRepository;
import com.cv.spring_workcv.services.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitmentServiceImpl implements RecruitmentService {


    @Autowired
    RecruitmentRepository recruitmentRepository;


    @Override
    public List<Recruitment> getAll(Sort sort) {
        return recruitmentRepository.findAll(sort);
    }
}
