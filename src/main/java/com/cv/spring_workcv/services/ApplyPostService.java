package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplyPostService {

    List<ApplyPost> getApplyPostsByRecruitment(Recruitment recruitment);

    ApplyPost save(ApplyPost applyPost);

    ApplyPost findApplyPostByRecruitmentAndUser(Recruitment recruitment, User user);
}
