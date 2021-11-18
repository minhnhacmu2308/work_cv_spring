package com.cv.spring_workcv.services.impl;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.repository.ApplyPostRepository;
import com.cv.spring_workcv.services.ApplyPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyPostServiceImpl implements ApplyPostService {

    @Autowired
    ApplyPostRepository applyPostRepository;

    @Override
    public List<ApplyPost> getApplyPostsByRecruitment(Recruitment recruitment) {
        return applyPostRepository.findApplyPostByRecruitment(recruitment);
    }
}
