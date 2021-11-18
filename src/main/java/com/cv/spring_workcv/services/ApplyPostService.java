package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.Recruitment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplyPostService {

    List<ApplyPost> getApplyPostsByRecruitment(Recruitment recruitment);
}
