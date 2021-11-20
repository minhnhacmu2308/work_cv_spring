package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Company;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.SaveJob;

import java.util.List;

public interface SaveJobService {

    List<SaveJob> findSaveJobByRecruitment(Recruitment recruitment);
}
