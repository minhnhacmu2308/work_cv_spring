package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.Recruitment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {

     List<Recruitment> findAll(Sort sort);

}
