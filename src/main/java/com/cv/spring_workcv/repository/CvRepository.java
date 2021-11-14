package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.Cv;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvRepository extends JpaRepository<Cv, Integer> {
    Cv findCvByUser(User user);

    Cv findCvById(int id);
}
