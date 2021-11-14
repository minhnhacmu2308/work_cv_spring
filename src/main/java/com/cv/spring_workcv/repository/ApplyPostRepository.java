package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.ApplyPost;
import com.cv.spring_workcv.domain.FollowCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyPostRepository extends JpaRepository<ApplyPost, Integer> {
}
