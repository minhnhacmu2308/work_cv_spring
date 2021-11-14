package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.SaveJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveJobRepository extends JpaRepository<SaveJob, Integer> {
}
