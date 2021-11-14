package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.Cv;
import com.cv.spring_workcv.domain.FollowCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<Cv, Integer> {
}
