package com.cv.spring_workcv.controllers.publics;

import com.cv.spring_workcv.constant.CommonConstants;
import com.cv.spring_workcv.domain.Recruitment;
import com.cv.spring_workcv.domain.SaveJob;
import com.cv.spring_workcv.domain.User;
import com.cv.spring_workcv.services.RecruitmentService;
import com.cv.spring_workcv.services.SaveJobService;
import com.cv.spring_workcv.services.UserService;
import com.cv.spring_workcv.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("save-job")
public class SaveJobController {

    @Autowired
    SaveJobService saveJobService;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    UserService userService;

    Middleware middleware = new Middleware();

    @PostMapping("/save")
    public @ResponseBody String save(HttpServletRequest request){

        boolean check = middleware.middleware(request);
        if(check){
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(CommonConstants.SESSION_USER);
            String idRecuitement = request.getParameter("idRe");
            Recruitment recruitment = recruitmentService.getRecruitmentById(Integer.parseInt(idRecuitement));
            SaveJob checkSave = saveJobService.findSaveJobByUserAndRecruitment(user,recruitment);
            if (Objects.nonNull(checkSave))  {
                return "exist";
            }else{
                SaveJob saveJob = new SaveJob();
                saveJob.setUser(user);
                saveJob.setRecruitment(recruitment);
                saveJobService.save(saveJob);
                return "true";
            }
        }else{
            return "false";
        }

    }
}
