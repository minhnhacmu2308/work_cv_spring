package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyPostRepository extends JpaRepository<ApplyPost, Integer> {

    List<ApplyPost> findApplyPostByRecruitment(Recruitment recruitment);
}
