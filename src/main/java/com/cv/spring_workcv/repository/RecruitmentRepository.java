package com.cv.spring_workcv.repository;

import com.cv.spring_workcv.domain.Category;
import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface RecruitmentRepository extends PagingAndSortingRepository<Recruitment, Integer> {

     List<Recruitment> findAll(Sort sort);


     Page<Recruitment> findRecruitmentsByCompany(Company company, Pageable pageable);

     List<Recruitment> findRecruitmentsByCompany(Company company);

     Recruitment findRecruitmentById(int id);

     List<Recruitment> findRecruitmentByCategory(Category category);

     @Query(value = "SELECT * FROM recruitment ORDER BY created_at DESC",nativeQuery = true)
     Page<Recruitment> getList(Pageable pageable);

}
