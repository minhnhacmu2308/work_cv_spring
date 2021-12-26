package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.SaveJob;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveJobRepository extends JpaRepository<SaveJob, Integer> {

    List<SaveJob> findSaveJobByRecruitment(Recruitment recruitment);

    SaveJob save(SaveJob saveJob);

    SaveJob findSaveJobByUserAndRecruitment(User user,Recruitment recruitment);
}
