package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Recruitment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruitmentService{
    List<Recruitment> getAll(Sort sort);

}
