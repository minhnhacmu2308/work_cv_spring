package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.FollowCompany;
import com.cv.spring_workcv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowCompanyRepository extends JpaRepository<FollowCompany, Integer> {

    FollowCompany save(FollowCompany followCompany);

    FollowCompany findFollowCompanyByCompanyAndUser(Company company, User user);
}
