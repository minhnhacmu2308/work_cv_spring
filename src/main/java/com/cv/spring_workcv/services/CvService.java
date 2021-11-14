package com.cv.spring_workcv.services;

import com.cv.spring_workcv.domain.Cv;
import com.cv.spring_workcv.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CvService {

    Cv saveFile(MultipartFile file, User user);

    Cv save(Cv cv);

    Cv getFile(User user);

    Cv getFileById(int id);
}
