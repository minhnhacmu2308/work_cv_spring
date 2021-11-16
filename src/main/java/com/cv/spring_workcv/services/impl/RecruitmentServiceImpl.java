package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.repository.RecruitmentRepository;
import com.cv.spring_workcv.services.RecruitmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RecruitmentServiceImpl implements RecruitmentService {


    @Autowired
    RecruitmentRepository recruitmentRepository;


    @Override
    public List<Recruitment> getAll(Sort sort) {
        return recruitmentRepository.findAll(sort);
    }

    @Override
    public Recruitment save(Recruitment recruitment) {
        try {
            return recruitmentRepository.save(recruitment);
        } catch (Exception e) {
            log.error("Error at [save]", e);
        }
        return null;
    }
}
